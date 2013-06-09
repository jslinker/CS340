package gui.controllers;

public class SendArgs {
	
	private int startId;
	private int targetId;
	private String message;
	
	public SendArgs(int startId, int targetId, String message){
		this.startId = startId;
		this.targetId = targetId;
		this.message = message;
	}

	public int getStartId() {
		return startId;
	}

	public void setStartId(int startId) {
		this.startId = startId;
	}

	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
