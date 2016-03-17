package menu;

import visibleObjects.VisibleObject;
import game.Game;
import game.GolfBall;
import graphics.Render;
import graphics.Render2D;
import graphics.Texture;

public class MenuButton implements VisibleObject{
	int x,y;
	Render sprite;
	boolean hover=false;
	Game game;
	private MenuButton(int x,int y,Render sprite,Game game){
		this.x=x;
		this.y=y;
		this.sprite=sprite;
		this.game=game;
	}
	
	public void update(int mX,int mY){
		if(isWithin(mX,mY)){
			hover=true;
		}
	}
	
	public boolean isWithin(int mX,int mY){
		double dx = Math.max(Math.max(this.x - mX,  mX - (this.x+sprite.width)),0);
		double dy = Math.max(Math.max(this.y - mY, mY - (this.y+sprite.height)),0);
		return Math.sqrt(dx*dx + dy*dy)==0;
	}
	
	public void click(){
		
	}

	public void render(Render2D r) {
		r.draw(sprite, x, y);
		
	}
	
	public class PlayButton extends MenuButton{
		public PlayButton(Game g){
			super(100,100,Texture.loadBitmap("textures/playButton.png"),g);
		}
		
		public void click(){
			game.setScreen(Game.SC_GOLF_GAME);
		}
	}
	
	public class TutorialButton extends MenuButton{
		public TutorialButton(Game g){
			super(100,200,Texture.loadBitmap("textures/tutorialButton.png"),g);
		}
		
		public void click(){
			game.setScreen(Game.SC_TUTORIAL_GAME);
		}
	}
	
	public class SettingsButton extends MenuButton{
		public SettingsButton(Game g){
			super(100,300,Texture.loadBitmap("textures/playButton.png"),g);
		}
		
		public void click(){
			game.setScreen(Game.SC_SETTINGS_GAME);
		}
	}
	
	public class QuitButton extends MenuButton{
		public QuitButton(Game g){
			super(100,300,Texture.loadBitmap("textures/quitButton.png"),g);
		}
		
		public void click(){
			//game.setScreen(Game.SC_SETTINGS_GAME);
		}
	}
}
