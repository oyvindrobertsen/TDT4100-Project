package map;

import javax.vecmath.Point2d;

import logic.Player;

import org.newdawn.slick.tiled.TiledMap;

public class Level {
	private boolean[][] terrainGrid, ladderGrid;
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
		terrainGrid = new boolean[height][width];
		ladderGrid = new boolean[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// Wall detection
				int tileID = map.getTileId(x, y, 1);
				String tileProp = map.getTileProperty(tileID, "name", "EMPTY");
				terrainGrid[y][x] = tileProp.equals("TERRAIN");
				
				// Ladder detection
				tileID = map.getTileId(x, y, 2);
				tileProp = map.getTileProperty(tileID, "name", "EMPTY");
				ladderGrid[y][x] = tileProp.equals("LADDER");
				
				// Player detection
				tileID = map.getTileId(x, y, 3);
				tileProp = map.getTileProperty(tileID, "name", "EMPTY");
				if (tileProp.equals("PLAYER"))
					p = new Player(new Point2d(x*32+15, y*32+15), this);
			}
		}
	}

	public boolean isBlocked(int i, int j) {
		return terrainGrid[(int)i/32][(int)j/32];
	}

	public boolean isLadder(int i, int j) {
		return ladderGrid[(int)i/32][(int)j/32];
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public String toString() {
		String tempStr = "";
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tempStr += (terrainGrid[y][x]) ? "1 " : "0 ";
			}
			tempStr += "\n";
		}
		return tempStr;
	}
}
