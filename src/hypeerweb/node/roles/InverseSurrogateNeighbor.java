package hypeerweb.node.roles;

import hypeerweb.node.Node;
import hypeerweb.node.NodeInterface;

public class InverseSurrogateNeighbor extends NodeRole{

	public InverseSurrogateNeighbor(NodeInterface node){
		this.node = node.getNode();
	}

	@Override
	public void addConnection(Node aNode) {
		node.addDownPointer(aNode);
	}

	@Override
	public void removeConnection(Node aNode) {
		node.removeDownPointer(aNode);
	}
	
	@Override
	public void removeConnection(Node aNode, Node parent) {
		removeConnection(aNode);
	}

	@Override
	public void replaceConnection(Node aNode, Node replacementNode) {
		node.removeDownPointer(aNode);
		node.addDownPointer(replacementNode);
		replacementNode.addUpPointer(this.node);
	}
	
	public String getType(){
		return "InverseSurrogateNeighbor";
	}
}
