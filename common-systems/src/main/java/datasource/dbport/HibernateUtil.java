/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datasource.dbport;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import app.exception.ResourceNotAvailableException;
import core.obj.FileSystemObject;


/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *	
 * @author kranti		
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;
    
    private static Session session;
    
    public static void init() throws ResourceNotAvailableException {
    	int stepCount = 0;
		try {
			sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
			stepCount += 1;
			System.out.println("Fetched Factory");
			session = sessionFactory.openSession();
			stepCount += 1;
			session.createSQLQuery("SELECT 2").executeUpdate();
			stepCount += 1;
		} catch (Throwable ex) {
            String failureCause = getFailureCause(stepCount);
            System.err.println(failureCause + ex);
            ex.printStackTrace();
            throw new ResourceNotAvailableException(
                    "Cannot configure database: [" + failureCause + "]\n" + ex.getMessage());
		}
	}

	public static void configureBackupInMemoryDBInit() throws ResourceNotAvailableException{
        int stepCount = 0;
        try {
            sessionFactory = new Configuration().configure("hibernate-hsqldb.cfg.xml").buildSessionFactory();
            stepCount += 1;
            System.out.println("Fetched Factory");
            session = sessionFactory.openSession();
            stepCount += 1;
            session.createSQLQuery("SELECT 2").executeUpdate();
            stepCount += 1;
        }catch (Throwable ex) {
            String failureCause = getFailureCause(stepCount);
            System.err.println(failureCause + ex);
            ex.printStackTrace();
            session.createSQLQuery("SHUTDOWN").executeUpdate();
            throw new ResourceNotAvailableException(
                    "Cannot configure database: [" + failureCause + "]\n" + ex.getMessage());
        }
	}

    private static String getFailureCause(int stepCount){
        String failureCause = null;
        switch (stepCount) {
            case 0:
                failureCause = "sessionFactory Creation Failed. ";
                break;
            case 1:
                failureCause = "session Creation Failed. ";
                break;
            case 2:
                failureCause = "test query execution Failed. ";
                break;
        }
        return failureCause;
    }

    public static Session getSession() {
        return session;
    }
    public static void main(String... args){
        System.out.println("Fetching session");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
      try{
         tx = session.beginTransaction();
         List employees = session.createQuery("FROM FileSystemObject").list(); 
         for (Iterator iterator = 
                           employees.iterator(); iterator.hasNext();){
            FileSystemObject fileSystemObject = (FileSystemObject) iterator.next(); 
            System.out.print(fileSystemObject);
         }
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
    }
}
