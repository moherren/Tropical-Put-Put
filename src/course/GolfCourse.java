package course;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import entities.Entity;
import entities.GameEntity;
import geometry.Circle;
import geometry.Vector2D;
import graphics.Render2D;
import visibleObjects.TempGraphics;
import visibleObjects.VisibleObject;

public class GolfCourse implements VisibleObject, TempGraphics{

	private ArrayList<Entity> entities;
	private ArrayList<Obstacle> obstacles;
	public long time=0;
	public double mu;//coefficient of friction
	public double g;//gravity
	public double tiltAngle;//in degrees
	public Vector2D tiltDirection;
	
	public GolfCourse() {
		entities=new ArrayList<Entity>();
		obstacles=new ArrayList<Obstacle>();
		mu=.1;
		g=0.1;
		tiltAngle=90;
		tiltDirection=new Vector2D(1,0);
	}
	
	public GolfCourse(ArrayList<Entity> entities,ArrayList<Obstacle> obstacles){
		this.entities=entities;
		this.obstacles=obstacles;
		mu=.1;
		g=0.1;
		tiltAngle=90;
		tiltDirection=new Vector2D(1,0);
	}
	
	public double getCoefFriction(){
		return mu;
	}
	
	public double getTiltAngle(){
		return tiltAngle;
	}
	
	public Vector2D getTiltDirection(){
		return tiltDirection.normalize();
	}
	
	public double getGravity(){
		return g;
	}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
	public ArrayList<Obstacle> getObstacles(){
		return obstacles;
	}
	
	public void update(double time){
		this.time+=time;
		ArrayList<Entity> temp=new ArrayList<Entity>(entities);
		for(Entity e:temp){
			e.update(time);
		}
	}
	
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	public void addWall(Obstacle o){
		obstacles.add(o);
	}
	
	public void render(Render2D r){
		Arrays.fill(r.pixels, 0x01A611);
		for(Entity e:entities){
			if(e instanceof VisibleObject){
				((VisibleObject)e).render(r);
			}
		}
		for(Obstacle o:obstacles){
			o.render(r);
		}
			
	}

	public void removeEntity(Entity e) {
		entities.remove(e);
	}

	public void handleCollisions(GameEntity e) {
		for(Obstacle o:obstacles){
			if(o.intersects(e)){
				o.handleCollision(e);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		for(Entity e:entities){
			if(e instanceof TempGraphics){
				((TempGraphics)e).render(g);
			}
		}
		for(Obstacle o:obstacles){
			o.render(g);
		}
		
	}

}
