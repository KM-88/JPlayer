/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framew.threads.threadgen;

import java.util.List;

import app.ExecutionTask;
import core.global.ApplicationConstantsCommon;
import framew.log.Logger;
import framew.threads.api.NonDaemonThread;


/**
 *
 * @author kranti
 */
public class EventMessageExecutionerDaemonThread  extends NonDaemonThread{
    
    private static Logger logger;
    

    public EventMessageExecutionerDaemonThread(String name) {
        super(name);
    }

    @Override
    public void execute() {
        while (true) {
            List<ExecutionTask> tasksList = TaskEventExecutorRegistry.getNextTask();
            if (!tasksList.isEmpty()) {
                for (ExecutionTask task : tasksList) {
                    if (!task.isAlive()) {
                        task.init();
                        task.start();
                        logger.log(EventMessageExecutionerDaemonThread.class, "execute()", "Executed task: " + task.toString());
                    } else {
                        logger.log(EventMessageExecutionerDaemonThread.class, "execute()", "Execution of task: " + task.toString() + " ignored : Already running");
                    }
                }
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    logger.log(EventMessageExecutionerDaemonThread.class, "execute()", "Thread sleep InterruptedException" + ex.getMessage());
                }
            }
        }
    }
    
    @Override
    public void init() {
        logger = (Logger) ApplicationConstantsCommon.getLogger();
    }
    
}
