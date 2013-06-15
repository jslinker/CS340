package hypeerweb.node.roles;

import hypeerweb.node.Node;
import hypeerweb.node.NodeInterface;
import hypeerweb.node.NodeState;

public class Fold extends NodeRole{
	
	public Fold(NodeInterface node){
		if(node == null){
			this.node = Node.NULL_NODE;
		} else{
			this.node = node.getNode();
		}
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
		replacementNode.setFold(this.node);
	}
	
	public String getType(){
		return "Fold";
	}
}
