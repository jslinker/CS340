package node;


import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.HashSet;
import node.states.*;
import static utilities.BitManipulation.*;

/**
 * @author Joseph
 */
public class Node implements Comparable<Node>{
	
	private WebId webId = null;
	private int height = -1;
	private Connections connections;
	private NodeState state = new StandardNodeState();
	
	private final int DEFAULT_STATE = 0;
	
	public static final Node NULL_NODE = new Node(){
		@Override public void addDownPointer(Node downPointer){ return; }
		@Override public void addNeighbor(Node neighbor){ return; }
		@Override public void addUpPointer(Node upPointer){ return; }
		@Override public void removeDownPointer(Node downPointer){ return; }
		@Override public void removeNeighbor(Node neighbor){ return; }
		@Override public void removeUpPointer(Node upPointer){ return; }
		@Override public void setWebId(WebId webId){ return; }
		@Override public void setFold(Node fold){ return; }
		@Override public void setSurrogateFold(Node surrogateFold){ return; }
		@Override public void setInverseSurrogateFold(Node inverseSurrogateFold){ return; }
	};
	
	private Node(){
		webId = WebId.NULL_WEB_ID;
		connections = new Connections();
		connections.setFold(this);
		connections.setSurrogateFold(this);
		connections.setInverseSurrogateFold(this);
	}

	public Node(int id){
		assert(id >= 0);
		webId = new WebId(id);
		connections = new Connections();
		connections.setFold(this);
		state = new StandardNodeState();
		this.height = webId.getHeight();
	}
	
	public Node(int id, int height){
		assert(id >= 0 && height >= 0);
		webId = new WebId(id, height);
		connections = new Connections();
		connections.setFold(this);
		state = new StandardNodeState();
		this.height = webId.getHeight();
	}
	
	/**
	 * Constructs a SimplifiedNodeDomain object from the attributes of this Node.
	 * @return The created SimplifiedNodeDomain object.
	 */
	public SimplifiedNodeDomain constructSimplifiedNodeDomain(){
		HashSet<Integer> neighborIds = new HashSet<Integer>();
		for(Node n : getNeighbors()) {
			neighborIds.add(n.getWebIdValue());
		}
		
		HashSet<Integer> upIds = new HashSet<Integer>();
		for(Node n : getUpPointers()){
			upIds.add(n.getWebIdValue());
		}
		
		HashSet<Integer> downIds = new HashSet<Integer>();
		for(Node n : getDownPointers()){
			downIds.add(n.getWebIdValue());
		}
		
		return new SimplifiedNodeDomain(webId.getValue(), this.getHeight(), neighborIds, upIds, downIds, 
									    getFold().getWebIdValue(), getSurrogateFold().getWebIdValue(), 
									    getInverseSurrogateFold().getWebIdValue(), state.getStateId());
	}
	
	/**
	 * Adds the node to the HyPeerWeb.
	 * @param newNode The new Node to add to the HyPeerWeb.
	 * @pre newNode is not null AND newNode is not NULL_NODE
	 * @post newNode is properly connected to the HyPeerWeb
	 */
	public void addToHyPeerWeb(Node newNode){
		assert (newNode != null && newNode != NULL_NODE);
		Node insertionPoint = findInsertionPoint();
		int childWebId = calculateChildWebId(insertionPoint.getWebIdValue(), insertionPoint.getHeight());
		newNode.setWebId(new WebId(childWebId));
		insertionPoint.addChild(newNode);
	}
	
	private Node findBiggestNeighbor(){
		if(getBiggestNeighbor().equals(Node.NULL_NODE)){
			return this;
		} else{
			return getBiggestNeighbor().findBiggestNeighbor();
		}
	}
	
	public Node findNodeZero(){
		return null;
	}
	
	protected Node findInsertionPoint(){
		Node biggestNeighbor = findBiggestNeighbor();
		Node insertionPoint = biggestNeighbor.squeeze();
		return insertionPoint;
	}
	
