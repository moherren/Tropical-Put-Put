package course;

import graphics.Render2D;

import java.awt.Graphics;

import entities.GameEntity;

public interface Obstacle {

	public boolean intersects(GameEntity entity);
	public void handleCollision(GameEntity entity);
	public void render(Render2D r);
	public void render(Graphics g);
}
