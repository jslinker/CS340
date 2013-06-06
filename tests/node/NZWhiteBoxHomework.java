package node;

import static org.junit.Assert.*;
import hypeerweb.HyPeerWeb;

import org.junit.*;

import utilities.BitManipulation;

/**
 * Test class that runs tests on methods in Node, Connections, and BitManipulation classes to exhibit
 * different techniques of white box testing.  Each test case is used to demonstrate loop testing,
 * relational testing, internal boundary value testing, or dataflow testing.  The tests each cover 
 * 100% of code in each method they run on.  Note - the coverage report for calculateChildWebId
 * reports that some branches were missed.  This is because the coverage tools I have don't handle 
 * assert statements very well.  All reachable code is covered.
 * 
 * @author Nathan Zabriskie
 *
 */
public class NZWhiteBoxHomework {
	private static Node baseNode;
	private Node testNode;
	private HyPeerWeb web;
	
	@BeforeClass
	public static void setupClass(){
		
	}
	
	@Before
	public void setup(){
		web = HyPeerWeb.getSingleton();
		web.clear();

		baseNode = new Node(0);
		web.addToHyPeerWeb(baseNode, Node.NULL_NODE);
		for(int i = 0; i < 15; i++){
			web.addToHyPeerWeb(new Node(i), baseNode);
		}
	}
	
	/**
	 * Loop testing.  Completely covers the code in getSmallestChildlessNeighbor() in Connections. 
	 * @custom.pre NONE
	 * @custom.post Tests have been executed that run the loop for 0 iterations, 1 iteration, 2 iterations, and many iterations.
	 */
	@Test
	public void testGetSmallestChildlessNeighbor(){
		Node neighbor = baseNode.getConnections().getSmallestChildlessNeighbor();	//loop runs 0 times
		assertEquals(neighbor, Node.NULL_NODE);
		
		neighbor = web.getNode(1).getConnections().getSmallestChildlessNeighbor();  //loop runs 1 time
		assertEquals(neighbor, Node.NULL_NODE);
		
		neighbor = web.getNode(2).getConnections().getSmallestChildlessNeighbor();  //loop runs 2 times
		assertEquals(neighbor, Node.NULL_NODE);
		
		web.addToHyPeerWeb(new Node(16), baseNode);
		neighbor = web.getNode(15).getConnections().getSmallestChildlessNeighbor(); //loop runs many times
		assertEquals(neighbor, Node.NULL_NODE);
		
		baseNode.getConnections().setLowerNeighbors(null);
		neighbor = baseNode.getConnections().getSmallestChildlessNeighbor(); 		//loop runs many times
		assertEquals(neighbor, Node.NULL_NODE);
	}
	
	/**
	 * Relational testing.  Tests the compareTo method I wrote for the node class so we can 
	 * keep a sorted set for quicker access to highest, lowest neighbors.
	 * @custom.pre NONE
	 * @custom.post Tests have been run that cover all possible results of the relational expressions in compareTo.    
	 */
	@Test
	public void testCompareTo(){
		Node node1 = new Node(10);
		Node node2 = new Node(10);
		assertTrue(node1.compareTo(node2) == 0); 		//test equal ids
		assertTrue(node2.compareTo(node1) == 0);
		
		node2 = new Node(9);
		assertTrue(node1.compareTo(node2) == 1);		//test compare to slightly lesser value
		assertTrue(node2.compareTo(node1) == -1);		//ensure the relation holds the other way
		
		node2 = new Node(8);
		assertTrue(node1.compareTo(node2) == 1);		//id1 = id2 + 2
		assertTrue(node2.compareTo(node1) == -1);
		
		node2 = new Node(11);
		assertTrue(node1.compareTo(node2) == -1);		//test compare to slightly higher value
		assertTrue(node2.compareTo(node1) == 1);
		
		node2 = new Node(12);
		assertTrue(node1.compareTo(node2) == -1);		//id1 + 2 = id2
		assertTrue(node2.compareTo(node1) == 1);
		
		node1 = new Node(0);
		node2 = new Node(Integer.MAX_VALUE);
		assertTrue(node1.compareTo(node2) == -1);		//test vastly different values.
		assertTrue(node2.compareTo(node1) == 1);
	}
	
