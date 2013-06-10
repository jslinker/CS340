package hypeerweb;

public interface HyPeerWebObserver{
	public void notifyNodeAdded(int nodeWebId);
	public void notifyNodeRemoved(int nodeWebId);
	public void notifyCleared();
	public void notifyHyPeerWebSegmentShutdown();
}
