import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class DraggerForTextField extends MouseAdapter{
	public void setComponent(Component component) {
		this.component = component;
	}

	private JTextField tf;
	private Component component; 
	private Font font = new Font(null, 0, 20);
	
	public DraggerForTextField(JTextField tf) {
		// TODO Auto-generated constructor stub
		this.tf = tf;
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(component != null){
			((JComponent) component).setBorder(BorderFactory.createLineBorder(Color.black));
			
		}
		component = e.getComponent();
		((JComponent) component).setBorder(BorderFactory.createLineBorder(Color.red));
	    System.out.println(component);
	    
	}	
	
	public Component getComponent() {
		return component;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getComponent() == tf){
			JTextField field = new JTextField();
			field.setBounds(e.getComponent().getBounds());
			field.setFont(font);
			field.addMouseListener(this);
			field.addMouseMotionListener(this);
			e.getComponent().getParent().add(field);
			component = field;
		}
		else{
			component = e.getComponent();
		}
		System.out.println(component);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println(component);
		System.out.println(e.getX() + " " + e.getY());
		if(component != null){
			component.setBounds(e.getX(),e.getY(), component.getWidth(), component.getHeight());
        	e.getComponent().repaint();
		}
        
	}

}
