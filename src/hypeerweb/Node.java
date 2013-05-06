package hypeerweb;

import java.util.ArrayList;

public class Node {
	
	private WebId webId = null;
	private ArrayList<Node> downPointers = null;
	private ArrayList<Node> upPointers = null;
	private ArrayList<Node> neighbors = null;
	private Node fold = null;
	private Node surrogateFold = null;
	private Node inverseSurrogateFold = null;
	
	public static final Node NULL_NODE = new Node();
	
	private final int DEFAULT_HEIGHT = 0;
	
	private Node(){
		webId = WebId.NULL_WEB_ID;
		downPointers = new ArrayList<Node>();
		upPointers = new ArrayList<Node>();
		neighbors = new ArrayList<Node>();
	}

	public Node(int id){
		assert(id >= 0);
		webId = new WebId(id, DEFAULT_HEIGHT);
	}
	
	public Node(int id, int height){
		assert(id >= 0 && height >= 0);
		webId = new WebId(id, height);
	}
	
	public void addDownPointer(Node downPointer){
		this.downPointers.add(downPointer);
	}
	
	public void addNeighbor(Node neighbor){
		this.neighbors.add(neighbor);
	}
	
	public void addUpPointer(Node upPointer){
		this.upPointers.add(upPointer);
	}
	
	public SimplifiedNodeDomain constructSimplifiedNodeDomain(){
		return null;
	}
	
	public WebId getWebId(){
		return webId;
	}
	
	public void removeDownPointer(Node downPointer){
		this.downPointers.remove(downPointer);
	}
	
	public void removeNeighbor(Node neighbor){
		this.neighbors.remove(neighbor);
	}
	
	public void removeUpPointer(Node upPointer){
		this.upPointers.remove(upPointer);
	}
	
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
	
	public boolean equals(Node otherNode){
		return this.webId == otherNode.webId;
	}
}
