package hypeerweb.broadcast;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * An arbitrary collection of named objects
 * @domain contents : Map -- a set of key-value pairs such that
 * no two key-value pairs have the same key.
 * @invariant Every key is unique and is of type String.
 * @author Craig Jacobson
 *
 */
public class Contents implements Serializable{
	private static final long serialVersionUID = -1867942350962484439L;
	
	private HashMap<String, Object> contents = new HashMap<String, Object>();

	/**
	 * The default constructor.
	 * @pre none
	 * @post |contents| = 0
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
	 * @pre none
	 * @post the object may be null
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
	 * @pre key != null
	 * @post (key,value) is a member of contents
	 */
	public void set(String key, Object value){ 
		assert (key != null);
		
		contents.put(key, value);
	}
	
	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		
		result.append("Contents:\n");
		
		if(contents.isEmpty()){
			result.append("Empty");
		}
		else{
			for(Map.Entry<String, Object> entry: contents.entrySet()){
				result.append("Key: "+entry.getKey()+"\n");
				result.append("Value: "+entry.getValue().toString()+"\n");
			}
		}
		
		return result.toString();
	}
	
}
