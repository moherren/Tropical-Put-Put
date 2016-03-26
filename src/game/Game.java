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
import visibleObjects.Painter;
import visibleObjects.VisibleObject;

public class Game implements VisibleObject,KeyListener, MouseListener, Runnable,Painter, ActionListener{

	public static final int SC_MAIN_MENU=0,SC_GOLF_GAME=1,SC_CUT_SCENE=2,SC_TUTORIAL_GAME=3,SC_SETTINGS_GAME=4;
	Display display;
	boolean running=true;
	GolfCourse c;
	int screen=SC_MAIN_MENU;
	Background background;
	GUI gui;
	MenuButton[] buttons;
	int mX=0,mY=0;
	private boolean putting=false;
	private double updatePerSecond=100;
	private GolfBall ball;
	private boolean playing=false;
	
	public static void main(String[] args) {
		new Game();
	}
	
	public Game(){
		display=Display.createNew();
		display.addVObject(this);
		display.addKeyListener(this);
		display.addMouseListener(this);
		buttons=MenuButton.getMenuButtons(this);
		gui=new GUI();
		c=new GolfCourse();
		c.addObstacle(new Wall(new Rectangle(40,250,40,300)));
		c.addObstacle(new Wall(new Rectangle(400,80,760,40)));
		c.addObstacle(new Wall(new Rectangle(400,420,760,40)));
		c.addObstacle(new Wall(new Rectangle(760,250,40,300)));
		c.addSurface(new Grass(new Rectangle(500,250,400,300)));
		background=new Background(this);
		
		new Thread(this).run();
	}

	public void render(Render2D r) {
		switch(screen){
		
		case SC_MAIN_MENU:{
			Arrays.fill(r.pixels, 0x87CEEB);
			background.render(r);
			for(MenuButton mb:buttons)
				mb.render(r);
			break;
		}
		
		case SC_GOLF_GAME:{
			c.render(r);
			
			if(putting){
				r.drawLine(r, 0, 0xFFFFFF, mX, mY, ball.getPosition().x, ball.getPosition().y);
				System.out.println(gui);
				gui.powerLevel=Math.min(new Vector2D(mX-ball.getPosition().x,mY-ball.getPosition().y).magnitude()/200,1);
			}
			else{
				gui.powerLevel=0;
			}
			gui.render(r);
			break;
		}
		}
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
			
			for(MenuButton mb:buttons)
				mb.update(mX, mY);
				
			display.Render();
		}
	}


	public void mouseClicked(MouseEvent arg0) {
		
	}


	public void mouseEntered(MouseEvent arg0) {
		
	}


	public void mouseExited(MouseEvent arg0) {
		
	}


	public void mousePressed(MouseEvent arg0) {
		System.out.println("hello");
		switch(screen){
		case SC_MAIN_MENU:{
			for(MenuButton mb:buttons)
				mb.click(mX, mY);
			break;
		}
		
		case SC_GOLF_GAME:{
			if(putting){
				Vector2D toBall=new Vector2D(mX-ball.getPosition().x,mY-ball.getPosition().y);
				ball.putt(Math.min(toBall.magnitude()/20,10), toBall.normalize().negative());
				putting=false;
				gui.strokesNum++;
			}
			break;
		}
		}
		
	}


	public void mouseReleased(MouseEvent arg0) {
		
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
	}

}