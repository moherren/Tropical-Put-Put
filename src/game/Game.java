package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.Timer;

import course.GolfCourse;
import course.Grass;
import course.Hole;
import course.Levels;
import course.Stone;
import course.MovingWall;
import course.Wall;
import course.Windmill;
import entities.GolfBall;
import geometry.Line;
import geometry.Rectangle;
import geometry.Vector2D;
import graphics.Display;
import graphics.Render;
import graphics.Render2D;
import graphics.Texture;
import menu.Background;
import menu.GUI;
import menu.MenuButton;
import sound.SoundHandler;
import visibleObjects.Painter;
import visibleObjects.VisibleObject;

public class Game implements VisibleObject,KeyListener, MouseListener, Runnable,Painter, ActionListener{

	public static final int SC_MAIN_MENU=0,SC_GOLF_GAME=1,SC_CUT_SCENE=2,SC_TUTORIAL_GAME=3,SC_SETTINGS_GAME=4,
			SC_SCORECARD=5, SC_PAUSE_MENU=6;
	public Display display;
	boolean running=true;
	GolfCourse course;
	GolfCourse[] holes;
	int screen=SC_MAIN_MENU;
	Background background;
	GUI gui;
	MenuButton[] menuButtons;
	MenuButton[] settingsButtons;
	MenuButton[] scorecardButtons;
	MenuButton[] pauseMenuButtons;
	MenuButton pauseButton;
	MenuButton finalScorecard;
	int mX=0,mY=0;
	private boolean putting=false;
	private double updatePerSecond=100;
	private GolfBall ball;
	private boolean playing=false;
	private boolean mouseDown=false;
	double volume=0.5;
	Render2D lastScreen, newScreen;
	int holeNumber=0;
	Scorecard scorecard;
	public static int backScreen=SC_MAIN_MENU;
	Timer timer;
	private double difficulty=0.5;
	
	public static void main(String[] args) {
		new Game();
	}
	
	public Game(){
		holes=new GolfCourse[18];
		display=Display.createNew();
		display.addVObject(this);
		display.addKeyListener(this);
		display.addMouseListener(this);
		menuButtons=MenuButton.getMenuButtons(this);
		settingsButtons=MenuButton.getSettingsButtons(this);
		scorecardButtons=MenuButton.getScorecardButtons(this);
		pauseMenuButtons=MenuButton.getPauseMenuButtons(this);
		pauseButton=new MenuButton.PauseButton(this);
		finalScorecard=new MenuButton.MainMenuReturnButton(this);
		
		course=new GolfCourse(new Vector2D(0,0),0);
		gui=new GUI(course);
		
		newScreen=new Render2D(Display.WIDTH,Display.HEIGHT);
		lastScreen=new Render2D(Display.WIDTH,Display.HEIGHT);
		
		holes[0]=new GolfCourse(new Vector2D(400,300), 5);
		holes[0].addObstacle(new Wall(new Rectangle(20,100,40,300)));
		Windmill spinny=new Windmill(new Vector2D(300,300),40,5);
		holes[0].addEntity(spinny);
		holes[0].addObstacle(spinny);
		MovingWall mw=new MovingWall(new Rectangle(100,100,50,50),new Line(new Vector2D(100,100),new Vector2D(300,100)));
		holes[0].addEntity(mw);
		holes[0].addObstacle(mw);
		holes[0].addObstacle(new Wall(new Rectangle(20,60,760,40)));
		holes[0].addObstacle(new Wall(new Rectangle(20,400,760,40)));
		holes[0].addObstacle(new Wall(new Rectangle(740,100,40,300)));
		holes[0].addSurface(new Grass(new Rectangle(300,100,400,300)));
		MovingWall movie=new MovingWall(new Rectangle(250,290,100,10),new Line(new Vector2D(500,300),new Vector2D(300,300)));
		holes[0].addEntity(movie);
		holes[0].addObstacle(movie);
		holes[0].addHole(new Hole(new Vector2D(200,200),8));
		holes[1]=new GolfCourse(new Vector2D(400,300), 2);
		holes[1].addObstacle(new Wall(new Rectangle(20,100,40,300)));
		holes[1].addObstacle(new Wall(new Rectangle(20,60,760,40)));
		holes[1].addObstacle(new Wall(new Rectangle(20,400,760,40)));
		holes[1].addObstacle(new Wall(new Rectangle(740,100,40,300)));
		holes[1].addSurface(new Stone(new Rectangle(300,100,400,300)));
		holes[1].addHole(new Hole(new Vector2D(600,200),8));
		holes[2]=Levels.getCourse1();
//		holes[0]=Levels.getCourse17();
		background=new Background(this);
		SoundHandler.playMusic(SoundHandler.SONG_ONE, 0);
		SoundHandler.setMusicVolume(volume);
		
		new Thread(this).run();
	}

