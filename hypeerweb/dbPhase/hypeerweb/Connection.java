package dbPhase.hypeerweb;


public abstract class Connection implements Node {
	private NodeCore nodeCore;
	
	/**
	 * This method is called on all the new connections when adding a new node to the HyPeerWeb.
	 * It is called in the form connection.addMe(this).
	 * 
	 * @param node	The node calling this method
	 */
	abstract public void addMe(Node node);
	
	/**
	 * This method is called on all the connections of the deletion node before it is moved to the node it will replace.
	 * It is called in the form connection.removeMe(this). It removes this from connection, not the other way around.
	 * There is no need to remove the connections from this, since it's connections object will simply be replaced.
	 * 
	 * @param node	The node calling this method
	 */
	abstract public void removeMe(Node node);
	
	/**
	 * This method is called on all the connections by the replacement node when a node is replaced.
	 * 
	 * @param node	The node calling this method
	 */
	abstract public void replaceMe(Node node);
	
	public Connection(Node node) {
		this.nodeCore = node.getNodeCore();
	}
	
	public Connection(NodeCore core)
	{
		this.nodeCore = core;
	}
	
	public NodeCore getNodeCore() {
		return nodeCore;
	}
	
	public Contents getContents() {
		return nodeCore.getContents();
	}
	
	public void addConnections() {
		nodeCore.addConnections();
	}
	
	public SimplifiedNodeDomain constructSimplifiedNodeDomain() {
		return nodeCore.constructSimplifiedNodeDomain();
	}

	public WebId getWebId() {
		return nodeCore.getWebId();
	}

	public void setWebId(WebId webId) {
		nodeCore.setWebId(webId);
	}

	public void addNeighbor(Node neighbor) {
		nodeCore.addNeighbor(neighbor);
	}

	public void removeNeighbor(Node neighbor) {
		nodeCore.removeNeighbor(neighbor);
	}

	public void addUpPointer(Node upPointer) {
		nodeCore.addUpPointer(upPointer);
	}

	public void removeUpPointer(Node upPointer) {
		nodeCore.removeUpPointer(upPointer);
	}

	public void addDownPointer(Node downPointer) {
		nodeCore.addDownPointer(downPointer);
	}

	public void removeDownPointer(Node downPointer) {
		nodeCore.removeDownPointer(downPointer);
	}

	public void removeDownPointer(Node downPointer, int i) {
		nodeCore.removeDownPointer(downPointer, i);
	}

	public void setFold(Node newFold) {
		nodeCore.setFold(newFold);
		nodeCore.removeSurrogateFold();
		if (nodeCore.getFold().getNodeCore().equals(nodeCore.getSurrogateFold().getNodeCore()))
		{
			nodeCore.getFold().removeInverseSurrogateFold();
		}
	}

	public void removeFold() {
		nodeCore.removeFold();
	}

	public Connections getConnections() {
		return nodeCore.getConnections();
	}
	
	public void setConnections(Connections newConnections) {
		nodeCore.setConnections(newConnections);
	}

	public void setSurrogateFold(Node newSurrogateFold) {
		nodeCore.setSurrogateFold(newSurrogateFold);
		nodeCore.removeFold();
	}

	public void removeSurrogateFold() {
		nodeCore.removeSurrogateFold();
	}

	public void setInverseSurrogateFold(Node newInverseSurrogateFold) {
		nodeCore.setInverseSurrogateFold(newInverseSurrogateFold);
	}

	public void removeInverseSurrogateFold() {
		nodeCore.removeInverseSurrogateFold();
	}

	public int getWebIdValue() {
		return nodeCore.getWebIdValue();
	}

	public int getHeight() {
		return nodeCore.getHeight();
	}

	public boolean equals(Node node) {	//TODO: I'm not really sure what I should do here. Currently, equals checks if their WebIds have the same height and value, and since the Connection has the same WebId as the node, this should work fine. This would be problematic if you want the neighbor and fold to be different.
		return nodeCore.equals(node);
	}

	public NodeList getNeighbors() {
		return nodeCore.getNeighbors();
	}

	public NodeList getUpPointers() {
		return nodeCore.getUpPointers();
	}

	public NodeList getDownPointers() {
		return nodeCore.getDownPointers();
	}

	public Node getFold() {
		return nodeCore.getFold();
	}

	public Node getSurrogateFold() {
		return nodeCore.getSurrogateFold();
	}

	public Node getInverseSurrogateFold() {
		return nodeCore.getInverseSurrogateFold();
	}

	public Node getParent() {
		return nodeCore.getParent();
	}

	public Node getChild() {
		return nodeCore.getChild();
	}

	public NodeState getNodeState() {
		return nodeCore.getNodeState();
	}

	public void setState(NodeState newState) {
		nodeCore.setState(newState);
	}

	public void addToHyPeerWeb(Node newNode) {
		nodeCore.addToHyPeerWeb(newNode);
	}
	
	public void deleteFromHyPeerWeb(Node toDelete)
	{
		nodeCore.deleteFromHyPeerWeb(toDelete);
	}

	public Node getDeletionNode() {
		return nodeCore.getDeletionNode();
	}

	public Node getDeletionNode(int i) {
		return nodeCore.getDeletionNode(i);
	}

	public Node getInsertionNode() {
		return nodeCore.getInsertionNode();
	}

	public Node getNode(int id) {
		return nodeCore.getNode(id);
	}

	public void addChild(Node child) {
		nodeCore.addChild(child);
	}
	
	public Node insertionStageOne() {
		return nodeCore.insertionStageOne();
	}
	
	public Node insertionStageTwo() {
		return nodeCore.insertionStageTwo();
	}
	
	public void accept(BroadcastVisitor broadcastVisitor, Parameters parameters) {
		nodeCore.accept(broadcastVisitor, parameters);
	}
	
	public void accept(Visitor visitor, Parameters parameters) {
		visitor.visit(this, parameters);
	}
	
	public String toString() {
		return nodeCore.toString();
	}
	
}
