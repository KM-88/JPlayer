/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framew.threads.threadgen;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import app.ExecutionTask;
import core.global.ApplicationConstantsCommon;
import framew.log.Logger;

/**
 *
 * @author kranti
 */
public class ExecutionTaskRegistry {

	private static final List<ExecutionTask> executionTaskListRegistry = new LinkedList<>();

	private static final List<ExecutionTask> interimTaskList = new LinkedList<>();

	private static boolean hasAllTaskStartedOnce = false;

	private static boolean hasNewTaskAdded = false;

	private static long time = 0l;

	public static final String SLEEP_INTERVAL = "SLEEP_INTERVAL", EXECUTION_ITEMS = "EXECUTION_ITEMS";

	private static Logger logger = ApplicationConstantsCommon.getLogger();

	public static Map<String, Object> getNextExecutionTask() {

		List<ExecutionTask> firstExecutionTaskList = getAllNeverStartedTasks();

		// Fetch, the next taskList in Queue and wait interval
		Long sleepInterval = listNextTasks();

		// compare them to the non-started tasks
		return getFinalTaskAndWaitTimeInfo(firstExecutionTaskList, sleepInterval);
	}

	/**
	 * Generate the finalMap
	 * 
	 * @param firstExecutionTaskList
	 * @param sleepInterval
	 * @return
	 */
	private static Map<String, Object> getFinalTaskAndWaitTimeInfo(List<ExecutionTask> firstExecutionTaskList,
			long sleepInterval) {
		Map<String, Object> returnData = new HashMap<>();
		List<ExecutionTask> finalExecutionList = new LinkedList<>();
		if (firstExecutionTaskList.isEmpty()) {
			finalExecutionList.addAll(interimTaskList);
			returnData.put(SLEEP_INTERVAL, sleepInterval);
		} else {
			if (!interimTaskList.isEmpty()) {
				ExecutionTask interimTask_Index0 = interimTaskList.get(0);
				ExecutionTask firstTimeTask_Index0 = firstExecutionTaskList.get(0);
				long timeGapBetweenTasks = (interimTask_Index0.getRefreshInterval() - sleepInterval)
						- (firstTimeTask_Index0.getFirstStartTime() - sleepInterval);
				// If interim tasks have lower wait time
				if (timeGapBetweenTasks < 0) {
					finalExecutionList.addAll(interimTaskList);
					returnData.put(SLEEP_INTERVAL, sleepInterval);
				} else // If firstExecutionTask have lower wait time
				if (timeGapBetweenTasks > 0) {
					finalExecutionList.addAll(firstExecutionTaskList);
					returnData.put(SLEEP_INTERVAL, (firstTimeTask_Index0.getFirstStartTime() - sleepInterval));
				} // When both have same wait time
				else {
					finalExecutionList.addAll(firstExecutionTaskList);
					finalExecutionList.addAll(interimTaskList);
					returnData.put(SLEEP_INTERVAL, sleepInterval);
				}
			}
			// When no interim tasks found
			else {
				finalExecutionList.addAll(firstExecutionTaskList);
				returnData.put(SLEEP_INTERVAL, firstExecutionTaskList.get(0).getFirstStartTime());
			}
		}
		time = time + (long) returnData.get(SLEEP_INTERVAL);
		returnData.put(EXECUTION_ITEMS, finalExecutionList);
		return returnData;
	}

	/**
	 * List all tasks that has never been started
	 * 
	 * @return
	 */
	private static List<ExecutionTask> getAllNeverStartedTasks() {
		// Has all tasks been started Once.
		List<ExecutionTask> firstExecutionTaskList = new LinkedList<>();
		if (hasNewTaskAdded && !hasAllTaskStartedOnce) {
			// Find Minimum startTime for all Tasks
			long startTime = -1L;
			for (ExecutionTask task1 : executionTaskListRegistry) {
				if (!task1.isIsFirstExecutionFinished()) {
					if (-1L == startTime || startTime > task1.getFirstStartTime()) {
						startTime = task1.getFirstStartTime();
					}
				}
			}
			// If startTime is modified then we have a probable task or
			// else all tasks have started once
			if (-1L != startTime) {
				for (ExecutionTask task : executionTaskListRegistry) {
					if (!task.isIsFirstExecutionFinished() && startTime == task.getFirstStartTime()) {
						firstExecutionTaskList.add(task);
					}
				}
			} else {
				hasNewTaskAdded = false;
				hasAllTaskStartedOnce = true;
			}

		}
		return firstExecutionTaskList;
	}

	/**
	 * List all tasks (have been previously executed at-least once) that to be
	 * scheduled to be executed next
	 * 
	 * @return
	 */
	private static long listNextTasks() {
		if (time > 100000l) {
			resetTime();
		}
		interimTaskList.clear();
		Long sleepInterval = -1L;
		if (!executionTaskListRegistry.isEmpty()) {
			for (ExecutionTask task : executionTaskListRegistry) {
				if (task.isIsFirstExecutionFinished()) {

					// Add when time!=0 and remainder is Zero
					Long value = (((int) time / task.getRefreshInterval()) + 1) * task.getRefreshInterval() - time;
					if (-1L == sleepInterval) {
						sleepInterval = value;
					} else if (value < sleepInterval) {
						sleepInterval = value;
					}
				}
			}
			for (ExecutionTask task : executionTaskListRegistry) {

				if (sleepInterval == ((1 + ((int) time / task.getRefreshInterval())) * task.getRefreshInterval())
						- time) {
					interimTaskList.add(task);
				}
			}
		}
		if (sleepInterval == -1L || interimTaskList.isEmpty()) {
			return 3600;
		}
		return sleepInterval;
	}

	private static void resetTime() {
		long maxRefreshInterval = getMaxRefreshInterval();
		try {
			int index = (int) (time / maxRefreshInterval);
			time = time - index * maxRefreshInterval;
		} catch (NullPointerException ex) {
			logger.debug(ExecutionTaskRegistry.class, "resetTime()", "Found Zero refreshInterval");
		}
	}

	private static long getMaxRefreshInterval() {
		ExecutionTask taskWithMaxRefreshInterval = null;
		for (ExecutionTask task : executionTaskListRegistry) {
			if (null == taskWithMaxRefreshInterval
					|| task.getRefreshInterval() > taskWithMaxRefreshInterval.getRefreshInterval()) {
				taskWithMaxRefreshInterval = task;
			}
		}
		return (null == taskWithMaxRefreshInterval ? 1000l : taskWithMaxRefreshInterval.getRefreshInterval());
	}

	public static boolean registerExecutionTask(ExecutionTask task) {
		if (executionTaskListRegistry.contains(task)) {
			return false;
		} else {
			executionTaskListRegistry.add(task);
			hasNewTaskAdded = true;
			hasAllTaskStartedOnce = false;
			return true;
		}
	}

	public static boolean deRegisterExecutionTask(ExecutionTask task) {
		if (executionTaskListRegistry.contains(task)) {
			executionTaskListRegistry.remove(task);
			return true;
		} else {
			return false;
		}
	}
}
