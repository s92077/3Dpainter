import java.awt.Graphics;
import java.awt.Color;
public class Rectangle extends Polygon
{
	private double length;
	private double width;
	private Vector3D vect[]=new Vector3D[4];
	public Rectangle(double length,double width,Vector3D position,Vector3D dirX,Vector3D dirY,Color color)
	{
		super(position,dirX,dirY,color);
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
	public void modifySize(Vector3D vect,int selectedAxis)
	{
		switch (selectedAxis) {
		
		case 0:
			width+=vect.dot(this.getDirX());
			break;
		case 1:
			length+=vect.dot(this.getDirY());
			break;
		case 2:
			break;
		default:
			break;
		}
		update();
	}
	public void modifyPosition(Vector3D vect,int selectedAxis)
	{
		switch (selectedAxis) {
		
		case 0:
			position=position.add(this.getDirX().normalize().scalarMultiply(vect.dot(this.getDirX())));
			break;
		case 1:
			position=position.add(this.getDirY().normalize().scalarMultiply(vect.dot(this.getDirY())));
			break;
		case 2:
			position=position.add(this.getDirZ().normalize().scalarMultiply(vect.dot(this.getDirZ())));
			break;
		default:
			break;
		}
		update();
	}
	public double getLength(){return length;}
	public double getWidth(){return width;}
}