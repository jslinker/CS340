package node.roles;

import node.Node;
import node.NodeInterface;

public class SurrogateNeighbor extends NodeRole{

	public SurrogateNeighbor(NodeInterface node){
		this.node = node.getNode();
	}

	@Override
	public void addConnection(Node aNode) {
		addUpPointer(aNode);
	}

	@Override
	public void removeConnection(Node aNode) {
		removeUpPointer(aNode);
	}
	
	public void removeConnection(Node aNode, Node parent){
		removeUpPointer(aNode);
	}

	@Override
	public void replaceConnection(Node aNode, Node replacementNode) {
		removeUpPointer(aNode);
		addUpPointer(replacementNode);
	}
}
