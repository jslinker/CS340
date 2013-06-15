package hypeerweb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ObjectStreamException;
import java.util.Enumeration;

import hypeerweb.node.ExpectedResult;
import hypeerweb.node.Node;
import identification.GlobalObjectId;
import identification.LocalObjectId;
import identification.ObjectDB;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import communicator.MachineAddress;
import communicator.PeerCommunicator;
import communicator.PortNumber;

public class HyPeerWebSegmentProxyTests {

	
	private static HyPeerWebSegment proxy;
	private static HyPeerWebSegment web;
	private static GlobalObjectId serverGlobalId;

	@BeforeClass
	public static void setupClass(){
		LocalObjectId first = new LocalObjectId(LocalObjectId.INITIAL_ID);
		PeerCommunicator.createPeerCommunicator(new PortNumber(65000));
		try {
			Class.forName("hypeerweb.HyPeerWebSegment");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}//ensures that the static block is executed
		
		
		serverGlobalId = new GlobalObjectId(MachineAddress.getThisMachinesInetAddress().getHostAddress(), new PortNumber(49200), first);
		proxy = new HyPeerWebSegmentProxy(serverGlobalId);
		web = HyPeerWebSegment.getSingleton();
		web.connectSegment(proxy);
		web.clear();
		try {
			proxy.connectSegment((HyPeerWebSegmentProxy)HyPeerWebSegment.getSingleton().writeReplace());
		} catch (ObjectStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Before
	public void setup(){
	//		proxy.clear();
	//		web.clear();
	}
		
	@AfterClass
	public static void teardownClass(){
		//PeerCommunicator.stopConnection(serverGlobalId);
		PeerCommunicator.stopThisConnection();
	}
		
	@Test
	public void testAddNodes(){
		
		proxy.clear();
		proxy.addNode(-1);
		assertEquals(proxy.getNode(0).constructSimplifiedNodeDomain(), new ExpectedResult(1,0));
		proxy.addNode(-1);
		assertEquals(proxy.getNode(1).constructSimplifiedNodeDomain(), new ExpectedResult(2,1));
		proxy.addNode(-1);
		assertEquals(proxy.getNode(2).constructSimplifiedNodeDomain(), new ExpectedResult(3,2));
		try{
			assertEquals(proxy.size(), 3);
		}
		catch(Exception e){
			fail(e.getMessage());
		}
		
		Node newNode = new Node(6000);
		HyPeerWebSegment.getSingleton().addToHyPeerWeb(newNode, null);
		assertEquals(newNode.getWebIdValue(), 3);
		
		assertEquals(new ExpectedResult(4, 3),web.getNodeByWebId(3).constructSimplifiedNodeDomain());
		
		ObjectDB.getSingleton().dump();
		newNode = new Node(6000);
		HyPeerWebSegment.getSingleton().addToHyPeerWeb(newNode, null);
		assertEquals(newNode.getWebIdValue(), 4);
		assertEquals(new ExpectedResult(5, 4),web.getNodeByWebId(4).constructSimplifiedNodeDomain());
		
		assertEquals(proxy.size(), 3);
	}
	
	@Test
	public void testRemove(){
		web.removeFromHyPeerWeb(web.getNodeByWebId(3));
		assertEquals(new ExpectedResult(4,3),web.getNodeByWebId(3).constructSimplifiedNodeDomain());
		assertEquals( new ExpectedResult(4,0), proxy.getNode(0).constructSimplifiedNodeDomain());
		assertEquals( new ExpectedResult(4,1), proxy.getNode(1).constructSimplifiedNodeDomain());
		assertEquals( new ExpectedResult(4,2), proxy.getNode(2).constructSimplifiedNodeDomain());
	}
	
	@Test
	public void testBroadcast(){
		web.sendBroadcast();		//had to create a method for testing, something weird was happening with JUNIT
	}
	
	@Test
	public void testKill(){
		proxy.kill();
		Enumeration<Object> e = ObjectDB.getSingleton().enumeration();

		
		assertEquals(4, web.getNodes().size());
		for(int i = 0; i < 4; i++){
			assertEquals(new ExpectedResult(4,i), web.getNodeByWebId(i).constructSimplifiedNodeDomain());
		}
	}
}
