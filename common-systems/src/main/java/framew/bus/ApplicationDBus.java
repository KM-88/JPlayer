package framew.bus;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import app.exception.EventListnerGroupAlreadyRegisteredException;
import framew.bus.infc.Event;
import framew.bus.infc.Listener;


public class ApplicationDBus {

	private static Map<Event, List<Listener>> eventListenerMap = new HashMap<>();

	public static boolean registerListener(Event event, Listener listner)
			throws EventListnerGroupAlreadyRegisteredException {
		boolean returnValue = false;
		List<Listener> listenerList = eventListenerMap.get(event);
		if (null != listenerList) {
			if (listenerList.isEmpty() || !listenerList.contains(listner)) {
				eventListenerMap.put(event, listenerList);
				returnValue = true;
			}
			else
				throw new EventListnerGroupAlreadyRegisteredException(listner + " already registered for event "+event);
		} else {
			listenerList = new LinkedList<>();
			listenerList.add(listner);
			eventListenerMap.put(event, listenerList);
			return true;
		}
		return returnValue;
	}

	/**
	 * <p>
	 * Receive the event and distribute it to all registered listeners.
	 * </p>
	 * 
	 * @param event
	 */
	public static void receiveEvent(Event event) {
		List<Listener> listenerList = eventListenerMap.get(event);
		listenerList.forEach(listener -> listener.listen(event));
	}

}
