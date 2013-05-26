package node.roles;

import node.Node;
import node.NodeInterface;

public class InverseSurrogateFold extends NodeRole{

	public InverseSurrogateFold(NodeInterface node){
		this.node = node.getNode();
	}

	@Override
	public void addConnection(Node aNode) {
	}

	@Override
	public void removeConnection(Node aNode) {
	}

	@Override
	public void removeConnection(Node aNode, Node parent) {
	}
	
	@Override
	public void replaceConnection(Node aNode, Node replacementNode) {
	}
}
