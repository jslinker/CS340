package hypeerweb.broadcast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import hypeerweb.HyPeerWeb;
import node.Node;
import junit.framework.TestCase;

public class SendVisitorBlackBoxTests extends TestCase{
	
	private final HashSet<Node> allNodesSet = new HashSet<Node>();
	private final ArrayList<Node> sentToNodesList = new ArrayList<Node>();

	public void testCreateInitialParameters(){
		//Invalid test case.
		try{
			@SuppressWarnings("unused")
			Parameters someParameters = SendVisitor.createInitialParameters(-1);
			fail();
		}
		catch(Exception e){
		}
		catch(Error e){
		}
		
		//Valid test case.
		Parameters param = SendVisitor.createInitialParameters(0);
		assertTrue(param != null);
		assertTrue(((Integer)param.get(SendVisitor.TARGET_KEY)).intValue() == 0);
	}
	
	/**
	 * Tests invalid parameters (partitioning: invalid cases).
	 */
	public void testVisitInvalidCases(){
		HyPeerWeb web = HyPeerWeb.getSingleton();
		createHyPeerWeb(1); 
		Node nodeZero = web.getNode(0);
		
		SendVisitor sendNothing = new SendVisitor(){
			public void targetOperation(Node node, Parameters parameters){
				//do nothing
			}
		};
		
		try{
			sendNothing.visit(null, null);
			fail("Failed to throw exception with null node and null parameters.");
		}
		catch(Exception e){
		}
		catch(Error e){
		}
		
		try{
			sendNothing.visit(nodeZero, null);
			fail("Failed to throw exception with null parameters.");
		}
		catch(Exception e){
		}
		catch(Error e){
		}
		
		try{
			sendNothing.visit(null, BroadcastVisitor.createInitialParameters());
			fail("Failed to throw exception for null node being passed in.");
		}
		catch(Exception e){
		}
		catch(Error e){
		}
	}
	
	public void testVisitValidCases(){
		HyPeerWeb web = HyPeerWeb.getSingleton();
		createHyPeerWeb(1);
		Node nodeZero = web.getNode(0);
		
		final int targetZero = 0;
		
		//Checks that the message got to node zero.
		SendVisitor sendToNodeZero = new SendVisitor(){
			public void intermediateOperation(Node node, Parameters parameters){
				throw new AssertionError("No intermediate operation needed.");
			}
			public void targetOperation(Node node, Parameters parameters){
				assert (allNodesSet.contains(node));
				sentToNodesList.add(node);
				assert (node.getWebIdValue() == targetZero);
			}
		};
		
		//Test HyPeerWeb of size one (boundary value analysis).
		try{
			sendToNodeZero.visit(nodeZero, SendVisitor.createInitialParameters(targetZero));
			assertTrue(1 == this.sentToNodesList.size());
		}
		catch(AssertionError e){
			fail("A node not in the HyPeerWeb was visited or the target operation was not executed on the target node.");
		}
		
		
		
		//Test HyPeerWeb of size two (boundary value analysis).
		createHyPeerWeb(2);
		final int startNodeWebId = 1;
		final int targetNodeWebId = 0;
		Node startNode = web.getNode(startNodeWebId);
		
		SendVisitor sendFromOneToZero = new SendVisitor(){
			public void intermediateOperation(Node node, Parameters parameters){
				assert (allNodesSet.contains(node));
				assert (node.getWebIdValue() == startNodeWebId);
			}
			public void targetOperation(Node node, Parameters parameters){
				assert (allNodesSet.contains(node));
				assert (node.getWebIdValue() == targetNodeWebId);
			}
		};
		
		try{
			sendFromOneToZero.visit(startNode, SendVisitor.createInitialParameters(targetNodeWebId));
		}
		catch(AssertionError e){
			fail("A node not in the HyPeerWeb was visited or an operation was called on the wrong node.");
		}
		
		
		
		Random generator = new Random();
		//Test a HyPeerWeb of size 121 (partitioning: valid case).
		int size = 121;
		createHyPeerWeb(size);
		final int startNodeWebId2 = generator.nextInt(size);
		final int targetNodeWebId2 = generator.nextInt(size);
		startNode = web.getNode(startNodeWebId2);
		
		SendVisitor sendToNode = new SendVisitor(){
			public void intermediateOperation(Node node, Parameters parameters){
				assert (allNodesSet.contains(node));
				assert (!sentToNodesList.contains(node));
				sentToNodesList.add(node);
			}
			public void targetOperation(Node node, Parameters parameters){
				assert (allNodesSet.contains(node));
				assert (node.getWebIdValue() == targetNodeWebId2);
			}
		};
		
		try{
			sendToNode.visit(startNode, SendVisitor.createInitialParameters(targetNodeWebId2));
			double log2 = Math.log(2.0d);
			assertTrue(this.sentToNodesList.size() < Math.ceil((Math.log(size) / log2)));
		}
		catch(AssertionError e){
			fail("A node not in the HyPeerWeb was visited.");
		}
	}
	
