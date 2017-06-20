import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CommandPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 570570707666000256L;
	private Canvas canvas;
	private JButton[] objectButtons,commandButtons;
	public CommandPanel(Canvas canvas)
	{
		this.canvas=canvas;
		objectButtons=new JButton[3];
		objectButtons[0]=new JButton("line");
		objectButtons[1]=new JButton("rectangle");
		objectButtons[2]=new JButton("cube");
		commandButtons=new JButton[2];
		commandButtons[0]=new JButton("Size");
		commandButtons[1]=new JButton("Draw");
		setLayout(new GridLayout(1,objectButtons.length+commandButtons.length,2,2)); 
		for(int i=0;i<objectButtons.length;i++)
			objectButtons[i].addActionListener(this);
		for(int i=0;i<objectButtons.length;i++)
			this.add(objectButtons[i]);
		for(int i=0;i<commandButtons.length;i++)
			commandButtons[i].addActionListener(this);
		for(int i=0;i<commandButtons.length;i++)
			this.add(commandButtons[i]);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO 自動產生的方法 Stub
		for(int i=0;i<objectButtons.length;i++)
			if(arg0.getSource()==objectButtons[i])
				canvas.addObject(i);
		if(arg0.getSource()==commandButtons[0]){
			if(commandButtons[0].getText().equals("Size"))
			{
				canvas.setModifytype(1);
				commandButtons[0].setText("Position");				
			}
			else if(commandButtons[0].getText().equals("Position"))
			{
				canvas.setModifytype(2);
				commandButtons[0].setText("Angle");
			}
			else if(commandButtons[0].getText().equals("Angle"))
			{
				canvas.setModifytype(0);
				commandButtons[0].setText("Size");
			}
		}
		if(arg0.getSource()==commandButtons[1]){
			if(commandButtons[1].getText().equals("Fill"))
			{
				canvas.setFill(false);;
				commandButtons[1].setText("Draw");				
			}
			else if(commandButtons[1].getText().equals("Draw"))
			{
				canvas.setFill(true);;
				commandButtons[1].setText("Fill");
			}
		}
		canvas.requestFocusInWindow();
	}

}
