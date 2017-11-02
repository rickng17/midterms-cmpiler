import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class MyActionListener implements ActionListener{

	private Dragger dragger;
	private DraggerForTextField draggerForTextField;
	private DraggerForButton draggerForButton;
	private JTextField width;
	private JTextField height;
	
	public MyActionListener(Dragger dragger, DraggerForTextField draggerForTextField, DraggerForButton draggerForButton, JTextField width, JTextField height) {
		// TODO Auto-generated constructor stub
		this.dragger = dragger;
		this.draggerForTextField = draggerForTextField;
		this.draggerForButton = draggerForButton;
		this.width = width;
		this.height = height;
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
	}

}
