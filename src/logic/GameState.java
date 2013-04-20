package logic;

import map.Level;

public class GameState {
	private Level currentLevel;

	public GameState() {
		return;
	}

	public GameState(Level level) {
		this.currentLevel = level;
	}

	public Level getLevel() {
		return currentLevel;
	} 
}
