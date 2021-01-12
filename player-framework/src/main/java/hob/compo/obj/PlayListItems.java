package hob.compo.obj;

import java.io.InputStream;

public class PlayListItems {

    private String pathToResource;

    private String resourceName;

    private InputStream resourceInputStream;

    public PlayListItems() {
    }

    public PlayListItems(String pathToResource) {
        this.pathToResource = pathToResource;
    }

    public PlayListItems(String pathToResource, String resourceName) {
        this.pathToResource = pathToResource;
        this.resourceName = resourceName;
    }

    public String getPathToResource() {
        return pathToResource;
    }

    public void setPathToResource(String pathToResource) {
        this.pathToResource = pathToResource;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public InputStream getResourceInputStream() {
        return resourceInputStream;
    }

    public void setResourceInputStream(InputStream resourceInputStream) {
        this.resourceInputStream = resourceInputStream;
    }

    @Override
    public String toString() {
        return "Item : " + resourceName + " @" + pathToResource;
    }

}
