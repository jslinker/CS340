package node.states;

/**
 * @author Craig Jacobson
 *
 */
public abstract class NodeState {

	public static NodeState getNodeState(int id){
		NodeState result = null;
		switch(id){
			case StandardNodeState.STATE_ID: result = new StandardNodeState(); break;
			case UpPointingNodeState.STATE_ID: result = new UpPointingNodeState(); break;
			case DownPointingNodeState.STATE_ID: result = new DownPointingNodeState(); break;
			case CapNodeState.STATE_ID: result = new CapNodeState(); break;
			case TerminalNodeState.STATE_ID: result = new TerminalNodeState(); break;
			default: throw new IllegalArgumentException();
		}
		return result;
	}
	
	public abstract String toString();
	public abstract int getStateId();
}
