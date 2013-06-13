package hypeerweb;

import hypeerweb.node.Node;
import identification.GlobalObjectId;
import identification.LocalObjectId;
import identification.ObjectDB;
import junit.framework.TestCase;

import communicator.PeerCommunicator;
import communicator.PortNumber;

public class HyPeerWebSegmentProxyTests extends TestCase{

	public void testConnect(){
		//start server first!!
		
		LocalObjectId first = new LocalObjectId(LocalObjectId.INITIAL_ID);
		
		PeerCommunicator.createPeerCommunicator(new PortNumber(49153));
		GlobalObjectId serverGlobalId = new GlobalObjectId("localhost", new PortNumber(PortNumber.DEFAULT_PORT_NUMBER),first);
		HyPeerWebSegmentProxy proxy = new HyPeerWebSegmentProxy(serverGlobalId);
		try{
			assertEquals(0, proxy.size());
		}
		catch(Exception e){
			fail(e.getMessage());
		}
//		Node toAdd = new Node(0);
//		ObjectDB.getSingleton().store(toAdd.getLocalObjectId(), toAdd);
//		proxy.addToHyPeerWeb(toAdd, Node.NULL_NODE);
//		assertEquals(1, proxy.size());
		
		proxy.addNode(-1);
		assertEquals(1, proxy.size());
		
		PeerCommunicator.stopConnection(serverGlobalId);
		PeerCommunicator.stopThisConnection();
	}
}
