package hypeerweb;

/**
 * 
 * @author Craig Jacobson
 *
 */
public class HyPeerWebDatabase {

	public static final String DATABASE_DIRECTORY = "databases";
	public static final String DEFAULT_DATABASE_NAME = "hypeerweb.db";
	
	private static HyPeerWebDatabase singleton = null;
	
	private HyPeerWebDatabase(String dbName){
		
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
}
