package map;

import javax.vecmath.Point2d;

import logic.InventoryObject;
import logic.Player;
import logic.TileCoordinate;

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
				if ( (tileID = map.getTileId(x, y, 3)) != 0 )
					invObjGrid[y][x] = new InventoryObject(map.getTileProperty(tileID, "name", "FaultyObj"));

				// Player detection
				tileID = map.getTileId(x, y, 4);
				tileProp = map.getTileProperty(tileID, "name", "EMPTY");
				if (tileProp.equals("PLAYER"))
					p = new Player(new Point2d(x*32+15, y*32+15), this);
			}
		}
	}

	public boolean isBlocked( TileCoordinate c ) {
		return terrainGrid[c.y()][c.x()];
	}

	public boolean isLadder( TileCoordinate c ) {
		return ladderGrid[c.y()][c.x()];
	}

	public boolean isOnObject( TileCoordinate c ) {
		return invObjGrid[c.y()][c.x()] != null;
	}

	public InventoryObject getInvObj( TileCoordinate c ) {
		return invObjGrid[c.y()][c.x()];
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

	public void removeObject( TileCoordinate c ) {
		invObjGrid[c.y()][c.x()] = null;
		map.setTileId(c.x(), c.y(), 3, 0);
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
