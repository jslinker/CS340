package dbPhase.hypeerweb;

import static utilities.BitManipulation.calculateInsertionPointWebId;
import static utilities.BitManipulation.calculateSurrogateWebId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import junit.framework.TestCase;

/**
 * Tests the Node class.
 * @author Craig Jacobson
 *
 */
public class NodeTests extends TestCase{
	
	public NodeTests(String name){
		super(name);
	}
	
	public void testNullNode(){
		NodeCore.getNullNode().setWebId(new WebId(2));
		assertTrue(NodeCore.getNullNode().getWebId() == WebId.NULL_WEB_ID);
		
		NodeCore.getNullNode().setFold(new NodeCore(0));
		NodeCore.getNullNode().setSurrogateFold(new NodeCore(10));
		NodeCore.getNullNode().setInverseSurrogateFold(new NodeCore(30));
		assertTrue(NodeCore.getNullNode().getFold() == NodeCore.getNullNode());
		assertTrue(NodeCore.getNullNode().getSurrogateFold() == NodeCore.getNullNode());
		assertTrue(NodeCore.getNullNode().getInverseSurrogateFold() == NodeCore.getNullNode());
		
		NodeCore.getNullNode().addNeighbor(new NodeCore(1));
		NodeCore.getNullNode().addUpPointer(new NodeCore(2));
		NodeCore.getNullNode().addDownPointer(new NodeCore(3));
		assertTrue(NodeCore.getNullNode().getNeighbors().size() == 0);
		assertTrue(NodeCore.getNullNode().getUpPointers().size() == 0);
		assertTrue(NodeCore.getNullNode().getDownPointers().size() == 0);
		
//		try {
//			NodeCore.getNullNode().removeConnection(null);
//			NodeCore.getNullNode().removeConnection(null, null);
//			NodeCore.getNullNode().removeDownPointer(null);
//			NodeCore.getNullNode().removeFromHyPeerWeb(null);
//			NodeCore.getNullNode().removeNeighbor(null);
//			NodeCore.getNullNode().removeUpPointer(null);
//			NodeCore.getNullNode().accept(null, null);
//			NodeCore.getNullNode().findNode(0);
//		} catch (Exception e) {
//			fail("Should be able to call remove and accept on null objects with throwing an exception");
//		}
	}
	
	public void testConstructors(){
		NodeCore node1 = new NodeCore(4);
		assertTrue(node1.getFold() == node1);
		assertTrue(node1.getSurrogateFold() == NodeCore.getNullNode());
		assertTrue(node1.getInverseSurrogateFold() == NodeCore.getNullNode());
		
		NodeCore node2 = new NodeCore(3,4);
		assertTrue(node2.getWebIdValue() == 3);
		assertTrue(node2.getHeight() == 4);
		assertTrue(node2.getFold() == node2);
	}
	
	public void testHashCode(){
		NodeCore node1 = new NodeCore(3);
		NodeCore node2 = new NodeCore(4);
		NodeCore node3 = new NodeCore(5);
		
		assertTrue(NodeCore.getNullNode().hashCode() == -1);
		assertTrue(node1.hashCode() != node2.hashCode());
		assertTrue(node2.hashCode() != node3.hashCode());
	}
	
	public void testConstructSimplifiedNodeDomain(){
		SimplifiedNodeDomain nullSND = NodeCore.getNullNode().constructSimplifiedNodeDomain();
		assertTrue(nullSND != null);
		assertTrue(nullSND.getFold() == -1);
		assertTrue(nullSND.getSurrogateFold() == -1);
		assertTrue(nullSND.getInverseSurrogateFold() == -1);
		assertTrue(nullSND.getWebId() == -1);
		assertTrue(nullSND.getHeight() == -1);
		assertTrue(nullSND.getNeighbors().size() == 0);
		assertTrue(nullSND.getUpPointers().size() == 0);
		assertTrue(nullSND.getDownPointers().size() == 0);
		
		NodeCore node1 = new NodeCore(1,3);
		NodeCore node5 = new NodeCore(5,3);
		NodeCore node6 = new NodeCore(6,3);
		node5.setFold(new NodeCore(2,3));
		node5.addNeighbor(new NodeCore(4,3));
		node5.addNeighbor(node1);
		node1.addNeighbor(node5);
		node1.addUpPointer(node6);
		node6.addDownPointer(node1);
		
		SimplifiedNodeDomain simple1 = node1.constructSimplifiedNodeDomain();
		SimplifiedNodeDomain simple5 = node5.constructSimplifiedNodeDomain();
		SimplifiedNodeDomain simple6 = node6.constructSimplifiedNodeDomain();
		assertTrue(simple1.getNeighbors().contains(new Integer(5)));
		assertTrue(simple1.getHeight() == 3);
		assertTrue(simple1.getUpPointers().contains(new Integer(6)));
		assertTrue(simple5.getHeight() == 3);
		assertTrue(simple5.getFold() == 2);
		assertTrue(simple5.getNeighbors().contains(new Integer(1)));
		assertTrue(simple5.getNeighbors().contains(new Integer(4)));
		assertTrue(simple6.getDownPointers().contains(new Integer(1)));
	}
	
