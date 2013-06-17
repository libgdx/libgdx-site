package com.badlogicgames.libgdx.site;

import javax.xml.bind.annotation.XmlRootElement;

import com.badlogicgames.libgdx.site.db.Game;

@XmlRootElement
public class GameRequest {
	public String challenge;
	public String response;
	public String token;
	public Game game;
}
