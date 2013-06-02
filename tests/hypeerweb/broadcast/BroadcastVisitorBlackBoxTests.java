package hypeerweb.broadcast;

import hypeerweb.HyPeerWeb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import junit.framework.TestCase;
import node.Node;

/**
 * Tests the specifications for the BroadcastVisitor class.
 * @author Craig Jacobson
 *
 */
public class BroadcastVisitorBlackBoxTests extends TestCase{
	
	private final HashSet<Node> allNodesSet = new HashSet<Node>();
	private final ArrayList<Node> broadcastedToNodesList = new ArrayList<Node>();
	
	public void testCreateInitialParameters(){
		Parameters param = BroadcastVisitor.createInitialParameters();
		assertTrue(param != null);
	}
	
	/**
	 * Test invalid parameters (partitioning: invalid cases).
	 */
	public void testVisitInvalidCases(){
		HyPeerWeb web = HyPeerWeb.getSingleton();
		createHyPeerWeb(1); 
		Node nodeZero = web.getNode(0);
		
		BroadcastVisitor broadcastNothing = new BroadcastVisitor(){
			public void operation(Node node, Parameters parameters){
				//do nothing
			}
		};
		
		try{
			broadcastNothing.visit(null, null);
			fail("Failed to throw exception with null node and null parameters.");
		}
		catch(Exception e){
		}
		catch(Error e){
		}
		
		try{
			broadcastNothing.visit(nodeZero, null);
			fail("Failed to throw exception with null parameters.");
		}
		catch(Exception e){
		}
		catch(Error e){
		}
		
		try{
			broadcastNothing.visit(null, BroadcastVisitor.createInitialParameters());
			fail("Failed to throw exception for null node being passed in.");
		}
		catch(Exception e){
		}
		catch(Error e){
		}
	}
	
	/**
	 * Test valid parameters (partitioning: valid cases).
	 */
	public void testVisitValidCases(){
		HyPeerWeb web = HyPeerWeb.getSingleton();
		createHyPeerWeb(1);
		Node nodeZero = web.getNode(0);
		
		//Checks that each node is only visited once and that the node is in the HyPeerWeb.
		BroadcastVisitor broadcastFindAllNodes = new BroadcastVisitor(){
			public void operation(Node node, Parameters parameters){
				assert (allNodesSet.contains(node));
				broadcastedToNodesList.add(node);
			}
		};
		
		//Test HyPeerWeb of size one (boundary value analysis).
		try{
			broadcastFindAllNodes.visit(nodeZero, BroadcastVisitor.createInitialParameters());
			assertTrue(1 == this.broadcastedToNodesList.size());
		}
		catch(AssertionError e){
			fail("A node not in the HyPeerWeb was visited.");
		}
		
		Random generator = new Random();
		//Test HyPeerWeb of size two (boundary value analysis).
		createHyPeerWeb(2);
		int startNodeWebId = generator.nextInt(2);
		Node startNode = web.getNode(startNodeWebId);
		try{
			broadcastFindAllNodes.visit(startNode, BroadcastVisitor.createInitialParameters());
			assertTrue(2 == this.broadcastedToNodesList.size());
		}
		catch(AssertionError e){
			fail("A node not in the HyPeerWeb was visited.");
		}
		
		//Test a HyPeerWeb of size 30 (partitioning: valid case).
		int size = 30;
		createHyPeerWeb(size);
		startNodeWebId = generator.nextInt(size);
		startNode = web.getNode(startNodeWebId);
		try{
			broadcastFindAllNodes.visit(startNode, BroadcastVisitor.createInitialParameters());
			assertTrue(size == this.broadcastedToNodesList.size());
		}
		catch(AssertionError e){
			fail("A node not in the HyPeerWeb was visited.");
		}
	}
	
	public void testOperation(){
		HyPeerWeb web = HyPeerWeb.getSingleton();
		createHyPeerWeb(1);
		final Node nodeZero = web.getNode(0);
		
		//Test that node and parameters are non-null.
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
		
		//Test that node and parameters are the node and parameters expected.
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
	
	private void createHyPeerWeb(int size){
		clearAll();
		HyPeerWeb web = HyPeerWeb.getSingleton();
		
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
	
	private void clearAll(){
		allNodesSet.clear();
		broadcastedToNodesList.clear();
		HyPeerWeb web = HyPeerWeb.getSingleton();
		web.clear();
	}
}
