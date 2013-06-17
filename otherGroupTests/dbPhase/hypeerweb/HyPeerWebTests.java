package dbPhase.hypeerweb;

import java.util.Random;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit Test cases for the HyPeerWeb class
 * @author Jason Robertson
 */
public class HyPeerWebTests extends TestCase{
	
	private HyPeerWeb web = HyPeerWeb.getSingleton();
	private NodeCore nodes[] = new NodeCore[8];
	
	@Before
	public void setUp() {
		web = HyPeerWeb.getSingleton();
		
		nodes[0] = new NodeCore(0,0);
		nodes[1] = new NodeCore(1,1);
		nodes[2] = new NodeCore(2,2);
		nodes[3] = new NodeCore(3,2);
		nodes[4] = new NodeCore(4,3);
		nodes[5] = new NodeCore(5,3);
		nodes[6] = new NodeCore(6,3);
		nodes[7] = new NodeCore(7,3);
		
		nodes[0].addNeighbor(nodes[1]);
		nodes[0].addNeighbor(nodes[2]);
		nodes[0].addNeighbor(nodes[4]);
		
		nodes[1].addNeighbor(nodes[0]);
		nodes[1].addNeighbor(nodes[3]);
		nodes[1].addNeighbor(nodes[5]);
		
		nodes[2].addNeighbor(nodes[0]);
		nodes[2].addNeighbor(nodes[3]);
		nodes[2].addNeighbor(nodes[6]);
		
		nodes[3].addNeighbor(nodes[1]);
		nodes[3].addNeighbor(nodes[2]);
		nodes[3].addNeighbor(nodes[7]);
		
		nodes[4].addNeighbor(nodes[0]);
		nodes[4].addNeighbor(nodes[5]);
		nodes[4].addNeighbor(nodes[6]);
		
		nodes[5].addNeighbor(nodes[1]);
		nodes[5].addNeighbor(nodes[4]);
		nodes[5].addNeighbor(nodes[7]);
		
		nodes[6].addNeighbor(nodes[4]);
		nodes[6].addNeighbor(nodes[7]);
		nodes[6].addNeighbor(nodes[2]);
		
		nodes[7].addNeighbor(nodes[3]);
		nodes[7].addNeighbor(nodes[6]);
		nodes[7].addNeighbor(nodes[5]);
	}
	
	@After
	public void tearDown() {
		web.clear();
	}
	
	@Test
	public void testGetSingleton(){
		assertNotNull(HyPeerWeb.getSingleton());
	}
	
	@Test
	public void testSize(){
		assertEquals(0, web.size());
	}
	
	@Test
	public void testAddNode(){
		web.addNode(nodes[0]);
		assertEquals(1, web.size());
	}
	
	@Test
	public void testGetNode(){
		
		web.addNode(nodes[0]);
		web.addNode(nodes[1]);
		
		assertTrue(nodes[0].equals(web.getNode(0)));
		assertTrue(nodes[1].equals(web.getNode(1)));
	}
	
	@Test
	public void testClear(){
		
		assertEquals(0,web.size());
		
		web.addNode(nodes[0]);
		assertEquals(1,web.size());
		
		web.addNode(nodes[1]);
		assertEquals(2,web.size());

		web.addNode(nodes[2]);
		assertEquals(3,web.size());
		
		web.clear();
		assertEquals(0,web.size());
	}
	
	@Test
	public void testContains(){

		web.addNode(nodes[0]);
		assertTrue(web.contains(nodes[0]));
	}
	
	@Test
	public void testGetHyPeerWebDatabase(){
		assertNotNull(web.getHyPeerWebDatabase());
	}
	
	@Test
	public void testReload(){
		int size = 15;
		
		for(int i = 0; i < size; i++){
			web.addToHyPeerWeb(new NodeCore(i), NodeCore.getNullNode());
		}
		
		for(int i = 0; i < size; i++){
			assertEquals(web.getNode(i).constructSimplifiedNodeDomain(), new ExpectedResult(size, i));
		}
		
		assertEquals(size,web.size());
		
		web.saveToDatabase();

		web.clear();
		assertEquals(0,web.size());
		
		web.reload();
		assertEquals(size,web.size());
		for(int i = 0; i < size; i++){
			assertEquals(web.getNode(i).constructSimplifiedNodeDomain(), new ExpectedResult(size, i));
		}
	}
	
	@Test
	public void testReloadWithName(){
		
		final String dbName = "testing.db";
//		HyPeerWebDatabase db = web.getHyPeerWebDatabase();
//		db.startTransaction(dbName);
//		db.dropTables();
//		db.createTables();
		int size = 16;
		for(int i = 0; i < size; i++){
			web.addToHyPeerWeb(new NodeCore(i), NodeCore.getNullNode());
		}
		
		assertEquals(size,web.size());

		web.saveToDatabase();
		
		web.clear();
		assertEquals(0,web.size());
		
		web.reload(dbName);
		assertEquals(nodes.length,web.size());
	}
	
