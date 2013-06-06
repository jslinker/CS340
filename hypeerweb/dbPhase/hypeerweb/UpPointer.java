package dbPhase.hypeerweb;

public class UpPointer extends Connection {

	public UpPointer(Node node) {
		super(node);
	}

	@Override
	public void addMe(Node node) {
		//This method should never be run.
		//A new node is an edge node, and has no up pointers.
		assert false;
	}

	@Override
	public void removeMe(Node node) {
		//Since only the highest node is removed, and the up pointer is always
		//higher, this method should never be called.
		assert false;
	}

	@Override
	public void replaceMe(Node node) {
		this.addDownPointer(node);
	}

}
