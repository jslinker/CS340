package gui;

import hypeerweb.HyPeerWebObserver;
import hypeerweb.NullHyPeerWebSegment;

/**
 * Functions as an interface between the GUI and another application, namely the HyPeerWebSegment, 
 * so that a proxy can be made of this for easy network communication.
 * @author Craig Jacobson
 *
 */
public class GUIFacade implements HyPeerWebObserver{
	
	private GUI main = null;

	public GUIFacade(GUI main){
		this.main = main;
	}

	@Override
	public void notifyNodeAdded(int nodeWebId){
		//TODO make this method add a node with the given webId
		main.getNodeListing().increaseListSize();
	}
	
	public void notifyNodeRemoved(int nodeWebId){
		//TODO make this method remove the node with the given webId
		main.getNodeListing().decreaseListSize();
	}
	
	public void notifyCleared(){
		main.getNodeListing().initList();
	}

	@Override
	public void notifyHyPeerWebSegmentShutdown() {
		main.setHyPeerWeb(new NullHyPeerWebSegment());
		main.getNodeListing().initList();
	}
}
