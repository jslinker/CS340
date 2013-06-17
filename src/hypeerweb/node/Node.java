package hypeerweb.node;


import static utilities.BitManipulation.calculateChildWebId;
import hypeerweb.HyPeerWebSegment;
import hypeerweb.broadcast.Contents;
import hypeerweb.broadcast.Parameters;
import hypeerweb.broadcast.Visitor;
import identification.GlobalObjectId;
import identification.LocalObjectId;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import utilities.BitManipulation;

import communicator.MachineAddress;
import communicator.PeerCommunicator;
import communicator.PortNumber;

/**
 * @author Joseph
 */
public class Node implements NodeInterface, Comparable<Node>, Serializable{
	private static final long serialVersionUID = -8116887139991043435L;
	
	private WebId webId = null;
	private int height = -1;
	protected Connections connections = null;
	private NodeState state = NodeState.STANDARD;
	private Contents contents = new Contents();
	private LocalObjectId localObjectId = new LocalObjectId(); 
	
	public static final Node NULL_NODE = new Node(){
		private static final long serialVersionUID = -4818395442433123461L;
		
		@Override public void addDownPointer(Node downPointer){ return; }
		@Override public void addNeighbor(NodeInterface neighbor){ return; }
		@Override public void addUpPointer(Node upPointer){ return; }
		@Override public void removeDownPointer(Node downPointer){ return; }
		@Override public void removeNeighbor(Node neighbor){ return; }
		@Override public void removeUpPointer(Node upPointer){ return; }
		@Override public void removeFromHyPeerWeb(Node deleteNode){ return; }
		@Override public void setWebId(WebId webId){ return; }
		@Override public void setFold(Node fold){ return; }
		@Override public void setSurrogateFold(Node surrogateFold){ return; }
		@Override public void setInverseSurrogateFold(Node inverseSurrogateFold){ return; }
		@Override public void addChild(Node child){
			child.setWebId(new WebId(0));
			child.setConnections(new Connections());
			child.setFold(child);
			child.setState(NodeState.CAP);
		}
		@Override public void addToHyPeerWeb(Node newNode){
			this.addChild(newNode);
		}
		@Override public Node findNode(int webId){
			return this;
		}
		@Override public void accept(Visitor visitor, Parameters parameters){ return; }
		@Override public String toString(){
			return "NULL_NODE: webID = -1";
		}
	};
	
	protected Node(){
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
		this.setFold(this);
		this.height = webId.getHeight();
		if(id == 0){
			setState(NodeState.CAP);
		}
	}
	
	public Node(int id, int height){
		assert(id >= 0 && height >= 0);
		webId = new WebId(id, height);
		connections = new Connections();
		this.setFold(this);
		this.height = webId.getHeight();
	}
	
	/**
	 * Constructs a SimplifiedNodeDomain object from the attributes of this Node.
	 * @return The created SimplifiedNodeDomain object.
	 */
	public SimplifiedNodeDomain constructSimplifiedNodeDomain(){
		HashSet<Integer> neighborIds = new HashSet<Integer>();
		for(NodeInterface n : getNeighbors().values()) {
			neighborIds.add(n.getWebIdValue());
		}
		
		HashSet<Integer> upIds = new HashSet<Integer>();
		for(NodeInterface n : getUpPointers().values()){
			upIds.add(n.getWebIdValue());
		}
		
		HashSet<Integer> downIds = new HashSet<Integer>();
		for(NodeInterface n : getDownPointers().values()){
			downIds.add(n.getWebIdValue());
		}
		
		return new SimplifiedNodeDomain(webId.getValue(), this.getHeight(), neighborIds, upIds, downIds, 
									    getFold().getWebIdValue(), getSurrogateFold().getWebIdValue(), 
									    getInverseSurrogateFold().getWebIdValue(), state.STATE_ID);
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
		int childWebId = calculateChildWebId(insertionPoint);
		newNode.setWebId(childWebId);
		insertionPoint.addChild(newNode);
	}
	
	/**
	 * Removes the node from the HyPeerWeb.
	 * @param deleteNode The node that is to be deleted.
	 * @pre The node is in the web and is not null or NULL_NODE
	 * @post The node was removed from the web and replaced with the last node in the web.
	 */
	public synchronized void removeFromHyPeerWeb(Node deleteNode) {
		Node deletionPoint = findDeletionPoint();
		deletionPoint.disconnect();
		deleteNode.replaceWithOtherNode(deletionPoint);
	}

