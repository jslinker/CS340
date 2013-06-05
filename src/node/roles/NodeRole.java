package node.roles;

import hypeerweb.broadcast.Contents;
import hypeerweb.broadcast.Parameters;
import hypeerweb.broadcast.Visitor;
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
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeRole other = (NodeRole) obj;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		return true;
	}

	@Override
	public Node getNode(){
		return node;
	}
	
	@Override 
	public Contents getContents(){
		return node.getContents();
	}
	
	@Override
	public void accept(Visitor visitor, Parameters parameters){
		node.accept(visitor, parameters);
	}
	
	public abstract String getType();
	
	public String toString(){
		return getType() + " " + node.toString();
	}
}
