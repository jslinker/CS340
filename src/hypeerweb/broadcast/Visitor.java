package hypeerweb.broadcast;

import hypeerweb.node.Node;

import java.io.Serializable;

public interface Visitor extends Serializable{
	
	/**
	 * The visit method for the visitor pattern.
	 * @param node - the node visited
	 * @param parameters - the parameters passed to the node then to the visitor.
	 * @pre - node != null AND parameters != null
	 * @post - True
	 */
	public void visit(Node node, Parameters parameters);
	
}