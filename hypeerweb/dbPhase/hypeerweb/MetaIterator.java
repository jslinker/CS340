package dbPhase.hypeerweb;

import java.util.Iterator;

public class MetaIterator<E> implements Iterator<E> {	//Note: At least one iterator must be nonEmpty.
	protected Iterator<Iterable<E>> metaIterator;
	protected Iterator<E> current;
	
	public MetaIterator() {
	}
	
	public MetaIterator(Iterable<Iterable<E>> metaIterable) {
		metaIterator = metaIterable.iterator();
		do {
			current = metaIterator.next().iterator();
		} while(!current.hasNext());
	}

	public boolean hasNext() {
		return metaIterator.hasNext() || current.hasNext();
	}

	public E next() {
		E out = current.next();
		while(!current.hasNext() && metaIterator.hasNext()) {
			current = metaIterator.next().iterator();
		}
		return out;
	}

	/**
	 * Not implemented.
	 */
	public void remove() {
	}

}
