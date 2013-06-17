package hypeerweb.node.roles;

import identification.GlobalObjectId;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

import communicator.PeerCommunicator;
import communicator.PortNumber;

import hypeerweb.broadcast.Contents;
import hypeerweb.broadcast.Parameters;
import hypeerweb.broadcast.Visitor;
import hypeerweb.node.Node;
import hypeerweb.node.NodeInterface;
import hypeerweb.node.NodeProxy;
import hypeerweb.node.SimplifiedNodeDomain;
import hypeerweb.node.WebId;

/**
 * An abstract class whose overriding classes each represent a type of connection 
 * that can exist in the HyPeerWeb (Neighbor, SurrogateNeighbor, InverseSurrogateNeighbor,
 * Fold, SurrogateFold, InverseSurrogateFold).  These classes follow the delegation pattern
 * to allow for easier replacement of nodes in the web and adding and removing specific types of connections.
 * 
 *  @pre
 *  <b>Domain</b><br>
 *  &#09 node : Node<br><br>
 *
 *	<b>Invariant</b><br>
 *  &#09 node != null AND<br>
 *  &#09 node != Node.NULL_NODE<br>
 */
public abstract class NodeRole implements NodeInterface, Serializable{
	
	/**
	 * The node contained in this wrapper class.
	 */
	protected Node node = null;

	/**
	 * Sets the WebId of the node contained in this wrapper.
	 * @param webId The int value of the new WebId.
	 * @pre Node != null AND Node != Node.NULL_NODE
	 * @post node.webId = webId
	 */
	public void setWebId(WebId webId) {
		node.setWebId(webId);
	}

	/**
	 * Sets the fold of the node contained in this wrapper.
	 * @param newFold The new fold of the node.
	 * @pre node != null AND node != Node.NULL_NODE
	 * @post node.fold = newFold
	 */
	public void setFold(Node newFold) {
		node.setFold(newFold);
	}

	/**
	 * Sets the surrogate fold of the node contained in this wrapper.
	 * @param newSurrogateFold The new surrogate fold of the node.
	 * @pre node != null AND node != Node.NULL_NODE
	 * @post node.surrogateFold = newSurrogateFold
	 */
	public void setSurrogateFold(Node newSurrogateFold) {
		node.setSurrogateFold(newSurrogateFold);
	}

	/**
	 * Sets the inverse surrogate fold of the node contained in this wrapper.
	 * @param newInverseSurrogateFold The new inverse surrogate fold of the node.
	 * @pre node != null AND node != Node.NULL_NODE
	 * @post node.inverseSurrogateFold = newInverseSurrogateFold
	 */
	public void setInverseSurrogateFold(Node newInverseSurrogateFold) {
		node.setInverseSurrogateFold(newInverseSurrogateFold);
	}

	/**
	 * Adds a neighbor to the node contained in this wrapper.
	 * @param neighbor The neighbor to be added 
	 * @pre neighbor != null AND neighbor != Node.NULL_NODE AND node != null AND node != Node.NULL_NODE
	 * @post node.neighbors.contains(neighbor)
	 */
	public void addNeighbor(NodeInterface neighbor) {
		node.addNeighbor(neighbor);
	}

	/**
	 * Adds an up pointer to the Node contained in this wrapper.
	 * @param upPointer The up pointer to be added to the node
	 * @pre upPointer != null AND upPointer != Node.NULL_NODE AND node != null AND node != Node.NULL_NODE
	 * @post node.upPointers.contains(upPointer)
	 */
	public void addUpPointer(Node upPointer) {
		node.addUpPointer(upPointer);
	}

	/**
	 * Adds an down pointer to the Node contained in this wrapper.
	 * @param downPointer The down pointer to be added to the node
	 * @pre downPointer != null AND downPointer != Node.NULL_NODE AND node != null AND node != Node.NULL_NODE
	 * @post node.downPointers.contains(downPointer)
	 */
	public void addDownPointer(Node downPointer) {
		node.addDownPointer(downPointer);
	}

	/**
	 * Adds a new node to the HyPeerWeb starting atthe node contained in this wrapper.
	 * @param newNode The node that is to be added to the HyPeerWeb
	 * @pre newNode != null AND newNode != Node.NULL_NODE AND node != null AND node != Node.NULL_NODE
	 * @post HyPeerWeb.contains(newNode) AND all connections are updated
	 */
	public void addToHyPeerWeb(Node newNode) {
		node.addToHyPeerWeb(newNode);
	}

