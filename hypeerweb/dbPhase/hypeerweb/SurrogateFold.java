package dbPhase.hypeerweb;

public class SurrogateFold extends Connection {

	public SurrogateFold(Node node) {
		super(node);
	}

	@Override
	public void addMe(Node node) {
		assert false;
		//This method should never be run.
		//On creation, a node is an edge node, so its fold will be in the completed hypercube.
		//As such, it has no surrogate fold.
	}

	@Override
	public void removeMe(Node node) {
		this.removeInverseSurrogateFold();
	}

	@Override
	public void replaceMe(Node node) {
		this.setInverseSurrogateFold(node);
	}

}
