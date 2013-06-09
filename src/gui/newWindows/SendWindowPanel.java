package gui.newWindows;

import gui.GUI;
import gui.controllers.SendArgs;
import gui.controllers.SendWindowController;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



@SuppressWarnings("serial")
public class SendWindowPanel
	extends JPanel
{
	protected GUI main;
	protected JLabel startingNodeLabel;
	protected JLabel endingNodeLabel;
	protected JLabel messageBoxLabel;
    protected JTextField startingNode;
    protected JTextField endingNode;
    protected JTextField messageBox;
    protected JButton sendButton;
    
    private SendWindowController controller;

    public SendWindowPanel(GUI main) {
        //super(new GridBagLayout());
    	super(new GridLayout(3, 1));
    	this.main = main;
    	
    	startingNodeLabel = new JLabel("Starting Node");
    	endingNodeLabel = new JLabel("Ending Node");
    	messageBoxLabel = new JLabel("Message");

        startingNode = new JTextField(3);
        endingNode = new JTextField(3);
        messageBox = new JTextField(20);	
        
		//Build the send button
		sendButton = new JButton("Send Message");
		sendButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				sendButtonPressed();				
			}
			public void windowClosing(WindowEvent we) {
				setSendWindowToNull();  
			} });

		
		JPanel startingEndingNodePanel = new JPanel();
		startingEndingNodePanel.add(startingNodeLabel);
		startingEndingNodePanel.add(startingNode);
		startingEndingNodePanel.add(endingNodeLabel);
		startingEndingNodePanel.add(endingNode);
		this.add(startingEndingNodePanel);
		
		JPanel messageNodePanel = new JPanel();
		messageNodePanel.add(messageBoxLabel);
		messageNodePanel.add(messageBox);
		this.add(messageNodePanel);
		
		this.add(sendButton);

		controller = new SendWindowController(main);
    }
    
    private void setSendWindowToNull(){
    	main.getHyPeerWebDebugger().getStandardCommands().setSendWindowToNull();
    }
    
    private void sendButtonPressed(){
    	//TODO Phase 5 -- starting at the indicated node, send the provided message to the target node.
    	//	I. Get the text in the "startingNode" component and convert it to an integer identifying the start node.
    	//		A. If the indicated start node is empty, or does not contain an integer, or does not identify an
    	//			existing node in the HyPeerWeb, post an error message in the "debugStatus" component of the GUI.
    	//		B. If successful, get the text in the "endingNode" component and convert it to an integer identifying the target node.
    	//			1. If the indicated target node is empty, or does not contain an integer, or does not identify an
    	//			existing node in the HyPeerWeb, post an error message in the "debugStatus" component of the GUI.
    	//			2. Otherwise, get the message from the "messageBox" component and send it from the start node to the target node
    	//				in the HyPeerWeb using the "GUISender" visitor.
    	
    	int startId = 0;
    	int targetId = 0;
    	String message = null;
    	try {
    		main.setDebugContent("");
    		startId = Integer.parseInt(startingNode.getText());
    		targetId = Integer.parseInt(endingNode.getText());
    		message = messageBox.getText();
    	} catch(NumberFormatException e){
    		main.setDebugContent("Please enter a valid integer as the startId");
    		return;
    	} catch(NullPointerException e){
    		main.setDebugContent("Please enter a valid string in the message box.");
    		return;
    	}
    	
    	controller.startSend(new SendArgs(startId, targetId, message));
    }
}


