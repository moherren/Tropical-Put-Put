package graphics;

import java.util.ArrayList;
import java.util.Arrays;

import visibleObjects.VisibleObject;

public class Screen extends Render{
	private Render2D render;
	public Screen(int width, int height){
		super(width,height);
		render=new Render2D(width,height);
	}
	
	public void render(ArrayList<VisibleObject> objects){
		for(VisibleObject vO:objects){
			vO.render(render);
		}
		draw(render,0,0);
	}
}
