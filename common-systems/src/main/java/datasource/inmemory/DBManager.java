package datasource.inmemory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**	
 * Created by kranti on 3/1/16.
 */
public class DBManager {

    static Connection dbConn = null;
    static final String dbLocation = "./lib/"; // change it to your db location
    static StartInMemoryDBThread inMemoryDBThread;

    public static void init() {
        inMemoryDBThread = new StartInMemoryDBThread("HSQLDB In Memory DB Thread");
        inMemoryDBThread.configureDB();
    }

    private static void performPostSetupConfiguration() {
        Connection conn = getDBConn();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeQuery("DROP TABLE IF EXISTS file_system_objects");
            stmt.executeQuery("CREATE TABLE IF NOT EXISTS file_system_objects (id int, parent_id int, name varchar(255), path varchar(1000), object_type tinyint, localpath varchar(1000), primary key(id))");
            stmt.executeQuery("INSERT INTO file_system_objects(id, name, path, object_type, localpath) values ( 1, 'test', 'some path', 1, 'some local path')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startDBServer() {
        inMemoryDBThread.start();
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        performPostSetupConfiguration();
    }

    public void stopDBServer() {
        if (inMemoryDBThread.isAlive())
            inMemoryDBThread.interrupt();
    }

    public static Connection getDBConn() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            dbConn = DriverManager.getConnection(
                    "jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbConn;
    }
}

