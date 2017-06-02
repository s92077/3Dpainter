import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
public class ObjectManager {
	private Transform tf;
	private ArrayList<Line> lines;
	private ArrayList<Rectangle> rectangles;
	private ArrayList<Cube> cubes;
	private int selectedType=-1;
	private int selectedNumber=-1;
	private int modifytype=0;//size:0 position:1 angle:2
	private Line modifierLines[]=new Line[3];
	private int selectedAxis=-1;//0:x 1:y 2:z
	public ObjectManager(Transform tf)
	{
		this.tf=tf;
		lines=new ArrayList<Line>();
		rectangles=new ArrayList<Rectangle>();
		cubes=new ArrayList<Cube>();
	}
	public void addLine(Line line){lines.add(line);}
	public void addRectangle(Rectangle rectangle){rectangles.add(rectangle);}
	public void addCube(Cube cube){cubes.add(cube);}
	public void updateTransform(Transform tf){this.tf=tf;}
	public void draw(Graphics g)
	{
		for(int i=0;i<lines.size();i++)
			lines.get(i).draw(g, tf);
		for(int i=0;i<rectangles.size();i++)
			rectangles.get(i).draw(g, tf);
		for(int i=0;i<cubes.size();i++)
			cubes.get(i).draw(g, tf);
	}
	public boolean insideModifier(int x,int y)
	{	int n=3;
		switch (selectedType) {
		case 0:
			if(modifytype==0)
				n=0;
			if(modifytype==1)
				n=3;
			break;
		case 1:
			if(modifytype==0)
				n=2;
			if(modifytype==1)
				n=3;
			break;
		case 2:
			n=3;
			break;
		default:
			break;
		}
		if(modifierLines[0]!=null)
			for(int i=0;i<n;i++)
				if(modifierLines[i].inside(x, y, tf))
				{	
					selectedAxis=i;
					return true;
				}
		if(n==0)
		{
			int x0,y0,x1,y1;
			Vector3D vect2D=tf.projection(lines.get(selectedNumber).getStartPoint());
			vect2D=tf.trans2D(vect2D);
	
			x0=(int)vect2D.getX();
			y0=(int)vect2D.getY();
			vect2D=tf.projection(lines.get(selectedNumber).getEndPoint());
			vect2D=tf.trans2D(vect2D);
			x1=(int)vect2D.getX();
			y1=(int)vect2D.getY();
			
			if((Math.pow(x-x0,2)+Math.pow(y-y0,2))<4)
			{
				selectedAxis=0;
				return true;
			}
			if((Math.pow(x-x1,2)+Math.pow(y-y1,2))<4)
			{	
				selectedAxis=1;
				return true;
			}
		}
		return false;
	}
	public void drawModifier(Graphics g)
	{
		Object object = null;
		switch (selectedType) {
		case 0:
			object=lines.get(selectedNumber);
			break;
		case 1:
			object=rectangles.get(selectedNumber);
			break;
		case 2:
			object=cubes.get(selectedNumber);
			break;
		default:
			break;
		}
		
		if(object!=null){
			if(selectedType==0)
				switch (modifytype) {
				case 0:
					int x1,y1,x2,y2;
					Vector3D vect2D=tf.projection(lines.get(selectedNumber).getStartPoint());
					vect2D=tf.trans2D(vect2D);
			
					x1=(int)vect2D.getX();
					y1=(int)vect2D.getY();
					vect2D=tf.projection(lines.get(selectedNumber).getEndPoint());
					vect2D=tf.trans2D(vect2D);
					x2=(int)vect2D.getX();
					y2=(int)vect2D.getY();
					g.drawArc(x1-2, y1-2,4,4,0,360);
					g.drawArc(x2-2, y2-2,4,4,0,360);
					
					break;
				case 1:
					modifierLines[0]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirX().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))));
					modifierLines[1]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirY().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))));
					modifierLines[2]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirZ().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))));
					
					for(int i=0;i<3;i++)
						modifierLines[i].draw(g,tf);
					break;
				case 2:
					
					break;
				default:
					break;
				}
			if(selectedType==1)
				switch (modifytype) {
				case 0:
					modifierLines[0]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirX().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))));
					modifierLines[1]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirY().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))));
					
					((Graphics2D)g).setStroke(new BasicStroke(3));
					for(int i=0;i<2;i++)
						modifierLines[i].draw(g,tf);
					break;
				case 1:
					modifierLines[0]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirX().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))));
					modifierLines[1]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirY().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))));
					modifierLines[2]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirZ().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))));
					
					((Graphics2D)g).setStroke(new BasicStroke(3));
					for(int i=0;i<3;i++)
						modifierLines[i].draw(g,tf);
					break;
				case 2:
					
					break;
				default:
					break;
				}
			if(selectedType==2)
				switch (modifytype) {
				case 0:
					modifierLines[0]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirX().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))));
					modifierLines[1]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirY().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))));
					modifierLines[2]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirZ().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))));
					
					((Graphics2D)g).setStroke(new BasicStroke(3));
					for(int i=0;i<3;i++)
						modifierLines[i].draw(g,tf);
					break;
				case 1:
					modifierLines[0]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirX().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))));
					modifierLines[1]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirY().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))));
					modifierLines[2]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirZ().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))));
					
					((Graphics2D)g).setStroke(new BasicStroke(3));
					for(int i=0;i<3;i++)
						modifierLines[i].draw(g,tf);
					break;
				case 2:
					
					break;
				default:
					break;
				}
		}
	}
	public void modifySelectedItemSize(int dx,int dy)
	{
		Vector3D vect,position;
		if(modifytype==0)
			switch (selectedType) {
			case 0:
				position=lines.get(selectedNumber).getPosition();
				vect=Vector3D.add(tf.get2DXNorm().scalarMultiply(dx*tf.getRatio(position)),tf.get2DYNorm().scalarMultiply(dy*tf.getRatio(position)));
				lines.get(selectedNumber).modifyLength(vect,selectedAxis);
				break;
			case 1:
				position=rectangles.get(selectedNumber).getPosition();
				vect=Vector3D.add(tf.get2DXNorm().scalarMultiply(dx*tf.getRatio(position)),tf.get2DYNorm().scalarMultiply(dy*tf.getRatio(position)));
				rectangles.get(selectedNumber).modifySize(vect,selectedAxis);
				break;
			case 2:
				position=cubes.get(selectedNumber).getPosition();
				vect=Vector3D.add(tf.get2DXNorm().scalarMultiply(dx*tf.getRatio(position)),tf.get2DYNorm().scalarMultiply(dy*tf.getRatio(position)));
				cubes.get(selectedNumber).modifySize(vect,selectedAxis);
				break;
			default:
				break;
			}
		if(modifytype==1)
			switch (selectedType) {
			case 0:
				position=lines.get(selectedNumber).getPosition();
				vect=Vector3D.add(tf.get2DXNorm().scalarMultiply(dx*tf.getRatio(position)),tf.get2DYNorm().scalarMultiply(dy*tf.getRatio(position)));
				lines.get(selectedNumber).modifyPosition(vect,selectedAxis);
				break;
			case 1:
				position=rectangles.get(selectedNumber).getPosition();
				vect=Vector3D.add(tf.get2DXNorm().scalarMultiply(dx*tf.getRatio(position)),tf.get2DYNorm().scalarMultiply(dy*tf.getRatio(position)));
				rectangles.get(selectedNumber).modifyPosition(vect,selectedAxis);
				break;
			case 2:
				position=cubes.get(selectedNumber).getPosition();
				vect=Vector3D.add(tf.get2DXNorm().scalarMultiply(dx*tf.getRatio(position)),tf.get2DYNorm().scalarMultiply(dy*tf.getRatio(position)));
				//vect.show();
				cubes.get(selectedNumber).modifyPosition(vect,selectedAxis);
				break;
			default:
				break;
			}
	}
	public void select(int x,int y)
	{
		switch (selectedType) {
		case 0:
			lines.get(selectedNumber).unSeleced();
			break;
		case 1:
			rectangles.get(selectedNumber).unSeleced();
			break;
		case 2:
			cubes.get(selectedNumber).unSeleced();
			break;
		default:
			break;
		}
		selectedType=-1;
		selectedNumber=-1;
		for(int i=0;i<lines.size();i++)
		{
			if(lines.get(i).inside(x, y, tf))
			{
				lines.get(i).setSeleced();
				selectedType=0;
				selectedNumber=i;
				return;
			}
		}
		for(int i=0;i<rectangles.size();i++)
		{
			if(rectangles.get(i).inside(x, y, tf))
			{
				rectangles.get(i).setSeleced();
				selectedType=1;
				selectedNumber=i;
				return;
			}
		}
		for(int i=0;i<cubes.size();i++)
		{
			if(cubes.get(i).inside(x, y, tf))
			{
				cubes.get(i).setSeleced();
				selectedType=2;
				selectedNumber=i;
				return;
			}
		}
	}
	public void setModifytype(int modifytype){this.modifytype=modifytype;}
}
