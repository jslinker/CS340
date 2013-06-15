package node;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import node.roles.*;


/**
 * Holds all connections for a <b>single<b> node.
 * <pre>
 *  <b>Domain:</b>
 *  	downPointers:		TreeMap<Integer, NodeInterface>
 *  	upPointer:			TreeMap<Integer, NodeInterface>
 *  	lowerNeighbors		TreeMap<Integer, NodeInterface>
 *  	upperNeightbors		TreeMap<Integer, NodeInterface>
 *  	fold				Node
 *  	surrogateFold		Node
 *  	inverseSurrogateFold Node
 * </pre>
 * @author Nathan Zabriskie
 *
 */

public class Connections {
	private TreeMap<Integer,NodeInterface> downPointers = new TreeMap<Integer,NodeInterface>();
	private TreeMap<Integer,NodeInterface> upPointers = new TreeMap<Integer,NodeInterface>();
	private TreeMap<Integer,NodeInterface> lowerNeighbors = new TreeMap<Integer,NodeInterface>();
	private TreeMap<Integer,NodeInterface> upperNeighbors = new TreeMap<Integer,NodeInterface>();
	
	private NodeInterface fold = Node.NULL_NODE;
	private NodeInterface surrogateFold = Node.NULL_NODE;
	private NodeInterface inverseSurrogateFold = Node.NULL_NODE;
	
	/**
	 * Default constructor
	 * @pre NONE
	 * @post result = initialized Connections
	 */
	public Connections(){
	}
	
	//--------------------
	//  GETTERS AND SETTERS
	//--------------------

	/**
	 * Returns a map of all this Connection's downpointers.
	 * @pre None
	 * @post None
	 * @return This connection's downpointers
	 */
	public Map<Integer,NodeInterface> getDownPointers() {
		return downPointers;
	}
	
	/**
	 * Contructs a list of every node that a node must notify when it is
	 * disconnected from the HyPeerWeb
	 * 
	 * @pre None
	 * @post None
	 * @return A list of nodes that the disconnected node must notify. 
	 */
	public ArrayList<NodeInterface> getDisconnectNodeList(){
		ArrayList<NodeInterface> nodeList = new ArrayList<NodeInterface>();
		nodeList.addAll(downPointers.values());
		nodeList.addAll(lowerNeighbors.values());
		return nodeList;
	}

	/**
	 * Sets the passed in map as this connections downpointers.  Wraps each node in the map
	 * in NodeRoles.SurrogateNeighbor
	 * @param downPointers
	 * @pre NONE
	 * @post this.downpointers = downpointers 
	 */
	public void setDownPointers(Map<Integer,NodeInterface> downPointers) {
		this.downPointers = new TreeMap<Integer, NodeInterface>();
		if(downPointers != null){
			for(NodeInterface nodeInterface : downPointers.values()){
				this.downPointers.put(nodeInterface.getWebIdValue(), new SurrogateNeighbor(nodeInterface));
			}
		} else{
			this.downPointers = null;
		}
	}

	/**
	 * Get this Connection's upPointers.
	 * @pre None
	 * @post None
	 * @return Returns all up pointers for the node
	 */
	public Map<Integer,NodeInterface> getUpPointers() {
		return upPointers;
	}

	/**
	 * A setter for upPointers that copies the nodes out of the given map
	 * 
	 * @param Map<Integer, NodeInterface> upPointers
	 * 	The map of up pointers to replace the current map of up pointers
	 * @pre None
	 * @post The nodes map of uppointers is replaced with a copy of the up pointers passed in
	 */
	public void setUpPointers(Map<Integer,NodeInterface> upPointers) {
		this.upPointers = new TreeMap<Integer, NodeInterface>();
		if(upPointers != null){
			for(NodeInterface nodeInterface : upPointers.values()){
				this.upPointers.put(nodeInterface.getWebIdValue(), new InverseSurrogateNeighbor(nodeInterface));
			}
		} else{
			this.upPointers = null;
		}
	}
	
	/**
	 * A getter for lower neighbors
	 * 
	 * @pre None
	 * @post None
	 * @return This nodes lowerNeighbors
	 */
	public Map<Integer,NodeInterface> getLowerNeighbors() {
		return lowerNeighbors;
	}

