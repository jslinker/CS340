package hypeerweb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Node {
	
	private WebId webId = null;
	
	private ArrayList<Node> downPointers = null;
	private ArrayList<Node> upPointers = null;
	private ArrayList<Node> neighbors = null;
	
	private Node fold = NULL_NODE;
	private Node surrogateFold = NULL_NODE;
	private Node inverseSurrogateFold = NULL_NODE;
	
	public static final Node NULL_NODE = new Node() {
		
		@Override public SimplifiedNodeDomain constructSimplifiedNodeDomain() { return null; }
		@Override public void addDownPointer(Node downPointer){ return; }
		@Override public void addNeighbor(Node neighbor){ return; }
		@Override public void addUpPointer(Node upPointer){ return; }
		@Override public void removeDownPointer(Node downPointer){ return; }
		@Override public void removeNeighbor(Node neighbor){ return; }
		@Override public void removeUpPointer(Node upPointer){ return; }
	};
	
	private Node(){
		webId = WebId.NULL_WEB_ID;
		initializeLists();
	}

	public Node(int id){
		assert(id >= 0);
		webId = new WebId(id);
		initializeLists();
	}
	
	public Node(int id, int height){
		assert(id >= 0 && height >= 0);
		webId = new WebId(id, height);
		initializeLists();
	}
	
	private void initializeLists(){
		downPointers = new ArrayList<Node>();
		upPointers = new ArrayList<Node>();
		neighbors = new ArrayList<Node>();
	}
	
	/**
	 * 
	 * @return The created SimplifiedNodeDomain object.
	 */
	public SimplifiedNodeDomain constructSimplifiedNodeDomain(){
		HashSet<Integer> neighbor_Ids = new HashSet<Integer>();
		for(Iterator<Node> i = neighbors.iterator() ; i.hasNext(); ){
			neighbor_Ids.add(i.next().getWebIdValue());
		}
		
		HashSet<Integer> up_Ids = new HashSet<Integer>();
		for(Iterator<Node> i = upPointers.iterator(); i.hasNext(); ){
			up_Ids.add(i.next().getWebIdValue());
		}
		
		HashSet<Integer> down_Ids = new HashSet<Integer>();
		for(Iterator<Node> i = downPointers.iterator(); i.hasNext(); ){
			down_Ids.add(i.next().getWebIdValue());
		}
		
		return new SimplifiedNodeDomain(webId.getValue(), webId.getHeight(), neighbor_Ids, up_Ids, down_Ids, 
									    fold.getWebIdValue(), surrogateFold.getWebIdValue(), inverseSurrogateFold.getWebIdValue());
	}
	
	//------------------
	//  A D D E R S
	//------------------
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
	
	//------------------
	//  G E T T E R S
	//------------------
	public WebId getWebId(){
		return webId;
	}
	
	public int getWebIdValue(){
		return webId.getValue();
	}
	
	public int getWebIdHeight(){
		return webId.getHeight();
	}
	
	public ArrayList<Node> getDownPointers() {
		return downPointers;
	}
	
	public ArrayList<Node> getUpPointers() {
		return upPointers;
	}
	
	public ArrayList<Node> getNeighbors() {
		return neighbors;
	}
	
	public Node getFold() {
		if(fold == null)
			return NULL_NODE;
		
		return fold;
	}
	
	public Node getSurrogateFold() {
		if(surrogateFold == null)
			return NULL_NODE;
		
		return surrogateFold;
	}
	
	public Node getInverseSurrogateFold() {
		if(inverseSurrogateFold == null)
			return NULL_NODE;
		
		return inverseSurrogateFold;
	}
	
	//------------------
	//  S E T T E R S
	//------------------
	public void setFold(Node fold){
		this.fold = fold;
	}
	
	public void setInverseSurrogateFold(Node inverseSurrogateFold){
		this.inverseSurrogateFold = inverseSurrogateFold;
	}
	
	public void setSurrogateFold(Node surrogateFold){
		this.surrogateFold = surrogateFold;
	}
	
	public void setWebId(WebId webId){
		this.webId = webId;
	}
	
	@Override
	public String toString() {
		return "Node [webId=" + webId + ", downPointers=" + downPointers
				+ ", upPointers=" + upPointers + ", neighbors=" + neighbors
				+ ", fold=" + fold + ", surrogateFold=" + surrogateFold
				+ ", inverseSurrogateFold=" + inverseSurrogateFold + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((webId == null) ? 0 : webId.hashCode());
		result = prime * result + ((downPointers == null) ? 0 : downPointers.hashCode());
		result = prime * result + ((upPointers == null) ? 0 : upPointers.hashCode());
		result = prime * result + ((fold == null) ? 0 : fold.hashCode());
		result = prime * result + ((inverseSurrogateFold == null) ? 0 : inverseSurrogateFold.hashCode());
		result = prime * result + ((neighbors == null) ? 0 : neighbors.hashCode());
		result = prime * result + ((surrogateFold == null) ? 0 : surrogateFold.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Node other = (Node) obj;
		
		boolean webId = this.webId == other.webId;
		boolean down = this.downPointers == other.downPointers;
		boolean up = this.upPointers == other.upPointers;
		boolean fold = this.fold.equals(other.fold);
		boolean isf = this.inverseSurrogateFold.equals(other.inverseSurrogateFold);
		boolean neigh = this.neighbors.equals(other.neighbors);
		boolean sf = this.surrogateFold.equals(other.surrogateFold);

		return webId && down && up && fold && isf && neigh && sf;
	}
}
