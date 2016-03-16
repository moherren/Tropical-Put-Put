package game;

import graphics.Render2D;

public interface Obstacle {
public boolean ballCollision(double x,double y);
public double[] deflectAngle(double x,double y,double vX,double vY);
public void render(Render2D r);
}
