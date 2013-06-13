package gui.controllers;

import gui.GUIFacade;
import hypeerweb.broadcast.BroadcastVisitor;
import hypeerweb.broadcast.Parameters;
import hypeerweb.node.Node;

/**
 * Broadcasts a message from the start node (the first node to receive this visitor) to all other nodes in the HyPeerWeb.
 * Every time the message reaches node the indicated node and message are printed in the "TraceInformation" section of
 * the GUI.
 * 
 * <pre>
 * 		<b>Domain:</b> <i>None</i>
 * </pre>
 */
public class Broadcaster extends BroadcastVisitor {
	private static final long serialVersionUID = -2914188563284995229L;

	private GUIFacade facade = null;

	/**
	 * The default constructor. It does nothing but call the superclass's default constructor.
	 * 
	 * @pre <i>None</i>
	 * @post super.post-condition
	 */
	public Broadcaster(GUIFacade facade){
		this.facade = facade;
	}

	
	/**
	 * Creates the parameters needed to send the Broadcaster visitor to the first Node.
     *
	 * @param message  the message to be broadcast to all node.
	 * 
	 * @pre <i>None</i>
	 * @post result &ne; null AND result.contains(MESSAGE_KEY) AND result.get(MESSAGE_KEY) = message
	 */
	public static Parameters createInitialParameters(String message) {
		//TODO Phase 5 -- replace the next line with one or more lines implementing the initialization of the parameters.
		Parameters param = new Parameters();
		param.set(MESSAGE_KEY, message);
		return param;
	}
	
	@Override
	/**
	 * Prints a string in the TracePanel of the GUI.  The string should contain the message and the
	 * labeled webId of the current node.
	 * 
	 * @pre node &ne; null AND node &isin; HyPeerWeb AND parameters &ne; null AND parameters.contains(MESSAGE_KEY) AND parameters.contians(TRACE_KEY)
	 * @post A string with the message and current node's id should be printed on the tracePanel of the GUI.<br>
	 * Required format: "Broadcasting '" parameters.get(MESSAGE_ID) "' to node " node.getWebId() ".\n"
	 */
	protected void operation(Node node, Parameters parameters) {
		//Phase 5 -- implement this method so that it satisfies the post condition.
		String result = "";
		if(node.getWebIdValue() == 0){
			result += "----------------------------------------\n";
			result += "---- Starting Broadcast ----\n";
			result += "----------------------------------------\n";
		}
		
		result += String.format("Broadcasting '%s' to node %s.\n", parameters.get(MESSAGE_KEY), node.getWebId());
		this.facade.printToTracePanel(result);
	}
	
	
	/**
	 * The message parameter identifier to be used to add messages to the parameter list.
	 */
	private static final String MESSAGE_KEY = "message";
}
