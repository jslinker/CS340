package gui;

import gui.controllers.BroadcastArgs;
import gui.controllers.BroadcastWindowController;
import gui.mapper.NodeListing;
import gui.printer.DebugPrinter;
import hypeerweb.HyPeerWebSegment;
import hypeerweb.node.Node;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;





/**
 * The central GUI used to display information about the HyPeerWeb and debug information
 * 
 * @author Matthew Smith
 *
 */
@SuppressWarnings("serial")
public class GUI extends JFrame implements Observer
{
	private static GUI singleton = null;
	
	/** Main Debugger Panel**/
	private HyPeerWebDebugger debugger;
	
	private HyPeerWebSegment hypeerweb;
	private JScrollPane scrollPane;
	
	/**
	 * Creates and initializes the GUI as being the root
	 */
	public GUI(HyPeerWebSegment hypeerweb){
		this.hypeerweb = hypeerweb;
		//TODO allow for reloading of HyPeerWeb segment from the database.
		hypeerweb.reload();
		this.setTitle("HyPeerWeb DEBUGGER V 1.1");

		this.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent we) {
				shutdown();
			    System.exit(0);
			  }
			});
		
		debugger = new HyPeerWebDebugger(this);
		scrollPane = new JScrollPane(debugger);
		scrollPane.setPreferredSize(new Dimension(debugger.WIDTH+20, debugger.HEIGHT));
		
		this.getContentPane().add(scrollPane);
		
		this.pack();
	}
	
	private void shutdown(){
		hypeerweb.close();
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
				hypeerweb.close();
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

				GUI gui = GUI.getSingleton(HyPeerWebSegment.getSingleton());
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
	
	public void printToTracePanel(Object msg){
		debugger.getTracePanel().print(msg);
	}
	
	public void finalize(){
		hypeerweb.close();
	}
	
	public void setDebugContent(String newContent){
		debugger.setDebugContent(newContent);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		update(arg0, arg1);
	}
	
	public NodeListing getNodeListing(){
		return debugger.getNodeListing();
	}
	
	public DebugPrinter getTracePanel(){
		return debugger.getTracePanel();
	}
}
