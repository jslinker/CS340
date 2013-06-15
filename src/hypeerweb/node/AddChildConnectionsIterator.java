package hypeerweb.node;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class AddChildConnectionsIterator<NodeInterface> implements Iterator<NodeInterface>{
	
	private int index = 0;
	private ArrayList<Collection<NodeInterface>> setsOfNodeInterface = new ArrayList<Collection<NodeInterface>>();
	
	public AddChildConnectionsIterator(Connections connections){
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public NodeInterface next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
