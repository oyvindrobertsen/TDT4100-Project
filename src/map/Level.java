package map;

import javax.vecmath.Point2d;

import logic.Player;

import org.newdawn.slick.tiled.TiledMap;

public class Level {
	private boolean[][] levelGrid;
	private int height, width;
	private Player p;
	
	/*
	 * For a .tmx-file to work with our game, it must adhere to the following conventions:
	 * 
	 * Layer 0: Background (if no background, layer 0 must be an empty layer)
	 * Layer 1: Terrain (Collision layer, any and all objects you want the player to collide with, should be in this layer)
	 * Layer 2: Player-controlled character (Tells the game where the player starts when the level loads)
	 * Layer 3: Interaction (all, in want for a better word, things the player can interact with)
	 * 
	 */
	
	public Level(TiledMap map) {
		height = map.getHeight();
		width = map.getWidth();
		levelGrid = new boolean[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// Wall detection
				int tileID = map.getTileId(x, y, 1);
				String tileProp = map.getTileProperty(tileID, "name", "EMPTY");
				if (tileProp.equals("TERRAIN")) {
					levelGrid[y][x] = true;
				} else {
					levelGrid[y][x] = false;
				}
				// Player detection
				tileID = map.getTileId(x, y, 2);
				tileProp = map.getTileProperty(tileID, "name", "EMPTY");
				if (tileProp.equals("PLAYER")) {
					p = new Player(new Point2d(x*32+16, y*32+16));
				}
			}
		}
	}

	public boolean isBlocked(int i, int j) {
		return levelGrid[i][j];
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public String toString() {
		String tempStr = "";
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (levelGrid[y][x] == true) {
					tempStr += "1 ";
				} else {
					tempStr += "0 ";
				}
			}
			tempStr += "\n";
		}
		return tempStr;
	}
}
