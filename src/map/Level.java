package map;

import javax.vecmath.Point2d;

import logic.InventoryObject;
import logic.Player;

import org.newdawn.slick.tiled.TiledMap;

public class Level {
	private boolean[][] terrainGrid, ladderGrid;
	private InventoryObject[][] invObjGrid;
	private int height, width;
	private Player p;
	private TiledMap map;
	
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
		this.map = map;
		height = map.getHeight();
		width = map.getWidth();
		terrainGrid = new boolean[height][width];
		ladderGrid = new boolean[height][width];
		invObjGrid = new InventoryObject[height][width];
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
				
				// Inventory object detection
				if ((tileID = map.getTileId(x, y, 3)) != 0) {
					invObjGrid[y][x] = new InventoryObject(map.getTileProperty(tileID, "name", "FaultyObj"));
				}
				
				// Player detection
				tileID = map.getTileId(x, y, 4);
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
	
	public boolean isOnObject(int i, int j) {
		return invObjGrid[(int)i/32][(int)j/32] != null;
	}
	
	public InventoryObject getInvObj(int i, int j) {
		return invObjGrid[(int)i/32][(int)j/32];
	}
	
	public void printObjGrid() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(invObjGrid[i][j]);
			}
			System.out.println();
		}
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public void removeObject(int i, int j) {
		invObjGrid[(int)i/32][(int)j/32] = null;
		map.setTileId((int)j/32, (int)i/32, 3, 0);
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
