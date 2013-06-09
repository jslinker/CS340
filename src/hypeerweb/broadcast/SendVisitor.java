package hypeerweb.broadcast;

import hypeerweb.node.Node;
import utilities.BitManipulation;

/**
 * Used to send a message from a source node to a target node.
 * The actual message is the targetOperation to be performed on the target Node.
 * The "targetOperation" is abstract and is to be overridden in a visitor that 
 * does the actual work. There is also an intermediateOperation that may be 
 * performed on nodes visited on the way to the target node.
 * @Domain None
 * @author Jason Robertson, Craig Jacobson
 */
public abstract class SendVisitor implements Visitor{
	
	/** 
	 * The key used in a key-value pair of the parameters to identify the target webId.
	 */
	protected final static String TARGET_KEY = "TARGET_KEY";
	
	/**
	 * The default constructor
	 * @Precondition None
	 * @Postcondition True
	 */
	public SendVisitor(){
	}
	
	/**
	 * The SendVisitor visitor expects the parameters to contain a target.
	 * This method initializes the parameters with the key-pair (TARGET_KEY, target).
	 * If more parameters are required in a subclass this method is overridden.
	 * Normally this method is only called by the source node before sending the "message".
	 * @param target - The webId of the node we are to perform the target operation on.
	 * @precondition The node is in the HyPeerWeb and node.webId = target
	 * @postcondition |result| = 1 AND (TARGET_KEY, target) in result.
	 */
	public static Parameters createInitialParameters(int target){
		assert (target >= 0);
		
		Parameters parameters = new Parameters();
		parameters.set(TARGET_KEY, target);
		return parameters;
	}

	/** The intermediate operation to be performed on a node as we traverse to the target node. */
	protected void intermediateOperation(Node node, Parameters parameters){
	}
    
	/** The abstract operation to be performed on the targetNode. */
	protected abstract void targetOperation(Node node, Parameters parameters);

	/** If the webId of the node == the target in the parameters then the targetOperation
	 * method is performed on the node. Otherwise the itermediateOperation method is performed
	 * on the node (this method often does nothing), a node closer to the target node is found,
	 * and the accept method is executed on that node passing in this SendVisitor and the parameters.
	 * @param node The node being visited.
	 * @param parameters The parameters storing additional information.
	 * @Precondition node != null, parameters != null, the target node exists and the target is
	 * in the parameters list
	 * @Postcondition See description.
	 */
	@Override
	public void visit(Node node, Parameters parameters){
		assert (node != null && parameters != null);
		assert (parameters.containsKey(TARGET_KEY) && parameters.get(TARGET_KEY) instanceof Integer);
		
		int targetWebId = ((Integer)parameters.get(TARGET_KEY)).intValue();
		if(node.getWebIdValue() == targetWebId){
			this.targetOperation(node, parameters);
		}
		else{
			this.intermediateOperation(node, parameters);
			
			int nextNodeWebId = BitManipulation.calculateNextJumpWebId(node.getWebIdValue(), targetWebId);
			Node nextNode = node.getConnections().getNeighborByWebId(nextNodeWebId);
			nextNode.accept(this, parameters);
		}
	}
	
}