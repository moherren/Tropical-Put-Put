package course;

import geometry.Line;
import geometry.Polygon;
import geometry.Rectangle;
import geometry.Vector2D;

public class Levels {
	
	public static GolfCourse getCourse1(){
		GolfCourse gc=new GolfCourse(new Vector2D(100,250), 2);
		
		gc.addObstacle(new Wall(new Rectangle(20,100,40,300)));
		gc.addObstacle(new Wall(new Rectangle(20,60,760,40)));
		gc.addObstacle(new Wall(new Rectangle(20,400,760,40)));
		gc.addObstacle(new Wall(new Rectangle(740,100,40,300)));
		
		gc.addObstacle(new Wall(new Rectangle(220,225,360,10)));
		gc.addObstacle(new Wall(new Rectangle(220,275,360,10)));
		
		gc.addSurface(new Grass(new Rectangle(40,100,730,300)));
		gc.addHole(new Hole(new Vector2D(650,250),8));
		
		return gc;
	}
	
	public static GolfCourse getCourse2(){
		GolfCourse gc=new GolfCourse(new Vector2D(650,300), 3);
		
		gc.addObstacle(new Wall(20,100,40,300));
		gc.addObstacle(new Wall(20,60,280,40));
		
		gc.addObstacle(new Wall(290,200,490,40));
		gc.addObstacle(new Wall(290,60,40,150));
		
		gc.addObstacle(new Wall(20,400,760,40));
		gc.addObstacle(new Wall(740,240,40,160));
		
		gc.addObstacle(new Wall(new Polygon(new double[]{290,290,390},new double[]{210,310,210})));
		gc.addObstacle(new Wall(new Polygon(new double[]{59,210,59},new double[]{400,400,250})));
		gc.addObstacle(new Wall(155,180,40,20));
		
		gc.addSurface(new Sand(new Rectangle(40,100,260,230)));
		gc.addSurface(new Grass(new Rectangle(40,170,260,230)));
		gc.addSurface(new Grass(new Rectangle(260,240,520,200)));
		
		gc.addHole(new Hole(new Vector2D(175,220),8));
		
		return gc;
	}
	
	public static GolfCourse getCourse3(){
		GolfCourse gc=new GolfCourse(new Vector2D(140,160), 3);
		gc.addObstacle(new Wall(40,40,40,240));
		gc.addObstacle(new Wall(80,40,280,40));
		gc.addObstacle(new Wall(new Polygon(new double[]{360,400,400,360},new double[]{40,40,160,120})));
		gc.addObstacle(new Wall(400,120,360,40));
		gc.addObstacle(new Wall(720,160,40,240));
		gc.addObstacle(new Wall(240,400,520,40));
		gc.addObstacle(new Wall(new Polygon(new double[]{240,280,280,240},new double[]{280,320,400,400})));
		gc.addObstacle(new Wall(40,280,200,40));
		gc.addObstacle(new Wall(new Polygon(new double[]{280,300,280,260},new double[]{220,240,260,240})));
		gc.addObstacle(new Wall(new Polygon(new double[]{400,420,400,380},new double[]{300,320,340,320})));
		gc.addObstacle(new Wall(new Polygon(new double[]{480,500,480,460},new double[]{220,240,260,240})));
		gc.addObstacle(new Wall(new Polygon(new double[]{640,660,640,620},new double[]{220,240,260,240})));

		gc.addSurface(new Grass(new Rectangle(80,80,280,200)));
		gc.addSurface(new Grass(new Rectangle(240,120,480,280)));
		gc.addSurface(new Stone(new Rectangle(540,260,120,120)));
		
		gc.addHole(new Hole(new Vector2D(600,320),8));
		
		return gc;
	}
	
	public static GolfCourse getCourse5(){
		GolfCourse gc=new GolfCourse(new Vector2D(160,240), 3);
		gc.addObstacle(new Wall(40,40,40,400));
		gc.addObstacle(new Wall(80,40,640,40));
		gc.addObstacle(new Wall(720,40,40,400));
		gc.addObstacle(new Wall(80,400,640,40));
		
		gc.addObstacle(new Wall(240,80,40,40));
		gc.addObstacle(new Wall(520,80,40,40));
		gc.addObstacle(new Wall(380,140,40,80));
		
		gc.addObstacle(new MovingWall(new Rectangle(280,180,40,40),new Line(new Vector2D(300,140),new Vector2D(300,220))));
		gc.addObstacle(new MovingWall(new Rectangle(280,260,40,40),new Line(new Vector2D(300,260),new Vector2D(300,340))));
		
		gc.addObstacle(new Wall(240,360,40,40));
		gc.addObstacle(new Wall(520,360,40,40));
		gc.addObstacle(new Wall(380,260,40,80));
		
		gc.addObstacle(new MovingWall(new Rectangle(480,180,40,40),new Line(new Vector2D(500,140),new Vector2D(500,220))));
		gc.addObstacle(new MovingWall(new Rectangle(480,260,40,40),new Line(new Vector2D(500,260),new Vector2D(500,340))));
		
		gc.addSurface(new Grass(new Rectangle(80,80,660,340)));
		
		gc.addHole(new Hole(new Vector2D(640,240),8));
		return gc;
	}
	
	public static GolfCourse getCourse6(){
		GolfCourse gc=new GolfCourse(new Vector2D(160,240), 3);
		gc.addObstacle(new Wall(160,40,40,80));
		gc.addObstacle(new Wall(200,40,560,40));
		gc.addObstacle(new Wall(720,80,40,280));
		gc.addObstacle(new Wall(160,40,40,80));
		gc.addObstacle(new Wall(520,360,240,40));
		gc.addObstacle(new Wall(520,160,40,200));
		gc.addObstacle(new Wall(80,280,440,40));
		gc.addObstacle(new Wall(40,120,40,200));
		gc.addObstacle(new Wall(80,120,120,40));
		
		gc.addObstacle(new MovingWall(new Polygon(new double[]{-20,20,20},new double[]{-20,-20,20},340,140),new Line(new Vector2D(340,140),new Vector2D(460,140))));
		gc.addObstacle(new MovingWall(new Polygon(new double[]{-20,20,20},new double[]{-20,-20,20},460,220),new Line(new Vector2D(460,220),new Vector2D(460,180))));
		gc.addObstacle(new Windmill(new Vector2D(640,140),40,5));
		
		
		gc.addSurface(new Grass(new Rectangle(80,160,440,120)));
		gc.addSurface(new Grass(new Rectangle(200,80,520,80)));
		gc.addSurface(new Grass(new Rectangle(560,160,160,200)));
		gc.addSurface(new Sand(new Polygon(new double[]{260,240,280,320,360,440},new double[]{280,220,160,140,240,280})));
		gc.addSurface(new Stone(new Polygon(new double[]{540,620,720,720,680,600},new double[]{280,200,180,320,360,360})));
		
		gc.addHole(new Hole(new Vector2D(640,280),8));
		return gc;
	}
}
