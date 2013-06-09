package gui.newWindows;

import gui.GUI;

@SuppressWarnings("serial")
public class SendWindow extends PopUpWindow {

	public SendWindow(GUI main, String title){
		super(main, title);
	}
	
	@Override
	protected void addPanel() {
		panel = new SendWindowPanel(main);
		this.add(panel);
	}
}
