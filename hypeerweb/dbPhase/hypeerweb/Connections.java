package dbPhase.hypeerweb;

import java.util.HashSet;
import java.util.Iterator;


/**
 * @author Daniel Carrier
 * 
 */
public class Connections implements Iterable<Connection> {
	private int webId;
	private NodeList neighbors;
	private NodeList upPointers;
	private NodeList downPointers;
	private Connection fold;
	private Connection surrogateFold;
	private Connection inverseSurrogateFold;
	NodeState state;

	/**
	 * Constructs the connections. The webID must be given in the constructor
	 * because it's used in how it stores its neighbors.
	 * 
	 * @param webId
	 *            The integer representing the webID
	 */
	public Connections(int webId) {
		this.webId = webId;
		neighbors = new NodeList();
		upPointers = new NodeList();
		downPointers = new NodeList();
		fold = NullConnection.getNullConnection();
		surrogateFold = NullConnection.getNullConnection();
		inverseSurrogateFold = NullConnection.getNullConnection();
		if (webId == 0) {
			state = NodeState.NodeZeroState;
		} else {
			state = NodeState.StandardNodeState;
		}
	}

	public void setState(NodeState newState) {
		state = newState;
	}

	public NodeState getState() {
		return state;
	}

	/**
	 * Returns a SimplifiedNodeDomain that represents the connections, as
	 * defined in SimplifiedNodeDomeain.
	 * 
	 * @return SimplifeidNodeDomain that represents the connections
	 */
	public SimplifiedNodeDomain constructSimplifiedNodeDomain() {
		HashSet<Integer> intNeighbors = new HashSet<Integer>();
		HashSet<Integer> intDownPointers = new HashSet<Integer>();
		for (Node neighbor : neighbors) {
			intNeighbors.add(neighbor.getWebIdValue());
		}
		HashSet<Integer> intUpPointers = new HashSet<Integer>();
		for (Node upPointer : upPointers) {
			intUpPointers.add(upPointer.getWebIdValue());
		}
		for (Node downPointer : downPointers) {
			intDownPointers.add(downPointer.getWebIdValue());
		}
		// It seems to have the same neighbor twice, which should be impossible.
		/*
		 * if(intNeighbors.size() != neighbors.numberOfNonNullElements()) {
		 * System.err.println("test"); }
		 */
		return new SimplifiedNodeDomain(webId, getHeight(), intNeighbors,
				intUpPointers, intDownPointers, getFold().getWebIdValue(),
				getSurrogateFold().getWebIdValue(),
				inverseSurrogateFold.getWebIdValue(), state.intValue());
	}

	/**
	 * Removes all neighbor connections
	 */
	public void clearNeighbors() {
		neighbors = new NodeList();
	}

	/**
	 * Removes all up pointers, also known as inverse surrogate neighbors.
	 */
	public void clearUpPointers() {
		upPointers = new NodeList();
	}

	/**
	 * Removes all down pointers, also known as surrogate neighbors.
	 */
	public void clearDownPointers() {
		downPointers = new NodeList();
	}

	/**
	 * @obvious
	 */
	public NodeList getNeighbors() {
		return neighbors;
	}

	/**
	 * @obvious
	 */
	public NodeList getDownPointers() {
		return downPointers;
	}

	/**
	 * @obvious
	 */
	public NodeList getUpPointers() {
		return upPointers;
	}

	/**
	 * @obvious
	 */
	public Node getFold() {
		return fold;
	}

	/**
	 * @obvious
	 */
	public Node getSurrogateFold() {
		return surrogateFold;
	}

	/**
	 * Since the algorithms don't really care whether they have a fold or
	 * surrogate fold, and there's always exactly one of the two, this just
	 * returns whichever exists.
	 * 
	 * @return Fold or surrogate fold, whichever exists
	 */
	public Connection getFoldOrSurrogateFold() {
		if (fold == NullConnection.getNullConnection()) {
			return surrogateFold;
		} else {
			return fold;
		}
	}

	/**
	 * @obvious
	 */
	public Connection getInverseSurrogateFold() {
		return inverseSurrogateFold;
	}

