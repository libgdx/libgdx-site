package com.badlogicgames.libgdx.site.db;

import java.util.List;

/**
 * If i ever use a real db...
 * @author badlogic
 *
 */
public interface GameDatabase {
	/**
	 * Creates a game to the database, returns a token
	 * for the user to be able to modify it later on.
	 * Sets a unique id on the Game instance passed in.
	 * Also sets the creation date on the game.
	 * @param game
	 * @return token
	 */
	public String create(Game game);
	
	/**
	 * Updates the game
	 * @param token the token of the game, returned by {@link #add(Game)}
	 * @param game the game
	 * @return whether the update succeeded
	 */
	public boolean update(String token, Game game);
	
	/**
	 * Deletes the game
	 * @param token the token of the game, returned by {@link #add(Game)}
	 * @param gameId
	 * @return whether the deletion succeeded
	 */
	public boolean delete(String token, String gameId);

	/**
	 * @return the list of games
	 */
	public List<Game> getGames();
}
