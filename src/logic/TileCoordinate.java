package logic;

public class TileCoordinate {

	private final int x, y;

	TileCoordinate(double pixel_x, double pixel_y){
		x = (int)pixel_x/32;
		y = (int)pixel_y/32;
	}

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}

}