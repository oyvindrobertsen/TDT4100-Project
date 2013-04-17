package gui;

import map.Level;

import logic.GameState;
import logic.Player;

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
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private GameState state;
	private Player p;

	public MainDisplay(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		for (int i = 0; i < map.getLayerCount(); i++) {
			map.render(0,0,i);
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		map = new TiledMap("res/Firsttest.tmx");
		state = new GameState(new Level(map));
		p = state.getLevel().getPlayer();
		input = gc.getInput();
		
		System.out.println(state.getLevel().toString());
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
