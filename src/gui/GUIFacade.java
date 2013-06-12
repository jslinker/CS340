package gui;

import identification.GlobalObjectId;
import identification.LocalObjectId;
import identification.ObjectDB;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 * Prevents the ProxyConstructor from creating proxy methods for every inherited method in the GUI.
 * Only the methods the HyPeerWebSegment will need should go in here.
 * @author Craig Jacobson
 *
 */
public class GUIFacade implements Observer, Serializable{

	private GUI main = null;
	private LocalObjectId localId = null;
	
	protected GUIFacade(){
	}
	
	public GUIFacade(GUI main){
		this.main = main;
		ObjectDB db = ObjectDB.getSingleton();
		localId = new LocalObjectId();
		db.store(localId, this);
	}

	public void update(Observable arg0, Object arg1) {
		main.update(arg0, arg1);
	}
	
	public Object writeReplace() throws ObjectStreamException{
		return new GUIProxy(new GlobalObjectId(localId));
	}
}
