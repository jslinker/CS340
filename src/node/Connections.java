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
	
	private Node fold = Node.NULL_NODE;
	private Node surrogateFold = Node.NULL_NODE;
	private Node inverseSurrogateFold = Node.NULL_NODE;
	
	public Connections(){
	}
	
	//--------------------
	//  GETTERS AND SETTERS
	//--------------------

	public Map<Integer,NodeInterface> getDownPointers() {
		return downPointers;
	}
	
	public ArrayList<NodeInterface> getDisconnectNodeList(){
		ArrayList<NodeInterface> nodeList = new ArrayList<NodeInterface>();
		nodeList.addAll(downPointers.values());
		nodeList.addAll(lowerNeighbors.values());
		return nodeList;
	}

	public void setDownPointers(TreeMap<Integer,NodeInterface> downPointers) {
		this.downPointers = new TreeMap<Integer, NodeInterface>();
		for(NodeInterface nodeInterface : downPointers.values()){
			this.downPointers.put(nodeInterface.getWebIdValue(), new SurrogateNeighbor(nodeInterface));
		}
	}

	public Map<Integer,NodeInterface> getUpPointers() {
		return upPointers;
	}

	public void setUpPointers(Map<Integer,NodeInterface> upPointers) {
		this.upPointers = new TreeMap<Integer, NodeInterface>();
		for(NodeInterface nodeInterface : upPointers.values()){
			this.upPointers.put(nodeInterface.getWebIdValue(), new InverseSurrogateNeighbor(nodeInterface));
		}
	}
	
	public Map<Integer,NodeInterface> getLowerNeighbors() {
		return lowerNeighbors;
	}

	public void setLowerNeighbors(Map<Integer, NodeInterface> lowerNeighbors) {
		this.lowerNeighbors = new TreeMap<Integer, NodeInterface>();
		for(NodeInterface nodeInterface : lowerNeighbors.values()){
			this.lowerNeighbors.put(nodeInterface.getWebIdValue(), new Neighbor(nodeInterface));
		}
	}

	public Map<Integer,NodeInterface> getUpperNeighbors() {
		return upperNeighbors;
	}

	public void setUpperNeighbors(Map<Integer,NodeInterface> upperNeighbors) {
		this.upperNeighbors = new TreeMap<Integer, NodeInterface>();
		for(NodeInterface nodeInterface : upperNeighbors.values()){
			this.upperNeighbors.put(nodeInterface.getWebIdValue(), new Neighbor(nodeInterface));
		}
	}

	public Node getFold() {
		return this.fold;
	}

	public void setFold(Node fold) {
		this.fold = fold;
	}

	public Node getSurrogateFold() {
		return surrogateFold;
	}

	public void setSurrogateFold(Node surrogateFold) {
		this.surrogateFold = surrogateFold;
	}

	public Node getInverseSurrogateFold() {
		return inverseSurrogateFold;
	}

	public void setInverseSurrogateFold(Node inverseSurrogateFold){
		this.inverseSurrogateFold = inverseSurrogateFold;
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
	
	//--------------------
	//  R E P L A C E R S
	//--------------------
	
	public void replaceNode(Node nodeToReplace, Node replacementNode){
		for (Entry<Integer, NodeInterface> entry : downPointers.entrySet())
		{
			Node node = entry.getValue().getNode();
		    node.removeUpPointer(nodeToReplace);
		    node.addUpPointer(replacementNode);
		}
		
		for (Entry<Integer, NodeInterface> entry : upPointers.entrySet())
		{
			Node node = entry.getValue().getNode();
			node.removeDownPointer(nodeToReplace);
			node.addDownPointer(replacementNode);
		}
		
		for (Entry<Integer, NodeInterface> entry : lowerNeighbors.entrySet())
		{
			Node node = entry.getValue().getNode();
			node.removeNeighbor(nodeToReplace);
			node.addNeighbor(replacementNode);
		}
		
		for (Entry<Integer, NodeInterface> entry : upperNeighbors.entrySet())
		{
			Node node = entry.getValue().getNode();
			node.removeNeighbor(nodeToReplace);
			node.addNeighbor(replacementNode);
		}
		
		replacementNode.setFold(this.getFold());
		replacementNode.setSurrogateFold(this.getSurrogateFold());
		replacementNode.setInverseSurrogateFold(this.getInverseSurrogateFold());
	}
	
	//--------------------
	//  A D D E R S
	//--------------------		
	public void addDownPointer(Node downPointer){
		this.downPointers.put(downPointer.getWebIdValue(), new SurrogateNeighbor(downPointer));
	}
	
	public void addLowerNeighbor(Node lowerNeighbor){
		this.lowerNeighbors.put(lowerNeighbor.getWebIdValue(), new Neighbor(lowerNeighbor));
	}
	
	public void addUpperNeighbor(Node upperNeighbor){
		this.upperNeighbors.put(upperNeighbor.getWebIdValue(), new Neighbor(upperNeighbor));
	}
	
	public void addUpPointer(Node upPointer){
		this.upPointers.put(upPointer.getWebIdValue(), new InverseSurrogateNeighbor(upPointer));
	}
	
	//--------------------
	//  R E M O V E R S
	//--------------------	
	public void removeDownPointer(Node downPointer){
		this.downPointers.remove(downPointer.getWebIdValue());
	}
	
	public void removeLowerNeighbor(Node lowerNeighbor){
		this.lowerNeighbors.remove(lowerNeighbor.getWebIdValue());
	}
	
	public void removeUpperNeighbor(Node upperNeighbor){
		this.upperNeighbors.remove(upperNeighbor.getWebIdValue());
	}
	
	public void removeUpPointer(Node upPointer){
		this.upPointers.remove(upPointer.getWebIdValue());
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
		} else if (!downPointers.equals(other.downPointers))
			return false;
		if (fold == null) {
			if (other.fold != null){
				return false;
			}
		} else if (!fold.equals(other.fold)){
			return false;
		}
		if (inverseSurrogateFold == null) {
			if (other.inverseSurrogateFold != null){
				return false;
			}
		} else if (!inverseSurrogateFold.equals(other.inverseSurrogateFold)){
			return false;
		}
		/*if (neighbors == null) {
			if (other.neighbors != null)
				return false;
		} else if (!neighbors.equals(other.neighbors))
			return false;*/
		if (surrogateFold == null) {
			if (other.surrogateFold != null){
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
		return upPointers.size();
	}
	
	public int getDownPointerCount() {
		return downPointers.size();
	}

	public NodeInterface getSmallestDownPointer() {
		return downPointers.get(downPointers.firstKey());
	}

	public Node getSmallestChildlessNeighbor() {
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

	
}
