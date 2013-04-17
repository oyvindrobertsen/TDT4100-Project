package logic;

import javax.vecmath.Point2d;


public class Player {
	// CONSTs
	private static final int MAX_HEALT = 300;
	
	// Fields
	private Point2d position;	   // A players current position (center of tile)
	private Point2d [] cornerList; // A list containing the four cornerpoints of the user-tile
	private int health;
	
	// Player constructor
	public Player(Point2d position) {
		this.position = position;
		cornerList = new Point2d [4];
		cornerList[0] = new Point2d(position.x-16, position.y-16); // Upper left
		cornerList[1] = new Point2d(position.x-16, position.y+16); // Lower left
		cornerList[2] = new Point2d(position.x+16, position.y-16); // Upper right
		cornerList[3] = new Point2d(position.x+16, position.y+16); // Lower right
		health = 100;
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
		if (health > 0 && health <= MAX_HEALT) {
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
		
	}

}
