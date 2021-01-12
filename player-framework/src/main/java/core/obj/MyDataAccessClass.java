package core.obj;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class MyDataAccessClass<K, V> {
	private final Ehcache cache;

	public MyDataAccessClass(Ehcache cache) {
		this.cache = cache;
	}

	/* read some data, check cache first, otherwise read from sor */
	public V readSomeData(K key) {
		Element element;
		if ((element = cache.get(key)) != null) {
			return (V) element.getValue();
		}
// note here you should decide whether your cache
// will cache 'nulls' or not
		Object value;
		if ((value = readDataFromDataStore(key)) != null) {
			cache.put(new Element(key, value));
			return (V) value;
		}
		return null;
	}

	/* write some data, write to sor, then update cache */
	private Object readDataFromDataStore(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	public void writeSomeData(K key, V value) {
		writeDataToDataStore(key, value);
		cache.put(new Element(key, value));
	}

	private void writeDataToDataStore(K key, V value) {
		// TODO Auto-generated method stub

	}
}
