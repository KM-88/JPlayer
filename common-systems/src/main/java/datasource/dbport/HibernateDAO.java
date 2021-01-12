/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datasource.dbport;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import datasource.DataBaseInterface;


/**
 *
 * @author kranti
 */	
public class HibernateDAO implements DataBaseInterface{

    private static Session session;

    public static void init(){
        session = HibernateUtil.getSession();
    }

    public Class<?> getById(Class clazz, Object id) {
        return (Class<?>) session.load(clazz.getClass(), (Long)id);
    }

    public synchronized List<?> getAll(Class clazz) {
        return session.createQuery("from "+clazz.getName()).list();
    }

    public synchronized void save(Object object) {
        Transaction transaction = session.beginTransaction();
        session.save(object);
        transaction.commit();
    }
    
    public synchronized void save(List<?> objects) {
        Transaction transaction = session.beginTransaction();
        for (Iterator<? extends Object> iterator = objects.iterator(); iterator.hasNext();) {
            Object object = iterator.next();
            session.save(object);
        }
        transaction.commit();
        session.flush();
    }
    
    public synchronized void deleteAll(Class clazz){
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from "+clazz.getName()).executeUpdate();
        transaction.commit();
    }
}