	public void render(Render2D r) {
		
		r.setFont("LithosBlack.ttf");
		
		switch(screen){
		
		case SC_MAIN_MENU:{
			Arrays.fill(r.pixels, 0x87CEEB);
			background.render(r);
			for(MenuButton mb:menuButtons)
				mb.render(r);
			break;
		}
		
		case SC_SETTINGS_GAME:{
			background.renderSettings(r);
			for(MenuButton mb:settingsButtons)
				mb.render(r);
			break;
		}
		
		case SC_GOLF_GAME:{
			if(course!=null){
				course.render(r);
			}
			if(putting){
				double x=ball.getPosition().x;
				double y=ball.getPosition().y;
				Vector2D dir=new Vector2D(mX-x,mY-y).negative().normalize();
				r.drawLine(r, 0, 0xFFFFFF, x, y, x+dir.x*gui.powerLevel*150, y+dir.y*gui.powerLevel*150);
				//the rendering here could be replaced with some sort of arrow maybe?
			}
			else{
				gui.powerLevel=0;
			}
			gui.render(r);
			pauseButton.render(r);
			break;
		}
		
		case SC_SCORECARD:{
			r.draw(lastScreen, 0, 0);
			renderScorecard(r);
			if(holeNumber<19)
				for(MenuButton mb:scorecardButtons)
					mb.render(r);
			else{
				finalScorecard.render(r);
				gui.renderTip(r);
			}
			break;
			}
		
			case SC_PAUSE_MENU:{
				r.draw(lastScreen, 0, 0);
				for(MenuButton mb:pauseMenuButtons)
					mb.render(r);
				break;
			}
		}
	}
	
	public Point getMouse(){
		return display.getMousePosition();
	}

	public boolean getMouseDown(){
		return mouseDown;
	}
	
	public void paint(Graphics g) {
		
	}


	public void run() {
		while(running){
			Point mouse=display.getMousePosition();
			if(mouse!=null){
				mX=mouse.x;
				mY=mouse.y;
			}
			
			for(MenuButton mb:menuButtons)
				mb.update(mX, mY);
				
			for(MenuButton mb:settingsButtons)
				mb.update(mX, mY);
			
			for(MenuButton mb:scorecardButtons)
				mb.update(mX, mY);
			
			display.Render();
		}
	}


	public void mouseClicked(MouseEvent arg0) {
		switch(screen){
		case SC_MAIN_MENU:{
			
		}
		
		case SC_GOLF_GAME:{
			
		}
	}
	}


	public void mouseEntered(MouseEvent arg0) {
		
	}


	public void mouseExited(MouseEvent arg0) {
		
	}


