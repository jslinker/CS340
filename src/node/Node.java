package node;


import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Joseph
 */
public class Node implements Comparable<Node>{
	
	private WebId webId = null;
	
	private Connections connections;
	
	public static final Node NULL_NODE = new Node(){
		@Override public void addDownPointer(Node downPointer){ return; }
		@Override public void addNeighbor(Node neighbor){ return; }
		@Override public void addUpPointer(Node upPointer){ return; }
		@Override public void removeDownPointer(Node downPointer){ return; }
		@Override public void removeNeighbor(Node neighbor){ return; }
		@Override public void removeUpPointer(Node upPointer){ return; }
		@Override public void setWebId(WebId webId){ return; }
		@Override public void setFold(Node fold){ return; }
		@Override public void setSurrogateFold(Node surrogateFold){ return; }
		@Override public void setInverseSurrogateFold(Node inverseSurrogateFold){ return; }
	};
	
	private Node(){
		webId = WebId.NULL_WEB_ID;
		connections = new Connections();
		connections.setFold(this);
		connections.setSurrogateFold(this);
		connections.setInverseSurrogateFold(this);
	}

	public Node(int id){
		assert(id >= 0);
		webId = new WebId(id);
		connections = new Connections();
		connections.setFold(this);
	}
	
	public Node(int id, int height){
		assert(id >= 0 && height >= 0);
		webId = new WebId(id, height);
		connections = new Connections();
		connections.setFold(this);
	}
	
	/**
	 * 
	 * @return The created SimplifiedNodeDomain object.
	 */
	public SimplifiedNodeDomain constructSimplifiedNodeDomain(){
		HashSet<Integer> neighborIds = new HashSet<Integer>();
		for(Node n : getNeighbors()) {
			neighborIds.add(n.getWebIdValue());
		}
		
		HashSet<Integer> upIds = new HashSet<Integer>();
		for(Node n : getUpPointers()){
			upIds.add(n.getWebIdValue());
		}
		
		HashSet<Integer> downIds = new HashSet<Integer>();
		for(Node n : getDownPointers()){
			downIds.add(n.getWebIdValue());
		}
		
		return new SimplifiedNodeDomain(webId.getValue(), webId.getHeight(), neighborIds, upIds, downIds, 
									    getFold().getWebIdValue(), getSurrogateFold().getWebIdValue(), 
									    getInverseSurrogateFold().getWebIdValue());
	}
	
	private boolean isValidSurrogateNeighbor(Node surrogate){
		if((webId.getHeight() == surrogate.getWebIdHeight() + 1 && 
		    webId.getValue() < surrogate.getWebIdValue() && checkBits(surrogate)) ||
		    surrogate.equals(Node.NULL_NODE)) {
			
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean checkBits(Node surrogate){
		if((~(-1 << webId.getHeight()) & surrogate.getWebIdValue()) == ~(~(-1 << webId.getHeight()) & webId.getValue())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//------------------
	//  A D D E R S
	//------------------
	public void addDownPointer(Node downPointer){
		connections.addDownPointer(downPointer);
	}
	
	public void addNeighbor(Node neighbor){
		connections.addNeighbor(neighbor);
	}
	
	public void addUpPointer(Node upPointer){
		connections.addUpPointer(upPointer);
	}
	
	//--------------------
	//  R E M O V E R S
	//--------------------	
	public void removeDownPointer(Node downPointer){
		connections.removeDownPointer(downPointer);
	}
	
	public void removeNeighbor(Node neighbor){
		connections.removeNeighbor(neighbor);
	}
	
	public void removeUpPointer(Node upPointer){
		connections.removeUpPointer(upPointer);
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
	
	public ArrayList<Node> getDownPointers(){
		return connections.getDownPointers();
	}
	
	public ArrayList<Node> getUpPointers(){
		return connections.getUpPointers();
	}
	
	public ArrayList<Node> getNeighbors(){
		return connections.getNeighbors();
	}
	
	public Node getFold(){
		return connections.getFold();
	}
	
	public Node getSurrogateFold(){
		return connections.getSurrogateFold();
	}
	
	public Node getInverseSurrogateFold(){
		return connections.getInverseSurrogateFold();
	}
	
	//------------------
	//  S E T T E R S
	//------------------
	public void setFold(Node fold){
		if(fold == null) {
			connections.setFold(Node.NULL_NODE);
		}
		else {
			connections.setFold(fold);
		}
	}
	
	public void setInverseSurrogateFold(Node inverseSurrogateFold){
		if(inverseSurrogateFold == null) {
			connections.setInverseSurrogateFold(Node.NULL_NODE);
		}
		else {
			connections.setInverseSurrogateFold(inverseSurrogateFold);
		}
	}
	
	public void setSurrogateFold(Node surrogateFold){
		if(surrogateFold == null) {
			connections.setSurrogateFold(Node.NULL_NODE);
		}
		else {
			connections.setSurrogateFold(surrogateFold);
		}
	}
	
	public void setWebId(WebId webId){
		if(webId == null) {
			this.webId = WebId.NULL_WEB_ID;
		}
		else {
			this.webId = webId;
		}
	}
	
	@Override
	public String toString(){
		return "Node [webId=" + webId + ", downPointers=" + connections.getDownPointers()
				+ ", upPointers=" + connections.getUpPointers() + ", neighbors=" + connections.getNeighbors()
				+ ", fold=" + connections.getFold() + ", surrogateFold=" + connections.getSurrogateFold()
				+ ", inverseSurrogateFold=" + connections.getInverseSurrogateFold() + "]";
	}

	@Override
	public int hashCode(){
		/*final int prime = 31;
		int result = 1;
		result = prime * result + ((webId == null) ? 0 : webId.hashCode());
		result = prime * result + ((downPointers == null) ? 0 : downPointers.hashCode());
		result = prime * result + ((upPointers == null) ? 0 : upPointers.hashCode());
		result = prime * result + ((fold == null) ? 0 : fold.hashCode());
		result = prime * result + ((inverseSurrogateFold == null) ? 0 : inverseSurrogateFold.hashCode());
		result = prime * result + ((neighbors == null) ? 0 : neighbors.hashCode());
		result = prime * result + ((surrogateFold == null) ? 0 : surrogateFold.hashCode());*/
		return webId.hashCode();
	}

	@Override
	public boolean equals(Object obj){
		
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Node other = (Node) obj;
		
		boolean webId = this.webId == other.webId;
		/*
		boolean down = this.downPointers == other.downPointers;
		boolean up = this.upPointers == other.upPointers;
		boolean fold = this.fold.equals(other.fold);
		boolean isf = this.inverseSurrogateFold.equals(other.inverseSurrogateFold);
		boolean neigh = this.neighbors.equals(other.neighbors);
		boolean sf = this.surrogateFold.equals(other.surrogateFold);
		*/
		return webId;// && down && up && fold && isf && neigh && sf;
	}

	@Override
	public int compareTo(Node other) {
		if(getWebIdValue() > other.getWebIdValue()){
			return 1;
		} else if(getWebIdValue() < other.getWebIdValue()){
			return -1;
		} else {
			return 0;
		}
	}
}