package hypeerweb.broadcast;

import hypeerweb.HyPeerWebSegment;
import hypeerweb.node.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import junit.framework.TestCase;

/**
 * Tests the specifications for the BroadcastVisitor class.
 * @author Craig Jacobson
 *
 */
public class BroadcastVisitorBlackBoxTests extends TestCase{
	
	private final HashSet<Node> allNodesSet = new HashSet<Node>();
	private final ArrayList<Node> broadcastedToNodesList = new ArrayList<Node>();
	
	/**
	 * Tests for valid output. Since no size method is provided in the Parameters specs
	 *  the postconditions cannot be fully tested through black box testing.
	 */
	public void testCreateInitialParameters(){
		Parameters param = BroadcastVisitor.createInitialParameters();
		assertTrue(param != null);
	}
	
	/**
	 * Test invalid parameters for visit (partitioning: invalid cases).
	 * 1. null, null
	 * 2. node, null
	 * 3. null, parameters
	 */
	public void testVisitInvalidCases(){
		HyPeerWebSegment web = HyPeerWebSegment.getSingleton();
		createHyPeerWeb(1); 
		Node nodeZero = web.getNode(0);
		
		BroadcastVisitor broadcastNothing = new BroadcastVisitor(){
			public void operation(Node node, Parameters parameters){
				//do nothing
			}
		};
		
		//#1
		try{
			broadcastNothing.visit(null, null);
			fail("Failed to throw exception with null node and null parameters.");
		}
		catch(AssertionError e){
		}
		
		//#2
		try{
			broadcastNothing.visit(nodeZero, null);
			fail("Failed to throw exception with null parameters.");
		}
		catch(AssertionError e){
		}
		
		//#3
		try{
			broadcastNothing.visit(null, BroadcastVisitor.createInitialParameters());
			fail("Failed to throw exception for null node being passed in.");
		}
		catch(AssertionError e){
		}
	}
	
	/**
	 * The valid partition is all HyPeerWebs of size >= 1, but there are two partitions in
	 * where you can start the broadcast: at node zero, not at node zero.
	 * Furthermore, the valid HyPeerWebs can be partitioned into HyPeerWebs that are HyperCubes,
	 *  and those that aren't.
	 * 
	 * 
	 * Boundary value analysis:
	 * 1. HyPeerWeb size 1, starting at node zero.
	 * 2. HyPeerWeb size 2, starting at node zero.
	 * 
	 * Test valid partitions:
	 * 3. HyPeerWeb size 32, starting at node zero.
	 * 4. HyPeerWeb size 32, starting at the last node (the last node would be a possible 
	 * boundary for the visit method's start node).
	 * 5. HyPeerWeb size 37, starting at node zero.
	 * 6. HyPeerWeb size 37, starting at a node that is not zero.
	 * Note that the upper bound for HyPeerWebs is too large to test.
	 */
	public void testVisitValidCases(){
		HyPeerWebSegment web = HyPeerWebSegment.getSingleton();
		
		//Checks that each node is only visited once and that the node is in the HyPeerWeb.
		BroadcastVisitor broadcastFindAllNodes = new BroadcastVisitor(){
			public void operation(Node node, Parameters parameters){
				assert (allNodesSet.contains(node));
				broadcastedToNodesList.add(node);
			}
		};
		
		//#1 Test HyPeerWeb of size one (boundary value analysis).
		createHyPeerWeb(1);
		Node nodeZero = web.getNode(0);
		try{
			broadcastFindAllNodes.visit(nodeZero, BroadcastVisitor.createInitialParameters());
			assertTrue(1 == this.broadcastedToNodesList.size());
		}
		catch(AssertionError e){
			fail("A node not in the HyPeerWeb was visited.");
		}
		
		Random generator = new Random();
		//#2 Test HyPeerWeb of size two (boundary value analysis).
		createHyPeerWeb(2);
		Node startNode = web.getNode(0);
		try{
			broadcastFindAllNodes.visit(startNode, BroadcastVisitor.createInitialParameters());
			assertTrue(2 == this.broadcastedToNodesList.size());
		}
		catch(AssertionError e){
			fail("A node not in the HyPeerWeb was visited.");
		}
		
		//#3 Test a HyPeerWeb of size 32.
		int size = 32;
		createHyPeerWeb(size);
		startNode = web.getNode(0);
		try{
			broadcastFindAllNodes.visit(startNode, BroadcastVisitor.createInitialParameters());
			assertTrue(size == this.broadcastedToNodesList.size());
		}
		catch(AssertionError e){
			fail("A node not in the HyPeerWeb was visited.");
		}
		
		//#4 Test a HyPeerWeb of size 32 starting at the last node.
		size = 32;
		createHyPeerWeb(size);
		startNode = web.getNode(31);
		try{
			broadcastFindAllNodes.visit(startNode, BroadcastVisitor.createInitialParameters());
			assertTrue(size == this.broadcastedToNodesList.size());
		}
		catch(AssertionError e){
			fail("A node not in the HyPeerWeb was visited.");
		}
		
		//#5 Test a HyPeerWeb size 37, starting at node zero.
		size = 37;
		createHyPeerWeb(size);
		startNode = web.getNode(0);
		try{
			broadcastFindAllNodes.visit(startNode, BroadcastVisitor.createInitialParameters());
			assertTrue(size == this.broadcastedToNodesList.size());
		}
		catch(AssertionError e){
			fail("A node not in the HyPeerWeb was visited.");
		}
		
		//#6 Test a HyPeerWeb size 37, starting at a node that is not zero.
		size = 37;
		createHyPeerWeb(size);
		int startNodeWebId = generator.nextInt(size-1) + 1;
		startNode = web.getNode(startNodeWebId);
		try{
			broadcastFindAllNodes.visit(startNode, BroadcastVisitor.createInitialParameters());
			assertTrue(size == this.broadcastedToNodesList.size());
		}
		catch(AssertionError e){
			fail("A node not in the HyPeerWeb was visited.");
		}
	}
	
