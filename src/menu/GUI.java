package menu;

import java.util.Arrays;

import course.GolfCourse;
import game.TextBox;
import geometry.Vector2D;
import graphics.Render;
import graphics.Render2D;
import graphics.Texture;
import visibleObjects.VisibleObject;

public class GUI implements VisibleObject{

	Render wordArt;
	Render strokes;
	Render par;
	Render tiltCircle;
	Render tiltBall;
	final int tiltX=0,tiltY=0;
	public double powerLevel;
	public int strokesNum;
	public int parNum;
	GolfCourse gc;
	TextBox tip;
	public boolean clickToNext=false;
	
	public GUI(GolfCourse gc){
		tip=TextBox.newChatBox(200, 0, 300, 90, "Hello, I'm a text box. Who are you????");
		
		wordArt=new Render(100,20);
		Arrays.fill(wordArt.pixels,0);
		wordArt.setFont("LithosBlack.ttf");
		wordArt.drawString("Power", 0, 19, 1);
		
		strokes=new Render(90,45);
		Texture.addGUIEdging(strokes, 0x00ff00,5);
		strokes.setFont("LithosBlack.ttf");
		strokes.setFont(strokes.getFont().deriveFont(16f));
		//strokes.draw(Texture.loadBitmap("textures/displayBack.png"), 20, 0);
		strokes.drawString("Strokes", 5, 19, 1);
		strokesNum=0;
		powerLevel=0;
		
		par=new Render(strokes.width,strokes.height);
		Texture.addGUIEdging(par, 0x00ff00,5);
		par.setFont(strokes.getFont());
		par.drawString("Par", 5, 19, 1);
		parNum=0;
		
		Render wood=Texture.getSpriteSheet(GolfCourse.tiles, 50, 50, 2);
		
		int length=40;
		double perimeter=(length)*Math.PI*2;
		tiltCircle=new Render(length*2,length*2);
		Arrays.fill(tiltCircle.pixels,0);
		for(double angle=Math.PI/2.0;angle<=Math.PI*3/2.0;angle+=Math.PI*2/perimeter){
			int x=(int) (Math.cos(angle)*length+length);
			int y=(int) (Math.sin(angle)*length+length);
			int lineLength=(int) (Math.cos(angle)*2*length);
			tiltCircle.drawPixel(0x0000ff,x,y);
			for(int line=-1;line>lineLength;line--)
				if(tiltCircle.pixels[x+y*tiltCircle.width-line]!=0x0000ff){
					tiltCircle.drawPixel(Render.mixColor(wood.pixels[((x-line)%wood.width)+(y%wood.height)*wood.width], 0x0000ff,0.3), x-line,y);
				}
			tiltCircle.drawPixel(0x0000ff,x-lineLength,y);
			
			this.gc=gc;
		}
		
		tiltBall=new Render(10,10);
		Arrays.fill(tiltBall.pixels,0);
		length=5;
		perimeter=(5)*Math.PI*2;
		for(double angle=Math.PI/2.0;angle<=Math.PI*3/2.0;angle+=Math.PI*2/perimeter){
			int x=(int) (Math.cos(angle)*length+length);
			int y=(int) (Math.sin(angle)*length+length);
			int lineLength=(int) (Math.cos(angle)*2*length);
			tiltBall.drawPixel(1,x,y);
			for(int line=-1;line>lineLength;line--)
				if(tiltBall.pixels[x+y*tiltBall.width-line]!=1){
					tiltBall.drawPixel(0x0000ff, x-line,y);
				}
			tiltBall.drawPixel(1,x-lineLength,y);
			
		}
	}
	
	@Override
	public void render(Render2D r) {
		renderPowerBar(r);
		renderStrokes(r);
		renderTilt(r);
		//tip.render(r);
	}

	public void renderPowerBar(Render2D r){
		boolean horizontal=true;
		int x=20,y=540;
		int width=300;
		int height=20;
		int startColor=0xffff00,endColor=0xff0000;
		int color=Render.mixColor(startColor, endColor, powerLevel);
		
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
		int x=65,y=450;
		r.draw(strokes,x-strokes.width/2,y);
		r.drawString(strokesNum+"", x-40, y+40, 1);
		r.draw(par,x-par.width/2,y+strokes.height);
		r.drawString(parNum+"", x-40, y+40+strokes.height, 1);
	}
	
	public void renderTilt(Render2D r){
		int X=150,Y=450;
		Vector2D vec=gc.getTilt();
		int tiltX=(int) (vec.x/(Math.sin(Math.toRadians(gc.maxTilt))*gc.g)*tiltCircle.width/2),tiltY=(int)(vec.y/(Math.sin(Math.toRadians(gc.maxTilt))*gc.g)*tiltCircle.height/2);
		int length=50;
		r.draw(tiltCircle, X, Y);
		r.draw(tiltBall, tiltCircle.width/2+X+tiltX-tiltBall.width/2, tiltCircle.height/2+Y+tiltY-tiltBall.width/2);
	}
	
	public void renderTip(Render2D r){
		tip.render(r);
	}
	
	public void setTip(int type,String text){
		switch(type){
		case TextBox.TB_CHAT:{
			tip=TextBox.newChatBox(200, 350, 400, 100, text);
			clickToNext=true;
			break;
			}
		case TextBox.TB_INSTRUCT:{
			clickToNext=false;
			tip=TextBox.newHelpBox(200, 0, 450, 100, text);
		}
		}
	}

	public void clearTip() {
		tip=TextBox.blankTextBox();
		clickToNext=false;
	}
}
