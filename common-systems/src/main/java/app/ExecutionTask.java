/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import framew.threads.api.ReferenceThread;

/**
 *
 * @author kranti
 */
public abstract class ExecutionTask extends ReferenceThread{
    
    protected long refreshInterval = 3600l;
    
    private long firstStartTime = 0l;
    
    private boolean isFirstExecutionFinished = false;
    
    protected EventMessage event;

    public ExecutionTask(String name) {
        super(name);
    }
    
    public ExecutionTask(String name, EventMessage event) {
        super(name);
        this.event = event;
    }
    
    public long getRefreshInterval(){
        return this.refreshInterval;
    }

    @Override
    public void run() {
        this.setName(this.getThreadName());
    }

    @Override
    public String toString() {
        return this.getThreadName()+"-"+this.getId();
    }

    public long getFirstStartTime() {
        return firstStartTime;
    }

    public void setFirstStartTime(long firstStartTime) {
        this.firstStartTime = firstStartTime;
    }

    public boolean isIsFirstExecutionFinished() {
        return isFirstExecutionFinished;
    }

    public void setIsFirstExecutionFinished(boolean isFirstExecutionFinished) {
        this.isFirstExecutionFinished = isFirstExecutionFinished;
    }

    public EventMessage getEvent() {
        return event;
    }

    public void setEvent(EventMessage event) {
        this.event = event;
    }
    
}
