import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class PropertyPanel extends JPanel
{
	private JLabel position,size;
	private JLabel[] positionLabel,sizeLabel;
	private JTextField[] positionTextField,sizeTextField;
	private Canvas canvas;
	private Timer time;
	private int sleepTime;
	public PropertyPanel(Canvas canvas)
	{
		this.canvas=canvas;
		sleepTime=50;
		position=new JLabel("position ");
		size=new JLabel("size ");
		positionLabel=new JLabel[3];
		sizeLabel=new JLabel[3];
		positionLabel[0]=new JLabel("x:");
		positionLabel[1]=new JLabel("y:");
		positionLabel[2]=new JLabel("z:");
		sizeLabel[0]=new JLabel("width:");
		sizeLabel[1]=new JLabel("length:");
		sizeLabel[2]=new JLabel("height:");
		positionTextField=new JTextField[3];
		sizeTextField=new JTextField[3];
		for(int i=0;i<3;i++)
		{
			positionTextField[i]=new JTextField();
			sizeTextField[i]=new JTextField();
		}
		this.setLayout(new GridLayout(14,1));
		this.add(position);
		for(int i=0;i<3;i++)
		{
			this.add(positionLabel[i]);
			this.add(positionTextField[i]);
		}
		this.add(size);
		for(int i=0;i<3;i++)
		{
			this.add(sizeLabel[i]);
			this.add(sizeTextField[i]);
		}
		start();
	}
	public void start()
	{
	  	   time = new Timer();
		   
	       time.schedule( new TimerTask() {
						public void run()
						{
							update();
						}
						
					},0,sleepTime); 
	 
	}
	public void update()
	{
		Object object=canvas.getSelectedObject();
		if(object!=null)
		{
			positionTextField[0].setText(Double.toString(object.getPosition().getX()));
			positionTextField[1].setText(Double.toString(object.getPosition().getY()));
			positionTextField[2].setText(Double.toString(object.getPosition().getZ()));
			if (object instanceof Rectangle) {
				Rectangle rectangle = (Rectangle) object;
				sizeTextField[0].setText(Double.toString(rectangle.getWidth()));
				sizeTextField[1].setText(Double.toString(rectangle.getLength()));
			}
			if (object instanceof Cube) {
				Cube cube = (Cube) object;
				sizeTextField[0].setText(Double.toString(cube.getWidth()));
				sizeTextField[1].setText(Double.toString(cube.getLength()));
				sizeTextField[2].setText(Double.toString(cube.getHeight()));
			}
		}
		
	}
}
