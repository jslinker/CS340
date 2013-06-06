package dbPhase.hypeerweb;

public enum NodeState {
	StandardNodeState, NodeZeroState, InsertionPointState;
	
	
	public static NodeState stateFromInt(int state)
	{
		switch(state) {
		case 0:
			return NodeZeroState;
		case 1:
			return StandardNodeState;
		case 2:
			return InsertionPointState;
		default:
			return null;
		}
	}
	
	public String toString() {
		switch(this) {
		case NodeZeroState:
			return "NodeZeroState";
		case InsertionPointState:
			return "InsertionPointState";
		case StandardNodeState:
			return "StandardNodeState";
		default:
			return "";
		}
	}
		
	public int intValue()
	{
		switch(this) {
		case NodeZeroState:
			return 0;
		case InsertionPointState:
			return 2;
		case StandardNodeState:
			return 1;
		default:
			return -1;
		}
	}
	
	public boolean equals(NodeState other)
	{
		return this.intValue() == other.intValue();
	}
}
