package hypeerweb;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.BeforeClass;
import org.junit.Test;

public class HyPeerWebDatabaseTests {
	
	private static Node node;
	private static Node neighbor, up, down;
	private static SimplifiedNodeDomain other;
	private static HyPeerWebDatabase db;
	
	private static final int TEST_HEIGHT = 1, TEST_ID = 1;
	private static final int NEIGHBOR_HEIGHT = 2, NEIGHBOR_ID = 2;
	
	@BeforeClass
	public static void setupClass(){
		node = new Node(TEST_ID, TEST_HEIGHT);
		neighbor = new Node(NEIGHBOR_ID, NEIGHBOR_HEIGHT);
		down = new Node(NEIGHBOR_ID + 1, NEIGHBOR_HEIGHT + 1);
		up = new Node(NEIGHBOR_ID + 2, NEIGHBOR_HEIGHT + 2);
		
		node.addNeighbor(neighbor);
		node.addUpPointer(up);
		node.addDownPointer(down);
		
		HyPeerWebDatabase.initHyPeerWebDatabase("test_db2.sqlite");
		db = HyPeerWebDatabase.getSingleton();
	}
	
	/**
	 * Simple case that inserts a node into the database, retrieves it, and checks
	 * that the retrieved values match what should have been put in.
	 */
	@Test
	public void testAddRetrieve(){
		db.storeNode(node);
		other = db.getNode(TEST_ID);
		
		Iterator<Integer> iter = other.getNeighbors().iterator();
		assertEquals(neighbor.getWebId().getValue(), iter.next().intValue());
		
		iter = other.getUpPointers().iterator();
		assertEquals(up.getWebId().getValue(), iter.next().intValue());
		
		iter = other.getDownPointers().iterator();
		assertEquals(down.getWebId().getValue(), iter.next().intValue());
	}
	
	@Test
	public void testStoreAndGet(){
		Node node1 = new Node(313, 20);
		node1.setFold(new Node(314));
		node1.setSurrogateFold(new Node(315));
		node1.setInverseSurrogateFold(new Node(316));
		node1.addNeighbor(new Node(312));
		node1.addUpPointer(new Node(1));
		node1.addDownPointer(new Node(33));
		node1.addDownPointer(new Node(34));
		
		db.storeNode(node1);
		SimplifiedNodeDomain result = db.getNode(313);
		SimplifiedNodeDomain simpleNode1 = node1.constructSimplifiedNodeDomain();
		System.out.println("from db: "+result);
		System.out.println("from node: "+simpleNode1);
		assertTrue(result.equals(simpleNode1));
		
		node1.setFold(new Node(3090));
		node1.setSurrogateFold(Node.NULL_NODE);
		db.storeNode(node1);
		result = db.getNode(313);
		simpleNode1 = node1.constructSimplifiedNodeDomain();
		assertTrue(result.equals(simpleNode1));
	}
}
