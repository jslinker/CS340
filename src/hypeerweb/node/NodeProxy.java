package hypeerweb.node;

import identification.GlobalObjectId;
import identification.ObjectDB;

import java.io.ObjectStreamException;
import java.io.Serializable;

import communicator.Command;
import communicator.PeerCommunicator;

public class NodeProxy extends Node implements Serializable{
	private static final long serialVersionUID = 5621400300321251146L;
	
	private GlobalObjectId globalObjectId = null;

    public NodeProxy(GlobalObjectId globalObjectId){
        this.globalObjectId = globalObjectId;
    }

    public boolean equals(java.lang.Object p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "java.lang.Object";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "equals", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Boolean)result;
    }

    public java.lang.String toString(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "toString", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.lang.String)result;
    }

    public int hashCode(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "hashCode", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }

    @Override
    public int compareTo(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "java.lang.Object";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "compareTo", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }

    public hypeerweb.node.NodeState getState(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "getState", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.NodeState)result;
    }

    public void accept(hypeerweb.broadcast.Visitor p0, hypeerweb.broadcast.Parameters p1){
        String[] parameterTypeNames = new String[2];
        parameterTypeNames[0] = "hypeerweb.broadcast.Visitor";
        parameterTypeNames[1] = "hypeerweb.broadcast.Parameters";
        Object[] actualParameters = new Object[2];
        actualParameters[0] = p0;
        actualParameters[1] = p1;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "accept", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public hypeerweb.node.SimplifiedNodeDomain constructSimplifiedNodeDomain(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "constructSimplifiedNodeDomain", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.SimplifiedNodeDomain)result;
    }

    public void setFold(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "setFold", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public int getHeight(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "getHeight", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Integer)result;
    }

    public java.util.TreeMap getNeighbors(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "getNeighbors", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.util.TreeMap)result;
    }

    public void setWebId(WebId webId){
        super.setWebId(webId);
    }

    public void setWebId(int p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "int";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "setWebId", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addChild(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "addChild", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void disconnect(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "disconnect", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void replaceNode(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "replaceNode", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void setHeight(int p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "int";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "setHeight", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public hypeerweb.node.Node findLargest(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "findLargest", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }

    public hypeerweb.node.Pair squeeze(hypeerweb.node.Pair p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Pair";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "squeeze", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Pair)result;
    }

    public hypeerweb.node.Node getNode(){
        return this;
    }

    public hypeerweb.node.Node findNode(int p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "int";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "findNode", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }

    public void addNeighbor(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "addNeighbor", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addUpPointer(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "addUpPointer", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void changeFold(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "changeFold", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public hypeerweb.broadcast.Contents getContents(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "getContents", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.broadcast.Contents)result;
    }

    public hypeerweb.node.Node deepCopy(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "deepCopy", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }

    public void setSurrogateFold(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "setSurrogateFold", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void setInverseSurrogateFold(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "setInverseSurrogateFold", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public java.util.Map getUpPointers(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "getUpPointers", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.util.Map)result;
    }

    public java.util.Map getDownPointers(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "getDownPointers", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.util.Map)result;
    }

    public void addToHyPeerWeb(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "addToHyPeerWeb", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public hypeerweb.node.Node findInsertionPoint(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "findInsertionPoint", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }

    public void removeFromHyPeerWeb(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "removeFromHyPeerWeb", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public hypeerweb.node.Node findDeletionPoint(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "findDeletionPoint", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Node)result;
    }

    public hypeerweb.node.Pair findLowerBoundUpperBoundPair(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "findLowerBoundUpperBoundPair", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (hypeerweb.node.Pair)result;
    }

    public java.util.Map getUpperNeighbors(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "getUpperNeighbors", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.util.Map)result;
    }

    public void removeConnection(hypeerweb.node.Node p0, hypeerweb.node.Node p1){
        String[] parameterTypeNames = new String[2];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        parameterTypeNames[1] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[2];
        actualParameters[0] = p0;
        actualParameters[1] = p1;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "removeConnection", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeConnection(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "removeConnection", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeDownPointer(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "removeDownPointer", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addDownPointer(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "addDownPointer", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeNeighbor(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "removeNeighbor", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void removeUpPointer(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "removeUpPointer", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void addConnection(hypeerweb.node.Node p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "addConnection", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public void replaceConnection(hypeerweb.node.Node p0, hypeerweb.node.Node p1){
        String[] parameterTypeNames = new String[2];
        parameterTypeNames[0] = "hypeerweb.node.Node";
        parameterTypeNames[1] = "hypeerweb.node.Node";
        Object[] actualParameters = new Object[2];
        actualParameters[0] = p0;
        actualParameters[1] = p1;
        Command command = new Command(globalObjectId.getLocalObjectId(), "hypeerweb.node.Node", "replaceConnection", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }
    
    public Object readResolve() throws ObjectStreamException{
    	Object result = this;
    	
    	if(this.getWebIdValue() == -1){
    		result = Node.NULL_NODE;
    	}
    	else{
    		ObjectDB db = ObjectDB.getSingleton();
        	Object possibleLocalNode = db.getValue(globalObjectId.getLocalObjectId());
        	if(possibleLocalNode instanceof Node){
        		Node localNode = (Node)possibleLocalNode;
        		if(localNode != null && localNode.getWebIdValue() == this.getWebIdValue()){
        			result = localNode;
        		}
        	}
    	}
    	
		return result;
	}
    
    /**
     * If this method is not overridden then the node will be replaced in the ObjectDB.
     * Additionally, the writeReplace() method in Node will be called recursively during 
     * serialization.
     */
    @Override
    public Object writeReplace() throws ObjectStreamException{
    	return this;
    }
}
