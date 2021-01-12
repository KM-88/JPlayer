package framew.api.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import core.obj.FileSystemObject;
import framew.api.infc.LibraryItemSearch;


public class LibraryItemSearchString implements LibraryItemSearch {

	private String searchString;

	public LibraryItemSearchString(String searchString) {
		this.setSearchString(searchString);
	}

	@Override
	public Map<FileSystemObject, List<FileSystemObject>> match(
			Map<FileSystemObject, List<FileSystemObject>> mediaItems) {
		Map<FileSystemObject, List<FileSystemObject>> returnMap = new HashMap<FileSystemObject, List<FileSystemObject>>();
		for (Map.Entry<FileSystemObject, List<FileSystemObject>> entry : mediaItems.entrySet()) {
			FileSystemObject newMapKey = null;
			List<FileSystemObject> newMapValues = new LinkedList<>();
			if (entry.getKey().getLocation().contains(searchString))
				newMapKey = entry.getKey();
			for (FileSystemObject fileSystemObject : entry.getValue()) {
				if (fileSystemObject.getLocation().contains(searchString))
					newMapValues.add(fileSystemObject);
			}
			if (null != newMapKey)
				returnMap.put(newMapKey, newMapValues);
		}
		return returnMap;

	}

	/**
	 * @return the searchString
	 */
	public String getSearchString() {
		return searchString;
	}

	/**
	 * @param searchString
	 *            the searchString to set
	 */
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

}
