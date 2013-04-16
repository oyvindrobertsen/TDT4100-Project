package gui;

import map.Level;

import logic.GameState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;


public class MainDisplay extends BasicGame {
	
	private TiledMap map;
	private Input input;
	private int x, y, playerW, playerH, speed;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private GameState state;

	public MainDisplay(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		map.render(-400,-400,0); //Background
		map.render(-400,-400,1); //Terrain
		if( input.isKeyDown(Input.KEY_UP)		&&	y > 0				)	y -= speed;
		if( input.isKeyDown(Input.KEY_DOWN)		&&	y < HEIGHT-playerH	)	y += speed;
		if( input.isKeyDown(Input.KEY_LEFT)		&&	x > 0				)	x -= speed;
		if( input.isKeyDown(Input.KEY_RIGHT)	&&	x < WIDTH-playerW 	)	x += speed;
		g.fillOval(x, y, playerW, playerH);		// player-figur
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		map = new TiledMap("res/Firsttest.tmx");
		state = new GameState(new Level(map));
		input = gc.getInput();
		
		System.out.println(state.getLevel().toString());
		
		x = 50;
		y = 50;
		playerW = 50;
		playerH = 150;
		speed = 10;
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new MainDisplay("PlatformGame"));
		app.setDisplayMode(WIDTH, HEIGHT, false); 
		app.start();
	}
	
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ESCAPE)
			System.exit(0);
	}


}
