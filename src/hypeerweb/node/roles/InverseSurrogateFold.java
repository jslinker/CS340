package hypeerweb.node.roles;

import hypeerweb.node.Node;
import hypeerweb.node.NodeInterface;

public class InverseSurrogateFold extends NodeRole{

	public InverseSurrogateFold(NodeInterface node){
		this.node = node.getNode();
	}

	@Override
	public void addConnection(Node aNode) {
		node.setSurrogateFold(aNode);
	}

	@Override
	public void removeConnection(Node aNode) {
		node.setSurrogateFold(Node.NULL_NODE);
	}

	@Override
	public void removeConnection(Node aNode, Node parent) {
		removeConnection(aNode);
	}
	
	@Override
	public void replaceConnection(Node aNode, Node replacementNode) {
		node.setSurrogateFold(replacementNode);
	}
	
	public String getType(){
		return "InverseSurrogateFold";
	}
}
