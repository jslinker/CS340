package dbPhase.hypeerweb;


/**
 * Wrapper class for SimplifiedNodeDomain
 * 
 * @author Daniel
 */
public class NodeCore implements Node {
	private WebId id;
	private Connections connections;
	private Contents contents;
	private static NodeCore nullNode;

	public static NodeCore getNullNode() {
		if (nullNode == null) {
			nullNode = new NodeCore(WebId.NULL_WEB_ID);
		}
		return nullNode;
	}

	private NodeCore(WebId id) {
		this.id = id;
		if (id != WebId.NULL_WEB_ID) {
			connections = new Connections(id.getValue());
			connections.setFold(new Fold(this));
		}
		contents = new Contents();
	}

	/**
	 * Constructs a node with the given webID. The height is the position of the
	 * most significant bit in the ID.
	 * <p>
	 * The specs said that the height should be based on the position of the
	 * most significant bit of the webID, but in class we were told that it
	 * should be based on the number of neighbors. I went with the specs,
	 * because while the Node(id, height) constructer still doesn't make much
	 * sense, at least it doesn't actually break the code.
	 * 
	 * @param id
	 *            The value of the WebId
	 */
	public NodeCore(int id) {
		this(new WebId(id));
	}

	/**
	 * Constructs a node with the given webID that has the given value and
	 * height.
	 * 
	 * @param id
	 *            value of the WebId
	 * @param height
	 *            height of the WebId
	 */
	public NodeCore(int id, int height) {
		this(new WebId(id, height));
	}

	public Contents getContents() {
		return contents;
	}

	/**
	 * This gives a SimplifiedNodeDomain, which just gives the values of the
	 * webIDs of the connections rather than the nodes themselves. This makes it
	 * easier to test.
	 * 
	 * @return simplified version of the node
	 */
	public SimplifiedNodeDomain constructSimplifiedNodeDomain() {
		return connections.constructSimplifiedNodeDomain();
	}

	public WebId getWebId() {
		return id;
	}

	public void addConnections() {
		for (Connection connection : connections) {
			connection.addMe(this);
		}
	}

	public void setWebId(WebId webId) {
		id = webId;
		connections.setWebID(id.getValue());
	}

	/**
	 * Adds a neighbor.
	 * 
	 * @param neighbor
	 */
	public void addNeighbor(Node neighbor) {
		connections.setNeighbor(new Neighbor(neighbor));
	}

	/**
	 * Removes the given neighbor and adds the neighbor's parent as a surrogate
	 * neighbor.
	 * 
	 * @param neighbor
	 *            Neighbor to remove
	 */
	public void removeNeighbor(Node neighbor) {
		connections.removeNeighbor(neighbor);
	}

	/**
	 * Adds an up pointer, also known as an inverse surrogate neighbor.
	 * 
	 * @param upPointer
	 */
	public void addUpPointer(Node upPointer) {
		connections.setUpPointer(new UpPointer(upPointer));
	}

	/**
	 * Removes the given up pointer if it exists. Does nothing if it does not.
	 * 
	 * @param upPointer
	 */
	public void removeUpPointer(Node upPointer) {
		connections.removeUpPointer(upPointer);
	}

	/**
	 * Adds a down pointer, also known as an surrogate neighbor.
	 * 
	 * @param downPointer
	 */
	public void addDownPointer(Node downPointer) {
		connections.setDownPointer(new DownPointer(downPointer));
	}

	/**
	 * Removes the given down pointer and adds in the corresponding neighbor.
	 * 
	 * @param downPointer
	 */
	public void removeDownPointer(Node downPointer) {
		connections.removeDownPointer(downPointer);
	}

	/**
	 * Removes the given down pointer.
	 * 
	 * @param downPointer
	 */
	public void removeDownPointer(Node downPointer, int i) {
		connections.setNeighbor(NullConnection.getNullConnection(), i);
	}

	/**
	 * Sets the folds of this - need to then call set fold of the new fold to
	 * this.
	 * 
	 * @param newFold
	 */
	public void setFold(Node newFold) {
		connections.setFold(new Fold(newFold));
	}

	public void removeFold() {
		connections.setFold(NullConnection.getNullConnection());
	}

