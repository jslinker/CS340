package dbPhase.hypeerweb;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class acts as a wrapper class to a List&lt;Node&gt; to make it so you
 * can write in arbitrary positions without adding, reading past the end just
 * gives a NULL_NODE, you can find out how many entries aren't null, and if you
 * iterate across it, it automatically skips the NULL_NODEs.
 * 
 * @author Daniel Carrier
 * 
 */
public class NodeList extends AbstractList<Connection> {
	private ArrayList<Connection> list;
	private int nonNull;

	/**
	 * Constructs an empty NodeList
	 */
	public NodeList() {
		list = new ArrayList<Connection>();
		nonNull = 0;
	}

	@Override
	public Connection get(int arg0) {
		if (list.size() > arg0) {
			return list.get(arg0);
		} else {
			return NullConnection.getNullConnection();
		}
	}

	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Returns the number of elements that aren't NULL_NODE. This is useful for
	 * finding the height of a node by checking the number of neighbors.
	 * 
	 * @return The number of elements that aren't NULL_NODE
	 */
	public int numberOfNonNullElements() {
		return nonNull;
	}

	@Override
	public Connection set(int index, Connection element) {
		while (list.size() <= index) {
			list.add(NullConnection.getNullConnection());
		}
		Connection out = list.set(index, element);
		if (out == NullConnection.getNullConnection()) {
			++nonNull;
		}
		if (element == NullConnection.getNullConnection()) {
			--nonNull;
		}
		while (list.size() != 0
				&& list.get(list.size() - 1) == NullConnection
						.getNullConnection()) {
			list.remove(list.size() - 1);
		}
		return out;
	}

	@Override
	public Iterator<Connection> iterator() {
		return new NodeListIterator(list);
	}

	@Override
	public String toString() {
		return list.toString();
	}
}
