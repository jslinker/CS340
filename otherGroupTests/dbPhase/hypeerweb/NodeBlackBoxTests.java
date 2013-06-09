package dbPhase.hypeerweb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import junit.framework.TestCase;

public class NodeBlackBoxTests extends TestCase{

	public void testConstructSimplifiedNodeDomain(){
		//Test default values.
		int webId = 5264;
		Node aNode = new NodeCore(webId);
		SimplifiedNodeDomain aNodeDomain = aNode.constructSimplifiedNodeDomain();
		assertTrue(aNodeDomain != null);
		assertTrue(aNodeDomain.getWebId() == webId);
		assertTrue(aNodeDomain.getFold() == webId);
		assertTrue(aNodeDomain.getSurrogateFold() == -1);
		assertTrue(aNodeDomain.getInverseSurrogateFold() == -1);
		assertTrue(aNodeDomain.getDownPointers().size() == 0);
		assertTrue(aNodeDomain.getUpPointers().size() == 0);
		assertTrue(aNodeDomain.getNeighbors().size() == 0);
		
		//Test setting some values.
		int someWebId = 3;
		Node someNode = new NodeCore(someWebId);
		aNode.setFold(someNode);
		aNode.setSurrogateFold(someNode);
		aNode.setInverseSurrogateFold(someNode);
		aNode.addDownPointer(someNode);
		aNode.addUpPointer(someNode);
		aNode.addNeighbor(someNode);
		aNodeDomain = aNode.constructSimplifiedNodeDomain();
		assertTrue(aNodeDomain != null);
		assertTrue(aNodeDomain.getWebId() == webId);
		assertTrue(aNodeDomain.getFold() == someWebId);
		assertTrue(aNodeDomain.getSurrogateFold() == someWebId);
		assertTrue(aNodeDomain.getInverseSurrogateFold() == someWebId);
		assertEquals(aNodeDomain.getDownPointers().size(), 1);
		assertTrue(aNodeDomain.getUpPointers().size() == 1);
		assertTrue(aNodeDomain.getNeighbors().size() == 1);
		assertTrue(aNodeDomain.getDownPointers().contains(someWebId));
		assertTrue(aNodeDomain.getUpPointers().contains(someWebId));
		assertTrue(aNodeDomain.getNeighbors().contains(someWebId));
	}
	
	public void testConstructor(){
		int webId = 5264;
		Node aNode = new NodeCore(webId);
		SimplifiedNodeDomain aNodeDomain = aNode.constructSimplifiedNodeDomain();
		assertTrue(aNodeDomain.getWebId() == webId);
		assertTrue(aNodeDomain.getFold() == webId);
		assertTrue(aNodeDomain.getSurrogateFold() == -1);
		assertTrue(aNodeDomain.getInverseSurrogateFold() == -1);
		assertTrue(aNodeDomain.getDownPointers().size() == 0);
		assertTrue(aNodeDomain.getUpPointers().size() == 0);
		assertTrue(aNodeDomain.getNeighbors().size() == 0);
	}
	
	/**
	 * Test addToHyPeerWeb. Partitions include invalid (null, NULL_NODE), 
	 * valid non-cap nodes, and valid cap nodes.
	 */
	public void testAddToHyPeerWeb(){
		//Tests boundary conditions (webs of size 1, 2, 3 ...) and
		//tests a middle value in partitioning.
		//Additionally, multiple cap nodes are inserted.
		ArrayList<Node> nodes = new ArrayList<Node>();
		Random generator = new Random();
		int size = 139;
		
		nodes.add(new NodeCore(0));
		
		for(int i = 1; i < size; i++){
			nodes.add(new NodeCore(generator.nextInt(10000)));
		}
		
		ExpectedResult expectedNodeZero = new ExpectedResult(1, 0);
		SimplifiedNodeDomain actualNodeZero = nodes.get(0).constructSimplifiedNodeDomain();
		assertTrue(actualNodeZero.equals(expectedNodeZero));
		
		for(int i = 1; i < nodes.size(); i++){
			Node newNode = nodes.get(i);
			int randomStartNodeWebId = generator.nextInt(i);
			Node startNode = nodes.get(randomStartNodeWebId);
			startNode.addToHyPeerWeb(newNode);
			
			//tests the HyPeerWeb invariant (all connections are correct)
			for(int j = 0; j <= i; j++){
				ExpectedResult expectedNodeDomain = new ExpectedResult(i+1, j);
				SimplifiedNodeDomain actualNodeDomain = nodes.get(j).constructSimplifiedNodeDomain();
				assertEquals(actualNodeDomain,expectedNodeDomain);
			}
		}
	}
	
	
	public void testAddDownPointer(){
		//Tests a valid case.
		Node existingNode = new NodeCore(2345);
		int newNodeWebId = 256;
		Node newNode = new NodeCore(newNodeWebId);
		
		existingNode.addDownPointer(newNode);
		
		SimplifiedNodeDomain existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		HashSet<Integer> downPointers = existingNodeDomain.getDownPointers();
		assertTrue(downPointers.size() == 1);
		assertTrue(downPointers.contains(newNodeWebId));
	}
	
	public void testAddUpPointer(){
		//Tests a valid case.
		Node existingNode = new NodeCore(2345);
		int newNodeWebId = 256;
		Node newNode = new NodeCore(newNodeWebId);
		
		existingNode.addUpPointer(newNode);
		
		SimplifiedNodeDomain existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		HashSet<Integer> upPointers = existingNodeDomain.getUpPointers();
		assertTrue(upPointers.size() == 1);
		assertTrue(upPointers.contains(newNodeWebId));
	}
	
