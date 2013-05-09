package node.states;

/**
 * @author Craig Jacobson
 *
 */
public class DownPointingNodeState extends NodeState{
	public final static int STATE_ID = 2;
	
	public DownPointingNodeState(){
		
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
