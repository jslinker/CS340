package gui.menus;

import gui.GUI;
import gui.menuItems.EndSessionMenuItem;
import gui.menuItems.JoinSessionMenuItem;
import gui.menuItems.LeaveSessionMenuItem;
import gui.menuItems.StartSessionMenuItem;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Represents the Debug Session Menu
 * 
 * @author Matthew Smith
 *
 */
@SuppressWarnings("serial")
public class DebugSessionMenu extends JMenu{

	/** Root of the GUI */
	private GUI main;
	
	/** Start menu item */
	private JMenuItem start;
	
	/** Join menu item */
	private JMenuItem join;
	
	/** Leave menu item */
	private JMenuItem leave;
	
	/** End menu item */
	private JMenuItem end;
	
	/** Shutdown menu item */
	private JMenuItem shutdown;
	
	/**
	 * Creates a debug session menu
	 * @param main
	 */
	public DebugSessionMenu(GUI main) {
		this.main = main;
		
		init();
	}
	
	/**
	 * Initializes GUI components
	 */
	public void init()
	{
		this.setText("Session");
		
		start = new StartSessionMenuItem(main);
		add(start);
		
		join = new JoinSessionMenuItem(main);
		add(join);
		
		leave = new LeaveSessionMenuItem(main);
		add(leave);
		
		end = new EndSessionMenuItem(main);
		add(end);
		
//		shutdown = new ShutdownSessionMenuItem(main);
//		add(shutdown);
	}

}
