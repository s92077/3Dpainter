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
	private boolean modifying=false;
	private int modifyingX0,modifyingY0,modifyingX1,modifyingY1;
	private int viewDragX0,viewDragY0,viewDragX1,viewDragY1;
	private ObjectManager objectManager;
	private Transform tf=new Transform(viewPoint, visionVector,getWidth(),getWidth());
	public Canvas(PropertyPanel propertyPanel)
	{
		
		objectManager=new ObjectManager(tf,propertyPanel);
		//addObject(0);
		//addObject(1);
		addObject(2);
		setVisible(true);
		setSize(500,600); 
		sleepTime = 50;
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true); 
		this.requestFocusInWindow();
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
									visionVector=visionVector.rotVect(0,-0.2);
								break;
							case 8://turndown
								if(Math.abs((Math.pow(Math.pow(visionVector.rotVect(0,1).getX(),2)+Math.pow(visionVector.rotVect(0,1).getY(),2),0.5)/visionVector.rotVect(0,1).getZ()))>0.01)
									visionVector=visionVector.rotVect(0,0.2);
								break;
							case 9://turnleft 
								visionVector=visionVector.rotVect(0.2,0);
								break;
							case 10://turnright
								visionVector=visionVector.rotVect(-0.2,0);
								break;
							}
						   //----------------------------------------
							tf=new Transform(viewPoint,visionVector,getWidth(),getHeight());
							objectManager.updateTransform(tf);
							setFocusable(true);
							if(!objectManager.getUpdateFlag())
								repaint();
						}
						
					},0,sleepTime); 
	 
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		setDoubleBuffered(true);
		objectManager.draw(g);
		objectManager.drawModifier(g);
	}
	public void setModifytype(int modifytype){objectManager.setModifytype(modifytype);}
	public void setFill(boolean fill){objectManager.setFill(fill);;}
	public void addObject(int objectType)
	{
		switch (objectType) {
		case 0:
			objectManager.addLine(new Line(viewPoint.add(visionVector.scalarMultiply(0.01)).add(new Vector3D(visionVector.getY(),-visionVector.getX(),100).normalize()), viewPoint.add(visionVector.scalarMultiply(0.01)).add(new Vector3D(visionVector.getY(),-visionVector.getX(),0).normalize().negate()), Color.BLACK));
			break;
		case 1:
			objectManager.addRectangle(new Rectangle(20,20, viewPoint.add(visionVector.scalarMultiply(0.1)),new Vector3D(0,0,1),new Vector3D(-1,1,0),Color.BLUE));
			break;
		case 2:
			objectManager.addCube(new Cube(10,10,10,viewPoint.add(visionVector.scalarMultiply(0.1)),new Vector3D(-1,1,0),new Vector3D(-1,-1,0),Color.BLUE));
			break;
		default:
			break;
		}
	}
	public void keyTyped(KeyEvent e) {}
    @Override
	public void keyPressed(KeyEvent e)
	{
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
			viewDragX1 = event.getX();
			viewDragY1 = event.getY();
		
			dx=viewDragX1-viewDragX0;
			dy=viewDragY1-viewDragY0;
			
			theta=Math.atan(dy/visionVector.getNorm());
			phi=Math.atan(dx/visionVector.getNorm());

			visionVector=visionVector.rotVect(20*phi,-20*theta);

			viewDragX0=viewDragX1;
			viewDragY0=viewDragY1;
		}
		if(modifying)
		{
			modifyingX1 = event.getX();
			modifyingY1 = event.getY();
		
			dx=modifyingX1-modifyingX0;
			dy=modifyingY1-modifyingY0;
			objectManager.modifySelectedItemSize(dx, dy);
			modifyingX0=modifyingX1;
			modifyingY0=modifyingY1;
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
		setFocusable(true); 
		this.requestFocusInWindow();
		if(event.getButton()==MouseEvent.BUTTON3)
		{	
			viewDrag=true;
			viewDragX0 = event.getX();
			viewDragY0 = event.getY();
		}
		if(event.getButton()==MouseEvent.BUTTON1)
		{
			if(objectManager.insideModifier(event.getX(),event.getY()))
			{
				modifying=true;
				modifyingX0 = event.getX();
				modifyingY0 = event.getY();
			}
			else
			{
				objectManager.select(event.getX(),event.getY());
			}
		}
	}
	@Override
	public void mouseReleased(MouseEvent event) {
		if(event.getButton()==MouseEvent.BUTTON3)
			viewDrag=false;
		if(event.getButton()==MouseEvent.BUTTON1)
			modifying=false;
	}
}