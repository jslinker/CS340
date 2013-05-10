package hypeerweb.broadcast;

import hypeerweb.Node;

/**
 * Used to send a message from a source node to a target node.
 * The actual message is the targetOperation to be performed on the target Node.
 * The "targetOperation" is abstract and is to be overridden in a visitor that 
 * does the actual work. There is also an intermediateOperation that may be 
 * performed on nodes visited on the way to the target node.
 * @Domain None
 * @author Jason Robertson
 */
public abstract class SendVisitor implements Visitor {
	
	/** 
	 * The key used in a key-value pair of the parameters to identify the target webId.
	 */
	protected String TARGET_KEY;
	
	/**
	 * The default constructor
	 * @Precondition None
	 * @Postcondition True
	 */
	public SendVisitor() {
		
	}
	
	/**
	 * The SendVisitor visitor expects the parameters to contain a target.
	 * This method initializes the parameters with the key-pair (TARGET_KEY, target).
	 * If more parameters are required in a subclass this method is overridden.
	 * Normally this method is only called by the source node before sending the "message".
	 * @param target - The webId of the node we are to perform the target operation on.
	 */
	public static Parameters createInitialParameters(int target) {
		return null;
	}

	/** The intermediate operation to be performed on a node as we traverse to the target node. */
	protected void intermediateOperation(Node node, Parameters parameters) {
		
	}
    
	/** The abstract operation to be performed on the targetNode. */
	protected abstract void targetOperation(Node node, Parameters parameters);

	/** If the webId of the node == the target in the parameters then the targetOperation
	 * method is performed on the node. Otherwise the itermediateOperation method is performed
	 * on the node (this method often does nothing), a node closer to the target node is found,
	 * and the accept method is executed on that node passing in this SendVisitor and the parameters.
	 */
	public void visit(Node node, Parameters parameters) {
		
	}
	
}