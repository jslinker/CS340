package hypeerweb.database;

import hypeerweb.node.Node;
import hypeerweb.node.NodeInterface;
import hypeerweb.node.NodeProxy;
import hypeerweb.node.SimplifiedNodeDomain;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * Singleton that creates easy access data in the connected database.
 * @author Craig Jacobson
 */
public class HyPeerWebDatabase {

	public static final String DATABASE_DIRECTORY = "db/";
	public static final String DEFAULT_DATABASE_NAME = "HyPeerWeb.db";
	private static final String SQLITE_DRIVER = "org.sqlite.JDBC";
	private static final String SQLITE_DRIVER_URL_PREFIX = "jdbc:sqlite";
	private static final String[] tableNames = {"nodes","neighbors","up_pointers","down_pointers"};
	
	private static HyPeerWebDatabase singleton = null;
	
	private Connection connection = null;
	private String url;
	
	private Connection getConnection(){
		try {
			if(connection == null || connection.isClosed()){
				try {
					connection = DriverManager.getConnection(url);
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.connection;
	}
	
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
		try {
			Class.forName(SQLITE_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url = SQLITE_DRIVER_URL_PREFIX + ":" + DATABASE_DIRECTORY + dbName;
		initHyPeerWebTables();
	}
	
	private static final String CREATE_TABLE_NODES = "CREATE TABLE IF NOT EXISTS " + tableNames[0] + "("+
														"web_id INTEGER UNIQUE NOT NULL,"+
														"height INTEGER NOT NULL,"+
														"fold INTEGER,"+
														"surrogate_fold INTEGER,"+
														"inverse_surrogate_fold INTEGER,"+
														"state INTEGER" +
														"port TEXT,"+
														"machine_addr TEXT," + 
														"local_id TEXT);";
	
	private static final String CREATE_TABLE_NEIGHBORS = "CREATE TABLE IF NOT EXISTS " + tableNames[1] + "("+
															"node INTEGER NOT NULL,"+
															"neighbor INTEGER NOT NULL,"+
															"UNIQUE (node, neighbor));";
	
	private static final String CREATE_TABLE_UP_POINTERS = "CREATE TABLE IF NOT EXISTS " + tableNames[2] + "("+
															"node INTEGER NOT NULL,"+
															"edge_node INTEGER NOT NULL,"+
															"UNIQUE (node, edge_node));";
	
	private static final String CREATE_TABLE_DOWN_POINTERS = "CREATE TABLE IF NOT EXISTS " + tableNames[3] + "("+
																"node INTEGER NOT NULL,"+
																"surrogate_neighbor INTEGER NOT NULL,"+
																"UNIQUE (node, surrogate_neighbor));";
	
	/**
	 * Ensures that the database has the proper tables.
	 * @param con A valid non-null Connection object.
	 * @Precondition <code>con</code> is non-null
	 * @Postcondition The database connection <code>con</code>  contains the necessary tables.
	 * @author Craig Jacobson
	 */
	private void initHyPeerWebTables(){
		
		try{
			Statement createTable = getConnection().createStatement();
			createTable.executeUpdate(CREATE_TABLE_NODES);
			createTable.executeUpdate(CREATE_TABLE_NEIGHBORS);
			createTable.executeUpdate(CREATE_TABLE_UP_POINTERS);
			createTable.executeUpdate(CREATE_TABLE_DOWN_POINTERS);
			createTable.close();
			closeConnection();
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
	
	/**
	 * Creates or loads the default HyPeerWebDatabase.
	 */
	public static void initHyPeerWebDatabase(){
		initHyPeerWebDatabase(null);
	}
	
	/**
	 * Creates or loads a HyPeerWebDatabase. 
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
		singleton = null;
		if(dbName == null || dbName.length() == 0){
			File databaseDirectory = new File(DATABASE_DIRECTORY);
			databaseDirectory.mkdirs();
			
			singleton = new HyPeerWebDatabase(DEFAULT_DATABASE_NAME);
		}
		else {
			singleton = new HyPeerWebDatabase(dbName);
		}
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
		if(singleton == null){
			initHyPeerWebDatabase("");
		}
			
		return singleton;
	}
	
	/**
	 * Creates a SimplifiedNodeDomain representing the node with indicated webId.
	 * The information is retrieved from the database.
	 * @param webId The webId of the node whose information will be retrieved.
	 * @return The SimplifiedNodeDomain corresponding to the webId.
	 * @Precondition There exists a node in the database with the given webId.
	 * @Postcondition result contains the webId, neighbors, upPointers, 
	 * downPointers, fold, surrogateFold, and inverse surrogate fold and state of the 
	 * indicated node.
	 * @author Jason Robertson
	 */
	public SimplifiedNodeDomain getNode(int webId){
		assert webId >= 0;

		SimplifiedNodeDomain result = null;
		
		try {
			
			Statement stmt = getSingleton().getConnection().createStatement();
			String sql = "SELECT * from nodes WHERE web_id = " + webId;
			ResultSet rs = stmt.executeQuery(sql);

			int web_id;
			int height;
			int fold;
			int surrogate_fold;
			int inverse_surrogate_fold;
			int state;

			if( ! rs.next()) {
				return null;
			}
			
			web_id = rs.getInt(1);
			height = rs.getInt(2);
			fold = rs.getInt(3);
			surrogate_fold = rs.getInt(4);
			inverse_surrogate_fold = rs.getInt(5);
			state = rs.getInt(6);
			String port = rs.getString(7);
			String machine_addr = rs.getString(8);
			String local_id = rs.getString(9);

			HashSet<Integer> neighbors = findNearbyFriends(webId, tableNames[1]);
			HashSet<Integer> upPointers = findNearbyFriends(webId, tableNames[2]); 
			HashSet<Integer> downPointers = findNearbyFriends(webId, tableNames[3]); 

			result = new SimplifiedNodeDomain(web_id, height, neighbors,
					upPointers, downPointers, fold, surrogate_fold, inverse_surrogate_fold, state);
			
			if (port != null && machine_addr != null && local_id != null) {
				result.setPort(port);
				result.setMachine_addr(machine_addr);
				result.setLocal_id(local_id);
			}
			
			rs.close();
			stmt.close();
			closeConnection();
		}
		catch(SQLException e) {
			System.out.println(e);
		}

		return result;
	}
	
	/**
	 * This method retrieves neighboring information of a given node.
	 * @param webId The web id of the node the resulting data belongs to.
	 * @param table The name of the table being searched. (neighbors, up_pointers, or down_pointers)
	 * @return A set of web id values of the nearby friends
	 * @author Jason Robertson
	 */
	private HashSet<Integer> findNearbyFriends(int webId, String table) {
		HashSet<Integer> result = new HashSet<Integer>();

		try {
			Statement stmt = getSingleton().getConnection().createStatement();
			String sql = "SELECT * from " + table + " WHERE node = " + webId;
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {
				result.add(rs.getInt(2));
			}
			
			rs.close();
			stmt.close();
			closeConnection();
		}
		catch (SQLException e) {
			System.out.println(e);
		}

		return result;
	}

	/**
	 * Returns a list of all the web ids currently stored in the database.
	 * @author Jason Robertson
	 */
	public List<Integer> getAllWebIds(){

		List<Integer> result = new ArrayList<Integer>();
		
		try {

			Statement stmt = getSingleton().getConnection().createStatement();
			String sql = "SELECT * from nodes;";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {
				result.add(rs.getInt(1));
			}
			rs.close();
			stmt.close();
			closeConnection();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		
		return result;
	}
	
	/**
	 * Stores the node and all of that nodes references.
	 * @param node - The node being stored 
	 * @author Jason Robertson
	 */
	public void storeNode(Node node){

		try {
			
			String sql_delete = "DELETE FROM " + tableNames[0] + " WHERE web_id = " + node.getWebIdValue();
			String sql_upPtrs = "DELETE FROM " + tableNames[1] + " WHERE node = " + node.getWebIdValue();
			String sql_downPtrs = "DELETE FROM " + tableNames[2] + " WHERE node = " + node.getWebIdValue();
			String sql_neighbors = "DELETE FROM " + tableNames[3] + " WHERE node = " + node.getWebIdValue();
			
			Statement stmt = getSingleton().getConnection().createStatement();
			
			stmt.executeUpdate(sql_delete);
			stmt.executeUpdate(sql_upPtrs);
			stmt.executeUpdate(sql_downPtrs);
			stmt.executeUpdate(sql_neighbors);					
			
			// Store General Info
			if (node instanceof NodeProxy) {
				this.writeNodeProxyToDatabase((NodeProxy)node, stmt);
			} else { // it's an instance of just normal node
				this.writeNodeToDatabase(node, stmt);
			}

			// Store Neighbors
			String sql = "";
			for(NodeInterface neighbor : node.getNeighbors().values()){
				sql = String.format("INSERT INTO " + tableNames[1] +
						"(node, neighbor) " +
						"VALUES ('%d', '%d');",
						node.getWebIdValue(), neighbor.getWebIdValue());

				stmt.executeUpdate(sql);
			}

			// Store Up Pointers

			for(NodeInterface up : node.getUpPointers().values()){
				sql = String.format("INSERT INTO " + tableNames[2] +
						"(node, edge_node) " +
						"VALUES ('%d', '%d');",
						node.getWebIdValue(), up.getWebIdValue());

				stmt.executeUpdate(sql);
			}

			// Store Down Pointers

			for(NodeInterface down : node.getDownPointers().values()){
				sql = String.format("INSERT INTO " + tableNames[3] +
						"(node, surrogate_neighbor) " +
						"VALUES ('%d', '%d');",
						node.getWebIdValue(), down.getWebIdValue());

				stmt.executeUpdate(sql);
			}

			stmt.close();
			closeConnection();
		}
		catch (SQLException e){
			e.printStackTrace();
		}

	}
	
	/**
	 * A method for storing a node in the database
	 * @param node, stmt to perform the update
	 * @throws SQLException 
	 * @pre Node must not be null
	 * @post Node will be written to the database
	 */
	public void writeNodeToDatabase(Node node, Statement stmt) throws SQLException{
		if(node == Node.NULL_NODE || stmt == null){
			return;
		}
		String sql = String.format("INSERT INTO " + tableNames[0] +
				"(web_id, height, fold, surrogate_fold, inverse_surrogate_fold, state) " +
				"VALUES ('%d', '%d', '%d', '%d', '%d', '%d');",
				node.getWebIdValue(),
				node.getHeight(),
				node.getFold().getWebIdValue(),
				node.getSurrogateFold().getWebIdValue(),
				node.getInverseSurrogateFold().getWebIdValue(),
				node.getState().STATE_ID
				);
		stmt.executeUpdate(sql);
	}
	
	/**
	 * A method for storing a node proxy in the database
	 * @param nodeProxy
	 * @param stmt
	 * @throws SQLException
	 * @pre Neither parameter may be null
	 * @post Node Proxy will be written to the database
	 */
	public void writeNodeProxyToDatabase(NodeProxy nodeProxy, Statement stmt) throws SQLException{
		if (nodeProxy == NodeProxy.NULL_NODE || stmt == null) {
			return;
		}
		String sql = String.format("INSERT INTO " + tableNames[0] +
				"(web_id, height, fold, surrogate_fold, inverse_surrogate_fold, state, port, machine_addr, local_id) " +
				"VALUES ('%d', '%d', '%d', '%d', '%d', '%d', '%d', '%d', '%d');",
				nodeProxy.getWebIdValue(),
				nodeProxy.getHeight(),
				nodeProxy.getFold().getWebIdValue(),
				nodeProxy.getSurrogateFold().getWebIdValue(),
				nodeProxy.getInverseSurrogateFold().getWebIdValue(),
				nodeProxy.getState().STATE_ID,
				nodeProxy.getGlobalObjectId().getPortNumber().toString(),
				nodeProxy.getGlobalObjectId().getMachineAddr(),
				nodeProxy.getGlobalObjectId().getLocalObjectId().toString()
				);
		stmt.executeUpdate(sql);
	}

	/**
	 * Removes all the data in the tables from the database. (The schema
	 * skeleton is left behind, ready to start adding things to it again)
	 * @author Jason Robertson
	 */
	public static void clear(){

		try {
			Statement stmt = getSingleton().getConnection().createStatement();
			for(String table : tableNames){

				String sql = "delete from " + table + ";";
				stmt.executeUpdate(sql);
			}
			stmt.close();
			getSingleton().closeConnection();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	private void closeConnection(){
		try {
			connection.close();
			connection = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}