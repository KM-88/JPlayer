package datasource.inmemory;

import core.global.ApplicationConstantsCommon;
import framew.log.Logger;

/**
 * Created by kranti on 3/1/16.
 */
public class StartInMemoryDBThread extends Thread{

    static final String dbLocation = "./lib/"; // change it to your db location
    static org.hsqldb.server.Server hsqlDBServer;
    static Logger logger;

    public StartInMemoryDBThread(String threadName){
        this.setName(threadName);
        logger = (Logger) ApplicationConstantsCommon.getLogger();
    }

    public void configureDB(){
        logger.info(this.getClass(),"configureDB()","Configuring In memory HSQLDB");
		/*
		 * HsqlProperties props = new HsqlProperties();
		 * props.setProperty("server.database.0", "file:" + dbLocation + "mydb;");
		 * props.setProperty("server.dbname.0", "xdb"); hsqlDBServer = new
		 * org.hsqldb.Server(); try { hsqlDBServer.setProperties(props); } catch
		 * (Exception e) { logger.log(this.getClass(),
		 * "configureDB()","Configuring In memory HSQLDB Failed : Failure to set property"
		 * , 0, Logger.ERROR); }
		 */
        logger.info(this.getClass(),"configureDB()","successfully Configured In memory HSQLDB");
    }
        public void run() {
            logger.debug(this.getClass(),"run()","Starting HSQLDB Server");
            //hsqlDBServer.start();
            logger.debug(this.getClass(),"run()","Started HSQLDB Server");
        }
    }
