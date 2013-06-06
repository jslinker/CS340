package dbPhase.hypeerweb;

public class DownPointer extends Connection {

	public DownPointer(Node node) {
		super(node);
	}

	@Override
	public void addMe(Node node) {
		this.addUpPointer(node);
	}

	@Override
	public void removeMe(Node node) {
		this.removeUpPointer(node);
	}

	@Override
	public void replaceMe(Node node) {
		this.addUpPointer(node);
	}

}
