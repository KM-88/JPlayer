/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hob.compo.player.instruments;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import core.obj.FileSystemObject;
import lib.inf.FileSystemScanner;

/**
 *
 * @author kranti
 */
public interface LocalMusicFileSystemScanner extends FileSystemScanner {

    default Map<Path, List<FileSystemObject>> getFilesAndDirectories(Path uri) throws IOException {
        return FileSystemScanner.super.getFilesAndDirectoriesWithExtensions(uri, Integer.MAX_VALUE, new ExtensionsFilterMusicImpl());
    }

}