	public void mousePressed(MouseEvent arg0) {
		if(arg0.getButton()==MouseEvent.BUTTON1)
		switch(screen){
		case SC_GOLF_GAME:{
			pauseButton.click(mX, mY);
			if(!pauseButton.isWithin(mX, mY))
				mouseDown=true;
			break;
		}
		
		case SC_MAIN_MENU:{
			for(MenuButton mb:menuButtons)
				mb.click(mX, mY);
			break;
		}
		
		case SC_SETTINGS_GAME:{
			if(!settingsButtons[0].isWithin(mX, mY))
			mouseDown=true;
			for(MenuButton mb:settingsButtons)
				mb.click(mX, mY);
			break;
		}
		
		case SC_SCORECARD:{
			if(holeNumber<19)
				for(MenuButton mb:scorecardButtons)
					mb.click(mX, mY);
			else
				finalScorecard.click(mX, mY);
			break;
		}
		}
		
	}


	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getButton()==MouseEvent.BUTTON1)
		switch(screen){
		case SC_MAIN_MENU:{
			for(MenuButton mb:menuButtons)
				mb.click(mX, mY);
			break;
		}
		
		case SC_SETTINGS_GAME:{
			mouseDown=false;
			break;
		}
		
		case SC_GOLF_GAME:{
			if(putting&&mouseDown){
				Vector2D toBall=new Vector2D(mX-ball.getPosition().x,mY-ball.getPosition().y);
				putt(gui.powerLevel*10, toBall.normalize().negative());
				putting=false;
				gui.strokesNum++;
			}
			mouseDown=false;
			break;
		}
		
		case SC_PAUSE_MENU:{
			for(MenuButton mb:pauseMenuButtons)
				mb.click(mX, mY);
			break;
		}
		}
	}


	public void putt(double power, Vector2D direction) {
		ball.putt(power, direction);
	}

	public void keyPressed(KeyEvent arg0) {
		
	}


	public void keyReleased(KeyEvent arg0) {
		
	}


	public void keyTyped(KeyEvent arg0) {
		
	}

	public void setScreen(int i){
		if(i==SC_PAUSE_MENU)
			pause();
		
		else if(i==SC_GOLF_GAME&&screen==SC_GOLF_GAME)
			unpause();
		
		else if(i==SC_GOLF_GAME&&SoundHandler.currentSong!=SoundHandler.SONG_TWO){
			SoundHandler.playMusic(SoundHandler.SONG_TWO, 0f);
			SoundHandler.setMusicVolume(volume);
		}
		else if(i==SC_MAIN_MENU&&SoundHandler.currentSong!=SoundHandler.SONG_ONE){
			SoundHandler.playMusic(SoundHandler.SONG_ONE, 0f);
			SoundHandler.setMusicVolume(volume);
		}
			
		backScreen=screen;
		render(lastScreen);
		screen=i;
		render(newScreen);
		if(i==SC_SCORECARD){
			for(int y=0;y<lastScreen.height;y++)
				lastScreen.blurRow(5, y);
			for(int y=0;y<lastScreen.width;y++)
				lastScreen.blurColumn(5, y);
		}
		if(i==SC_PAUSE_MENU){
			lastScreen.convertToGray(1);
			Render2D panel=new Render2D(200,190);
			Texture.addGUIEdging(panel, 0xffffff, 5);
			lastScreen.draw(panel, lastScreen.width/2-panel.width/2, lastScreen.height/2-panel.height/2);
			lastScreen.setFont("LithosBlack.ttf");
			lastScreen.setFont(lastScreen.getFont().deriveFont(64f));
			lastScreen.drawDetailedString("Paused", 260, 150, 1);
			lastScreen.setFont(lastScreen.getFont().deriveFont(24f));
		}
	}
	
	public void setScreen(int i,int backScreen){
		screen=backScreen;
		setScreen(i);
	}
	
	public int getScreen() {
		return screen;
	}

	public void startGame() {
		scorecard=new Scorecard();
		if(!playing){
			holeNumber=0;
			timer=new Timer((int)(1000/updatePerSecond),this);
			timer.start();
			playing=true;
			loadCourse(holes[0]);
			putting=true;
			new Thread(){
				public void run(){
					Scanner scan=new Scanner(System.in);
					while(true){
						course.maxPreventPush=scan.nextDouble();
					}
				}
			}.start();
		}
	}
	
	public void stopGame(){
		playing=false;
		
	}
	
	public void actionPerformed(ActionEvent e) {
		course.update((int)(1000/updatePerSecond)/10);
		if(course.scored(ball)){
			course.removeEntity(ball);
			scorecard.setStrokes("Player 1", holeNumber,ball.putts);
			setScreen(SC_SCORECARD);
			if(holeNumber<18)
				loadCourse(holes[holeNumber]);
			else{
				compliment("Player 1");
			}
		}
		if(difficulty>0){
			if(Math.random()<0.5)
				course.preventScore(ball,20);
		}
		if(ball.getVelocity().isZeroed()){
			if(!putting){
				putting=true;
			}
			else if(putting&&mouseDown){
				gui.powerLevel=gui.powerLevel%1+0.005;
			}
		}
		else{
			putting=false;
			gui.powerLevel=0;
		}
		
	}
	
	private void compliment(String string) {
		double ability=scorecard.getComplimentScore(string);
		
		if(ability>=1)
			gui.setTip(TextBox.TB_CHAT, "Wow a perfect game! You're a Putt-Putt champion!");
		else if(ability>=0.5)
			gui.setTip(TextBox.TB_CHAT, "Really great game! You should play professionally!");
		else if(ability>=-0.05)
			gui.setTip(TextBox.TB_CHAT, "You got a good score! Way to go!");
		else if(ability>=-0.3)
			gui.setTip(TextBox.TB_CHAT, "Not bad!");
		else if(ability>=-0.5)
			gui.setTip(TextBox.TB_CHAT, "Don't worry about the score, you'll do better next time!");
		else if(ability>=-0.7)
			gui.setTip(TextBox.TB_CHAT, "Maybe you should practice some more!");
	}

	public void loadCourse(GolfCourse gc){
		scorecard.setPars(holeNumber, course.par);
		course=gc;
		gc.removeBalls();
		gc.tiltVelocity=new Vector2D(0,0);
		ball=new GolfBall(gc.ballStart.clone(),gc);
		course.addEntity(ball);
		gui=new GUI(course);
		gui.parNum=course.par;
		holeNumber++;
		if(difficulty>0){
			course.tiltDirection=new Vector2D(Math.random()*2-1,Math.random()*2-1).normalize();
			course.tiltAngle=Math.random()*course.maxTilt;
		}
		else{
			course.tiltDirection=new Vector2D(0,0);
			course.tiltAngle=0;
		}
	}

	public void setVolume(double amount) {
		volume=amount;
		SoundHandler.setMusicVolume(amount);
	}

	public void setDifficulty(double d) {
		difficulty=d;
	}
	
	public void renderScorecard(Render2D r){
		scorecard.render(r);
	}

	public void pause(){
		timer.stop();
		timer=new Timer((int)(1000/updatePerSecond),this);
	}
	
	public void unpause(){
		
		timer.start();
	}
}