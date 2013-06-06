package dbPhase.hypeerweb;

import java.util.HashMap;
import java.util.Map;

public class Parameters {
	private Map<String, Object> map;
	
	public Parameters() {
		map = new HashMap<String, Object>();
	}
	
	public boolean containsKey(String key) {
		return map.containsKey(key);
	}
	
	public Object get(String key) {
		return map.get(key);
	}
	
	public void set(String key, Object value) {
		map.put(key, value);
	}
}
