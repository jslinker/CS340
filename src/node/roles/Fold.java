package node.roles;

import node.Node;

public class Fold extends NodeRole{
	
	public Fold(Node node){
		this.node = node;
	}

	@Override
	public void addConnection(Node aNode) {
	}

	@Override
	public void removeConnection(Node aNode) {
	}

	@Override
	public void replaceConnection(Node aNode, Node replacementNode) {
	}
}