	/**
	 * Boundary testing.  Tests the assertions made in calculateChildWebId to ensure that they 
	 * hold even at extreme integer values.  
	 * @custom.pre NONE
	 * @custom.post Tests have been run to test the boundaries of the assertions in calculateWhildWebId
	 */
	@Test
	public void testCalculateChildWebId(){
		Node parent = new Node(-1, 1); 								//test lower boundary of webId
		int childId = 0;
		try{
			BitManipulation.calculateChildWebId(parent);
			fail();
		} catch(AssertionError e){
			assertTrue(true);
		}
		
		parent = new Node(0, 1);									//test webId barely in range
		try{
			childId = BitManipulation.calculateChildWebId(parent);
			assertTrue(childId == 2);
		} catch(AssertionError e){
			fail();
		}
		
		parent = new Node(1,1);
		childId = BitManipulation.calculateChildWebId(parent);		//test webId in range
		assertTrue(childId == 3);
		
		parent = new Node(0x3FFFFFFE,30);
		childId = BitManipulation.calculateChildWebId(parent);		//test webId near maximum allowed value
		assertEquals(childId, 0x7FFFFFFE);
		
		parent = new Node(0x3FFFFFFF,30);							//test webId at maximum allowed value
		childId = BitManipulation.calculateChildWebId(parent);
		assertEquals(childId, 0x7FFFFFFF);
		
		parent = new Node(0x40000000,30);							//test weebId above maximum allowed value
		try{
			BitManipulation.calculateChildWebId(parent);
			fail();
		} catch(AssertionError e){
			assertTrue(true);
		}
		
		parent = new Node(1,-1);									//test height below minimum allowed value
		try{
			BitManipulation.calculateChildWebId(parent);
			fail();
		} catch(AssertionError e){
			assertTrue(true);
		}
		
		parent = new Node(0,0);										//test height at minimum allowed value
		childId = BitManipulation.calculateChildWebId(parent);
		assertTrue(childId == 1);
		
		parent = new Node(0,1);										//test height within range
		childId = BitManipulation.calculateChildWebId(parent);
		assertTrue(childId == 2);
		
		parent = new Node(0x3FFFFFFF, 30);							//test height at maximum allowed value
		childId = BitManipulation.calculateChildWebId(parent);
		assertEquals(childId, 0x7FFFFFFF);
		
		parent = new Node(0x3FFFFFFF, 31);							//test height out of range (too high)
		try{
			BitManipulation.calculateChildWebId(parent);
			fail();
		} catch(AssertionError e){
			assertTrue(true);
		}
	}
	
	/**
	 * Dataflow testing.  
	 */
	@Test
	public void testFindLargest(){
		Node testNode = web.getNode(15);							//assigned on line 1, used on line 16
		assertTrue(testNode.findLargest().getWebIdValue() == 15);
		
		testNode = web.getNode(0);									//assigned on line 3, used on line 19	
		assertTrue(testNode.findLargest().getWebIdValue() == 15);		
		
		testNode = web.getNode(14);									//assigned on line 9, used on line 19
		assertTrue(testNode.findLargest().getWebIdValue() == 15);
		
		web.removeFromHyPeerWeb(web.getNode(15));					//remove node to break perfect Hyper web
		
		testNode = web.getNode(7);									//assigned on line 12, used on line 19
		assertTrue(testNode.findLargest().getWebIdValue() == 14);
		
		testNode = web.getNode(0);									//assigned on line 6, used on line 19
		assertTrue(testNode.findLargest().getWebIdValue() == 14);		
	}
}
