/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.inf;

import java.util.List;
import java.util.Map;

/**
 *
 * @author kranti
 */
public interface Cache<T> {

	Object get(Object key);

	void put(Object key, Object value);

	void put(Object key, List<?> value);

	boolean refresh();

	void setCacheName(String cacheName);

	String getCacheName();

	/**
	 * This method return a deep copy of the cache object.
	 * <p>
	 * Since cache is a generic object here, no generic implementation seems
	 * feasible here. So, every child is left to create their own implementation.
	 * </p>
	 *
	 * @return
	 */
	Map<?, ?> getAllElements();

	/**
	 * This method removes all data from cache object.
	 * <p>
	 * The implementation for this method is left to be specified by each individual
	 * child class. Although a default implementation can be provided, it'd be very
	 * resource consuming.
	 * </p>
	 */
	void clear();

}