	private Node squeeze() {
	
		Node upperBound = NULL_NODE;
		Node lowerBound = NULL_NODE;
		
		if(this.hasDownPointers()) {
			
			upperBound = this.getLowestDownPointer();
		}
		
		else {
			return this;
		}
		
		if(upperBound.hasUpPointers()) {
			
			lowerBound = upperBound.getHighestUpPointer();
		}
		
		else {
			return upperBound;
		}
		
		return lowerBound.squeeze();
	}
	
	private boolean hasUpPointers() {
		if(getUpPointers().isEmpty()){
			return false;
		}
		return true;
	}
	
	private boolean hasDownPointers() {
		if(getDownPointers().isEmpty()){
			return false;
		}
		return true;
	}
	
	private Node getLowestDownPointer() {
		// Assuming sorted order - ascending
		assert ! getDownPointers().isEmpty();
		return getDownPointers().first();
	}
	
	private Node getHighestUpPointer() {
		// Assuming sorted order - ascending
		assert ! getUpPointers().isEmpty();
		return getUpPointers().last();
	}

	public Node findDeletionPoint(){
		return null;
	}
	
	public Node getParent(){
		return null;
	}
	
	public Node findNode(int webId){
		return null;
	}
	
	/**
	 * Adds the given node to the HyPeerWeb.
	 * @param child The child node of this node.
	 * @pre The child node has the appropriate webId (webId = N + 1) and the child node is a
	 * child of this node.
	 * @post The child node is connected to the HyPeerWeb, all connections are set, 
	 * this node's height is correct.
	 */
	public void addChild(Node child){
		this.height++;
		
		Connections childConnections = new Connections();
		child.setConnections(childConnections);
		
		//set child node's lower neighbors
		TreeSet<Node> upPointers = connections.getUpPointers();
		childConnections.setLowerNeighbors(upPointers);
		connections.setUpPointers(new TreeSet<Node>());
		//notify child node's lower neighbors
		Iterator<Node> childsLowerNeighbors = upPointers.iterator();
		while(childsLowerNeighbors.hasNext()){
			Node lowerNeighbor = childsLowerNeighbors.next();
			lowerNeighbor.removeDownPointer(this);
			lowerNeighbor.addNeighbor(child);
		}
		
		//set child node's surrogate neighbors
		Iterator<Node> childsSurrogateNeighbors = connections.getUpperNeighbors().iterator();
		while(childsSurrogateNeighbors.hasNext()){
			Node surrogateNeighbor = childsSurrogateNeighbors.next();
			childConnections.addDownPointer(surrogateNeighbor);
			surrogateNeighbor.addUpPointer(child);
		}
		
		//set all of the folds
		Node childsNewFold = null;
		if(connections.hasInverseSurrogateFold()){
			childsNewFold = this.getInverseSurrogateFold();
			child.setFold(childsNewFold);
			childsNewFold.setFold(child);
			childsNewFold.setSurrogateFold(NULL_NODE);
			this.setInverseSurrogateFold(NULL_NODE);
		}
		else{
			childsNewFold = this.getFold();
			child.setFold(childsNewFold);
			childsNewFold.setFold(child);
			if(this.getWebIdValue() == 0 && this.getHeight() == 1){
				this.setFold(child);
			}
			else{
				this.setSurrogateFold(childsNewFold);
				this.setFold(Node.NULL_NODE);
				childsNewFold.setInverseSurrogateFold(this);
			}
		}
		
		//add child's first neighbor
		childConnections.addLowerNeighbor(this);
		//add child as neighbor last
		connections.addUpperNeighbor(child);
		
		
	}
	
	//------------------
	//  A D D E R S
	//------------------
	public void addDownPointer(Node downPointer){
		connections.addDownPointer(downPointer);
	}
	
