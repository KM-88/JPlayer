/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framew.api.infc;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import core.global.ApplicationConstantsFramework;
import core.obj.FileSystemObject;
import datasource.BaseDAO;
import framew.log.Logger;

/**
 *
 * @author kranti
 */
public class Library {

	private Logger log;

	private Path path;

	private String libraryName;

	private Map<FileSystemObject, List<FileSystemObject>> mediaItems = new HashMap<FileSystemObject, List<FileSystemObject>>();

	public Library(Path path) {
		this.path = path;
	}

	public Library(Path path, String name) {
		this.path = path;
		this.libraryName = name;
	}

	public Map<FileSystemObject, List<FileSystemObject>> match(LibraryItemSearch searchImpl) {
		return searchImpl.match(mediaItems);
	}

	public void reload() {
		Map<FileSystemObject, List<FileSystemObject>> tmpMediaItems = new HashMap<FileSystemObject, List<FileSystemObject>>();
		final String methodName = "reload()";
		int itemsCount = 0;
		try {
			log = (Logger) ApplicationConstantsFramework.getLogger();
			log.debug(Library.class, methodName, "Library [" + libraryName + "] Import Started from : " + path);
			@SuppressWarnings("unchecked")
			List<FileSystemObject> allFileSystemObjects = (List<FileSystemObject>) BaseDAO
					.getAll(FileSystemObject.class);
			for (FileSystemObject fileSystemObject : allFileSystemObjects) {
				List<FileSystemObject> valueItems = tmpMediaItems.get(fileSystemObject.getParent());
				if (null == valueItems) {
					valueItems = new LinkedList<FileSystemObject>();
					tmpMediaItems.put(fileSystemObject.getParent(), valueItems);
				}
				valueItems.add(fileSystemObject);
				itemsCount++;
			}
			mediaItems = tmpMediaItems;
			log.debug(Library.class, methodName,
					"Library [" + libraryName + "] Imported [" + itemsCount + "] items from : " + path);
		} finally {
			System.gc();
		}
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path uri) {
		this.path = uri;
	}

	public Map<FileSystemObject, List<FileSystemObject>> getMediaItems() {
		return mediaItems;
	}

	public void setMediaItems(Map<FileSystemObject, List<FileSystemObject>> mediaItems) {
		this.mediaItems = mediaItems;
	}

}
