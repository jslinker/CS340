package hypeerweb;

import java.sql.*;

/**
 * Singleton that creates easy access data in the connected database.
 * 
 * @author Craig Jacobson
 *
 */
public class HyPeerWebDatabase {

	public static final String DATABASE_DIRECTORY = "databases";
	public static final String DEFAULT_DATABASE_NAME = "hypeerweb.db";
	private static final String SQLITE_DRIVER = "org.sqlite.JDBC";
	private static final String SQLITE_DRIVER_URL_PREFIX = "jdbc:sqlite";
	
	private static HyPeerWebDatabase singleton = null;
	
	private Connection connection = null;
	
	/**
	 * Creates a HyPeerWebDatabase that connects to a sqlite database 
	 * to load or save information.
	 * @param dbName Valid database name.
	 * @Precondition dbName is valid and located in the DATABASE_DIRECTORY
	 * @Postcondition The database is connected and <code>connection</code> is not null.
	 * Additionally, dbName is created if it did not exist 
	 * and any necessary tables are created.
	 * @author Craig Jacobson
	 */
	private HyPeerWebDatabase(String dbName){
		try{
			Class.forName(SQLITE_DRIVER);
			String url = SQLITE_DRIVER_URL_PREFIX + ":" + dbName;
			connection = DriverManager.getConnection(url);
			connection.setAutoCommit(true);
			
			initHyPeerWebTables(connection);
		}
		catch(ClassNotFoundException e){
			System.out.println(e);
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
	
	/**
	 * Ensures that the database has the proper tables.
	 * @param con A valid non-null Connection object.
	 * @Precondition <code>con</code> is non-null
	 * @Postcondition The database <code>con</code> is 
	 * connected to contains the necessary tables.
	 * @author Craig Jacobson
	 */
	private void initHyPeerWebTables(Connection con){
		assert con != null;
	}
	
	/**
	 * Creates and loads a HyPeerWebDatabase. 
	 * Should be one of the first things called when creating a HyPeerWeb.
	 * @param dbName The name of the database. Must be valid, null, or empty.
	 * @Precondition dbName = null OR |dbName| = 0 OR dbName is valid
	 * @Postcondition 
	 * IF dbName == null OR |dbName| = 0
	 * new HyPeerWebDatabase(DEFAULT_DATABASE_NAME)
	 * ELSE
	 * new HyPeerWebDatabase(dbName)
	 * @author Craig Jacobson
	 */
	public static void initHyPeerWebDatabase(String dbName){
		if(dbName == null || dbName.length() == 0)
			singleton = new HyPeerWebDatabase(DEFAULT_DATABASE_NAME);
		else
			singleton = new HyPeerWebDatabase(dbName);
	}
	
	/**
	 * Gets the single HyPeerWebDatabase.
	 * @return The HyPeerWebDatabase singleton.
	 * @Precondition <code>initHyPeerWebDatabase()</code> must have been called previously to ensure
	 * that result != null
	 * @Postcondition result = singleton
	 * @author Craig Jacobson
	 */
	public static HyPeerWebDatabase getSingleton(){
		assert singleton != null;
		return singleton;
	}
	
	/**
	 * Creates a SimplifiedNodeDomain representing the node with indicated webId.
	 * The information is retrieved from the database.
	 * @param webId The webId of the node whose information will be retrieved.
	 * @return The SimplifiedNodeDomain corresponding to the webId.
	 * @Precondition There exists a node in the database with the given webId.
	 * @Postcondition result contains the webId, neighbors, upPointers, 
	 * downPointers, fold, surrogateFold, and inverse surrogate fold of the 
	 * indicated node.
	 * @author Craig Jacobson
	 */
	public SimplifiedNodeDomain getNode(int webId){
		assert webId >= 0;
		return null;
	}
}
