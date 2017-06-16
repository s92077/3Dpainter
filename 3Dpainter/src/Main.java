import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Main {
	public static void main(String[] args)
	{
		Canvas canvas=new Canvas();
		CommandPanel commandPanel=new CommandPanel(canvas); 
		PropertyPanel propertyPanel=new PropertyPanel(canvas);
		JFrame f=new JFrame();
		f.setLayout(new BorderLayout());
		f.add(canvas,BorderLayout.CENTER);
		f.add(commandPanel,BorderLayout.SOUTH);
		f.add(propertyPanel,BorderLayout.EAST);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(500,600);
		f.setVisible(true);
	}
}