	public void testFindNode(){
		NodeCore node1 = new NodeCore(0,3);
		NodeCore node2 = new NodeCore(1,3);
		NodeCore node3 = new NodeCore(2,3);
		NodeCore node4 = new NodeCore(3,3);
		NodeCore node5 = new NodeCore(4,3);
		NodeCore node6 = new NodeCore(5,3);
		NodeCore node7 = new NodeCore(6,3);
		NodeCore node8 = new NodeCore(7,3);
		
		HyPeerWeb web = HyPeerWeb.getSingleton();
		web.addToHyPeerWeb(node1, null);
		web.addToHyPeerWeb(node2, node1);
		web.addToHyPeerWeb(node3, node1);
		web.addToHyPeerWeb(node4, node1);
		web.addToHyPeerWeb(node5, node1);
		web.addToHyPeerWeb(node6, node1);
		web.addToHyPeerWeb(node7, node1);
		web.addToHyPeerWeb(node8, node1);
		
//		node1.addNeighbor(node2);
//		node1.addNeighbor(node3);
//		node1.addNeighbor(node5);
//		node2.addNeighbor(node4);
//		node2.addNeighbor(node6);
//		node2.addNeighbor(node1);
//		node3.addNeighbor(node4);
//		node3.addNeighbor(node7);
//		node3.addNeighbor(node1);
//		node4.addNeighbor(node8);
//		node4.addNeighbor(node2);
//		node4.addNeighbor(node3);
//		node5.addNeighbor(node6);
//		node5.addNeighbor(node7);
//		node5.addNeighbor(node1);
//		node6.addNeighbor(node8);
//		node6.addNeighbor(node5);
//		node6.addNeighbor(node2);
//		node7.addNeighbor(node8);
//		node7.addNeighbor(node3);
//		node7.addNeighbor(node5);
//		node8.addNeighbor(node7);
//		node8.addNeighbor(node6);
//		node8.addNeighbor(node4);
		
		assertTrue(node1.getNode(0).equals(node1));
		assertTrue(node1.getNode(1).equals(node2));
		assertTrue(node1.getNode(2).equals(node3));
		assertTrue(node1.getNode(3).equals(node4));
		assertTrue(node1.getNode(4).equals(node5));
		assertTrue(node1.getNode(5).equals(node6));
		assertTrue(node1.getNode(6).equals(node7));
		assertTrue(node1.getNode(7).equals(node8));
		
		/*
		 * Start Exhaustive Testing
		 * Exhaustively tests findNode for a web of size 32.
		 */
		//create nodes
		ArrayList<Node> nodes = new ArrayList<Node>();
		int numberOfNodes = 32;
		for(int i = 0; i < numberOfNodes; i++){
			nodes.add(new NodeCore(i));
		}
		//construct web
		for(int i = 1; i < nodes.size(); i++){
			nodes.get(calculateSurrogateWebId(i)).addChild(nodes.get(i));
		}
		//run exhaustive testing
		for(int i = 0; i < nodes.size(); i++){
			for(int j = 0; j < nodes.size(); j++){
				Node startNode = nodes.get(i);
				Node expectedFoundNode = nodes.get(j);
				Node foundNode = startNode.getNode(j);
				
				assertTrue("\nActual: " + foundNode.constructSimplifiedNodeDomain().toString() + "\n" +
							"Expected: " + expectedFoundNode.constructSimplifiedNodeDomain().toString(),
							(expectedFoundNode.equals(foundNode)));
			}
		}
		/*
		 * End Exhaustive Testing
		 */
	}
	
