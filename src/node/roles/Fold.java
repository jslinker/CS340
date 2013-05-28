package node.roles;

import node.Node;
import node.NodeInterface;
import node.NodeState;

public class Fold extends NodeRole{
	
	public Fold(NodeInterface node){
		this.node = node.getNode();
	}

	@Override
	public void addConnection(Node aNode) {
		node.setFold(aNode);
	}

	@Override
	public void removeConnection(Node aNode) {
		node.setFold(Node.NULL_NODE);
	}
	
	@Override
	public void removeConnection(Node aNode, Node parent) {
		removeConnection(aNode);
	}

	@Override
	public void replaceConnection(Node aNode, Node replacementNode) {
		node.setFold(replacementNode);
	}
}
