package menu;

import visibleObjects.VisibleObject;
import game.Game;
import graphics.Render;
import graphics.Render2D;
import graphics.Texture;

public class MenuButton implements VisibleObject{
	int x,y;
	Render sprite;
	boolean hover=false;
	Game game;
	static Render buttons=Texture.loadBitmap("textures/buttons.png");
	
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
	
	public void click(int x,int y){
		if(isWithin(x,y))
			click();
	}
	
	public void click(){
		
	}

	public void render(Render2D r) {
		r.draw(sprite, x, y);
		
	}
	
	public static MenuButton[] getMenuButtons(Game game){
		MenuButton[] mb=new MenuButton[4];
		mb[0]=new PlayButton(game);
		mb[1]=new TutorialButton(game);
		mb[2]=new SettingsButton(game);
		mb[3]=new QuitButton(game);
		return mb;
	}
	
	public static class PlayButton extends MenuButton{
		public PlayButton(Game g){
			super(50,200,Texture.getSpriteSheet(buttons, 199, 75, 0),g);
		}
		
		public void click(){
			game.setScreen(Game.SC_GOLF_GAME);
			game.startGame();
		}
	}
	
	public static class TutorialButton extends MenuButton{
		public TutorialButton(Game g){
			super(50,300,Texture.getSpriteSheet(buttons, 199, 75, 1),g);
		}
		
		public void click(){
			game.setScreen(Game.SC_TUTORIAL_GAME);
		}
	}
	
	public static class SettingsButton extends MenuButton{
		public SettingsButton(Game g){
			super(50,400,Texture.getSpriteSheet(buttons, 199, 75, 2),g);
		}
		
		public void click(){
			game.setScreen(Game.SC_SETTINGS_GAME);
		}
	}
	
	public static class QuitButton extends MenuButton{
		public QuitButton(Game g){
			super(50,500,Texture.getSpriteSheet(buttons, 199, 75, 3),g);
		}
		
		public void click(){
			//game.setScreen(Game.SC_SETTINGS_GAME);
		}
	}
}
