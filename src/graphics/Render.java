package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JLabel;

public class Render {
	public final int width;
	public final int height;
	public final int pixels[];
	public static final double vDisplacement=4/3.000,hDisplacement=Math.pow(vDisplacement,-1);
	public Font font = new JLabel().getFont().deriveFont(0, 24);
	
	public Render(int width,int height){
		this.width=width;
		this.height=height;
		this.pixels=new int[width*height];
		
		for(int x=0; x<width*height;x++){
			pixels[x]=0xffffff;
		}
	}
	public void draw(Render render,int xOffSet,int yOffSet){
		for(int y=0; y<render.height;y++){
			int yPix=y+yOffSet;
			if (yPix<0||yPix>=height) continue;
			for(int x=0; x<render.width;x++){
				int xPix=x+xOffSet;
				if (xPix<0||xPix>=width) continue;
				int alpha=render.pixels[x+y*render.width];
				if(alpha>0)
				pixels[xPix+yPix*width]=alpha;
			}
		}
		
	}
	
	public void drawPixel(int color,int x,int y){
		if(x>=0&&x<width&&y>=0&&y<height){
			pixels[x+y*width]=color;
		}
	}
	
	/** This method shades the image to a specified degree and color.
	 * @param shadeAmount - A double used to define how powerful the shading should be. Should be a value 1 to 0.
	 * @param shadeColor - This is the color that the image will be shaded to.
	 */
	public void shade(double shadeAmount,int shadeColor){
		if(shadeAmount==0)
			return;
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				int hex=pixels[x+y*width];
				int r = (hex & 0xFF0000) >> 16;
		    	int g = (hex & 0xFF00) >> 8;
		    	int b = (hex & 0xFF);
		    	r-=(r-((shadeColor & 0xFF0000) >> 16))*shadeAmount;
		    	g-=(g-((shadeColor & 0xFF00) >> 8))*shadeAmount;
		    	b-=(b-((shadeColor & 0xFF)))*shadeAmount;
		    	
		    	int rgb = r;
				rgb = (rgb << 8) + g;
				rgb = (rgb << 8) + b;
				pixels[x+y*width]=rgb;
			}
		}
	}
	
	/** This changes the image to gray scale. 
	 * @param shadeAmount -  The degree in which you want it to be converted to gray scale. Should be a value 1 to 0.
	 */
	public void convertToGray(double shadeAmount){
			
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				int hex=pixels[x+y*width];
				
				if(hex==0)
					continue;
				
				int r = (hex & 0xFF0000) >> 16;
		    	int g = (hex & 0xFF00) >> 8;
		    	int b = (hex & 0xFF);
		    	
		    	int shadeColor= (int) (r* 0.299);
		    	shadeColor += (int) (g*0.587);
				shadeColor += (int) (b*0.114);
				shadeColor = (shadeColor + (((shadeColor << 8) + shadeColor) << 8));
		    	
		    	r-=(r-((shadeColor & 0xFF0000) >> 16))*shadeAmount;
		    	g-=(g-((shadeColor & 0xFF00) >> 8))*shadeAmount;
		    	b-=(b-((shadeColor & 0xFF)))*shadeAmount;
		    	
		    	int rgb = r;
				rgb = (rgb << 8) + g;
				rgb = (rgb << 8) + b;
				pixels[x+y*width]=Math.max(1, rgb);
			}
		}
	}
	
	public void setFont(Font f){
		font=f;
	}
	
	public void setFont(String file){
		try {
			InputStream is=Render.class.getResourceAsStream(file);

			font=Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(font.getStyle(),font.getSize());
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Font getFont(){
		return font;
	}
	
	public void drawString(String s,int x,int y,int color){
		FontRenderContext frc=new FontRenderContext(null,true,true);
		GlyphVector gv=font.createGlyphVector(frc, s);
		Shape shape=gv.getOutline(x, y);
		Rectangle2D rect=gv.getVisualBounds();
		if(!shape.intersects(0, 0, width, height))
			return;
		for(int X=Math.max(x, 0);X<Math.min(x+rect.getWidth()+50,width);X++){
			for(int Y=(int) Math.max(y-rect.getHeight(), 0);Y<Math.min(y+rect.getHeight()/2.000,height);Y++){
				if(shape.contains(X, Y))
					pixels[X+Y*width]=color;
			}
		}
	}
	
	public void drawDetailedString(String s,int x,int y,int color){
		FontRenderContext frc=new FontRenderContext(null,true,true);
		GlyphVector gv=font.createGlyphVector(frc, s);
		Shape shape=gv.getOutline(x, y);
		Rectangle2D rect=gv.getVisualBounds();
		if(!shape.intersects(0, 0, width, height))
			return;
		for(int X=Math.max(x, 0);X<Math.min(x+rect.getWidth()+50,width);X++){
			for(int Y=(int) Math.max(y-rect.getHeight(), 0);Y<Math.min(y+rect.getHeight()/2.000,height);Y++){			
				
				Color oldC=new Color(pixels[X+Y*width]);
				Color newC=new Color(color);
				
				int red,green,blue;
				
				if(shape.intersects(X, Y, 1/3.0, 1))
					red=newC.getRed();
				else
					red=oldC.getRed();
				if(shape.intersects(X+1/3.0, Y, 1/3.0, 1))
					green=newC.getGreen();
				else
					green=oldC.getGreen();
				if(shape.intersects(X+2/3.0, Y, 1/3.0, 1))
					blue=newC.getBlue();
				else
					blue=oldC.getBlue();
				
				int rgb = red;
				rgb = (rgb << 8) + green;
				rgb = (rgb << 8) + blue;
				
				if(color!=0&&pixels[X+Y*width]!=0){
					if(rgb==color)
						pixels[X+Y*width]=color;
					else
						pixels[X+Y*width]=mixColor(oldC.getRGB(),rgb,0.5);
				}
				else 
					pixels[X+Y*width]=Math.max(1, rgb);
			}
		}
	}
	
	public GlyphVector getGlyohVector(String s){
		FontRenderContext frc=new FontRenderContext(null,true,true);
		return font.createGlyphVector(frc, s);
	}
	
	public GlyphVector getGlyohVector(String s,Font font){
		FontRenderContext frc=new FontRenderContext(null,true,true);
		return font.createGlyphVector(frc, s);
	}
	
	public static int mixColor(int base,int color,double d){
		int r = (base & 0xFF0000) >> 16;
    	int g = (base & 0xFF00) >> 8;
    	int b = (base & 0xFF);
    	r-=(r-((color & 0xFF0000) >> 16))*d;
    	g-=(g-((color & 0xFF00) >> 8))*d;
    	b-=(b-((color & 0xFF)))*d;
    	int rgb = r;
		rgb = (rgb << 8) + g;
		rgb = (rgb << 8) + b;
		return rgb;
	}
	
	public void blurRow(int radius,int y){
		int xs[]=new int[width];
		for(int x=0;x<width;x++){
			int min=x-radius;
			int max=x+radius;
			int reps=0;
			int tR=0;
			int tG=0;
			int tB=0;
			for(int rx1=min;rx1<max;rx1++){
				int rx=rx1;
				while(rx<0)
					rx+=width;
				while(rx>=width)
					rx-=width;
				reps++;
				int color=pixels[rx+y*width];
				tR += (color & 0xFF0000) >> 16;
    			tG += (color & 0xFF00) >> 8;
    			tB += (color & 0xFF);
			}
			tR/=reps;
			tG/=reps;
			tB/=reps;
			
			int rgb = tR;
			rgb = (rgb << 8) + tG;
			rgb = (rgb << 8) + tB;
			
			xs[x]=rgb;
		}
		
		for(int x=0;x<width;x++){
			pixels[x+y*width]=xs[x];
		}
	}
	
	public void blurColumn(int radius,int x){
		int Ys[]=new int[height];
		for(int y=0;y<height;y++){
			int min=y-radius;
			int max=y+radius;
			int reps=0;
			int tR=0;
			int tG=0;
			int tB=0;
			for(int ry1=min;ry1<max;ry1++){
				int ry=ry1;
				while(ry<0)
					ry+=height;
				while(ry>=height)
					ry-=height;
				reps++;
				int color=pixels[x+ry*width];
				tR += (color & 0xFF0000) >> 16;
    			tG += (color & 0xFF00) >> 8;
    			tB += (color & 0xFF);
			}
			tR/=reps;
			tG/=reps;
			tB/=reps;
			
			int rgb = tR;
			rgb = (rgb << 8) + tG;
			rgb = (rgb << 8) + tB;
			
			Ys[y]=rgb;
		}
		
		for(int y=0;y<height;y++){
			pixels[x+y*width]=Ys[y];
		}
	}
}
