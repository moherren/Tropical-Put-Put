package course;

import geometry.Line;
import geometry.Polygon;
import geometry.Rectangle;
import geometry.Vector2D;

public class Levels {
	
	public static GolfCourse getCourse1(){
		GolfCourse gc=new GolfCourse(new Vector2D(160,220), 2);
		
		gc.addObstacle(new Wall(new Rectangle(80,80,640,40)));
		gc.addObstacle(new Wall(new Rectangle(80,120,40,200)));
		gc.addObstacle(new Wall(new Rectangle(80,320,640,40)));
		gc.addObstacle(new Wall(new Rectangle(680,120,40,200)));
		
		gc.addObstacle(new Wall(new Rectangle(320,200,160,40)));
		
		
		gc.addSurface(new Grass(new Rectangle(120,120,560,200)));
		gc.addHole(new Hole(new Vector2D(640,220),8));
		
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
	
	public static GolfCourse getCourse4(){
		GolfCourse gc=new GolfCourse(new Vector2D(120,320), 4);
		gc.addObstacle(new Wall(new Rectangle(40,40,40,360)));
		gc.addObstacle(new Wall(new Rectangle(80,40,640,40)));
		gc.addObstacle(new Wall(new Rectangle(720,40,40,280)));
		gc.addObstacle(new Wall(new Rectangle(240,200,40,200)));
		gc.addObstacle(new Wall(new Rectangle(280,200,240,40)));
		gc.addObstacle(new Wall(new Rectangle(520,200,40,120)));
		gc.addObstacle(new Wall(new Rectangle(80,360,160,40)));
		gc.addObstacle(new Wall(new Rectangle(560,280,160,40)));
		gc.addObstacle(new Wall(new Rectangle(340,120,40,80)));
		
		gc.addSurface(new Sand(new Rectangle(80,80,160,280)));
		gc.addSurface(new Sand(new Rectangle(240,80,480,120)));
		gc.addSurface(new Sand(new Rectangle(560,200,160,80)));
		gc.addSurface(new Grass(new Polygon(new double[]{80,80,100,220,360,400,460,580,660,660,620,560,560,600,600,560,480,440,400,360,280,240,160,120},
				new double[]{360,240,120,120,80,80,140,120,160,200,280,280,240,220,190,180,180,200,140,120,140,180,180,360})));
		
		gc.addHole(new Hole(new Vector2D(600,260),8));
		
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
		GolfCourse gc=new GolfCourse(new Vector2D(160,240), 2);
		
		gc.addObstacle(new Wall(40,160,40,160));
		gc.addObstacle(new Wall(80,80,40,120));
		gc.addObstacle(new Wall(80,280,40,120));
		gc.addObstacle(new Wall(120,80,200,40));
		gc.addObstacle(new Wall(120,360,200,40));
		gc.addObstacle(new Wall(320,80,40,120));
		gc.addObstacle(new Wall(320,280,40,120));
		gc.addObstacle(new Wall(360,160,160,40));
		gc.addObstacle(new Wall(360,280,160,40));
		gc.addObstacle(new Wall(480,80,40,120));
		gc.addObstacle(new Wall(480,280,40,120));
		gc.addObstacle(new Wall(520,80,200,40));
		gc.addObstacle(new Wall(520,80,200,40));
		gc.addObstacle(new Wall(520,360,200,40));
		gc.addObstacle(new Wall(720,80,40,320));
		
		gc.addObstacle(new MovingWall(new Rectangle(220,200,60,80),new Line(new Vector2D(200,240),new Vector2D(640,240))));
		gc.addObstacle(new MovingWall(new Rectangle(580,170,60,20),new Line(new Vector2D(570,180),new Vector2D(675,180))));

		gc.addSurface(new Grass(new Rectangle(80,200,680,80)));
		gc.addSurface(new Grass(new Rectangle(120,120,200,240)));
		gc.addSurface(new Grass(new Rectangle(520,120,200,240)));
		
		gc.addHole(new Hole(new Vector2D(560,140),8));
		
		return gc;
	}
	
	public static GolfCourse getCourse7(){
		GolfCourse gc=new GolfCourse(new Vector2D(160,240), 2);
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
		gc.addObstacle(new Windmill(new Vector2D(640,140),45,5));
		
		
		gc.addSurface(new Grass(new Rectangle(80,160,440,120)));
		gc.addSurface(new Grass(new Rectangle(200,80,520,80)));
		gc.addSurface(new Grass(new Rectangle(560,160,160,200)));
		gc.addSurface(new Sand(new Polygon(new double[]{260,240,280,320,360,440},new double[]{280,220,160,140,240,280})));
		gc.addSurface(new Stone(new Polygon(new double[]{540,620,720,720,680,600},new double[]{280,200,180,320,360,360})));
		
		gc.addHole(new Hole(new Vector2D(640,280),8));
		return gc;
	}
	
	public static GolfCourse getCourse8(){
		GolfCourse gc=new GolfCourse(new Vector2D(160,240), 2);
		
		gc.addObstacle(new Wall(40,40,40,440));
		gc.addObstacle(new Wall(720,40,40,440));
		gc.addObstacle(new Wall(new Polygon(new double[]{80,400,720,720,400,80},new double[]{40,120,40,80,160,80})));
		gc.addObstacle(new Wall(new Polygon(new double[]{80,400,720,720,400,80},new double[]{440,360,440,400,320,400})));
		
		gc.addSurface(new Grass(new Polygon(new double[]{80,400,720,720,400,80},new double[]{80,160,80,400,320,400})));
		gc.addSurface(new Sand(new Polygon(new double[]{80,240,80},new double[]{80,240,400})));
		gc.addSurface(new Sand(new Polygon(new double[]{720,560,720},new double[]{80,240,400})));
		gc.addSurface(new Stone(new Rectangle(340,140,120,200)));
		
		gc.addHole(new Hole(new Vector2D(400,240),8));
		
		return gc;
	}
	
	public static GolfCourse getCourse9(){
		GolfCourse gc=new GolfCourse(new Vector2D(140,340), 2);
		
		double[] xs=new double[]{180,200,100,180,60,140,120,20,140,60};
		double[] ys=new double[]{20,40,140,220,340,420,440,340,220,140};
		gc.addObstacle(new Wall(new Polygon(xs,ys)));
		for(int i=0;i<xs.length;i++){
			xs[i]=800-xs[i];
		}
		gc.addObstacle(new Wall(new Polygon(xs,ys)));
		xs=new double[]{200,260,320,320,260,180};
		ys=new double[]{40,100,40,80,140,60};
		gc.addObstacle(new Wall(new Polygon(xs,ys)));
		for(int i=0;i<xs.length;i++){
			xs[i]=800-xs[i];
		}
		gc.addObstacle(new Wall(new Polygon(xs,ys)));
		xs=new double[]{120,260,400,400,260,140};
		ys=new double[]{440,280,440,480,320,460};
		gc.addObstacle(new Wall(new Polygon(xs,ys)));
		for(int i=0;i<xs.length;i++){
			xs[i]=800-xs[i];
		}
		gc.addObstacle(new Wall(new Polygon(xs,ys)));
		gc.addObstacle(new Wall(new Polygon(new double[]{400,480,460,400,340,320},new double[]{120,200,220,160,220,200})));
		gc.addObstacle(new Wall(320,40,160,40));
		
		gc.addObstacle(new MovingWall(new Polygon(new double[]{-20,0,40},new double[]{20,-40,0},200,160),new Line(new Vector2D(180,140),new Vector2D(300,260))));
		gc.addObstacle(new MovingWall(new Polygon(new double[]{-20,0,40},new double[]{-20,40,0},600,160),new Line(new Vector2D(500,260),new Vector2D(620,140))));
		
		xs=new double[]{100,180,260,320,400,400,320,340,260,260,140,60,180};
		ys=new double[]{140,60,140,80,80,120,200,220,300,280,420,340,220};
		gc.addSurface(new Grass(new Polygon(xs,ys)));
		for(int i=0;i<xs.length;i++){
			xs[i]=800-xs[i];
		}
		gc.addSurface(new Grass(new Polygon(xs,ys)));
		xs=new double[]{100,180,220,140};
		ys=new double[]{140,60,100,180};
		gc.addSurface(new Sand(new Polygon(xs,ys)));
		for(int i=0;i<xs.length;i++){
			xs[i]=800-xs[i];
		}
		gc.addSurface(new Sand(new Polygon(xs,ys)));
		xs=new double[]{260,400,400};
		ys=new double[]{300,160,440};
		gc.addSurface(new Sand(new Polygon(xs,ys)));
		for(int i=0;i<xs.length;i++){
			xs[i]=800-xs[i];
		}
		gc.addSurface(new Sand(new Polygon(xs,ys)));
		
		gc.addHole(new Hole(new Vector2D(660,340),8));
		
		return gc;
	}
	
	public static GolfCourse getCourse10(){
		GolfCourse gc=new GolfCourse(new Vector2D(480,280), 2);
		
		gc.addObstacle(new Wall(new Polygon(new double[]{200,200,80,200,200,40},new double[]{40,80,200,320,360,200})));
		gc.addObstacle(new Wall(200,40,480,40));
		gc.addObstacle(new Wall(200,320,320,40));
		gc.addObstacle(new Wall(300,180,380,40));
		gc.addObstacle(new Wall(680,40,40,180));
		gc.addObstacle(new Wall(520,220,40,140));
		
		gc.addObstacle(new Windmill(new Vector2D(200,200),60,10));
		
		gc.addSurface(new Grass(new Rectangle(320,80,360,100)));
		gc.addSurface(new Grass(new Rectangle(320,220,200,100)));
		gc.addSurface(new Grass(new Polygon(new double[]{80,200,200},new double[]{200,80,320})));
		gc.addSurface(new Stone(new Rectangle(200,80,120,240)));
		gc.addSurface(new Sand(new Polygon(new double[]{520,540,540,600,680,680},new double[]{80,120,140,180,180,80})));
		
		gc.addHole(new Hole(new Vector2D(480,120),8));
		
		return gc;
	}
}
