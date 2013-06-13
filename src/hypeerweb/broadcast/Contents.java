package hypeerweb.broadcast;

import java.io.Serializable;
import java.util.HashMap;

/**
 * An arbitrary collection of named objects
 * @Domain contents : Map -- a set of key-value pairs such that
 * no two key-value pairs have the same key.
 * @author Jason Robertson, Craig Jacobson
 *
 */
public class Contents implements Serializable{
	
	private HashMap<String, Object> contents = new HashMap<String, Object>();

	/**
	 * The default constructor.
	 * @precondition none
	 * @postcondition |contents| = 0
	 */
	public Contents(){
	}
	
	/**
	 * Returns true or false depending on whether or not
	 * there exists a key-value pair with the given key.
	 * @param key - The key we are searching for.
	 * @return
	 */
	boolean	containsKey(String key){
		return contents.containsKey(key); 
	}
	
	/** 
	 * Retrieves an object from the contents with the given key.
	 * @param key
	 * @return
	 * @precondition none
	 * @postcondition the object may be null
	 */
	public Object get(String key){
		return contents.get(key);
	}
	
	/** 
	 * Inserts the key-value pair (key, value) into the contents.
	 * If there was a key-value pair with the given key, it is removed
	 * from the contents before inserting the new key-value pair.
	 * The value may be null.
	 * @param key - 
	 * @param value - 
	 * @precondition key != null
	 * @postcondition (key,value) is a member of contents
	 */
	public void set(String key, Object value){ 
		assert (key != null);
		
		contents.put(key, value);
	}
	
}
