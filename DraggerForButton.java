import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;

public class DraggerForButton extends MouseAdapter {
    private JButton button;
    private Component component;
    private Font font = new Font(null, 0, 20);
    private int origX;
    private int origY;
    private int origXComponent;
    private int origYComponent;
    private Dragger dragger;
    private DraggerForTextField draggerForTextField;
    private ArrayList<Object> objects;

    public DraggerForButton(JButton button, Dragger dragger, DraggerForTextField draggerForTextField, ArrayList<Object> objects) {
        // TODO Auto-generated constructor stub
        this.button = button;
        this.dragger = dragger;
        this.draggerForTextField = draggerForTextField;
        this.objects = objects;
    }

    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        if (component != null) {
            ((JComponent) component).setBorder(BorderFactory.createLineBorder(Color.black));

        }

        if (dragger.getComponent() != null) {
            ((JComponent) dragger.getComponent()).setBorder(null);
            dragger.setComponent(null);
        }

        if (draggerForTextField.getComponent() != null) {
            ((JComponent) draggerForTextField.getComponent()).setBorder(BorderFactory.createLineBorder(Color.black));
            draggerForTextField.setComponent(null);
        }

        component = e.getComponent();
        ((JComponent) component).setBorder(BorderFactory.createLineBorder(Color.red));

    }

    public void setDragger(Dragger dragger) {
        this.dragger = dragger;
    }

    public void setDraggerForTextField(DraggerForTextField draggerForTextField) {
        this.draggerForTextField = draggerForTextField;
    }

    public Component getComponent() {
        return component;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getComponent() == button) {
            JButton button = new JButton();
            button.setBounds(e.getComponent().getBounds());
            button.setFont(font);
            button.addMouseListener(this);
            button.addMouseMotionListener(this);
            e.getComponent().getParent().add(button);
            component = button;
        } else {
            component = e.getComponent();
        }

        origX = e.getXOnScreen();
        origY = e.getYOnScreen();
        origXComponent = e.getComponent().getX();
        origYComponent = e.getComponent().getY();

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        component.setBounds(e.getXOnScreen() - origX + origXComponent, e.getYOnScreen() - origY + origYComponent, component.getWidth(), component.getHeight());
        e.getComponent().repaint();

    }

    public void setComponent(Component component) {
        this.component = component;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (component != null) {
            ((JComponent) component).setBorder(BorderFactory.createLineBorder(Color.black));
            if (!objects.contains(component)) {
                objects.add(component);
            }
        }
        component = null;
    }

}
