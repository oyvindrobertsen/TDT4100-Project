package gui;

import map.Level;

import logic.Direction;
import logic.GameState;
import logic.Player;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;


public class MainDisplay extends BasicGame {

	private TiledMap map;
	private Input input;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private GameState state;
	private Player p;
	private Image playerImage;

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

		// Player rendering
		playerImage.draw( (float)p.getCorners()[0].x, (float)p.getCorners()[0].y ); // Layer 4 in .tmx
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		// Game essential stuff 
		map = new TiledMap("res/Firsttest.tmx");
		state = new GameState(new Level(map));
		p = state.getLevel().getPlayer();
		playerImage = new Image("res/pubdlcnt.png");
		input = gc.getInput();
		
		// UI
		gc.setTargetFrameRate(60);
		gc.setShowFPS(false);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {

		if ( p.onLadder() ){
			if ( input.isKeyDown(Input.KEY_UP) )	p.move(Direction.UP, 2);
			if ( input.isKeyDown(Input.KEY_DOWN) )	p.move(Direction.DOWN, 2);
			if ( input.isKeyDown(Input.KEY_LEFT) )	p.move(Direction.LEFT, 1);
			if ( input.isKeyDown(Input.KEY_RIGHT) )	p.move(Direction.RIGHT, 1);
			return;
		}

		if ( p.onObject() && input.isKeyDown(Input.KEY_E) ) 	p.pickupObject();

		if( input.isKeyDown(Input.KEY_SPACE) )
			p.jump();

		if ( p.collision(Direction.DOWN) ) {
			if( input.isKeyDown(Input.KEY_LEFT) )	p.accelerate(Direction.LEFT, 1);
			if( input.isKeyDown(Input.KEY_RIGHT) )	p.accelerate(Direction.RIGHT, 1);
		}

		p.applyForces();

		for ( int i = 0; i < Math.abs( (int)p.getVelocityX() ); i++) {
			if( p.getVelocityX() > 0 )
				p.move(Direction.RIGHT, 1);
			if( p.getVelocityX() < 0 )
				p.move(Direction.LEFT, 1);
		}

		for ( int i = 0; i < Math.abs( (int)p.getVelocityY() ); i++) {
			if( p.getVelocityY() < 0 )
				p.move(Direction.UP, 1);
			if( p.getVelocityY() > 0 )
				p.move(Direction.DOWN, 1);
		}
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
			System.out.println(p.getInventory());

	}
}
