package entities;

import visibleObjects.VisibleObject;
import course.GolfCourse;
import geometry.Circle;
import geometry.Shape;
import geometry.Vector2D;
import graphics.Display;
import graphics.Render2D;

public class GolfBall extends GameEntity implements VisibleObject{

	public static double BALL_RADIUS=7;
	private GolfCourse course;
		
	public GolfBall(Vector2D position, GolfCourse course) {
		super(position, new Circle(BALL_RADIUS), course,-1,-1);
		this.course=course;
	}
	
	public void update(double time){
		super.update(time);
		velocity.iadd(course.getTiltDirection().mult(course.getGravity()*Math.sin(Math.toRadians(course.getTiltAngle()))*time));
		if(!velocity.isZeroed()){
			Vector2D frictionDirection=velocity.normalize().negative();
			double frictionStrength=course.getCoefFriction()*course.getGravity()*Math.cos(Math.toRadians(course.getTiltAngle()))*time;
			if(velocity.magnitudeSq()<frictionStrength*frictionStrength)
				velocity.zero();
			else{
				velocity.iadd(frictionDirection.mult(frictionStrength));
			}
		}
		course.handleCollisions(this);
		if(velocity.magnitudeSq()<0.01*0.01){
			velocity.zero();
		}
	}
	
	public void render(Render2D r){
		Vector2D v=position;
		int X=(int) v.x,Y=(int) v.y;
		
		int length=(int) BALL_RADIUS;
		double perimeter=(length)*Math.PI*2;
			for(double angle=Math.PI/2.0;angle<=Math.PI*3/2.0;angle+=Math.PI*2/perimeter){
				
				int x=(int) (Math.cos(angle)*length+X);
				int y=(int) (Math.sin(angle)*length+Y);
				int lineLength=(int) (Math.cos(angle)*2*length);
				r.drawPixel(1,x,y);
				for(int line=-1;line>lineLength;line--)
					if(r.pixels[x+y*r.width-line]!=1)
						r.drawPixel(0xffffff, x-line,y);
				r.drawPixel(1,x-lineLength,y);
				
			}
	}
	
	public void putt(double strength, Vector2D direction){
		applyImpulse(direction.normalize().mult(strength));
	}
}
