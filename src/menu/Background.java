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
	
	public Background(Game g){
		game=g;
		waterTile=Texture.loadBitmap("textures/watertile.png");
		ship=Texture.loadBitmap("textures/ship.png");
	}
	
	public void render(Render2D r) {
		switch(game.getScreen()){
		case Game.SC_MAIN_MENU:{
			long time=(int) (System.currentTimeMillis());
			for(int x=0;x<r.width;x++){
				for(int y=400;y<r.height;y++){
					double alter=1;
					if(y>440)
						alter=1.1;
					if(y>480)
						alter=1.3;
					if(y>520)
						alter=1.6;
					if(y>560)
						alter=1.9;
					
					//alter=1.6;
					
					int newChange=(int) (time*alter/10%waterTile.width);
					r.pixels[x+y*r.width]=waterTile.pixels[(((x-newChange)%waterTile.width+waterTile.width)%waterTile.width)+((y-400)%waterTile.height)*waterTile.width];
				}
			}
			r.draw(ship, r.width-ship.width, r.height/2-ship.height/2);
			
			break;
			}
		}
	}

}
