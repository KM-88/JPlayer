package core.obj;

import java.nio.file.Path;

public class File extends FileSystemObject {

	private long size;
        
        public File(){
        
        }

	public File(String name, Path location, FileTypeEnum type) {
		super(name, location, type);
	}

	public File(String name, Path location) {
		super(name, location, FileTypeEnum.FILE);
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

}
