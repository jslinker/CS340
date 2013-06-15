package hypeerweb.node;


import java.util.Iterator;

/**
 * The Template Method pattern used in modifying connections.
 * @author Craig Jacobson
 *
 */
public abstract class ModifyConnectionsTemplateMethod{
	
	@SuppressWarnings("unused")
	private Connections connections = null;

	/**
	 * Constructs a template method for use in modifying connections.
	 * @param connections The node's connections.
	 * @pre connections != null
	 * @post True.
	 */
	public ModifyConnectionsTemplateMethod(Connections connections){
		this.connections = connections;
	}
	
	/**
	 * Modifies every connection returned by the iterator.
	 */
	public void execute(){
		Iterator<NodeInterface> iterator = getNodeInterfaceIterator();
		while(iterator.hasNext()){
			modifyConnection(iterator.next());
		}
	}
	
	/**
	 * Gets an iterator for all of the relevant connections.
	 * @return An iterator of NodeInterfaces.
	 */
	public abstract Iterator<NodeInterface> getNodeInterfaceIterator();
	
	/**
	 * Modifies a connection.
	 * @param nodeI The connection to be modified.
	 */
	public abstract void modifyConnection(NodeInterface nodeI);
}
