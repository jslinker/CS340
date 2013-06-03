package hypeerweb.broadcast;

import junit.framework.TestCase;

/**
 * Tests the Contents specs.
 * @author Craig Jacobson
 *
 */
public class ContentsBlackBoxTests extends TestCase{

	public void testContainsKey(){
		Contents contents = new Contents();
		
		String aKey = "hello world";
		//Test size zero.
		assertFalse(contents.containsKey(aKey));
		
		//Test size one.
		contents.set(aKey, new Object());
		assertTrue(contents.containsKey(aKey));
		
		String anotherKey = "hi";
		assertFalse(contents.containsKey(anotherKey));
		
		//Test size two.
		contents.set(anotherKey, new Object());
		assertTrue(contents.containsKey(aKey));
		assertTrue(contents.containsKey(anotherKey));
	}
	
	public void testGet(){
		Contents contents = new Contents();
		
		//Try to get something that doesn't exist.
		String unassociatedKey = "blah";
		assertTrue(contents.get(unassociatedKey) == null);
		
		//Try to get something that does exist.
		String aKey = "key";
		Object aValue = new Object();
		contents.set(aKey, aValue);
		assertTrue(contents.get(aKey) == aValue);
	}
	
	public void testSet(){
		Contents contents = new Contents();
		
		//Test invalid case.
		try{
			contents.set(null, new Object());
			fail();
		}
		catch(Exception e){
		}
		catch(Error e){
		}
		
		//Test setting null.
		String nullKey = "null";
		contents.set(nullKey, null);
		assertTrue(contents.get(nullKey) == null);
		
		//Test changing a value.
		String aKey = "key";
		Object aFirstValue = new Object();
		Object aSecondValue = new Object();
		contents.set(aKey, aFirstValue);
		assertTrue(contents.get(aKey) == aFirstValue);
		contents.set(aKey, aSecondValue);
		assertTrue(contents.get(aKey) == aSecondValue);
	}
}
