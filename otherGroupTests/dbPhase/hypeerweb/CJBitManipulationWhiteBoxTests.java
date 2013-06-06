package dbPhase.hypeerweb;

import junit.framework.TestCase;
import static utilities.BitManipulation.*;

/**
 * Demonstrates white box testing.
 * Homework #12: White Box Testing.
 * @author Craig Jacobson
 *
 */
public class CJBitManipulationWhiteBoxTests extends TestCase{

	/**
	 * Relational Condition Testing:
	 * 1. currentWebId == destinationWebId
	 * 2. currentWebId slightly > destinationWebId
	 * 3. currentWebId slightly < destinationWebId
	 * 
	 * Dataflow Testing (definition, use):
	 * 4. (line 182, line 184)
	 * 5. (182, 187)
	 * 6. (184, 189)
	 * 7. (187, 189)
	 */
	public void testCalculateNextJumpWebId(){
		//#1. currentWebId == destinationWebId
		//#5, and #7
		int currentWebId = 144; 
		int destinationWebId = 144;
		int nextJump = calculateNextJumpWebId(currentWebId, destinationWebId);
		assertEquals(144, nextJump);
		
		//#2. currentWebId slightly > destinationWebId
		//#4, and #6
		currentWebId = 145;
		nextJump = calculateNextJumpWebId(currentWebId, destinationWebId);
		assertEquals(144, nextJump);
		
		//#3. currentWebId slightly < destinationWebId
		currentWebId = 143;
		nextJump = calculateNextJumpWebId(currentWebId, destinationWebId);
		assertEquals(142, nextJump);
	}
	
	/**
	 * Loop Testing:
	 * 1. Skip loop.
	 * 2. Execute loop once.
	 * 3. Execute loop 15 times.
	 * 4. Execute loop 29 times.
	 * 5. Execute loop 30 times.
	 * Note that 30 is the upper bound due to assertion checking.
	 */
	public void testCalculateChildWebId(){
		int height0 = 0;//To test #1
		int height1 = 1;//To test #2
		int height15 = 15;//To test #3
		int height29 = 29;//To test #4
		int height30 = 30;//To test #5
		int webIdZero = 0;
		
		//#1
		int result = calculateChildWebId(webIdZero, height0);
		assertEquals(1, result);
		
		//#2
		result = calculateChildWebId(webIdZero, height1);
		assertEquals(2, result);
		
		//#3
		result = calculateChildWebId(webIdZero, height15);
		assertEquals(32768, result);
		
		//#4
		result = calculateChildWebId(webIdZero, height29);
		assertEquals(0x20000000, result);
		
		//#5
		result = calculateChildWebId(webIdZero, height30);
		assertEquals(0x40000000, result);
	}
	
	/**
	 * There is a natural boundary created by the number of trailing zeros in an integer.
	 * Note that the height doesn't matter, except for the case of webId = 0.
	 * 
	 * Internal Boundary Testing:
	 * 1. No trailing zeros.
	 * 2. One trailing zero.
	 * 3. 5 trailing zeros.
	 * 4. 29 trailing zeros.
	 * 5. 30 trailing zeros.
	 * 
	 * 6. Test webId = 0 for 100% line coverage.
	 */
	public void testCalculateBroadcastWebIds(){
		//#1
		int webId = 1;
		int height = 31;
		int[] results = calculateBroadcastWebIds(webId, height);
		assertEquals(0, results.length);
		
		//#2
		webId = 2;
		results = calculateBroadcastWebIds(webId, height);
		assertEquals(1, results.length);
		assertEquals(3, results[0]);
		
		//#3
		webId = 32;
		results = calculateBroadcastWebIds(webId, height);
		int[] expected3 = {33, 34, 36, 40, 48};
		assertEquals(5, results.length);
		for(int i = 0; i < expected3.length; i++){
			assertEquals(expected3[i], results[i]);
		}
		
		//#4
		webId = 0x20000000;
		results = calculateBroadcastWebIds(webId, height);
		int[] expected4 = {536870913, 536870914, 536870916, 536870920, 
							536870928, 536870944, 536870976, 536871040, 
							536871168, 536871424, 536871936, 536872960, 
							536875008, 536879104, 536887296, 536903680, 
							536936448, 537001984, 537133056, 537395200, 
							537919488, 538968064, 541065216, 545259520, 
							553648128, 570425344, 603979776, 671088640, 
							805306368};
		assertEquals(29, results.length);
		for(int i = 0; i < expected4.length; i++){
			assertEquals(expected4[i], results[i]);
		}
		
		//#5
		webId = 0x40000000;
		results = calculateBroadcastWebIds(webId, height);
		int[] expected5 = {1073741825, 1073741826, 1073741828, 1073741832, 
							1073741840, 1073741856, 1073741888, 1073741952, 
							1073742080, 1073742336, 1073742848, 1073743872, 
							1073745920, 1073750016, 1073758208, 1073774592, 
							1073807360, 1073872896, 1074003968, 1074266112, 
							1074790400, 1075838976, 1077936128, 1082130432, 
							1090519040, 1107296256, 1140850688, 1207959552, 
							1342177280, 1610612736};
		assertEquals(30, results.length);
		for(int i = 0; i < expected5.length; i++){
			assertEquals(expected5[i], results[i]);
		}
		
		//#6
		webId = 0;
		height = 0;
		results = calculateBroadcastWebIds(webId, height);
		assertEquals(0, results.length);
	}
	
	/**
	 * Another example of Internal Boundary Testing is the nodes before, after and equal to the
	 * cape node.
	 * Internal Boundary Testing:
	 * 1. One before (webId = 126).
	 * 2. Equal to (webId = 127).
	 * 3. One after (webId = 128).
	 * 
	 * 4. Test webId = 0 for 100% line coverage.
	 */
	public void testIsCapNodeWebId(){
		//#1
		int webId = 126;
		assertFalse(isCapNodeWebId(webId));
		
		//#2
		webId = 127;
		assertTrue(isCapNodeWebId(webId));
		
		//#3
		webId = 128;
		assertFalse(isCapNodeWebId(webId));
		
		//#4
		webId = 0;
		assertTrue(isCapNodeWebId(webId));
	}
}