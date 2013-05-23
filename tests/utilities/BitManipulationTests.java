package utilities;

import static utilities.BitManipulation.*;
import junit.framework.TestCase;

/**
 * @author Craig Jacobson
 *
 */
public class BitManipulationTests extends TestCase{
	
	public void testCalculateChildWebId(){
		assertEquals(1, calculateChildWebId(0, 0));
		assertEquals(2, calculateChildWebId(0, 1));
		assertEquals(4, calculateChildWebId(0, 2));
		assertEquals(7, calculateChildWebId(3, 2));
		assertEquals(11, calculateChildWebId(3, 3));
		assertEquals(11, calculateChildWebId(3, 3));
		assertEquals(11, calculateChildWebId(3, 3));
		assertEquals(33554734, calculateChildWebId(302, 25));
		assertEquals(Integer.MAX_VALUE, calculateChildWebId(0x3FFFFFFF, 30));
		
		try{
			calculateChildWebId((0x7FFFFFFF - 1), 30);
			fail();
		}
		catch(AssertionError e){
			assertTrue(true);
		}
		
		try{
			calculateChildWebId(1, 31);
			fail();
		}
		catch(AssertionError e){
			assertTrue(true);
		}
		
		try{
			calculateChildWebId(0, -1);
			fail();
		}
		catch(AssertionError e){
			assertTrue(true);
		}
		
		try{
			calculateChildWebId(-1, 0);
			fail();
		}
		catch(AssertionError e){
			assertTrue(true);
		}
		
		try{
			calculateChildWebId(Integer.MIN_VALUE, -1);
			fail();
		}
		catch(AssertionError e){
			assertTrue(true);
		}
	}
	
	public void testCalculateHighestOneBitMask(){
		assertEquals(0, calculateHighestOneBitMask(0));
		assertEquals(1, calculateHighestOneBitMask(1));
		assertEquals(2, calculateHighestOneBitMask(2));
		assertEquals(2, calculateHighestOneBitMask(3));
		assertEquals(8, calculateHighestOneBitMask(9));
		assertEquals(8, calculateHighestOneBitMask(10));
		assertEquals(8, calculateHighestOneBitMask(11));
		assertEquals(16, calculateHighestOneBitMask(19));
		assertEquals(1073741824, calculateHighestOneBitMask(Integer.MAX_VALUE));
		
		try{
			calculateHighestOneBitMask(-1);
			fail();
		}
		catch(AssertionError e){
			assertTrue(true);
		}
		
		try{
			calculateHighestOneBitMask(-2);
			fail();
		}
		catch(AssertionError e){
			assertTrue(true);
		}
		
		try{
			calculateHighestOneBitMask(Integer.MIN_VALUE);
			fail();
		}
		catch(AssertionError e){
			assertTrue(true);
		}
	}
	
	/**
	 * Remember that the insertion point is calculating the webId of the insertion point from the 
	 * deletion point.
	 */
	public void testCalculateInsertionPointWebId(){
		assertEquals(0, calculateInsertionPointWebId(1));
		assertEquals(1, calculateInsertionPointWebId(2));
		assertEquals(0, calculateInsertionPointWebId(3));
		assertEquals(1, calculateInsertionPointWebId(4));
		assertEquals(2, calculateInsertionPointWebId(5));
		assertEquals(4, calculateInsertionPointWebId(19));
		assertEquals(63, calculateInsertionPointWebId(126));
		assertEquals(0, calculateInsertionPointWebId(127));
		assertEquals(1, calculateInsertionPointWebId(128));
		assertEquals(1073741823, calculateInsertionPointWebId(Integer.MAX_VALUE-1));
		assertEquals(0, calculateInsertionPointWebId(Integer.MAX_VALUE));
		
		try{
			calculateInsertionPointWebId(-1);
			fail();
		}
		catch(AssertionError e){
			assertTrue(true);
		}
		
		try{
			calculateInsertionPointWebId(Integer.MIN_VALUE);
			fail();
		}
		catch(AssertionError e){
			assertTrue(true);
		}
	}
	
