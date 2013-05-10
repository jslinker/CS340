package newWindows;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Main.GUI;

@SuppressWarnings("serial")
public class BroadcastWindowPanel
	extends JPanel
{
	protected GUI main;
	protected JLabel startingNodeLabel;
	protected JLabel messageBoxLabel;
    protected JTextField startingNode;
    protected JTextField messageBox;
    protected JButton broadcastButton;

    public BroadcastWindowPanel(GUI main) {
        //super(new GridBagLayout());
    	super(new GridLayout(3, 1));
    	this.main = main;
    	
    	startingNodeLabel = new JLabel("Starting Node");
    	messageBoxLabel = new JLabel("Message");

        startingNode = new JTextField(3);
        messageBox = new JTextField(20);	
        
		//Build the send button
		broadcastButton = new JButton("Broadcast Message");
		broadcastButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				broadcastButtonPressed();				
			} 
			public void windowClosing(WindowEvent we) {
				setBroadcastWindowToNull();  
			} });
		
		JPanel startingEndingNodePanel = new JPanel();
		startingEndingNodePanel.add(startingNodeLabel);
		startingEndingNodePanel.add(startingNode);
		this.add(startingEndingNodePanel);
		
		JPanel messageNodePanel = new JPanel();
		messageNodePanel.add(messageBoxLabel);
		messageNodePanel.add(messageBox);
		this.add(messageNodePanel);
		
		this.add(broadcastButton);

    }
    
    private void setBroadcastWindowToNull(){
    	main.getHyPeerWebDebugger().getStandardCommands().setBroadcastWindowToNull();
    }
    
    
    private void broadcastButtonPressed(){
    	//TODO Phase 5 -- starting at the indicated node, broadcast the provided message to all nodes in the HyPeerWeb.
    	//I. Get the text in the "startingNode" component and convert it to an integer.
    	//		A. If the indicated start node is empty, or does not contain an integer, or does not identify an
    	//			existing node in the HyPeerWeb, post an error message in the "debugStatus" component of the GUI.
    	//		B. Otherwise, get the message from the "messageBox" component and broadcast it to all nodes in the HyPeerWeb,
    	//			starting at the indicated start node, using the Broadcaster visitor.
    }
}