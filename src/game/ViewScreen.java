package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import course.GolfCourse;
import course.Wall;
import entities.GolfBall;
import geometry.Circle;
import geometry.Polygon;
import geometry.Rectangle;
import geometry.Shape;
import geometry.Vector2D;

/**
 * I was using this to test the physics
 * @author Peter
 *
 */
public class ViewScreen extends JPanel{

	private GolfCourse world;
	
	private double fps=100;
	
	public ViewScreen(){
		super();
		world=new GolfCourse();
		GolfBall ball=new GolfBall(new Vector2D(200,200), world);
		world.addEntity(ball);
		world.addObstacle(new Wall(new Rectangle(40,250,40,300)));
		world.addObstacle(new Wall(new Rectangle(500,80,960,40)));
		world.addObstacle(new Wall(new Rectangle(500,420,960,40)));
		world.addObstacle(new Wall(new Rectangle(960,250,40,300)));
		ball.applyForce(new Vector2D(5,0));
		world.tiltAngle=0;
		world.tiltDirection=new Vector2D(0,1);
		setPreferredSize(new Dimension(1000,500));
		setBackground(Color.WHITE);
		Timer t=new Timer((int)(1000/fps),new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				long start=System.nanoTime();
				world.update((int)(1000/fps)/10);
				repaint();
			}
		});
		t.start();
		new Thread(){
			public void run(){
				Scanner scan=new Scanner(System.in);
				while(true){
					System.out.println("new tilt direction:");
					Vector2D newTilt=new Vector2D(scan.nextDouble(),scan.nextDouble()).normalize();
					System.out.println("new angle:");
					double newAngle=scan.nextDouble();
					world.tiltDirection=newTilt;
					world.tiltAngle=newAngle;
				}
			}
		}.start();
	}
	
	public static void main(String[] args){
		JFrame frame=new JFrame("Test");
		frame.add(new ViewScreen());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setFocusable(true);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		world.render(g);
	}
	
}
