package hypeerweb;

import hypeerweb.database.HyPeerWebDatabase;
import hypeerweb.node.Node;
import hypeerweb.node.NodeProxy;
import hypeerweb.node.SimplifiedNodeDomain;
import identification.GlobalObjectId;
import identification.LocalObjectId;
import identification.ObjectDB;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import communicator.PeerCommunicator;
import communicator.PortNumber;


/**
 * @author Jason Robertson
 */
public class HyPeerWebSegment extends Observable implements Serializable{
	
	private static HyPeerWebSegment singleton = null;
	private List<Node> nodes = null;
	private LocalObjectId localId = null;
	private Set<HyPeerWebSegment> connectedSegments;
	
	/**
	 * Makes sure that this class is registered in the ObjectDB.
	 */
	static{
		LocalObjectId localObjectId = LocalObjectId.getFirstId();
		HyPeerWebSegment singleton = HyPeerWebSegment.getSingleton();
		singleton.setLocalId(localObjectId);
		ObjectDB.getSingleton().store(localObjectId, singleton);
	}
	
	protected HyPeerWebSegment(){
		this.nodes = new ArrayList<Node>();
		this.connectedSegments = new HashSet<HyPeerWebSegment>();
	}
	
	public static HyPeerWebSegment getSingleton(){
		if(singleton == null){
			singleton = new HyPeerWebSegment();
		}
		return singleton;
	}
	
	/**
	 * Adds a new node to the HyPeerWeb.
	 * @param newNode The node to add to the HyPeerWeb.
	 * @param startNode The node that represents the start point in the HyPeerWeb.
	 * @pre newNode is not null/NULL_NODE
	 * @post newNode is in nodes and is connected to the HyPeerWeb
	 */
	public synchronized void addToHyPeerWeb(Node newNode, Node startNode){
		assert (newNode != null && newNode != Node.NULL_NODE);
		//assert ((startNode != null && startNode != Node.NULL_NODE) || nodes.isEmpty());
		
		ObjectDB.getSingleton().store(newNode.getLocalObjectId(), newNode);
		if(nodes.isEmpty()){
			Node foreignNode = getForeignNode();
			if(foreignNode == Node.NULL_NODE){
				Node.NULL_NODE.addToHyPeerWeb(newNode);
			} else {
				foreignNode.addToHyPeerWeb(newNode);
			}
		}
		else{
			if(startNode == Node.NULL_NODE || startNode == null){
				startNode = nodes.get(0);
			}
			startNode.addToHyPeerWeb(newNode);
		}
		this.addNode(newNode);
		this.fireNodeAdded(newNode.getWebIdValue());
	}	
	
	/**
	 * Removes a node from the HyPeerWeb
	 * @param removeNode The node to remove from the HyPeerWeb.
	 * @pre removeNode is not null/NULL_NODE and is in the HyPeerWeb;
	 * @post removeNode has been removed from HyPeerWeb.  Web has N-1 nodes and connections are updated.
	 */
	public synchronized void removeFromHyPeerWeb(Node removeNode){
		assert (removeNode != null && removeNode != Node.NULL_NODE);
		assert (this.getNodeByWebId(removeNode.getWebIdValue()).getWebIdValue() == removeNode.getWebIdValue());
		
		nodes.remove(HyPeerWebSegment.getSingleton().getNodeByWebId(removeNode.getWebIdValue()));
		removeNode.removeFromHyPeerWeb(removeNode);		
		
		this.fireNodeRemoved();
	}
	
	public synchronized void addNode(Node node){
		this.nodes.add(node);
	}
	
	/**
	 * Created for simple GUI-HyPeerWeb communication.
	 * @param startNodeIndex The index of the node to start at.
	 * @pre 0 <= startNodeIndex < nodes.size(), OR startNodeIndex == -1 if nodes is empty
	 * @post A new node is added to the HyPeerWebSegment.
	 */
	public synchronized void addNode(int startNodeIndex){
		assert (startNodeIndex == -1 || (startNodeIndex >= 0 && startNodeIndex < nodes.size()));
		
		Node startNode = Node.NULL_NODE;
		if(startNodeIndex != -1){
			startNode = this.getNode(startNodeIndex);
		}
		Node newNode = new Node(0);
		this.addToHyPeerWeb(newNode, startNode);
	}
	
	public synchronized void clear(){
		this.nodes.clear();
		assert (nodes.isEmpty());
		
		this.fireCleared();
	}
	
	public boolean contains(Node node){
		return this.nodes.contains(node);
	}
	
	public void connectSegment(HyPeerWebSegment segment){
		connectedSegments.add(segment);
	}
	
	public void disconnectSegment(HyPeerWebSegment segment){
		connectedSegments.remove(segment);
	}
	
	public HyPeerWebSegment getSegment(){
		return this;
	}
	
	public HyPeerWebDatabase getHyPeerWebDatabase(){
		return HyPeerWebDatabase.getSingleton();
	}
	
