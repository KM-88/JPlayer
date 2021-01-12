package core.obj;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import core.global.ApplicationConstantsFramework;
import framew.api.impl.MediaCollectionsPaths;
import framew.log.Logger;
import lib.impl.ObjectCache;
import lib.inf.FileSystemScanner;
import lib.inf.DataSource;

public class InternalCacheDB extends ObjectCache {

	private Map<FileSystemObject, List<FileSystemObject>> cache;

	protected Logger log;

	public InternalCacheDB(String cacheName) {
		this.cache = new HashMap<FileSystemObject, List<FileSystemObject>>();
		this.cacheName = cacheName;
		this.log = (Logger) ApplicationConstantsFramework.getLogger();
		this.dataSource = (DataSource) ApplicationConstantsFramework
				.getByName(ApplicationConstantsFramework.DATA_SOURCE);
	}

	@Override
	public boolean refresh() {
		log = (Logger) ApplicationConstantsFramework.getByName("logger");
		int itemCount = 0, totalItemCount = 0;
		FileSystemScanner fileSystemScanner = (FileSystemScanner) ApplicationConstantsFramework
				.getByName(ApplicationConstantsFramework.FILE_SYSTEM_SCANNER);
		Map<Path, List<FileSystemObject>> fileSystemObjects = null;
		Map<FileSystemObject, List<FileSystemObject>> tmpCache = new HashMap<FileSystemObject, List<FileSystemObject>>();
		for (Path path : MediaCollectionsPaths.getAllPaths()) {
			try {
				fileSystemObjects = fileSystemScanner.getFilesAndDirectories(path, Integer.MAX_VALUE);
			} catch (IOException e) {
				e.printStackTrace();
				log.error(InternalCacheDB.class, "refresh",
						"IOException occured for path : " + path.toString() + "\n" + e.getStackTrace());
				continue;
			}
			for (Map.Entry<Path, List<FileSystemObject>> entry : fileSystemObjects.entrySet()) {
				if (null != entry.getValue() && !(entry.getValue().isEmpty())) {
					tmpCache.put(entry.getValue().get(0).getParent(), entry.getValue());
					itemCount++;
				}
			}
			log.debug(InternalCacheDB.class, "refresh", "Imported [" + itemCount + "] items from : " + path.toString());
			totalItemCount = totalItemCount + itemCount;
		}
		synchronized (this) {
			clear();
			cache = tmpCache;
		}
		log.debug(InternalCacheDB.class, "refresh", "Imported total of [" + totalItemCount + "] items ");
		return true;
	}

	@Override
	public Object get(Object key) {
		return cache.get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void put(Object key, Object value) {
		if (cache.get((FileSystemObject) key) == null) {
			LinkedList<FileSystemObject> temp = new LinkedList<FileSystemObject>();
			temp.add((FileSystemObject) value);
			cache.put((FileSystemObject) key, temp);
		} else
			cache.get((FileSystemObject) key).add((FileSystemObject) value);
	}

	@Override
	public Map<?, ?> getAllElements() {
		Map<FileSystemObject, List<FileSystemObject>> returnItems = new HashMap<FileSystemObject, List<FileSystemObject>>();
		cache.forEach((k, v) -> {
			List<FileSystemObject> value = new LinkedList<FileSystemObject>();
			v.forEach((v1) -> {
				try {
					value.add(v1.clone());
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			});
			try {
				returnItems.put(k.clone(), value);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		});
		return returnItems;
	}

	@Override
	public void clear() {
		synchronized (this) {
			cache.clear();
		}
	}

	@Override
	public void put(Object key, List value) {
		cache.put((FileSystemObject) key, (List<FileSystemObject>) value);
	}

}
