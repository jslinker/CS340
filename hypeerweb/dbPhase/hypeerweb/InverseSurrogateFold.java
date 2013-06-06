package dbPhase.hypeerweb;

public class InverseSurrogateFold extends Connection {

	public InverseSurrogateFold(Node node) {
		super(node);
	}

	@Override
	public void addMe(Node node) {
		//This method should never be called.
		//No node is created as a surrogate fold, so it should have no inverse surrogate fold.
		assert false;
	}

	@Override
	public void removeMe(Node node) {
		Thread.dumpStack();	//Since only the highest node is removed, and the inverse surrogate fold is always higher, this method should never be called.
	}

	@Override
	public void replaceMe(Node node) {
		this.setSurrogateFold(node);
	}

}