	public void testCalculateSurrogateWebId(){
		assertEquals(0, calculateSurrogateWebId(1));
		assertEquals(0, calculateSurrogateWebId(2));
		assertEquals(1, calculateSurrogateWebId(3));
		assertEquals(0, calculateSurrogateWebId(16));
		assertEquals(55, calculateSurrogateWebId(567));
		assertEquals(1073741823, calculateSurrogateWebId(Integer.MAX_VALUE));
		
		try{
			calculateSurrogateWebId(0);
			fail();
		}
		catch(AssertionError e){
			assertTrue(true);
		}
		
		try{
			calculateSurrogateWebId(-1);
			fail();
		}
		catch(AssertionError e){
			assertTrue(true);
		}
		
		try{
			calculateSurrogateWebId(Integer.MIN_VALUE);
			fail();
		}
		catch(AssertionError e){
			assertTrue(true);
		}
	}
	
	public void testCalculateWebIdHeight(){
		assertEquals(0, calculateWebIdHeight(0));
		assertEquals(1, calculateWebIdHeight(1));
		assertEquals(2, calculateWebIdHeight(2));
		assertEquals(2, calculateWebIdHeight(3));
		assertEquals(9, calculateWebIdHeight(457));
		assertEquals(31, calculateWebIdHeight(Integer.MAX_VALUE));
		
		try{
			calculateWebIdHeight(-1);
			fail();
		}
		catch(AssertionError e){
			assertTrue(true);
		}
		
		try{
			calculateWebIdHeight(Integer.MIN_VALUE);
			fail();
		}
		catch(AssertionError e){
			assertTrue(true);
		}
	}
	
	public void testIsCapNodeWebId(){
		assertTrue(isCapNodeWebId(0));
		assertTrue(isCapNodeWebId(1));
		assertTrue(isCapNodeWebId(3));
		assertTrue(isCapNodeWebId(7));
		assertTrue(isCapNodeWebId(15));
		assertTrue(isCapNodeWebId(31));
		assertTrue(isCapNodeWebId(Integer.MAX_VALUE));
		
		assertFalse(isCapNodeWebId(2));
		assertFalse(isCapNodeWebId(4));
		assertFalse(isCapNodeWebId(6));
		assertFalse(isCapNodeWebId(8));
		assertFalse(isCapNodeWebId(21062));
		
		try{
			isCapNodeWebId(-1);
			fail();
		}
		catch(AssertionError e){
			assertTrue(true);
		}
		
		try{
			isCapNodeWebId(Integer.MIN_VALUE);
			fail();
		}
		catch(AssertionError e){
			assertTrue(true);
		}
	}
	
	public void testCalculateLowestOneBitMask(){
		assertTrue(calculateLowestOneBitMask(0)==0);
		assertTrue(calculateLowestOneBitMask(1)==1);
		assertTrue(calculateLowestOneBitMask(2)==2);
		assertTrue(calculateLowestOneBitMask(3)==1);
		assertTrue(calculateLowestOneBitMask(4)==4);
		assertTrue(calculateLowestOneBitMask(8)==8);
		assertTrue(calculateLowestOneBitMask(4460380)==4);
		assertTrue(calculateLowestOneBitMask(131372)==4);
		assertTrue(calculateLowestOneBitMask(131072)==131072);
	}
	
