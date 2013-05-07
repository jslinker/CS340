package hypeerweb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

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
	
	public void addDownPointer(Node downPointer){
		this.downPointers.add(downPointer);
	}
	
	public void addNeighbor(Node neighbor){
		this.neighbors.add(neighbor);
	}
	
	public void addUpPointer(Node upPointer){
		this.upPointers.add(upPointer);
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
	
	public int getWebIdValue(){
		return webId.getValue();
	}
	
	public boolean equals(Node otherNode){
		return this.webId == otherNode.webId;
	}
}
