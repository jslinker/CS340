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
		
		HyPeerWebDatabase.initHyPeerWebDatabase("test_db");
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
}
