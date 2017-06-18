import java.awt.Graphics;

import java.awt.Color;
public class Cube extends Object
{
	private double length;
	private double width;
	private double height;
	private Color color;
	private Rectangle[] Rectangle=new Rectangle[6];
	public Cube(double length,double width,double height,Vector3D position,Vector3D dirX,Vector3D dirY,Color color)
	{
		super(position,dirX,dirY);
		this.length=length;
		this.width=width;
		this.height=height;
		this.color=color;
		this.update();
	} 
	public void fill(Graphics g,Transform tf)
	{
		if(tf.getVisionVector().dot(position.add(tf.getviewPoint().negate()))>0)
		{
			for(int i=0;i<3;i++)
			{
				if(Rectangle[2*i].getPosition().add(tf.getviewPoint().negate()).getNorm()<Rectangle[2*i+1].getPosition().add(tf.getviewPoint().negate()).getNorm())
					Rectangle[2*i].fill(g,tf);
				else
					Rectangle[2*i+1].fill(g,tf);
			}
		}
	}
	public void draw(Graphics g,Transform tf)
	{
		if(tf.getVisionVector().dot(position.add(tf.getviewPoint().negate()))>0)
		{
			g.setColor(Color.BLACK);
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
	public void modifyAngle(Vector3D vect,int selectedAxis){
		switch (selectedAxis) {
		
		case 0:
			double tY=-vect.dot(this.getDirY());
			dirY=Vector3D.rotVect(this.getDirX(),this.getDirY(),tY);
			dirZ=Vector3D.rotVect(this.getDirX(),this.getDirZ(),tY);
			//width+=vect.dot(this.getDirX());
			break;
		case 1:
			double tZ=-vect.dot(this.getDirZ());
			dirZ=Vector3D.rotVect(this.getDirY(),this.getDirZ(),tZ);
			dirX=Vector3D.rotVect(this.getDirY(),this.getDirX(),tZ);
			//length+=vect.dot(this.getDirY());
			break;
		case 2:
			double tX=-vect.dot(this.getDirX());
			dirX=Vector3D.rotVect(this.getDirZ(),this.getDirX(),tX);
			dirY=Vector3D.rotVect(this.getDirZ(),this.getDirY(),tX);
			//height+=vect.dot(this.getDirZ());
			break;
		default:
			break;
		}
		update();
	}
	public void update()
	{ 
/*Z*/	Rectangle[0]=new Rectangle(length,width,position.add(dirZ.normalize().scalarMultiply(height/2)),dirX,dirY,color);
		Rectangle[1]=new Rectangle(length,width,position.add(dirZ.normalize().scalarMultiply(height/(-2))),dirX,dirY.negate(),color);
/*X*/	Rectangle[2]=new Rectangle(height,length,position.add(dirX.normalize().scalarMultiply(width/2)),dirY,dirZ,color);
		Rectangle[3]=new Rectangle(height,length,position.add(dirX.normalize().scalarMultiply(width/(-2))),dirY,dirZ.negate(),color);
/*Y*/	Rectangle[4]=new Rectangle(height,width,position.add(dirY.normalize().scalarMultiply(length/2)),dirX,dirZ,color);
		Rectangle[5]=new Rectangle(height,width,position.add(dirY.normalize().scalarMultiply(length/(-2))),dirX,dirZ.negate(),color);
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
	public double getLength(){return length;}
	public double getWidth(){return width;}
	public double getHeight(){return height;}
	
}