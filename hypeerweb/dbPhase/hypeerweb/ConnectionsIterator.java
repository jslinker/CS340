package dbPhase.hypeerweb;

import java.util.List;
import java.util.ArrayList;

public class ConnectionsIterator extends MetaIterator<Connection> {	//Hopefully, I can make a better version of this later.

	public ConnectionsIterator(Connections connections) {
		List<Connection> foldAndSuch = new ArrayList<Connection>();
		foldAndSuch.add(connections.getFoldOrSurrogateFold());
		Connection inverseSurrogateFold = connections.getInverseSurrogateFold();
		if(inverseSurrogateFold != NullConnection.getNullConnection()) {
			foldAndSuch.add(inverseSurrogateFold);
		}
		List<Iterable<Connection>> metaIterable = new ArrayList<Iterable<Connection>>();
		metaIterable.add(connections.getNeighbors());
		metaIterable.add(connections.getUpPointers());
		metaIterable.add(connections.getDownPointers());
		metaIterable.add(foldAndSuch);
		metaIterator = metaIterable.iterator();
		do {
			current = metaIterator.next().iterator();
		} while(!current.hasNext());
	}
	
}
