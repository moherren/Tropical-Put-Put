package game;

import graphics.Render2D;

public class Wall implements Obstacle{
	final static int TYPE_HORIZONTAL=0,TYPE_VERTICAL=1,TYPE_NEGATIVE_SLANT=2,TYPE_POSITIVE_SLANT=3;
	double width=2,height=GolfBall.radius,length;
	double x,y;
	int type;

	public Wall(double x,double y,int type,double length){
		this.x=x;
		this.y=y;
		this.type=type;
		this.length=length;
	}
	
	public boolean ballCollision(double x, double y) {
		
		switch(type){
		
		case TYPE_HORIZONTAL:{
			double dx = Math.max(this.x - x,  x - (this.x+length));
			double dy = Math.max(this.y-width - y, y - (this.y+width));
			return Math.sqrt(dx*dx + dy*dy)<=GolfBall.radius;
			}
		
		case TYPE_VERTICAL:{
			double dx = Math.max(this.x-width - x,  x - (this.x+width));
			double dy = Math.max(this.y - y, y - (this.y+length));
			return Math.sqrt(dx*dx + dy*dy)<=GolfBall.radius;
			}
		
		case TYPE_NEGATIVE_SLANT:{
			break;
			}
		
		case TYPE_POSITIVE_SLANT:{
			break;
			}
		}
		
		return false;
	}

	public double[] deflectAngle(double x, double y, double vX, double vY) {
		
		double bounceCoeffecient=1;
		
		if(ballCollision(x+vX,y))
			vX*=-1*bounceCoeffecient;
		if(ballCollision(x,y+vY))
			vY*=-1*bounceCoeffecient;
			
		return new double[]{vX,vY};
	}

	public void render(Render2D r) {
		switch(type){
		
			case TYPE_HORIZONTAL:{
				for(int x=0;x<length;x++){
					for(int y=(int)(-width);y<width;y++){
						r.pixels[(int)(x+this.x)+r.width*(int)(y+this.y)]=0x333333;
						//r.depthMap[x+r.width*y]=(int) (Math.cos(Math.PI/4.0)*y+Math.cos(Math.PI/4.0)*height);
					}
				}
				break;
			}
			
			case TYPE_VERTICAL:{
				for(int y=0;y<length;y++){
					for(int x=(int)(-width);x<width;x++){
						r.pixels[(int)(x+this.x)+r.width*(int)(y+this.y)]=0x333333;
						//r.depthMap[x+r.width*y]=(int) (Math.cos(Math.PI/4.0)*y+Math.cos(Math.PI/4.0)*height);
					}
				}
				break;
			}
			
		}
		
	}
	
	
}
