import javax.swing.JFrame;

public class Main {
	public static void main(String[] args)
	{
		Canvas p=new Canvas();
		JFrame f=new JFrame();
		f.add(p);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(500,600);
		f.setVisible(true);
	}
}
