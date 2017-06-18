import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class PropertyPanel extends JPanel
{
	private JLabel position,size;
	private JLabel[] positionLabel,sizeLabel;
	private JTextField[] positionTextField,sizeTextField;
	private Object object;
	public PropertyPanel()
	{
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
			positionTextField[i].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                	for(int i=0;i<3;i++)
                	{
                		if(e.getSource()==positionTextField[i])
                			modifyObject(i);
                	}
                }});
			sizeTextField[i]=new JTextField();
			sizeTextField[i].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                	for(int i=0;i<3;i++)
                	{
                		if(e.getSource()==sizeTextField[i])
                			modifyObject(i+3);
                	}
                }});
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
	}
	public void update(Object object)
	{
		this.object=object;
		if(object!=null)
		{
			positionTextField[0].setText(Double.toString(object.getPosition().getX()).substring(0, Math.min(Double.toString(object.getPosition().getX()).length(),6)));
			positionTextField[1].setText(Double.toString(object.getPosition().getY()).substring(0, Math.min(Double.toString(object.getPosition().getY()).length(),6)));
			positionTextField[2].setText(Double.toString(object.getPosition().getZ()).substring(0, Math.min(Double.toString(object.getPosition().getZ()).length(),6)));
			if (object instanceof Rectangle) {
				Rectangle rectangle = (Rectangle) object;
				sizeTextField[0].setText(Double.toString(rectangle.getWidth()).substring(0, Math.min(Double.toString(rectangle.getWidth()).length(),6)));
				sizeTextField[1].setText(Double.toString(rectangle.getLength()).substring(0, Math.min(Double.toString(rectangle.getLength()).length(),6)));
			}
			if (object instanceof Cube) {
				Cube cube = (Cube) object;
				sizeTextField[0].setText(Double.toString(cube.getWidth()).substring(0, Math.min(Double.toString(cube.getWidth()).length(),6)));
				sizeTextField[1].setText(Double.toString(cube.getLength()).substring(0, Math.min(Double.toString(cube.getLength()).length(),6)));
				sizeTextField[2].setText(Double.toString(cube.getHeight()).substring(0, Math.min(Double.toString(cube.getHeight()).length(),6)));
			}
		}
	}
	public void modifyObject(int x)
	{
		System.out.println(x);
		if (object instanceof Rectangle) {
			Rectangle rectangle = (Rectangle) object;
			switch (x) {
			case 0:
				rectangle.setPositionX(Double.parseDouble(positionTextField[x].getText()));
				break;
			case 1:
				rectangle.setPositionY(Double.parseDouble(positionTextField[x].getText()));
				break;
			case 2:
				rectangle.setPositionZ(Double.parseDouble(positionTextField[x].getText()));
				break;
			case 3:
				rectangle.setWidth(Double.parseDouble(sizeTextField[x-3].getText()));
				break;
			case 4:
				rectangle.setLength(Double.parseDouble(sizeTextField[x-3].getText()));
				break;
			default:
				break;
			}
		}
		if (object instanceof Cube) {
			Cube cube = (Cube) object;
			switch (x) {
			case 0:
				cube.setPositionX(Double.parseDouble(positionTextField[x].getText()));
				break;
			case 1:
				cube.setPositionY(Double.parseDouble(positionTextField[x].getText()));
				break;
			case 2:
				cube.setPositionZ(Double.parseDouble(positionTextField[x].getText()));
				break;
			case 3:
				cube.setWidth(Double.parseDouble(sizeTextField[x-3].getText()));
				break;
			case 4:
				cube.setLength(Double.parseDouble(sizeTextField[x-3].getText()));
				break;
			case 5:
				cube.setHeight(Double.parseDouble(sizeTextField[x-3].getText()));
				break;
			default:
				break;
			}
		}
	}
}
