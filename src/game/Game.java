package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

import course.GolfCourse;
import course.Wall;
import geometry.Rectangle;
import graphics.Display;
import graphics.Render2D;
import menu.Background;
import menu.MenuButton;
import visibleObjects.Painter;
import visibleObjects.VisibleObject;

public class Game implements VisibleObject,KeyListener, MouseListener, Runnable,Painter{

	public static final int SC_MAIN_MENU=0,SC_GOLF_GAME=1,SC_CUT_SCENE=2,SC_TUTORIAL_GAME=3,SC_SETTINGS_GAME=4;
	Display display;
	boolean running=true;
	GolfCourse c;
	int screen=SC_MAIN_MENU;
	Background background;
	MenuButton[] buttons;
	int mX=0,mY=0;
	
	public static void main(String[] args) {
		new Game();
	}
	
	public Game(){
		display=Display.createNew();
		display.addVObject(this);
		display.addKeyListener(this);
		display.addMouseListener(this);
		buttons=MenuButton.getMenuButtons(this);
		
		c=new GolfCourse();
		c.addWall(new Wall(new Rectangle(100,100,100,100),new Color(100,0,100)));
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
			Arrays.fill(r.pixels, 0x01A611);
			c.render(r);
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
		for(MenuButton mb:buttons)
			mb.click(mX, mY);
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

}