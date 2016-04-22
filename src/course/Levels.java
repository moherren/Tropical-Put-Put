package course;

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
}
