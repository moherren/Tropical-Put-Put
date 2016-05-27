package course;

import java.awt.Graphics;
import java.util.ArrayList;

import entities.Entity;
import entities.GameEntity;
import graphics.Render2D;
import visibleObjects.VisibleObject;
import geometry.Circle;
import geometry.Line;
import geometry.Polygon;
import geometry.Rectangle;
import geometry.Vector2D;

public class Windmill implements VisibleObject,Obstacle,Entity{

//	private ArrayList<Polygon> fins;
	private ArrayList<Wall> fins;
	private Circle middle;
	private double turnSpeed=0.5;//degrees
	private double rotation=0;//degrees
	private double elasticity=0.3;
	private Vector2D center;
	
	public Windmill(Vector2D center,double finLength,double finWidth){
		this.center=center;
		fins=new ArrayList<Wall>();
		fins.add(new Wall(new Rectangle(center.x-finWidth-finLength,center.y-finWidth/2,finLength,finWidth)));
		fins.add(new Wall(new Rectangle(center.x+finWidth,center.y-finWidth/2,finLength,finWidth)));
		fins.add(new Wall(new Rectangle(center.x-finWidth/2,center.y-finWidth-finLength,finWidth,finLength)));
		fins.add(new Wall(new Rectangle(center.x-finWidth/2,center.y+finWidth,finWidth,finLength)));
		middle=new Circle(center.x,center.y,finWidth*1.5);
	}
	
	@Override
	public boolean intersects(GameEntity entity) {
		for(Wall w:fins){
			if(w.intersects(entity))
				return true;
		}
		if(middle.intersects(entity.shape))
			return true;
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
		if(middle.intersects(entity.shape)){
			Vector2D normal=entity.getPosition().sub(center);
			Vector2D toEntity=center.sub(entity.getPosition());
			Vector2D relativeVelocity=entity.getVelocity().add(toEntity.perpendicular().mult(Math.toRadians(turnSpeed)));
			Vector2D impulse=normal.normalize().mult(Math.abs(relativeVelocity.dot(normal.negative()))/normal.magnitude()*(1+elasticity));
			entity.applyImpulse(impulse);
		}
		
	}

	@Override
	public void render(Graphics g) {
		for(Wall w:fins){
			w.render(g);
		}
		
	}

	public void renderFin(Render2D r,Wall fin){
		Polygon shape= fin.getShape();
		for(int x=fin.minX;x<=fin.maxX;x++)
			for(int y=fin.minY;y<=fin.maxY;y++){
				if(shape.includes(new Vector2D(x+fin.X,y+fin.Y)))
					r.pixels[(int)(x+fin.X)+r.width*(int)(y+fin.Y)]=0xff0000;
			}
		Line[] line=shape.toLines();
		Render2D.drawLine(r, 2, line[0].getPoint1().x, line[0].getPoint1().y, line[0].getPoint2().x, line[0].getPoint2().y);
		Render2D.drawLine(r, 2, line[1].getPoint1().x, line[1].getPoint1().y, line[1].getPoint2().x, line[1].getPoint2().y);
		Render2D.drawLine(r, 2, line[2].getPoint1().x, line[2].getPoint1().y, line[2].getPoint2().x, line[2].getPoint2().y);
		Render2D.drawLine(r, 2, line[3].getPoint1().x, line[3].getPoint1().y, line[3].getPoint2().x, line[3].getPoint2().y);
	}
	
	@Override
	public void render(Render2D r) {
		for(Wall w:fins){
			renderFin(r,w);
		}
		int X=(int) center.x,Y=(int) center.y;
		
		int length=(int) middle.getRadius();
		double perimeter=(length)*Math.PI*2;
			for(double angle=Math.PI/2.0;angle<=Math.PI*3/2.0;angle+=Math.PI*2/perimeter){
				
				int x=(int) (Math.cos(angle)*length+X);
				int y=(int) (Math.sin(angle)*length+Y);
				int lineLength=(int) (Math.cos(angle)*2*length);
				r.drawPixel(1,x,y);
				for(int line=-1;line>lineLength;line--)
					if(r.pixels[x+y*r.width-line]!=1)
						r.drawPixel(0xffffff, x-line,y);
				r.drawPixel(1,x-lineLength,y);
				
			}
	}
	
	@Override
	public void update(double t){
		rotation+=turnSpeed*t;
		for(Wall w:fins){
			w.getShape().rotateAbout(center,Math.toRadians(turnSpeed)*t);
			w.calculateRenderBounds();
			w.calculateNormals();
		}
		
	}

}