	/**
	 * Removes the specified neighbor from the neighbor list in the node in this wrapper.
	 * @param neighbor The neighbor to be removed
	 * @pre node != null AND node != Node.NULL_NODE
	 * @post !node.neighbors.contains(neighbor)
	 */
	public void removeNeighbor(Node neighbor) {
		node.removeNeighbor(neighbor);
	}

	/**
	 * Removes the specified upPointer from the upPointer list for the node contained in this wrapper.
	 * @param upPointer The node that will be removed from the upPointer list
	 * @pre node != null AND node != Node.NULL_NODE
	 * @post !node.upPointers.contains(upPointer)
	 */
	public void removeUpPointer(Node upPointer) {
		node.removeUpPointer(upPointer);
	}

	/**
	 * Removes the specified node from the downPointer list for the node contained in this wrapper.
	 * @param downPointer The node that will be removed from the upPointer list.
	 * @pre node != null AND node != Node.NULL_NODE
	 * @post !node.downPointers.contains(downPointer)
	 */
	public void removeDownPointer(Node downPointer) {
		node.removeDownPointer(downPointer);
	}

	/**
	 * Constructs the simplifiedNodeDomain representing the node contained in this wrapper.
	 * @pre node != null AND node != Node.NULL_NODE
	 * @post result = node.constructSimplifiedNodeDomain()
	 */
	public SimplifiedNodeDomain constructSimplifiedNodeDomain() {
		return node.constructSimplifiedNodeDomain();
	}

	/**
	 * Replaces the node's current fold with a new fold
	 * @param newFold The node that will become the node's new fold
	 * @pre node != null AND node != Node.NULL_NODE
	 * @post node.fold = newFold
	 */
	public void changeFold(Node newFold) {
		node.changeFold(newFold);
	}

	/**
	 * Adds a new connection to the node contained in this wrapper.
	 * @param aNode The node that will be added to the connection list
	 * @pre node != null AND node != Node.NULL_NODE AND aNode != null AND aNode != Node.NULL_NODE
	 * @post aNode has been added to the node's connections.
	 */
	@Override
	public void addConnection(Node aNode) {
		node.addConnection(aNode);
	}

	/**
	 * Removes a connection from the node contained in this wrapper.
	 * @param aNode The node that will be removed from the connection list.
	 * @pre
	 */
	@Override
	public void removeConnection(Node aNode) {
		node.removeConnection(aNode);
	}

	/**
	 * Remove connection and add connection in one fell swoop.
	 * See addConnection and removeConnection
	 */
	@Override
	public void replaceConnection(Node aNode, Node replacementNode) {
		node.replaceConnection(aNode, replacementNode);
	}
	
	/**
	 * Gets the webId of the node contained in this wrapper.
	 * @pre node != null AND node != Node.NULL_NODE
	 * @post result = node.getWebIdValue()
	 */
	public int getWebIdValue(){
		return node.getWebIdValue();
	}
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeRole other = (NodeRole) obj;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		return true;
	}

	/**
	 * Returns the node contained in this wrapper.
	 * @pre NONE
	 * @post result = node
	 */
	@Override
	public Node getNode(){
		return node;
	}
	
	/**
	 * Retrieves the contents of the node in this wrapper.
	 * @pre node != null AND node != Node.NULL_NODE
	 * @post result = node.getContents()
	 */
	@Override 
	public Contents getContents(){
		return node.getContents();
	}
	
	/**
	 * Passes on a visitor to the node contained in this wrapper
	 * @param visitor The visitor that the node will accept.
	 * @param parameters The parameters that accompany the visitor
	 * @pre node != null AND node != Node.NULL_NODE
	 * @post node has been visited
	 */
	@Override
	public void accept(Visitor visitor, Parameters parameters){
		node.accept(visitor, parameters);
	}
	
	public abstract String getType();
	
	public String toString(){
		return getType() + " " + node.toString();
	}
	
	/**
	 * Passes on a writeReplace request to the node contained in this wrapper during serialization
	 * @pre node != null AND node != Node.NULL_NODE
	 * @post result = new NodeProxy representing node
	 * @throws ObjectStreamException
	 */
	public Object writeReplace() throws ObjectStreamException{
		return node.writeReplace();
	}
}
