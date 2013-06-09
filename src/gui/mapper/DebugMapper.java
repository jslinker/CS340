package gui.mapper;

import gui.GUI;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class DebugMapper extends JPanel {
	
	//private JTabbedPane content;
	
	private NodeListing mainTab;
	
	public DebugMapper(GUI main) {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("List Of Nodes In HyPeerWeb"));
		
		//content = new JTabbedPane();
		
		mainTab = new NodeListing(main);
		
		//content.addTab("Node Listing",mainTab);
		
		//this.add(content);
		this.add(mainTab);
	}
	
	public NodeListing getNodeListing(){
		return mainTab;
	}

}