	/**
	 * @obvious
	 */
	public Connections getConnections() {
		return connections;
	}

	public void setConnections(Connections newConnections) {
		connections = newConnections;
	}

	/**
	 * Sets the given node to the surrogate fold of this, and sets this to the
	 * inverse surrogate fold of the given node.
	 * 
	 * @param newSurrogateFold
	 */
	public void setSurrogateFold(Node newSurrogateFold) {
		connections.setSurrogateFold(new SurrogateFold(newSurrogateFold));
	}

	public void removeSurrogateFold() {
		connections.setSurrogateFold(NullConnection.getNullConnection());
	}

	/**
	 * Sets the given node to the inverse surrogate fold of this, and sets this
	 * to the surrogate fold of the given node.
	 * 
	 * @param newInverseSurrogateFold
	 */
	public void setInverseSurrogateFold(Node newInverseSurrogateFold) {
		connections.setInverseSurrogateFold(new InverseSurrogateFold(
				newInverseSurrogateFold));
	}

	public void removeInverseSurrogateFold() {
		connections.removeInverseSurrogateFold();
	}

	public int getWebIdValue() {
		return id.getValue();
	}

	public int getHeight() {
		return connections.getHeight();
	}

	public boolean equals(Node node) {
		return id.equals(node.getWebId());
	}

	public NodeList getNeighbors() {
		return connections.getNeighbors();
	}

	public NodeList getUpPointers() {
		return connections.getUpPointers();
	}

	public NodeList getDownPointers() {
		return connections.getDownPointers();
	}

	public Node getFold() {
		return connections.getFold();
	}

	public Node getSurrogateFold() {
		return connections.getSurrogateFold();
	}

	public Node getInverseSurrogateFold() {
		return connections.getInverseSurrogateFold();
	}

	public Node getParent() {
		return connections.getParent();
	}

	public Node getChild() {
		return connections.getChild();
	}

	public NodeState getNodeState() {
		return connections.getState();
	}

	public void setState(NodeState newState) {
		if (id.getValue() == 0) {
			connections.setState(NodeState.NodeZeroState);
		} else {
			connections.setState(newState);
		}
	}

	public void addToHyPeerWeb(Node newNode) {
		if (this != NodeCore.getNullNode()) {
			getInsertionNode().addChild(newNode);
		}
		
		HyPeerWeb.getSingleton().addNode(newNode);
	}

	public void deleteFromHyPeerWeb(Node toDelete) {
		if (HyPeerWeb.getSingleton().size() == 1) {
			HyPeerWeb.getSingleton().deleteFromHyPeerWeb(toDelete, this);
		}
		this.getInsertionNode().setState(NodeState.StandardNodeState);
		Node deletionPoint = this.getDeletionNode();

		deletionPoint.getParent().getConnections()
				.setState(NodeState.InsertionPointState);

		new PerformUpdateOnConnections() {
			@Override
			public void modifyConnection(Connection node, Node caller) {
				node.removeMe(caller);
			}
		}.execute(deletionPoint); // Removes all connections to deletionPoint.

		if (!deletionPoint.getNodeCore().equals(toDelete.getNodeCore())) {
			deletionPoint.setWebId(toDelete.getWebId());
			deletionPoint.setConnections(toDelete.getConnections());

			new PerformUpdateOnConnections() {
				@Override
				public void modifyConnection(Connection node, Node caller) {
					node.replaceMe(caller);
				}
			}.execute(deletionPoint); // Sets toDelete's connections to instead
										// point to deletionPoint.
		}

		HyPeerWeb.getSingleton().removeNodeFromList(toDelete);
		// Node 0 should be the insertion node sometimes - that's the only time
		// it shouldn't be nodeZeroState
	}

	public Node getDeletionNode() {
		if (getNodeState().equals(NodeState.NodeZeroState)
				|| id.getValue() == 0) {
			return getDeletionNode(this.getHeight());
		} else {
			return getNode(0).getDeletionNode();
		}
	}

	public Node getDeletionNode(int i) {
		if (i < 0) {
			return this;
		}
		Connection neighbor = connections.getNeighbor(i);
		if (neighbor == NullConnection.getNullConnection()) {
			return this.getDeletionNode(i - 1);
		} else {
			return neighbor.getDeletionNode(i - 1);
		}
	}

