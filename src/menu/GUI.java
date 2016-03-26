package menu;

import java.util.Arrays;

import game.Game;
import graphics.Render;
import graphics.Render2D;
import visibleObjects.VisibleObject;

public class GUI implements VisibleObject{

	Game game;
	Render wordArt;
	Render strokes;
	public double powerLevel;
	public int strokesNum;
	
	public GUI(){
		wordArt=new Render(100,20);
		Arrays.fill(wordArt.pixels,0);
		wordArt.setFont("/LithosBlack.ttf");
		wordArt.drawString("Power", 0, 19, 1);
		
		strokes=new Render(94,20);
		Arrays.fill(strokes.pixels,0);
		strokes.drawString("Strokes", 10, 19, 1);
		strokesNum=0;
		powerLevel=0;
	}
	
	public void render(Render2D r) {
		renderPowerBar(r);
		renderStrokes(r);
	}

	public void renderPowerBar(Render2D r){
		boolean horizontal=true;
		int x=0,y=0;
		int width=400;
		int height=20;
		int startColor=0xffff00,endColor=0xff0000;
		int color=Render2D.mixColor(startColor, endColor, powerLevel);
		
		Render2D.drawLine(r, 1, x+1, y, x+width, y);
		Render2D.drawLine(r, 1, x+1, y+height, x+width, y+height);
		Render2D.drawLine(r, 1, x, y+1, x, y+height);
		Render2D.drawLine(r, 1, x+width, y+1, x+width, y+height);
		
		if(horizontal){
			for(int X=1;X<(int)(powerLevel*(width-1));X++){
				Render2D.drawLine(r, color, X+x, y+1, X+x, y+height);
			}
		}
		else{
			for(int Y=1;Y<(int)(powerLevel*(height-1));Y++){
				Render2D.drawLine(r, color, x+1, y+Y, x+width, y+Y);
			}
		}
		
		r.draw(wordArt, x, y+height-wordArt.height);
	}
	
	
	public void renderStrokes(Render2D r){
		int x=100,y=100;
		r.draw(strokes,x-strokes.width/2,y);
		r.drawString(strokesNum+"", x, y+45, 1);
	}
}
