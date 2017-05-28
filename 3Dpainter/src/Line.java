import java.awt.Graphics;
public class Line extends Object
{
	private Vector3D startPoint,endPoint;
	public Line(Vector3D startPoint,Vector3D endPoint)
	{
		this.startPoint=startPoint;
		this.endPoint=endPoint;
	}
	public void draw(Graphics g,Transform tf)
	{
		if(tf.getVisionVector().dot(startPoint.add(tf.getviewPoint().negate()))>0&&tf.getVisionVector().dot(endPoint.add(tf.getviewPoint().negate()))>0)
		{
			int x1,y1,x2,y2;
			//startPoint.show();
			//endPoint.show();
			Vector3D vect2D=tf.projection(startPoint);
			//vect2D.show();
			vect2D=tf.trans2D(vect2D);
	
			x1=(int)vect2D.getX();
			y1=(int)vect2D.getY();
			vect2D=tf.projection(endPoint);
			//vect2D.show();
			vect2D=tf.trans2D(vect2D);
			x2=(int)vect2D.getX();
			y2=(int)vect2D.getY();
			g.drawLine(x1,y1,x2,y2);
			//System.out.printf("%d %d %d %d\n",x1,y1,x2,y2);
		}
	}
}