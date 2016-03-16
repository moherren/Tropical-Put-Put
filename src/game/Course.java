package game;

import java.util.ArrayList;

import graphics.Render2D;
import visibleObjects.VisibleObject;

public class Course implements VisibleObject{
	ArrayList<Obstacle> obstacles;
	
	public Course(){
		obstacles=new ArrayList<Obstacle>();
		formBasicSquare();
	}
	
	public void formBasicSquare(){
		obstacles.clear();
		obstacles.add(new Wall(100,100,Wall.TYPE_HORIZONTAL,200));
		obstacles.add(new Wall(100,100,Wall.TYPE_VERTICAL,200));
		obstacles.add(new Wall(100,300,Wall.TYPE_HORIZONTAL,200));
		obstacles.add(new Wall(300,100,Wall.TYPE_VERTICAL,200));
	}

	@Override
	public void render(Render2D r) {
		for(Obstacle o:obstacles)
			o.render(r);
	}
}
