package dbPhase.hypeerweb;


/**
 * Wrapper class for SimplifiedNodeDomain
 * 
 * @author Daniel
 */
public interface Node {
	
	NodeCore getNodeCore();
	
	public Contents getContents();

	/**
	 * This gives a SimplifiedNodeDomain, which just gives the values of the
	 * webIDs of the connections rather than the nodes themselves. This makes it
	 * easier to test.
	 * 
	 * @return simplified version of the node
	 */
	public SimplifiedNodeDomain constructSimplifiedNodeDomain();

	public WebId getWebId();

	public void setWebId(WebId webId);
	
	public void addConnections();

	/**
	 * Adds a neighbor.
	 * 
	 * @param neighbor
	 */
	public void addNeighbor(Node neighbor);

	/**
	 * Removes the given neighbor.
	 * 
	 * @param neighbor	Neighbor to remove
	 */
	public void removeNeighbor(Node neighbor);

	/**
	 * Adds an up pointer, also known as an inverse surrogate neighbor.
	 * 
	 * @param upPointer
	 */
	public void addUpPointer(Node upPointer);

	/**
	 * Removes the given up pointer if it exists. Does nothing if it does not.
	 * 
	 * @param upPointer
	 */
	public void removeUpPointer(Node upPointer);

	/**
	 * Adds a down pointer, also known as an surrogate neighbor.
	 * 
	 * @param downPointer
	 */
	public void addDownPointer(Node downPointer);

	/**
	 * Removes the given down pointer and adds in the corresponding neighbor.
	 * 
	 * @param downPointer
	 */
	public void removeDownPointer(Node downPointer);

	/**
	 * Removes the given down pointer and adds in the corresponding neighbor.
	 * 
	 * @param downPointer
	 */
	public void removeDownPointer(Node downPointer, int i);

	/**
	 * Sets the folds of this - need to then call set fold of the new fold to this.
	 * 
	 * @param newFold
	 */
	public void setFold(Node newFold);
	
	public void removeFold();
	
	/**
	 * @obvious
	 */
	public Connections getConnections();
	
	public void setConnections(Connections newConnections);

	/**
	 * Sets the given node to the surrogate fold of this, and sets this to the
	 * inverse surrogate fold of the given node.
	 * 
	 * @param newSurrogateFold
	 */
	public void setSurrogateFold(Node newSurrogateFold);
	
	public void removeSurrogateFold();

	/**
	 * Sets the given node to the inverse surrogate fold of this, and sets this
	 * to the surrogate fold of the given node.
	 * 
	 * @param newInverseSurrogateFold
	 */
	public void setInverseSurrogateFold(Node newInverseSurrogateFold);
	
	public void removeInverseSurrogateFold();

	public int getWebIdValue();

	public int getHeight();

	public boolean equals(Node node);
	
	public NodeList getNeighbors();
	
	public NodeList getUpPointers();
	
	public NodeList getDownPointers();

	public Node getFold();

	public Node getSurrogateFold();

	public Node getInverseSurrogateFold();
	
	public Node getParent();
	
	public Node getChild();
	
	public NodeState getNodeState();
	
	public void setState(NodeState newState);
	
	public void addToHyPeerWeb(Node newNode);
	
	public void deleteFromHyPeerWeb(Node toDelete);
	
	public Node getDeletionNode();
	
	public Node getDeletionNode(int i);
	
	public Node getInsertionNode();
	
	public Node getNode(int id);
	
	public void addChild(Node child);
	
	public Node insertionStageOne();
	
	public Node insertionStageTwo();
	
	public void accept(Visitor visitor, Parameters parameters);

}
