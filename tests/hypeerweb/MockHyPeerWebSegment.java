package hypeerweb;

import hypeerweb.node.Node;

import java.util.ArrayList;

public class MockHyPeerWebSegment extends HyPeerWebSegment{
	
	private static MockHyPeerWebSegment singleton;
	
	public MockHyPeerWebSegment(){
		this.nodes = new ArrayList<Node>();
	}
	
	public static HyPeerWebSegment getSingleton(){
		return singleton;
	}

}
