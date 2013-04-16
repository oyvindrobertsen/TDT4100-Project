package map;

import org.newdawn.slick.tiled.TiledMap;

public class Level {
	private boolean[][] levelGrid;
	private int height, width;
	
	public Level(TiledMap map) {
		height = map.getHeight();
		width = map.getWidth();
		levelGrid = new boolean[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int tileID = map.getTileId(x, y, 1);
				String tileProp = map.getTileProperty(tileID, "name", "EMPTY");
				if (tileProp.equals("TERRAIN")) {
					levelGrid[y][x] = true;
				} else {
					levelGrid[y][x] = false;
				}
			}
		}
	}

	public boolean isBlocked(int i, int j) {
		return levelGrid[i][j];
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
