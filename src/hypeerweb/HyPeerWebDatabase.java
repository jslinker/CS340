package hypeerweb;

import java.io.File;
import java.sql.*;
import java.util.HashSet;

/**
 * Singleton that creates easy access data in the connected database.
 * 
 * @author Craig Jacobson
 *
 */
public class HyPeerWebDatabase {

	public static final String DATABASE_DIRECTORY = "db/";
	public static final String DEFAULT_DATABASE_NAME = "HyPeerWeb.db";
	private static final String SQLITE_DRIVER = "org.sqlite.JDBC";
	private static final String SQLITE_DRIVER_URL_PREFIX = "jdbc:sqlite";
	
	private static HyPeerWebDatabase singleton = null;
	
	private Connection connection = null;
	
	/**
	 * Creates a HyPeerWebDatabase that connects to a sqlite database 
	 * to load or save information.
	 * @param dbName Valid database name.
	 * @pre dbName is valid and located in the DATABASE_DIRECTORY
	 * @post The database is connected and <code>connection</code> is not null.
	 * Additionally, dbName is created if it did not exist 
	 * and any necessary tables are created.
	 * @author Craig Jacobson
	 */
	private HyPeerWebDatabase(String dbName){
		try{
			Class.forName(SQLITE_DRIVER);
			String url = SQLITE_DRIVER_URL_PREFIX + ":" + DATABASE_DIRECTORY + dbName;
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
	
	private static final String CREATE_TABLE_NODES = "CREATE TABLE IF NOT EXISTS nodes("+
														"web_id INTEGER UNIQUE NOT NULL,"+
														"height INTEGER NOT NULL,"+
														"fold INTEGER,"+
														"surrogate_fold INTEGER,"+
														"inverse_surrogate_fold INTEGER);";
	private static final String CREATE_TABLE_NEIGHBORS = "CREATE TABLE IF NOT EXISTS neighbors("+
															"node INTEGER NOT NULL,"+
															"neighbor INTEGER NOT NULL,"+
															"UNIQUE (node, neighbor));";
	private static final String CREATE_TABLE_UP_POINTERS = "CREATE TABLE IF NOT EXISTS up_pointers("+
															"node INTEGER NOT NULL,"+
															"edge_node INTEGER NOT NULL,"+
															"UNIQUE (node, edge_node));";
	
	private static final String CREATE_TABLE_DOWN_POINTERS = "CREATE TABLE IF NOT EXISTS down_pointers("+
																"node INTEGER NOT NULL,"+
																"surrogate_neighbor INTEGER NOT NULL,"+
																"UNIQUE (node, surrogate_neighbor));";
	
	/**
	 * Ensures that the database has the proper tables.
	 * @param con A valid non-null Connection object.
	 * @pre <code>con</code> is non-null
	 * @post The database connection <code>con</code>  contains the necessary tables.
	 * @author Craig Jacobson
	 */
	private void initHyPeerWebTables(Connection con){
		assert con != null;
		
		try{
			Statement createTable = con.createStatement();
			createTable.executeUpdate(CREATE_TABLE_NODES);
			createTable.executeUpdate(CREATE_TABLE_NEIGHBORS);
			createTable.executeUpdate(CREATE_TABLE_UP_POINTERS);
			createTable.executeUpdate(CREATE_TABLE_DOWN_POINTERS);
			createTable.close();
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
	
	/**
	 * Creates and loads a HyPeerWebDatabase. 
	 * Should be one of the first things called when creating a HyPeerWeb.
	 * @param dbName The name of the database. Must be valid, null, or empty.
	 * @pre dbName = null OR |dbName| = 0 OR dbName is valid
	 * @post 
	 * IF dbName == null OR |dbName| = 0
	 * new HyPeerWebDatabase(DEFAULT_DATABASE_NAME)
	 * ELSE
	 * new HyPeerWebDatabase(dbName)
	 * @author Craig Jacobson
	 */
	public static void initHyPeerWebDatabase(String dbName){
		if(dbName == null || dbName.length() == 0){
			File databaseDirectory = new File(DATABASE_DIRECTORY);
			databaseDirectory.mkdirs();
			
			singleton = new HyPeerWebDatabase(DEFAULT_DATABASE_NAME);
		}
		else
			singleton = new HyPeerWebDatabase(dbName);
	}
	
	/**
	 * Gets the single HyPeerWebDatabase.
	 * @return The HyPeerWebDatabase singleton.
	 * @pre <code>initHyPeerWebDatabase()</code> must have been called previously to ensure
	 * that result != null
	 * @post result = singleton
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
	 * @pre There exists a node in the database with the given webId.
	 * @post result contains the webId, neighbors, upPointers, 
	 * downPointers, fold, surrogateFold, and inverse surrogate fold of the 
	 * indicated node.
	 * @author Craig Jacobson
	 */
	public SimplifiedNodeDomain getNode(int webId){
		assert webId >= 0;
		
		SimplifiedNodeDomain result = null;
		
		int height = -1;
		int fold = -1;
		int surrogateFold = -1;
		int inverseSurrogateFold = -1;
		HashSet<Integer> neighbors = new HashSet<Integer>();
		HashSet<Integer> upPointers = new HashSet<Integer>();
		HashSet<Integer> downPointers = new HashSet<Integer>();
		
		try{
			String sqlGetNodeInfo = "SELECT height, fold, surrogate_fold, inverse_surrogate_fold "+
									"FROM nodes "+
									"WHERE web_id = " + webId + ";";
			String sqlGetNeighbors = "SELECT neighbor FROM neighbors "+
										"WHERE node = " + webId + ";";
			String sqlGetUpPointers = "SELECT edge_node FROM up_pointers "+
										"WHERE node = " + webId + ";";
			String sqlGetDownPointers = "SELECT surrogate_neighbor FROM down_pointers "+
										"WHERE node = " + webId + ";";
			
			Statement statement = connection.createStatement();
			ResultSet queryResults = statement.executeQuery(sqlGetNodeInfo);
			if(queryResults.next()){
				height = queryResults.getInt(1);
				fold = queryResults.getInt(2);
				surrogateFold = queryResults.getInt(3);
				inverseSurrogateFold = queryResults.getInt(4);
			}
			queryResults.close();
			
			queryResults = statement.executeQuery(sqlGetNeighbors);
			while(queryResults.next()){
				neighbors.add(new Integer(queryResults.getInt(1)));
			}
			queryResults.close();
			
			queryResults = statement.executeQuery(sqlGetUpPointers);
			while(queryResults.next()){
				upPointers.add(new Integer(queryResults.getInt(1)));
			}
			queryResults.close();
			
			queryResults = statement.executeQuery(sqlGetDownPointers);
			while(queryResults.next()){
				downPointers.add(new Integer(queryResults.getInt(1)));
			}
			queryResults.close();
			
			result = new SimplifiedNodeDomain(webId, height, neighbors, upPointers, downPointers,
												fold, surrogateFold, inverseSurrogateFold);
		}
		catch(SQLException e){
			System.out.println(e);
		}
		
		return result;
	}
	
	
	/**
	 * Stores/updates the node and all of that nodes references.
	 * @param node The node to be added to the database.
	 * @pre node &ne; null AND node &ne; NULL_NODE AND all values in node are valid
	 * @post The database contains the representation of the node.
	 * @author Craig Jacobson
	 */
	public void storeNode(Node node){
		assert (node != null && node != Node.NULL_NODE);
		
		SimplifiedNodeDomain simpleNode = node.constructSimplifiedNodeDomain();
		
		String sqlDeleteNode = "DELETE FROM nodes WHERE web_id = " + simpleNode.getWebId();
		String sqlDeleteNeighbors = "DELETE FROM neighbors WHERE node = " + simpleNode.getWebId();
		String sqlDeleteUpPointers = "DELETE FROM up_pointers WHERE node = " + simpleNode.getWebId();
		String sqlDeleteDownPointers = "DELETE FROM down_pointers WHERE node = " + simpleNode.getWebId();
		String sqlAddNode = "INSERT INTO nodes (web_id, height, fold, surrogate_fold, inverse_surrogate_fold) "+
							"VALUES (?,?,?,?,?);";
		String sqlAddNeighbors = "INSERT INTO neighbors (node, neighbor) VALUES (?,?);";
		String sqlAddUpPointers = "INSERT INTO up_pointers (node, edge_node) VALUES (?,?);";
		String sqlAddDownPointers = "INSERT INTO down_pointers (node, surrogate_neighbor) VALUES (?,?);";
		
		try{
			Statement deleteStatement = connection.createStatement();
			
			PreparedStatement nodePreparedStatement = connection.prepareStatement(sqlAddNode);
			nodePreparedStatement.setInt(1, simpleNode.getWebId());
			nodePreparedStatement.setInt(2, simpleNode.getHeight());
			nodePreparedStatement.setInt(3, simpleNode.getFold());
			nodePreparedStatement.setInt(4, simpleNode.getSurrogateFold());
			nodePreparedStatement.setInt(5, simpleNode.getInverseSurrogateFold());
			nodePreparedStatement.addBatch();
			
			PreparedStatement neighborsPreparedStatement = connection.prepareStatement(sqlAddNeighbors);
			HashSet<Integer> neighbors = simpleNode.getNeighbors();
			for(Integer n: neighbors){
				neighborsPreparedStatement.setInt(1, simpleNode.getWebId());
				neighborsPreparedStatement.setInt(2, n);
				neighborsPreparedStatement.addBatch();
			}
			
			PreparedStatement upPointersPreparedStatement = connection.prepareStatement(sqlAddUpPointers);
			HashSet<Integer> upPointers = simpleNode.getUpPointers();
			for(Integer n: upPointers){
				upPointersPreparedStatement.setInt(1, simpleNode.getWebId());
				upPointersPreparedStatement.setInt(2, n);
				upPointersPreparedStatement.addBatch();
			}
			
			PreparedStatement downPointersPreparedStatement = connection.prepareStatement(sqlAddDownPointers);
			HashSet<Integer> downPointers = simpleNode.getDownPointers();
			for(Integer n: downPointers){
				downPointersPreparedStatement.setInt(1, simpleNode.getWebId());
				downPointersPreparedStatement.setInt(2, n);
				downPointersPreparedStatement.addBatch();
			}
			
			connection.setAutoCommit(false);
			deleteStatement.executeUpdate(sqlDeleteNode);
			deleteStatement.executeUpdate(sqlDeleteNeighbors);
			deleteStatement.executeUpdate(sqlDeleteUpPointers);
			deleteStatement.executeUpdate(sqlDeleteDownPointers);
			connection.commit();
			nodePreparedStatement.executeBatch();
			neighborsPreparedStatement.executeBatch();
			upPointersPreparedStatement.executeBatch();
			downPointersPreparedStatement.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
}
