/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hob.compo.player.instruments.inf;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author kranti
 */
public interface ExtensionsFilter extends FileVisitor<Path> {

    List<String> musicFiltersDefault = new ArrayList<>();

    default List<String> getDefaultFilters() {
        return musicFiltersDefault;
    }

    default boolean matchFilter(Path object) {
        String fileName = object.toString();
        String[] parts = fileName.split("\\.");
        String fileExtension;
        boolean isMatchingExtension = true;
        if (1 < parts.length) {
            fileExtension = parts[parts.length - 1];
            for (String extensionFilter : getDefaultFilters()) {
                if (!(fileExtension.equalsIgnoreCase(extensionFilter))) {
                    isMatchingExtension = false;
                }
                break;
            }
            return isMatchingExtension;
        }
        return false;
    }

    @Override
    default FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    default FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    default FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        throw exc;
    }

    @Override
    default FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

}
