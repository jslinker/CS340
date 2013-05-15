package node;

import java.util.TreeSet;


/**
 * Holds connections for a single node.
 * 
 * @author Nathan Zabriskie
 *
 */
public class Connections {
	private TreeSet<Node> downPointers = new TreeSet<Node>();
	private TreeSet<Node> upPointers = new TreeSet<Node>();
	private TreeSet<Node> lowerNeighbors = new TreeSet<Node>();
	private TreeSet<Node> upperNeighbors = new TreeSet<Node>();
	
	private Node fold = Node.NULL_NODE;
	private Node surrogateFold = Node.NULL_NODE;
	private Node inverseSurrogateFold = Node.NULL_NODE;
	
	public Connections(){
	}
	
	//--------------------
	//  GETTERS AND SETTERS
	//--------------------

	public TreeSet<Node> getDownPointers() {
		return downPointers;
	}

	public void setDownPointers(TreeSet<Node> downPointers) {
		this.downPointers = downPointers;
	}

	public TreeSet<Node> getUpPointers() {
		return upPointers;
	}

	public void setUpPointers(TreeSet<Node> upPointers) {
		this.upPointers = upPointers;
	}
	
	public TreeSet<Node> getLowerNeighbors() {
		return lowerNeighbors;
	}

	public void setLowerNeighbors(TreeSet<Node> lowerNeighbors) {
		this.lowerNeighbors = lowerNeighbors;
	}

	public TreeSet<Node> getUpperNeighbors() {
		return upperNeighbors;
	}

	public void setUpperNeighbors(TreeSet<Node> upperNeighbors) {
		this.upperNeighbors = upperNeighbors;
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
			return upPointers.last();
		}
		else{
			return Node.NULL_NODE;
		}
	}
	
	public Node getBiggestNeighbor(){
		if(upperNeighbors.size() > 0){
			return upperNeighbors.last();
		}
		else{
			return Node.NULL_NODE;
		}
	}
	
	//--------------------
	//  A D D E R S
	//--------------------		
	public void addDownPointer(Node downPointer){
		this.downPointers.add(downPointer);
	}
	
	public void addLowerNeighbor(Node lowerNeighbor){
		this.lowerNeighbors.add(lowerNeighbor);
	}
	
	public void addUpperNeighbor(Node upperNeighbor){
		this.upperNeighbors.add(upperNeighbor);
	}
	
	public void addUpPointer(Node upPointer){
		this.upPointers.add(upPointer);
	}
	
	//--------------------
	//  R E M O V E R S
	//--------------------	
	public void removeDownPointer(Node downPointer){
		this.downPointers.remove(downPointer);
	}
	
	public void removeLowerNeighbor(Node lowerNeighbor){
		this.lowerNeighbors.remove(lowerNeighbor);
	}
	
	public void removeUpperNeighbor(Node upperNeighbor){
		this.upperNeighbors.remove(upperNeighbor);
	}
	
	public void removeUpPointer(Node upPointer){
		this.upPointers.remove(upPointer);
	}
	
	//--------------------
	//  Q U E R I E S
	//--------------------
	
	public boolean hasInverseSurrogateFold(){
		if(inverseSurrogateFold == Node.NULL_NODE)
			return false;
		else
			return true;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connections other = (Connections) obj;
		if (downPointers == null) {
			if (other.downPointers != null)
				return false;
		} else if (!downPointers.equals(other.downPointers))
			return false;
		if (fold == null) {
			if (other.fold != null)
				return false;
		} else if (!fold.equals(other.fold))
			return false;
		if (inverseSurrogateFold == null) {
			if (other.inverseSurrogateFold != null)
				return false;
		} else if (!inverseSurrogateFold.equals(other.inverseSurrogateFold))
			return false;
		/*if (neighbors == null) {
			if (other.neighbors != null)
				return false;
		} else if (!neighbors.equals(other.neighbors))
			return false;*/
		if (surrogateFold == null) {
			if (other.surrogateFold != null)
				return false;
		} else if (!surrogateFold.equals(other.surrogateFold))
			return false;
		if (upPointers == null) {
			if (other.upPointers != null)
				return false;
		} else if (!upPointers.equals(other.upPointers))
			return false;
		return true;
	}

	public int getUpPointerCount() {
		return upPointers.size();
	}
	
	public int getDownPointerCount() {
		return downPointers.size();
	}

	public Node getSmallestDownPointer() {
		return downPointers.first();
	}

	public Node getSmallestChildlessNeighbor() {
		Node result = Node.NULL_NODE;
		for(Node ln: lowerNeighbors){
			if(ln.getConnections().getUpPointerCount() > 0){
				return ln;
			}
		}
		return result;
	}
}
