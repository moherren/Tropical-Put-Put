package course;

import entities.Entity;
import entities.GameEntity;
import geometry.Line;
import geometry.Polygon;
import geometry.Vector2D;
import graphics.Render2D;

public class MovingWall extends Wall implements Entity{

	private Vector2D velocity;
	private Vector2D position;
	private Line path;
	boolean movingTowardsP1;
	private double speed=0.4;
	private double turnTime;
	private double totalTime;
	
	public MovingWall(Polygon shape,Line path) {
		super(shape);
		this.path=path;
		position=shape.getPosition();
		Vector2D toP1=path.getPoint2().sub(position);
		Vector2D toP2=path.getPoint2().sub(position);
		if(toP1.magnitudeSq()>toP2.magnitudeSq()){
			velocity=toP1.normalize().mult(speed);
			movingTowardsP1=true;
		}
		else{
			velocity=toP2.normalize().mult(speed);
			movingTowardsP1=false;
		}
		totalTime=0;
		turnTime=toP2.magnitude()/speed;
	}

	@Override
	public void update(double time) {
		totalTime+=time;
		if(movingTowardsP1&&turnTime<totalTime){
			velocity=path.getPoint2().sub(position).normalize().mult(speed);
			movingTowardsP1=false;
			turnTime=path.getPoint2().sub(position).magnitude()/speed+totalTime;
			System.out.println(turnTime);
		}
		else if(!movingTowardsP1&&turnTime<totalTime){
			velocity=path.getPoint1().sub(position).normalize().mult(speed);
			movingTowardsP1=true;
			turnTime=path.getPoint1().sub(position).magnitude()/speed+totalTime;
			System.out.println(turnTime);
		}
		position.iadd(velocity.mult(time));
		shape.setPosition(position);
		sides=shape.toLines();
	}
	
	public void render(Render2D r){
		for(int x=minX;x<maxX;x++)
			for(int y=minY;y<maxY;y++){
				if(shape.includes(new Vector2D(x+position.x,y+position.y)))
					r.pixels[(int)(x+position.x)+r.width*(int)(y+position.y)]=GolfCourse.tilesA[0].pixels[((int)(x+X)%GolfCourse.tilesA[1].width)+((int)(y+Y)%GolfCourse.tilesA[1].height)*GolfCourse.tilesA[1].width];
			}
	}
	
	public void handleCollision(GameEntity entity){
		Vector2D normal=getNormal(entity.shape);
		if(normal!=null&&!normal.isZeroed()){
			Vector2D relativeVelocity=entity.getVelocity().sub(velocity);
			Vector2D impulse=normal.normalize().mult(Math.abs(relativeVelocity.dot(normal.negative()))/normal.magnitude()*(1+elasticity));
			entity.applyImpulse(impulse);
		}
	}

}
