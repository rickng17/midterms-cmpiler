import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class Dragger extends MouseAdapter{
	private JLabel jlabel;
	private Component component; 
	private Font font = new Font(null, 0, 20);
	private DraggerForTextField draggerForTextField;
	private DraggerForButton draggerForButton;
	
	public Dragger(JLabel jlabel, DraggerForTextField draggerForTextField, DraggerForButton draggerForButton) {
		// TODO Auto-generated constructor stub
		this.jlabel = jlabel;
		this.draggerForTextField = draggerForTextField;
		this.draggerForButton = draggerForButton;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(component != null){
			((JComponent) component).setBorder(null);
		}
		
		if(draggerForTextField.getComponent() != null){
			((JComponent) draggerForTextField.getComponent()).setBorder(BorderFactory.createLineBorder(Color.black));
			draggerForTextField.setComponent(null);
		}
		
		if(draggerForButton.getComponent() != null){
			((JComponent) draggerForButton.getComponent()).setBorder(BorderFactory.createLineBorder(Color.black));
			draggerForButton.setComponent(null);
		}
		
		 Container container = (Container) e.getComponent();
	        for (Component c : container.getComponents()) {
	            if (c.getBounds().contains(e.getPoint())) {
	            	((JComponent) c).setBorder(BorderFactory.createLineBorder(Color.red));
	            	component = c;
	            }
	        }
	}	
	
	public void setDraggerForTextField(DraggerForTextField draggerForTextField) {
		this.draggerForTextField = draggerForTextField;
	}

	public void setDraggerForButton(DraggerForButton draggerForButton) {
		this.draggerForButton = draggerForButton;
	}

	@Override
    public void mousePressed(MouseEvent e) {
        Container container = (Container) e.getComponent();
        for (Component c : container.getComponents()) {
            if (c.getBounds().contains(e.getPoint())) {
            	if(c == jlabel){
            		JLabel label = new JLabel("JLabel");
            		label.setBounds(c.getBounds());
            		label.setFont(font);
            		container.add(label);
            		component = label;
            	}
            	else{
            		component = c;
            	}
                
                
                break;
            }
        }
    }
	
	 public Component getComponent() {
		return component;
	}

	@Override
    public void mouseDragged(MouseEvent e) {
        if (component != null) {
        	component.setBounds(e.getX(), e.getY(), component.getWidth(), component.getHeight());
            e.getComponent().repaint();
        }
    }
	 
	 @Override
    public void mouseReleased(MouseEvent e) {
		 if(component != null){
			 ((JComponent) component).setBorder(null);
		 }
		 component = null;
		 draggerForTextField.setComponent(null);
		 draggerForButton.setComponent(null);
    }

	public void setComponent(Component component) {
		this.component = component;
	}
}
