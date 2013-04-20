package logic;

import javax.vecmath.Point2d;

public class TileCoordinate {

	private final int x, y;

	TileCoordinate( int x, int y ){
		this.x = x;
		this.y = y;
	}

	TileCoordinate( Point2d pixel_pos ){
		x = (int)pixel_pos.x/32;
		y = (int)pixel_pos.y/32;
	}

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}

}