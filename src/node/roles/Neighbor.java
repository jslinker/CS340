package node.roles;

import utilities.BitManipulation;
import node.Node;
import node.NodeInterface;
import node.NodeState;

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
	}
}
