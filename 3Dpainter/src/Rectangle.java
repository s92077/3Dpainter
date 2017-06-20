import java.awt.Graphics;
import java.awt.Color;
public class Rectangle extends Polygon
{
	private double length;
	private double width;
	private Vector3D vect[]=new Vector3D[4];
	private Color color;
	public Rectangle(double length,double width,Vector3D position,Vector3D dirX,Vector3D dirY,Color color)
	{
		super(position,dirX,dirY,color);
		this.color=color;
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
		super.update(vect,color);
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
	public void modifyAngle(Vector3D vect,int selectedAxis){
		double tY=-vect.dot(this.getDirY());
		double tZ=-vect.dot(this.getDirZ());
		double tX=-vect.dot(this.getDirX());
		double delta;
		switch (selectedAxis) {
		
		case 0:
			if(Math.signum(tZ)>=0){
				delta=-Math.sqrt(tY*tY+tZ*tZ);
			}
			else{
				delta=Math.sqrt(tY*tY+tZ*tZ);
			}
			
			dirY=Vector3D.rotVect(this.getDirX(),this.getDirY(),delta);
			dirZ=Vector3D.rotVect(this.getDirX(),this.getDirZ(),delta);
			break;
		case 1:
			if(Math.signum(tX)>=0){
				delta=-Math.sqrt(tX*tX+tZ*tZ);
			}
			else{
				delta=Math.sqrt(tX*tX+tZ*tZ);
			}
			dirZ=Vector3D.rotVect(this.getDirY(),this.getDirZ(),delta);
			dirX=Vector3D.rotVect(this.getDirY(),this.getDirX(),delta);
			break;
		case 2:
			if(Math.signum(tY)>=0){
				delta=-Math.sqrt(tY*tY+tX*tX);
			}
			else{
				delta=Math.sqrt(tY*tY+tX*tX);
			}
			dirX=Vector3D.rotVect(this.getDirZ(),this.getDirX(),delta);
			dirY=Vector3D.rotVect(this.getDirZ(),this.getDirY(),delta);
			break;
		default:
			break;
		}
		update();
	}
	public double getLength(){return length;}
	public double getWidth(){return width;}
	public void setLength(double length){this.length=length;update();}
	public void setWidth(double width){this.width=width;update();}
	public void setPositionX(double x){this.position=new Vector3D(x,position.getY(),position.getZ());update();}
	public void setPositionY(double y){this.position=new Vector3D(position.getX(),y,position.getZ());update();}
	public void setPositionZ(double z){this.position=new Vector3D(position.getX(),position.getY(),z);update();}
	public Color getColor(){return color;}
	public void setColor(Color color){this.color=color;update();}
}