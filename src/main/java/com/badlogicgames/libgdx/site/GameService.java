package com.badlogicgames.libgdx.site;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.badlogicgames.libgdx.site.db.DownloadUrl;
import com.badlogicgames.libgdx.site.db.FileGameDatabase;
import com.badlogicgames.libgdx.site.db.Game;
import com.badlogicgames.libgdx.site.db.GameDatabase;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.sun.jersey.spi.resource.Singleton;

/**
 * Simple service using sqllite to store and return
 * libgdx game entries, for libgdx's site.
 * @author badlogic
 */
@Path("/")
@Singleton
public class GameService {
	private final GameDatabase db;
	private final String recaptchaPrivateKey;
	
	public GameService() throws IOException {
		String dir = System.getenv().get("GDX_GAME_DB_DIR");
		if(dir == null) {
			dir = "/opt/gamedb";
		}
		recaptchaPrivateKey = FileUtils.readFileToString(new File(dir, "recaptcha.key")).trim();
		db = new FileGameDatabase(dir);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("games")
	public List<Game> games() {
		return db.getGames();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("game")
	public Game game(@QueryParam("id") String gameId) {
		return db.getGame(gameId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("randomGames")
	public List<Game> randomGames(@QueryParam("num") int num) {
		if(num == 0) num = 8;
		List<Game> games = db.getGames();
		Collections.shuffle(games);
		List<Game> randomGames = new ArrayList<Game>();
		for(int i = 0; i < num && i < games.size(); i++) {
			randomGames.add(games.get(i));
		}
		return randomGames;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("create")
	public Result create(@Context HttpServletRequest hsr, GameRequest request) {
		if(!checkCaptcha(hsr.getRemoteAddr(), request)) return new Result(false, "Captcha wrong");
		String token = db.create(request.game);
		return new Result(true, "created", request.game.id, token);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("update")
	public Result update(@Context HttpServletRequest hsr, GameRequest request) {
		Result result = validate(request.game);
		if(!result.success) return result;
		if(!checkCaptcha(hsr.getRemoteAddr(), request)) return new Result(false, "Captcha wrong");
		if(!db.update(request.token, request.game)) return new Result(false, "Token wrong");
		return new Result(true, "updated", request.game.id, request.token);
	}
	
	private boolean checkCaptcha(String remoteIp, GameRequest request) {
		String privateKey = recaptchaPrivateKey;
		String challenge = request.challenge;
		String response = request.response;
		String result = HttpUtils.postHttp("http://www.google.com/recaptcha/api/verify", 
							"privatekey", privateKey,
							"remoteip", remoteIp,
							"challenge", challenge,
							"response", response);
		if(result == null) return false;
		System.out.println(result);
		return result.startsWith("true");
	}
	
	private Result validate(Game game) {
		if(game.title == null || game.title.length() == 0) return new Result(false, "Enter title");
		game.title = sanitize(game.title, 140);
		if(game.creator == null || game.creator.length() == 0) return new Result(false, "Enter creator");
		game.creator = sanitize(game.creator, 140);
		if(game.description == null || game.description.length() == 0) return new Result(false, "Enter description");
		game.description = sanitize(game.description, 1000);
		if(game.downloadUrls == null || game.downloadUrls.size() == 0) return new Result(false, "Add at least one link");
		if(game.downloadUrls.size() > 4) return new Result(false, "4 links supported at most");
		UrlValidator urlValidator = new UrlValidator();
		for(int i = 0; i < game.downloadUrls.size(); i++) {
			DownloadUrl url = game.downloadUrls.get(i);
			if(url.name == null || url.name.length() == 0) return new Result(false, "Link #" + (i+1) + " name must not be empty");
			url.name = sanitize(url.name, 60);
			if(!urlValidator.isValid(url.url)) return new Result(false, "Link #" + (i + 1) + "URL is not valid");
		}
		if(game.imageUrls == null || game.imageUrls.size() == 0) return new Result(false, "Add at least one image");
		for(int i = 0; i < game.imageUrls.size(); i++) {
			String url = game.imageUrls.get(i);
			if(!urlValidator.isValid(url)) return new Result(false, "Image #" + (i+1) + " URL is not valid");
		}
		// FIXME no videos for now
		game.videoUrl = null;
		return new Result(true, "Validated");
	}
	
	private String sanitize(String text, int maxLength) {
		if(text.length() > maxLength) {
			text = text.substring(0, maxLength);
		}
		text = StringEscapeUtils.escapeHtml4(text);
		return text;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("delete")
	public Result delete(GameRequest request) {
		// FIXME
		return null;
//		return new Result(db.delete(request.token, request.game), "Deleted");
	}
	
	private static Server server;

	public static void start(int port) throws Exception {
		if (server != null) {
			throw new IllegalStateException("Server is already running");
		}

		ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/");
		Map<String, Object> initMap = new HashMap<String, Object>();
		initMap.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
		initMap.put("com.sun.jersey.config.property.packages",
				"com.badlogicgames.libgdx.site");
		context.addServlet(new ServletHolder(new ServletContainer(
				new PackagesResourceConfig(initMap))), "/libgdx-site/service/*");

		ContextHandler context_static = new ContextHandler(server, "/");
		context_static.setHandler(new ResourceHandler());
		context_static.setResourceBase("src/main/webapp");

		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[] { context, context_static });	
		
		server = new Server(port);
		server.setHandler(contexts);
		server.start();
	}
	
	public static void main(String[] args) throws Exception {
		start(7777);
	}
}
