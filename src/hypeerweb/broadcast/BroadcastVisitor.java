package hypeerweb.broadcast;

import node.Node;

/**
 * Broadcasts a message from a source node to all nodes in the HyPeerWeb.
 * The message is actually the method operation(Node, Parameters) to be 
 * performed on all nodes. The method operation(Node, Parameters) must be 
 * overridden in an implementing subclass.
 * @domain None
 * @author Jason Robertson
 *
 */
public abstract class BroadcastVisitor implements Visitor{

	/** 
	 * The key used to identify a key-value pair in the parameters list.
	 * The actual value associated with the key can be any value including null.
	 */
	protected static final String STARTED_KEY = "STARTED_KEY";
	
	/**
	 * The default constructor.
	 * @precondition None
	 * @postcondition True
	 */
	public BroadcastVisitor(){
	}
	
	/**
	 * The visit operation called by a node in the accept method implementing the 
	 * broadcast visitor pattern.
	 * @param node The node being visited.
	 * @param parameters The parameters used during the broadcast.
	 * @pre
	 * @post parameters contains START_KEY AND operation.postCondition is met AND 
	 */
	@Override
	public void visit(Node node, Parameters parameters) {
		assert (node != null && parameters != null);
		
		
	}
	
	/** 
	 * Creates the minimum set of parameters needed when invoking
	 * an accept method during a broadcast.
	 * At the top level (this level) there are no required parameters.
	 * If there are more required parameters in a subclass, this method is overridden.
	 * @precondition None
	 * @postcondition |result| = 0
	 */
	public static Parameters createInitialParameters(){
		return (new Parameters());
	}
	
	/**
	 * The abstract operation to be performed on all nodes.
	 * This operation must be implemented in all concrete subclasses.
	 * @param node - The node the operation is to be performed on.
	 * @param parameters - The parameters needed to perform the operation.
	 * @precondition node != null && parameters != null
	 * @postcondition true
	 */
	protected abstract void operation(Node node, Parameters parameters);	
}