	/**
	 * Returns the node of the provided index.
	 * 
	 * WARNING: Please note that there is a conflict between Phase 1 and Phase 2 specifications.
	 * This method uses an index. If you want to use a webId use {@code getNodeByWebId(int)}.
	 * 
	 * @param webId The index of the Node you want to get.
	 * @pre 0 <= index < size()
	 * @post result = node at given index
	 */
	public Node getNode(int index){
		assert (index >= 0 && index < this.size());
		
		Node result = nodes.get(index);
		return result;
	}
	
	public Node getANode(){
		if(nodes.isEmpty()){
			return getForeignNode();
		}
		else{
			return nodes.get(0);
		}
	}
	
	/**
	 * Gets a node from a connected HyPeerWeb.
	 * @return A NodeProxy that is in another HyPeerWeb.
	 * @pre None.
	 * @post A NodeProxy from any other HyPeerWeb; NULL_NODE if no other node can be found.
	 */
	public Node getForeignNode(){
		Node foreignNode = Node.NULL_NODE;
		
		for(HyPeerWebSegment segment: connectedSegments){
			if(segment.size() > 1){
				foreignNode = segment.getANode();
				break;
			}
		}
		
		return foreignNode;
	}
	
	/**
	 * Returns the node of the provided webId.
	 * 
	 * WARNING: Please note that there is a conflict between Phase 1 and Phase 2 specifications.
	 * This method uses a webId. If you want to use an index use {@code getNode(int)}.
	 * 
	 * @param webId The webId of the Node you want to get.
	 * @pre webId is the webId of an existing Node connected to the HyPeerWeb
	 * @post result = Node with given webId in HyPeerWeb
	 */
	public Node getNodeByWebId(int webId){
		Node existingNode = Node.NULL_NODE;
		if(nodes.isEmpty()){
			existingNode = getForeignNode();
		}
		else{
			for(int i = 0; i < nodes.size(); i++){
				if(nodes.get(i).getWebIdValue() == webId){
					existingNode = nodes.get(i);
					break;
				}
			}			
		}
		
		return existingNode.findNode(webId);
	}
	
	/**
	 * Reloads the HyPeerWeb from the database using the default database name.
	 * @Precondition None
	 * @Postcondition All nodes are removed from the HyPeerWeb and the nodes in the
	 * existing default database are loaded into the HyPeerWeb
	 * @author Jason Robertson
	 */
	public void reload(){
		reload(HyPeerWebDatabase.DEFAULT_DATABASE_NAME);
	}
	
	public List<Node> getNodes(){
		return nodes;
	}
	
	/**
	 * Rebuilds the HyPeerWeb from the database provided.
	 * If the dbName is null then the results are loaded from the default database.
	 * @param dbName The name of the database you want to load from
	 * @Precondition None
	 * @Postcondition All nodes are removed from the HyPeerWeb and the nodes in the provided
	 * database are loaded into the HyPeerWeb along with their connections and pointers.
	 * @author Jason Robertson
	 */
	public void reload(String dbName){

		clear();
		HyPeerWebDatabase.initHyPeerWebDatabase(dbName);

		List<Integer> webIds = HyPeerWebDatabase.getSingleton().getAllWebIds();
		List<Node> list = new ArrayList<Node>();
		Map<Integer, SimplifiedNodeDomain> rawData = new HashMap<Integer, SimplifiedNodeDomain>();
		
		// First create all of the nodes without any connections.
		for(Integer i : webIds){
			SimplifiedNodeDomain snd = HyPeerWebDatabase.getSingleton().getNode(i);
			list.add(new Node(snd.getWebId(), snd.getHeight())); 
			
			// Save the raw data in a map so we can access it later
			rawData.put(snd.getWebId(), snd);
		}
		
		// Next we map out the nodes so we can find them while doing the linking
		Map<Integer, Node> map = new HashMap<Integer, Node>();
		for(Node n : list){
			map.put(n.getWebIdValue(), n);
		}

		// Now we can do the linking for each node
		for(Node node : list){
			
			SimplifiedNodeDomain data = rawData.get(node.getWebIdValue());
			
			for(Integer ptr : data.getDownPointers()){
				node.addDownPointer(map.get(ptr));
			}
			
			for(Integer ptr : data.getUpPointers()){
				node.addUpPointer(map.get(ptr));
			}
			
			for(Integer ptr : data.getNeighbors()){
				node.addNeighbor(map.get(ptr));
			}
			
			Node fold = map.get(data.getFold());
			if(fold == null){
				fold = Node.NULL_NODE;
			}
			node.setFold(fold);
			
			Node surrogateFold = map.get(data.getSurrogateFold());
			if(surrogateFold == null){
				surrogateFold = Node.NULL_NODE;
			}
			node.setSurrogateFold(surrogateFold);
			
			Node inverseSurrogateFold = map.get(data.getInverseSurrogateFold());
			if(inverseSurrogateFold == null){
				inverseSurrogateFold = Node.NULL_NODE;
			}
			node.setInverseSurrogateFold(inverseSurrogateFold);
		}
		
		this.nodes = list;
	}

