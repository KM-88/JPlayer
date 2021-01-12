/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

import core.global.ApplicationConstantsFramework;
import core.obj.FileSystemObject;
import framew.log.Logger;
import lib.inf.DataSource;

/**
 *
 * @author kranti
 */
public class FileSystemObjectCache extends ObjectCache {

	private final Map<Path, List<FileSystemObject>> cache;

	protected Logger log;

	public FileSystemObjectCache(String cacheName, Logger log, DataSource dataSource) {
		this.cache = new HashMap<>();
		this.cacheName = cacheName;
		this.log = log;
		this.dataSource = dataSource;
	}

	@Override
	public Map<Path, List<FileSystemObject>> getAllElements() throws ClassCastException {
		Map<Path, List<FileSystemObject>> tempMap = new TreeMap<>();
		cache.forEach((k, v) -> {
			List<FileSystemObject> list = new LinkedList<>();
			v.forEach((a) -> {
				FileSystemObject b = null;
				try {
					b = a.clone();
				} catch (CloneNotSupportedException ex) {
					java.util.logging.Logger.getLogger(FileSystemObjectCache.class.getName()).log(Level.SEVERE, null,
							ex);
				}
				list.add(b);
			});
			Path p = Paths.get(k.toUri());
			tempMap.put(p, list);
		});
		return tempMap;
	}

	@Override
	public void clear() {
		cache.clear();
	}

	@Override
	public Object get(Object key) {
		return cache.get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void put(Object key, Object value) {
		if (cache.get((Path) key) == null) {
			LinkedList<FileSystemObject> temp = new LinkedList<FileSystemObject>();
			temp.add((FileSystemObject) value);
			cache.put((Path) key, temp);
		} else
			cache.get((Path) key).add((FileSystemObject) value);

	}

	@Override
	public boolean refresh() {
		log.info(this.getClass(), "refresh()", "Cache [" + cacheName + "] Refresh Started");
		cache.clear();
		cache.putAll((Map<Path, List<FileSystemObject>>) dataSource.getData());
		log.info(this.getClass(), "refresh()", "Cache [" + cacheName + "] Refresh Finished");
		return true;
	}

	@Override
	public void put(Object key, List value) {
		cache.put((Path) key, (List<FileSystemObject>) value);
	}

}
