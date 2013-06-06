package dbPhase.hypeerweb;

public class Fold extends Connection {

	public Fold(Node node) {
		super(node);
	}

	@Override
	public void addMe(Node node) {
		if(node.getWebIdValue() == 1) {
			//If you're the first node and the second node is asking you to add it.
			//This case comes up only during that first addition.
			this.setFold(node);
			this.removeSurrogateFold();
		} else if(this.getSurrogateFold() == NullConnection.getNullConnection()) {
			//If you have a fold
			this.setInverseSurrogateFold(this.getFold());
			this.getFold().setSurrogateFold(this);
			this.setFold(node);
		} else {
			//If you have a surrogateFold
			this.getSurrogateFold().removeInverseSurrogateFold();
			this.removeSurrogateFold();
			this.setFold(node);
		}
	}

	@Override
	public void removeMe(Node node) {
		if(node.getWebIdValue() == 1) {
			//If you're the first node in the web and the second node is asking
			//you to add it.
			//This case only comes up when deleting the second node.
			this.setFold(this);
		} else if(this.getInverseSurrogateFold() == NullConnection.getNullConnection()) {
			this.removeFold();
			Node surrogateFold = node.getParent();
			this.setSurrogateFold(surrogateFold);
			surrogateFold.setInverseSurrogateFold(this);			
		} else {
			Node fold = this.getInverseSurrogateFold();
			this.removeInverseSurrogateFold();
			fold.removeSurrogateFold();
			this.setFold(fold);
			fold.setFold(this);
		}
	}

	@Override
	public void replaceMe(Node node) {
		this.setFold(node);
	}

}
