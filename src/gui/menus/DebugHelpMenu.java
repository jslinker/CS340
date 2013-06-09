package gui.menus;

import gui.GUI;
import gui.menuItems.AboutMenuItem;
import gui.menuItems.HelpContentsMenuItem;

import javax.swing.JMenu;
import javax.swing.JMenuItem;


/**
 * Represents the Debug help menu in the GUI
 * 
 * @author msmith52
 *
 */
@SuppressWarnings("serial")
public class DebugHelpMenu extends JMenu {
	
	/** Root of the GUI */
	public GUI main;
	
	/** About menu item */
	private JMenuItem about;
	
	/** Contents menu item */
	private JMenuItem contents;
	
	/**
	 * Create a debug help menu
	 * @param main
	 */
	public DebugHelpMenu(GUI main)
	{
		this.main = main;
		
		init();
	}
	
	/**
	 * initializes GUI components
	 */
	public void init()
	{
		this.setText("Help");
		
		contents = new HelpContentsMenuItem(main);
		this.add(contents);
		
		about = new AboutMenuItem(main);
		this.add(about);
	}
}
