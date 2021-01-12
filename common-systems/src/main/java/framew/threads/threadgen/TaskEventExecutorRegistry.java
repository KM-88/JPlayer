/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framew.threads.threadgen;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;

import app.EventMessage;
import app.ExecutionTask;
import core.global.ApplicationConstantsCommon;
import framew.log.Logger;

/**
 *
 * @author kranti
 */
public class TaskEventExecutorRegistry {

	private static final Map<EventMessage, Class<ExecutionTask>> eventTaskMap = new HashMap<>();

	private static final Map<EventMessage, Integer> instanceCount = new HashMap<>();

	private static final Queue<EventMessage> eventMessageQueue = new ConcurrentLinkedQueue<>();

	private static Logger logger;

	static {
		logger = (Logger) ApplicationConstantsCommon.getLogger();
	}

	/**
	 * Fetch next task in Queue as per any message event registered.
	 *
	 * @return
	 */
	public static List<ExecutionTask> getNextTask() {
		List<ExecutionTask> executionList = new LinkedList<>();
		while (!eventMessageQueue.isEmpty()) {
			EventMessage event = eventMessageQueue.remove();
			Class<ExecutionTask> taskClass = eventTaskMap.get(event);
			ExecutionTask task = null;
			try {
				int instanceIndex = instanceCount.get(event);
				task = taskClass.getDeclaredConstructor(String.class)
						.newInstance(taskClass.getSimpleName() + "-" + instanceIndex);
				executionList.add(task);
				instanceCount.put(event, new Integer(instanceIndex + 1));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException ex) {
				java.util.logging.Logger.getLogger(TaskEventExecutorRegistry.class.getName()).log(Level.SEVERE, null,
						ex);
				logger.error(TaskEventExecutorRegistry.class, "getNextTask()",
						"Attempt to create new instance for Task: " + taskClass.getSimpleName() + " has failed");
			}
		}
		return executionList;
	}

	/**
	 * Register Event-Task pair
	 *
	 * @param event
	 * @param overWrite : change an existing event-task pair
	 * @return true: new event-task pair false: already registered event-task pair
	 */
	public static boolean registerTaskEventPair(EventMessage event, Class<ExecutionTask> taskClass, boolean overWrite) {
		if (null != eventTaskMap.get(event) && !overWrite) {
			return false;
		} else {
			eventTaskMap.put(event, taskClass);
			instanceCount.put(event, 0);
			logger.debug(TaskEventExecutorRegistry.class, "registerTaskEventPair()", "Attempt to register Task: "
					+ taskClass.getSimpleName() + " and Event: " + event + " is successful");
			return true;
		}
	}

	/**
	 * De-register an event-task pair
	 *
	 * @param event
	 * @return true: event-task pair exists false: else
	 */
	public static boolean deRegisterTaskEventPair(EventMessage event) {
		if (null != event && null != eventTaskMap.get(event)) {
			eventTaskMap.remove(event);
			logger.debug(TaskEventExecutorRegistry.class, "deRegisterEventMessage()",
					"Attempt to de-register Event: " + event + "is successful");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method to register a message to start corresponding event
	 *
	 * @param event
	 * @return
	 */
	public static boolean registerEventMessage(EventMessage event) {
		eventMessageQueue.add(event);
		logger.debug(TaskEventExecutorRegistry.class, "registerEventMessage()",
				"Attempt to register Event: " + event + " is successful");
		return true;
	}
}
