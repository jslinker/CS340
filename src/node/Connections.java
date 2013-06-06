package node;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import node.roles.*;


/**
 * Holds connections for a single node.
 * 
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
	 * @pre NONE
	 * @post result = Map of all downpointers.
	 */
	public Map<Integer,NodeInterface> getDownPointers() {
		return downPointers;
	}
	
	/**
	 * Contructs a list of every node that a node must notify when it is
	 * disconnected from the HyPeerWeb
	 * 
	 * @pre NONE
	 * @post result = List of nodes that the disconnected node must notify.
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
	 * @pre 
	 * @post 
	 */
	public Map<Integer,NodeInterface> getUpPointers() {
		return upPointers;
	}

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
	
	public Map<Integer,NodeInterface> getLowerNeighbors() {
		return lowerNeighbors;
	}

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

	public Map<Integer,NodeInterface> getUpperNeighbors() {
		return upperNeighbors;
	}

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

	public Node getFold() {
		return this.fold.getNode();
	}

	public void setFold(Node fold) {
		if(fold == Node.NULL_NODE){
			this.fold = Node.NULL_NODE;
		} else{
			this.fold = new Fold(fold);
		}
	}

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

	public Node getInverseSurrogateFold() {
		return inverseSurrogateFold.getNode();
	}

	public void setInverseSurrogateFold(Node inverseSurrogateFold){
		if(inverseSurrogateFold == Node.NULL_NODE){
			this.inverseSurrogateFold = Node.NULL_NODE;
		} else {
			this.inverseSurrogateFold = new InverseSurrogateFold(inverseSurrogateFold);
		}
	}
	
	public Node getLargestUpPointer(){
		if(upPointers.size() > 0){
			return upPointers.get(upPointers.lastKey()).getNode();
		}
		else{
			return Node.NULL_NODE;
		}
	}
	
	public Node getBiggestNeighbor(){
		if(upperNeighbors.size() > 0){
			return upperNeighbors.get(upperNeighbors.lastKey()).getNode();
		}
		else{
			return Node.NULL_NODE;
		}
	}
	
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
	
	public void replaceNode(Node nodeToReplace, Node replacementNode){
		ArrayList<NodeInterface> replaceNodeList = getReplaceNodeList();
		
		for(NodeInterface nodeInterface : replaceNodeList){
			nodeInterface.replaceConnection(nodeToReplace, replacementNode);
		}
	}
	
	//--------------------
	//  A D D E R S
	//--------------------		
	public void addDownPointer(Node downPointer){
		if(this.downPointers != null){
			this.downPointers.put(downPointer.getWebIdValue(), new SurrogateNeighbor(downPointer));
		}
	}
	
	public void addLowerNeighbor(Node lowerNeighbor){
		if(this.lowerNeighbors != null){
			this.lowerNeighbors.put(lowerNeighbor.getWebIdValue(), new Neighbor(lowerNeighbor));
		}
	}
	
	public void addUpperNeighbor(Node upperNeighbor){
		if(this.upperNeighbors != null){
			this.upperNeighbors.put(upperNeighbor.getWebIdValue(), new Neighbor(upperNeighbor));
		}
	}
	
	public void addUpPointer(Node upPointer){
		if(this.upPointers != null){
			this.upPointers.put(upPointer.getWebIdValue(), new InverseSurrogateNeighbor(upPointer));
		}
	}
	
	//--------------------
	//  R E M O V E R S
	//--------------------	
	public void removeDownPointer(Node downPointer){
		if(this.downPointers != null){
			this.downPointers.remove(downPointer.getWebIdValue());
		}
	}
	
	public void removeLowerNeighbor(Node lowerNeighbor){
		if(this.lowerNeighbors != null){
			this.lowerNeighbors.remove(lowerNeighbor.getWebIdValue());
		}
	}
	
	public void removeUpperNeighbor(Node upperNeighbor){
		if(this.upperNeighbors != null){
			this.upperNeighbors.remove(upperNeighbor.getWebIdValue());
		}
	}
	
	public void removeUpPointer(Node upPointer){
		if(this.upPointers != null){
			this.upPointers.remove(upPointer.getWebIdValue());
		}
	}
	
	//--------------------
	//  Q U E R I E S
	//--------------------
	
	public boolean hasInverseSurrogateFold(){
		if(inverseSurrogateFold == Node.NULL_NODE) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean hasSurrogateFold() {
		if(surrogateFold == Node.NULL_NODE) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean hasFold(){
		if(fold == Node.NULL_NODE) {
			return false;
		}
		else {
			return true;
		}
	}

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

	public int getUpPointerCount() {
		if(upPointers == null){
			return 0;
		}
		return upPointers.size();
	}
	
	public int getDownPointerCount() {
		if(downPointers == null){
			return 0;
		}
		return downPointers.size();
	}

	public NodeInterface getSmallestDownPointer() {
		if(downPointers == null){
			return Node.NULL_NODE;
		}
		return downPointers.get(downPointers.firstKey());
	}

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
	 * @return
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
