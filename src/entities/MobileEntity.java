package entities;

import geometry.Vector2D;

public class MobileEntity implements Entity{

	protected Vector2D velocity;
	protected Vector2D acceleration=new Vector2D(0,0);
	protected Vector2D position;
	protected double maxVelocity;
	protected double maxTurnSpeed;
	
	public MobileEntity(double x,double y){
		position=new Vector2D(x,y);
		velocity=new Vector2D(0,0);
	}
	
	public MobileEntity(double x,double y,Vector2D velocity){
		position=new Vector2D(x,y);
		this.velocity=velocity;
	}
	
	public MobileEntity(Vector2D position){
		this.position=position;
		velocity=new Vector2D(0,0);
	}
	
	public MobileEntity(Vector2D position,Vector2D velocity,double maxVelocity,
			double maxTurnSpeed){
		this.position=position;
		this.velocity=velocity;
		this.maxVelocity=maxVelocity;
		this.maxTurnSpeed=maxTurnSpeed;
	}

	@Override
	public void update(double time){
		position.iadd(velocity.mult(time));
		velocity.iadd(acceleration.mult(time));
		acceleration.zero();
		if(maxVelocity>0)
			velocity=velocity.truncate(maxVelocity);
	}
	
	public void applyForce(Vector2D force){
		acceleration.iadd(force);
	}
	
	public Vector2D getVelocity(){
		return velocity;
	}
	
	public Vector2D getPosition(){
		return position;
	}
	
	/**
	 * adds to velocity
	 * @param impulse
	 */
	public void applyImpulse(Vector2D impulse){
		velocity.iadd(impulse);
	}
	
	public double getMaxVelocity(){
		return maxVelocity;
	}
	
	public void setSpeed(double speed){
		velocity=velocity.normalize().mult(speed);
	}
}




