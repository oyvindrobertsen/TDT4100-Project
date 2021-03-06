package logic;

import javax.vecmath.Point2d;

import map.Level;


public class Player {
	// CONSTs
	static final int MAX_HEALTH = 300;

	// Fields
	private Point2d position;	   // A players current pixel position (center of tile)
	private TileCoordinate tilePosition;	// the position of the player (center) in the tile grid
	private Point2d [] cornerList = new Point2d[4]; // A list containing the four cornerpoints of the user-tile
	private int health;
	private Level currentLevel;
	private Inventory inventory;

	private double velocityX, velocityY;

	// Player constructor
	public Player( Point2d position, Level currentLevel ) {
		this.currentLevel = currentLevel;
		this.position = position;
		tilePosition = new TileCoordinate( position );
		updateCornerlist();
		health = 100;
		velocityX = 0;
		velocityY = 0;
		inventory = new Inventory();
	}

	public void setCurrentLevel(Level arg) {
		this.currentLevel = arg;
	}

	// Position

	public void setPos( Point2d position ) {
		this.position = position ;
		updateCornerlist();
	}

	public Point2d getPosition() {
		return position;
	}

	private void updateCornerlist() {
		cornerList[0] = new Point2d( position.x-15, position.y-15 ); // Upper left
		cornerList[1] = new Point2d( position.x-15, position.y+15 ); // Lower left
		cornerList[2] = new Point2d( position.x+15, position.y-15 ); // Upper right
		cornerList[3] = new Point2d( position.x+15, position.y+15 ); // Lower right
	}

	public Point2d[] getCorners() {
		return cornerList;
	}

	public TileCoordinate getTilePosition() {
		return tilePosition;
	}

	// Health
	public int getHealth() {
		return health;
	}

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
		if (this.health - dHealth >= 0 && dHealth > 0)
			this.health -= dHealth;
		else if (dHealth > 0)
			this.health = 0;
	}

	public boolean dead() {
		return health == 0;
	}

	// Movement

	public double getVelocityX() {
		return velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void stop() {
		this.velocityX = 0;
		this.velocityY = 0;
	}

	public void accelerate( Direction dir, double magnitude ) {
		velocityX += magnitude * dir.dx();
		velocityY += magnitude * dir.dy();
	}

	public boolean collision( Direction dir ) {
		for ( int i = 0; i < 4; i++ ) {
			int nextX = (int)cornerList[i].x + dir.dx();
			int nextY = (int)cornerList[i].y + dir.dy();
			TileCoordinate nextTile = new TileCoordinate( new Point2d( nextX, nextY ) );
			try {
				if ( ( currentLevel.isBlocked( nextTile  ) ) || nextX < 0 ) return true;
			} catch ( Exception e ) {
				return true;
			}
		}
		return false;
	}

	public void move( Direction dir, int pixels ) {
		if ( collision(dir) ) {
			if ( velocityX > 20 || velocityY > 20 )
				decreaseHealth(3);
			return;
		}

		this.position.x += dir.dx() * pixels;
		this.position.y += dir.dy() * pixels;
		updateCornerlist();
		tilePosition = new TileCoordinate( position );
	}

	public void jump(){
		if( !collision( Direction.DOWN ) || collision( Direction.UP ) ) return;
		accelerate(Direction.UP, 13);
	}

	public void applyForces() {
		if ( onLadder() || ( collision(Direction.DOWN) && velocityY > 0 ) )
			velocityY = 0;
		else
			accelerate(Direction.DOWN, 1);

		if ( collision(Direction.DOWN) || collision(Direction.UP) )
			velocityX = velocityX/1.25;
	}

	public void doMovement() {
		for ( int i = 0; i < Math.abs( (int)getVelocityX() ); i++ ) {
			if( getVelocityX() > 0 )
				move(Direction.RIGHT, 1);
			if( getVelocityX() < 0 )
				move(Direction.LEFT, 1);
		}

		for ( int i = 0; i < Math.abs( (int)getVelocityY() ); i++ ) {
			if( getVelocityY() < 0 )
				move(Direction.UP, 1);
			if( getVelocityY() > 0 )
				move(Direction.DOWN, 1);
		}

	}

	public boolean onLadder() {
		return currentLevel.isLadder( new TileCoordinate( new Point2d( position.x, position.y+15 ) ) );
	}

	// Interaction

	public boolean onObject() {
		return currentLevel.isOnObject( tilePosition );
	}

	public void pickupObject() {

		InventoryObject item = currentLevel.getInvObj( tilePosition );

		if ( ( item.toString().equals("APPLE") || item.toString().equals("ORANGE") ) && ( health < MAX_HEALTH ) )
			increaseHealth(20);
		else
			inventory.addItem( item  );

		currentLevel.removeObject( tilePosition );
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void clearInventory() {
		inventory.clear();
	}
}