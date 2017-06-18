import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
public class PropertyPanel extends JPanel implements KeyListener
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
		addKeyListener(this);
	}
	public void update(Object object)
	{
		this.object=object;
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
	public void modifyObject(int x)
	{
		System.out.println(x);
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
	@Override
	public void keyPressed(KeyEvent e)
	{
		System.out.println("= =");
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動產生的方法 Stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動產生的方法 Stub
		
	}
}
