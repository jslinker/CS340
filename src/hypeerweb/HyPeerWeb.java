package hypeerweb;

import java.util.ArrayList;
import java.util.List;

public class HyPeerWeb {
	
	private static HyPeerWeb singleton = null;
	private List<Node> nodes = null;
	
	private HyPeerWeb(){
		this.nodes = new ArrayList<Node>();
	}
	
	public static HyPeerWeb getSingleton(){
		if(singleton == null) {
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
		
		if(i >= 0 && i < size())
			return this.nodes.get(i);
		
		return Node.NULL_NODE;
	}
	
	public void reload(){
		
	}
	
	public void reload(String dbName){
		
	}
	
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
		
		System.out.println(nodes.size());
		
		for(Node n : nodes) {
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
