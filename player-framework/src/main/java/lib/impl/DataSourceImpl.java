package lib.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import core.global.ApplicationConstantsFramework;
import core.obj.FileSystemObject;
import datasource.BaseDAO;
import lib.inf.DataSource;

/**
 * Created by kranti on 3/1/16.
 */
public class DataSourceImpl implements DataSource {
	
	public DataSourceImpl() {
		
	}
    @Override
    public Map<?, ?> getData() {
        Map<Path,List<FileSystemObject>> cacheData = new HashMap<>();
        List<FileSystemObject> fileSystemObjects = (List<FileSystemObject>) BaseDAO.getAll(FileSystemObject.class);
        List<Path> mediaCollectionsPath = (List<Path>) ApplicationConstantsFramework.getByName(ApplicationConstantsFramework.MEDIA_COLLECTION_PATH);
        fileSystemObjects.forEach(fileSystemObject -> {
            mediaCollectionsPath.forEach( mediaPath -> {
                if(fileSystemObject.getLocationPath().startsWith(mediaPath)){
                    if(cacheData.get(mediaPath) == null){
                        cacheData.put(Paths.get(mediaPath.toUri()),new LinkedList<>());
                    }
                    cacheData.get(mediaPath).add(fileSystemObject);
                }
            });
        });
        return cacheData;
    }
}
