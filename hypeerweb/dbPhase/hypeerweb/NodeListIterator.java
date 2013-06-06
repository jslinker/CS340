package dbPhase.hypeerweb;

import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This allows you to automatically skip NULL_NODES while iterating across a
 * NodeList.
 * 
 * @author Daniel Carrier
 *
 */
public class NodeListIterator implements Iterator<Connection> {
	private Iterator<Connection> iterator;
	
	public NodeListIterator(List<Connection> list) {
		iterator = list.iterator();
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	/**
	 * Returns the next element that isn't NULL_NODE.
	 * 
	 * @return The next element that isn't NULL_NODE
	 * @throws NoSuchElementException - if the iteration has no more elements
	 */
	public Connection next() throws NoSuchElementException {
		Connection out;
		do {
			out = iterator.next();
		} while(out == NullConnection.getNullConnection());
		return out;
	}

	/**
	 * Not implemented.
	 */
	public void remove() {
	}
}
