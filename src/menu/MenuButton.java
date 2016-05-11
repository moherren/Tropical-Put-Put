package menu;

import visibleObjects.VisibleObject;

import java.awt.Point;

import javax.swing.JOptionPane;

import game.Game;
import game.TextBox;
import geometry.Vector2D;
import graphics.Display;
import graphics.Render;
import graphics.Render2D;
import graphics.Texture;

public class MenuButton implements VisibleObject{
	int x,y;
	Render sprite;
	boolean hover=false;
	Game game;
	public static int gameType=Game.SC_GOLF_GAME;
	static Render ball=Texture.loadBitmap("textures/ball.png");
	static Render buttons=Texture.loadBitmap("textures/buttons2.png");
	
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
		else
			hover=false;
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
		if(hover&&game.getScreen()==Game.SC_MAIN_MENU){
			r.draw(ball,x-50,y);
			r.draw(ball,x+sprite.width,y);
		}
	}
	
	public void update(){
		
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
			super(325,275,Texture.getSpriteSheet(buttons, 150, 50, 0),g);
		}
		
		public void click(){
			game.setScreen(Game.SC_GOLF_GAME);
			game.startGame();
			
		}
		
	}
	
	public static class TutorialButton extends MenuButton{
		public TutorialButton(Game g){
			super(325,350,Texture.getSpriteSheet(buttons, 150, 50, 1),g);
		}
		
		public void click(){
			game.setScreen(Game.SC_TUTORIAL_GAME);
			game.startTutorial();
			game.gui.setTip(TextBox.TB_INSTRUCT, "Hold down the left mouse button the prepare your swing.");		}
	}
	
	public static class SettingsButton extends MenuButton{
		public SettingsButton(Game g){
			super(325,425,Texture.getSpriteSheet(buttons, 150, 50, 2),g);
		}
		
		public void click(){
			game.setScreen(Game.SC_SETTINGS_GAME);
		}
		
	}
	
	public static class QuitButton extends MenuButton{
		public QuitButton(Game g){
			super(325,500,Texture.getSpriteSheet(buttons, 150, 50, 3),g);
		}
		
		public void click(){
			int n = JOptionPane.showConfirmDialog(
				    game.display,
				    "Are you sure you'd like to quit?",
				    "Tropical Cruise Putt-Putt",
				    JOptionPane.YES_NO_OPTION);
			if(n==0)
			System.exit(0);
		}
	}
	
	
	
	public static MenuButton[] getSettingsButtons(Game game){
		MenuButton[] mb=new MenuButton[3];
		mb[0]=new BackButton(game);
		mb[1]=new VolumeSlider(game);
		mb[2]=new DifficultySlider(game);
		return mb;
	}
	
	public static class BackButton extends MenuButton{
		public BackButton(Game g){
			super(50,500,Texture.getSpriteSheet(buttons, 150, 50, 4),g);
		}
		
		public void click(){
			game.setScreen(Game.backScreen,gameType);
		}
	}
	
	public static class VolumeSlider extends MenuButton{
		Render slider;
		
		int sliderX=0;
		int sliderY=0;
		
		boolean pressed=false;
		
		public VolumeSlider(Game g){
			super(200,130,new Render(400,50),g);
			sliderX=sprite.width/2;
			slider=Texture.loadBitmap("textures/slider.png");
			sliderY=y+sprite.height/2-slider.height/2;
		}
		
		public void click(){
			pressed=true;
		}
		
		public void update(int mX,int mY){
			if(!game.getMouseDown())
			{
				pressed=false;
				
			}
			else if(pressed){
				sliderX=Math.max(mX-x,0);
				sliderX=Math.min(sliderX, sprite.width);
				game.setVolume(sliderX/(sprite.width*1.0));
			}
		}
		
		public void render(Render2D r){
			int lineThickness=2;
				for(int y=-(lineThickness/2);y<lineThickness/2;y++){
					Render2D.drawLine(r, 1, x, y+sprite.height/2+this.y, x+sprite.width, y+sprite.height/2+this.y);
				}
				for(int x=0;x<lineThickness;x++){
					Render2D.drawLine(r, 1, x+this.x, y, x+this.x, y+sprite.height);
					Render2D.drawLine(r, 1, this.x-x+sprite.width, y, this.x-x+sprite.width, y+sprite.height);
				}
				r.draw(slider, x+sliderX-slider.width/2, sliderY);
				r.drawString("Volume: "+(int)(sliderX*100/(sprite.width*1.0))+"%", x, sliderY-5, 1);
		}
	}
	
	public static class DifficultySlider extends MenuButton{
		Render slider;
		
		int sliderX=0;
		int sliderY=0;
		
		boolean pressed=false;
		
		public DifficultySlider(Game g){
			super(200,300,new Render(400,50),g);
			sliderX=sprite.width/2;
			slider=Texture.loadBitmap("textures/slider.png");
			sliderY=y+sprite.height/2-slider.height/2;
		}
		
		public void click(){
			pressed=true;
		}
		
		public void update(int mX,int mY){
			if(!game.getMouseDown())
			{
				pressed=false;
				
			}
			else if(pressed){
				sliderX=Math.max(mX-x,0);
				sliderX=Math.min(sliderX, sprite.width);
				game.setDifficulty(sliderX/(sprite.width*1.0));
			}
		}
		
		public void render(Render2D r){
			int lineThickness=2;
				for(int y=-(lineThickness/2);y<lineThickness/2;y++){
					Render2D.drawLine(r, 1, x, y+sprite.height/2+this.y, x+sprite.width, y+sprite.height/2+this.y);
				}
				for(int x=0;x<lineThickness;x++){
					Render2D.drawLine(r, 1, x+this.x, y, x+this.x, y+sprite.height);
					Render2D.drawLine(r, 1, this.x-x+sprite.width, y, this.x-x+sprite.width, y+sprite.height);
				}
				r.draw(slider, x+sliderX-slider.width/2, sliderY);
				if((int)(sliderX*100/(sprite.width*1.0))!=0)
					r.drawString("Difficulty: "+(int)(sliderX*100/(sprite.width*1.0))+"%", x, sliderY-5, 1);
				else
					r.drawString("Difficulty: Baby", x, sliderY-5, 1);
		}
	}
	
	
	
	
	public static MenuButton[] getScorecardButtons(Game game){
		MenuButton[] mb=new MenuButton[1];
		mb[0]=new NextCourseButton(game);
		return mb;
	}
	
	public static class NextCourseButton extends MenuButton{
		public NextCourseButton(Game g){
			super(Display.WIDTH-50-150,Display.HEIGHT-50-50,Texture.getSpriteSheet(buttons, 150, 50, 5),g);
		}
		
		public void click(){
			if(gameType==Game.SC_TUTORIAL_GAME)
				game.gui.setTip(TextBox.TB_CHAT, "On windy days the cruise ship we're on playing on can rock back and forth.");
			game.setScreen(gameType);
		}
	}
	
	public static class PauseButton extends MenuButton{
		public PauseButton(Game g){
			super(Display.WIDTH-15-50,15,Texture.loadBitmap("textures/pauseButton.png"),g);
		}
		
		public void click(){
			gameType=game.getScreen();
			game.setScreen(Game.SC_PAUSE_MENU);
		}
	}
	
	public static MenuButton[] getPauseMenuButtons(Game game){
		MenuButton[] mb=new MenuButton[3];
		mb[0]=new SettingsButton(game);
		mb[0].y=275-53;
		mb[1]=new MainMenuButton(game);
		mb[2]=new BackButton(game);
		mb[2].x=325;
		mb[2].y=mb[1].y+53;
		return mb;
	}
	
	public static class MainMenuButton extends MenuButton{
		public MainMenuButton(Game g){
			super(325,275,Texture.getSpriteSheet(buttons, 150, 50, 6),g);
		}
		public void click(){
			int n = JOptionPane.showConfirmDialog(
				    game.display,
				    "Are you sure you'd like to quit to the main menu?",
				    "Tropical Cruise Putt-Putt",
				    JOptionPane.YES_NO_OPTION);
			if(n==0){
			game.stopGame();
			game.setScreen(Game.SC_MAIN_MENU);
			}
		}
	}
	
	public static class MainMenuReturnButton extends MenuButton{
		public MainMenuReturnButton(Game g){
			super(Display.WIDTH-50-150,Display.HEIGHT-50-50,Texture.getSpriteSheet(buttons, 150, 50, 6),g);
		}
		public void click(){
			
			game.setScreen(Game.SC_MAIN_MENU);
		}
	}
}
