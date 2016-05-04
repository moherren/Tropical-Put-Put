package course;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import geometry.Line;
import geometry.Polygon;
import geometry.Rectangle;
import geometry.Shape;
import geometry.Vector2D;
import graphics.Render2D;
import visibleObjects.TempGraphics;
import visibleObjects.VisibleObject;
import entities.GameEntity;

public class Wall implements VisibleObject,Obstacle,TempGraphics{

	protected Line[] sides;
	protected Polygon shape;
	protected Vector2D[] normals;
	protected Color color;
	private int col=0xffffff;
	public double elasticity=0.3;//percentage of speed that is conserved in collisions
	int minX=Integer.MAX_VALUE,minY=Integer.MAX_VALUE,maxX=Integer.MIN_VALUE,maxY=Integer.MIN_VALUE;
	double X,Y;
	
	public Wall(Polygon shape,Color c) {
		this.shape=shape;
		calculateNormals();
		color=c;
		calculateRenderBounds();
		int r=c.getRed();
		int b=c.getBlue();
		int g=c.getGreen();
		int rgb = r;
		rgb = (rgb << 8) + g;
		rgb = (rgb << 8) + b;
		col=rgb;
	}

	public Wall(Polygon shape){
		this.shape=shape;
		calculateNormals();
		color=Color.black;
		calculateRenderBounds();
		Color c=new Color(1);
		int r=c.getRed();
		int b=c.getBlue();
		int g=c.getGreen();
		int rgb = r;
		rgb = (rgb << 8) + g;
		rgb = (rgb << 8) + b;
		col=rgb;
	}
	
	public Wall(int x,int y,int width,int height){
		this.shape=new Rectangle(x,y,width,height);
		calculateNormals();
		color=Color.black;
		calculateRenderBounds();
		Color c=new Color(1);
		int r=c.getRed();
		int b=c.getBlue();
		int g=c.getGreen();
		int rgb = r;
		rgb = (rgb << 8) + g;
		rgb = (rgb << 8) + b;
		col=rgb;
	}
	
	public void render(Render2D r){
		//Still needs to be added
		for(int x=minX;x<=maxX;x++)
			for(int y=minY;y<=maxY;y++){
				if(shape.includes(new Vector2D(x+X,y+Y)))
					r.pixels[(int)(x+X)+r.width*(int)(y+Y)]=GolfCourse.tilesA[0].pixels[((int)(x+X)%GolfCourse.tilesA[1].width)+((int)(y+Y)%GolfCourse.tilesA[1].height)*GolfCourse.tilesA[1].width];
			}
	}
	
	public void render(Graphics g){
		shape.render(g);
	}
	
	public Polygon getShape(){
		return shape;
	}
	
	public boolean contains(Vector2D point){
		return shape.includes(point);
	}
	
	public boolean intersects(GameEntity entity){
		return shape.intersects(entity.shape);
	}
	
	public void handleCollision(GameEntity entity){
		Vector2D normal=getNormal(entity.shape);
		if(normal!=null&&!normal.isZeroed()){
			Vector2D impulse=normal.normalize().mult(Math.abs(entity.getVelocity().dot(normal.negative()))/normal.magnitude()*(1+elasticity));
			entity.applyImpulse(impulse);
		}
	}
	
	public Vector2D getNormal(Shape other){
		if(!shape.intersects(other))
			return new Vector2D(0,0);
		else{
			Vector2D result=new Vector2D(0,0);
			for(int i=0;i<sides.length;i++){
				if(other.intersects(sides[i]))
					result.iadd(normals[i]);
			}
			//if shape intersects a side, return resulting normal
			if(!result.isZeroed()){
				return result.normalize();
			}
			else{
			//normal along vector from center to shape if shape is inside wall
				shape.intersects(other);
				result=other.getPosition().sub(shape.getPosition());
				if(!result.isZeroed()){
					return result.normalize();
				}
				else
					return new Vector2D(1,0);//default
			}
		}
		
	}
	
	public void calculateNormals(){
		sides=shape.toLines();
		normals=calculateNormals(shape);
	}
	
	public static Vector2D[] calculateNormals(Polygon shape){
		Line[] sides=shape.toLines();
		Vector2D[] normals=new Vector2D[sides.length];
		
		//calculate normals of sides
		for(int i=0;i<sides.length;i++){
			Vector2D ab=sides[i].getPoint2().sub(sides[i].getPoint1());//find vector from a to b
			Vector2D ac;//find vector from a to c
			if(i<sides.length-1)
				ac=sides[i+1].getPoint2().sub(sides[i].getPoint1());
			else
				ac=sides[0].getPoint1().sub(sides[i].getPoint1());
			Vector2D n=new Vector2D(ab.y,-ab.x);//take perpendicular to ab
			if(n.dot(ac)>0){
				normals[i]=n.negative().normalize();
			}
			else
				normals[i]=n.normalize();
			//if normal points inward, reverse it
			if(shape.includes(sides[i].getPoint1().add(ab.div(2)).add(normals[i].mult(0.01))))
					normals[i]=normals[i].negative();
		}
		return normals;
	}
	
	public double getElasticity(){
		return elasticity;
	}
	
	public void calculateRenderBounds(){
		for(Vector2D v:shape.points){
			minX=(int) Math.min(minX, v.x);
			minY=(int) Math.min(minY, v.y);
			maxX=(int) Math.max(maxX, v.x);
			maxY=(int) Math.max(maxY, v.y);
		}
		Vector2D v=shape.getPosition();
		X=v.x;
		Y=v.y;
		
	}
}