	/**
	 * A setter for lowerNeighbors that copies the nodes out of the given map
	 * 
	 * @param Map<Integer, NodeInterface> lowerNeighbors
	 * 	The map of lowerNeighbors to replace the current map of lowerNeighbors
	 * @pre None
	 * @post The nodes map of lowerNeightbors is replaced with a copy of the lowerNeighbors passed in
	 */
	public void setLowerNeighbors(Map<Integer, NodeInterface> lowerNeighbors) {
		this.lowerNeighbors = new TreeMap<Integer, NodeInterface>();
		if(lowerNeighbors != null){
			for(NodeInterface nodeInterface : lowerNeighbors.values()){
				this.lowerNeighbors.put(nodeInterface.getWebIdValue(), new Neighbor(nodeInterface));
			}
		} else{
			this.lowerNeighbors = null;
		}
	}

	/**
	 * A getter for upper neighbors
	 * 
	 * @pre None
	 * @post None
	 * @return This nodes upper neighbors
	 */
	public Map<Integer,NodeInterface> getUpperNeighbors() {
		return upperNeighbors;
	}

	/**
	 * A setter for upperNeighbors that copies the nodes out of the given map
	 * 
	 * @param Map<Integer, NodeInterface> upperNeighbors
	 * 	The map of upper neighbors to replace the current map of upper neighbors
	 * @pre None
	 * @post The nodes map of upperNeighbors is replaced with a copy of the upperNeighbors passed in
	 */
	public void setUpperNeighbors(Map<Integer,NodeInterface> upperNeighbors) {
		this.upperNeighbors = new TreeMap<Integer, NodeInterface>();
		if(upperNeighbors != null){
			for(NodeInterface nodeInterface : upperNeighbors.values()){
				this.upperNeighbors.put(nodeInterface.getWebIdValue(), new Neighbor(nodeInterface));
			}
		} else {
			this.upperNeighbors = null;
		}
	}

	/**
	 * A getter for folds
	 * 
	 * @pre None
	 * @post None
	 * @return This nodes fold
	 */
	public Node getFold() {
		return this.fold.getNode();
	}

	/**
	 * A setter for folds
	 * 
	 * @pre None
	 * @post The fold of this node is set to a copy of the node passed in
	 */
	public void setFold(Node fold) {
		if(fold == Node.NULL_NODE){
			this.fold = Node.NULL_NODE;
		} else{
			this.fold = new Fold(fold);
		}
	}

	/**
	 * A getter for surrogateFolds
	 * 
	 * @pre None
	 * @post None
	 * @return This nodes surrogate fold
	 */
	public Node getSurrogateFold() {
		return surrogateFold.getNode();
	}

	public void setSurrogateFold(Node surrogateFold) {
		if(surrogateFold == Node.NULL_NODE){
			this.surrogateFold = Node.NULL_NODE;
		} else {
			this.surrogateFold = new SurrogateFold(surrogateFold);
		}
	}

	/**
	 * A getter for inverseSurrogateFold
	 * 
	 * @pre None
	 * @post None
	 * @return This nodes inverse surrogate fold
	 */
	public Node getInverseSurrogateFold() {
		return inverseSurrogateFold.getNode();
	}

	/**
	 * A setter for inverseSurrogateFold
	 * 
	 * @pre None
	 * @post this.inverseSurrogateFold is a copy of inverseSurrogateFold
	 */
	public void setInverseSurrogateFold(Node inverseSurrogateFold){
		if(inverseSurrogateFold == Node.NULL_NODE){
			this.inverseSurrogateFold = Node.NULL_NODE;
		} else {
			this.inverseSurrogateFold = new InverseSurrogateFold(inverseSurrogateFold);
		}
	}
	
	/**
	 * A getter for the largest upPointer
	 * 
	 * @pre None
	 * @post None
	 * @return The node with the largest web id in this nodes up pointers
	 */
	public Node getLargestUpPointer(){
		if(upPointers.size() > 0){
			return upPointers.get(upPointers.lastKey()).getNode();
		}
		else{
			return Node.NULL_NODE;
		}
	}
	
	/**
	 * A getter for neighbor with largest web id
	 * 
	 * @pre None
	 * @post None
	 * @return The node with the largest web id in this nodes neighbors
	 */
	public Node getBiggestNeighbor(){
		if(upperNeighbors.size() > 0){
			return upperNeighbors.get(upperNeighbors.lastKey()).getNode();
		}
		else{
			return Node.NULL_NODE;
		}
	}
	
