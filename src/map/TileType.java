package map;

public enum TileType {
	KEY("KEY"),
	SWORD("SWORD"),
	HPBOTTLE("HPBOTTLE");

	private final String name;

	TileType(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
