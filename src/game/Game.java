package game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import graphics.Display;
import graphics.Render2D;
import visibleObjects.Painter;
import visibleObjects.VisibleObject;

public class Game implements VisibleObject,KeyListener, MouseListener, Runnable,Painter{

	Display display;
	boolean running=true;
	Course c;
	
	public static void main(String[] args) {
		new Game();
	}
	
	public Game(){
		display=Display.createNew();
		display.addVObject(this);
		display.addKeyListener(this);
		display.addMouseListener(this);
		
		c=new Course();
		
		new Thread(this).run();
	}

	public void render(Render2D r) {
		Arrays.fill(r.pixels, 0x01A611);
		c.render(r);
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

}
