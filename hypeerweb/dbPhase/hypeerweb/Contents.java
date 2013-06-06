package dbPhase.hypeerweb;

import java.util.HashMap;
import java.util.Map;

public class Contents {
	
	private Map<String, Object> map;
	
	public Contents() {
		map = new HashMap<String, Object>();
	}
	
	public boolean containsKey(String key) {
		return map.containsKey(key);
	}
	
	public Object get(String key) {
		return map.get(key);
	}
	
	public void set(String key, Object object) {
		map.put(key, object);
	}

}
