package menu;

import java.util.Random;

import game.Game;
import graphics.Display;
import graphics.Render;
import graphics.Render2D;
import graphics.Texture;
import visibleObjects.VisibleObject;

public class Background implements VisibleObject{

	Game game;
	Render waterTile;
	Render ship;
	Render sun;
	Render title;
	Render cloud;
	Render settingsBack;
	int[] cloudXs, cloudYs;
	
	public Background(Game g){
		game=g;
		waterTile=Texture.loadBitmap("textures/watertile.png");
		ship=Texture.loadBitmap("textures/ship.png");
		sun=Texture.loadBitmap("textures/sun.png");
		title=Texture.loadBitmap("textures/Title.png");
		cloud=Texture.loadBitmap("textures/cloud.png");
		settingsBack=Texture.loadBitmap("textures/background.png");
		Random rand=new Random();
		int cloudCount=3;
		cloudXs=new int[cloudCount];
		cloudYs=new int[cloudCount];
		for(int i=0;i<cloudCount;i++){
			cloudXs[i]=rand.nextInt(800);
			cloudYs[i]=rand.nextInt(300);
		}
	}
	
	public void render(Render2D r) {
		switch(game.getScreen()){
		case Game.SC_MAIN_MENU:{
			long time=(int) (System.currentTimeMillis());
			for(int x=0;x<r.width;x++){
				for(int y=400;y<r.height;y++){
					double alter=0.9;
					if(y>440)
						alter=1.1;
					if(y>480)
						alter=1.3;
					if(y>520)
						alter=1.5;
					if(y>560)
						alter=1.7;
					
					//alter=1.6;
					
					int newChange=(int) (time*alter/20%waterTile.width);
					r.pixels[x+y*r.width]=waterTile.pixels[(((x-newChange)%waterTile.width+waterTile.width)%waterTile.width)+((y-400)%waterTile.height)*waterTile.width];
				}
			}
			
			
			double rayRadius=175;
			double increment=1/rayRadius;
			
			double beginRad=time/6000.0;
			int beginX=800;
			int beginY=0;
			
			for(double i=0;i<Math.PI/16.0;i+=increment){
				int endX=(int) (beginX+Math.cos((beginRad+i)%(Math.PI/2.0)-Math.PI)*rayRadius);
				int endY=(int) (beginY+Math.sin((beginRad+i)%(Math.PI/2.0)-Math.PI)*rayRadius);
				drawRay(r, 0xFCDC3B, beginX, beginY, endX, endY);
			}
			
			for(double i=0;i<Math.PI/16.0;i+=increment){
				int endX=(int) (beginX+Math.cos((beginRad+i+Math.PI/4.0)%(Math.PI/2.0)-Math.PI)*rayRadius);
				int endY=(int) (beginY+Math.sin((beginRad+i+Math.PI/4.0)%(Math.PI/2.0)-Math.PI)*rayRadius);
				drawRay(r, 0xFCDC3B, beginX, beginY, endX, endY);
			}
			
			r.draw(sun, 775, -25);
				
			for(int i=0;i<cloudXs.length;i++){
				drawCloud((int)((cloudXs[i]+(time/50.0))%(r.width+cloud.width))+(r.width),cloudYs[i],r);
			}
			r.draw(ship, r.width-ship.width, r.height/2-ship.height/2);
			
			r.draw(title, 250, 50);
			
			break;
			}
		
			
		}
	}

	public static void drawRay(Render2D r,int color,double x1,double y1,double x2,double y2){
		double length=Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
		double slopeY=(y2-y1)/length,slopeX=(x2-x1)/length;
		
		for(int i=0;i<length;i++){
			int x=(int) (x1+slopeX*i);
			int y=(int) (y1+slopeY*i);
			if(x>=0&&y>=0&&x<r.width&&y<r.height){
				r.pixels[x+y*r.width]=Render.mixColor(0x87CEEB, color, 1-(i/length));
			}
		}			
	}
	
	public void drawCloud(int x,int y,Render r){
		r.draw(cloud, x, y);
	}

	public void renderSettings(Render2D r) {
		r.draw(settingsBack, 0, 0);
		
	}
}