	public void testReplaceNode(){
		Node node0 = new NodeCore(0);
	}
	
	/**
	 * Tests adding a few child nodes.
	 */
	public void testAddChild(){
		/*
		 * Start Simple Test
		 */
		Node node0 = new NodeCore(0);
		int webSize = 1;
		assertTrue(isNodeDomainCorrect(node0, webSize));
		
		Node node1 = new NodeCore(1);
		node0.addChild(node1);
		webSize++;
		assertTrue(isNodeDomainCorrect(node0, webSize));
		assertTrue(isNodeDomainCorrect(node1, webSize));
		
		Node node2 = new NodeCore(2);
		node0.addChild(node2);
		webSize++;
		assertTrue(isNodeDomainCorrect(node0, webSize));
		assertTrue(isNodeDomainCorrect(node1, webSize));
		assertTrue(isNodeDomainCorrect(node2, webSize));
		
		Node node3 = new NodeCore(3);
		node1.addChild(node3);
		webSize++;
		assertTrue(isNodeDomainCorrect(node0, webSize));
		assertTrue(isNodeDomainCorrect(node1, webSize));
		assertTrue(isNodeDomainCorrect(node2, webSize));
		assertTrue(isNodeDomainCorrect(node3, webSize));
		/*
		 * End Simple Test
		 */
		
		/*
		 * Start Random Exhaustive Testing
		 */
		//create the nodes
		ArrayList<Node> nodes = new ArrayList<Node>();
		Random generator = new Random();
		int numberOfNodes = generator.nextInt(37-19)+20;
		for(int i = 0; i < numberOfNodes; i++){
			nodes.add(new NodeCore(i));
		}
		SimplifiedNodeDomain simpleNode = nodes.get(0).constructSimplifiedNodeDomain();
		ExpectedResult expectedNode = new ExpectedResult(1, 0);
		assertTrue("\nActual: " + simpleNode + "\n" +
					"Expected: " + expectedNode,
					simpleNode.equals(expectedNode));
		
		//add the nodes to their respective parents
		for(int i = 1; i < numberOfNodes; i++){
			nodes.get(calculateSurrogateWebId(i)).addChild(nodes.get(i));
			
			//after each addition, exhaustively test all of the nodes in the web
			for(int j = i; j >= 0; j--){
				simpleNode = nodes.get(j).constructSimplifiedNodeDomain();
				expectedNode = new ExpectedResult(i+1, j);
				//assertTrue(isNodeDomainCorrect(nodes.get(j), i+1));
				assertTrue("\nSize of web: " + (i+1) + "\n" +
							"Actual: " + simpleNode + "\n" +
							"Expected: " + expectedNode,
							simpleNode.equals(expectedNode));
			}
		}
		/*
		 * End Random Exhaustive Testing
		 */
		
		/*
		 * Start Random Testing
		 * Adds 2049 to 4096 (inclusive) nodes testing random nodes for the proper node domain.
		 */
		//create the nodes
		nodes = new ArrayList<Node>();
		numberOfNodes = generator.nextInt(2048)+2049;
		for(int i = 0; i < numberOfNodes; i++){
			nodes.add(new NodeCore(i));
		}
		
		//add the nodes to their respective parents
		for(int i = 1; i < numberOfNodes; i++){
			nodes.get(calculateSurrogateWebId(i)).addChild(nodes.get(i));
			
			//test a random node
			int randomNode = generator.nextInt(i);
			assertTrue(isNodeDomainCorrect(nodes.get(randomNode), i+1));
		}
		/*
		 * End Random Testing
		 */
	}
	
