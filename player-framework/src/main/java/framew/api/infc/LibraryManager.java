package framew.api.infc;

import java.util.List;
import java.util.Map;

import core.obj.FileSystemObject;


public interface LibraryManager {

	boolean addLibrary(Library object);

	boolean removeLibrary(Library object);

	List<FileSystemObject> getFileSystemObjects();
	
	void reload();
	
	Map<FileSystemObject, List<FileSystemObject>> match(LibraryItemSearch libraryItemSearch);
	
	public List<Library> getAllLibraries();

}
