package gui.controllers;

import gui.GUI;
import hypeerweb.HyPeerWebSegmentProxy;
import hypeerweb.NullHyPeerWebSegment;
import identification.GlobalObjectId;
import identification.LocalObjectId;
import utilities.Shell;

import communicator.PortNumber;

public class SessionController {

	private GUI main = null;
	
	public SessionController(GUI main){
		this.main = main;
	}
	
	public void newHyPeerWebSegment(){
		Shell.executeCommand("java -cp C:/Users/V/Documents/GitHub/CS340/bin hypeerweb.HyPeerWebSegment "+PortNumber.DEFAULT_PORT_NUMBER.getValue());
		this.joinHyPeerWebSegment(new GlobalObjectId("localhost", 
														PortNumber.DEFAULT_PORT_NUMBER, 
														new LocalObjectId(LocalObjectId.INITIAL_ID)));
	}
	
	public void joinHyPeerWebSegment(GlobalObjectId hypeerwebSegmentId){
		//TODO create HyPeerWebProxy
		//HyPeerWebSegment web = HyPeerWebSegment.getSingleton();
		HyPeerWebSegmentProxy proxy = new HyPeerWebSegmentProxy(hypeerwebSegmentId);
		proxy.addObserver(main.getFacade());
		main.setHyPeerWeb(proxy);
		main.getNodeListing().initList();
	}
	
	public void leaveHyPeerWebSegment(){
		main.getHyPeerWeb().deleteObserver(main);
		main.getNodeListing().initList();
		main.setHyPeerWeb(new NullHyPeerWebSegment());
	}
	
	public void endHyPeerWebSegment(){
		main.getHyPeerWeb().kill();
		main.setHyPeerWeb(new NullHyPeerWebSegment());
	}
}
