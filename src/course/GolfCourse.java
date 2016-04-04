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
import menu.GUI;
import visibleObjects.TempGraphics;
import visibleObjects.VisibleObject;

public class GolfCourse implements VisibleObject, TempGraphics{

	private ArrayList<Entity> entities;
	private ArrayList<Obstacle> obstacles;
	private ArrayList<Surface> surfaces;
	private ArrayList<Hole> holes;
	public long time=0;
	public double mu;//coefficient of friction
	public double g;//gravity
	
	public double tiltAngle=0;//in degrees
	public Vector2D tiltDirection;
	public double targetTilt=0;//in degrees
	public Vector2D targetTiltDirection=new Vector2D(1,0);
	public double maxTilt=20;//in degrees
	public double tiltAngleSpeed=1;
	public double tiltDirectionSpeed=1;
	
	public Vector2D ballStart;
	public int par;
	
	public static Render tiles=Texture.loadBitmap("textures/tiles.png");
	public static Render tilesA[]=new Render[3];
	
	public GolfCourse(Vector2D ballStart,int par) {
		entities=new ArrayList<Entity>();
		obstacles=new ArrayList<Obstacle>();
		surfaces=new ArrayList<Surface>();
		holes=new ArrayList<Hole>();
		mu=.20;
		g=0.1;
		this.ballStart=ballStart;
		this.par=par;
		tiltDirection=new Vector2D(1,0);
		for(int i=0;i<tilesA.length;i++){
			tilesA[i]=Texture.getSpriteSheet(tiles, 50, 50, i);
		}
	}

	public GolfCourse(ArrayList<Entity> entities,ArrayList<Obstacle> obstacles,ArrayList<Surface> surfaces,ArrayList<Hole> holes,Vector2D ballStart,int par){
		this.entities=entities;
		this.obstacles=obstacles;
		this.surfaces=surfaces;
		this.holes=holes;
		mu=.20;
		g=0.1;
		this.ballStart=ballStart;
		this.par=par;
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
	
	public Vector2D getTilt(){
		return getTiltDirection().mult(getGravity()*Math.sin(Math.toRadians(getTiltAngle())));
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
	
	public ArrayList<Surface> getSurfaces(){
		return surfaces;
	}
	
	public void setTilt(double angle,Vector2D direction){
		targetTilt=angle;
		targetTiltDirection=direction;
	}
	
	public void update(double time){
		//move tilt towards targetTilt
		this.time+=time;
		ArrayList<Entity> temp=new ArrayList<Entity>(entities);
		for(Entity e:temp){
			e.update(time);
		}
	}
	
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	public void addObstacle(Obstacle o){
		obstacles.add(o);
	}
	
	public void addSurface(Surface s){
		surfaces.add(s);
	}
	
	public void addHole(Hole h){
		holes.add(h);
	}
	
	public void render(Render2D r){
		
		for(int x=0;x<r.width;x++)
			for(int y=0;y<r.height;y++)
				r.pixels[x+y*r.width]=tilesA[2].pixels[(x%tilesA[2].width)+(y%tilesA[2].height)*tilesA[2].width];
		for(Surface s:surfaces){
			s.render(r);
		}
		for(Hole h:holes){
			h.render(r);
		}
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
	
	public boolean scored(GolfBall b){
		for(Hole h:holes){
			if(h.contains(b))
				return true;
		}
		return false;
	}

	@Override
	public void render(Graphics g) {
		for(Surface s:surfaces){
			s.render(g);
		}
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