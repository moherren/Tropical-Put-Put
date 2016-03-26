package course;
import java.awt.Graphics;

import geometry.Polygon;
import geometry.Shape;
import geometry.Vector2D;
import graphics.Render2D;

public class Grass extends Surface {

	public Grass(Polygon shape){
		super(shape);
	}
	
	@Override
	public void render(Render2D r) {
		for(int x=minX;x<maxX;x++)
			for(int y=minY;y<maxY;y++){
				if(shape.includes(new Vector2D(x+X,y+Y)))
					r.pixels[(int)(x+X)+r.width*(int)(y+Y)]=GolfCourse.tilesA[1].pixels[((int)(x+X)%GolfCourse.tilesA[1].width)+((int)(y+Y)%GolfCourse.tilesA[1].height)*GolfCourse.tilesA[1].width];
			}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
	}

	@Override
	public double getCoefFriction() {
		return 1;
	}

}
