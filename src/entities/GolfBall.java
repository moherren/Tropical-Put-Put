package entities;

import course.GolfCourse;
import geometry.Circle;
import geometry.Shape;
import geometry.Vector2D;

public class GolfBall extends GameEntity{

	public static double BALL_RADIUS=20;
	private GolfCourse course;
		
	public GolfBall(Vector2D position, GolfCourse course) {
		super(position, new Circle(BALL_RADIUS), course,-1,-1);
		this.course=course;
	}
	
	public void update(double time){
		super.update(time);
		velocity.iadd(course.getTiltDirection().mult(course.getGravity()*Math.sin(Math.toRadians(course.getTiltAngle()))*time));
		
		course.handleCollisions(this);
		if(!velocity.isZeroed()){
			Vector2D frictionDirection=velocity.normalize().negative();
			double frictionStrength=course.getCoefFriction()*course.getGravity()*Math.cos(Math.toRadians(course.getTiltAngle()))*time;
			if(velocity.magnitudeSq()<frictionStrength*frictionStrength)
				velocity.zero();
			else{
				velocity.iadd(frictionDirection.mult(frictionStrength));
			}
		}
	}
	
	public void hit(double strength,double dx,double dy){
		applyImpulse(new Vector2D(dx,dy).normalize().mult(strength));
	}
	

}
