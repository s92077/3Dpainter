import java.awt.Graphics;

import java.awt.Color;
public class Cube extends Object
{
	private double length;
	private double width;
	private double height;
	private Color color;
	private Rectangle[] Rectangle=new Rectangle[6];
	private Vector3D[] rectPosition=new Vector3D[6];
	private int[] index={0,1,2,3,4,5};;
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
		for(int i=0;i<6;i++)
			index[i]=i;
		/*for(int i=0;i<6;i++){
			for(int j=0;j<i;j++){
				if(Rectangle[j].getPosition().add(tf.getviewPoint().negate()).getNorm()<Rectangle[j+1].getPosition().add(tf.getviewPoint().negate()).getNorm()){
					Rectangle temp=Rectangle[j];
					Rectangle[j]=Rectangle[j+1];
					Rectangle[j+1]=temp;
					int tmp=index[j];
					index[j]=index[j+1];
					index[j+1]=tmp;
				}	
			}	
		}*/
		rectPosition[0]=position.add(dirZ.normalize().scalarMultiply(height/2));
		rectPosition[1]=position.add(dirZ.normalize().scalarMultiply(height/(-2)));
		rectPosition[2]=position.add(dirX.normalize().scalarMultiply(width/2));
		rectPosition[3]=position.add(dirX.normalize().scalarMultiply(width/(-2)));
		rectPosition[4]=position.add(dirY.normalize().scalarMultiply(length/2));
		rectPosition[5]=position.add(dirY.normalize().scalarMultiply(length/(-2)));
		if(rectPosition[0].add(tf.getviewPoint().negate()).getNorm()<rectPosition[1].add(tf.getviewPoint().negate()).getNorm())
			index[0]=0;
		else
			index[0]=1;
		if(rectPosition[2].add(tf.getviewPoint().negate()).getNorm()<rectPosition[3].add(tf.getviewPoint().negate()).getNorm())
			index[1]=2;
		else
			index[1]=3;
		if(rectPosition[4].add(tf.getviewPoint().negate()).getNorm()<rectPosition[5].add(tf.getviewPoint().negate()).getNorm())
			index[2]=4;
		else
			index[2]=5;
		for(int i=0;i<3;i++){
			for(int j=0;j<i;j++){
				if(rectPosition[index[j]].add(tf.getviewPoint().negate()).getNorm()<rectPosition[index[j+1]].add(tf.getviewPoint().negate()).getNorm()){
					int tmp=index[j];
					index[j]=index[j+1];
					index[j+1]=tmp;
				}	
			}	
		}
		if(tf.getVisionVector().dot(position.add(tf.getviewPoint().negate()))>0)
		{
			for(int i=0;i<3;i++)
				Rectangle[index[i]].fill(g,tf);
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
	public void update()
	{ 
/*Z*/	Rectangle[0]=new Rectangle(length,width,position.add(dirZ.normalize().scalarMultiply(height/2)),dirX,dirY,color);
		Rectangle[1]=new Rectangle(width,length,position.add(dirZ.normalize().scalarMultiply(height/(-2))),dirY,dirX,color);
/*X*/	Rectangle[2]=new Rectangle(height,length,position.add(dirX.normalize().scalarMultiply(width/2)),dirY,dirZ,color);
		Rectangle[3]=new Rectangle(length,height,position.add(dirX.normalize().scalarMultiply(width/(-2))),dirZ,dirY,color);
/*Y*/	Rectangle[4]=new Rectangle(width,height,position.add(dirY.normalize().scalarMultiply(length/2)),dirZ,dirX,color);
		Rectangle[5]=new Rectangle(height,width,position.add(dirY.normalize().scalarMultiply(length/(-2))),dirX,dirZ,color);
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
	public void setLength(double length){this.length=length;update();}
	public void setWidth(double width){this.width=width;update();}
	public void setHeight(double height){this.height=height;update();}
	public void setPositionX(double x){this.position=new Vector3D(x,position.getY(),position.getZ());update();}
	public void setPositionY(double y){this.position=new Vector3D(position.getX(),y,position.getZ());update();}
	public void setPositionZ(double z){this.position=new Vector3D(position.getX(),position.getY(),z);update();}
	public Color getColor(){return color;}
	public void setColor(Color color){this.color=color;update();}
}