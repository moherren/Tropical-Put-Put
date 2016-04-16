package game;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

import graphics.Render;
import graphics.Render2D;
import graphics.Texture;
import visibleObjects.VisibleObject;

public class TextBox implements VisibleObject,ActionListener{
	public final static int TB_HELP=0,TB_CHAT=1,TB_INSTRUCT=2;
	int type;
	int x,y;
	Render box;
	static Render gui=null;
	static Render[] guis;
	boolean visible=true;
	static Render PuttyFace=null;
	public boolean waitingForResponse=false;
	
	private TextBox(int type,int x,int y,int width,int height,String text){
		this.type=type;
		this.x=x;
		this.y=y;
		
		if(PuttyFace==null)
		PuttyFace=Texture.loadBitmap("textures/putty.png");
		
		if(gui==null){
		gui=Texture.loadBitmap("textures/guichatbox.png");
		guis=Texture.getSpriteSheet(gui, 30, 30);
		}
		
		switch(type){
		
		case TB_HELP:{
			
			box=generateHelpBox(width,height,text);
			
			break;
			}
		
			case TB_CHAT:{
			
			box=generateChatBox(width,height,text);
			
			break;
			}
		
		}
	}
	
	private static Render generateHelpBox(int width,int height,String text) {
		
		Render sprite=new Render(width,height);
		
		Arrays.fill(sprite.pixels, 0);
		
		//sprite.setFont("LithosBlack.ttf");
		sprite.setFont(sprite.getFont().deriveFont(16f));
		
		
		for(int x=0;x<width/30;x++){
			boolean left=(x==0);
			boolean right=(x==width/30-1);
			for(int y=0;y<height/30;y++){
				boolean top=(y==0);
				boolean bottom=(y==height/30-1);
				
				int num=5;
				
				if(top)
					num-=4;
				else if(bottom)
					num+=4;
				
				if(left)
					num--;
				else if(right)
					num++;
				
				sprite.draw(guis[num], x*30, y*30);
			}
		}
		
		String[] words=text.split(" ");
		
		AffineTransform affinetransform = new AffineTransform();   
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		Font f = sprite.getFont();
		int x=0,y=0;
		for(int i=0;i<words.length;i++){
			Rectangle2D rec=f.getStringBounds(words[i], frc);
			if(y==0||x+rec.getWidth()>width-5){
				y+=rec.getHeight()+3;
				x=5;
			}
			sprite.drawDetailedString(words[i], x, y, 1);
			x+=rec.getWidth()+f.getStringBounds(" ", frc).getWidth();
		}
		
		return sprite;
	}
	
	private static Render generateChatBox(int width,int height,String text) {
		
		Render sprite=new Render(width,height);
		
		Arrays.fill(sprite.pixels, 0);
		sprite.draw(PuttyFace, 0, 0);
		
		//sprite.setFont("LithosBlack.ttf");
		sprite.setFont(sprite.getFont().deriveFont(16f));
		
		for(int x=3;x<width/30;x++){
			boolean left=(x==3);
			boolean right=(x==width/30-1);
			for(int y=0;y<height/30;y++){
				boolean top=(y==0);
				boolean bottom=(y==height/30-1);
				
				int num=5;
				
				if(top)
					num-=4;
				else if(bottom)
					num+=4;
				
				if(left)
					num--;
				else if(right)
					num++;
				
				sprite.draw(guis[num], x*30, y*30);
			}
		}
		
		text="Putty: "+text;
		
		String[] words=text.split(" ");
		
		AffineTransform affinetransform = new AffineTransform();   
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		Font f = sprite.getFont();
		int x=0,y=0;
		for(int i=0;i<words.length;i++){
			Rectangle2D rec=f.getStringBounds(words[i], frc);
			if(y==0||x+rec.getWidth()>width-5){
				y+=rec.getHeight()+3;
				x=95;
			}
			sprite.drawDetailedString(words[i], x, y, 1);
			x+=rec.getWidth()+f.getStringBounds(" ", frc).getWidth();
		}
		
		return sprite;
	}

	public static TextBox newHelpBox(int x,int y,int width,int height,String text){
		TextBox tb=new TextBox(TB_HELP,x,y,width,height,text);
		
		return tb;
	}
	
	
	public static TextBox newChatBox(int x,int y,int width,int height,String text){
		TextBox tb=new TextBox(TB_CHAT,x,y,width,height,text);
		tb.waitingForResponse=true;
		return tb;
	}


	public void render(Render2D r) {
		if(visible){
			r.draw(box, x, y);
			if(type==TB_CHAT&&System.currentTimeMillis()%1200/600==0){
				for(int y=0;y<5;y++){
					for(int x=y;x<10-y;x++){
						r.pixels[(this.x+x+box.width-20)+(this.y+y+box.height-20)*r.width]=1;
					}
				}
			}
		}
	}
	
	public void keyPressed(KeyEvent ke){
		if(waitingForResponse)
			moveOn();
	}
	
	public void moveOn(){
		visible=false;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(type){
			case TB_CHAT:{
				visible=false;
				break;
			}
			case TB_HELP:{
				break;
			}
			case TB_INSTRUCT:{
				break;
			}
		}
	}
}
