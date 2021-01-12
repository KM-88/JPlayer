/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.obj;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 *
 * @author kranti
 */
public class FileSystemObject implements Cloneable{

    private Long id;

    private String name;

    private Path location;

    protected FileTypeEnum objectType;

    private FileSystemObject parent;

    public FileSystemObject() {

    }

    public FileSystemObject(Path location) {
        this.location = location;
        this.name = fetchName(location);
    }

    private String fetchName(Path path){
        String pathInString = path.toString();
        String fileName=pathInString.substring(pathInString.lastIndexOf(File.separator),pathInString.length());
        return fileName;
    }

    public FileSystemObject(String name, Path location, FileTypeEnum type) {
        this.name = name;
        this.location = location;
        this.objectType = type;
    }

    public FileSystemObject(String name, Path location, FileTypeEnum type,
            FileSystemObject parent) {
        this.parent = parent;
        this.name = name;
        this.location = location;
        this.objectType = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Path getLocationPath() {
        return location;
    }

    public void setLocationPath(Path location) {
        this.location = location;
    }

    public String getLocation() {
        return location.toString();
    }

    public void setLocation(String location) {
        this.location = Paths.get(location);
    }

    public FileTypeEnum getObjectTypeEnum() {
        return objectType;
    }

    public void setObjectTypeEnum(FileTypeEnum objectType) {
        this.objectType = objectType;
    }

    public int getObjectType() {
        return objectType.getOrdinal();
    }

    public void setObjectType(int objectType) {
        this.objectType = (objectType == FileTypeEnum.FILE.getOrdinal() ? FileTypeEnum.FILE : FileTypeEnum.DIRECTORY);
    }

    public FileSystemObject getParent() {
        return parent;
    }

    public void setParent(FileSystemObject parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(null == objectType ? "" : objectType.getValue()).append('\t').append('\t');
        if (null != parent) {
            strBuilder.append("Parent : ").append(parent.name).append('\t');
        }
        strBuilder.append('\t').append(null == name ? "" : name).append('\t').append(null == location ? "" : location.toString());
        return strBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        FileSystemObject fsObject = (FileSystemObject) obj;
        return null != fsObject
                && fsObject.getLocation().equals(this.getLocation())
                && fsObject.getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.location);
        hash = 73 * hash + Objects.hashCode(this.objectType);
        hash = 73 * hash + Objects.hashCode(this.parent);
        return hash;
    }

    @Override
    public FileSystemObject clone() throws CloneNotSupportedException {
        return (FileSystemObject) super.clone(); 
    }
    
    

}
