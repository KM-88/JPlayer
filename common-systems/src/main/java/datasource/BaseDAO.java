/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datasource;

import java.util.List;

/**
 *
 * @author kranti
 */
public class BaseDAO {

    private static DataBaseInterface dataLink;

    public static void setDataLink(DataBaseInterface dataLink){
        BaseDAO.dataLink = dataLink;
    }
    
    public static void init(DataBaseInterface dataSource){
    	dataLink = dataSource;
    }

    public static Class<?> getById(Class clazz, Long id) {
        return dataLink.getById(clazz, id);
    }

    public static synchronized List<?> getAll(Class clazz) {
        return dataLink.getAll(clazz);
    }

    public static void save(Object object) {
    	dataLink.save(object);
    }
    
    public static synchronized void save(List<?> objects) {
        dataLink.save(objects);
    }
    
    public static void deleteAll(Class clazz){
        dataLink.deleteAll(clazz);
    }
}
