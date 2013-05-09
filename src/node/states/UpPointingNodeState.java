package node.states;

/**
 * @author Craig Jacobson
 *
 */
public class UpPointingNodeState extends NodeState{
	public final static int STATE_ID = 1;
	
	public UpPointingNodeState(){
		
	}

	/* (non-Javadoc)
	 * @see node.states.NodeState#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getStateId() {
		return this.STATE_ID;
	}
}
