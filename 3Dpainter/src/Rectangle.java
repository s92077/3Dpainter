import java.awt.Graphics;
public class Rectangle extends Polygon
{
	private double length;
	private double width;
	private Vector3D vect[]=new Vector3D[4];
	public Rectangle(double length,double width,Vector3D position,Vector3D dirX,Vector3D dirY)
	{
		super(position,dirX,dirY);
		this.width=width;
		this.length=length;
		update();
	}
	public void update()
	{
		vect[0]=position.add(dirX.normalize().scalarMultiply(width/2));
		vect[0]=vect[0].add(dirY.normalize().scalarMultiply(length/2));
		vect[1]=position.add(dirX.normalize().scalarMultiply(width/(-2)));
		vect[1]=vect[1].add(dirY.normalize().scalarMultiply(length/2));		
		vect[2]=position.add(dirX.normalize().scalarMultiply(width/(-2)));
		vect[2]=vect[2].add(dirY.normalize().scalarMultiply(length/(-2)));
		vect[3]=position.add(dirX.normalize().scalarMultiply(width/2));
		vect[3]=vect[3].add(dirY.normalize().scalarMultiply(length/(-2)));
		super.update(vect);
	}
	public void draw(Graphics g,Transform tf)
	{
		if(tf.getVisionVector().dot(position.add(tf.getviewPoint().negate()))>0)
		{
			super.draw(g,tf);
		}
	}
	public void fill(Graphics g,Transform tf)
	{
		if(tf.getVisionVector().dot(position.add(tf.getviewPoint().negate()))>0)
		{
			super.fill(g, tf);
		}
	}
}