	/**
	 * Removes the node from the HyPeerWeb.
	 * @param replacementNode is the node that will be taking this nodes place
	 * @pre The node is in the web and is not null or NULL_NODE
	 * @post All pointers to this node will now be pointing to replacementNode
	 */
	public synchronized void replaceWithOtherNode(Node replacementNode) {
		if(!this.equals(replacementNode)) {
			replacementNode.clearConnections();
			replacementNode.setWebId(this.webId.getValue());
			replacementNode.setState(this.state);
			
			// Notify neighbors of the new node
			this.connections.replaceNode(this, replacementNode);
			
			//replacementNode.setConnections(this.connections);
			replacementNode.setHeight(this.height);
		}
	}
	
	/**
	 * Finds the insertion point.
	 * @return The insertion point.
	 */
	public synchronized Node findInsertionPoint(){
		return findLowerBoundUpperBoundPair().getUpperBound();
	}
	
	public synchronized void clearConnections(){
		connections.clear();
	}

	/**
	 * Finds the deletion point.
	 * @return The deletion point.
	 */
	public synchronized Node findDeletionPoint(){
		return findLowerBoundUpperBoundPair().getLowerBound();
	}
	
	/**
	 * Using the greedy algorithm and the squeeze algorithm, finds both
	 * the insertion point and deletion point.
	 * @return The Pair that contains the lower bound (deletion point) and the upper bound
	 * (insertion point).
	 */
	public Pair<Node> findLowerBoundUpperBoundPair(){
		Node largest = this.findLargest();
		Pair<Node> lowerBoundUpperBoundPair = new Pair<Node>(largest, Node.NULL_NODE);
		lowerBoundUpperBoundPair = largest.squeeze(lowerBoundUpperBoundPair);
		return lowerBoundUpperBoundPair;
	}
	
	/**
	 * Forwards the squeeze call to the NodeState. Helps eliminate message chains.
	 */
	public Pair<Node> squeeze(Pair<Node> lowerBoundUpperBoundPair){
		return this.getState().squeeze(lowerBoundUpperBoundPair);
	}
	
	/**
	 * Disconnects this node from the hypeerweb.
	 * @pre |web| >= 2, web.contains(this), this.connections.lowerNeighbors != null, this.webId = web.maxWebId
	 * @post web does not contain this.  Connections of surrounding nodes updated.
	 */
	public synchronized void disconnect() {
		assert(connections.getLowerNeighbors() != null && connections.getLowerNeighbors().size() != 0 &&
			   HyPeerWebSegment.getSingleton().sizeOfHyPeerWeb() >= 2);

		
		int parentId = BitManipulation.calculateSurrogateWebId(getWebIdValue());
		Node parent = this.findNode(parentId);
		
		parent.setHeight(parent.getHeight() - 1);
		parent.removeNeighbor(this);
		connections.removeLowerNeighbor(parent);
		
		for(NodeInterface connection: connections.getDisconnectNodeList()){
			connection.removeConnection(this, parent);
		}
		
		Node fold = this.getFold();
		if(fold.equals(parent)){
			parent.setFold(parent);
			parent.setInverseSurrogateFold(NULL_NODE);
			parent.setSurrogateFold(NULL_NODE);
		}
		else if(parent.hasSurrogateFold()){
			parent.setFold(fold);
			parent.setInverseSurrogateFold(NULL_NODE);
			parent.setSurrogateFold(NULL_NODE);
			fold.setFold(parent);
			fold.setInverseSurrogateFold(NULL_NODE);
		}
		else {
			fold.setFold(NULL_NODE);
			fold.setSurrogateFold(parent);
			parent.setInverseSurrogateFold(fold);
		}
		
		NodeState.setNodeState(parent);
		NodeState.setNodeState(fold);
	}

	private boolean hasSurrogateFold() {
		return connections.hasSurrogateFold();
	}

	/**
	 * This is a greedy algorithm and only guarantees finding the largest node if the
	 * HyPeerWeb is also a HyperCube; otherwise this algorithm only guarantees finding an edge node.
	 * @pre This node is connected to the HyPeerWeb.
	 * @post result = Either the cap node (if it exists); otherwise an edge node.
	 */
	public synchronized Node findLargest(){
		Node largest = this;
		if(this.getFold().getWebIdValue() > largest.getWebIdValue()){
			largest = this.getFold();
		}
		if(this.getSurrogateFold().getWebIdValue() > largest.getWebIdValue()){
			largest = this.getSurrogateFold();
		}
		if(this.connections.getBiggestNeighbor().getWebIdValue() > largest.getWebIdValue()){
			largest = this.connections.getBiggestNeighbor();
		}
		if(this.connections.getLargestUpPointer().getWebIdValue() > largest.getWebIdValue()){
			largest = this.connections.getLargestUpPointer();
		}
		
		if(largest == this){
			return largest;
		}
		else{
			return largest.findLargest();
		}
	}
	
