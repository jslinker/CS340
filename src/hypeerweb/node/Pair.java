package hypeerweb.node;

import java.io.Serializable;

/**
 * Simple class to Pair two values together.
 * @author Craig Jacobson
 *
 */
public class Pair<T> implements Serializable{
	private static final long serialVersionUID = 7898652019966508045L;
	
	private T lowerBound = null;
	private T upperBound = null;
	
	public Pair(T lowerBound, T upperBound){
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	public T getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(T lowerBound) {
		this.lowerBound = lowerBound;
	}

	public T getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(T upperBound) {
		this.upperBound = upperBound;
	}
}
