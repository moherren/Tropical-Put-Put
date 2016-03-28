package menu;

import java.util.Arrays;

import game.Game;
import graphics.Render;
import graphics.Render2D;
import graphics.Texture;
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
		wordArt.setFont("LithosBlack.ttf");
		wordArt.drawString("Power", 0, 19, 1);
		
		strokes=new Render(90,50);
		Texture.addGUIEdging(strokes, 0x00ff00,5);
		strokes.setFont("LithosBlack.ttf");
		strokes.setFont(strokes.getFont().deriveFont(16f));
		//Arrays.fill(strokes.pixels,0xaaaaaa);
		//strokes.draw(Texture.loadBitmap("textures/displayBack.png"), 20, 0);
		strokes.drawString("Strokes", 5, 19, 1);
		strokesNum=0;
		powerLevel=0;
	}
	
	public void render(Render2D r) {
		renderPowerBar(r);
		renderStrokes(r);
	}

	public void renderPowerBar(Render2D r){
		boolean horizontal=true;
		int x=20,y=540;
		int width=300;
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
		int x=70,y=450;
		r.draw(strokes,x-strokes.width/2,y);
		r.drawString(strokesNum+"", x-40, y+40, 1);
	}
}