	/**
	 * Tests a node's domain and prints out descriptive error messages.
	 * @param node The node to be tested for validity.
	 * @param webSize The size of the web the node is in.
	 * @return True if the node's domain is correct; false otherwise.
	 */
	public static boolean isNodeDomainCorrect(Node node, int webSize){
		SimplifiedNodeDomain simpleNode = node.constructSimplifiedNodeDomain();
		ExpectedResult expectedNode = new ExpectedResult(webSize, node.getWebIdValue());
		
		boolean result = true;
		
		if(simpleNode.getHeight() != expectedNode.getHeight()){
			result = false;
			System.err.println("Actual height: " + simpleNode.getHeight() + "\n" +
								"Expected height: " + expectedNode.getHeight());
		}
		
		if(simpleNode.getFold() != expectedNode.getFold()){
			result = false;
			System.err.println("Actual fold: " + simpleNode.getFold() + "\n" +
								"Expected fold: " + expectedNode.getFold());
		}
		if(simpleNode.getSurrogateFold() != expectedNode.getSurrogateFold()){
			result = false;
			System.err.println("Actual surrogate fold: " + simpleNode.getSurrogateFold() + "\n" +
								"Expected surrogate fold: " + expectedNode.getSurrogateFold());
		}
		if(simpleNode.getInverseSurrogateFold() != expectedNode.getInverseSurrogateFold()){
			result = false;
			System.err.println("Actual inverse surrogate fold: " + simpleNode.getInverseSurrogateFold() + "\n" +
								"Expected inverse surrogate fold: " + expectedNode.getInverseSurrogateFold());
		}
		
		if(simpleNode.getNeighbors().size() != expectedNode.getNeighbors().size()){
			result = false;
			System.err.println("Actual number of neighbors: " + simpleNode.getNeighbors().size() + "\n" +
								"Expected number of neighbors: " + expectedNode.getNeighbors().size());
		}
		else{
			HashSet<Integer> expectedNeighbors = expectedNode.getNeighbors();
			for(Integer neighbor: simpleNode.getNeighbors()){
				if(!expectedNeighbors.contains(neighbor)){
					result = false;
					System.err.println("Actual neighbor not found in expected neighbor list: " + neighbor);
				}
			}
		}
		
		if(simpleNode.getDownPointers().size() != expectedNode.getDownPointers().size()){
			result = false;
			System.err.println("Actual number of down pointers: " + simpleNode.getDownPointers().size() + "\n" +
								"Expected number of down pointers: " + expectedNode.getDownPointers().size());
		}
		else{
			HashSet<Integer> expectedDownPointers = expectedNode.getDownPointers();
			for(Integer downPointer: simpleNode.getDownPointers()){
				if(!expectedDownPointers.contains(downPointer)){
					result = false;
					System.err.println("Actual down pointer not found in expected down pointer list: " + downPointer);
				}
			}
		}
		
		if(simpleNode.getUpPointers().size() != expectedNode.getUpPointers().size()){
			result = false;
			System.err.println("Actual number of up pointers: " + simpleNode.getUpPointers().size() + "\n" +
								"Expected number of up pointers: " + expectedNode.getUpPointers().size());
		}
		else{
			HashSet<Integer> expectedUpPointers = expectedNode.getUpPointers();
			for(Integer upPointer: simpleNode.getUpPointers()){
				if(!expectedUpPointers.contains(upPointer)){
					result = false;
					System.err.println("Actual up pointer not found in expected up pointer list: " + upPointer);
				}
			}
		}
		
		if(simpleNode.getState() != expectedNode.getState()){
			result = false;
			System.err.println("Actual state: " + simpleNode.getState() + "\n" +
								"Expected state: " + expectedNode.getState());
		}
		
		return result;
	}
	
	/**
	 * Randomly creates a HyPeerWeb of size between 101 and 200, inclusive.
	 * Then finds the insertion point by randomly selecting a node and calling 
	 * {@code findInsertionPoint}.
	 */
	public void testFindInsertionPoint(){
		/*
		 * Start Random Testing
		 */
		//create the nodes
		ArrayList<Node> nodes = new ArrayList<Node>();
		int numberOfNodes = 200;
		for(int i = 0; i < numberOfNodes; i++){
			nodes.add(new NodeCore(i));
		}
		
		//add the nodes one by one testing for the insertion point after each addition
		for(int i = 1; i < numberOfNodes; i++){
			
			Node expectedInsertionPoint = nodes.get(calculateInsertionPointWebId(i-1));
			Node insertionPoint = NodeCore.getNullNode();
			//test findInsertionPoint on each node
			for(int j = 0; j < i; j++){
				insertionPoint = nodes.get(j).insertionStageOne();
				assertTrue("\nExpected: " + expectedInsertionPoint.getWebIdValue() + "\n" +
							"Actual: " + insertionPoint.getWebIdValue() + "\n" +
							"Deletion Point: " + (i-1), 
							insertionPoint == expectedInsertionPoint);
			}
			
			insertionPoint.addChild(nodes.get(i));
			assertTrue(isNodeDomainCorrect(nodes.get(i), i+1));
		}
		/*
		 * End Random Testing
		 */
	}

