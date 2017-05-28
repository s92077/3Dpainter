import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
public class Cube extends Object
{
	private double length;
	private double width;
	private double height;
	private Rectangle[] Rectangle=new Rectangle[6];
	public Cube(double length,double width,double height,Vector3D position,Vector3D dirX,Vector3D dirY)
	{
		super(position,dirX,dirY);
		this.length=length;
		this.width=width;
		this.height=height;
		this.update();
	} 
	public void draw(Graphics g,Transform tf)
	{
		if(tf.getVisionVector().dot(position.add(tf.getviewPoint().negate()))>0)
		{
			for(int i=0;i<6;i++)
				Rectangle[i].draw(g,tf);
			if(selected)
			{
				Line lines[]=new Line[3];
				lines[0]=new Line(Rectangle[0].getPosition(),Vector3D.add(Rectangle[0].getPosition(),this.getDirZ().normalize().scalarMultiply(height/2)));
				lines[1]=new Line(Rectangle[3].getPosition(),Vector3D.add(Rectangle[3].getPosition(),this.getDirX().negate().normalize().scalarMultiply(width/2)));
				lines[2]=new Line(Rectangle[5].getPosition(),Vector3D.add(Rectangle[5].getPosition(),this.getDirY().negate().normalize().scalarMultiply(length/2)));
				((Graphics2D)g).setStroke(new BasicStroke(3));
				for(int i=0;i<2;i++)
					lines[i].draw(g,tf);
			} 
		}
	}
	public void widthPlus(){width++;}
	public void update()
	{ 
/*Z*/	Rectangle[0]=new Rectangle(length,width,position.add(dirZ.normalize().scalarMultiply(height/2)),dirX,dirY);
		Rectangle[1]=new Rectangle(length,width,position.add(dirZ.normalize().scalarMultiply(height/(-2))),dirX,dirY.negate());
/*X*/	Rectangle[2]=new Rectangle(height,length,position.add(dirX.normalize().scalarMultiply(width/2)),dirY,dirZ);
		Rectangle[3]=new Rectangle(height,length,position.add(dirX.normalize().scalarMultiply(width/(-2))),dirY,dirZ.negate());
/*Y*/	Rectangle[4]=new Rectangle(height,width,position.add(dirY.normalize().scalarMultiply(length/2)),dirX,dirZ);
		Rectangle[5]=new Rectangle(height,width,position.add(dirY.normalize().scalarMultiply(length/(-2))),dirX,dirZ.negate());
	}
	public boolean inside(int x,int y,Transform tf)
	{
		for(int i=0;i<6;i++)
			if(Rectangle[i].inside(x, y, tf))
			{
				System.out.printf("%d",i);
				return true;
			}
		return false;
	}
}