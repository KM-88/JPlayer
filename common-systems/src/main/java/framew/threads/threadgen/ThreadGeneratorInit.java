package framew.threads.threadgen;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import app.exception.ResourceNotAvailableException;
import core.global.ApplicationConstants;
import core.global.ApplicationConstantsCommon;
import core.global.ApplicationResource;
import framew.log.Logger;
import framew.threads.api.NonDaemonThread;
import framew.threads.api.ReferenceThread;

public class ThreadGeneratorInit {

	private static Logger log;

	private static Map<String, NonDaemonThread> threadMap;

	private static Map<String, Integer> threadStartupTimestamp;

	public ThreadGeneratorInit() {

	}

	public static void setThreadMap(Map<String, NonDaemonThread> threadmap) {
		threadMap = threadmap;
	}

	public static void setThreadStartupTimestamp(Map<String, Integer> threadStartuptimestamp) {
		threadStartupTimestamp = threadStartuptimestamp;
	}

	public static void initComponents() {
		log = (Logger) ApplicationConstantsCommon.getLogger();
	}

	public static void startThreads() {
            final String methodName = "startThreads()";
		initComponents();
		Object[][] sortArray = sortAsperStartTime();
		int lastTime = 0;
		for (Object[] array : sortArray) {
			ReferenceThread thread = threadMap.get(array[1]);
                        thread.init();
			try {
				Thread.sleep(((Integer) array[0]) - lastTime);
				lastTime = (Integer) array[0];
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			thread.start();
			log.debug(ThreadGeneratorInit.class, methodName, array[1] + " thread started ");
		}
	}

	public static void print() {
		for (Map.Entry<String, NonDaemonThread> entry : threadMap.entrySet())
			System.out.println(entry.getKey() + "=" + entry.getValue());
		for (Map.Entry<String, Integer> entry : threadStartupTimestamp.entrySet())
			System.out.println(entry.getKey() + "=" + entry.getValue());
	}

	private static Object[][] sortAsperStartTime() {
		Object[][] sortArray = new Object[threadStartupTimestamp.size()][2];
		int index = 0;
		for (Map.Entry<String, Integer> entry : threadStartupTimestamp.entrySet()) {
			sortArray[index][0] = entry.getValue();
			sortArray[index][1] = entry.getKey();
			index++;
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
		System.out.println("After Sorting");
		Arrays.sort(sortArray, new Comparator<Object[]>() {

			@Override
			public int compare(Object[] o1, Object[] o2) {
				return ((Integer) o1[0]).compareTo((Integer) o2[0]);
			}

		});
		for (Object[] array : sortArray)
			System.out.println(array[0] + " - " + array[1]);
		return sortArray;
	}

}
