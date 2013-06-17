package com.badlogicgames.libgdx.site;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Result {
	public boolean success;
	public String message;
	
	public Result() {

	}
	
	public Result(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
}
