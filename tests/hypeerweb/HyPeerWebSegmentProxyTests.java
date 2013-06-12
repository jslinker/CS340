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
		GlobalObjectId serverGlobalId = new GlobalObjectId(first);
		HyPeerWebSegmentProxy proxy = new HyPeerWebSegmentProxy(serverGlobalId);
		
		try{
			assertTrue(proxy.size() == 0);
		}
		catch(Exception e){
			fail(e.getMessage());
		}
		System.out.println("here2");
		
		PeerCommunicator.stopConnection(serverGlobalId);
		PeerCommunicator.stopThisConnection();
	}
}