	/**
	 * Returns the neighbor or down pointer in the given dimension. Since they
	 * can't both exist, that possibility is not a problem. If neither exists,
	 * it returns a NULL_NODE.
	 * 
	 * @param i
	 *            The dimension on which to look for the neighbor or down
	 *            pointer.
	 * @return The neighbor or down pointer in the given dimension, whichever
	 *         exists
	 */
	public Connection getNeighborOrDownPointer(int i) {
		Connection neighbor = neighbors.get(i);
		if (neighbor == NullConnection.getNullConnection()) {
			return downPointers.get(i);
		} else {
			return neighbor;
		}
	}

	/**
	 * Returns the neighbor in the given dimension, or NULL_NODE if there isn't
	 * one.
	 * 
	 * @param i
	 *            The dimension on which to look for the neighbor.
	 * @return The neighbor in the given dimension, or NULL_NODE if there isn't
	 *         one.
	 */
	public Connection getNeighbor(int i) {
		return neighbors.get(i);
	}

	/**
	 * Returns the up pointer in the given dimension, or NULL_NODE if there
	 * isn't one.
	 * 
	 * @param i
	 *            The dimension on which to look for the up pointer.
	 * @return The up pointer in the given dimension, or NULL_NODE if there
	 *         isn't one.
	 */
	public Connection getUpPointer(int i) {
		return upPointers.get(i);
	}

	/**
	 * Returns the height of the node, calculated by the number of neighbors.
	 * 
	 * @return The height of the node, calculated by the number of neighbors
	 */
	public int getHeight() {
		return neighbors.numberOfNonNullElements()
				+ downPointers.numberOfNonNullElements();
	}

	/**
	 * Sets the given node as a neighbor. If the node does not have a
	 * neighboring webID, this method may give unexpected results.
	 * 
	 * @param neighbor
	 */
	// TODO: Should we have this function? We currently have a copy of
	// lastDiffereingBit in Connections and in Node.
	// At least, I thought we did.
	public void setNeighbor(Connection neighbor) {
		assert Integer.bitCount(webId ^ neighbor.getWebIdValue()) == 1;
		int i = lastDifferingBit(neighbor);
		setNeighbor(neighbor, i);
	}

	public void removeDownPointer(Node downPointer) {
		downPointers.set(lastDifferingBit(downPointer),
				NullConnection.getNullConnection());
	}

	public void removeNeighbor(Node neighbor) {
		int i = lastDifferingBit(neighbor);
		neighbors.set(i, NullConnection.getNullConnection());
	}

	/**
	 * Sets the given node as a neighbor in the given dimension. If it does not
	 * have the webID of a neighbor in that direction, it will cause problems
	 * with the algorithms.
	 * 
	 * @param neighbor
	 * @param i
	 */
	public void setNeighbor(Connection neighbor, int i) {
		// TODO: There seems to be a part in phase one that connects nodes 1 and
		// 4 as neighbors. Is this supposed to happen?
		neighbors.set(i, neighbor);
		downPointers.set(i, NullConnection.getNullConnection());
	}

	/**
	 * @obvious
	 */
	public void setNeighbors(NodeList neighbors) {
		this.neighbors = neighbors;
	}

	/**
	 * Sets the given node as an up pointer. If the node does not have a webID
	 * corresponding to an up pointer, this method may give unexpected results.
	 * 
	 * @param upPointer
	 */
	public void setUpPointer(Connection upPointer) {
		assert Integer.bitCount(webId ^ upPointer.getWebIdValue()) == 2;
		upPointers.set(lastDifferingBit(upPointer), upPointer);
	}

	/**
	 * Sets the given node as an up pointer in the given dimension. If it does
	 * not have the webID of an up pointer in that direction, it will cause
	 * problems with the algorithms.
	 * 
	 * @param upPointer
	 * @param i
	 */
	public void setUpPointer(Connection upPointer, int i) {
		upPointers.set(i, upPointer);
	}

	/**
	 * @obvious
	 */
	public void setUpPointers(NodeList upPointers) {
		this.upPointers = upPointers;
	}

	/**
	 * @obvious
	 */
	public void removeUpPointer(Node upPointer) {
		upPointers.set(lastDifferingBit(upPointer),
				NullConnection.getNullConnection());
	}

	/**
	 * Removes the up pointer in the given dimension.
	 * 
	 * @param i
	 *            The dimension in which to remove an up pointer.
	 */
	public void removeUpPointer(int i) {
		upPointers.set(i, NullConnection.getNullConnection());
	}

