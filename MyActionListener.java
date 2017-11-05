import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MyActionListener implements ActionListener{

	private Dragger dragger;
	private DraggerForTextField draggerForTextField;
	private DraggerForButton draggerForButton;
	private JTextField width;
	private JTextField height;
	private JTextField text;
	
	public MyActionListener(Dragger dragger, DraggerForTextField draggerForTextField, DraggerForButton draggerForButton, JTextField width, JTextField height, JTextField text) {
		// TODO Auto-generated constructor stub
		this.dragger = dragger;
		this.draggerForTextField = draggerForTextField;
		this.draggerForButton = draggerForButton;
		this.width = width;
		this.height = height;
		this.text = text;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Component c = dragger.getComponent();
		if(c == null){
			c = draggerForTextField.getComponent();
			System.out.println(c);
		}
		if(c == null){
			c = draggerForButton.getComponent();
			System.out.println(c);
		}
		try{
		c.setBounds(c.getX(), c.getY(), Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));
		}catch(Exception exception){
			
		}
		try{
			if(c instanceof JLabel){
				((JLabel) c).setText(text.getText());
			}
			else if(c instanceof JTextField){
				((JTextField) c).setText(text.getText());
			}
			else if(c instanceof JButton){System.out.println("l");
				((JButton) c).setText(text.getText());
			}
		}catch(Exception exception){
			
		}
	}

}
