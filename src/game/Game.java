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
import course.Wall;
import entities.GolfBall;
import geometry.Rectangle;
import geometry.Vector2D;
import graphics.Display;
import graphics.Render2D;
import menu.Background;
import menu.GUI;
import menu.MenuButton;
import sound.SoundHandler;
import visibleObjects.Painter;
import visibleObjects.VisibleObject;

public class Game implements VisibleObject,KeyListener, MouseListener, Runnable,Painter, ActionListener{

	public static final int SC_MAIN_MENU=0,SC_GOLF_GAME=1,SC_CUT_SCENE=2,SC_TUTORIAL_GAME=3,SC_SETTINGS_GAME=4;
	public Display display;
	boolean running=true;
	GolfCourse c;
	int screen=SC_MAIN_MENU;
	Background background;
	GUI gui;
	MenuButton[] menuButtons;
	MenuButton[] settingsButtons;
	int mX=0,mY=0;
	private boolean putting=false;
	private double updatePerSecond=100;
	private GolfBall ball;
	private boolean playing=false;
	private boolean mouseDown=false;
	double volume=0.5;
	
	public static void main(String[] args) {
		new Game();
	}
	
	public Game(){
		display=Display.createNew();
		display.addVObject(this);
		display.addKeyListener(this);
		display.addMouseListener(this);
		menuButtons=MenuButton.getMenuButtons(this);
		settingsButtons=MenuButton.getSettingsButtons(this);
		
		c=new GolfCourse();
		c.addObstacle(new Wall(new Rectangle(40,250,40,300)));
		c.addObstacle(new Wall(new Rectangle(400,80,760,40)));
		c.addObstacle(new Wall(new Rectangle(400,420,760,40)));
		c.addObstacle(new Wall(new Rectangle(760,250,40,300)));
		c.addSurface(new Grass(new Rectangle(500,250,400,300)));
		gui=new GUI(c);
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
			c.render(r);
			
			if(putting){
				double x=ball.getPosition().x;
				double y=ball.getPosition().y;
				Vector2D dir=new Vector2D(mX-x,mY-y).negative().normalize();
				r.drawLine(r, 0, 0xFFFFFF, x, y, x+dir.x*gui.powerLevel*200, y+dir.y*gui.powerLevel*200);
				//the rendering here could be replaced with some sort of arrow maybe?
			}
			else{
				gui.powerLevel=0;
			}
			gui.render(r);
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
		switch(screen){
		case SC_MAIN_MENU:{
			for(MenuButton mb:menuButtons)
				mb.click(mX, mY);
			break;
		}
		
		case SC_SETTINGS_GAME:{
			for(MenuButton mb:settingsButtons)
				mb.click(mX, mY);
			break;
		}
		
		case SC_GOLF_GAME:{
			
			break;
		}
		}
		mouseDown=true;
		
	}


	public void mouseReleased(MouseEvent arg0) {
		switch(screen){
		case SC_MAIN_MENU:{
			for(MenuButton mb:menuButtons)
				mb.click(mX, mY);
			break;
		}
		
		case SC_GOLF_GAME:{
			if(putting&&mouseDown){
				Vector2D toBall=new Vector2D(mX-ball.getPosition().x,mY-ball.getPosition().y);
				ball.putt(gui.powerLevel*10, toBall.normalize().negative());
				putting=false;
				gui.strokesNum++;
			}
			break;
		}
		}
		mouseDown=false;
	}


	public void keyPressed(KeyEvent arg0) {
		
	}


	public void keyReleased(KeyEvent arg0) {
		
	}


	public void keyTyped(KeyEvent arg0) {
		
	}

	public void setScreen(int i){
		screen=i;
	}
	
	public int getScreen() {
		return screen;
	}

	public void startGame() {
		if(!playing){
			new Timer((int)(1000/updatePerSecond),this).start();
			playing=true;
			ball=new GolfBall(new Vector2D(450,200),c);
			c.addEntity(ball);
			putting=true;
			new Thread(){
				public void run(){
					Scanner scan=new Scanner(System.in);
					while(true){
						System.out.println("new tilt direction:");
						Vector2D newTilt=new Vector2D(scan.nextDouble(),scan.nextDouble()).normalize();
						System.out.println("new angle:");
						double newAngle=scan.nextDouble();
						c.tiltDirection=newTilt;
						c.tiltAngle=newAngle;
					}
				}
			}.start();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		c.update((int)(1000/updatePerSecond)/10);
		if(ball.getVelocity().isZeroed()&&!putting){
			putting=true;
		}
		else if(putting&&mouseDown){
			gui.powerLevel=gui.powerLevel%1+0.005;
		}
	}

	public void setVolume(double amount) {
		volume=amount;
		SoundHandler.setMusicVolume(amount);
	}

	public void setDifficulty(double d) {
		
	}

}