	/**
	 * A getter next neighbor with next closest web id
	 * 
	 * @pre None
	 * @post None
	 * @return The node with the closest web id to the target web id
	 */
	public Node getNextClosestNeighbor(int myWebId, int webId){
		assert(myWebId != webId);
		
		NodeInterface nextClosest = Node.NULL_NODE;
		
		int bitsToFlip = myWebId ^ webId;
		
		int mask = 1;
		for(int bitIndex = 0; bitIndex < 32; bitIndex++){
			if((bitsToFlip & mask) == 0){
				mask <<= 1;
		    	continue;
		    }
			
			int masker = (((int)1) << bitIndex);
			int nextWebId = myWebId ^ masker;
			nextClosest = upperNeighbors.get(nextWebId);
			
			if (nextClosest == null) {
				nextClosest = lowerNeighbors.get(nextWebId);
			}
			
			if (nextClosest != null) {
				break;
			}
			
			mask <<= 1;
		}
		
		if(nextClosest == null){
			return Node.NULL_NODE;
		} else {
			return nextClosest.getNode();
		}
	}
	
	/**
	 * A convenience method that returns a list of nodes that need to be notified during a replacement operation
	 * 
	 * @pre None
	 * @post None
	 * @return A list of all the nodes that need to be notified during a replacement
	 */
	private ArrayList<NodeInterface> getReplaceNodeList(){
		ArrayList<NodeInterface> replaceNodeList = new ArrayList<NodeInterface>();
		replaceNodeList.addAll(downPointers.values());
		replaceNodeList.addAll(upPointers.values());
		replaceNodeList.addAll(lowerNeighbors.values());
		replaceNodeList.addAll(upperNeighbors.values());
		replaceNodeList.add(fold);
		replaceNodeList.add(inverseSurrogateFold);
		replaceNodeList.add(surrogateFold);
		
		return replaceNodeList;
	}
	
	//--------------------
	//  R E P L A C E R S
	//--------------------
	
	/**
	 * Replacing nodes in a web
	 * 
	 * @pre None
	 * @post The nodeToReplace will be removed from the web all replacementNode will take its place.
	 * @param The node to be replaced.  The node to replace with.
	 */
	public void replaceNode(Node nodeToReplace, Node replacementNode){
		ArrayList<NodeInterface> replaceNodeList = getReplaceNodeList();
		
		for(NodeInterface nodeInterface : replaceNodeList){
			nodeInterface.replaceConnection(nodeToReplace, replacementNode);
		}
	}
	
	//--------------------
	//  A D D E R S
	//--------------------	
	
	/**
	 * Adds a downpointer to the list of downpointers
	 * @pre this.downPointers is not null.  downPointer is not null.
	 * @post downPointer will be properly inserted into the list of downPointers
	 * @param downPointer
	 */
	public void addDownPointer(Node downPointer){
		if(this.downPointers != null){
			this.downPointers.put(downPointer.getWebIdValue(), new SurrogateNeighbor(downPointer));
		}
	}
	
	/**
	 * Adds a node to the list of lower neighbors
	 * @pre this.lowerNeighbors is not null.  lowerNeighbor is not null.
	 * @post lowerNeighbor will be properly inserted into the list of lowerNeighbors
	 * @param lowerNeighbor to be inserted
	 */
	public void addLowerNeighbor(Node lowerNeighbor){
		if(this.lowerNeighbors != null){
			this.lowerNeighbors.put(lowerNeighbor.getWebIdValue(), new Neighbor(lowerNeighbor));
		}
	}
	
	/**
	 * Adds a node to the list of upper neighbors
	 * @pre this.upperNeighbors is not null.  upperNeighbor is not null.
	 * @post upperNeighbor will be properly inserted into the list of upperNeighbors
	 * @param upperNeighbor to be inserted
	 */
	public void addUpperNeighbor(Node upperNeighbor){
		if(this.upperNeighbors != null){
			this.upperNeighbors.put(upperNeighbor.getWebIdValue(), new Neighbor(upperNeighbor));
		}
	}
	
