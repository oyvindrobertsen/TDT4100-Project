package logic;

import javax.vecmath.Point2d;

import map.Level;


public class Player {
	// CONSTs
	private static final int MAX_HEALTH = 300;
	
	// Fields
	private Point2d position;	   // A players current position (center of tile)
	private Point2d [] cornerList = new Point2d[4]; // A list containing the four cornerpoints of the user-tile
	private int health;
	private Level currentLevel;
	
	// Player constructor
	public Player(Point2d position, Level currentLevel) {
		this.currentLevel = currentLevel;
		this.position = position;
		updateCornerlist();
		health = 100;
	}
	
	private void updateCornerlist() {
		cornerList[0] = new Point2d(position.x-16, position.y-16); // Upper left
		cornerList[1] = new Point2d(position.x-16, position.y+16); // Lower left
		cornerList[2] = new Point2d(position.x+16, position.y-16); // Upper right
		cornerList[3] = new Point2d(position.x+16, position.y+16); // Lower right
	}
	
	// Getters
	public int getHealth() {
		return health;
	}
	
	public Point2d getPos() {
		return position;
	}
	
	public Point2d[] getCorners() {
		return cornerList;
	}
	// Setters
	public void setHealth(int health) {
		if (health > 0 && health <= MAX_HEALTH) {
			this.health = health;
		}
	}
	
	public void increaseHealth(int dHealth) {
		if (this.health + dHealth <= 300 && dHealth > 0) {
			this.health += dHealth;
		} else if (dHealth > 0) {
			this.health = 300;
		}
	}
	
	public void decreaseHealth(int dHealth) {
		if (this.health - dHealth >= 0 && dHealth < 0) {
			this.health -= dHealth;
		} else if (dHealth > 0) {
			this.health = 0;
		}
	}

	
	public void move(Direction dir) {
		for (int i = 0; i < 4; i++) {
			if ( moveIllegal(i, dir) ) return;
		}
		this.position.x += dir.dx();
		this.position.y += dir.dy();
		updateCornerlist();
	}

	private boolean moveIllegal(int i, Direction dir) {
		int nextX = (int)cornerList[i].x+dir.dx();
		int nextY = (int)cornerList[i].y+dir.dy();
		return ( ( currentLevel.isBlocked(nextY, nextX) ) || nextY < 0 || nextY > 600 || nextX < 0 || nextX > 798 );
	}

}
