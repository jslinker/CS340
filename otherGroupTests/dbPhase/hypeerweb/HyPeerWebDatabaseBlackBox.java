package dbPhase.hypeerweb;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HyPeerWebDatabaseBlackBox {
	
	private static HyPeerWebDatabase db;
	private static HyPeerWeb web;
	
	@BeforeClass
	public static void setupClass(){
		db = HyPeerWebDatabase.getSingleton();
		web = HyPeerWeb.getSingleton();
	}
	
	@Before
	public void setup(){
		db.startTransaction();
		db.dropTables();
		db.createTables();
	}
	
	@After
	public void tearDown(){
		db.endTransaction(true);
	}
	
	@Test
	public void testCreateNode(){
		int size = 20;
		makeWeb(size);
		
		for(int i = 0; i < size; i++){
			Node node = web.getNode(i);
			db.createNode(node);
			assertEquals(new ExpectedResult(size, i), db.getNode(i));  //height is not being store in database
		}		
	}
	
	@Test
	public void testGetAllNodes(){
		int size = 8;
		makeWeb(size);
		
		for(int i = 0; i < size; i++){
			db.createNode(web.getNode(i));
		}
		
		ArrayList<Node> nodes = db.getAllNodes();
		for(Node n : nodes){
			assertTrue(web.getNode(n.getWebIdValue()) != null);
			assertEquals(new ExpectedResult(size, n.getWebIdValue()), n.constructSimplifiedNodeDomain());
		}
	}
	
	private void makeWeb(int size){
		web.clear();
		NodeCore node0 = new NodeCore(0);
		web.addToHyPeerWeb(node0, NodeCore.getNullNode());
		
		for(int i = 1; i < size; i++){
			web.addToHyPeerWeb(new NodeCore(i), node0);
		}
	}
	
	private void checkWeb(int size){
		for(int i = 0; i < size; i++){
			ExpectedResult result = new ExpectedResult(size, i);
			assertEquals(result, web.getNode(i).constructSimplifiedNodeDomain());
		}
	}
}
