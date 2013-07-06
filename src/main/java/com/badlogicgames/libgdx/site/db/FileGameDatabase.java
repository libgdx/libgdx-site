package com.badlogicgames.libgdx.site.db;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;

public class FileGameDatabase implements GameDatabase {
	private static final Logger log = Logger.getLogger(FileGameDatabase.class.getSimpleName());
	private final ObjectMapper mapper = new ObjectMapper();
	private final InMemoryGameDatabase db;
	private final File dir;
	
	/**
	 * Creates a new database in the given directory. Each file
	 * is a single JSON encoding of a {@link Game} instance.
	 * @param directory
	 */
	public FileGameDatabase(String directory) {
		dir = new File(directory);
		if(!dir.exists()) {
			if(!dir.mkdirs()) throw new RuntimeException("Couldn't create db dir " + dir.getAbsolutePath());
		}
		log.info("Reading games from " + dir.getAbsolutePath());
		db = new InMemoryGameDatabase();
		for(File file: dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".json");
			}
		})) {
			String gameJson;
			try {
				gameJson = FileUtils.readFileToString(file);
				GameRecord game = mapper.readValue(gameJson, GameRecord.class);
				log.info("load game "+game.game.title);
				db.addGame(game);
			} catch (IOException e) {
				log.log(Level.SEVERE, "Couldn't load game record " + file.getAbsolutePath(), e);
			}
		}
		log.info("done loading " + db.getGames().size() + " games");
	}
	
	private String getFileName(String gameId) {
		return gameId + ".json";
	}
	
	private boolean writeEntry(String token, Game game) {
		GameRecord record = new GameRecord();
		record.token = token;
		record.game = game;
		try {
			String json = mapper.writeValueAsString(record);
			FileUtils.writeStringToFile(new File(dir, getFileName(game.id)), json);
			return true;
		} catch (Exception e) {
			log.log(Level.SEVERE, "Couldn't write game record", e);
			return false;
		}
	}
	
	public String create(Game game) {
		String token = db.create(game);
		if(writeEntry(token, game)) return token;
		else return null;
	}

	public boolean update(String token, Game game) {
		if(db.update(token, game)) {
			return writeEntry(token, game);
		} else {
			return false;
		}
	}

	public boolean delete(String token, String gameId) {
		if(db.delete(token, gameId)) {
			return new File(dir, getFileName(gameId)).delete();
		} else {
			return false;
		}
	}

	public List<Game> getGames() {
		return db.getGames();
	}
	
	public List<Game> getGames(int page) {
		return db.getGames(page);
	}
	
	public Game getGame(String gameId) {
		return db.getGame(gameId);
	}

	public int countGames() {
		return db.countGames();
	}

}