	public void testAddNeighbor(){
		//Tests a valid case.
		Node existingNode = new NodeCore(2345);
		int newNodeWebId = 256;
		Node newNode = new NodeCore(newNodeWebId);
		
		existingNode.addNeighbor(newNode);
		
		SimplifiedNodeDomain existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		HashSet<Integer> neighbors = existingNodeDomain.getNeighbors();
		assertTrue(neighbors.size() == 1);
		assertTrue(neighbors.contains(newNodeWebId));
	}
	
	public void testChangeFold(){
		//Tests a valid case.
		Node existingNode = new NodeCore(2345);
		int newFoldWebId = 256;
		Node newFold = new NodeCore(newFoldWebId);
		
		existingNode.setFold(newFold);
		
		SimplifiedNodeDomain existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		assertTrue(existingNodeDomain.getFold() == newFoldWebId);
		
		//Tests valid special case (changing to NULL_NODE)
		existingNode.setFold(NodeCore.getNullNode());
		existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		assertTrue(existingNodeDomain.getFold() == -1);
	}
	
	public void testSetFold(){
		//Tests a valid case.
		Node existingNode = new NodeCore(2345);
		int newFoldWebId = 256;
		Node newFold = new NodeCore(newFoldWebId);
		
		existingNode.setFold(newFold);
		
		SimplifiedNodeDomain existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		assertTrue(existingNodeDomain.getFold() == newFoldWebId);
		
		//Tests valid special case (setting to NULL_NODE)
		existingNode.setFold(NodeCore.getNullNode());
		existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		System.out.println("FOLD " +existingNodeDomain.getFold());
		assertTrue(existingNodeDomain.getFold() == -1);
	}
	
	public void testSetSurrogateFold(){
		//Tests a valid case.
		Node existingNode = new NodeCore(2345);
		int newSFWebId = 256;
		Node newSF = new NodeCore(newSFWebId);
		
		existingNode.setSurrogateFold(newSF);
		
		SimplifiedNodeDomain existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		assertTrue(existingNodeDomain.getSurrogateFold() == newSFWebId);
		
		//Tests valid special case (setting to NULL_NODE)
		existingNode.setSurrogateFold(NodeCore.getNullNode());
		existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		assertTrue(existingNodeDomain.getSurrogateFold() == -1);
	}
	
	public void testSetInverseSurrogateFold(){
		//Tests a valid case.
		Node existingNode = new NodeCore(2345);
		int newISFWebId = 256;
		Node newISF = new NodeCore(newISFWebId);
		
		existingNode.setInverseSurrogateFold(newISF);
		
		SimplifiedNodeDomain existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		assertTrue(existingNodeDomain.getInverseSurrogateFold() == newISFWebId);
		
		//Tests valid special case (setting to NULL_NODE)
		existingNode.setInverseSurrogateFold(NodeCore.getNullNode());
		existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		assertTrue(existingNodeDomain.getInverseSurrogateFold() == -1);
	}
	
	public void testSetWebId(){
		Node aNode = new NodeCore(0);
		SimplifiedNodeDomain aNodeDomain = aNode.constructSimplifiedNodeDomain();
		assertTrue(aNodeDomain.getWebId() == 0);
		
		int newWebIdValue = 2345;
		WebId newWebId = new WebId(newWebIdValue);
		aNode.setWebId(newWebId);
		aNodeDomain = aNode.constructSimplifiedNodeDomain();
		assertTrue(aNodeDomain.getWebId() == newWebIdValue);
	}
	
	public void testRemoveDownPointer(){
		//Tests a valid case.
		Node existingNode = new NodeCore(2345);
		SimplifiedNodeDomain existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		assertTrue(existingNodeDomain.getDownPointers().size() == 0);
		
		int newNodeWebId = 234;
		Node newNode = new NodeCore(newNodeWebId);
		
		existingNode.addDownPointer(newNode);
		existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		assertTrue(existingNodeDomain.getDownPointers().size() == 1);
		assertTrue(existingNodeDomain.getDownPointers().contains(newNodeWebId));
		
		existingNode.removeDownPointer(newNode);
		existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		assertTrue(existingNodeDomain.getDownPointers().size() == 0);
	}
	
	public void testRemoveUpPointer(){
		//Tests a valid case.
		Node existingNode = new NodeCore(2345);
		SimplifiedNodeDomain existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		assertTrue(existingNodeDomain.getUpPointers().size() == 0);
		
		int newNodeWebId = 234;
		Node newNode = new NodeCore(newNodeWebId);
		
		existingNode.addUpPointer(newNode);
		existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		assertTrue(existingNodeDomain.getUpPointers().size() == 1);
		assertTrue(existingNodeDomain.getUpPointers().contains(newNodeWebId));
		
		existingNode.removeUpPointer(newNode);
		existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		assertTrue(existingNodeDomain.getUpPointers().size() == 0);
	}
	
	public void testRemoveNeighbor(){
		//Tests a valid case.
		Node existingNode = new NodeCore(2345);
		SimplifiedNodeDomain existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		assertTrue(existingNodeDomain.getNeighbors().size() == 0);
		
		int newNodeWebId = 234;
		Node newNode = new NodeCore(newNodeWebId);
		
		existingNode.addNeighbor(newNode);
		existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		assertTrue(existingNodeDomain.getNeighbors().size() == 1);
		assertTrue(existingNodeDomain.getNeighbors().contains(newNodeWebId));
		
		existingNode.removeNeighbor(newNode);
		existingNodeDomain = existingNode.constructSimplifiedNodeDomain();
		assertTrue(existingNodeDomain.getNeighbors().size() == 0);
	}
	
	public void testGetContents(){
		Node aNode = new NodeCore(0);
		assertTrue(aNode.getContents() != null);
	}
}
