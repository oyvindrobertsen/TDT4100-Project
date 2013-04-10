package logic;

import javax.vecmath.Point2d;


public class Player {
	// CONSTs
	private static final int MAX_HEALT = 300;
	
	// Fields
	private Point2d position;
	private int health;
	
	// Player constructor
	public Player(Point2d position) {
		this.position = position;
		health = 100;
	}
	
	// Getters
	public int getHealth() {
		return health;
	}
	
	public Point2d getPos() {
		return position;
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


}