	/**
	 * Adds a node to the list of up pointers
	 * @pre this.upPointers is not null.  upPointer is not null.
	 * @post lowerNeighbor will be properly inserted into the list of upPointers
	 * @param upPointer to be inserted
	 */
	public void addUpPointer(Node upPointer){
		if(this.upPointers != null){
			this.upPointers.put(upPointer.getWebIdValue(), new InverseSurrogateNeighbor(upPointer));
		}
	}
	
	//--------------------
	//  R E M O V E R S
	//--------------------	
	/**
	 * Removes a node from the list of downPointers
	 * @pre this.downPointers is not null.  downPointer is not null.
	 * @post downPointer will be properly removed from the list of downPointers
	 * @param downPointer to be removed
	 */
	public void removeDownPointer(Node downPointer){
		if(this.downPointers != null){
			this.downPointers.remove(downPointer.getWebIdValue());
		}
	}
	
	/**
	 * Removes a node from the list of lower neighbors
	 * @pre this.lowerNeighbors is not null.  lowerNeighbor is not null.
	 * @post lowerNeighbor will be properly removed from the list of lowerNeighbors
	 * @param lowerNeighbor to be removed
	 */
	public void removeLowerNeighbor(Node lowerNeighbor){
		if(this.lowerNeighbors != null){
			this.lowerNeighbors.remove(lowerNeighbor.getWebIdValue());
		}
	}
	
	/**
	 * Removes a node from the list of upperNeighbors
	 * @pre this.upperNeighbors is not null.  upperNeighbor is not null.
	 * @post upperNeighbor will be properly removed from the list of upperNeighbors
	 * @param upperNeighbor to be removed
	 */
	public void removeUpperNeighbor(Node upperNeighbor){
		if(this.upperNeighbors != null){
			this.upperNeighbors.remove(upperNeighbor.getWebIdValue());
		}
	}
	
	/**
	 * Removes a node from the list of upPointers
	 * @pre this.upPointers is not null.  upPointer is not null.
	 * @post upPointer will be properly removed from the list of upPointer
	 * @param upPointer to be removed
	 */
	public void removeUpPointer(Node upPointer){
		if(this.upPointers != null){
			this.upPointers.remove(upPointer.getWebIdValue());
		}
	}
	
	//--------------------
	//  Q U E R I E S
	//--------------------
	
