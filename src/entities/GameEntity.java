package entities;

import geometry.Shape;
import geometry.Vector2D;
import graphics.Render2D;

import java.awt.Color;
import java.awt.Graphics;

import course.GolfCourse;
import visibleObjects.TempGraphics;
import visibleObjects.VisibleObject;

public class GameEntity extends MobileEntity implements VisibleObject,TempGraphics{

	public Vector2D heading;
	public Shape shape;
	public Color c;
	public GolfCourse course;
	protected double id;
	
	public GameEntity(Vector2D position,Shape shape,GolfCourse world,double maxSpeed,
			double maxTurnSpeed){
		super(position,new Vector2D(0,0),maxSpeed,maxTurnSpeed);
		heading=new Vector2D(1,0);
		this.shape=shape;
		shape.setPosition(position);
		this.course=world;
		c=Color.blue;
	}
	
	public GameEntity(double x,double y,Shape shape,GolfCourse world,double maxSpeed,
			double maxTurnSpeed){
		super(new Vector2D(x,y),new Vector2D(0,0),maxSpeed,maxTurnSpeed);
		heading=new Vector2D(1,0);
		this.shape=shape;
		shape.setPosition(position);
		id=Math.random();
		this.course=world;
		c=Color.blue;
	}
	
	public GameEntity(Vector2D position,Shape shape,GolfCourse world,double maxSpeed,
			double maxTurnSpeed,Color c){
		super(position,new Vector2D(0,0),maxSpeed,maxTurnSpeed);
		heading=new Vector2D(1,0);
		this.shape=shape;
		shape.setPosition(position);
		this.course=world;
		this.c=c;
	}
	
	public GameEntity(double x,double y,Shape shape,GolfCourse world,double mass,double maxSpeed,
			double maxTurnSpeed,Color c){
		super(new Vector2D(x,y),new Vector2D(0,0),maxSpeed,maxTurnSpeed);
		heading=new Vector2D(1,0);
		this.shape=shape;
		shape.setPosition(position);
		id=Math.random();
		this.course=world;
		this.c=c;
	}
	
	public void setHeading(Vector2D heading){
		this.heading=heading;
	}
	
	@Override
	public void update(double time){
		super.update(time);
		shape.setPosition(position);
	}

	public Vector2D getHeading(){
		return heading.normalize();
	}
	
	public double getSpeed(){
		return velocity.magnitude();
	}
	
	public boolean contains(Shape s){
		return shape.intersects(s);
	}

	@Override
	public void render(Render2D r) {
		//Graphics for this not yet added
	}
	
	@Override
	public void render(Graphics g){
		g.setColor(c);
		shape.render(g);
	}
}

