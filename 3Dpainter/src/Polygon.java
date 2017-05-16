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
		int[] xPoints=new int[points.length],yPoints=new int[points.length];
		for(int i=0;i<points.length;i++)
		{
			xPoints[i]=(int)points[i].getX();
			yPoints[i]=(int)points[i].getY();
		}
		g.drawPolygon(xPoints, yPoints,points.length);
	}
	public void fill(Transform tf,Graphics g)
	{
		int[] xPoints=new int[points.length],yPoints=new int[points.length];
		for(int i=0;i<points.length;i++)
		{
			xPoints[i]=(int)points[i].getX();
			yPoints[i]=(int)points[i].getY();
		}
		g.fillPolygon(xPoints, yPoints,points.length);
	}
}
