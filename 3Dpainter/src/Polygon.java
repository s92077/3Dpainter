import java.awt.Graphics;

import java.awt.Color;

public class Polygon extends Object 
{
	private Vector3D points[];
	private Color color;
	public Polygon(){}
	public Polygon(Vector3D position,Vector3D dirX,Vector3D dirY,Color color)//constructor for Rectangle
	{
		super(position,dirX,dirY);
		this.color=color;
	}
	public void update(Vector3D points[])
	{
		this.points=points;
	}
	public void draw(Graphics g,Transform tf)
	{
		int[] xPoints=new int[points.length+1],yPoints=new int[points.length+1];
		for(int i=0;i<points.length;i++)
		{
			Vector3D point2D=tf.projection(points[i]);
			point2D=tf.trans2D(point2D);
			xPoints[i]=(int)point2D.getX();
			yPoints[i]=(int)point2D.getY();
		}
		xPoints[points.length]=xPoints[0];
		yPoints[points.length]=yPoints[0];
		g.setColor(Color.BLACK);
		g.drawPolygon(xPoints, yPoints,points.length+1);
	}
	public void fill(Graphics g,Transform tf)
	{
		int[] xPoints=new int[points.length+1],yPoints=new int[points.length+1];
		for(int i=0;i<points.length;i++)
		{
			Vector3D point2D=tf.projection(points[i]);
			point2D=tf.trans2D(point2D);
			xPoints[i]=(int)point2D.getX();
			yPoints[i]=(int)point2D.getY();
		}
		xPoints[points.length]=xPoints[0];
		yPoints[points.length]=yPoints[0];
		g.setColor(color);
		g.fillPolygon(xPoints, yPoints,points.length+1);
		g.setColor(Color.BLACK);
		g.drawPolygon(xPoints, yPoints,points.length+1);
	}
	public boolean inside(int x,int y,Transform tf)
	{
		double[] xPoints=new double[points.length+1],yPoints=new double[points.length+1];
		for(int i=0;i<points.length;i++)
		{
			Vector3D point2D=tf.projection(points[i]);
			point2D=tf.trans2D(point2D);
			xPoints[i]=point2D.getX();
			yPoints[i]=point2D.getY();
		}
		xPoints[points.length]=xPoints[0];
		yPoints[points.length]=yPoints[0];
		if(Vector3D.inside(x,y,xPoints, yPoints))
			return true;
		else
			return false;
	}
}
