package map;

public class Tile {
	private TileType tileType;

	public Tile(String objectName) {
		for (TileType t : TileType.values()) {
			if (objectName.equals(t)) {
				tileType = t;
			}
		}
	}

	public TileType getTileType() {
		return tileType;
	}

	public void setTileType(TileType tileType) {
		this.tileType = tileType;
	}
}
