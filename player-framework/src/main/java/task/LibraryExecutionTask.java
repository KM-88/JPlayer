/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import app.EventMessage;
import app.ExecutionTask;
import core.global.ApplicationConstantsFramework;
import framew.api.infc.LibraryManager;
import framew.log.Logger;

/**
 *
 * @author kranti
 */
public class LibraryExecutionTask extends ExecutionTask{
    
    private static Logger log;
    
    private static LibraryManager libraryManager;
    
    public LibraryExecutionTask(String name){
        super(name);
    }
    
    public LibraryExecutionTask(String name,long refreshInterval){
        super(name);
        this.refreshInterval = refreshInterval;
    }

    public LibraryExecutionTask(String name, EventMessage event, long refreshInterval){
        super(name, event);
        this.refreshInterval = refreshInterval;
    }
    
    @Override
    public synchronized void run() {
        super.run();
        log.info(LibraryExecutionTask.class, "execute", "Activities : Starting");
        libraryManager.reload();
        log.info(LibraryExecutionTask.class, "execute", "Activities : Finished");
    }

    @Override
    public void init() {
        log =  (Logger) ApplicationConstantsFramework.getLogger();
        libraryManager = (LibraryManager) ApplicationConstantsFramework.getByName("libraryManager");
    }
    
}
