package hypeerweb;

import hypeerweb.database.HyPeerWebDatabase;
import hypeerweb.node.Node;
import hypeerweb.node.SimplifiedNodeDomain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Jason Robertson
 */
public class HyPeerWebSegment {
	
	private static HyPeerWebSegment singleton = null;
	private List<Node> nodes = null;
	
	private HyPeerWebSegment(){
		this.nodes = new ArrayList<Node>();
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
	 * @pre newNode is not null/NULL_NODE; startNode is not null and is in nodes
	 * @post newNode is in nodes and is connected to the HyPeerWeb
	 */
	public void addToHyPeerWeb(Node newNode, Node startNode){
		assert (newNode != null && newNode != Node.NULL_NODE);
		
		if(nodes.isEmpty()){
			Node.NULL_NODE.addChild(newNode);
		}
		else{
			startNode.addToHyPeerWeb(newNode);
		}
		this.addNode(newNode);
	}
	
	
	/**
	 * Removes a node from the HyPeerWeb
	 * @param removeNode The node to remove from the HyPeerWeb.
	 * @pre removeNode is not null/NULL_NODE and is in nodes;
	 * @post removeNode has been removed from HyPeerWeb.  Web has N-1 nodes and connections are updated.
	 */
	public void removeFromHyPeerWeb(Node removeNode){
		assert(removeNode != null && removeNode != Node.NULL_NODE);
		assert(nodes.contains(removeNode));
		
		nodes.remove(removeNode);
		removeNode.removeFromHyPeerWeb(removeNode);
	}
	
	public void addNode(Node node){
		this.nodes.add(node);
	}
	
	public void clear(){
		this.nodes.clear();
		assert (nodes.isEmpty());
	}
	
	public boolean contains(Node node){
		return this.nodes.contains(node);
	}
	
	public HyPeerWebDatabase getHyPeerWebDatabase(){
		return HyPeerWebDatabase.getSingleton();
	}
	
	/**
	 * Returns the node of the provided webId.
	 * @param webId The webId of the Node you want to get.
	 * @author Jason Robertson
	 * @pre 0 <= webId < size()
	 * @post result = Node with given webId
	 */
	public Node getNode(int webId){
		//TODO assert (webId >= 0 && webId < this.size());
		
		Node result = Node.NULL_NODE;
		if(!nodes.isEmpty()){
			result = nodes.get(0).findNode(webId);
		}
		return result;
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
	 * Determines the size of this HyPeerWeb Segment
	 * @return The number of nodes in this segment
	 */
	public int size(){
		return this.nodes.size();
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
}
