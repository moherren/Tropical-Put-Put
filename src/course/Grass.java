package course;
import java.awt.Graphics;

import geometry.Shape;
import graphics.Render2D;

public class Grass extends Surface {

	public Grass(Shape shape){
		this.shape=shape;
	}
	
	@Override
	public void render(Render2D r) {
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
	}

	@Override
	public double getCoefFriction() {
		return 0.3;
	}

}
