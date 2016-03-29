package graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

public class Texture {
	
	
	public static Render generatePolishedMetal(int width, int height, int color){
		
		Random random=new Random(0);
		Render render=new Render(width,height);
		for(int y=0;y<height;y++){
			for(int x=0;x<width;x++){
				int r = (color & 0xFF0000) >> 16;
	    		int g = (color & 0xFF00) >> 8;
	    		int b = (color & 0xFF);
				
				double mult=1;
				double range=0.4;
				if(random.nextBoolean())
					mult-=range*random.nextDouble();
				else
					mult+=range*random.nextDouble();
				r=(int) Math.min(r*mult, 255);
				g=(int) Math.min(g*mult, 255);
				b=(int) Math.min(b*mult, 255);
				
				int rgb = r;
				rgb = (rgb << 8) + g;
				rgb = (rgb << 8) + b;
				render.pixels[x+y*width]=rgb;
			}
			render.blurRow(10, y);
		}
		for(int x=0;x<width;x++){
			double d=Math.abs(x-width/2)*0.5/width;
			for(int y=0;y<height;y++){
				render.pixels[x+y*width]=Render.mixColor(render.pixels[x+y*width], 0xffffff, d);
			}
		}
		return render;
	}
	
	public static Render addGUIEdging(Render r,int color,int edgeLength){
		
		for(int x=0;x<r.width;x++){
			for(int y=0;y<r.height;y++){
				int dRight=r.width-x,dBottom=r.height-y;
				if(x<edgeLength&&((y>r.height/2&&x<dBottom)||(y<r.height/2&&x<y)||(y==r.height/2)))
					r.pixels[x+y*r.width]=Render.mixColor(color, 0, 0.20);
				else if(dRight<edgeLength&&((y>r.height/2&&dRight<dBottom)||(y<r.height/2&&dRight<y)||(y==r.height/2)))
					r.pixels[x+y*r.width]=Render.mixColor(color, 0, 0.50);
				else if(y<edgeLength)
					r.pixels[x+y*r.width]=Render.mixColor(color, 0, 0.30);
				else if(dBottom<edgeLength)
					r.pixels[x+y*r.width]=Render.mixColor(color, 0, 0.40);
				else
					r.pixels[x+y*r.width]=color;
			}
		}
		
		return r;
	}
	
	/**  This method loads an image file onto a Render object.
	 * @param fileName
	 * @return Render with file loaded onto it
	 */
	public static Render loadBitmap(String fileName){
		try{
			BufferedImage image=ImageIO.read(Texture.class.getResource(fileName));
			int width=image.getWidth();
			int height=image.getHeight();
			Render result=new Render(width,height);
			image.getRGB(0, 0,width, height, result.pixels,0,width);
			
			for(int x=0; x<width;x++){
				for(int y=0; y<height; y++){
					Color c=new Color(image.getRGB(x,y));
					int r=c.getRed();
					int b=c.getBlue();
					int g=c.getGreen();
					int rgb = r;
					rgb = (rgb << 8) + g;
					rgb = (rgb << 8) + b;
					result.pixels[x+y*width]=rgb;
				}
			}
			return result;
		}catch(Exception e){
			System.out.println("CRASH!");
			throw new RuntimeException(e);
		}
	} 
	
	public static Render2D getSpriteSheet(Render r,int width,int height,int num){
		int columns=r.width/width,rows=r.height/height;
		if(num>=rows*columns)
			return null;
		Render2D sprite=new Render2D(width,height);
		
		for(int x=0;x<width;x++){
			int xPix=x+((num%columns)*width);
			for(int y=0;y<height;y++){
				int yPix=y+(num/columns)*height;
				int alpha=r.pixels[xPix+yPix*r.width];
				sprite.pixels[x+y*sprite.width]=alpha;
			}
		}
		
		return sprite;
	}
	
	public static Render2D getSpriteSheet(Render r,int width,int height,int num,boolean flipped){
		int columns=r.width/width,rows=r.height/height;
		if(num>=rows*columns)
			return null;
		Render2D sprite=new Render2D(width,height);
		
		for(int x=0;x<width;x++){
			int xPix=x+((num%columns)*width);
			for(int y=0;y<height;y++){
				int yPix=y+(num/columns)*height;
				int alpha=r.pixels[xPix+yPix*r.width];
				if(!flipped)
					sprite.pixels[x+y*sprite.width]=alpha;
				else
					sprite.pixels[sprite.width-1-x+y*sprite.width]=alpha;
			}
		}
		
		return sprite;
	}
}
