import java.awt.Graphics;
public class Rectangle extends Polygon
{
	private double length;
	private double width;
	public Rectangle(double length,double width,Vector3D position,Vector3D dirX,Vector3D dirY)
	{
		super(position,dirX,dirY);
		this.width=width;
		this.length=length;
	}
	public void fill(Graphics g,Transform tf)
	{
		if(tf.getVisionVector().dot(position.add(tf.getviewPoint().negate()))>0)
		{
			int[] x=new int[5];
			int[] y=new int[5];
			Vector3D vect[]=new Vector3D[4];
			vect[0]=position.add(dirX.normalize().scalarMultiply(width/2));
			vect[0]=vect[0].add(dirY.normalize().scalarMultiply(length/2));
			vect[1]=position.add(dirX.normalize().scalarMultiply(width/(-2)));
			vect[1]=vect[1].add(dirY.normalize().scalarMultiply(length/2));		
			vect[2]=position.add(dirX.normalize().scalarMultiply(width/(-2)));
			vect[2]=vect[2].add(dirY.normalize().scalarMultiply(length/(-2)));
			vect[3]=position.add(dirX.normalize().scalarMultiply(width/2));
			vect[3]=vect[3].add(dirY.normalize().scalarMultiply(length/(-2)));
	
			for(int i=0;i<4;i++)
			{
				Vector3D test2D=tf.projection(vect[i]);
				test2D=tf.trans2D(test2D);
				x[i]=(int)test2D.getX();
				y[i]=(int)test2D.getY();
			}
			x[4]=x[0];y[4]=y[0];
			g.fillPolygon(x,y,5);
		}
	}
	public void draw(Graphics g,Transform tf)
	{
		if(tf.getVisionVector().dot(position.add(tf.getviewPoint().negate()))>0)
		{
			int[] x=new int[5];
			int[] y=new int[5];
			Vector3D vect[]=new Vector3D[4];
			vect[0]=position.add(dirX.normalize().scalarMultiply(width/2));
			vect[0]=vect[0].add(dirY.normalize().scalarMultiply(length/2));
			vect[1]=position.add(dirX.normalize().scalarMultiply(width/(-2)));
			vect[1]=vect[1].add(dirY.normalize().scalarMultiply(length/2));		
			vect[2]=position.add(dirX.normalize().scalarMultiply(width/(-2)));
			vect[2]=vect[2].add(dirY.normalize().scalarMultiply(length/(-2)));
			vect[3]=position.add(dirX.normalize().scalarMultiply(width/2));
			vect[3]=vect[3].add(dirY.normalize().scalarMultiply(length/(-2)));
	
			for(int i=0;i<4;i++)
			{
				Vector3D test2D=tf.projection(vect[i]);
				test2D=tf.trans2D(test2D);
				x[i]=(int)test2D.getX();
				y[i]=(int)test2D.getY();
			}
			x[4]=x[0];y[4]=y[0];
			g.drawPolygon(x,y,5);
		}
	}
}