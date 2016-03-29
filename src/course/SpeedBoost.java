package course;
import java.awt.Graphics;

import geometry.Polygon;
import geometry.Shape;
import geometry.Vector2D;
import graphics.Render2D;

public class SpeedBoost extends Surface {

	public SpeedBoost(Polygon shape){
		super(shape);
	}
	
	@Override
	public void render(Render2D r) {
		for(int x=minX;x<maxX;x++)
			for(int y=minY;y<maxY;y++){
				if(shape.includes(new Vector2D(x+X,y+Y)))
					r.drawPixel(0x2200FF, (int)(x+X), (int)(y+Y));
			}
		//to be implemented
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
	}

	@Override
	public double getCoefFriction() {
		return -0.5;//friction will now speed it up
	}

}