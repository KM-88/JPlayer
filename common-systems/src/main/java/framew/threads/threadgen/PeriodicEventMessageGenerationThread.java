/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framew.threads.threadgen;

import java.util.List;
import java.util.Map;

import app.ExecutionTask;
import core.global.ApplicationConstantsCommon;
import framew.log.Logger;
import framew.threads.api.NonDaemonThread;

/**
 *
 * @author kranti
 */
public class PeriodicEventMessageGenerationThread extends NonDaemonThread{

    private static Logger log;
    
    public PeriodicEventMessageGenerationThread(String name) {
        super(name);
    }
    
    
    @Override
    public void init() {
        log = (Logger) ApplicationConstantsCommon.getLogger();
    }

    @Override
    public void execute() {
        log.log(PeriodicEventMessageGenerationThread.class, "start()", "Started thread Execution", 4);
        while (true) {
            Map<String, Object> executionInfo = ExecutionTaskRegistry.getNextExecutionTask();
            List<ExecutionTask> executionTaskList = (List<ExecutionTask>) executionInfo.get(ExecutionTaskRegistry.EXECUTION_ITEMS);
            Long sleepInterval = (Long) executionInfo.get(ExecutionTaskRegistry.SLEEP_INTERVAL);
            log.log(PeriodicEventMessageGenerationThread.class, "start()", "Waiting :" + sleepInterval, 4);
            try {
                sleep(sleepInterval);
            } catch (InterruptedException iex) {
                iex.getMessage();
            }
            for (ExecutionTask task : executionTaskList) {
                TaskEventExecutorRegistry.registerEventMessage(task.getEvent());
                if(!task.isIsFirstExecutionFinished())
                    task.setIsFirstExecutionFinished(true);
            }
        }
    }
    
}