	/**
	 * Randomly creates a HyPeerWeb of size between 101 and 2000, inclusive.
	 * Then tests the {@code findDeletionPoint} from all the nodes within the web.
	 */
	public void testFindDeletionPoint(){

		HyPeerWeb web = HyPeerWeb.getSingleton();
		web.clear();

		// Generate a random size for the web
		Random random = new Random();
		int webSize = random.nextInt(2000-100) + 101;
		int expectedDeletionPoint = webSize - 1;
		
		// Fill the web with nodes
		for(int i = 0; i < webSize; i++) {
			web.addToHyPeerWeb(new NodeCore(i), web.getNode(0));
		}
		
		// Test findDeletionPoint from all the nodes in the web
		for(int i = 0; i < webSize; i++) {
			Node deletionPoint = web.getNode(i).getDeletionNode();
			assertEquals(deletionPoint.getWebIdValue(), expectedDeletionPoint);
		}
	}
	
	public void testAddToHyPeerWeb(){
		ArrayList<Node> nodes = new ArrayList<Node>();
		Random generator = new Random();
		int numberOfNodes = 200;
		for(int i = 0; i < numberOfNodes; i++){
			nodes.add(new NodeCore(generator.nextInt(10000)));
		}
		
		for(int i = 0; i < nodes.size(); i++){
			//insert next node using addToHyPeerWeb called on a randomly selected node
			Node newNode = nodes.get(i);
			int randomStartNodeWebId = generator.nextInt(i);
			Node startNode = nodes.get(randomStartNodeWebId);
			startNode.addToHyPeerWeb(newNode);
			for(int j = 0; j <= i; j++){
				assertTrue(isNodeDomainCorrect(nodes.get(j), i+1));
			}
		}
	}
	
	/**
	 * A semi-exhaustive Black Box test on removing nodes from the web
	 */
	public void testRemoveFromHyPeerWeb() {
		HyPeerWeb web = HyPeerWeb.getSingleton();
		web.clear();

		// Generate a random size for the web
		Random random = new Random();
		int webSize = random.nextInt(2000-100) + 101;
		
		// Fill the web with nodes
		for(int i = 0; i < webSize; i++) {
			web.addToHyPeerWeb(new NodeCore(i), web.getNode(0));
		}
		
		Node rootNode = web.getNode(0);
		// check to make sure all the nodes are present
		for (int i = webSize - 1; i > 0; i--) {
			assertTrue(rootNode.getNode(i) != NodeCore.getNullNode());
		}
		
		// check to make sure deleting them makes them go away
		for (int i = webSize - 1; i > 0; i--) {
			rootNode.deleteFromHyPeerWeb(web.getNode(i));
			assertEquals(rootNode.getNode(i),NodeCore.getNullNode());
		}
	}
	
	/**
	 * Simple test for comparing nodes
	 */
	
	//they have no compareTO
//	public void testCompareNodes(){
//		Node n0 = new NodeCore(0);
//		Node n1 = new NodeCore(1);
//		Node n2 = new NodeCore(2);
//		assertTrue(n0.compareTo(n0) == 0);
//		assertTrue(n0.compareTo(n1) == -1);
//		assertTrue(n0.compareTo(n2) == -1);
//		
//		assertTrue(n1.compareTo(n0) == 1);
//		assertTrue(n1.compareTo(n1) == 0);
//		assertTrue(n1.compareTo(n2) == -1);
//		
//		assertTrue(n2.compareTo(n0) == 1);
//		assertTrue(n2.compareTo(n1) == 1);
//		assertTrue(n2.compareTo(n2) == 0);
//	}
	