	@Test
	public void testRemoveNode(){
		assertEquals(0,web.size());
		
		web.addNode(nodes[0]);
		assertEquals(1,web.size());
		
		web.removeNode(nodes[0]);
		assertEquals(0,web.size());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testSaveToDatabase(){
		web.addToHyPeerWeb(new NodeCore(0), NodeCore.getNullNode());
		web.addToHyPeerWeb(new NodeCore(1), NodeCore.getNullNode());
		web.addToHyPeerWeb(new NodeCore(2), NodeCore.getNullNode());
		
		web.saveToDatabase();
		web.getHyPeerWebDatabase().getSingleton().startTransaction();
		assertEquals(3, web.getHyPeerWebDatabase().getSingleton().getAllNodes().size());
		web.getHyPeerWebDatabase().getSingleton().endTransaction(true);
	}
	
	@Test
	public void testAddToHyPeerWeb(){
		HyPeerWeb web = HyPeerWeb.getSingleton();
		web.clear();
		
		Random generator = new Random();
		int numberOfNodes = generator.nextInt(300)+231;
		
		Node startNode = null;
		Node newNode = null;
		for(int i = 0; i < numberOfNodes; i++){
			newNode = new NodeCore(generator.nextInt(10000));
			
			if(i == 0){
				startNode = NodeCore.getNullNode();
			}
			else{
				startNode = web.getNode(generator.nextInt(i));
			}
			
			web.addToHyPeerWeb(newNode, startNode);
			
			assertTrue("\nExpected WebId: " + i + "\n"+
						"Actual: " + web.getNode(i).getWebIdValue(), 
						web.getNode(i).getWebIdValue() == i);
			
			for(int j = 0; j <= i; j++){
				assertTrue(NodeTests.isNodeDomainCorrect(web.getNode(j), i+1));
			}
		}
	}
	
	@Test
	public void testRemoveFromHyPeerWeb(){
		//HyPeerWeb web = HyPeerWeb.getSingleton();
		web.clear();
		
		final int TEST_SIZE = 32;
		
		for(int i = 0; i < TEST_SIZE; i++){
			web.addToHyPeerWeb(new NodeCore(i), NodeCore.getNullNode());
		}
		
		int max = TEST_SIZE - 1;
		
		while(max > 1){
			for(int i = max; i > 0; i--){
				SimplifiedNodeDomain expected = new ExpectedResult(max + 1, i);
				assertEquals(expected, web.getNode(i).constructSimplifiedNodeDomain());
			}
		
			web.deleteFromHyPeerWeb(web.getNode(max),web.getNode(0));
			max--;
		}
	}
	
	@Test
	public void testAddToHyPeerWebExp(){
		HyPeerWeb web = HyPeerWeb.getSingleton();
        web.clear();
        
        final int HYPEERWEB_SIZE = 32;
        
        for (int size = 1; size <= HYPEERWEB_SIZE; size++) {
            web.clear();
            NodeCore node0 = new NodeCore(0);
            web.addToHyPeerWeb(node0, null);
            Node firstNode = web.getNode(0);
            SimplifiedNodeDomain simplifiedNodeDomain = firstNode.constructSimplifiedNodeDomain();
            ExpectedResult expectedResult = new ExpectedResult(1, 0);

            if (!simplifiedNodeDomain.equals(expectedResult)) {
                System.out.println("Size: "+size+"\nActual Node: "+simplifiedNodeDomain.toString()+"\n"+
                					"Expected Node: "+expectedResult.toString());
            }
            
            for (int startNodeId = 0; startNodeId < size - 1; startNodeId++) {
                web.clear();
                NodeCore nodeZero = new NodeCore(0);
                web.addToHyPeerWeb(nodeZero, null);

                for (int i = 1; i < size-1; i++) {
                	NodeCore node = new NodeCore(0);
                    web.addToHyPeerWeb(node, nodeZero);
                }
                
                NodeCore node = new NodeCore(0);
                Node startNode = web.getNode(startNodeId);
                web.addToHyPeerWeb(node, startNode);
                
                for (int i = 0; i < size; i++) {
                	
                    Node nodei = web.getNode(i);
                    simplifiedNodeDomain = nodei.constructSimplifiedNodeDomain();
                    expectedResult = new ExpectedResult(size, i);

                    if (!simplifiedNodeDomain.equals(expectedResult)) {
                    	System.out.println("Error");
                    }
                }
            }
        }
	}
	
	@Test
	public void testCloseReload(){
		web.clear();
		int size = 14;
		HyPeerWebDatabase db = web.getHyPeerWebDatabase();
		db.startTransaction();
		db.dropTables();
		db.createTables();
		assertEquals(web.getHyPeerWebDatabase().getAllNodes().size(), 0);
		db.endTransaction(true);
		makeWeb(size);
		for(int i = 0; i < size; i++){
			assertEquals(new ExpectedResult(size, i), web.getNode(i).constructSimplifiedNodeDomain());
		}
		
		web.clear();
		web.reload();
		assertEquals(size,web.getHyPeerWebDatabase().getAllNodes().size());
		for(int i = 0; i < size; i++){
			assertEquals(new ExpectedResult(size, i), web.getNode(i).constructSimplifiedNodeDomain());
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
}