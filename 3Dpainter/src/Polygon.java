import java.awt.Graphics;

public class Polygon extends Object 
{
	private Vector3D points[];
	public Polygon(Vector3D points[])
	{
		this.points=points;
	}
	public Polygon(Vector3D position,Vector3D dirX,Vector3D dirY)//constructor for Rectangle
	{
		super(position,dirX,dirY);
	}
	public void draw(Transform tf,Graphics g)
	{
		int[] xPoints=new int[points.length+1],yPoints=new int[points.length+1];
		for(int i=0;i<points.length;i++)
		{
			Vector3D point2D=tf.projection(points[i]);
			point2D=tf.trans2D(point2D);
			xPoints[i]=(int)point2D.getX();
			yPoints[i]=(int)point2D.getY();
		}
		xPoints[xPoints.length]=xPoints[0];
		yPoints[yPoints.length]=yPoints[0];
		g.drawPolygon(xPoints, yPoints,points.length+1);
	}
	public void fill(Transform tf,Graphics g)
	{
		int[] xPoints=new int[points.length+1],yPoints=new int[points.length+1];
		for(int i=0;i<points.length;i++)
		{
			Vector3D point2D=tf.projection(points[i]);
			point2D=tf.trans2D(point2D);
			xPoints[i]=(int)point2D.getX();
			yPoints[i]=(int)point2D.getY();
		}
		xPoints[xPoints.length]=xPoints[0];
		yPoints[yPoints.length]=yPoints[0];
		g.fillPolygon(xPoints, yPoints,points.length+1);
	}
}
