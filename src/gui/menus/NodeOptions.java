package gui.menus;

import gui.GUI;
import gui.menuItems.ObserverMenuItem;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Menu for display node options related to a node
 * 
 * @author Matthew Smith
 *
 */
@SuppressWarnings("serial")
public class NodeOptions extends JPopupMenu{
	
	private GUI main;
	
	private JMenuItem remoteOpen;
	
	private JMenuItem jumpTo;
	
	/**
	 * Creates a node options menu
	 * @param main
	 */
	public NodeOptions(GUI main){
		this.main = main;
		
		init();
	}
	
	/**
	 * initializes GUI components
	 */
	public void init()
	{
		remoteOpen = new ObserverMenuItem(main);;
		this.add(remoteOpen);
		
		jumpTo = new JMenuItem("Jump To");
		this.add(jumpTo);
	}
}
