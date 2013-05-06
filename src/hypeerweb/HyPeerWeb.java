package hypeerweb;

import java.util.HashSet;

public class HyPeerWeb {
	
	private static HyPeerWeb singleton = null;
	
	private HashSet<Node> nodes = null;
	
	private HyPeerWeb(){
		this.nodes = new HashSet<Node>();
	}
	
	public static HyPeerWeb getSingleton(){
		if(singleton == null) {
			singleton = new HyPeerWeb();
		}
		return singleton;
	}
	
	public void addNode(Node node){
		this.nodes.add(node);
	}
	
	public void clear(){
		
	}
	
	public boolean contains(Node node){
		return this.nodes.contains(node);
	}
	
	public HyPeerWebDatabase getHyPeerWebDatabase(){
		return null;
	}
	
	public Node getNode(int i){
		return null;
	}
	
	public void reload(){
		
	}
	
	public void reload(String dbName){
		
	}
	
	public void removeNode(Node node){
		this.nodes.remove(node);
	}
	
	public void saveToDatabase(){
		
	}
	
	public int size(){
		return this.nodes.size();
	}
}
