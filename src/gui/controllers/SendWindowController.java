package gui.controllers;

import gui.GUI;
import hypeerweb.broadcast.Parameters;
import hypeerweb.node.Node;

public class SendWindowController {
	
	private GUI main;
	
	public SendWindowController(GUI main) {
		this.main = main;
	}
	
	public void startSend(SendArgs args){
		Node startNode = main.getHyPeerWeb().getNodeByWebId(args.getStartId());
		if(startNode == Node.NULL_NODE){
			main.setDebugContent("Invalid StartNode: Please select a starting node contained in the HyPeerWeb");
			return;
		}
		
		Node targetNode = main.getHyPeerWeb().getNodeByWebId(args.getTargetId());
		if(targetNode == Node.NULL_NODE){
			main.setDebugContent("Invalid Target: Please select a target node contained in the HyPeerWeb");
			return;
		}
		
		GUISender sender = new GUISender(this.main.getFacade());		
		Parameters param = GUISender.createInitialParameters(args.getTargetId(), args.getMessage());
		startNode.accept(sender, param);
	}
}
