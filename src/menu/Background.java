package menu;

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
	
	public Background(Game g){
		game=g;
		waterTile=Texture.loadBitmap("textures/watertile.png");
		ship=Texture.loadBitmap("textures/ship.png");
		sun=Texture.loadBitmap("textures/sun.png");
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
			
			r.draw(sun, 700, 25);
			double rayRadius=150;
			double increment=1/150.0;
			
			double beginRad=time/2000.0;
			int beginX=725;
			int beginY=50;
			
			for(double i=0;i<Math.PI/32.0;i+=increment){
				int endX=(int) (beginX+Math.cos((beginRad+i)%(Math.PI/2.0)-Math.PI)*rayRadius);
				int endY=(int) (beginY+Math.sin((beginRad+i)%(Math.PI/2.0)-Math.PI)*rayRadius);
				Render2D.drawLine(r, 0, 0xff0000, beginX, beginY, endX, endY);
			}
			
			for(double i=0;i<Math.PI/32.0;i+=increment){
				int endX=(int) (beginX+Math.cos((beginRad+i+Math.PI/4.0)%(Math.PI/2.0)-Math.PI)*rayRadius);
				int endY=(int) (beginY+Math.sin((beginRad+i+Math.PI/4.0)%(Math.PI/2.0)-Math.PI)*rayRadius);
				Render2D.drawLine(r, 0, 0xff0000, beginX, beginY, endX, endY);
			}
			
				
			r.draw(ship, r.width-ship.width, r.height/2-ship.height/2);
			
			break;
			}
		}
	}

}
