package node;

import java.util.ArrayList;

public class Connections {
	private ArrayList<Node> downPointers = new ArrayList<Node>();
	private ArrayList<Node> upPointers = new ArrayList<Node>();
	private ArrayList<Node> neighbors = new ArrayList<Node>();
	
	private Node fold = Node.NULL_NODE;
	private Node surrogateFold = Node.NULL_NODE;
	private Node inverseSurrogateFold = Node.NULL_NODE;
	
	public Connections(){
		
	}

	public ArrayList<Node> getDownPointers() {
		return downPointers;
	}

	public void setDownPointers(ArrayList<Node> downPointers) {
		this.downPointers = downPointers;
	}

	public ArrayList<Node> getUpPointers() {
		return upPointers;
	}

	public void setUpPointers(ArrayList<Node> upPointers) {
		this.upPointers = upPointers;
	}

	public ArrayList<Node> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(ArrayList<Node> neighbors) {
		this.neighbors = neighbors;
	}

	public Node getFold() {
		return fold;
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

	public void setInverseSurrogateFold(Node inverseSurrogateFold) {
		this.inverseSurrogateFold = inverseSurrogateFold;
	}
	
	//--------------------
	//  A D D E R S
	//--------------------		
	public void addDownPointer(Node downPointer){
		this.downPointers.add(downPointer);
	}
	
	public void addNeighbor(Node neighbor){
		this.neighbors.add(neighbor);
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
	
	public void removeNeighbor(Node neighbor){
		this.neighbors.remove(neighbor);
	}
	
	public void removeUpPointer(Node upPointer){
		this.upPointers.remove(upPointer);
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
		result = prime * result
				+ ((neighbors == null) ? 0 : neighbors.hashCode());
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
		if (neighbors == null) {
			if (other.neighbors != null)
				return false;
		} else if (!neighbors.equals(other.neighbors))
			return false;
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
}
