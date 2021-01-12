/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hob.compo.player.instruments;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import hob.compo.player.instruments.inf.ExtensionsFilter;

/**
 *
 * @author kranti
 */
public class ExtensionsFilterMusicImpl implements ExtensionsFilter {

    @Override
    public List<String> getDefaultFilters() {
        musicFiltersDefault.add("mp3");
        musicFiltersDefault.add("wav");
        return musicFiltersDefault;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (matchFilter(file)) {
            return FileVisitResult.CONTINUE;
        } else {
            return FileVisitResult.TERMINATE;
        }
    }
}
