import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;

public class DraggerForButton extends MouseAdapter{
	private JButton button;
	private Component component; 
	private Font font = new Font(null, 0, 20);
	
	public DraggerForButton(JButton button) {
		// TODO Auto-generated constructor stub
		this.button = button;
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
		if(e.getComponent() == button){
			JButton button = new JButton();
			button.setBounds(e.getComponent().getBounds());
			button.setFont(font);
			button.addMouseListener(this);
			button.addMouseMotionListener(this);
			e.getComponent().getParent().add(button);
			component = button;
		}
		else{
			component = e.getComponent();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		component.setBounds(e.getX(), e.getY(), component.getWidth(), component.getHeight());
        e.getComponent().repaint();
        
	}
	
	public void setComponent(Component component) {
		this.component = component;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(component != null){
			 ((JComponent) component).setBorder(BorderFactory.createLineBorder(Color.black));
		 }
		component = null;
	}

}
