package gui;

import identification.GlobalObjectId;

import java.io.Serializable;

import communicator.Command;
import communicator.PeerCommunicator;

public class GUIProxy extends GUIFacade implements Serializable{
    private GlobalObjectId globalObjectId;

    public GUIProxy(GlobalObjectId globalObjectId){
        this.globalObjectId = globalObjectId;
    }

    public void update(java.util.Observable p0, java.lang.Object p1){
        String[] parameterTypeNames = new String[2];
        parameterTypeNames[0] = "java.util.Observable";
        parameterTypeNames[1] = "java.lang.Object";
        Object[] actualParameters = new Object[2];
        actualParameters[0] = p0;
        actualParameters[1] = p1;
        Command command = new Command(globalObjectId.getLocalObjectId(), "gui.GUIFacade", "update", parameterTypeNames, actualParameters, false);
        PeerCommunicator.getSingleton().sendASynchronous(globalObjectId, command);
    }

    public boolean equals(java.lang.Object p0){
        String[] parameterTypeNames = new String[1];
        parameterTypeNames[0] = "java.lang.Object";
        Object[] actualParameters = new Object[1];
        actualParameters[0] = p0;
        Command command = new Command(globalObjectId.getLocalObjectId(), "java.lang.Object", "equals", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (Boolean)result;
    }

    public java.lang.String toString(){
        String[] parameterTypeNames = new String[0];
        Object[] actualParameters = new Object[0];
        Command command = new Command(globalObjectId.getLocalObjectId(), "java.lang.Object", "toString", parameterTypeNames, actualParameters, true);
        Object result = PeerCommunicator.getSingleton().sendSynchronous(globalObjectId, command);
        return (java.lang.String)result;
    }

    public int hashCode(){
        return globalObjectId.hashCode();
    }

}
