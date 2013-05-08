package hypeerweb;

import junit.framework.TestCase;

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
}
