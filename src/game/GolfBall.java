package game;

import graphics.Render2D;
import visibleObjects.VisibleObject;

public class GolfBall implements VisibleObject{
 double x,y;
 double vX,vY;
 final static double radius=5;
 

 public GolfBall(double x,double y){
	 this.x=x;
	 this.y=y;
 }
 
 public void update(){
	 
 }
 public void render(Render2D r) {
	
 }
}
