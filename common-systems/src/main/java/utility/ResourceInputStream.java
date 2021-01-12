/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author kranti
 */
public class ResourceInputStream extends FileInputStream {

    public ResourceInputStream(String string) throws FileNotFoundException {
        super(string);
    }
    
    public ResourceInputStream(File string) throws FileNotFoundException {
        super(string);
    }

    private boolean isClosed = false;

    public boolean isStreamClosed() {
        return isClosed;
    }

    /**
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        this.isClosed = true;
        super.close();
    }

}