	public Node getInsertionNode() {
		return insertionStageOne();
	}

	public Node getNode(int id) {
		int xor = id ^ this.getWebIdValue();
		if (xor == 0) {
			return this;
		}
		int bitCount = Integer.bitCount(xor);
		if (bitCount > this.getHeight() / 2 + 1) { // Is that right?
			return connections.getFoldOrSurrogateFold().getNode(id);
		}
		int significantBit = Integer.numberOfTrailingZeros(xor);
		if (bitCount == 1) { // If there's only one one bit
			return connections.getNeighborOrDownPointer(significantBit);
		} else {
			return connections.getNeighborOrDownPointer(significantBit)
					.getNode(id);
			// Normally I wouldn't bother with this, and just let it recurse
			// until it returns itself.
			// However, due to the fact that this can involve an internet
			// connection, saving one step is well worth it.
		}
	}

	public void addChild(Node child) {
		child.setWebId(new WebId(this.getWebIdValue() + (1 << this.getHeight())));

		for (int i = 0; i < this.getHeight(); ++i) {
			if (connections.getUpPointer(i) == NullConnection
					.getNullConnection()) {
				child.addDownPointer(connections.getNeighbor(i));
			} else {
				child.addNeighbor(connections.getUpPointer(i));
			}
		}

		child.getConnections()
				.setNeighbor(new Neighbor(this), this.getHeight());

		connections.clearUpPointers();
		if (this.getWebIdValue() == 0 && child.getWebIdValue() == 1) {
			child.setFold(this);
		} else if (this.getInverseSurrogateFold() == NullConnection
				.getNullConnection()) {
			child.setFold(this.getFold());
		} else {
			child.setFold(this.getInverseSurrogateFold());
		}

		this.setState(NodeState.StandardNodeState);
		child.setState(NodeState.StandardNodeState);

		// Put Template Pattern here.
		new PerformUpdateOnConnections() {
			@Override
			public void modifyConnection(Connection node, Node caller) {
				node.addMe(caller);
			}
		}.execute(child);

		int id = child.getWebIdValue() + 1;
		id -= Integer.highestOneBit(id);
		getNode(id).getConnections().setState(NodeState.InsertionPointState);
	}

	/**
	 * Uses the greedy algorithm until it no longer can, then goes to the lowest
	 * surrogate neighbor and moves to stage two. This function does it for one
	 * step, then calls itself recursively. It also calls stage two, so it
	 * returns the final node.
	 * 
	 * @return insertion node
	 */
	public Node insertionStageOne() {
		Connection highestConnection = connections.getHighestConnection();
		if (highestConnection == NullConnection.getNullConnection()) {
			Connection upperBound = connections.getLowestUpperBoundFromBelow();
			if (upperBound == NullConnection.getNullConnection()) {
				// Only happens if this is the cap node, and this is a perfect
				// hypercube.
				return getNode(0);
				// Should be the fold, but I'm not going to risk it just yet.
			} else {
				return connections.getLowestUpperBoundFromBelow()
						.insertionStageTwo();
			}
		} else {
			return highestConnection.insertionStageOne();
		}
	}

	/**
	 * Performs the greedy algorithm from above to find the insertion node.
	 * While the greedy algorithm generally fails, it is guaranteed to succeed
	 * from the lowest upper bound found from following the greedy algorithm
	 * from below, which is what stage one does.
	 * 
	 * @return insertion node
	 */
	public Node insertionStageTwo() {
		Connection lowestChildlessNeighbor = connections
				.getLowestUpperBoundFromAbove();
		if (lowestChildlessNeighbor == NullConnection.getNullConnection()) {
			return this;
		} else {
			return lowestChildlessNeighbor.insertionStageTwo();
		}
	}

	@Override
	public String toString() {
		if (this.equals(NodeCore.getNullNode())) {
			return "Null Node";
		}
		return "WebIdVal = " + Integer.toBinaryString(this.getWebIdValue()) + ", height = " + getHeight();
	}

	public void accept(Visitor visitor, Parameters parameters) {
		visitor.visit(this, parameters);
	}

	public NodeCore getNodeCore() {
		return this;
	}
}
