package datasource;

import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import core.global.ApplicationConstantsFrameworkTest;
import core.obj.FileSystemObject;
import framew.log.Logger;

public class CacheDAOTest {

	List<FileSystemObject> fileObjectList = new LinkedList<FileSystemObject>();
	CacheDAO cacheDao = new CacheDAO();
	Logger logger = ApplicationConstantsFrameworkTest.getLogger();

	@Test
	public void saveObjectTest() {
		String methodName = "saveObjectTest";
		fileObjectList.add(new FileSystemObject(Paths.get("C:\\Users\\Kranti\\eclipse-workspace\\Pink_Floyd")));
		cacheDao.save(fileObjectList);
		List<FileSystemObject> fileObject2 = (List<FileSystemObject>) cacheDao.getAll(FileSystemObject.class);
		Assertions.assertFalse(fileObject2.isEmpty());
		Assertions.assertTrue(fileObject2.containsAll(fileObjectList));
		for (FileSystemObject file : fileObject2)
			logger.debug(getClass(), methodName, file.toString());
	}

}
