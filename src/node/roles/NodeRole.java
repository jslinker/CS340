package node.roles;

import node.Node;
import node.NodeInterface;
import node.SimplifiedNodeDomain;
import node.WebId;

public abstract class NodeRole implements NodeInterface{
	protected Node node = null;

	public void setWebId(WebId webId) {
		node.setWebId(webId);
	}

	public void setFold(Node newFold) {
		node.setFold(newFold);
	}

	public void setSurrogateFold(Node newSurrogateFold) {
		node.setSurrogateFold(newSurrogateFold);
	}

	public void setInverseSurrogateFold(Node newInverseSurrogateFold) {
		node.setInverseSurrogateFold(newInverseSurrogateFold);
	}

	public void addNeighbor(Node neighbor) {
		node.addNeighbor(neighbor);
	}

	public void addUpPointer(Node upPointer) {
		node.addUpPointer(upPointer);
	}

	public void addDownPointer(Node downPointer) {
		node.addDownPointer(downPointer);
	}

	public void addToHyPeerWeb(Node newNode) {
		node.addToHyPeerWeb(newNode);
	}

	public void removeNeighbor(Node neighbor) {
		node.removeNeighbor(neighbor);
	}

	public void removeUpPointer(Node upPointer) {
		node.removeUpPointer(upPointer);
	}

	public void removeDownPointer(Node downPointer) {
		node.removeDownPointer(downPointer);
	}

	public SimplifiedNodeDomain constructSimplifiedNodeDomain() {
		return node.constructSimplifiedNodeDomain();
	}

	public void changeFold(Node newFold) {
		node.changeFold(newFold);
	}

	@Override
	public void addConnection(Node aNode) {
		node.addConnection(aNode);
	}

	@Override
	public void removeConnection(Node aNode) {
		node.removeConnection(aNode);
	}

	@Override
	public void replaceConnection(Node aNode, Node replacementNode) {
		node.replaceConnection(aNode, replacementNode);
	}
	
	public int getWebIdValue(){
		return node.getWebIdValue();
	}
	
	public boolean equals(Object other){
		return node.equals(other);
	}
	
	public Node getNode(){
		return this.node;
	}
}
