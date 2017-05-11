import java.awt.Graphics;
public class Line extends Object
{
	private double length;
	public Line(Vector3D position,Vector3D dir,double length)
	{
		super(position,dir,dir);
		this.length=length;
	}
	public void draw(Graphics g,Transform tf)
	{
		if(tf.getVisionVector().dot(position.add(tf.getviewPoint().negate()))>0)
		{
			int x1,y1,x2,y2;
			Vector3D vect[]=new Vector3D[2];
			vect[0]=position.add(dirX.normalize().scalarMultiply(length/2));
			vect[1]=position.add(dirX.normalize().scalarMultiply(length/(-2)));
			//vect[0].show();
			//vect[1].show();
			Vector3D vect2D=tf.projection(vect[0]);
			//vect2D.show();
			vect2D=tf.trans2D(vect2D);
	
			x1=(int)vect2D.getX();
			y1=(int)vect2D.getY();
			vect2D=tf.projection(vect[1]);
			//vect2D.show();
			vect2D=tf.trans2D(vect2D);
			x2=(int)vect2D.getX();
			y2=(int)vect2D.getY();
			g.drawLine(x1,y1,x2,y2);
			//System.out.printf("%d %d %d %d\n",x1,y1,x2,y2);
		}
	}
	public double getlength(){return length;}
	
}