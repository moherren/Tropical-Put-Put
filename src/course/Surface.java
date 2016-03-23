package course;

import java.awt.Graphics;

import geometry.Shape;
import geometry.Vector2D;
import graphics.Render2D;

public abstract class Surface {

	protected Shape shape;
	
	public boolean contains(Vector2D point){
		return shape.includes(point);
	}
	
	public abstract void render(Render2D r);
	
	public abstract void render(Graphics g);
	
	public abstract double getCoefFriction();
}
