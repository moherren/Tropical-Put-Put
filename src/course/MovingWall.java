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
		}
		else if(!movingTowardsP1&&turnTime<totalTime){
			velocity=path.getPoint1().sub(position).normalize().mult(speed);
			movingTowardsP1=true;
			turnTime=path.getPoint1().sub(position).magnitude()/speed+totalTime;
		}
		position.iadd(velocity.mult(time));
		shape.setPosition(position);
		sides=shape.toLines();
	}
	
	public void renderUnderside(Render2D r){
		Vector2D p1=path.getPoint1(),p2=path.getPoint2();
		double direction=Math.atan2(p2.y-p1.y, p2.x-p1.x);
		double size=2;
		double[] xs=new double[]{p1.x+Math.cos(direction+Math.PI*3/4.0)*size,p1.x+Math.cos(direction-Math.PI*3/4.0)*size,p2.x+Math.cos(direction-Math.PI/4.0)*size,p2.x+Math.cos(direction+Math.PI/4.0)*size},
				ys=new double[]{p1.y+Math.sin(direction+Math.PI*3/4.0)*size,p1.y+Math.sin(direction-Math.PI*3/4.0)*size,p2.y+Math.sin(direction-Math.PI/4.0)*size,p2.y+Math.sin(direction+Math.PI/4.0)*size};
		Polygon poly1=new Polygon(xs,ys);
		Vector2D pos=poly1.getPosition();
		size=4;
		double[] oxs=new double[]{p1.x+Math.cos(direction+Math.PI*3/4.0)*size,p1.x+Math.cos(direction-Math.PI*3/4.0)*size,p2.x+Math.cos(direction-Math.PI/4.0)*size,p2.x+Math.cos(direction+Math.PI/4.0)*size},
				oys=new double[]{p1.y+Math.sin(direction+Math.PI*3/4.0)*size,p1.y+Math.sin(direction-Math.PI*3/4.0)*size,p2.y+Math.sin(direction-Math.PI/4.0)*size,p2.y+Math.sin(direction+Math.PI/4.0)*size};

		Polygon poly2=new Polygon(oxs,oys);
		int minx=(int)Math.min(p1.x-4,p2.x-4),miny=(int)Math.min(p1.y-4,p2.y-4),maxx=(int)Math.max(p1.x+4,p2.x+4),maxy=(int)Math.max(p1.y+4,p2.y+4);
		
		for(int x=minx;x<maxx;x++)
			for(int y=miny;y<maxy;y++){
				if(poly1.includes(new Vector2D(x-pos.x,y-pos.y)))
					r.pixels[x+y*r.width]=2;
				else if(poly2.includes(new Vector2D(x-pos.x,y-pos.y)))
					r.pixels[x+y*r.width]=0x999999;
			}
		
	}
	
	public void render(Render2D r){
		for(int x=minX;x<maxX;x++)
			for(int y=minY;y<maxY;y++){
				if(shape.includes(new Vector2D(x+position.x,y+position.y)))
					r.pixels[(int)(x+position.x)+r.width*(int)(y+position.y)]=0xff0000;
			}
		Line[] lines=shape.toLines();
		for(Line l:lines)
			Render2D.drawLine(r, 1, l.getPoint1().x, l.getPoint1().y, l.getPoint2().x, l.getPoint2().y);
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
