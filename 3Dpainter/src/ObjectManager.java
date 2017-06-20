import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.Color;
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
	private PropertyPanel propertyPanel;
	private boolean fill=false;
	private boolean updateFlag=false;
	public ObjectManager(Transform tf,PropertyPanel propertyPanel)
	{
		this.propertyPanel=propertyPanel;
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
		int n=lines.size()+rectangles.size()+cubes.size();
		Object[] object=new Object[n];
		for(int i=0;i<lines.size();i++)
			object[i]=lines.get(i);
		for(int i=lines.size();i<lines.size()+rectangles.size();i++)
			object[i]=rectangles.get(i-lines.size());
		for(int i=lines.size()+rectangles.size();i<n;i++)
			object[i]=cubes.get(i-lines.size()-rectangles.size());
		for(int i=0;i<n;i++){
			for(int j=0;j<i;j++){
				if(object[j].getPosition().add(tf.getviewPoint().negate()).getNorm()<object[j+1].getPosition().add(tf.getviewPoint().negate()).getNorm()){
					Object temp=object[j];
					object[j]=object[j+1];
					object[j+1]=temp;
				}	
			}	
		}
		for(int i=0;i<n;i++)
		{
			if(object[i] instanceof Line)
			{
				Line line=(Line) object[i];
				line.draw(g, tf);
			}
			if(object[i] instanceof Rectangle)
			{
				Rectangle rectangle=(Rectangle) object[i];
				if(fill)
					rectangle.fill(g, tf);
				else
					rectangle.draw(g, tf);
			}
			if(object[i] instanceof Cube)
			{
				Cube cube=(Cube) object[i];
				if(fill)
					cube.fill(g, tf);
				else
					cube.draw(g, tf);
			}
		}
	}
	public boolean insideModifier(int x,int y)
	{	
		int n=3;
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
					modifierLines[0]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirX().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.BLUE);
					modifierLines[1]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirY().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.GREEN);
					modifierLines[2]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirZ().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.RED);
					
					for(int i=0;i<3;i++)
						modifierLines[i].draw(g,tf);
					break;
				case 2:
					modifierLines[0]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirX().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.BLUE);
					modifierLines[1]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirY().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.GREEN);
					modifierLines[2]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirZ().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.RED);
					
					for(int i=0;i<3;i++)
						modifierLines[i].draw(g,tf);
					break;
				default:
					break;
				}
			if(selectedType==1)
				switch (modifytype) {
				case 0:
					modifierLines[0]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirX().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.BLUE);
					modifierLines[1]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirY().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.GREEN);
					
					((Graphics2D)g).setStroke(new BasicStroke(3));
					for(int i=0;i<2;i++)
						modifierLines[i].draw(g,tf);
					break;
				case 1:
					modifierLines[0]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirX().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.BLUE);
					modifierLines[1]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirY().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.GREEN);
					modifierLines[2]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirZ().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.RED);
					
					((Graphics2D)g).setStroke(new BasicStroke(3));
					for(int i=0;i<3;i++)
						modifierLines[i].draw(g,tf);
					break;
				case 2:
					modifierLines[0]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirX().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.BLUE);
					modifierLines[1]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirY().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.GREEN);
					modifierLines[2]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirZ().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.RED);
					
					((Graphics2D)g).setStroke(new BasicStroke(3));
					for(int i=0;i<3;i++)
						modifierLines[i].draw(g,tf);
					break;
				default:
					break;
				}
			if(selectedType==2)
				switch (modifytype) {
				case 0:
					modifierLines[0]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirX().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.BLUE);
					modifierLines[1]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirY().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.GREEN);
					modifierLines[2]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirZ().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.RED);
					
					((Graphics2D)g).setStroke(new BasicStroke(3));
					for(int i=0;i<3;i++)
						modifierLines[i].draw(g,tf);
					break;
				case 1:
					modifierLines[0]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirX().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.BLUE);
					modifierLines[1]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirY().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.GREEN);
					modifierLines[2]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirZ().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.RED);
					
					((Graphics2D)g).setStroke(new BasicStroke(3));
					for(int i=0;i<3;i++)
						modifierLines[i].draw(g,tf);
					break;
				case 2:
					modifierLines[0]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirX().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.BLUE);
					modifierLines[1]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirY().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.GREEN);
					modifierLines[2]=new Line(object.getPosition(),Vector3D.add(object.getPosition(),object.getDirZ().normalize().scalarMultiply(50*tf.getRatio(object.getPosition()))), Color.RED);
					
					((Graphics2D)g).setStroke(new BasicStroke(3));
					for(int i=0;i<3;i++)
						modifierLines[i].draw(g,tf);
					break;
				default:
					break;
				}
		}
	}
	public void modifySelectedItemSize(int dx,int dy)
	{
		updateFlag=true;
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
		if(modifytype==2)
			switch (selectedType) {
			case 0:
				position=lines.get(selectedNumber).getPosition();
				vect=Vector3D.add(tf.get2DXNorm().scalarMultiply(dx*tf.getRatio(position)),tf.get2DYNorm().scalarMultiply(dy*tf.getRatio(position)));
				lines.get(selectedNumber).modifyPosition(vect,selectedAxis);
				break;
			case 1:
				position=rectangles.get(selectedNumber).getPosition();
				vect=Vector3D.add(tf.get2DXNorm().scalarMultiply(dx*tf.getRatio(position)),tf.get2DYNorm().scalarMultiply(dy*tf.getRatio(position)));
				rectangles.get(selectedNumber).modifyAngle(vect,selectedAxis);
				break;
			case 2:
				position=cubes.get(selectedNumber).getPosition();
				vect=Vector3D.add(tf.get2DXNorm().scalarMultiply(dx*tf.getRatio(position)),tf.get2DYNorm().scalarMultiply(dy*tf.getRatio(position)));
				//vect.show();
				cubes.get(selectedNumber).modifyAngle(vect,selectedAxis);
				break;
			default:
				break;
			}
		this.updatePropertyPanel();
		updateFlag=false;
	}
	public void select(int x,int y)
	{
		ArrayList<Integer> selectedArr=new ArrayList<Integer>();
		for(int i=0;i<lines.size();i++)
		{
			if(lines.get(i).inside(x, y, tf))
			{
				if(selectedType!=0||selectedNumber!=i)
				{
					selectedArr.add(0);
					selectedArr.add(i);
				}
			}
		}
		for(int i=0;i<rectangles.size();i++)
		{
			if(rectangles.get(i).inside(x, y, tf))
			{	
				if(selectedType!=1||selectedNumber!=i)
				{
					selectedArr.add(1);
					selectedArr.add(i);
				}
			}
		}
		for(int i=0;i<cubes.size();i++)
		{
			if(cubes.get(i).inside(x, y, tf))
			{
				if(selectedType!=2||selectedNumber!=i)
				{
					selectedArr.add(2);
					selectedArr.add(i);
				}
			}
		}
		if(selectedArr.size()>=4){
			for(int i=0;i<selectedArr.size();i+=2){
				if(selectedArr.get(i)==selectedType && selectedArr.get(i+1)>selectedNumber){
					selectedType=selectedArr.get(i);
					selectedNumber=selectedArr.get(i+1);
					this.updatePropertyPanel();
					return;
				}
				else if(selectedArr.get(i)>selectedType){
					selectedType=selectedArr.get(i);
					selectedNumber=selectedArr.get(i+1);
					this.updatePropertyPanel();
					return;
				}
			}
			selectedType=selectedArr.get(0);
			selectedNumber=selectedArr.get(1);
			this.updatePropertyPanel();
			return;
		}
		else if(selectedArr.size()==2){
			selectedType=selectedArr.get(0);
			selectedNumber=selectedArr.get(1);
			this.updatePropertyPanel();
			return;
		}
		selectedType=-1;
		selectedNumber=-1;
	}
	public void setModifytype(int modifytype){this.modifytype=modifytype;}
	public void setFill(boolean fill){this.fill=fill;}
	public void updatePropertyPanel()
	{	
		Object selectedObject= null;
		switch (selectedType) {
		case 0:
			selectedObject=lines.get(selectedNumber);
			break;
		case 1:
			selectedObject=rectangles.get(selectedNumber);
			break;
		case 2:
			selectedObject=cubes.get(selectedNumber);
			break;
		default:
			break;
		}
		propertyPanel.update(selectedObject);
	}
	public boolean getUpdateFlag(){return updateFlag;}
	public void pop()
	{
		if(selectedNumber!=-1)
		switch (selectedType) {
		case 0:
			lines.remove(selectedNumber);
			selectedType=-1;
			selectedNumber=-1;
			break;
		case 1:
			rectangles.remove(selectedNumber);
			selectedType=-1;
			selectedNumber=-1;
			break;
		case 2:
			cubes.remove(selectedNumber);
			selectedType=-1;
			selectedNumber=-1;
			break;
		default:
			break;
		
		}
	}
}
