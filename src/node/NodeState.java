package node;

import utilities.BitManipulation;
import static java.lang.System.out;

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
		public Node squeeze(Node lowerBound, Node upperBound) {
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
		public Node squeeze(Node lowerBound, Node upperBound) {
			if(BitManipulation.calculateChildWebId(upperBound.getWebIdValue()-1, 
													upperBound.getHeight()) == lowerBound.getWebIdValue()){
				return upperBound;
			}
			else{
				
				Node largestUpPointer = upperBound.getConnections().getLargestUpPointer();
				if(largestUpPointer.getWebIdValue() > lowerBound.getWebIdValue()){
					lowerBound = largestUpPointer;
					return lowerBound.getState().squeeze(lowerBound, upperBound);
				}
				else{
					upperBound = upperBound.getConnections().getSmallestChildlessNeighbor();
					return upperBound.getState().squeeze(lowerBound, upperBound);
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
		public Node squeeze(Node lowerBound, Node upperBound) {
			Node smallestDownPointer = lowerBound.getConnections().getSmallestDownPointer();
			if(smallestDownPointer.getWebIdValue() < upperBound.getWebIdValue() || upperBound == Node.NULL_NODE){
				upperBound = smallestDownPointer;
			}
			return upperBound.getState().squeeze(lowerBound, upperBound);
		}
	},
	
	CAP(4){
		@Override
		public String toString(){
			return "Cap Node State";
		}

		@Override
		public Node squeeze(Node lowerBound, Node upperBound) {
			return lowerBound.getFold();
		}
	},
	
	TERMINAL(5){
		@Override
		public String toString(){
			return "Terminal Node State";
		}

		@Override
		public Node squeeze(Node lowerBound, Node upperBound) {
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
			if(state.STATE_ID == id) return state;
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
	
	public abstract Node squeeze(Node lowerBound, Node upperBound);
	public abstract String toString();
}
