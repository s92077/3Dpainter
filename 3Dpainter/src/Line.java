import java.awt.Graphics;

import java.awt.Color;
public class Line extends Object
{
	private Vector3D startPoint,endPoint;
	private Color color;
	public Line(Vector3D startPoint,Vector3D endPoint,Color color)
	{
		super(startPoint.add(endPoint).scalarMultiply(0.5),endPoint.add(startPoint.negate()),new Vector3D(-1*endPoint.add(startPoint.negate()).getY(),endPoint.add(startPoint.negate()).getX(),0));
		this.color=color;
		this.startPoint=startPoint;
		this.endPoint=endPoint;
	}
	public void draw(Graphics g,Transform tf)
	{
		if(tf.getVisionVector().dot(startPoint.add(tf.getviewPoint().negate()))>0&&tf.getVisionVector().dot(endPoint.add(tf.getviewPoint().negate()))>0)
		{
			int x1,y1,x2,y2;
			Vector3D vect2D=tf.projection(startPoint);
			vect2D=tf.trans2D(vect2D);
	
			x1=(int)vect2D.getX();
			y1=(int)vect2D.getY();
			vect2D=tf.projection(endPoint);
			vect2D=tf.trans2D(vect2D);
			x2=(int)vect2D.getX();
			y2=(int)vect2D.getY();
			g.setColor(color);
			g.drawLine(x1,y1,x2,y2);
		}
	}
	public Vector3D getStartPoint(){return startPoint;}
	public Vector3D getEndPoint(){return endPoint;}
	public Double getLength(){return endPoint.add(startPoint.negate()).getNorm();}
	public void modifyLength(Vector3D vect,int selectedAxis)
	{
		//surface:(dirX.getY())x+(-dirX.getX())y=0
		//(vect.getX()+k,vect.getY()+k)
		//-k(dirX.getY()-dirX.getX())=dirX.getY()vect.getX()-dirX.getX()vect.getY()
		double k=-1*(dirX.getY()*vect.getX()-dirX.getX()*vect.getY())/(dirX.getY()-dirX.getX());
		vect=vect.add(new Vector3D(k,k,0));
		if(selectedAxis==0)
			startPoint=startPoint.add(vect);
		if(selectedAxis==1)
			endPoint=endPoint.add(vect);
		position=startPoint.add(endPoint).scalarMultiply(0.5);
	}
	public void modifyPosition(Vector3D vect,int selectedAxis)
	{
		switch (selectedAxis) {
		
		case 0:
			position=position.add(this.getDirX().normalize().scalarMultiply(vect.dot(this.getDirX().normalize())));
			startPoint=startPoint.add(this.getDirX().normalize().scalarMultiply(vect.dot(this.getDirX().normalize())));
			endPoint=endPoint.add(this.getDirX().normalize().scalarMultiply(vect.dot(this.getDirX().normalize())));
			break;
		case 1:
			position=position.add(this.getDirY().normalize().scalarMultiply(vect.dot(this.getDirY().normalize())));
			startPoint=startPoint.add(this.getDirY().normalize().scalarMultiply(vect.dot(this.getDirY().normalize())));
			endPoint=endPoint.add(this.getDirY().normalize().scalarMultiply(vect.dot(this.getDirY().normalize())));
			break;
		case 2:
			position=position.add(this.getDirZ().normalize().scalarMultiply(vect.dot(this.getDirZ().normalize())));
			startPoint=startPoint.add(this.getDirZ().normalize().scalarMultiply(vect.dot(this.getDirZ().normalize())));
			endPoint=endPoint.add(this.getDirZ().normalize().scalarMultiply(vect.dot(this.getDirZ().normalize())));
			break;
		default:
			break;
		}
	}
	public Color getColor(){return color;}
	public void setColor(Color color){this.color=color;}
	public boolean inside(int x,int y,Transform tf)
	{
		double x1,y1,x2,y2;
		
		Vector3D vect2D=tf.projection(startPoint);
		vect2D=tf.trans2D(vect2D);
		x1=vect2D.getX();
		y1=vect2D.getY();
		
		vect2D=tf.projection(endPoint);
		vect2D=tf.trans2D(vect2D);
		x2=vect2D.getX();
		y2=vect2D.getY();
		
		double[] xPoints=new double[5],yPoints=new double[5];
		double theta=Math.atan((y1-y2)/(x1-x2));
		xPoints[0]=x1+4*Math.sin(theta);
		xPoints[1]=x1-4*Math.sin(theta);
		xPoints[2]=x2-4*Math.sin(theta);
		xPoints[3]=x2+4*Math.sin(theta);
		yPoints[0]=y1+4*Math.cos(theta);
		yPoints[1]=y1-4*Math.cos(theta);
		yPoints[2]=y2-4*Math.cos(theta);
		yPoints[3]=y2+4*Math.cos(theta);
		xPoints[4]=xPoints[0];
		yPoints[4]=yPoints[0];
		if(Vector3D.inside(x, y, xPoints, yPoints))
			return true;
		else
			return false;
	}
}