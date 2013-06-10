package gui.controllers;

import communicator.PortNumber;

import utilities.Shell;
import gui.GUI;
import hypeerweb.HyPeerWebSegment;
import hypeerweb.NullHyPeerWebSegment;
import identification.GlobalObjectId;

public class SessionController {

	private GUI main = null;
	
	public SessionController(GUI main){
		this.main = main;
	}
	
	public void newHyPeerWebSegment(){
		//Shell.executeCommand("java HyPeerWebSegment "+PortNumber.DEFAULT_PORT_NUMBER);
		this.joinHyPeerWebSegment(new GlobalObjectId("localhost", PortNumber.DEFAULT_PORT_NUMBER, null));
	}
	
	public void joinHyPeerWebSegment(GlobalObjectId hypeerwebSegmentId){
		//TODO create HyPeerWebProxy
		HyPeerWebSegment web = HyPeerWebSegment.getSingleton();
		web.registerObserver(main.getFacade());
		main.setHyPeerWeb(web);
		main.getNodeListing().initList();
	}
	
	public void leaveHyPeerWebSegment(){
		main.getHyPeerWeb().unregisterObserver(main.getFacade());
		main.getNodeListing().initList();
		main.setHyPeerWeb(new NullHyPeerWebSegment());
	}
	
	public void endHyPeerWebSegment(){
		main.getHyPeerWeb().shutdown();
		main.setHyPeerWeb(new NullHyPeerWebSegment());
	}
}