	/**
	 * Tests the findLargest() method which should return either the cap node or an edge node.
	 */
	public void testFindLargest(){
		/*
		 * Start Exhaustive Testing
		 */
		//create the nodes
		ArrayList<Node> nodes = new ArrayList<Node>();
		int numberOfNodes = 200;
		for(int i = 0; i < numberOfNodes; i++){
			nodes.add(new NodeCore(i));
		}
		
		for(int i = 1; i < numberOfNodes; i++){
			//add the nodes to their respective parents
			nodes.get(calculateSurrogateWebId(i)).addChild(nodes.get(i));
			
			//test post conditions of the findLargest() method
			//Node largest = nodes.get(generator.nextInt(i)).findLargest();
			for(int j = 0; j <= i; j++){
				Node largest = nodes.get(j).insertionStageOne();
				
				assertTrue("\nActual largest webId: " + largest.getWebIdValue() + "\n" + 
							"Last webId in web: " + i + "\n" +
							"Start node webId: " + j + "\n" +
							"Cap Node? " + (largest.getFold().getWebIdValue() == 0) + "\n" + 
							"|DownPointers| = " + largest.getDownPointers().size(), 
							(largest.getFold().getWebIdValue() == 0 &&
							largest.getDownPointers().size() > 0));
			}
		}
		/*
		 * End Exhaustive Testing
		 */
	}
	
	public void testDisconnect(){
		/*
		 * Start Exhaustive Testing
		 */
		
		//create nodes
		ArrayList<Node> nodes = new ArrayList<Node>();
		int numberOfNodes = 32;
		for(int i = 0; i < numberOfNodes; i++){
			nodes.add(new NodeCore(i));
		}
		//construct web
		for(int i = 1; i < nodes.size(); i++){
			nodes.get(calculateSurrogateWebId(i)).addChild(nodes.get(i));
		}
		//disconnect and test nodes
		for(int i = nodes.size()-1; i != 0; i--){
			//disconnect final node
			nodes.get(i).deleteFromHyPeerWeb(nodes.get(i));
			for(int j = 0; j < i; j++){
				Node aNode = nodes.get(j);
				SimplifiedNodeDomain aSimpleNode = aNode.constructSimplifiedNodeDomain();
				ExpectedResult expectedNode = new ExpectedResult(i, j);
				assertTrue("\nActual: " + aSimpleNode.toString() + "\n" +
						"Expected: " + expectedNode.toString(),
						aSimpleNode.equals(expectedNode));
			}
		}
		
		/*
		 * End Exhaustive Testing
		 */
	}
	
	public void testReplace(){
		/*
		 * Start Exhaustive Testing
		 */
		
		//create nodes
		ArrayList<Node> nodes = new ArrayList<Node>();
		int numberOfNodes = 32;
		for(int i = 0; i < numberOfNodes; i++){
			nodes.add(new NodeCore(i));
		}
		
		//construct web
		for(int i = 1; i < nodes.size(); i++){
			nodes.get(calculateSurrogateWebId(i)).addChild(nodes.get(i));
		}

		//test replace
		for(int i = 0; i < nodes.size(); i++){
			
			//replace the node
			Node replacementNode = new NodeCore(230);
			Node nodeToReplace = nodes.get(i);
			nodeToReplace.deleteFromHyPeerWeb(nodeToReplace);
			nodes.set(i, replacementNode);
			
			//Test references to ensure the node is actually replaced.
			//This also checks to see if the web can be properly traversed after replacement
			//since the node domain may not be enough to ensure web integrity.
			
			Node nodeFound = null;
			if(i == 0){
				nodeFound = nodes.get(1).getNode(i);
			}
			else{
				nodeFound = nodes.get(0).getNode(i);
			}
			
			assertTrue("The node found in the web is not the replacement node.", 
						nodeFound == nodes.get(i) && nodeFound != nodeToReplace);
			
			//test node domains to ensure connection integrity is intact
			for(int j = 0; j < nodes.size(); j++){
				Node aNode = nodes.get(j);
				SimplifiedNodeDomain aSimpleNode = aNode.constructSimplifiedNodeDomain();
				ExpectedResult expectedNode = new ExpectedResult(nodes.size(), j);
				assertTrue("\nActual: " + aSimpleNode.toString() + "\n" +
							"Expected: " + expectedNode.toString(),
							aSimpleNode.equals(expectedNode));
			}
		}
		/*
		 * End Exhaustive Testing
		 */
	}
	
	public void testEquals(){
		//Test null.
		Node newNode = new NodeCore(0);
		assertFalse(newNode.equals(null));
		
		//Test non-Node object.
		assertFalse(newNode.equals("Node"));
	}
	
	public void testToString(){
		Node newNode = new NodeCore(0);
		assertTrue(newNode.toString().equals(Integer.toBinaryString(newNode.getWebIdValue())));
	}
}
