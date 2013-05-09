package node.states;

/**
 * @author Craig Jacobson
 *
 */
public class StandardNodeState extends NodeState{
	public final static int STATE_ID = 0;
	
	public StandardNodeState(){
		
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
