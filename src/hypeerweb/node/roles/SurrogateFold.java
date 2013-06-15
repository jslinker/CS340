package hypeerweb.node.roles;

import hypeerweb.node.Node;
import hypeerweb.node.NodeInterface;

public class SurrogateFold extends NodeRole{

	public SurrogateFold(NodeInterface node){
		this.node = node.getNode();
	}

	@Override
	public void addConnection(Node aNode) {
		node.setInverseSurrogateFold(aNode);
	}

	@Override
	public void removeConnection(Node aNode) {
		node.setInverseSurrogateFold(Node.NULL_NODE);
	}
	
	@Override
	public void removeConnection(Node aNode, Node parent) {
		removeConnection(aNode);
	}

	@Override
	public void replaceConnection(Node aNode, Node replacementNode) {
		node.setInverseSurrogateFold(replacementNode);
		replacementNode.setSurrogateFold(this.node);
	}

	@Override
	public String getType() {
		return "SurrogateFold";
	}
}
