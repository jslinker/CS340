package menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import menuItems.EndSessionMenuItem;
import menuItems.JoinSessionMenuItem;
import menuItems.LeaveSessionMenuItem;
import menuItems.StartSessionMenuItem;
import Main.GUI;
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
	}

}
