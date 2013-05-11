package node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import node.Node;
import node.SimplifiedNodeDomain;
import node.WebId;
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
		Node.NULL_NODE.setWebId(new WebId(2));
		assertTrue(Node.NULL_NODE.getWebId() == WebId.NULL_WEB_ID);
		
		Node.NULL_NODE.setFold(new Node(0));
		Node.NULL_NODE.setSurrogateFold(new Node(10));
		Node.NULL_NODE.setInverseSurrogateFold(new Node(30));
		assertTrue(Node.NULL_NODE.getFold() == Node.NULL_NODE);
		assertTrue(Node.NULL_NODE.getSurrogateFold() == Node.NULL_NODE);
		assertTrue(Node.NULL_NODE.getInverseSurrogateFold() == Node.NULL_NODE);
		
		Node.NULL_NODE.addNeighbor(new Node(1));
		Node.NULL_NODE.addUpPointer(new Node(2));
		Node.NULL_NODE.addDownPointer(new Node(3));
		assertTrue(Node.NULL_NODE.getNeighbors().size() == 0);
		assertTrue(Node.NULL_NODE.getUpPointers().size() == 0);
		assertTrue(Node.NULL_NODE.getDownPointers().size() == 0);
	}
	
	public void testConstructors(){
		Node node1 = new Node(4);
		assertTrue(node1.getFold() == node1);
		assertTrue(node1.getSurrogateFold() == Node.NULL_NODE);
		assertTrue(node1.getInverseSurrogateFold() == Node.NULL_NODE);
		
		Node node2 = new Node(3,4);
		assertTrue(node2.getWebIdValue() == 3);
		assertTrue(node2.getWebIdHeight() == 4);
		assertTrue(node2.getFold() == node2);
	}
	
	public void testHashCode(){
		Node node1 = new Node(3);
		Node node2 = new Node(4);
		Node node3 = new Node(5);
		
		assertTrue(Node.NULL_NODE.hashCode() == -1);
		assertTrue(node1.hashCode() != node2.hashCode());
		assertTrue(node2.hashCode() != node3.hashCode());
	}
	
	public void testConstructSimplifiedNodeDomain(){
		SimplifiedNodeDomain nullSND = Node.NULL_NODE.constructSimplifiedNodeDomain();
		assertTrue(nullSND != null);
		assertTrue(nullSND.getFold() == -1);
		assertTrue(nullSND.getSurrogateFold() == -1);
		assertTrue(nullSND.getInverseSurrogateFold() == -1);
		assertTrue(nullSND.getWebId() == -1);
		assertTrue(nullSND.getHeight() == -1);
		assertTrue(nullSND.getNeighbors().size() == 0);
		assertTrue(nullSND.getUpPointers().size() == 0);
		assertTrue(nullSND.getDownPointers().size() == 0);
		
		Node node1 = new Node(1,3);
		Node node5 = new Node(5,3);
		Node node6 = new Node(6,3);
		node5.setFold(new Node(2,3));
		node5.addNeighbor(new Node(4,3));
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
	
	public void testAddAndRemove(){
		
	}
	
	/**
	 * Tests adding a few child nodes.
	 */
	public void testAddChildSimple(){
		Node node0 = new Node(0);
		int webSize = 1;
		assertTrue(isNodeDomainCorrect(node0, webSize));
		
		Node node1 = new Node(1);
		node0.addChild(node1);
		webSize++;
		assertTrue(isNodeDomainCorrect(node0, webSize));
		assertTrue(isNodeDomainCorrect(node1, webSize));
		
		Node node2 = new Node(2);
		node0.addChild(node2);
		webSize++;
		assertTrue(isNodeDomainCorrect(node0, webSize));
		assertTrue(isNodeDomainCorrect(node1, webSize));
		assertTrue(isNodeDomainCorrect(node2, webSize));
		
		Node node3 = new Node(3);
		node1.addChild(node3);
		webSize++;
		assertTrue(isNodeDomainCorrect(node0, webSize));
		assertTrue(isNodeDomainCorrect(node1, webSize));
		assertTrue(isNodeDomainCorrect(node2, webSize));
		assertTrue(isNodeDomainCorrect(node3, webSize));
	}
	
	/**
	 * Tests a node's domain and prints out descriptive error messages.
	 * @param node The node to be tested for validity.
	 * @param webSize The size of the web the node is in.
	 * @return True if the node's domain is correct; false otherwise.
	 */
	private boolean isNodeDomainCorrect(Node node, int webSize){
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
		
		return result;
	}
	
	/**
	 * Adds 19 to 37 (inclusive) nodes (by adding to the parent node directly) to the web and 
	 * exhaustively tests each and every node after each insertion.
	 */
	public void testAddChildRandomExhaustive(){
		//create the nodes
		ArrayList<Node> nodes = new ArrayList<Node>();
		Random generator = new Random();
		int numberOfNodes = generator.nextInt(37-19)+20;
		for(int i = 0; i < numberOfNodes; i++){
			nodes.add(new Node(i));
		}
		
		//add the nodes to their respective parents
		for(int i = 1; i < numberOfNodes; i++){
			nodes.get(calculateSurrogate(i)).addChild(nodes.get(i));
			
			//after each addition, exhaustively test all of the nodes in the web
			for(int j = i; j >= 0; j--){
				assertTrue(isNodeDomainCorrect(nodes.get(j), i+1));
			}
		}
	}
	
	/**
	 * Adds 500 to 1000 (inclusive) nodes (by adding to the parent node directly) to the web and 
	 * exhaustively tests each and every node after each insertion.
	 */
	public void testAddChildRandomLarge(){
		//create the nodes
		ArrayList<Node> nodes = new ArrayList<Node>();
		Random generator = new Random();
		int numberOfNodes = generator.nextInt(500)+501;
		for(int i = 0; i < numberOfNodes; i++){
			nodes.add(new Node(i));
		}
		
		//add the nodes to their respective parents
		for(int i = 1; i < numberOfNodes; i++){
			nodes.get(calculateSurrogate(i)).addChild(nodes.get(i));
			
			//test a random node
			int randomNode = generator.nextInt(i);
			assertTrue(isNodeDomainCorrect(nodes.get(randomNode), i+1));
		}
	}
	
	/**
	 * Finds the surrogate for a given webId. A surrogate is defined here as the number 
	 * who's bit representation has a zero where the id's leading 1 bit is, for example:
	 * if the id is three the bit representation is 11 so the surrogate is 01.
	 * @param id The id to find a surrogate of.
	 * @return The id's surrogate.
	 * @pre An integer greater than or equal to one.
	 * @post result = id's surrogate
	 */
	private int calculateSurrogate(int id){
		int temp = id;
		int digitsToLeadingOneBit = 0;
		while(temp > 0){
			digitsToLeadingOneBit++;
			temp >>>= 1;
		}
		
		int highestOneBitMask = 1;
		for(; digitsToLeadingOneBit > 1; digitsToLeadingOneBit--){
			highestOneBitMask <<= 1;
		}
		
		return (id^highestOneBitMask);
	}
}