	/**
	 * Tests for invalid inputs into the operation method.
	 * Invalid: null values, a node not in the HyPeerWeb, or parameters not originally passed in.
	 * Valid: a node in the HyPeerWeb, and the parameters originally passed in.
	 * 
	 * Note that testing whether or not a node was in the HyPeerWeb was done in the
	 * testVisitValidCases() method.
	 */
	public void testOperation(){
		HyPeerWebSegment web = HyPeerWebSegment.getSingleton();
		createHyPeerWeb(1);
		final Node nodeZero = web.getNode(0);
		
		//Testing for valid inputs.
		BroadcastVisitor operationTestNotNull = new BroadcastVisitor(){
			public void operation(Node node, Parameters parameters){
				assert (node != null && parameters != null);
			}
		};
		try{
			operationTestNotNull.visit(nodeZero, BroadcastVisitor.createInitialParameters());
		}
		catch(AssertionError e){
			fail("Passed null value to operation method.");
		}
		
		//Testing for valid and expected inputs.
		final Parameters originalParameters = BroadcastVisitor.createInitialParameters();
		BroadcastVisitor operationTestSameObject = new BroadcastVisitor(){
			public void operation(Node node, Parameters parameters){
				assert (node == nodeZero && parameters == originalParameters);
			}
		};
		try{
			operationTestSameObject.visit(nodeZero, originalParameters);
		}
		catch(AssertionError e){
			fail("Original parameters should have been passed into the operation method.");
		}
	}
	
	/**
	 * Creates a web of the given size.
	 * @param size The size of the desired web.
	 * @pre size >= 0
	 */
	private void createHyPeerWeb(int size){
		assert (size >= 0);
		
		clearAll();
		HyPeerWebSegment web = HyPeerWebSegment.getSingleton();
		
		if(size > 0){
			web.addToHyPeerWeb(new Node(0), null);
			Node nodeZero = web.getNode(0);
			allNodesSet.add(nodeZero);
			
			for(int i = 1; i < size; i++){
				Node newNode = new Node(i);
				nodeZero.addToHyPeerWeb(newNode);
				allNodesSet.add(newNode);
			}
		}
	}
	
	/**
	 * Resets all webs, sets, and lists in use.
	 */
	private void clearAll(){
		allNodesSet.clear();
		broadcastedToNodesList.clear();
		HyPeerWebSegment web = HyPeerWebSegment.getSingleton();
		web.clear();
	}
}