	public void addNeighbor(Node neighbor){
		if(neighbor.getWebIdValue() > this.getWebIdValue()){
			connections.addUpperNeighbor(neighbor);
		}
		else{
			connections.addLowerNeighbor(neighbor);
		}
	}
	
	public void addUpPointer(Node upPointer){
		connections.addUpPointer(upPointer);
	}
	
	//--------------------
	//  R E M O V E R S
	//--------------------	
	public void removeDownPointer(Node downPointer){
		connections.removeDownPointer(downPointer);
	}
	
	public void removeNeighbor(Node neighbor){
		if(neighbor.getWebIdValue() > this.getWebIdValue()){
			connections.removeUpperNeighbor(neighbor);
		}
		else{
			connections.removeLowerNeighbor(neighbor);
		}
	}
	
	public void removeUpPointer(Node upPointer){
		connections.removeUpPointer(upPointer);
	}
	
	public void setState(NodeState state){
		this.state = state;
	}
	
	//------------------
	//  G E T T E R S
	//------------------
	public WebId getWebId(){
		return webId;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWebIdValue(){
		return webId.getValue();
	}
	
	public int getWebIdHeight(){
		return webId.getHeight();
	}
	
	public TreeSet<Node> getDownPointers(){
		return connections.getDownPointers();
	}
	
	public TreeSet<Node> getUpPointers(){
		return connections.getUpPointers();
	}
	
	public TreeSet<Node> getNeighbors(){
		TreeSet<Node> result = new TreeSet<Node>();
		result.addAll(connections.getLowerNeighbors());
		result.addAll(connections.getUpperNeighbors());
		return result;
	}
	
	public Node getFold(){
		return connections.getFold();
	}
	
	public Node getSurrogateFold(){
		return connections.getSurrogateFold();
	}
	
	public Node getInverseSurrogateFold(){
		return connections.getInverseSurrogateFold();
	}
	
	public NodeState getState(){
		return state;
	}
	
	public Connections getConnections(){
		return connections;
	}
	
	public Node getBiggestNeighbor(){
		return connections.getBiggestNeighbor();
	}
	
	//------------------
	//  S E T T E R S
	//------------------
	public void setFold(Node fold){
		if(fold == null) {
			connections.setFold(Node.NULL_NODE);
		}
		else {
			connections.setFold(fold);
		}
	}
	
	public void setInverseSurrogateFold(Node inverseSurrogateFold){
		if(inverseSurrogateFold == null) {
			connections.setInverseSurrogateFold(Node.NULL_NODE);
		}
		else {
			connections.setInverseSurrogateFold(inverseSurrogateFold);
		}
	}
	
	public void setSurrogateFold(Node surrogateFold){
		if(surrogateFold == null) {
			connections.setSurrogateFold(Node.NULL_NODE);
		}
		else {
			connections.setSurrogateFold(surrogateFold);
		}
	}
	
	public void setWebId(WebId webId){
		if(webId == null) {
			this.webId = WebId.NULL_WEB_ID;
		}
		else {
			this.webId = webId;
		}
	}
	
	public void setBiggestNeighbor(Node biggestNeighbor){
		connections.setBiggestNeighbor(biggestNeighbor);
	}
	
	/**
	 * Sets this nodes connections. Should only need to be called by the addChild method.
	 * @param connections This nodes new connections.
	 * @pre connections is not null
	 * @post this.connections = connections
	 */
	private void setConnections(Connections connections){
		assert connections != null;
		this.connections = connections;
	}
	
	@Override
	public String toString(){
		return "Node [webId=" + webId + "]";
	}

	@Override
	public int hashCode(){
		return webId.hashCode();
	}

	@Override
	public boolean equals(Object obj){
		
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Node other = (Node) obj;
		
		return this.webId == other.webId;
	}

	@Override
	public int compareTo(Node other) {
		if(getWebIdValue() > other.getWebIdValue()){
			return 1;
		} else if(getWebIdValue() < other.getWebIdValue()){
			return -1;
		} else {
			return 0;
		}
	}
}
