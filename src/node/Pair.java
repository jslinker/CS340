package node;

/**
 * Simple class to Pair two values together.
 * @author Craig Jacobson
 *
 */
public class Pair<T>{
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
