package node;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import org.junit.*;
import org.junit.runners.JUnit4;

import hypeerweb.HyPeerWeb;

public class NZWhiteBoxConnectionsTests {
	
	private static Node[] nodes;
	private Node testNode;
	private HyPeerWeb web;
	
	@BeforeClass
	public static void setupClass(){
		nodes = new Node[5];
		nodes[0] = new Node(0);
		nodes[1] = new Node(1);
		nodes[2] = new Node(2);
		nodes[3] = new Node(3);
		nodes[4] = new Node(4);
	}
	
	@Before
	public void setup(){
		web = HyPeerWeb.getSingleton();
		web.clear();
		web.addToHyPeerWeb(nodes[0], Node.NULL_NODE);
		web.addToHyPeerWeb(nodes[1], nodes[0]);
		web.addToHyPeerWeb(nodes[2], nodes[0]);
		web.addToHyPeerWeb(nodes[3], nodes[0]);
		web.addToHyPeerWeb(nodes[4], nodes[0]);
	}
	
	@Test
	public void testHasFold(){
		assertTrue(nodes[3].getConnections().hasFold());
		assertFalse(nodes[0].getConnections().hasFold());
	}
	
	@Test
	public void testHashCode(){
		HashSet<Connections> connections = new HashSet<Connections>();
		connections.add(nodes[0].getConnections());
		assertTrue(connections.contains(nodes[0].getConnections()));

		web.removeFromHyPeerWeb(nodes[1]);
		assertFalse(connections.contains(nodes[0].getConnections()));
	}
	
	@Test
	public void testEquals(){
		Connections node0Con = nodes[0].getConnections().deepCopy();
		assertTrue(node0Con.equals(node0Con));
		assertFalse(node0Con.equals(null));
		assertFalse(node0Con.equals(nodes[0]));
		
		node0Con.setDownPointers(null);
		assertFalse(node0Con.equals(nodes[1].getConnections()));  //test null downpointers
		assertFalse(nodes[4].getConnections().equals(nodes[3].getConnections()));//test non equal downpointers
		
		node0Con = nodes[0].getConnections().deepCopy();
		assertTrue(node0Con.equals(nodes[0].getConnections()));
		Connections node4Con = nodes[4].getConnections().deepCopy();		
		node4Con.setFold(Node.NULL_NODE);
		assertFalse(node4Con.equals(nodes[4].getConnections()));
	}
	
	@Test
	public void testToString(){
		Connections node2Con = nodes[2].getConnections().deepCopy();
		assertEquals(node2Con.toString(), nodes[2].getConnections().toString());
		Connections node4Con = nodes[4].getConnections().deepCopy();
		assertEquals(node4Con.toString(), nodes[4].getConnections().toString());
		node2Con.setLowerNeighbors(null);
	}
	
	@Test
	public void testNullCollections(){
		Connections node0Con = nodes[0].getConnections();
		node0Con.setDownPointers(null);
		node0Con.setUpPointers(null);
		node0Con.setUpperNeighbors(null);
		node0Con.setLowerNeighbors(null);
		
		assertEquals(node0Con.getUpPointerCount(), 0);
		assertEquals(node0Con.getDownPointerCount(), 0);
		assertEquals(node0Con.getLowerNeighbors(), null);
		assertEquals(node0Con.getDownPointers(), null);
		assertEquals(node0Con.getUpPointers(), null);
		assertEquals(node0Con.getUpperNeighbors(), null);
		assertEquals(node0Con.getSmallestChildlessNeighbor(), Node.NULL_NODE);
		assertEquals(node0Con.getSmallestDownPointer(), Node.NULL_NODE);
	}
	
	@Test
	public void testGetSmallestChildlessNeighbor(){
		Node smallestChildlessNeighbor = nodes[3].getConnections().getSmallestChildlessNeighbor(); //loop runs multiple times
		assertEquals(nodes[1],smallestChildlessNeighbor);
		
		web.removeFromHyPeerWeb(nodes[4]);
		smallestChildlessNeighbor = nodes[3].getConnections().getSmallestChildlessNeighbor(); //no node should be found
		assertEquals(Node.NULL_NODE, smallestChildlessNeighbor);
		
		assertEquals(Node.NULL_NODE, nodes[0].getConnections().getSmallestChildlessNeighbor()); //loop runs 0 times
		assertEquals(Node.NULL_NODE, nodes[1].getConnections().getSmallestChildlessNeighbor()); //loop runs 1 time
	}
	
	@Test
	public void testgetNeighborByWebId(){
		try{
			nodes[0].getConnections().getNeighborByWebId(-100);
			fail();
		} catch(AssertionError e){
			
		}
		
		assertEquals(nodes[1].getConnections().getNeighborByWebId(nodes[0].getWebIdValue()), nodes[0]); //lower neighbor if statement
		assertEquals(nodes[2].getConnections().getNeighborByWebId(nodes[3].getWebIdValue()), nodes[3]); //upper neighbor if statement
		assertEquals(nodes[0].getConnections().getNeighborByWebId(7), Node.NULL_NODE);					//none found
	}
	
	@Test 
	public void testHasSurrogateFold(){
		assertFalse(nodes[4].getConnections().hasSurrogateFold());
		assertTrue(nodes[0].getConnections().hasSurrogateFold());
	}
	
	@Test
	public void testHasInverseSurrogateFold(){
		assertFalse(nodes[4].getConnections().hasInverseSurrogateFold());
		assertTrue(nodes[3].getConnections().hasInverseSurrogateFold());
	}
	
	@Test
	public void testGetNextClosestNeighbor(){
		Connections nodeConnections = nodes[0].getConnections();
		
		try{
			nodeConnections.getNextClosestNeighbor(nodes[0].getWebIdValue(), nodes[0].getWebIdValue());
			fail();
		} catch(AssertionError e){
			//woot
		}
		
		Node nextClosest = nodeConnections.getNextClosestNeighbor(nodes[0].getWebIdValue(), nodes[3].getWebIdValue());
		assertEquals(nextClosest, nodes[1]);  //should be found in upper neighbors
		
		nextClosest = nodeConnections.getNextClosestNeighbor(nodes[0].getWebIdValue(), nodes[2].getWebIdValue());
		assertEquals(nextClosest, nodes[2]);  
		
		nodeConnections = nodes[1].getConnections();
		nextClosest = nodeConnections.getNextClosestNeighbor(nodes[1].getWebIdValue(), nodes[4].getWebIdValue());
		assertEquals(nextClosest, nodes[0]);  //should be found in lower neighbors
		
		nodeConnections = nodes[4].getConnections();
		nextClosest = nodeConnections.getNextClosestNeighbor(nodes[4].getWebIdValue(), 5);
		assertEquals(nextClosest, Node.NULL_NODE);
	}
}
