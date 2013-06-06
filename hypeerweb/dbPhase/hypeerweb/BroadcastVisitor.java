package dbPhase.hypeerweb;

public abstract class BroadcastVisitor implements Visitor {
	
	private static final String BROADCAST_INDEX = "broadcastIndex";

	public BroadcastVisitor() {}
	
	public static Parameters createInitialParameters() {
		Parameters parameters = new Parameters();
		parameters.set(BROADCAST_INDEX, -1);
		return parameters;
	}
	
	protected abstract void operation(Node node, Parameters parameters);
	
	public void visit(Node node, Parameters parameters) {
		operation(node, parameters);
		for(int i = (Integer)parameters.get(BROADCAST_INDEX)+1; i < node.getHeight(); ++i) {
			parameters.set(BROADCAST_INDEX, i);
			node.getConnections().getNeighborOrDownPointer(i).accept(this, parameters);
		}
		
		/*operation(node, parameters);
		int i = (Integer)parameters.get(BROADCAST_INDEX)+1;
		if(node.getHeight() < i) {
			parameters.set(BROADCAST_INDEX, i);
			node.accept(this, parameters);
			Node neighborOrDownpointer = node.getConnections().getNeighborOrDownPointer(i);
			parameters.set(BROADCAST_INDEX, i);
			//Since that parameter was changed in further iterations of visit after the last node.accpet, it must be set to i again.
			neighborOrDownpointer.accept(this, parameters);
		}*/
	}

}
