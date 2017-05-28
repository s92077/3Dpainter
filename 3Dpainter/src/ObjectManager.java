import java.util.ArrayList;
import java.awt.Graphics;
public class ObjectManager {
	private Transform tf;
	private ArrayList<Line> lines;
	private ArrayList<Rectangle> rectangles;
	private ArrayList<Cube> cubes;
	private int selectedType=-1;
	private int selectedNumber=-1;
	private int selectedAxis=-1;//0:x 1:y 2:z
	public ObjectManager(Transform tf)
	{
		this.tf=tf;
		lines=new ArrayList<Line>();
		rectangles=new ArrayList<Rectangle>();
		cubes=new ArrayList<Cube>();
		lines.add(new Line(new Vector3D(200,200,0),new Vector3D(200,200,10)));
		rectangles.add(new Rectangle(10,10,new Vector3D(200,200,0),new Vector3D(1,-1,0),new Vector3D(0,0,1)));
		cubes.add(new Cube(30,30,30,new Vector3D(200,200,0),new Vector3D(1,-1,0),new Vector3D(1,1,0)));
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
	public void modifySelectedItemSize()
	{
		switch (selectedType) {
		case 0:
			Line line=lines.get(selectedNumber);
			line.unSeleced();
			lines.set(selectedNumber,line);
			break;
		case 1:
			Rectangle rectangle=rectangles.get(selectedNumber);
			rectangle.unSeleced();
			rectangles.set(selectedNumber,rectangle);
			break;
		case 2:
			Cube cube=cubes.get(selectedNumber);
			cube.unSeleced();
			cubes.set(selectedNumber,cube);
			break;
		default:
			break;
		}
	}
	public void select(int x,int y)
	{
		switch (selectedType) {
		case 0:
			Line line=lines.get(selectedNumber);
			line.unSeleced();
			lines.set(selectedNumber,line);
			break;
		case 1:
			Rectangle rectangle=rectangles.get(selectedNumber);
			rectangle.unSeleced();
			rectangles.set(selectedNumber,rectangle);
			break;
		case 2:
			Cube cube=cubes.get(selectedNumber);
			cube.unSeleced();
			cubes.set(selectedNumber,cube);
			break;
		default:
			break;
		}
		selectedType=-1;
		selectedNumber=-1;
		for(int i=0;i<rectangles.size();i++)
		{
			if(rectangles.get(i).inside(x, y, tf))
			{
				Rectangle rectangle=rectangles.get(i);
				rectangle.setSeleced();
				rectangles.set(i,rectangle);
				selectedType=1;
				selectedNumber=i;
				return;
			}
		}
		for(int i=0;i<cubes.size();i++)
		{
			if(cubes.get(i).inside(x, y, tf))
			{
				Cube cube=cubes.get(i);
				cube.setSeleced();
				cubes.set(i,cube);
				selectedType=2;
				selectedNumber=i;
				return;
			}
		}
	}
}