	public void testCalculateNextJumpWebId(){
		//first test
		int currentWebId = 2464;
		int destinationWebId = 2464;
		assertTrue(calculateNextJumpWebId(currentWebId, destinationWebId) == currentWebId);
		
		//second test, destination is greater than start
		currentWebId = 862;
		destinationWebId = 3189;
		currentWebId = calculateNextJumpWebId(currentWebId, destinationWebId);
		assertTrue(currentWebId == 863);
		
		currentWebId = calculateNextJumpWebId(currentWebId, destinationWebId);
		assertTrue(currentWebId == 861);
		
		currentWebId = calculateNextJumpWebId(currentWebId, destinationWebId);
		assertTrue(currentWebId == 853);
		
		currentWebId = calculateNextJumpWebId(currentWebId, destinationWebId);
		assertTrue(currentWebId == 885);
		
		currentWebId = calculateNextJumpWebId(currentWebId, destinationWebId);
		assertTrue(currentWebId == 629);
		
		currentWebId = calculateNextJumpWebId(currentWebId, destinationWebId);
		assertTrue(currentWebId == 117);
		
		currentWebId = calculateNextJumpWebId(currentWebId, destinationWebId);
		assertTrue(currentWebId == 1141);
		
		currentWebId = calculateNextJumpWebId(currentWebId, destinationWebId);
		assertTrue(currentWebId == 3189);
		
		currentWebId = calculateNextJumpWebId(currentWebId, destinationWebId);
		assertTrue(currentWebId == 3189);
		
		//third test, destination is less than start
		currentWebId = 8;
		destinationWebId = 6;
		
		currentWebId = calculateNextJumpWebId(currentWebId, destinationWebId);
		assertTrue(currentWebId == 0);
		
		currentWebId = calculateNextJumpWebId(currentWebId, destinationWebId);
		assertTrue(currentWebId == 2);
		
		currentWebId = calculateNextJumpWebId(currentWebId, destinationWebId);
		assertTrue(currentWebId == 6);
	}
	
	public void testCalculateBroadcastWebIds(){
		//test invalid inputs
		try{
			calculateBroadcastWebIds(-1, 0);
			fail("Failed to throw AssertionError on invalid arguments.");
		}
		catch(AssertionError e){
		}
		
		try{
			calculateBroadcastWebIds(0, -1);
			fail("Failed to throw AssertionError on invalid arguments.");
		}
		catch(AssertionError e){
		}
		
		try{
			calculateBroadcastWebIds(Integer.MIN_VALUE, 0);
			fail("Failed to throw AssertionError on invalid arguments.");
		}
		catch(AssertionError e){
		}
		
		try{
			calculateBroadcastWebIds(0, Integer.MIN_VALUE);
			fail("Failed to throw AssertionError on invalid arguments.");
		}
		catch(AssertionError e){
		}
		
		//test edge cases
		int[] result = calculateBroadcastWebIds(0,0);
		assertTrue("\nActual array length: " + result.length + "\n" +
					"Expected array length: 0",
					result.length == 0);
		
		result = calculateBroadcastWebIds(0,1);
		assertTrue("\nActual array length: " + result.length + "\n" +
					"Expected array length: 1",
					result.length == 1);
		assertEquals(1, result[0]);
		
		result = calculateBroadcastWebIds(0,3);
		assertTrue("\nActual array length: " + result.length + "\n" +
					"Expected array length: 3",
					result.length == 3);
		assertEquals(1, result[0]);
		assertEquals(2, result[1]);
		assertEquals(4, result[2]);
		
		result = calculateBroadcastWebIds(1,0);//note that the height is only important for webId = 0
		assertTrue("\nActual array length: " + result.length + "\n" +
					"Expected array length: 0",
					result.length == 0);
		
		result = calculateBroadcastWebIds(3,0);
		assertTrue("\nActual array length: " + result.length + "\n" +
					"Expected array length: 0",
					result.length == 0);
		
		result = calculateBroadcastWebIds(2,0);
		assertTrue("\nActual array length: " + result.length + "\n" +
					"Expected array length: 1",
					result.length == 1);
		assertEquals(3, result[0]);
		
		result = calculateBroadcastWebIds(Integer.MAX_VALUE,0);
		assertTrue("\nActual array length: " + result.length + "\n" +
					"Expected array length: 0",
					result.length == 0);
		
		result = calculateBroadcastWebIds(Integer.MAX_VALUE-1,0);
		assertTrue("\nActual array length: " + result.length + "\n" +
					"Expected array length: 1",
					result.length == 1);
		assertEquals(Integer.MAX_VALUE, result[0]);
		
		//test regular value
		result = calculateBroadcastWebIds(688,0);
		assertTrue("\nActual array length: " + result.length + "\n" +
					"Expected array length: 4",
					result.length == 4);
		assertEquals(688+1, result[0]);
		assertEquals(688+2, result[1]);
		assertEquals(688+4, result[2]);
		assertEquals(688+8, result[3]);
	}
}
