package node;

import junit.framework.TestCase;
import node.roles.Neighbor;

/**
 * Finishes testing the NodeState class.
 * @author Craig Jacobson
 *
 */
public class NodeStateTests extends TestCase{

	/**
	 * Tests the valid and invalid states.
	 */
	public void testGetNodeState(){
		//Test invalid states.
		//Loop testing for zero times.
		try{
			NodeState.getNodeState(-1);
			fail();
		}
		catch(IllegalArgumentException e){
			assertTrue(true);
		}
		try{
			NodeState.getNodeState(0);
			fail();
		}
		catch(IllegalArgumentException e){
			assertTrue(true);
		}
		try{
			NodeState.getNodeState(5);
			fail();
		}
		catch(IllegalArgumentException e){
			assertTrue(true);
		}
		
		//Test valid states.
		//Loop testing for one cycle.
		NodeState standard = NodeState.getNodeState(1);
		assertTrue(standard == NodeState.STANDARD);
		//Loop testing for two cycles.
		NodeState upPointing = NodeState.getNodeState(2);
		assertTrue(upPointing == NodeState.UP_POINTING);
		NodeState downPointing = NodeState.getNodeState(3);
		assertTrue(downPointing == NodeState.DOWN_POINTING);
		NodeState cap = NodeState.getNodeState(4);
		assertTrue(cap == NodeState.CAP);
	}
	
	/**
	 * Only tests an illegal squeeze call.
	 */
	public void testSqueezeStandard(){
		//Test illegal squeeze call.
		try{
			NodeState.STANDARD.squeeze(null);
			fail();
		}
		catch(UnsupportedOperationException e){
			assertTrue(true);
		}
	}
	
	/**
	 * Tests the setNodeState(NodeInterface) method.
	 */
	public void testSetNodeState(){
		NodeInterface iNode = new Neighbor(new Node(0));
		NodeState.setNodeState(iNode);
		assertTrue(iNode.getNode().getState() == NodeState.CAP);
	}
}
