package gui.menuItems;

import gui.GUI;
import gui.controllers.SessionController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

/**
 * Represents the end session menu item presented in the Help menu
 * 
 * @author Matthew Smith
 *
 */
@SuppressWarnings("serial")
public class EndSessionMenuItem extends JMenuItem implements ActionListener{

	GUI main;
	
	/**
	 * Creates an End session menu Item
	 * @param main
	 */
	public EndSessionMenuItem (GUI main)
	{
		this.main = main;
		
		init();
	}
	
	/**
	 * initializes the GUI components
	 */
	public void init()
	{
		this.setText("End");
		
		this.addActionListener(this);
	}
	
	/**
	 * Action when menu item is pressed
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Phase 6 -- provide functionality for terminating an existing HyPeerWeb
		SessionController controller = new SessionController(this.main);
		controller.endHyPeerWebSegment();
	}

}
