package map;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import javax.vecmath.Point2d;

import logic.InventoryObject;
import logic.Player;
import logic.TileCoordinate;

import org.newdawn.slick.tiled.TiledMap;

import java.util.Scanner;

public class Level {
	private boolean[][] terrainGrid, ladderGrid;
	private InventoryObject[][] invObjGrid;
	private int height, width, mapNr;
	private Player player;
	private TiledMap map;
	private TileCoordinate goalTile;
	private Point2d startPos;
	private HashMap<InventoryObject, Integer> requirements;

	/*
	 * For a .tmx-file to work with our game, it must adhere to the following conventions:
	 * 
	 * Layer 0: Background (if no background, layer 0 must be an empty layer)
	 * Layer 1: Terrain (Collision layer, any and all objects you want the player to collide with, should be in this layer)
	 * Layer 2: Ladders
	 * Layer 3: Inventory objects
	 * Layer 4: Player-controlled character and goalpost (Tells the game where the player starts when the level loads)
	 * 
	 */

	public Level(TiledMap map) {
		this.map = map;
		createGrid();
		player = new Player(startPos, this);
		readRequirements();
	}

	public Level( TiledMap map, Player player ) {
		this.map = map;
		this.player = player;
		createGrid();
		player.setPos( startPos );
		readRequirements();
	}

	private void createGrid() {
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
				if ( ( tileID = map.getTileId(x, y, 3) ) != 0 )
					invObjGrid[y][x] = new InventoryObject( map.getTileProperty(tileID, "name", "FaultyObj") );

				// Player/goal detection
				tileID = map.getTileId(x, y, 4);
				tileProp = map.getTileProperty(tileID, "name", "EMPTY");
				if (tileProp.equals("PLAYER"))
					startPos = new Point2d(x*32+15, y*32+15);
				if (tileProp.equals("GOAL"))
					goalTile = new TileCoordinate(x, y);
			}
		}
	}

	private void readRequirements() {
		mapNr = Integer.parseInt(map.getMapProperty("mapnumber", "-1"));
		if (mapNr != -1) {
			requirements = new HashMap<InventoryObject, Integer>();
			try {
				Scanner scanner = new Scanner( new FileReader("./res/requirements"+mapNr) );
				try {
					while (scanner.hasNextLine()) {
						String[] parts = scanner.nextLine().split("\\:");
						requirements.put(new InventoryObject(parts[0]),Integer.parseInt(parts[1]));
					}
				} finally {
					scanner.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("Error: missing requirements file for this level!");
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
		return player;
	}

	public HashMap<InventoryObject, Integer> getRequirements() {
		return requirements;
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

	public TileCoordinate getGoal() {
		return goalTile;
	}

	public TiledMap getMap() {
		return map;
	}

	private Point2d findPlayer() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int tileID = map.getTileId(x, y, 4);
				String tileProp = map.getTileProperty(tileID, "name", "EMPTY");
				if (tileProp.equals("PLAYER")) return new Point2d(x*32+15, y*32+15);
			}
		}
		return new Point2d(0,0);
	}

	public void reloadLevel() {
		player.setPos(findPlayer());
		player.stop();
		player.clearInventory();
		player.setHealth(100);
	}
}