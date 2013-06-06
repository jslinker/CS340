package dbPhase.hypeerweb;

public class NullConnection extends Connection implements Node{
	private static NullConnection nullConnection;
	
	private NullConnection() {
		super(NodeCore.getNullNode());
	}
	
	public static NullConnection getNullConnection() {
		if(nullConnection == null) {
			nullConnection = new NullConnection();
		}
		return nullConnection;
	}

	public void addConnections() {
	}

	@Override
	public void addMe(Node node) {
	}

	@Override
	public void removeMe(Node node) {
	}

	@Override
	public void replaceMe(Node node) {
	}
}