	/**
	 * Sets the given node as a down pointer. If the node does not have a webID
	 * corresponding to a down pointer, this method may give unexpected results.
	 * 
	 * @param downPointer
	 */
	public void setDownPointer(Connection downPointer) {
		downPointers.set(lastDifferingBit(downPointer), downPointer);
	}

	/**
	 * Sets the given node as a down pointer in the given dimension. If it does
	 * not have the webID of a down pointer in that direction, it will cause
	 * problems with the algorithms.
	 * 
	 * @param downPointer
	 * @param i
	 */
	public void setDownPointer(Connection downPointer, int i) {
		downPointers.set(i, downPointer);
	}

	/**
	 * @obvious
	 */
	public void setFold(Connection fold) {
		if (fold == null) {
			assert true;
		}
		this.fold = fold;
	}

	/**
	 * @obvious
	 */
	public void setSurrogateFold(Connection surrogateFold) {
		this.surrogateFold = surrogateFold;
	}

	/**
	 * @obvious
	 */
	public void setInverseSurrogateFold(Connection inverseSurrogateFold) {
		this.inverseSurrogateFold = inverseSurrogateFold;
	}

	/**
	 * @obvious
	 */
	public void removeInverseSurrogateFold() {
		inverseSurrogateFold = NullConnection.getNullConnection();
	}

	/**
	 * @obvious
	 */
	public Node getParent() {
		int t = this.getHeight() - 1;
		int mask = 1 << (this.getHeight() - 1);
		if ((webId & mask) == 0) {
			return NullConnection.getNullConnection();
		} else {
			return neighbors.get(this.getHeight() - 1);
		}
	}

	/**
	 * @obvious
	 */
	public Node getChild() {
		int mask = 1 << (this.getHeight() - 1);
		if ((webId & mask) == 0) {
			return neighbors.get(this.getHeight() - 1);
		} else {
			return NullConnection.getNullConnection();
		}
	}

	@Override
	public String toString() {
		return Integer.toBinaryString(webId);
	}

	/**
	 * Returns the last bit that's different between the webId of the given node
	 * and the one with these connections.
	 * 
	 * @param node
	 *            The node of the webId to compare this with
	 * @return The last differing bit
	 */
	private int lastDifferingBit(Node node) {
		int xor = node.getWebIdValue() ^ webId;
		return Integer.numberOfTrailingZeros(xor);
	}

	public void setWebID(int id) {
		webId = id;
	}

	/**
	 * Returns the highest connection, or NullConnection.getNullConnection() if
	 * the node containing these Connections is the highest. It might be better
	 * to have Connections contain the parent node, and return that.
	 * 
	 * @return The highest connection, or NullConnection.getNullConnection() if
	 *         the node containing these Connections is the highest.
	 */
	public Connection getHighestConnection() {
		int maxWebId = webId;
		Connection out = NullConnection.getNullConnection();
		for (Connection neighbor : neighbors) {
			if (neighbor.getWebIdValue() > maxWebId) {
				maxWebId = neighbor.getWebIdValue();
				out = neighbor;
			}
		}
		
		if (fold.getWebIdValue() > maxWebId) {
			return fold;
		} else {
			return out;
		}
	}

	public Connection getLowestUpperBoundFromBelow() {
		int minWebId = Integer.MAX_VALUE;
		Connection out = NullConnection.getNullConnection();
		for (Connection downPointer : downPointers) {
			if (downPointer.getWebIdValue() < minWebId) {
				minWebId = downPointer.getWebIdValue();
				out = downPointer;
			}
		}
		return out;
	}

	public Connection getLowestUpperBoundFromAbove() {
		int minWebId = webId;
		Connection out = NullConnection.getNullConnection();
		for (Connection neighbor : neighbors) {
			if (neighbor.getWebIdValue() < minWebId
					&& neighbor.getHeight() == this.getHeight()) {
				minWebId = neighbor.getWebIdValue();
				out = neighbor;
			}
		}
		return out;
	}

	public Iterator<Connection> iterator() {
		return new ConnectionsIterator(this);
	}

	/*
	 * There is no removeNeighbor or removeDownPointer. This is because those
	 * should never be removed without replacement. For example, setNeighbor
	 * will automatically replace the corresponding downPointer.
	 */
	/*
	 * There are a few others that were missing for similar reasons, but I never
	 * got around to adding. Feel free to add them if you wish.
	 */
}
