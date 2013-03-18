package gui;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class MainDisplay extends BasicGame{

	public MainDisplay(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new MainDisplay("PlatformGame"));
		app.setDisplayMode(800, 600, true);
		app.start();
	}

}
