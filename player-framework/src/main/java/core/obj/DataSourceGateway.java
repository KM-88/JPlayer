package core.obj;

import app.exception.ResourceNotAvailableException;
import core.global.ApplicationConstantsFramework;
import datasource.BaseDAO;
import datasource.CacheDAO;
import datasource.CacheManager;
import datasource.DataBaseInterface;
import datasource.dbport.HibernateDAO;
import datasource.dbport.HibernateUtil;
import datasource.inmemory.DBManager;
import framew.log.Logger;

/**
 * This class just directs all requests to the available data source/storage
 * 
 * @author Kranti
 *
 */

public class DataSourceGateway {

	private static DataBaseInterface dataLink;

	public static void init() {

		try {
			HibernateUtil.init();
			// If Hibernate is Successful, then create Session and assign data
			// source and destination to database
			HibernateDAO.init();
			// If Hibernate successfully setup, then all is well.
			dataLink = new HibernateDAO();
		} catch (ResourceNotAvailableException ex) {
			Logger log = (Logger) ApplicationConstantsFramework.getLogger();
			log.error(DataSourceGateway.class, "init",
					"Cannot configure external database : Configuring internal in-memory DB");
			// Configure Internal in memory HSQLDB Database
			DBManager.init();
			DBManager.startDBServer();
			try {
				HibernateUtil.configureBackupInMemoryDBInit();
			} catch (Exception ex1) {
				log.error(DataSourceGateway.class, "init",
						"Cannot configure In-memory database : We're fucked man....");
			}
		} catch (Exception e) {
			Logger log = (Logger) ApplicationConstantsFramework.getLogger();
			log.error(DataSourceGateway.class, "init",
					"Cannot configure External (Dedicated/In-memory) database : Some fucking Error man...."
							+ " Will be configuring Application Cache Now....");
		}
		if (null == dataLink) {
			CacheManager.createNewCache(ApplicationConstantsFramework.INTERNAL_APP_CACHE, InternalCacheDB.class.getName());
			CacheManager.refreshCache(ApplicationConstantsFramework.INTERNAL_APP_CACHE);
			dataLink = new CacheDAO();
		}
		BaseDAO.setDataLink(dataLink);
	}
}
