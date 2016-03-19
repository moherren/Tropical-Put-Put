package game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

import course.GolfCourse;
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