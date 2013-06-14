package gui;

import identification.GlobalObjectId;
import identification.LocalObjectId;
import identification.ObjectDB;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import communicator.MachineAddress;
import communicator.PeerCommunicator;
import communicator.PortNumber;

/**
 * Prevents the ProxyConstructor from creating proxy methods for every inherited method in the GUI.
 * Only the methods the HyPeerWebSegment will need should go in here.
 * @author Craig Jacobson
 *
 */
public class GUIFacade implements Observer, Serializable{

	private GUI main = null;
	private LocalObjectId localId = LocalObjectId.getFirstId();
	
	protected GUIFacade(){
	}
	
	public GUIFacade(GUI main){
		this.main = main;
		ObjectDB db = ObjectDB.getSingleton();
		localId = new LocalObjectId();
		db.store(localId, this);
	}

	public synchronized void update(Observable arg0, Object arg1) {
		main.update(arg0, arg1);
	}
	
	/**
	 * Replaces this object with a Proxy when serializing.
	 * @return
	 * @throws ObjectStreamException
	 */
	public Object writeReplace() throws ObjectStreamException{
		System.out.println(MachineAddress.getThisMachinesInetAddress());
		String machineAddress = "localhost";
		PortNumber portNumber = PeerCommunicator.getSingleton().getPortNumber();
		GlobalObjectId globalId = new GlobalObjectId(machineAddress, portNumber, localId);
		return new GUIProxy(globalId);
	}

	public void setLocalId(LocalObjectId localId) {
		this.localId = localId;
	}

	public synchronized void printToTracePanel(String message) {
		this.main.printToTracePanel(message);
	}
}
