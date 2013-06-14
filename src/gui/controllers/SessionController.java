package gui.controllers;

import gui.GUI;
import hypeerweb.HyPeerWebSegmentProxy;
import hypeerweb.NullHyPeerWebSegment;
import identification.GlobalObjectId;
import identification.LocalObjectId;

import communicator.PortNumber;

public class SessionController {

	private GUI main = null;
	
	public SessionController(GUI main){
		this.main = main;
	}
	
	public void newHyPeerWebSegment(){
		//TODO get this to create a separate process on the same machine
		//Shell.executeCommand("java -cp C:/Users/V/Documents/GitHub/CS340/bin hypeerweb.HyPeerWebSegment "+PortNumber.DEFAULT_PORT_NUMBER.getValue());
		//currently just joins a segment already on the current machine
		this.joinHyPeerWebSegment(new GlobalObjectId("localhost", 
														PortNumber.DEFAULT_PORT_NUMBER, 
														LocalObjectId.getFirstId()));
	}
	
	public void joinHyPeerWebSegment(GlobalObjectId hypeerwebSegmentId){
		HyPeerWebSegmentProxy proxy = new HyPeerWebSegmentProxy(hypeerwebSegmentId);
		proxy.addObserver(main.getFacade());
		main.setHyPeerWeb(proxy);
		main.getNodeListing().reset();
	}
	
	public void leaveHyPeerWebSegment(){
		main.getHyPeerWeb().deleteObserver(main.getFacade());
		main.setHyPeerWeb(new NullHyPeerWebSegment());
		main.getNodeListing().reset();
	}
	
	public void endHyPeerWebSegment(){
		main.getHyPeerWeb().kill();
		main.setHyPeerWeb(new NullHyPeerWebSegment());
		main.getNodeListing().reset();
	}
}
