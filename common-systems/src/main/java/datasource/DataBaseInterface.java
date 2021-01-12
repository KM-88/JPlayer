package datasource;

import java.util.List;

public interface DataBaseInterface {
	
    Class<?> getById(Class clazz, Object id);

    List<?> getAll(Class clazz);

    void save(Object object);
    
    void save(List<?> objects);
    
    void deleteAll(Class clazz);
}
