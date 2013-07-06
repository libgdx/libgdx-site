package com.badlogicgames.libgdx.site.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This should really use lock-less collections...
 * @author badlogic
 *
 */
public class InMemoryGameDatabase implements GameDatabase {
	Map<String, String> tokensToIds = new HashMap<String, String>();
	Map<String, Game> idsToGames = new HashMap<String, Game>();
	
	public InMemoryGameDatabase() {
	}
	
	public InMemoryGameDatabase(Game[] games) {
		for(Game game: games) {
			idsToGames.put(game.id, game);
		}
	}
	
	public synchronized String create(Game game) {
		if(game == null) return null;
		game.id = UUID.randomUUID().toString();
		game.created = new Date();
		idsToGames.put(game.id, game);
		String token = UUID.randomUUID().toString();
		tokensToIds.put(token, game.id);
		return token;
	}

	public synchronized boolean update(String token, Game game) {
		if(token == null) return false;
		if(game == null) return false;
		String id = tokensToIds.get(token);
		if(id == null) return false;
		game.id = id;
		game.modified = new Date();
		idsToGames.put(id, game);
		return true;
	}

	public synchronized boolean delete(String token, String gameId) {
		if(token == null) return false;
		if(gameId == null) return false;
		String id = tokensToIds.get(token);
		if(id == null) return false;
		if(!id.equals(gameId)) return false;
		idsToGames.remove(id);
		tokensToIds.remove(token);
		return true;
	}

	public synchronized List<Game> getGames() {
		ArrayList<Game> games = new ArrayList<Game>(idsToGames.values());
		Collections.sort(games, new Comparator<Game>() {
			public int compare(Game o1, Game o2) {
				return o2.created.compareTo(o1.created);
			}
		});
		return games;
	}

	
	public synchronized List<Game> getGames(int page) {
		// get all Games
		ArrayList<Game> allgames = (ArrayList<Game>) getGames();
		
		ArrayList<Game> games=new ArrayList<Game>(16);
		for(int i=page*16;i<page*16+16;i++){
			if(i>=allgames.size()){
				// we do not have enough games to show you
				break;
			}
			System.out.println(i);
			games.add(allgames.get(i));
		}
		return games;
	}
	
	public synchronized void addGame(GameRecord gameRecord) {
		idsToGames.put(gameRecord.game.id, gameRecord.game);
		tokensToIds.put(gameRecord.token, gameRecord.game.id);
	}

	public synchronized Game getGame(String gameId) {
		return idsToGames.get(gameId);
	}

	public int countGames() {
		return idsToGames.size();
	}
}
