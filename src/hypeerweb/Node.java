package hypeerweb;

public class Node {
	
	private WebId webId = null;
	
	public static final Node NULL_NODE = new Node();
	
	private Node(){
		webId = WebId.NULL_WEB_ID;
	}

	public Node(int id){
		
	}
	
	public Node(int id, int height){
		
	}
	
	public void addDownPointer(Node downPointer){
		
	}
	
	public void addNeighbor(Node neighbor){
		
	}
	
	public void addUpPointer(Node upPointer){
		
	}
	
	public SimplifiedNodeDomain constructSimplifiedNodeDomain(){
		return null;
	}
	
	public WebId getWebId(){
		return webId;
	}
	
	public void removeDownPointer(Node downPointer){
		
	}
	
	public void removeNeighbor(Node neighbor){
		
	}
	
	public void removeUpPointer(Node upPointer){
		
	}
	
	public void setFold(Node fold){
		
	}
	
	public void setInverseSurrogateFold(Node inverseSurrogateFold){
		
	}
	
	public void setSurrogateFold(Node surrogateFold){
		
	}
	
	public void setWebId(WebId webId){
		
	}
}