	public void testIntermediateOperation(){
		HyPeerWeb web = HyPeerWeb.getSingleton();
		int size = 30;
		createHyPeerWeb(size);
		final int targetZero = 0;
		final Node startNode = web.getNode(29);
		
		//Test that intermediateOperation is reached.
		SendVisitor sendVisitorTestReached = new SendVisitor(){
			public void intermediateOperation(Node node, Parameters parameters){
				throw new AssertionError("Reached.");
			}
			public void targetOperation(Node node, Parameters parameters){
				//do nothing
			}
		};
		
		try{
			sendVisitorTestReached.visit(startNode, SendVisitor.createInitialParameters(targetZero));
			fail("Intermediate operation method not reached.");
		}
		catch(AssertionError e){
			assertTrue(true);
		}
		
		//Test that node and parameters are non-null.
		SendVisitor sendVisitorTestNotNull = new SendVisitor(){
			public void intermediateOperation(Node node, Parameters parameters){
				assert (node != null && parameters != null);
			}
			public void targetOperation(Node node, Parameters parameters){
				//do nothing
			}
		};
		
		try{
			sendVisitorTestNotNull.visit(startNode, SendVisitor.createInitialParameters(targetZero));
		}
		catch(AssertionError e){
			fail("Passed null value to operation method.");
		}
		
		//Test that parameters are the parameters expected.
		final Parameters originalParameters = SendVisitor.createInitialParameters(targetZero);
		SendVisitor sendVisitorTestSameObject = new SendVisitor(){
			public void intermediateOperation(Node node, Parameters parameters){
				assert (parameters == originalParameters);
			}
			public void targetOperation(Node node, Parameters parameters){
				//do nothing
			}
		};
		try{
			sendVisitorTestSameObject.visit(startNode, originalParameters);
		}
		catch(AssertionError e){
			fail("Original parameters should have been passed into the operation method.");
		}
	}
	
	public void testTargetOperation(){
		HyPeerWeb web = HyPeerWeb.getSingleton();
		int size = 30;
		createHyPeerWeb(size);
		final int targetZero = 0;
		final Node startNode = web.getNode(29);
		
		//Test that intermediateOperation is reached.
		SendVisitor sendVisitorTestReached = new SendVisitor(){
			public void targetOperation(Node node, Parameters parameters){
				throw new AssertionError("Reached.");
			}
		};
		
		try{
			sendVisitorTestReached.visit(startNode, SendVisitor.createInitialParameters(targetZero));
			fail("Target operation method not reached.");
		}
		catch(AssertionError e){
			assertTrue(true);
		}
		
		//Test that node and parameters are non-null.
		SendVisitor sendVisitorTestNotNull = new SendVisitor(){
			public void targetOperation(Node node, Parameters parameters){
				assert (node != null && parameters != null);
			}
		};
		
		try{
			sendVisitorTestNotNull.visit(startNode, SendVisitor.createInitialParameters(targetZero));
		}
		catch(AssertionError e){
			fail("Passed null value to operation method.");
		}
		
		//Test that node and parameters are the node and parameters expected.
		final Parameters originalParameters = SendVisitor.createInitialParameters(targetZero);
		SendVisitor sendVisitorTestSameObject = new SendVisitor(){
			public void targetOperation(Node node, Parameters parameters){
				assert (parameters == originalParameters);
			}
		};
		try{
			sendVisitorTestSameObject.visit(startNode, originalParameters);
		}
		catch(AssertionError e){
			fail("Original parameters should have been passed into the operation method.");
		}
		
		//Test that TARGET_KEY is still set.
		assertTrue(originalParameters.containsKey(SendVisitor.TARGET_KEY));
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
		sentToNodesList.clear();
		HyPeerWeb web = HyPeerWeb.getSingleton();
		web.clear();
	}
}
