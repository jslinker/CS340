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
}
