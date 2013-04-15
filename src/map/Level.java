package map;

import java.util.ArrayList;

import org.newdawn.slick.tiled.TiledMap;

public class Level {
	private ArrayList<ArrayList<Tile>> levelGrid = new ArrayList<ArrayList<Tile>>();
	
	public Level(TiledMap level) {
		for (int i = 0; i < level.getHeight(); i++) {
			levelGrid.add(new ArrayList<Tile>());
			for (int j = 0; j < level.getWidth(); j++) {
				levelGrid.get(i).add(new Tile(level.getObjectName(i, j)));
			}
		}
	}

	public Tile getTile(int i, int j) {
		return levelGrid.get(i).get(j);
	}
	
	public String toString() {
		String tempStr = null;
		for (int i = 0; i < levelGrid.size(); i++) {
			for (int j = 0; j < levelGrid.get(i).size(); j++) {
				tempStr += levelGrid.get(i).get(j);
			}
			tempStr += "\n";
		}
		return tempStr;
	}
}
