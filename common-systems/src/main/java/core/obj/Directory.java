package core.obj;

import java.nio.file.Path;
import java.util.List;

public class Directory extends FileSystemObject {

	private List<File> filesList;

	private List<Directory> directoryList;

	public List<File> getFilesList() {
		return filesList;
	}

	public void setFilesList(List<File> filesList) {
		this.filesList = filesList;
	}

	public List<Directory> getDirectoryList() {
		return directoryList;
	}

	public void setDirectoryList(List<Directory> directoryList) {
		this.directoryList = directoryList;
	}
        
        public Directory(){
        
        }

	public Directory(String name, Path location, FileTypeEnum type) {
		super(name, location, type);
	}

	public Directory(String name, Path location) {
		super(name, location, FileTypeEnum.DIRECTORY);
	}

}
