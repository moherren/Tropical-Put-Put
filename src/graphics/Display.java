package graphics;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import javax.swing.JFrame;
import visibleObjects.Painter;
import visibleObjects.VisibleObject;

public class Display extends Canvas{
	public final static int WIDTH = 800;
	public final static int HEIGHT = 600;
	private int[] pixels;
	private BufferedImage img;
	Screen screen;
	long frameNum=System.nanoTime();
	ArrayList<VisibleObject> objects=new ArrayList<VisibleObject>();
	Painter painter=null;

	/**This method should be the only way that Display is initiated
	 * @return a new Display object
	 */
	public static Display createNew(){
		Display game = new Display();
		JFrame window=new JFrame();
		window.pack();
		window.add(game);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
		window.setSize(WIDTH, HEIGHT);
		window.setLocationRelativeTo(null);

		//window.revalidate();
		return game;
	}
	
	private Display(){
		img=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		pixels= ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		screen=new Screen(WIDTH,HEIGHT);
	}
	
	public void Render(){
		frameNum=System.nanoTime();
		BufferStrategy bs=this.getBufferStrategy();
		if(bs==null||bs.contentsLost()){
			createBufferStrategy(2);
			return;
		}
		screen.render(objects);
		for(int i=0;i<WIDTH*HEIGHT;i++){
			pixels[i]=screen.pixels[i];
		}
		Graphics g=bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		extraPaint(g);
		g.dispose();
		bs.show();
		
	}
	
	public void addVObject(VisibleObject vO){
		changeVObjects(vO,0);
	}
	
	public void removeVObject(VisibleObject vO){
		changeVObjects(vO,1);
	}
	
	public void clearVObjects(){
		changeVObjects(null,2);
	}
	
	/** This method is used if there are any additional graphics that you would like generated using the old Graphics function.
	 *  Do not rely on this method!
	 * @param g
	 */
	private void extraPaint(Graphics g){
		Graphics2D g2=(Graphics2D) g;
		/*
		 * Put whatever you want in here
		 */
		if(painter!=null)
			painter.paint(g);
	}
	public void showScreen(){
		BufferStrategy bs=this.getBufferStrategy();
		if(bs==null){
			createBufferStrategy(2);
			showScreen();
			return;
		}
		
		Graphics g=bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();
		bs.show();
	}
	private synchronized ArrayList<VisibleObject> changeVObjects(VisibleObject v,int action){
		if(action==0)
			objects.add(v);
		else if(action==1)
			objects.remove(v);
		else if(action==2)
			objects.clear();
		
		return (ArrayList<VisibleObject>) objects.clone();
	}
	public void setPainter(Painter p){
		painter=p;
	}
}
