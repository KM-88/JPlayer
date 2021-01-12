
package datasource;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import core.global.ApplicationConstantsFramework;
import lib.inf.Cache;

public class CacheManager {

	private static Map<String, Cache> cacheMap;

	static {
		cacheMap = new HashMap<String, Cache>();
		cacheMap.put(ApplicationConstantsFramework.INTERNAL_APP_CACHE,
				(Cache) ApplicationConstantsFramework.getByClass(Cache.class));
	}

	public static Cache getNamedCache(String cacheName) {
		return cacheMap.get(cacheName);
	}

	public static List<String> getAllCacheNames() {
		List<String> cacheNames = new LinkedList<String>();
		for (Map.Entry<String, Cache> entry : cacheMap.entrySet())
			cacheNames.add(entry.getKey());
		return cacheNames;
	}

	public static List<Cache> getAllCache() {
		List<Cache> caches = new LinkedList<Cache>();
		for (Map.Entry<String, Cache> entry : cacheMap.entrySet())
			caches.add(entry.getValue());
		return caches;
	}

	public static Cache createNewCache(String cacheName, String className) {
		Cache cache = null;
		try {
			cache = (Cache) Class.forName(className).getConstructor(String.class).newInstance(cacheName);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		cacheMap.put(cacheName, cache);
		return cache;
	}

	public static void refreshCache(String cacheName) {
		Cache cache = cacheMap.get(cacheName);
		cache.refresh();
	}

	public static void refreshAll() {
		for (Map.Entry<String, Cache> entry : cacheMap.entrySet())
			entry.getValue().refresh();
	}

}
