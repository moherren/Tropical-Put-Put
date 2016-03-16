package graphics;

public class Render2D extends Render{
	public int[] depthMap=new int[height*width];
	
	public Render2D(int width, int height) {
		super(width, height);
	}
	
	/** This method draws a pixel at a specific depth. If it is behind another pixel than the method returns.
	 * @param color
	 * @param x
	 * @param y
	 * @param depth
	 */
	public void drawPixel(int color,int x,int y,int depth){
		if(depthMap[x+y*width]<=depth){
			pixels[x+y*width]=color;
			depthMap[x+y*width]=depth;
		}
	}
	
	public void draw(Render render,int xOffSet,int yOffSet,int depth){
		for(int y=0; y<render.height;y++){
			int yPix=y+yOffSet;
			if (yPix<0||yPix>=height) continue;
			for(int x=0; x<render.width;x++){
				int xPix=x+xOffSet;
				if (xPix<0||xPix>=width) continue;
				int alpha=render.pixels[x+y*render.width];
				
				if(alpha>0&&depthMap[xPix+yPix*width]<=depth){
					pixels[xPix+yPix*width]=alpha;
					depthMap[xPix+yPix*width]=depth;
				}
			}
		}
		
	}
	
	public static void drawLine(Render2D r,int depth,int color,double x1,double y1,double x2,double y2){
		double length=Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
		double slopeY=(y2-y1)/length,slopeX=(x2-x1)/length;
		
		for(int i=0;i<length;i++){
			int x=(int) (x1+slopeX*i);
			int y=(int) (y1+slopeY*i);
			if(x>=0&&y>=0&&x<r.width&&y<r.height)
			if(r.depthMap[x+y*r.width]<=depth){
				r.pixels[x+y*r.width]=color;
				r.depthMap[x+y*r.width]=depth;
			}
		}
	}
}
