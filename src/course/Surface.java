package course;

import java.awt.Graphics;

import geometry.Polygon;
import geometry.Shape;
import geometry.Vector2D;
import graphics.Render2D;
import visibleObjects.VisibleObject;

public abstract class Surface implements VisibleObject{
	
	protected Polygon shape;
	
	int minX=Integer.MAX_VALUE,minY=Integer.MAX_VALUE,maxX=Integer.MIN_VALUE,maxY=Integer.MIN_VALUE;
	double X,Y;
	
	public Surface(){
		for(Vector2D v:shape.points){
			minX=(int) Math.min(minX, v.x);
			minY=(int) Math.min(minY, v.y);
			maxX=(int) Math.max(maxX, v.x);
			maxY=(int) Math.max(maxY, v.y);
		}
		Vector2D v=shape.getPosition();
		X=v.x;
		Y=v.y;
	}
	
	public boolean contains(Vector2D point){
		return shape.includes(point);
	}
	
	public abstract void render(Render2D r);
	
	public abstract void render(Graphics g);
	
	public abstract double getCoefFriction();
}
