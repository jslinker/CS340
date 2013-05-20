package node;

import utilities.BitManipulation;

/**
 * 
 * @author Craig Jacobson
 *
 */
public enum NodeState{
	STANDARD(1){
		@Override
		public String toString(){
			return "Standard Node State";
		}

		@Override
		public Pair<Node> squeeze(Pair<Node> pair) {
			throw new IllegalArgumentException("How did you get here? You can't call squeeze on a " +
					"Standard Node!");
		}
	},
	
	UP_POINTING(2){
		@Override
		public String toString(){
			return "Up Pointing Node State";
		}

		@Override
		public Pair<Node> squeeze(Pair<Node> pair) {
			if(BitManipulation.calculateChildWebId(pair.getUpperBound().getWebIdValue()-1, 
					pair.getUpperBound().getHeight()) == pair.getLowerBound().getWebIdValue()){
				return pair;
			}
			else{
				Node largestUpPointer = pair.getUpperBound().getConnections().getLargestUpPointer();
				if(largestUpPointer.getWebIdValue() > pair.getLowerBound().getWebIdValue()){
					pair.setLowerBound(largestUpPointer);
					return pair.getLowerBound().getState().squeeze(pair);
				}
				else{
					pair.setUpperBound(pair.getUpperBound().getConnections().getSmallestChildlessNeighbor());
					return pair.getUpperBound().getState().squeeze(pair);
				}
			}
		}
	},
	
	DOWN_POINTING(3){
		@Override
		public String toString(){
			return "Down Pointing Node State";
		}

		@Override
		public Pair<Node> squeeze(Pair<Node> pair) {
			Node smallestDownPointer = pair.getLowerBound().getConnections().getSmallestDownPointer();
			if(smallestDownPointer.getWebIdValue() < pair.getUpperBound().getWebIdValue() || pair.getUpperBound() == Node.NULL_NODE){
				pair.setUpperBound(smallestDownPointer);
			}
			return pair.getUpperBound().getState().squeeze(pair);
		}
	},
	
	CAP(4){
		@Override
		public String toString(){
			return "Cap Node State";
		}

		@Override
		public Pair<Node> squeeze(Pair<Node> pair){
			pair.setUpperBound(pair.getLowerBound().getFold());
			return pair;
		}
	},
	
	TERMINAL(5){
		@Override
		public String toString(){
			return "Terminal Node State";
		}

		@Override
		public Pair<Node> squeeze(Pair<Node> pair) {
			throw new IllegalArgumentException("How did you get here? You can't call squeeze on a " +
					"Terminal Node!");
		}
	};
	
	public final int STATE_ID;
	
	private NodeState(int stateId){
		STATE_ID = stateId;
	}
	
	public static NodeState getNodeState(int id){
		for(NodeState state: NodeState.values()){
			if(state.STATE_ID == id){
				return state;
			}
		}
		throw new IllegalArgumentException();
	}
	
	public static void setNodeState(Node node){
		if(node.getConnections().getDownPointerCount() > 0){
			node.setState(NodeState.DOWN_POINTING);
		}
		else if(node.getConnections().getUpPointerCount() > 0){
			node.setState(NodeState.UP_POINTING);
		}
		else if(node.getFold().getWebIdValue() == 0){
			node.setState(NodeState.CAP);
		}
		else{
			node.setState(NodeState.STANDARD);
		}
	}
	
	public abstract Pair<Node> squeeze(Pair<Node> pair);
	public abstract String toString();
}
