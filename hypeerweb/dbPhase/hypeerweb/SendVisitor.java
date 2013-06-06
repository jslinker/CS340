package dbPhase.hypeerweb;

public abstract class SendVisitor implements Visitor {
	
	protected static String TARGET_KEY;
	
	public SendVisitor() {
		
	}
	
	public static Parameters createInitialParameters(int target) {
		Parameters out = new Parameters();
		out.set(TARGET_KEY, target);
		return out;
	}
	
	protected void intermediateOperation(Node node, Parameters parameters) {
		int id = (Integer)parameters.get(TARGET_KEY);
		int xor = id^node.getWebIdValue();
		if(xor == 0) {
			targetOperation(node, parameters);
			return;
		}
		int bitCount = Integer.bitCount(xor);
		if(bitCount > node.getHeight()/2+1) {	//Is that right?
			node.getConnections().getFoldOrSurrogateFold().accept(this, parameters);
			return;
			//We should probably put getFoldOrSurrogateFold in Node.
		}
		int significantBit = Integer.numberOfTrailingZeros(xor);
		node.getConnections().getNeighborOrDownPointer(significantBit).accept(this, parameters);
		return;
	}
	
	public abstract void targetOperation(Node node, Parameters parameters);
	
	public void visit(Node node, Parameters parameters) {
		if(node.getWebIdValue() == (Integer)parameters.get(TARGET_KEY)) {
			targetOperation(node, parameters);
		} else {
			intermediateOperation(node, parameters);
		}
	}

}
