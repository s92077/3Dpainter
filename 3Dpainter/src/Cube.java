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
			Surface[] surface=new Surface[6];
/*Z*/		surface[0]=new Surface(length,width,position.add(dirZ.normalize().scalarMultiply(height/2)),dirX,dirY);
			surface[1]=new Surface(length,width,position.add(dirZ.normalize().scalarMultiply(height/(-2))),dirX,dirY.negate());
			surface[0].draw(g,tf);
			surface[1].draw(g,tf);
/*X*/		surface[2]=new Surface(height,length,position.add(dirX.normalize().scalarMultiply(width/2)),dirY,dirZ);
			surface[3]=new Surface(height,length,position.add(dirX.normalize().scalarMultiply(width/(-2))),dirY,dirZ.negate());
			surface[2].draw(g,tf);
			surface[3].draw(g,tf);
/*X*/		surface[4]=new Surface(height,width,position.add(dirY.normalize().scalarMultiply(length/2)),dirX,dirZ);
			surface[5]=new Surface(height,width,position.add(dirY.normalize().scalarMultiply(length/(-2))),dirX,dirZ.negate());
			surface[4].draw(g,tf);
			surface[5].draw(g,tf);
		}
	}
}