package logic;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import map.Level;

public class GameState {
	private Level currentLevel;

	public GameState() {
	}

	public GameState(Level level) {
		this.currentLevel = level;
	}

	public Level getLevel() {
		return currentLevel;
	}

	public void loadNextLevel( Player p ) {
		try {
			currentLevel = new Level( new TiledMap("res/Firsttest2.tmx"), p );
			p.setCurrentLevel( currentLevel );
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unable to load requested level");
		}
	} 
}
