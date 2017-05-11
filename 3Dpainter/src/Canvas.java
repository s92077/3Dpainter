import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
public class Canvas extends JPanel implements KeyListener
{
	private Vector3D viewPoint=new Vector3D(0,0,0);
	private Vector3D visionVector=new Vector3D(1600,1600,0);
	private Timer time;
	private int sleepTime;
	private int t=0;
	public Canvas()
	{
		setVisible(true);
		setSize(500,600); 
		sleepTime = 50;
		addKeyListener(this);
		setFocusable(true); 
		start();
	}	
	public void start()
	{
	  	   time = new Timer();
		   
	       time.schedule( new TimerTask() {
						public void run()
						{
						   //----------------------------------------
						   //----------------------------------------
						    repaint();
						}
						
					},0,sleepTime); 
	 
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		Transform tf=new Transform(viewPoint,visionVector,getWidth(),getHeight());
		//surfacetest
		Vector3D dirX=new Vector3D(1,-1,0);
		Vector3D dirY=new Vector3D(-1,-1,0);
		Vector3D position=new Vector3D(210,300,14);
		Surface surf=new Surface(Math.pow(800,0.5),Math.pow(800,0.5),position,dirX,dirY);
		surf.draw(g,tf);
		//surfacetest
		//LineTest

		Line line=new Line(new Vector3D(210,100,0),new Vector3D(0,0,1),28);
		line.draw(g,tf);
		//LineTest
		
		Cube cube=new Cube(1,1,1,new Vector3D(10,10,0),new Vector3D(1,-1,0),new Vector3D(1,1,0));
		cube.draw(g,tf);
		Vector3D test[]=new Vector3D[8];
		test[0]=new Vector3D(210,190,14);
		test[1]=new Vector3D(210,190,-14);
		test[2]=new Vector3D(190,210,14);
		test[3]=new Vector3D(190,210,-14);
		test[4]=new Vector3D(230,210,14);
		test[5]=new Vector3D(230,210,-14);
		test[6]=new Vector3D(210,230,14);
		test[7]=new Vector3D(210,230,-14);
		for(int i=0;i<8;i++)
		{	
			Vector3D test2D=tf.projection(test[i]);
			test2D=tf.trans2D(test2D);
			g.drawOval((int)(test2D.getX()),(int)(test2D.getY()),1,1);
		}
	}
	public void keyTyped(KeyEvent e) 
	{
		//System.out.println("keyTyped");
	}
    @Override
	public void keyPressed(KeyEvent e)
	{
		Vector3D orthogonal=new Vector3D(visionVector.normalize().negate().getY(),visionVector.normalize().getX(),0);
		visionVector.show();
		t++;
		System.out.printf("%d",t);
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_Q) 
			viewPoint=viewPoint.add(new Vector3D(0,0,1));
		if (key == KeyEvent.VK_E)
			viewPoint=viewPoint.add(new Vector3D(0,0,-1));
		if (key == KeyEvent.VK_W) 
			viewPoint=viewPoint.add(new Vector3D(visionVector.getX(),visionVector.getY(),0).normalize());
		if (key == KeyEvent.VK_S)
			viewPoint=viewPoint.add(new Vector3D(visionVector.getX(),visionVector.getY(),0).normalize().negate());
		if (key == KeyEvent.VK_A) 
			viewPoint=viewPoint.add(orthogonal);
		if (key == KeyEvent.VK_D)
			viewPoint=viewPoint.add(orthogonal.negate());
		if (key == KeyEvent.VK_UP) 
			//if(Math.abs((Math.pow(Math.pow(visionVector.getX(),2)+Math.pow(visionVector.getY(),2),0.5)/visionVector.getZ()))>0.01)
				visionVector=visionVector.rotVect(0,-1);
		if (key == KeyEvent.VK_DOWN)
			//if(Math.abs((Math.pow(Math.pow(visionVector.getX(),2)+Math.pow(visionVector.getY(),2),0.5)/visionVector.getZ()))>0.01)
				visionVector=visionVector.rotVect(0,1);
		if (key == KeyEvent.VK_LEFT) 
			visionVector=visionVector.rotVect(1,0);
		if (key == KeyEvent.VK_RIGHT)
			visionVector=visionVector.rotVect(-1,0);
	}
    @Override
	public void keyReleased(KeyEvent e) 
	{
		//System.out.println("keyReleased");
	}
	public static void main(String[] args)
	{
		Canvas p=new Canvas();
		JFrame f=new JFrame();
		f.add(p);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(500,600);
		f.setVisible(true);
	}
}