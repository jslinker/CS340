package dbPhase.hypeerweb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import hypeerweb.HyPeerWeb;

import java.awt.List;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HyPeerWebDatabaseTests {
	
	private static NodeCore node;
	private static NodeCore neighbor, up, down;
	private static SimplifiedNodeDomain other;
	private static HyPeerWebDatabase db;
	
	private static final int TEST_HEIGHT = 1, TEST_ID = 1;
	private static final int NEIGHBOR_HEIGHT = 2, NEIGHBOR_ID = 2;
	
	@BeforeClass
	public static void setupClass(){
		node = new NodeCore(TEST_ID, TEST_HEIGHT);
		neighbor = new NodeCore(NEIGHBOR_ID, NEIGHBOR_HEIGHT);
		down = new NodeCore(NEIGHBOR_ID + 1, NEIGHBOR_HEIGHT + 1);
		up = new NodeCore(NEIGHBOR_ID + 2, NEIGHBOR_HEIGHT + 2);
		
		node.addNeighbor(neighbor);
		node.addUpPointer(up);
		node.addDownPointer(down);

		db = HyPeerWebDatabase.getSingleton();
		db.startTransaction();
		db.createTables();
		db.clear();
	}
	
	@Before
	public void setup(){
		db = HyPeerWebDatabase.getSingleton();
		db.startTransaction();
		db.dropTables();
		db.createTables();
		db.clear();
	}
	
	@After
	public void tearDown(){
		db.endTransaction(true);
	}

	/**
	 * Simple case that inserts a node into the database, retrieves it, and checks
	 * that the retrieved values match what should have been put in.
	 */
	@Test
	public void testAddRetrieve(){
		db.createNode(node);
		db.getNode(node.getWebIdValue());

		SimplifiedNodeDomain domain = db.getNode(node.getWebIdValue());
		assertEquals(domain, node.constructSimplifiedNodeDomain());
	}

	@Test
	public void testStoreAndGet(){
		// Build a node with random data
		NodeCore node1 = new NodeCore(10);

		node1.setFold(new NodeCore(47));
		node1.setSurrogateFold(new NodeCore(25));
		node1.setInverseSurrogateFold(new NodeCore(89));
		
		node1.addNeighbor(new NodeCore(747));
		node1.addNeighbor(new NodeCore(343));
		
		node1.addUpPointer(NodeCore.getNullNode());
		node1.addDownPointer(new NodeCore(17));

		// Store the node
		db.createNode(node1);

		// Retrieve the node
		dbPhase.hypeerweb.SimplifiedNodeDomain result = db.getNode(node1.getWebIdValue());

		// Expect to get this node back
		SimplifiedNodeDomain confirm = node1.constructSimplifiedNodeDomain();

//		System.out.println("from db: "+ result);
//		System.out.println("from node: "+ confirm);

		assertEquals(result,confirm);

		// Change some information
		node1.setFold(new NodeCore(3090));
		node1.setSurrogateFold(NodeCore.getNullNode());
		// Store the node again...it should update the node already in the database
		db.createNode(node1);

		// Confirm changes took place
		result = db.getNode(node1.getWebIdValue());
		confirm = node1.constructSimplifiedNodeDomain();
		assertTrue(result.equals(confirm));
	}
	
	/**
	 * A simple White Box test that checks all the error scenarios with the database
	 */
	@Test
	public void testEdgeCases(){
		ArrayList<Node> nodes = db.getAllNodes();
		ArrayList<Integer> webIdsList = new ArrayList<Integer>();
		for(Node n : nodes){
			webIdsList.add(n.getWebIdValue());
		}
		
		assertTrue("We should have a non null list on WebId's", webIdsList != null);
		
		assertTrue("We should have a valid hypeerweb singleton", db != null);
		 
		db.dropTables();
		db.createTables();
		nodes = db.getAllNodes();
		System.out.println(nodes);
		webIdsList = new ArrayList<Integer>();
		for(Node n : nodes){
			webIdsList.add(n.getWebIdValue());
		}
		assertTrue("We should have no web id's after the clear operation", webIdsList.size() == 0);
	}
}