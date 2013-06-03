package controllers;

public class BroadcastArgs {
	private int startId;
	private String message;
	
	public BroadcastArgs(int startId, String message) {
		super();
		this.startId = startId;
		this.message = message;
	}

	public int getStartId() {
		return startId;
	}

	public void setStartId(int startId) {
		this.startId = startId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
}
