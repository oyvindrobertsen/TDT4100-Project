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
	private Inventory inv;

	private double velocityX, velocityY;
	
	// Player constructor
	public Player(Point2d position, Level currentLevel) {
		this.currentLevel = currentLevel;
		this.position = position;
		updateCornerlist();
		health = 100;
		velocityX = 0;
		velocityY = 0;
		inv = new Inventory();
	}
	
	private void updateCornerlist() {
		cornerList[0] = new Point2d(position.x-15, position.y-15); // Upper left
		cornerList[1] = new Point2d(position.x-15, position.y+15); // Lower left
		cornerList[2] = new Point2d(position.x+15, position.y-15); // Upper right
		cornerList[3] = new Point2d(position.x+15, position.y+15); // Lower right
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
		if ( health > 0 && health <= MAX_HEALTH )
			this.health = health;
	}
	
	public void increaseHealth(int dHealth) {
		if (this.health + dHealth <= 300 && dHealth > 0)
			this.health += dHealth;
		else if (dHealth > 0)
			this.health = 300;
	}
	
	public void decreaseHealth(int dHealth) {
		if (this.health - dHealth >= 0 && dHealth < 0)
			this.health -= dHealth;
		else if (dHealth > 0)
			this.health = 0;
	}

	// Movement

	public boolean moveIllegal( Direction dir ) {
		for (int i = 0; i < 4; i++) {
			int nextX = (int)cornerList[i].x + dir.dx();
			int nextY = (int)cornerList[i].y + dir.dy();
			try {
				if ( ( currentLevel.isBlocked(nextY, nextX) ) || nextX < 0 ) return true;
			} catch (Exception e) {
				return true;
			}
		}
		return false;
	}
	
	public void move(Direction dir) {
		if ( moveIllegal(dir) ) return;
		this.position.x += dir.dx();
		this.position.y += dir.dy();
		updateCornerlist();
	}
	
	public void jump(){
		if( !moveIllegal( Direction.DOWN ) || moveIllegal( Direction.UP ) ) return;
		accelerate(Direction.UP, 12);
	}

	public void accelerate( Direction dir, double magnitude ) {
		velocityX += magnitude*dir.dx();
		velocityY += magnitude*dir.dy();
	}

	public double getVelocityX() {
		return velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void applyForces() {
		if ( onLadder() || ( moveIllegal(Direction.DOWN) && velocityY > 0 ) )
			velocityY = 0;
		else
			accelerate(Direction.DOWN, 1);

		if ( moveIllegal(Direction.DOWN) || moveIllegal(Direction.UP ) )
			velocityX = velocityX/1.25;
	}

	public boolean onLadder() {
		return currentLevel.isLadder( (int)position.y+15, (int)position.x );
	}
	
	// Interaction
	
	public boolean onObject() {
		return currentLevel.isOnObject((int)position.y, (int)position.x);
	}
	
	public void pickupObject() {
		inv.addItem(currentLevel.getInvObj((int)position.y, (int)position.x));
		currentLevel.removeObject((int)position.y, (int)position.x);
	}
	
	public Inventory getInventory() {
		return inv;
	}

}
