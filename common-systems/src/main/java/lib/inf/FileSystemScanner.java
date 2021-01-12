/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.inf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import core.obj.FileSystemObject;
import core.obj.FileTypeEnum;
import hob.compo.player.instruments.inf.ExtensionsFilter;

/**
 *
 * @author kranti
 */
public interface FileSystemScanner {

    default Map<Path, List<FileSystemObject>> getFilesAndDirectories(Path uri, int depth) throws IOException {
        Map<Path, List<FileSystemObject>> fileSystemObjectsMap = new TreeMap<>();
        Files.exists(uri);
        Files.walk(uri, depth).forEach(p -> putObject(p, fileSystemObjectsMap));
        return fileSystemObjectsMap;
    }

    default Map<Path, List<FileSystemObject>> getFilesAndDirectoriesWithExtensions(Path uri, int depth, ExtensionsFilter filters) throws IOException {
        Map<Path, List<FileSystemObject>> fileSystemObjectsMap = new TreeMap<>();
        Files.exists(uri);
        Files.walk(uri, depth).filter(p -> filters.matchFilter(p)).forEach(p -> putObject(p, fileSystemObjectsMap));
        return fileSystemObjectsMap;
    }

    default void putObject(Path path, Map<Path, List<FileSystemObject>> fileSystemObjectsMap) {
        FileSystemObject fileSystemObject = assignValues(path);
        Path pathForMap = (fileSystemObject.getObjectTypeEnum() == FileTypeEnum.FILE ? fileSystemObject.getParent().getLocationPath() : fileSystemObject.getLocationPath());
        List<FileSystemObject> filesList = fileSystemObjectsMap.get(pathForMap);
        if (null == filesList) {
            filesList = new LinkedList<>();
            fileSystemObjectsMap.put(pathForMap, filesList);
        }
        if (fileSystemObject.getObjectTypeEnum() == FileTypeEnum.FILE) {
            filesList.add(fileSystemObject);
        }
    }

    default FileSystemObject assignValues(Path path){
        FileSystemObject fileSystemObject = new FileSystemObject();
        String pathInString = path.toString();
        String fileName=pathInString.substring(pathInString.lastIndexOf(File.separator)+1,pathInString.length());
        fileSystemObject.setName(fileName);
        fileSystemObject.setLocationPath(path);
        if(Files.isDirectory(path)){
            fileSystemObject.setObjectTypeEnum(FileTypeEnum.DIRECTORY);
        }
        else{
            String parentPath = pathInString.substring(0,pathInString.lastIndexOf(File.separator)+1);
            fileSystemObject.setParent(assignValues(Paths.get(parentPath)));
            fileSystemObject.setObjectTypeEnum(FileTypeEnum.FILE);
        }
        return fileSystemObject;
    }

}
