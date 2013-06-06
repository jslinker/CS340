package dbPhase.hypeerweb;

public abstract class PerformUpdateOnConnections {
	
	public abstract void modifyConnection(Connection node, Node caller);

	public void execute(Node caller){
		for (Connection c : caller.getConnections()) {
			if (!c.getNodeCore().equals(caller.getNodeCore())) {
				modifyConnection(c, caller);
			}
		}
	}
}