	/**
	 * Finds the node with the given webId.
	 * @param webId The webIdValue of the node to be found.
	 * @pre The node with the given webId exists and is connected to the HyPeerWeb.
	 * @post result = Node with given webId
	 */
	public synchronized Node findNode(int webId){
		if (webId == this.webId.getValue()) {
			return this;
		}
		
		Node closest = this.connections.getNextClosestNeighbor(this.webId.getValue(), webId);
		return closest.findNode(webId);
	}
	
	/**
	 * Adds the given node to the HyPeerWeb.
	 * @param child The node that will be added as the child of this node.
	 * @pre The child node has the appropriate webId (webId = N + 1) and the child node is a
	 * child of this node.
	 * @post The child node is connected to the HyPeerWeb, all connections are set, 
	 * this node's height is correct.
	 */
	public synchronized void addChild(Node child){
		this.height++;
		
//		Connections childConnections = new Connections();
//		child.setConnections(childConnections);
		
		//set child node's lower neighbors
		Map<Integer,NodeInterface> upPointers = connections.getUpPointers();

		connections.setUpPointers(new TreeMap<Integer,NodeInterface>());
		
		//notify child node's lower neighbors
		Iterator<NodeInterface> childsLowerNeighbors = upPointers.values().iterator();
		while(childsLowerNeighbors.hasNext()){
			NodeInterface lowerNeighbor = childsLowerNeighbors.next();
			child.addNeighbor(lowerNeighbor);
			lowerNeighbor.removeDownPointer(this);
			lowerNeighbor.addNeighbor(child);
		}
		
		//set child node's surrogate neighbors
		Iterator<NodeInterface> childsSurrogateNeighbors = connections.getUpperNeighbors().values().iterator();
		while(childsSurrogateNeighbors.hasNext()){
			Node surrogateNeighbor = childsSurrogateNeighbors.next().getNode();
			child.addDownPointer(surrogateNeighbor);
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
		child.addNeighbor(this);
		//add child as neighbor last
		this.addNeighbor(child);
		
		NodeState.setNodeState(child);
		NodeState.setNodeState(this);
	}
	
	public void setLowerNeighbors(Map<Integer, NodeInterface> lowerNeighbors) {
		System.out.println("Setting lower neighbors = " + lowerNeighbors);
		connections.setLowerNeighbors(lowerNeighbors);		
	}

	//------------------
	//  A D D E R S
	//------------------
	public void addDownPointer(Node downPointer){
		connections.addDownPointer(downPointer);
		if(connections.getDownPointerCount() > 0){
			NodeState.setNodeState(this);
		}
	}
	
	public void addNeighbor(NodeInterface lowerNeighbor){
		if(lowerNeighbor.getWebIdValue() > this.getWebIdValue()){
			connections.addUpperNeighbor(lowerNeighbor);
		}
		else{
			connections.addLowerNeighbor(lowerNeighbor);
		}
	}
	
	public void addUpPointer(Node upPointer){
		connections.addUpPointer(upPointer);
		if(connections.getUpPointerCount() > 0){
			NodeState.setNodeState(this);
		}
	}
	
	//--------------------
	//  R E M O V E R S
	//--------------------	
	public void removeDownPointer(Node downPointer){
		connections.removeDownPointer(downPointer);
		if(connections.getDownPointerCount() == 0){
			NodeState.setNodeState(this);
		}
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
		if(connections.getUpPointerCount() == 0){
			NodeState.setNodeState(this);
		}
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
	
	public Map<Integer,NodeInterface> getDownPointers(){
		return connections.getDownPointers();
	}
	
	public Map<Integer,NodeInterface> getUpPointers(){
		return connections.getUpPointers();
	}
	
	public TreeMap<Integer,NodeInterface> getNeighbors(){
		TreeMap<Integer,NodeInterface> result = new TreeMap<Integer,NodeInterface>();
		result.putAll(connections.getLowerNeighbors());
		result.putAll(connections.getUpperNeighbors());
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
	
	public Map<Integer, NodeInterface> getUpperNeighbors(){
		return connections.getUpperNeighbors();
	}
	
	public LocalObjectId getLocalObjectId(){
		return localObjectId;
	}
	
	//------------------
	//  S E T T E R S
	//------------------
	public synchronized void setFold(Node fold){
		assert (fold != null);
		
		connections.setFold(fold);
		
		NodeState.setNodeState(this);
	}
	
	public synchronized void setLocalObjectId(LocalObjectId id){
		localObjectId = id;
	}
	
	public synchronized void setHeight(int height){
		this.height = height;
	}
	
	public synchronized void setInverseSurrogateFold(Node inverseSurrogateFold){
		assert (inverseSurrogateFold != null);
		
		connections.setInverseSurrogateFold(inverseSurrogateFold);
	}
	
	public synchronized void setSurrogateFold(Node surrogateFold){
		assert (surrogateFold != null);
		
		connections.setSurrogateFold(surrogateFold);
	}
	
	public synchronized void setWebId(int newWebId) {
		setWebId(new WebId(newWebId));
	}
	
	public void setWebId(WebId newWebId){
		assert (newWebId != null);
		
		this.webId = newWebId;
		this.height = this.getWebIdHeight();
		
		NodeState.setNodeState(this);
	}
	
	/**
	 * Sets this nodes connections. Should only need to be called by the {@code addChild} method.
	 * @param connections This nodes new connections.
	 * @pre connections is not null
	 * @post this.connections = connections
	 */
	protected void setConnections(Connections connections){
		assert connections != null;
		this.connections = connections;
	}
	
	/**
	 * Used in serialization.
	 */
	protected void setConnectionsToNull(){
		this.connections = null;
	}
	
	@Override
	public String toString(){
		return "Node [webId=" + webId.getValue() + "]";
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

		return this.webId.getValue() == other.webId.getValue();
	}

	@Override
	public int compareTo(Node other) {
		if(getWebIdValue() > other.getWebIdValue()){
			return 1;
		}
		else if(getWebIdValue() < other.getWebIdValue()){
			return -1;
		}
		else {
			return 0;
		}
	}

	/**
	 * Duplicate method. Defined in specs though.
	 */
	@Override
	public synchronized void changeFold(Node newFold) {
		assert (getConnections().hasFold());
		this.setFold(newFold);
	}

	@Override
	public Node getNode(){
		return this;
	}
	
	@Override 
	public Contents getContents(){
		return this.contents;
	}
	
	@Override
	public void accept(Visitor visitor, Parameters parameters){
		assert (visitor != null && parameters != null);
		visitor.visit(this, parameters);
	}

	@Override
	public void removeConnection(Node aNode, Node parent) {	
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
	
	@Override
	public void addConnection(Node aNode) {
	}

	@Override
	public void removeConnection(Node aNode) {
	}

	@Override
	public void replaceConnection(Node aNode, Node replacementNode) {
	}
	
	
	//I was writing this and a similar method in connections for testing purposes
	//but they are not working yet.  Haven't worked out the creation of new objects
	//when there is a web with bi-directional references.
	public Node deepCopy(){
		if(this == Node.NULL_NODE){
			return Node.NULL_NODE;
		}
		
		Node node = new Node(getWebIdValue(), getWebIdHeight());
		node.setHeight(this.height);
		
		//need to find a way to copy connections.  
		node.setConnections(connections);
		node.setState(this.state);
		return node;
	}
	
	public Object writeReplace() throws ObjectStreamException{
		String machineAddress = MachineAddress.getThisMachinesInetAddress().getHostAddress().toString();
		PortNumber portNumber = PeerCommunicator.getSingleton().getPortNumber();
		GlobalObjectId globalId = new GlobalObjectId(machineAddress, portNumber, localObjectId);
		NodeProxy result = new NodeProxy(globalId);
		result.setWebId(this.getWebId());
		return result;
	}

	public void connectExpectedResult(ExpectedResult result) {
		HyPeerWebSegment web = HyPeerWebSegment.getSingleton();
		clearConnections();
		
		for(Integer id : result.getDownPointers()){
			addDownPointer(web.getNodeByWebId(id));
		}
		
		for(Integer id : result.getUpPointers()){
			addUpPointer(web.getNodeByWebId(id));
		}
		
		for(Integer id : result.getNeighbors()){
			addNeighbor(web.getNodeByWebId(id));
		}
		
		setFold(web.getNodeByWebId(result.getFold()));
		setSurrogateFold(web.getNodeByWebId(result.getSurrogateFold()));
		setInverseSurrogateFold(web.getNodeByWebId(result.getInverseSurrogateFold()));
		
		setState(NodeState.getNodeState(result.getState()));
		setHeight(result.getHeight());
	}
}
