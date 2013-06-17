package hypeerweb.broadcast;

import java.io.Serializable;
import java.util.HashMap;

/**
 * An arbitrary collection of named objects
 * @domain parameters : Map -- a set of key-value pairs
 * such that no two key-value pairs have the same key.
 * @invariant Every key is unique and is of type String.
 * @author Craig Jacobson
 */
public class Parameters implements Serializable{
	private static final long serialVersionUID = -4648781306628891458L;
	
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
	 * @precondition None.
	 * @postcondition True if the key exists; false otherwise.
	 */
	public boolean containsKey(String key){
		return parameters.containsKey(key); 
	}

	/**
	 * Retrieves an object from the parameters with the given key.
	 * @param key
	 * @precondition None.
	 * @postcondition result = object associated with key; otherwise, result = null;
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
