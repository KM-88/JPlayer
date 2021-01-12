package framew.api.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import core.global.ApplicationConstantsFramework;
import core.obj.FileSystemObject;
import framew.api.infc.Library;
import framew.api.infc.LibraryItemSearch;
import framew.api.infc.LibraryManager;
import framew.log.Logger;

public class LibraryManagerImpl implements LibraryManager {

	private static final List<Library> LIBRARIES = new LinkedList<>();

	private static Logger log;

	@Override
	synchronized public boolean addLibrary(Library object) {
		log = (Logger) ApplicationConstantsFramework.getLogger();
		boolean retValue = false;
		if (!LIBRARIES.contains(object)) {
			retValue = LIBRARIES.add(object);
			log.debug(LibraryManagerImpl.class, "addLibrary", "Adding Library= " + object + " Operation = " + retValue);
		}
		return retValue;
	}

	@Override
	synchronized public boolean removeLibrary(Library object) {
		return LIBRARIES.remove(object);
	}

	@Override
	public List<FileSystemObject> getFileSystemObjects() {
		List<FileSystemObject> tempObjects = new LinkedList<>();
		LIBRARIES.forEach(library -> {
			if (null != library.getMediaItems()) {
				library.getMediaItems().forEach((k, v) -> tempObjects.addAll(v));
			}
		});
		return tempObjects;
	}

	/**
	 * Reload all objects and save them to the DB.
	 *
	 */
	@Override
	public void reload() {
		final String methodName = "reload()";
		checkForNewPaths();
		log = (Logger) ApplicationConstantsFramework.getLogger();
		try {
			log.info(LibraryManagerImpl.class, methodName, "Library Refresh Activities : Starting");
			LIBRARIES.forEach((library) -> {
				library.reload();
			});
			log.info(LibraryManagerImpl.class, methodName, "Library Refresh Activities : Finished");
		} catch (Exception ex) {
			log.error(LibraryManagerImpl.class, methodName, "Exception Occurred" + ex.getMessage());
		} finally {
			System.gc();
		}
	}

	/**
	 * Check if any new path has been added to the Media Collection
	 */
	private void checkForNewPaths() {
		MediaCollectionsPaths.getAllPaths().forEach((path) -> {
			boolean newPath = true;

			for (Library lib : LIBRARIES) {
				if (lib.getPath().equals(path)) {
					newPath = false;
				}
			}
			if (newPath) {
				Library library = new Library(path);
				LIBRARIES.add(library);
				library.reload();
			}
		});
	}

	@Override
	public Map<FileSystemObject, List<FileSystemObject>> match(LibraryItemSearch libraryItemSearch) {
		Map<FileSystemObject, List<FileSystemObject>> returnValueMap = new HashMap<FileSystemObject, List<FileSystemObject>>();
		LIBRARIES.forEach(library -> {
			returnValueMap.putAll(library.match(libraryItemSearch));
		});
		return returnValueMap;
	}

	public List<Library> getAllLibraries() {
		List<Library> librariesCopy = new LinkedList<Library>();
		librariesCopy.addAll(LIBRARIES);
		return librariesCopy;
	}

}
