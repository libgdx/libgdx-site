package com.badlogicgames.libgdx.site;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

import com.badlogicgames.libgdx.site.db.Game;
import com.badlogicgames.libgdx.site.db.GameDatabase;
import com.badlogicgames.libgdx.site.db.InMemoryGameDatabase;
import com.sun.jersey.spi.resource.Singleton;

/**
 * Simple service using sqllite to store and return
 * libgdx game entries, for libgdx's site.
 * @author badlogic
 */
@Path("games/")
@Singleton
public class GameService {
	private GameDatabase db;
	
	public GameService() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Game[] games = mapper.readValue(GameService.class.getResourceAsStream("/games.json"), Game[].class);
			db = new InMemoryGameDatabase(games);
		} catch (IOException e) {
			e.printStackTrace();
			db = new InMemoryGameDatabase(new Game[0]);
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("hello")
	public String hello() {
		return "{ \"hello\": \"world\" }";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("games")
	public List<Game> games() {
		return db.getGames();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("create")
	public Result create(Game game) {
		String token = db.create(game);
		return new Result(true, token);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("update")
	public Result update(String token, Game game) {
		return new Result(db.update(token, game), "Updated");
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("delete")
	public Result update(String token, String gameId) {
		return new Result(db.delete(token, gameId), "Deleted");
	}
}
