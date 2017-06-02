import java.awt.Graphics;
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
			height+=vect.dot(this.getDirZ());
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
			position=position.add(this.getDirZ().normalize().scalarMultiply(vect.dot(this.getDirZ().normalize())));
			break;
		default:
			break;
		}
		update();
	}
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
				return true;
			}
		return false;
	}
}