package game;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import graphics.Render;
import graphics.Render2D;
import visibleObjects.VisibleObject;

public class Scorecard implements VisibleObject{
	ArrayList<Player> players=new ArrayList<Player>();
	Render card=null;

	public Scorecard(){
		addPlayer("Player 1");
	}
	
	public void addPlayer(String name){
		players.add(new Player(name));
		card=null;
	}
	
	public void generateCard(){
		card=new Render(650,players.size()*20+24);
		
		Arrays.fill(card.pixels, 0xffffff);
		
		for(int i=0;i<card.width*23;i++){
			card.pixels[i]=0xff0000;
		}
		
		for(int x=0;x<card.width;x++){
			card.pixels[x]=1;
			card.pixels[x+23*card.width]=1;
			card.pixels[x+(card.height-1)*card.width]=1;
		}
		for(int y=0;y<card.height-1;y++){
			card.pixels[y*card.width]=1;
			for(double x=80;x<card.width;x+=(card.width-80)/18.0){
				card.pixels[y*card.width+(int)x]=1;
			}
			card.pixels[y*card.width+card.width-1]=1;
		}
		
		card.drawDetailedString("Hole", 3, 21, 1);
		card.setFont(card.getFont().deriveFont(18f));
		for(int i=1;i<=18;i++){
			if(i>9)
			card.drawDetailedString(i+"", 86+(i-1)*30, 20, 1);
			else
				card.drawDetailedString(i+"", 91+(i-1)*30, 20, 1);
		}
		
		int y=24;
		for(Player p:players){
			p.generateSlot();
			card.draw(p.slot, 0, y);
			y+=20;
		}
	}
	
	public void setStrokes(String name,int hole,int strokes){
		for(Player p:players){
			if(p.name.equals(name)){
				p.setStrokes(hole, strokes);
				card=null;
				return;
			}
		}
	}
	
	public void render(Render2D r) {
		if(card==null)
			generateCard();
		
		r.draw(card, r.width/2-card.width/2, r.height/2-card.height/2);
	}
	
}

class Player{
	int[] strokes=new int[18];
	String name;
	Render slot;
	
	public Player(String name){
		this.name=name;
		Arrays.fill(strokes, -1);
	}
	
	public void setStrokes(int hole,int strokes){
		this.strokes[hole]=strokes;
	}
	
	public void generateSlot(){
		slot=new Render(800,20);
		
		Arrays.fill(slot.pixels,0xffffff);
		
		String name=this.name;
		boolean tooLong=false;

		slot.setFont(slot.getFont().deriveFont(17f));
		
		AffineTransform affinetransform = new AffineTransform();   
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		Font f = slot.getFont();
		Rectangle2D rec=f.getStringBounds(name, frc);
		while(rec.getWidth()>74){
			tooLong=true;
			name=name.substring(0, name.length()-1);
			rec=f.getStringBounds(name+"...", frc);
		}
		
		
		
		if(tooLong)
			slot.drawDetailedString(name+"...", 3, 17, 1);
		else
			slot.drawDetailedString(name, 3, 17, 1);
		
		for(int i=0;i<strokes.length;i++){
			if(strokes[i]>-1)
			slot.drawString(strokes[i]+"", 82+30*i, 19, 1);
		}
		
		for(int i=0;i<slot.width*slot.height;i++){
			if(slot.pixels[i]==0xffffff)
				slot.pixels[i]=0;
		}
	}
}
