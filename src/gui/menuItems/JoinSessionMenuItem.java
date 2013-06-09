package gui.menuItems;

import gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;


/**
 * Represents the Join session menu item presented in the Help menu
 * 
 * @author Matthew Smith
 *
 */
@SuppressWarnings("serial")
public class JoinSessionMenuItem extends JMenuItem implements ActionListener{

	GUI main;
	
	/**
	 * Creates an Join session menu Item
	 * @param main
	 */
	public JoinSessionMenuItem (GUI main)
	{
		this.main = main;
		
		init();
	}
	
	/**
	 * initializes the GUI components
	 */
	public void init()
	{
		this.setText("Join");
		
		this.addActionListener(this);
	}
	
	/**
	 * Action when menu item is pressed
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Phase 6 -- provide functionality for joining an existing HyPeerWeb
		
	}

}
