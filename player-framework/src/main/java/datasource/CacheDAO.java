/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datasource;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import core.global.ApplicationConstantsFramework;
import core.obj.FileSystemObject;
import lib.inf.Cache;

import java.util.Set;

/**
 *
 * @author kranti
 */
public class CacheDAO implements DataBaseInterface {

	@Override
	public Class<?> getById(Class clazz, Object id) {
		Cache cache = CacheManager.getNamedCache(ApplicationConstantsFramework.INTERNAL_APP_CACHE);
		return (Class<?>) cache.get(id);
	}

	@Override
	public List<?> getAll(Class clazz) {
		List<Object> objList = new LinkedList<>();
		Cache cache = CacheManager.getNamedCache(ApplicationConstantsFramework.INTERNAL_APP_CACHE);
		Map<Object, Object> cacheMap = (Map<Object, Object>) cache.getAllElements();
		Set<Entry<Object, Object>> keyValuePair = cacheMap.entrySet();
		for (Iterator<Entry<Object, Object>> iterator = keyValuePair.iterator(); iterator.hasNext();) {
			Entry<Object, Object> entry = iterator.next();
			((LinkedList<FileSystemObject>) entry.getValue()).forEach(fileSystemObject -> {
				objList.add(fileSystemObject);
			});

		}
		return objList;
	}

	@Override
	public void save(Object object) {
		Cache cache = CacheManager.getNamedCache(ApplicationConstantsFramework.INTERNAL_APP_CACHE);
		FileSystemObject fileSystemObject = (FileSystemObject) object;
		cache.put(fileSystemObject.getLocationPath(), fileSystemObject);
	}

	@Override
	public void save(List<?> objects) {
		Cache cache = CacheManager.getNamedCache(ApplicationConstantsFramework.INTERNAL_APP_CACHE);
		for (Iterator<?> iterator = objects.iterator(); iterator.hasNext();) {
			FileSystemObject fileSystemObject = (FileSystemObject) iterator.next();
			cache.put(fileSystemObject.getLocationPath(), fileSystemObject);
		}
	}

	@Override
	public void deleteAll(Class clazz) {
		Cache cache = CacheManager.getNamedCache(ApplicationConstantsFramework.INTERNAL_APP_CACHE);
		cache.clear();
	}

}
