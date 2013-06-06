package dbPhase.hypeerweb;

public class Neighbor extends Connection {

	public Neighbor(Node node) {
		super(node);
	}

	@Override
	public void addMe(Node node) {
		this.addNeighbor(node);	//Removing the surrogate neighbor is automatic.
	}

	@Override
	public void removeMe(Node node) {
		this.removeNeighbor(node);
		if (!this.getNodeCore().equals(node.getParent().getNodeCore())) {
			/*
			 * TODO: Currently, just checking if they're equal will work, but I'd
			 * rather talk about how exactly we check for equality of connections
			 * before changing this line.
			 */
			this.addDownPointer(node.getParent());
			node.getParent().addUpPointer(this);
		}
		
	}

	@Override
	public void replaceMe(Node node) {
		this.addNeighbor(node);
	}

}
