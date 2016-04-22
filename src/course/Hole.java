package course;

import entities.GolfBall;
import geometry.Circle;
import geometry.Vector2D;
import graphics.Render2D;
import visibleObjects.VisibleObject;

public class Hole implements VisibleObject{

	private Vector2D position;
	private double radius;
	private Circle shape;
	
	public Hole(Vector2D p, double r){
		position=p;
		radius=r;
		shape=new Circle(p.x,p.y,r);
	}
	
	@Override
	public void render(Render2D r) {
		Vector2D v=position;
		int X=(int) v.x,Y=(int) v.y;
		
		int length=(int) radius;
		double perimeter=(length)*Math.PI*2;
		for(double angle=Math.PI/2.0;angle<=Math.PI*3/2.0;angle+=Math.PI*2/perimeter){
			
			int x=(int) (Math.cos(angle)*length+X);
			int y=(int) (Math.sin(angle)*length+Y);
			int lineLength=(int) (Math.cos(angle)*2*length);
			r.drawPixel(2,x,y);
			for(int line=-1;line>lineLength;line--)
				if(r.pixels[x+y*r.width-line]!=1)
					r.drawPixel(2, x-line,y);
			
		}
	}
	
	public boolean contains(GolfBall ball){
		if(ball.getPosition().distanceTo(position)<=radius&&radius>=GolfBall.BALL_RADIUS)
			return true;
		else
			return false;
	}
	
	public Circle getShape(){
		return shape;
	}

}
