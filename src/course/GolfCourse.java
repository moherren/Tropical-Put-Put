package course;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import entities.Entity;
import entities.GameEntity;
import entities.GolfBall;
import geometry.Circle;
import geometry.Vector2D;
import graphics.Render;
import graphics.Render2D;
import graphics.Texture;
import visibleObjects.TempGraphics;
import visibleObjects.VisibleObject;

public class GolfCourse implements VisibleObject, TempGraphics{

	private ArrayList<Entity> entities;
	private ArrayList<Obstacle> obstacles;
	private ArrayList<Surface> surfaces;
	public long time=0;
	public double mu;//coefficient of friction
	public double g;//gravity
	public double tiltAngle=0;//in degrees
	public Vector2D tiltDirection;
	static Render tiles;
	static Render tilesA[]=new Render[3];
	
	public GolfCourse() {
		entities=new ArrayList<Entity>();
		obstacles=new ArrayList<Obstacle>();
		surfaces=new ArrayList<Surface>();
		mu=.20;
		g=0.1;
		tiltDirection=new Vector2D(1,0);
		tiles=Texture.loadBitmap("textures/tiles.png");
		for(int i=0;i<tilesA.length;i++){
			tilesA[i]=Texture.getSpriteSheet(tiles, 50, 50, i);
		}
	}
	
	public GolfCourse(ArrayList<Entity> entities,ArrayList<Obstacle> obstacles,ArrayList<Surface> surfaces){
		this.entities=entities;
		this.obstacles=obstacles;
		this.surfaces=surfaces;
		mu=.20;
		g=0.1;
		tiltDirection=new Vector2D(1,0);
		tiles=Texture.loadBitmap("textures/tiles.png");
		for(int i=0;i<tilesA.length;i++){
			tilesA[i]=Texture.getSpriteSheet(tiles, 50, 50, i);
		}
	}
	
	public double getCoefFriction(Vector2D position){
		for(Surface s:surfaces)
			if(s.contains(position))
				return s.getCoefFriction();
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
		for(int x=0;x<r.width;x++)
			for(int y=0;y<r.height;y++)
				r.pixels[x+y*r.width]=tilesA[2].pixels[(x%tilesA[2].width)+(y%tilesA[2].height)*tilesA[2].width];
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
