package framew.api.impl;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import core.global.ApplicationConstantsFramework;

public class MediaCollectionsPaths {

	private static List<Path> collectionsPath;

	public static List<Path> getCollectionsPath() {
		collectionsPath = (List<Path>) ApplicationConstantsFramework
				.getByName(ApplicationConstantsFramework.MEDIA_COLLECTION_PATH);
		return collectionsPath;
	}

	public static void setCollectionsPath(List<Path> paths) {
		collectionsPath = paths;
	}

	synchronized public static List<Path> getAllPaths() {
		List<Path> paths = new LinkedList<Path>();
		if (null == collectionsPath)
			getCollectionsPath();
		for (Path path : collectionsPath)
			paths.add(path);
		return paths;
	}

}