	/**
	 * Removes a node from the HyPeerWeb. Note: This does not remove it from
	 * the database...You have to call saveToDatabase afterwards for permanent changes.
	 * @param node The node being removed from the HyPeerWeb
	 */
	public void removeNode(Node node){
		this.nodes.remove(node);
	}
	
	/**
	 * Clears the database, then adds all of the current nodes (including
	 * references) back into the database.
	 * @author Jason Robertson
	 */
	public synchronized void saveToDatabase(){
		HyPeerWebDatabase.clear();
		
		for(Node n : nodes){
			HyPeerWebDatabase.getSingleton().storeNode(n);
		}
	}
	
	/**
	 * Determines the size of this HyPeerWeb Segment.
	 * @return The number of nodes in this segment
	 */
	public int size(){
		return this.nodes.size();
	}
	
	/**
	 * Finds the size of the entire HyPeerWeb.
	 * @return The size of the entire HyPeerWeb.
	 * @pre None.
	 * @post result = deletion point webId + 1.
	 */
	public int sizeOfHyPeerWeb(){
		Node deletionPoint = Node.NULL_NODE;
		if(nodes.isEmpty()){
			deletionPoint = getForeignNode().findDeletionPoint();
		}
		else{
			deletionPoint = nodes.get(0).findDeletionPoint();
		}
		return (deletionPoint.getWebIdValue() + 1);
	}
	
	/**
	 * This method is being called from the GUI.
	 * @author Jason Robertson
	 */
	public void close() {
		saveToDatabase();
		clear();
	}
	
	
	/**
	 * Triggers a notification to all registered Observers that a node was added.
	 * @param webId The webId of the node that was added.
	 * @pre The node with the given webId must be in the HyPeerWeb.
	 * @post The Observers have all been notified of the addition.
	 */
	public void fireNodeAdded(int webId){
		setChanged();
		this.notifyObservers("addedNode");
		clearChanged();
	}
	
	/**
	 * Triggers a notification to all registered Observers that a node was removed.
	 * @pre A node was removed from this segment.
	 * @post The Observers have all been notified of the removal.
	 */
	public void fireNodeRemoved(){
		setChanged();
		this.notifyObservers("removedNode");
		clearChanged();
	}
	
	/**
	 * Triggers a notification to all registered Observers that all of the nodes in this segment
	 *  have been cleared.
	 * @pre No remaining nodes remain in this segment.
	 * @post The Observers have all been notified of the change.
	 */
	public void fireCleared(){
		setChanged();
		this.notifyObservers("cleared");
		clearChanged();
	}
	
	/**
	 * Triggers a notification to all registered Observers that this segment is shutting down.
	 * @pre This segment has either been shutdown or killed, either way the effect on the 
	 * Observers is the same.
	 * @post The Observers have all been notified of the change.
	 */
	public void fireShutdown(){
		setChanged();
		this.notifyObservers("shutdown");
		clearChanged();
	}
	
	/**
	 * Kills this segment distributing any remaining nodes to any other connected segments.
	 * Additionally, any segments connected to this segment will be reattached to a parent segment.
	 * @pre None.
	 * @post This segments nodes are distributed to any other segments
	 *  then this segment is destroyed. If this is the last segment then the nodes are stored in
	 *   the database. Disconnects any observers.
	 */
	public synchronized void kill(){
		this.fireShutdown();
		
		//TODO distribute this segments nodes to the other segments
		//ensure the segments are connected properly
		
		System.exit(0);
	}
	
	/**
	 * Shuts down the entire web, saving all nodes to the database and saving the LocalObjectIds.
	 * @pre None.
	 * @post All nodes and hypeerweb segment connections are saved.
	 */
	public synchronized void shutdown(){
		this.fireShutdown();
		//TODO shutdown the entire web, persistence is required
		System.exit(0);
	}
	
	public static void main(String[] args){
		if(args.length == 1){
			try{
				System.out.println("Starting on port "+args[0]);
				int portNumber = Integer.parseInt(args[0]);
				
				Class.forName("hypeerweb.HyPeerWebSegment");//ensures that the static block is executed
				PortNumber port = new PortNumber(portNumber);
				PeerCommunicator.createPeerCommunicator(port);
			}
			catch(NumberFormatException e){
				e.printStackTrace(System.err);
			}
			catch(ClassNotFoundException e){
				e.printStackTrace(System.err);
			}
		} 
	}
	
	private void setLocalId(LocalObjectId localId) {
		this.localId = localId;
	}

	/**
	 * Replaces this object with a Proxy when serializing to prevent sending the entire segment.
	 * @return
	 * @throws ObjectStreamException
	 */
	public Object writeReplace() throws ObjectStreamException{
		GlobalObjectId globalId = new GlobalObjectId(this.getLocalId());
		return new HyPeerWebSegmentProxy(globalId);
	}

	private LocalObjectId getLocalId(){
		return this.localId;
	}
}
