package hypeerweb;

import hypeerweb.database.HyPeerWebDatabase;
import hypeerweb.node.Node;
import hypeerweb.node.SimplifiedNodeDomain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import communicator.PeerCommunicator;
import communicator.PortNumber;


/**
 * @author Jason Robertson
 */
public class HyPeerWebSegment {
	
	private static HyPeerWebSegment singleton = null;
	private List<Node> nodes = null;
	private HashSet<HyPeerWebObserver> observers = null;
	
	protected HyPeerWebSegment(){
		this.nodes = new ArrayList<Node>();
		this.observers = new HashSet<HyPeerWebObserver>();
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
	public void addToHyPeerWeb(Node newNode, Node startNode){
		assert (newNode != null && newNode != Node.NULL_NODE);
		
		if(nodes.isEmpty()){
			getForeignNode().addToHyPeerWeb(newNode);
		}
		else{
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
	public void removeFromHyPeerWeb(Node removeNode){
		assert (removeNode != null && removeNode != Node.NULL_NODE);
		assert (this.getNodeByWebId(removeNode.getWebIdValue()).getWebIdValue() == removeNode.getWebIdValue());
		
		removeNode.removeFromHyPeerWeb(removeNode);
		nodes.remove(removeNode);
		this.fireNodeRemoved(removeNode.getWebIdValue());
	}
	
	public void addNode(Node node){
		this.nodes.add(node);
	}
	
	public void clear(){
		this.nodes.clear();
		assert (nodes.isEmpty());
		
		this.fireCleared();
	}
	
	public boolean contains(Node node){
		return this.nodes.contains(node);
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
			existingNode = nodes.get(0);
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
	public void saveToDatabase(){
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
	 * @post Deletion point webId + 1.
	 */
	public int sizeOfHyPeerWeb(){
		Node deletionPoint = Node.NULL_NODE;
		if(nodes.isEmpty()){
			
		}
		else{
			nodes.get(0).findDeletionPoint();
		}
		return (deletionPoint.getWebIdValue() + 1);
	}
	
	/**
	 * Gets a node from a connected HyPeerWeb.
	 * @return A NodeProxy that is in another HyPeerWeb.
	 * @pre None.
	 * @post A NodeProxy from any other HyPeerWeb; NULL_NODE if no other node can be found.
	 */
	public Node getForeignNode(){
		//TODO implement this method so it gets a NodeProxy from another HyPeerWebSegement
		return Node.NULL_NODE;
	}
	
	/**
	 * This method is being called from the GUI.
	 * @author Jason Robertson
	 */
	public void close() {
		saveToDatabase();
		HyPeerWebDatabase.closeConnection();
		clear();
	}
	
	
	/**
	 * Registers the given observer to receive updates to changes in this HyPeerWebSegment.
	 * @param observer The HyPeerWebObserver to be registered.
	 * @pre None.
	 * @post observer in observers.
	 */
	public void registerObserver(HyPeerWebObserver observer){
		observers.add(observer);
	}
	
	/**
	 * Unregisters the given observer to no longer receive updates.
	 * @param observer The HyPeerWebObserver to remove.
	 * @pre None.
	 * @post If observer in guiRegister, then observer not in guiRegister.
	 */
	public void unregisterObserver(HyPeerWebObserver observer){
		observers.remove(observer);
	}
	
	/**
	 * Used to trigger a notification to all listening GUIs.
	 * @param webId The webId of the node that was added.
	 * @pre The node with the given webId must be in the HyPeerWeb.
	 * @post The GUIs have all been notified of the addition.
	 */
	public void fireNodeAdded(int webId){
		for(HyPeerWebObserver observer: observers){
			observer.notifyNodeAdded(webId);
		}
	}
	
	public void fireNodeRemoved(int webId){
		for(HyPeerWebObserver observer: observers){
			observer.notifyNodeRemoved(webId);
		}
	}
	
	public void fireCleared(){
		for(HyPeerWebObserver observer: observers){
			observer.notifyCleared();
		}
	}
	
	/**
	 * Shuts down this segment distributing any remaining nodes to any other connected segments.
	 * Additionally, any segments connected to this segment will be reattached to a parent segment.
	 * @pre None.
	 * @post This segments nodes are distributed to any other segments
	 *  then this segment is destroyed. If this is the last segment then the nodes are stored in
	 *   the database. Disconnects any observers.
	 */
	public void shutdown(){
		for(HyPeerWebObserver observer: observers){
			observer.notifyHyPeerWebSegmentShutdown();
		}
		//TODO distribute this segments nodes to the other segments
		//ensure the segments are connected properly
		
		System.exit(0);
	}
	
	public static void main(String[] args){
		if(args.length == 1){
			try{
				int portNumber = Integer.parseInt(args[0]);
				PeerCommunicator.createPeerCommunicator(new PortNumber(portNumber));
			}
			catch(NumberFormatException e){
				System.out.println(e);
			}
		}
	}
}
