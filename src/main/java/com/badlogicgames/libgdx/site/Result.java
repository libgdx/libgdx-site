package com.badlogicgames.libgdx.site;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Result {
	public boolean success;
	public String message;
	public String gameId;
	public String token;
	
	public Result() {

	}
	
	public Result(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	public Result(boolean success, String message, String gameId, String token) {
		this.success = success;
		this.message = message;
		this.gameId = gameId;
		this.token = token;
	}
}
