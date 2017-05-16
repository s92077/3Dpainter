import java.awt.Graphics;
public class Cube extends Object
{
	private double length;
	private double width;
	private double height;
	public Cube(double length,double width,double height,Vector3D position,Vector3D dirX,Vector3D dirY)
	{
		super(position,dirX,dirY);
		this.length=length;
		this.width=width;
		this.height=height;
	} 
	public void draw(Graphics g,Transform tf)
	{
		if(tf.getVisionVector().dot(position.add(tf.getviewPoint().negate()))>0)
		{
			Rectangle[] Rectangle=new Rectangle[6];
/*Z*/		Rectangle[0]=new Rectangle(length,width,position.add(dirZ.normalize().scalarMultiply(height/2)),dirX,dirY);
			Rectangle[1]=new Rectangle(length,width,position.add(dirZ.normalize().scalarMultiply(height/(-2))),dirX,dirY.negate());
			Rectangle[0].draw(g,tf);
			Rectangle[1].draw(g,tf);
/*X*/		Rectangle[2]=new Rectangle(height,length,position.add(dirX.normalize().scalarMultiply(width/2)),dirY,dirZ);
			Rectangle[3]=new Rectangle(height,length,position.add(dirX.normalize().scalarMultiply(width/(-2))),dirY,dirZ.negate());
			Rectangle[2].draw(g,tf);
			Rectangle[3].draw(g,tf);
/*X*/		Rectangle[4]=new Rectangle(height,width,position.add(dirY.normalize().scalarMultiply(length/2)),dirX,dirZ);
			Rectangle[5]=new Rectangle(height,width,position.add(dirY.normalize().scalarMultiply(length/(-2))),dirX,dirZ.negate());
			Rectangle[4].draw(g,tf);
			Rectangle[5].draw(g,tf);
		}
	}
}