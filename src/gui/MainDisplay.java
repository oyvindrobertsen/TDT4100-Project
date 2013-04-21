package gui;

import map.Level;

import logic.Direction;
import logic.GameState;
import logic.Player;
import logic.TileCoordinate;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class MainDisplay extends BasicGame {

	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	private GameState state;
	private TiledMap map;
	private Player player;
	private Input input;
	private Image playerImage, healthBar, healthImage;
	private TileCoordinate goalTile;

	public MainDisplay(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// General map rendering
		map.render(0, 0, 0); // Background
		map.render(0, 0, 1); // Terrain
		map.render(0, 0, 2); // Ladders
		map.render(0, 0, 3); // Inventory Objects
		map.render(0, 0, 4); // Goal

		// Player rendering
		playerImage.draw( (float)player.getCorners()[0].x, (float)player.getCorners()[0].y ); // Layer 4 in .tmx

		// UI
		healthBar.draw(10, 10);
		healthImage.draw(11,12, (float)( 326*( player.getHealth() / 300.0 ) ), 12 );
	}

	@Override
	public void init( GameContainer gc ) throws SlickException {
		// Game essential stuff 
		map = new TiledMap("res/Firsttest.tmx");
		state = new GameState(new Level(map));
		player = state.getLevel().getPlayer();
		playerImage = new Image("res/pubdlcnt.png");
		input = gc.getInput();

		// UI
		gc.setTargetFrameRate(60);
		gc.setShowFPS(false);
		healthBar = new Image("res/bar_empty.png"); // Health bar background
		healthImage = new Image("res/health.png");

		goalTile = state.getLevel().getGoal();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		map = state.getLevel().getMap();
		player = state.getLevel().getPlayer();

		if ( player.dead() )
			state.getLevel().reloadLevel();

		if ( player.onLadder() ){
			if ( input.isKeyDown(Input.KEY_UP) )	player.move(Direction.UP, 2);
			if ( input.isKeyDown(Input.KEY_DOWN) )	player.move(Direction.DOWN, 2);
			if ( input.isKeyDown(Input.KEY_LEFT) )	player.move(Direction.LEFT, 1);
			if ( input.isKeyDown(Input.KEY_RIGHT) )	player.move(Direction.RIGHT, 1);
			return;
		}

		if ( player.onObject() && input.isKeyDown(Input.KEY_E) )
			player.pickupObject();

		if( input.isKeyDown(Input.KEY_SPACE) )
			player.jump();

		if ( player.collision(Direction.DOWN) ) {
			if( input.isKeyDown(Input.KEY_LEFT) )	player.accelerate(Direction.LEFT, 1);
			if( input.isKeyDown(Input.KEY_RIGHT) )	player.accelerate(Direction.RIGHT, 1);
		}

		player.applyForces();
		player.doMovement();

		TileCoordinate tile = player.getTilePosition();
		if ( input.isKeyDown(Input.KEY_E) && tile.x() == goalTile.x() && tile.y() == goalTile.y() )
			state.loadNextLevel(player);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer( new MainDisplay("PlatformGame") );
		app.setDisplayMode(WIDTH, HEIGHT, false); 
		app.start();
	}

	public void keyPressed( int key, char c ) {
		if (key == Input.KEY_ESCAPE)
			System.exit(0);

		if (key == Input.KEY_I)
			System.out.println(player.getInventory());

	}
}
