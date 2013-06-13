package gui.controllers;

import gui.GUI;
import hypeerweb.node.Node;



/**
 * Controller for the broadcast window.  Handles the notifying of observers
 * when actions are performed in the broadcast window.
 * 
 * @author Nathan Zabriskie
 *
 */
public class BroadcastWindowController  {
	
	private GUI main;
	
	public BroadcastWindowController(GUI main){
		this.main = main;
	}
	
	public void startBroadcast(BroadcastArgs args){
		Node startNode = main.getHyPeerWeb().getNode(args.getStartId());
		if(startNode == Node.NULL_NODE){
			main.setDebugContent("Please select an existing webId as the start node");
			return;
		} else {
			Broadcaster broadcaster = new Broadcaster(this.main.getFacade());
			
			broadcaster.visit(startNode, Broadcaster.createInitialParameters(args.getMessage()));
		}
	}
}
