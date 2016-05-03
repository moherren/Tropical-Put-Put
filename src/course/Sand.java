package course;
import java.awt.Graphics;

import geometry.Polygon;
import geometry.Shape;
import geometry.Vector2D;
import graphics.Render2D;

public class Sand extends Surface {

	public Sand(Polygon shape){
		super(shape);
	}
	
	@Override
	public void render(Render2D r) {
		for(int x=minX;x<maxX;x++)
			for(int y=minY;y<maxY;y++){
				if(shape.includes(new Vector2D(x+X,y+Y)))
					r.pixels[(int)(x+X)+r.width*(int)(y+Y)]=GolfCourse.tilesA[3].pixels[((int)(x+X)%GolfCourse.tilesA[3].width)+((int)(y+Y)%GolfCourse.tilesA[3].height)*GolfCourse.tilesA[3].width];
			}
		//to be implemented
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
	}

	@Override
	public double getCoefFriction() {
		return 1.2;
	}

}