package com.examples.model;

public class News {
	long id;
	String message;
	String timestamp;

	public News() {

	}

	public News(String msg, String time) {
		this.message = msg;
		this.timestamp = time;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
