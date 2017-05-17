import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
public class Canvas extends JPanel implements KeyListener,MouseListener, MouseMotionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2810019773818231611L;
	private Vector3D viewPoint=new Vector3D(0,0,0);
	private Vector3D visionVector=new Vector3D(2000,2000,0);
	private Timer time;
	private int sleepTime;
	private int viewCommand=0;
	private boolean viewDrag=false;
	private int viewDragX0,viewDragY0,viewDragX1,viewDragY1;
	public Canvas()
	{
		setVisible(true);
		setSize(500,600); 
		sleepTime = 50;
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true); 
		start();
	}	
	public void start()
	{
	  	   time = new Timer();
		   
	       time.schedule( new TimerTask() {
						public void run()
						{
							Vector3D orthogonal;
							//----------------------------------------
							switch(viewCommand)
							{
							
							case 1://up
								viewPoint=viewPoint.add(new Vector3D(0,0,1));
								break;
							case 2://down
								viewPoint=viewPoint.add(new Vector3D(0,0,-1));
								break;
							case 3://foward
								viewPoint=viewPoint.add(new Vector3D(visionVector.getX(),visionVector.getY(),0).normalize());
								break;
							case 4://backward
								viewPoint=viewPoint.add(new Vector3D(visionVector.getX(),visionVector.getY(),0).normalize().negate());
								break;
							case 5://right
								orthogonal=new Vector3D(visionVector.normalize().negate().getY(),visionVector.normalize().getX(),0);
								viewPoint=viewPoint.add(orthogonal);
								break;
							case 6://left
								orthogonal=new Vector3D(visionVector.normalize().negate().getY(),visionVector.normalize().getX(),0);
								viewPoint=viewPoint.add(orthogonal.negate());
								break;
							case 7://turnup 
								if(Math.abs((Math.pow(Math.pow(visionVector.rotVect(0,-1).getX(),2)+Math.pow(visionVector.rotVect(0,-1).getY(),2),0.5)/visionVector.rotVect(0,-1).getZ()))>0.01)
									visionVector=visionVector.rotVect(0,-1);
								break;
							case 8://turndown
								if(Math.abs((Math.pow(Math.pow(visionVector.rotVect(0,1).getX(),2)+Math.pow(visionVector.rotVect(0,1).getY(),2),0.5)/visionVector.rotVect(0,1).getZ()))>0.01)
									visionVector=visionVector.rotVect(0,1);
								break;
							case 9://turnleft 
								visionVector=visionVector.rotVect(1,0);
								break;
							case 10://turnright
								visionVector=visionVector.rotVect(-1,0);
								break;
							}
						   //----------------------------------------
						    repaint();
						}
						
					},0,sleepTime); 
	 
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		Transform tf=new Transform(viewPoint,visionVector,getWidth(),getHeight());

		Rectangle rect=new Rectangle(Math.pow(800,0.5),Math.pow(800,0.5),new Vector3D(210,300,14),new Vector3D(1,-1,0),new Vector3D(-1,-1,0));
		rect.draw(g,tf);

		Line line=new Line(new Vector3D(210,100,0),new Vector3D(0,0,1),28);
		line.draw(g,tf);
		
		Cube cube=new Cube(1,1,1,new Vector3D(10,10,0),new Vector3D(1,-1,0),new Vector3D(1,1,0));
		cube.draw(g,tf);
	}
	public void keyTyped(KeyEvent e) 
	{
		//System.out.println("keyTyped");
	}
    @Override
	public void keyPressed(KeyEvent e)
	{
		//visionVector.show();
		//t++;
		//System.out.printf("%d",t);
		int key = e.getKeyCode();
		switch(key)
		{
			case KeyEvent.VK_Q: 
				viewCommand=1;
				break;
			case KeyEvent.VK_E:
				viewCommand=2;
				break;
			case KeyEvent.VK_W: 
				viewCommand=3;
				break;
			case KeyEvent.VK_S:
				viewCommand=4;
				break;
			case KeyEvent.VK_A: 
				viewCommand=5;
				break;
			case KeyEvent.VK_D:
				viewCommand=6;
				break;
			case KeyEvent.VK_UP: 
				viewCommand=7;
				break;
			case KeyEvent.VK_DOWN:
				viewCommand=8;
				break;
			case KeyEvent.VK_LEFT: 
				viewCommand=9;
				break;
			case KeyEvent.VK_RIGHT:
				viewCommand=10;
				break;
			default:
				break;
		}
	}
    @Override
	public void keyReleased(KeyEvent e) 
	{
		//System.out.println("keyReleased");
		int key = e.getKeyCode();
		switch(key)
		{
			case KeyEvent.VK_Q: 
			case KeyEvent.VK_E:
			case KeyEvent.VK_W: 
			case KeyEvent.VK_S:
			case KeyEvent.VK_A: 
			case KeyEvent.VK_D:
			case KeyEvent.VK_UP: 
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_LEFT: 
			case KeyEvent.VK_RIGHT:
				viewCommand=0;
				break;
			default:
				break;
		}
	}
	@Override
	public void mouseDragged(MouseEvent event) {
		
		int dx,dy;
		double theta,phi;
		if(viewDrag)
		{
			System.out.println("ㄏㄏ");
			viewDragX1 = event.getX();
			viewDragY1 = event.getY();
		
			dx=viewDragX1-viewDragX0;
			dy=viewDragY1-viewDragY0;
			
			theta=Math.atan(dy/visionVector.getNorm());
			phi=Math.atan(dx/visionVector.getNorm());
			System.out.printf("%d %d",dx,dy);
			visionVector=visionVector.rotVect(10*phi,-10*theta);
			visionVector.show();
			viewDragX0=viewDragX1;
			viewDragY0=viewDragY1;
		}
	}
	@Override
	public void mouseMoved(MouseEvent event) {
		// TODO 自動產生的方法 Stub
		
	}
	@Override
	public void mouseClicked(MouseEvent event) {
		
	}
	@Override
	public void mouseEntered(MouseEvent event) {
		// TODO 自動產生的方法 Stub
		
	}
	@Override
	public void mouseExited(MouseEvent event) {
		// TODO 自動產生的方法 Stub
		
	}
	@Override
	public void mousePressed(MouseEvent event) {
		if(event.getButton()==MouseEvent.BUTTON3)
			viewDrag=true;
		viewDragX0 = event.getX();
		viewDragY0 = event.getY();
	}
	@Override
	public void mouseReleased(MouseEvent event) {
		if(event.getButton()==MouseEvent.BUTTON3)
			viewDrag=false;
	}
}