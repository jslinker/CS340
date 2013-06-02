package hypeerweb.broadcast;

import junit.framework.TestCase;

public class ParametersBlackBoxTests extends TestCase{

	public void testContainsKey(){
		Parameters param = new Parameters();
		
		String aKey = "hello world";
		//Test size zero.
		assertFalse(param.containsKey(aKey));
		
		//Test size one.
		param.set(aKey, new Object());
		assertTrue(param.containsKey(aKey));
		
		String anotherKey = "hi";
		assertFalse(param.containsKey(anotherKey));
		
		//Test size two.
		param.set(anotherKey, new Object());
		assertTrue(param.containsKey(aKey));
		assertTrue(param.containsKey(anotherKey));
	}
	
	public void testGet(){
		Parameters param = new Parameters();
		
		//Try to get something that doesn't exist.
		String unassociatedKey = "blah";
		assertTrue(param.get(unassociatedKey) == null);
		
		//Try to get something that does exist.
		String aKey = "key";
		Object aValue = new Object();
		param.set(aKey, aValue);
		assertTrue(param.get(aKey) == aValue);
	}
	
	public void testSet(){
		Parameters param = new Parameters();
		
		//Test invalid case.
		try{
			param.set(null, new Object());
			fail();
		}
		catch(Exception e){
		}
		catch(Error e){
		}
		
		//Test setting null.
		String nullKey = "null";
		param.set(nullKey, null);
		assertTrue(param.get(nullKey) == null);
		
		//Test changing a value.
		String aKey = "key";
		Object aFirstValue = new Object();
		Object aSecondValue = new Object();
		param.set(aKey, aFirstValue);
		assertTrue(param.get(aKey) == aFirstValue);
		param.set(aKey, aSecondValue);
		assertTrue(param.get(aKey) == aSecondValue);
	}
}
