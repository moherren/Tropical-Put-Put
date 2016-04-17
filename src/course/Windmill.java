package course;

import java.awt.Graphics;
import java.util.ArrayList;

import entities.Entity;
import entities.GameEntity;
import graphics.Render2D;
import visibleObjects.VisibleObject;
import geometry.Polygon;
import geometry.Rectangle;
import geometry.Shape;
import geometry.Vector2D;

public class Windmill implements VisibleObject,Obstacle,Entity{

//	private ArrayList<Polygon> fins;
	private ArrayList<Wall> fins;
	private double turnSpeed=1;//degrees
	private double rotation=0;//degrees
	private double elasticity=0.3;
	private Vector2D center;
	
	public Windmill(Vector2D center,double finLength,double finWidth){
		this.center=center;
		fins=new ArrayList<Wall>();
		fins.add(new Wall(new Rectangle(center.x-finWidth-finLength/2,center.y,finLength,finWidth)));
		fins.add(new Wall(new Rectangle(center.x+finWidth+finLength/2,center.y,finLength,finWidth)));
		fins.add(new Wall(new Rectangle(center.x,center.y-finWidth-finLength/2,finWidth,finLength)));
		fins.add(new Wall(new Rectangle(center.x,center.y+finWidth+finLength/2,finWidth,finLength)));
	}
	
	@Override
	public boolean intersects(GameEntity entity) {
		for(Wall w:fins){
			if(w.intersects(entity))
				return true;
		}
		return false;
	}

	@Override
	public void handleCollision(GameEntity entity) {
		for(Wall w:fins){
			Vector2D normal=w.getNormal(entity.shape);
			if(normal!=null&&!normal.isZeroed()){
				Vector2D toEntity=center.sub(entity.getPosition());
				Vector2D relativeVelocity=entity.getVelocity().add(toEntity.perpendicular().mult(Math.toRadians(turnSpeed)));
				Vector2D impulse=normal.normalize().mult(Math.abs(relativeVelocity.dot(normal.negative()))/normal.magnitude()*(1+elasticity));
				entity.applyImpulse(impulse);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		for(Wall w:fins){
			w.render(g);
		}
	}

	@Override
	public void render(Render2D r) {
		for(Wall w:fins){
			w.render(r);
		}
	}
	
	public void update(double t){
		rotation+=turnSpeed*t;
		for(Wall w:fins){
			w.getShape().rotateAbout(center,Math.toRadians(turnSpeed)*t);
			w.calculateRenderBounds();
			w.calculateNormals();
		}
		
	}

}
