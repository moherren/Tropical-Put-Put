package course;
import java.awt.Graphics;

import geometry.Polygon;
import geometry.Shape;
import geometry.Vector2D;
import graphics.Render2D;

public class Stone extends Surface {

	public Stone(Polygon shape){
		super(shape);
	}
	
	@Override
	public void render(Render2D r) {
		for(int x=minX;x<=maxX;x++)
			for(int y=minY;y<=maxY;y++){
				if(shape.includes(new Vector2D(x+X,y+Y)))
					r.pixels[(int)(x+X)+r.width*(int)(y+Y)]=GolfCourse.tilesA[4].pixels[((int)(x+X)%GolfCourse.tilesA[4].width)+((int)(y+Y)%GolfCourse.tilesA[4].height)*GolfCourse.tilesA[4].width];
			}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
	}

	@Override
	public double getCoefFriction() {
		return 0.05;
	}

}