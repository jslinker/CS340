package gui.newWindows;

import gui.GUI;
import gui.controllers.SendArgs;
import gui.controllers.SendWindowController;
import gui.controllers.SessionController;
import identification.GlobalObjectId;
import identification.LocalObjectId;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import communicator.PortNumber;



@SuppressWarnings("serial")
public class JoinWindowPanel
	extends JPanel
{
	protected GUI main;
	protected JoinWindow window;
	protected JLabel hypeerwebHostLabel;
	protected JLabel hypeerwebPortLabel;
    protected JTextField hypeerwebHost;
    protected JTextField hypeerwebPort;
    protected JButton joinButton;

    public JoinWindowPanel(GUI main, JoinWindow window) {
        //super(new GridBagLayout());
    	super(new GridLayout(3, 1));
    	this.main = main;
    	this.window = window;
    	
    	hypeerwebHostLabel = new JLabel("Host");
    	hypeerwebPortLabel = new JLabel("Port");

    	hypeerwebHost = new JTextField(15);
    	hypeerwebPort = new JTextField(5);
    	hypeerwebHost.setText("localhost");
    	hypeerwebPort.setText("49200");
        
		//Build the send button
		joinButton = new JButton("Join");
		joinButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				joinButtonPressed();				
			}
			public void windowClosing(WindowEvent we) {
			} 
		});

		
		JPanel hostPanel = new JPanel();
		hostPanel.add(hypeerwebHostLabel);
		hostPanel.add(hypeerwebHost);
		this.add(hostPanel);
		
		JPanel portPanel = new JPanel();
		portPanel.add(hypeerwebPortLabel);
		portPanel.add(hypeerwebPort);
		this.add(portPanel);
		
		this.add(joinButton);
    }
    
    private void joinButtonPressed(){
    	try {
    		main.setDebugContent("");
    		PortNumber port = new PortNumber(Integer.parseInt(hypeerwebPort.getText()));
    		GlobalObjectId id = new GlobalObjectId(hypeerwebHost.getText(), port, new LocalObjectId(LocalObjectId.INITIAL_ID));
    		SessionController controller = new SessionController(main);
    		controller.joinHyPeerWebSegment(id);
    	} catch(NumberFormatException e){
    		main.setDebugContent("Please enter a valid integer for the port.");
    		return;
    	} catch(NullPointerException e){
    		main.setDebugContent("Please enter a valid host.");
    		return;
    	}
    	
    	window.dispose();
    }
}


