package dbPhase.hypeerweb;

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
			assertTrue(null == NodeState.stateFromInt(-1));
			//fail();
		}
		catch(IllegalArgumentException e){
			fail();
		}
		try{
			assertTrue(NodeState.NodeZeroState == NodeState.stateFromInt(0));
		}
		catch(IllegalArgumentException e){
			fail();
		}
		try{
			assertTrue(null == NodeState.stateFromInt(5));
		}
		catch(IllegalArgumentException e){
			fail();
		}
		
		//Test valid states.
		//Loop testing for one cycle.
		NodeState standard = NodeState.stateFromInt(1);
		assertTrue(standard == NodeState.StandardNodeState);
		//Loop testing for two cycles.
		NodeState insert = NodeState.stateFromInt(2);
		assertTrue(insert == NodeState.InsertionPointState);
	}
	
	/**
	 * Only tests an illegal squeeze call.
	 */
//	public void testSqueezeStandard(){
//		//Test illegal squeeze call.
//		try{
//			NodeState.STANDARD.squeeze(null);
//			fail();
//		}
//		catch(UnsupportedOperationException e){
//			assertTrue(true);
//		}
//	}
	
	/**
	 * Tests the setNodeState(NodeInterface) method.
	 */
//	public void testSetNodeState(){
//		NodeInterface iNode = new Neighbor(new Node(0));
//		NodeState.setNodeState(iNode);
//		assertTrue(iNode.getNode().getState() == NodeState.CAP);
//	}
}
