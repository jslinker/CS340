package gui.menus;

import gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;


/**
 * Represents the file Menu
 * 
 * @author msmith52
 *
 */
@SuppressWarnings("serial")
public class DebugFileMenu extends JMenu{

	/** Root the GUI **/
	private GUI main;
	
	/** Debug session menu */
	private DebugSessionMenu sessionMenu;
	
	/** Exit menu item */
	private JMenuItem exit;
	
	/**
	 * Creates a Debug File Menu object
	 * @param main
	 */
	DebugFileMenu(GUI main)	{
		this.main = main;
		
		
		
		init();
	}
	
	/**
	 *  Initializes GUI components
	 */
	public void init()	{
		this.setText("File");
		
		sessionMenu = new DebugSessionMenu(main);
		this.add(sessionMenu);
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				destroy();
				main.getHyPeerWeb().close();
				System.exit(0);
			}
			
		});
		
		this.add(exit);
	}
	
	/**
	 *  Called before the program exits
	 */
	public void destroy()	{
		//TODO Phase 5 -- clean up before exiting the program
		main.shutdown();
	}
}
