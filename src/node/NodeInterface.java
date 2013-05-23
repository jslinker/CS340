package node;

/**
 * All of the methods as defined by the Node specification.
 * Additionally, the addMe(), disconnectMe(), and replaceMe(Node) methods have been added.
 * @author Craig Jacobson
 *
 */
public interface NodeInterface{
	/*
	 * As defined by the Node specification.
	 */
	public void setWebId(WebId webId);
	public void setFold(Node newFold);
	public void setSurrogateFold(Node newSurrogateFold);
	public void setInverseSurrogateFold(Node newInverseSurrogateFold);
	public void addNeighbor(Node neighbor);
	public void addUpPointer(Node upPointer);
	public void addDownPointer(Node downPointer);
	public void addToHyPeerWeb(Node newNode);
	public void removeNeighbor(Node neighbor);
	public void removeUpPointer(Node upPointer);
	public void removeDownPointer(Node downPointer);
	public SimplifiedNodeDomain constructSimplifiedNodeDomain();
	public void changeFold(Node newFold);//duplicate method in Node specification
	
	/*
	 * Methods for moving nodes around.
	 */
	public void addConnection(Node aNode);
	public void removeConnection(Node aNode);
	public void replaceConnection(Node aNode, Node replacementNode);

	/*
	 * Other methods we may need in the interface.
	 */
}
