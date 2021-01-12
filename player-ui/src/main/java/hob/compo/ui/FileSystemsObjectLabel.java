/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hob.compo.ui;

import javax.swing.JLabel;

import core.obj.FileSystemObject;

/**
 *
 * @author kranti
 */
public class FileSystemsObjectLabel extends JLabel{
    
    private FileSystemObject sourceObject;
        
    public FileSystemsObjectLabel(String labelText,FileSystemObject source){
        super(labelText);
        this.sourceObject  = source;
    }
    
    public FileSystemObject getSourceObject(){
        return sourceObject;
    }
}
