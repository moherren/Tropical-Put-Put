package course;

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
	
}
