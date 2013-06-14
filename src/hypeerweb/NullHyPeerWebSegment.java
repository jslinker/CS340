package hypeerweb;

import hypeerweb.database.HyPeerWebDatabase;
import hypeerweb.node.Node;

import java.util.ArrayList;
import java.util.List;

public class NullHyPeerWebSegment extends HyPeerWebSegment{

	public NullHyPeerWebSegment(){
	}
	
	public static HyPeerWebSegment getSingleton(){
		return new NullHyPeerWebSegment();
	}

	@Override
	public void addToHyPeerWeb(Node newNode, Node startNode) {
	}

	@Override
	public void removeFromHyPeerWeb(Node removeNode) {
	}

	@Override
	public void addNode(Node node) {
	}

	@Override
	public void clear() {
	}

	@Override
	public boolean contains(Node node) {
		return false;
	}

	@Override
	public HyPeerWebDatabase getHyPeerWebDatabase() {
		return super.getHyPeerWebDatabase();
	}

	@Override
	public Node getNode(int index) {
		return Node.NULL_NODE;
	}

	@Override
	public Node getNodeByWebId(int webId) {
		return Node.NULL_NODE;
	}

	@Override
	public void reload() {
	}

	@Override
	public List<Node> getNodes() {
		return new ArrayList<Node>();
	}

	@Override
	public void reload(String dbName) {
	}

	@Override
	public void removeNode(Node node) {
	}

	@Override
	public void saveToDatabase() {
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public int sizeOfHyPeerWeb() {
		return 0;
	}

	@Override
	public Node getForeignNode() {
		return Node.NULL_NODE;
	}

	@Override
	public void close() {
	}

	@Override
	public void fireNodeAdded(int webId) {
	}

	@Override
	public void fireNodeRemoved() {
	}

	@Override
	public void fireCleared() {
	}

	@Override
	public void fireShutdown() {
	}

	@Override
	public void kill() {
	}
	
	
}
