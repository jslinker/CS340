package gui;

import gui.mapper.NodeListing;
import gui.printer.DebugPrinter;
import hypeerweb.HyPeerWebSegment;
import hypeerweb.NullHyPeerWebSegment;
import identification.LocalObjectId;
import identification.ObjectDB;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import communicator.PeerCommunicator;
import communicator.PortNumber;





/**
 * The central GUI used to display information about the HyPeerWeb and debug information
 * 
 * @author Matthew Smith
 *
 */
@SuppressWarnings("serial")
public class GUI extends JFrame implements Observer
{
	public static final PortNumber DEFAULT_GUI_PORT_NUMBER = new PortNumber(49201);
	
	private static GUI singleton = null;
	private GUIFacade facade = null;
	
	/** Main Debugger Panel**/
	private HyPeerWebDebugger debugger;
	
	private HyPeerWebSegment hypeerweb;
	private JScrollPane scrollPane;
	
	/**
	 * Creates and initializes the GUI as being the root
	 */
	public GUI(HyPeerWebSegment hypeerweb){
		this.hypeerweb = hypeerweb;
		
		LocalObjectId facadeLocalId = LocalObjectId.getFirstId();
		this.facade = new GUIFacade(this);
		facade.setLocalId(facadeLocalId);
		ObjectDB.getSingleton().store(facadeLocalId, this.facade);
		
		PeerCommunicator.createPeerCommunicator(DEFAULT_GUI_PORT_NUMBER);
		
		this.setTitle("HyPeerWeb DEBUGGER V 1.1");

		this.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent we) {
				shutdown();
			  }
			});
		
		debugger = new HyPeerWebDebugger(this);
		scrollPane = new JScrollPane(debugger);
		scrollPane.setPreferredSize(new Dimension(debugger.WIDTH+20, debugger.HEIGHT));
		
		this.getContentPane().add(scrollPane);
		
		this.pack();
	}
	
	public void shutdown(){
		System.exit(0);
	}
	
	public static GUI getSingleton(HyPeerWebSegment hypeerweb){
		if(singleton == null){
			try{
				singleton = new GUI(hypeerweb);
				singleton.setVisible(true);
			}
			catch(Exception e)	{
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"ERROR",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				System.exit(1);
			}
		}
		return singleton;
	}
	
	/**
	 * Start Point of the Program
	 */
	public static void main (String[] args){
		SwingUtilities.invokeLater(new Runnable() {		
			@Override
			public void run() {

				GUI gui = GUI.getSingleton(new NullHyPeerWebSegment());
				gui.setVisible(true);
			}
		});
	}

	/**
	 * Retrieves the HyPeerWeb Debugging Panel
	 * @return HyPeerWebDebugger
	 */
	public HyPeerWebDebugger getHyPeerWebDebugger() {
		return debugger;
	}
	
	public HyPeerWebSegment getHyPeerWeb(){
		return hypeerweb;
	}
	
	public void setHyPeerWeb(HyPeerWebSegment segment){
		this.hypeerweb = segment;
	}
	
	public void printToTracePanel(Object msg){
		debugger.getTracePanel().print(msg);
	}
	
	public void finalize(){
		hypeerweb.close();
	}
	
	public void setDebugContent(String newContent){
		debugger.setDebugContent(newContent);
	}

	/**
	 * Arguments for arg1 are strings as follows: cleared, shutdown, addedNode, removedNode
	 */
	@Override
	public void update(Observable arg0, Object arg1){
		if(arg1 instanceof String){
			String notification = (String) arg1;
			if(notification.equals("cleared")){
				getNodeListing().clear();
			}
			else if(notification.equals("shutdown")){
				setHyPeerWeb(new NullHyPeerWebSegment());
				getNodeListing().clear();
			}
			else if(notification.equals("addedNode")){
				getNodeListing().increaseListSize();
			}
			else if(notification.equals("removedNode")){
				getNodeListing().decreaseListSize();
			}
			else{
				//do nothing
			}
		}
	}
	
	public NodeListing getNodeListing(){
		return debugger.getNodeListing();
	}
	
	public DebugPrinter getTracePanel(){
		return debugger.getTracePanel();
	}

	public boolean isConnectedToHyPeerWeb() {
		if(hypeerweb instanceof NullHyPeerWebSegment){
			return false;
		}
		else{
			return true;
		}
	}
	
	public GUIFacade getFacade(){
		return this.facade;
	}
}
