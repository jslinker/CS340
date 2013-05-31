package hypeerweb.broadcast;

import java.util.HashMap;

/**
 * An arbitrary collection of named objects
 * @Domain parameters : Map -- a set of key-value pairs
 * such that no two key-value pairs have the same key.
 * @author Jason Robertson, Craig Jacobson
 */
public class Parameters{
	
	private HashMap<String, Object> parameters = new HashMap<String, Object>();

	/**
	 * The default constructor.
	 * @precondition none
	 * @postcondition |parameters| = 0
	 */
	public Parameters(){
	}

	/**
	 * Returns true or false depending on whether or not there exists a key-value pair with the given key.
	 * @param key - The key we are searching for.
	 * @return
	 */
	public boolean containsKey(String key){
		return parameters.containsKey(key); 
	}

	/**
	 * Retrieves an object from the parameters with the given key.
	 * @param key
	 * @return
	 */
	public Object get(String key){
		return parameters.get(key); 
	}
	
	/**
	 * Inserts the key-value pair (key, value) into the parameters.
	 * If there was a key-value pair with the given key, it is removed from the
	 * parameters before inserting the new key-value pair. The value may be null.
	 * @param key
	 * @param value
	 * @precondition key != null
	 * @postcondition (key,value) is a member of parameters
	 */
	public void set(String key, Object value){ 
		assert (key != null);
		
		parameters.put(key, value);
	}
	
}
