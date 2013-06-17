package hypeerweb.node.roles;

import hypeerweb.node.Node;
import hypeerweb.node.NodeInterface;
import hypeerweb.node.NodeState;
import utilities.BitManipulation;

public class Neighbor extends NodeRole{

	public Neighbor(NodeInterface node){
		this.node = node.getNode();
	}

	@Override
	public void addConnection(Node aNode) {
		node.addNeighbor(aNode);
	}
	
	@Override
	public void removeConnection(Node aNode) {
		removeNeighbor(aNode);
	}

	@Override
	public void removeConnection(Node aNode, Node parent) {
		removeNeighbor(aNode);
		addDownPointer(parent);
		parent.addUpPointer(node);
		NodeState.setNodeState(node);
	}

	@Override
	public void replaceConnection(Node aNode, Node replacementNode) {
		node.removeNeighbor(aNode);
		node.addNeighbor(replacementNode);
		replacementNode.addNeighbor(this.node);
	}
	
	public String getType(){
		return "Neighbor";
	}
}
