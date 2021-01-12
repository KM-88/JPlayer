/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framew.threads.threadgen;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

import app.ExecutionTask;
import core.global.ApplicationConstantsCommon;
import framew.log.Logger;
import framew.threads.api.ReferenceThread;

/**
 *
 * @author kranti
 */
public class TaskExecutionEventThread extends ReferenceThread {

    private static final List<ExecutionTask> executionTaskList = new LinkedList<>();

    private static final Queue<ExecutionTask> taskQueue = new LinkedList();

    private static ExecutorService executorService;

    private static Logger log;

    private static long time;

    public TaskExecutionEventThread(String name) {
        super(name);
    }

    @Override
    public synchronized void run() {
        time = 0L;
        log.info(TaskExecutionEventThread.class, "start()", "Started thread Execution");
        while (true) {
            while (!taskQueue.isEmpty()) {
                ExecutionTask nextTask = taskQueue.remove();
                log.debug(TaskExecutionEventThread.class, "start()", "Starting Execution :" + nextTask);
                executorService.execute(nextTask);
            }
            long sleepInterval = pushNextTasks();
            log.debug(TaskExecutionEventThread.class, "start()", "Waiting :" + sleepInterval);
            try {
                sleep(sleepInterval);
            } catch (InterruptedException iex) {
                iex.getMessage();
            }
            time += sleepInterval;
        }
    }

    private long pushNextTasks() {
        if (time > 100000l) {
            resetTime();
        }
        Long sleepInterval = -1L;
        if (!executionTaskList.isEmpty()) {
            for (ExecutionTask task : executionTaskList) {
                //Add when time!=0 and remainder is Zero
                
                Long value = (((int)time/task.getRefreshInterval())+1)*task.getRefreshInterval()-time;
                //(time < task.getRefreshInterval() ? task.getRefreshInterval()-time : time % task.getRefreshInterval());
                if (-1L == sleepInterval) {
                    sleepInterval = value;
                } else if (value < sleepInterval) {
                    sleepInterval = value;
                }
            }
            for (ExecutionTask task : executionTaskList) {
                
                if (sleepInterval == ((1+((int)time/task.getRefreshInterval()))*task.getRefreshInterval())-time) {
                    taskQueue.add(task);
                }
            }
        }
        if (sleepInterval == -1L || taskQueue.isEmpty() ) {
            return 3600*1000l;
        }
        return sleepInterval;
    }

    private void resetTime() {
        long maxRefreshInterval = getMaxRefreshInterval();
        int index = (int) (time / maxRefreshInterval);
        time = time - index * maxRefreshInterval;
    }

    private long getMaxRefreshInterval() {
        ExecutionTask taskWithMaxRefreshInterval = null;
        for (ExecutionTask task : executionTaskList) {
            if (null == taskWithMaxRefreshInterval || task.getRefreshInterval() > taskWithMaxRefreshInterval.getRefreshInterval()) {
                taskWithMaxRefreshInterval = task;
            }
        }
        return (null == taskWithMaxRefreshInterval ? 0l : taskWithMaxRefreshInterval.getRefreshInterval());
    }

    /**
     * <p>
     * Add a new task.</p>
     *
     * @param task
     * @return
     */
    public boolean addExecutionTask(ExecutionTask task) {
        if (executionTaskList.contains(task)) {
            return false;
        }
        task.init();
        return executionTaskList.add(task);
    }

    /**
     * <p>
     * Update an task.</p>
     *
     * @param task
     * @return
     */
    public boolean updateExecutionTask(ExecutionTask task) {
        if (executionTaskList.contains(task)) {
            executionTaskList.remove(task);
            return executionTaskList.add(task);
        } else {
            return false;
        }
    }

    @Override
    public void init() {
        log = (Logger) ApplicationConstantsCommon.getByName("logger");
        executorService = (ExecutorService) ApplicationConstantsCommon.getByName("executorService");
        List<ExecutionTask> taskList = (List<ExecutionTask>) ApplicationConstantsCommon.getByName("taskInventory");
        for (ExecutionTask task : taskList) {
            addExecutionTask(task);
        }
    }

}
