package framew.api.infc;

import java.util.List;
import java.util.Map;

import core.obj.FileSystemObject;

public interface LibraryItemSearch {

	public Map<FileSystemObject, List<FileSystemObject>> match(Map<FileSystemObject, List<FileSystemObject>> mediaItems);
	
}
