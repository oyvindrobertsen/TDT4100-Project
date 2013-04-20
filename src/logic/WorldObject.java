package logic;

public abstract class WorldObject {

	private String name;

	public WorldObject(String objectName) {
		this.name = objectName;
	}

	public String toString() {
		return name;
	}
}
