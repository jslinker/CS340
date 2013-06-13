package hypeerweb.node;

import hypeerweb.HyPeerWebSegment;
import identification.ObjectDB;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import communicator.PeerCommunicator;

import junit.framework.TestCase;

public class NodeProxyTests extends TestCase{

	public void testSerialization(){
		HyPeerWebSegment.getSingleton();//may not be necessary, but ensures this test won't conflict with others
		PeerCommunicator.createPeerCommunicator();
		
		Node node0 = new Node(0);
		ObjectDB.getSingleton().store(node0.getLocalObjectId(), node0);
		
		File testFileDirectory = new File("test_files");
		testFileDirectory.mkdirs();
		File testFile = new File("test_files/test_file.object");
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			fos = new FileOutputStream(testFile);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(node0);
			
			fos.close();
			oos.close();
			
			fis = new FileInputStream(testFile);
			ois = new ObjectInputStream(fis);
			Object object = ois.readObject();
			assertTrue(object instanceof Node);
			assertFalse(object instanceof NodeProxy);
			assertTrue(object == node0);
		}
		catch(IOException e){
			e.printStackTrace(System.err);
			fail();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace(System.err);
			fail();
		}
		finally{
			safeClose(fis);
			safeClose(ois);
		}
		
		PeerCommunicator.stopThisConnection();
	}
	
	public static void safeClose(Closeable c){
		if(c != null){
			try{
				c.close();
			}
			catch(Exception e){
				
			}
		}
	}
}
