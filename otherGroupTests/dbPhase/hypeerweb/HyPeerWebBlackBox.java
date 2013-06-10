package dbPhase.hypeerweb;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HyPeerWebBlackBox {
	
	private static HyPeerWeb web;
	private static Random rand;
	private NodeCore node0, node1, node2, node3;
	
	@BeforeClass
	public static void setupClass(){
		web = HyPeerWeb.getSingleton();
	}
	
	@Before
	public void setup(){
		web.clear();
		node0 = new NodeCore(0);
		node1 = new NodeCore(1);
		node2 = new NodeCore(2);
		node3 = new NodeCore(3);
	}
	
	@Test
	public void testAddNode(){
		web.addToHyPeerWeb(new NodeCore(0), NodeCore.getNullNode());
		checkWeb(1);
		
		web.addToHyPeerWeb(new NodeCore(1), web.getNode(0));
		checkWeb(2);
		
		web.addToHyPeerWeb(new NodeCore(2), null);
		checkWeb(3);
		
		makeWeb(8);
		checkWeb(8);
		
		makeWeb(31);
		checkWeb(31);
	}
	
	@Test
	public void testRemoveNode(){
		int size = 16;
		makeWeb(size);
		for(int i = size - 1; i > 0; i--){
			web.deleteFromHyPeerWeb(web.getNode(i), web.getNode(0));
			checkWeb(i);
		}
		
		makeWeb(1);
		web.deleteFromHyPeerWeb(web.getNode(1), web.getNode(1));
	}
	
	@Test
	public void testContains(){
		web.addToHyPeerWeb(node0, null);
		assertTrue(web.contains(node0));
		
		web.addToHyPeerWeb(node1, null);
		web.addToHyPeerWeb(node2, null);
		web.addToHyPeerWeb(node3, null);
		
		assertTrue(web.contains(node1));
		assertTrue(web.contains(node2));
		assertTrue(web.contains(node3));
		
		web.clear();
		assertFalse(web.contains(node0));
	}
	
	@Test
	public void testReload(){
		int size = 16;
		makeWeb(size);
		checkWeb(size);
		
		web.saveToDatabase();
		web.clear();
		web.reload();
		checkWeb(size);
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
