package course;
import java.awt.Graphics;

import entities.GameEntity;
import geometry.Polygon;
import geometry.Shape;
import geometry.Vector2D;
import graphics.Render2D;

public class SpeedBoost implements Obstacle {

	private Polygon shape;
	
	int minX=Integer.MAX_VALUE,minY=Integer.MAX_VALUE,maxX=Integer.MIN_VALUE,maxY=Integer.MIN_VALUE;
	double X,Y;
	
	public SpeedBoost(Polygon shape){
		for(Vector2D v:shape.points){
			minX=(int) Math.min(minX, v.x);
			minY=(int) Math.min(minY, v.y);
			maxX=(int) Math.max(maxX, v.x);
			maxY=(int) Math.max(maxY, v.y);
		}
		this.shape=shape;
		Vector2D v=shape.getPosition();
		X=v.x;
		Y=v.y;
	}
	
	@Override
	public void render(Render2D r) {
		for(int x=minX;x<maxX;x++)
			for(int y=minY;y<maxY;y++){
				if(shape.includes(new Vector2D(x+X,y+Y)))
					r.drawPixel(0x2200FF, (int)(x+X), (int)(y+Y));
			}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean intersects(GameEntity entity) {
		return shape.includes(entity.getPosition());
	}

	@Override
	public void handleCollision(GameEntity entity) {
		entity.setSpeed(10);
	}

}