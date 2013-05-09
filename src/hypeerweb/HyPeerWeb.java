package hypeerweb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.HyPeerWebDatabase;

import node.Node;
import node.SimplifiedNodeDomain;

/**
 * @author Jason Robertson
 */
public class HyPeerWeb {
	
	private static HyPeerWeb singleton = null;
	private List<Node> nodes = null;
	
	private HyPeerWeb(){
		this.nodes = new ArrayList<Node>();
	}
	
	public static HyPeerWeb getSingleton(){
		if(singleton == null){
			singleton = new HyPeerWeb();
		}
		return singleton;
	}
	
	public void addNode(Node node){
		this.nodes.add(node);
	}
	
	public void clear(){
		this.nodes.clear();
	}
	
	public boolean contains(Node node){
		return this.nodes.contains(node);
	}
	
	public HyPeerWebDatabase getHyPeerWebDatabase(){
		return HyPeerWebDatabase.getSingleton();
	}
	
	/**
	 * This method is implemented as I understood it...It returns the node at the local index i,
		which is between 0 and the number of nodes. In order to do this I had to switch the
		implementation of our collection from a hash set to an array list.  
	 * @param i The index of the node to be retrieved.
	 * @return The node at index i.
	 * @author Jason Robertson
	 */
	public Node getNode(int i){
		
		if(i >= 0 && i < size()){
			return this.nodes.get(i);
		}
		
		return null;
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
			
			node.setFold(map.get(data.getFold()));
			node.setSurrogateFold(map.get(data.getSurrogateFold()));
			node.setInverseSurrogateFold(map.get(data.getInverseSurrogateFold()));
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
}
