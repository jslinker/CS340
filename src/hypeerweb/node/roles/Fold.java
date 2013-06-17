package hypeerweb.node.roles;

import hypeerweb.node.Node;
import hypeerweb.node.NodeInterface;

/**
 * Extends the abstract class NodeRole.  This class contains a single node that is a fold to another node.
 * Implements the delegate pattern to ease the modifying of connections.
 * @domain See NodeRole.
 * @invariant See NodeRole.
 * @author Nathan Zabriskie
 *
 */
public class Fold extends NodeRole{
	
	/**
	 * Constructs a new Fold object.
	 * @param node The node that will be contained in this wrapper class.
	 * @pre NONE
	 * @post this.node = node. If node = null, this.node = Node.NULL_NODE
	 */
	public Fold(NodeInterface node){
		if(node == null){
			this.node = Node.NULL_NODE;
		} else{
			this.node = node.getNode();
		}
	}

	/**
	 * Overrides the addConnection of NodeRole.  Adds the given node as the fold of the node
	 * contained in this wrapper.
	 * @param aNode The node that will be set as the fold of the node contained in this wrapper.
	 * @pre node != null 
	 * @post this.node.fold = aNode
	 */
	@Override
	public void addConnection(Node aNode) {
		node.setFold(aNode);
	}

	/**
	 * Removes the fold of the node contained in this wrapper.
	 * @param aNode Not used by this method.  It is present only for other extensions of nodeRole.
	 * @pre node != null 
	 * @post node.fold = Node.NULL_NODE
	 */
	@Override
	public void removeConnection(Node aNode) {
		node.setFold(Node.NULL_NODE);
	}
	
	/**
	 * Removes the given node from all connections of the contained node.
	 * @param aNode The node that is to be removed.
	 * @param parent Not used by this method. It is present only for other extension of nodeRole.
	 * @pre node != null 
	 * @post aNode has been removed from all connections
	 */
	@Override
	public void removeConnection(Node aNode, Node parent) {
		removeConnection(aNode);
	}

	/**
	 * Replaces the current fold of node and updates its new fold to point back to node.
	 * @param aNode Not used by this method. It is present only for other extension of nodeRole.
	 * @param replacementNode The node that will replaces node's fold and will have its fold set to node.
	 * @pre node != null AND replacementNode != null  
	 * @post node.fold = replacementNode.  replacementNode.fold = node
	 */
	@Override
	public void replaceConnection(Node aNode, Node replacementNode) {
		node.setFold(replacementNode);
		replacementNode.setFold(this.node);
	}
	
	/**
	 * Used by the getString method of NodeRole to get what type of NodeRole this is.
	 * @pre NONE
	 * @post result = "Fold"
	 */
	public String getType(){
		return "Fold";
	}
}