	/**
	 * Checks to see if an node has an inverseSurrogateFold
	 * @return returns true if inverserSurrogateFold is not a null node
	 */
	public boolean hasInverseSurrogateFold(){
		if(inverseSurrogateFold == Node.NULL_NODE) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Checks to see if an node has an surrogateFold
	 * @return returns true if surrogateFold is not a null node
	 */
	public boolean hasSurrogateFold() {
		if(surrogateFold == Node.NULL_NODE) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Checks to see if an node has a fold
	 * @return returns true if fold is not a null node
	 */
	public boolean hasFold(){
		if(fold == Node.NULL_NODE) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * A convenience method for getting a hashcode for this connection
	 * @return The hashcode for this connectoin
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((downPointers == null) ? 0 : downPointers.hashCode());
		result = prime * result + ((fold == null) ? 0 : fold.hashCode());
		result = prime
				* result
				+ ((inverseSurrogateFold == null) ? 0 : inverseSurrogateFold
						.hashCode());
		//result = prime * result
				//+ ((neighbors == null) ? 0 : neighbors.hashCode());
		result = prime * result
				+ ((surrogateFold == null) ? 0 : surrogateFold.hashCode());
		result = prime * result
				+ ((upPointers == null) ? 0 : upPointers.hashCode());
		return result;
	}

	/**
	 * Comparator method for connections
	 * @return Returns true if the objects are the same object, or if all properties are the same
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Connections other = (Connections) obj;
		if (downPointers == null) {
			if (other.downPointers != null){
				return false;
			}
		} else if (!downPointers.equals(other.downPointers)){
			return false;
		}
		if (fold == Node.NULL_NODE) {
			if (other.fold != Node.NULL_NODE){
				return false;
			}
		} else if (!fold.equals(other.fold)){
			return false;
		}
		if (inverseSurrogateFold == Node.NULL_NODE) {
			if (other.inverseSurrogateFold != Node.NULL_NODE){
				return false;
			}
		} else if (!inverseSurrogateFold.equals(other.inverseSurrogateFold)){
			return false;
		}
		if (surrogateFold == Node.NULL_NODE) {
			if (other.surrogateFold != Node.NULL_NODE){
				return false;
			}
		} else if (!surrogateFold.equals(other.surrogateFold)){
			return false;
		}
		if (upPointers == null) {
			if (other.upPointers != null){
				return false;
			}
		} else if (!upPointers.equals(other.upPointers)){
			return false;
		}
		return true;
	}

	/**
	 * Convenience method for getting the total number of upPointers
	 * @return The number of upPointers
	 */
	public int getUpPointerCount() {
		if(upPointers == null){
			return 0;
		}
		return upPointers.size();
	}
	
	/**
	 * Convenience method for getting the total number of downPointers
	 * @return The number of downPointers
	 */
	public int getDownPointerCount() {
		if(downPointers == null){
			return 0;
		}
		return downPointers.size();
	}

	/**
	 * Convenience method for getting the smallest downPointer
	 * @return The smallest downPointer
	 */
	public NodeInterface getSmallestDownPointer() {
		if(downPointers == null){
			return Node.NULL_NODE;
		}
		return downPointers.get(downPointers.firstKey());
	}

	/**
	 * Convenience method for getting the smallest neighbor with no child
	 * @return The smallest childless neighbor
	 */
	public Node getSmallestChildlessNeighbor() {
		if(lowerNeighbors == null){
			return Node.NULL_NODE;
		}
		Node result = Node.NULL_NODE;
		for(NodeInterface ln: lowerNeighbors.values()){
			if(ln.getNode().getConnections().getUpPointerCount() > 0){
				return ln.getNode();
			}
		}
		return result;
	}
	
	/**
	 * To string method, mostly useful for debugging.
	 * @return A string representing this connection
	 */
	public String toString(){
		String info = "";
		info += String.format("Fold WebId: %d\n", fold.getWebIdValue());
		info += String.format("Surrogate Fold WebId: %d\n", surrogateFold.getWebIdValue());
		info += String.format("Inverse Surrogate Fold WebID: %d\n", inverseSurrogateFold.getWebIdValue());
		info += "Neighbors: ";
		
		for(NodeInterface neighbor: upperNeighbors.values()){
			info += neighbor.getWebIdValue() + " ";
		}
		
		for(NodeInterface neighbor: lowerNeighbors.values()){
			info += neighbor.getWebIdValue() + " ";
		}
		
		info += "\nUpPointers: ";
		
		for(NodeInterface upPointer: upPointers.values()){
			info += upPointer.getWebIdValue() + " ";
		}
		
		info += "\nDownPointers: ";
		
		for(NodeInterface downPointer: downPointers.values()){
			info += downPointer.getWebIdValue() + " ";
		}
		
		info += "\n";
		
		return info;
	}
	
	/**
	 * Gets a neighbor by webId.
	 * @param webId The desired neighbor's webId.
	 * @pre webId >= 0
	 * @post If a neighbor has the given webId that node is returned; otherwise, NULL_NODE is returned.
	 */
	public Node getNeighborByWebId(int webId){
		assert (webId >= 0);
		
		if(this.upperNeighbors.containsKey(webId)){
			return this.upperNeighbors.get(webId).getNode();
		}
		else if(this.lowerNeighbors.containsKey(webId)){
			return this.lowerNeighbors.get(webId).getNode();
		}
		else{
			return Node.NULL_NODE;
		}
	}

	/**
	 * Returns a copy of this Connections.  Used primarily for testing.
	 * @return A deep copy of this connection
	 */
	public Connections deepCopy(){
		Connections con = new Connections();
		con.setDownPointers(new TreeMap<Integer, NodeInterface>(this.downPointers));
		con.setUpPointers(new TreeMap<Integer, NodeInterface>(this.upPointers));
		con.setUpperNeighbors(new TreeMap<Integer, NodeInterface>(upperNeighbors));
		con.setLowerNeighbors(new TreeMap<Integer, NodeInterface>(this.lowerNeighbors));
		con.setFold(fold.getNode().deepCopy());
		con.setSurrogateFold(surrogateFold.getNode().deepCopy());
		con.setInverseSurrogateFold(inverseSurrogateFold.getNode().deepCopy());
		
		return con;		
	}
}
