import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class DraggerForTextField extends MouseAdapter {

    private JTextField tf;
    private Component component;
    private Font font = new Font(null, 0, 20);
    private int origX;
    private int origY;
    private int origXComponent;
    private int origYComponent;
    private Dragger dragger;
    private DraggerForButton draggerForButton;
    private ArrayList<Object> objects;

    public DraggerForTextField(JTextField tf, Dragger dragger, DraggerForButton draggerForButton, ArrayList<Object> objects) {
        // TODO Auto-generated constructor stub
        this.tf = tf;
        this.dragger = dragger;
        this.draggerForButton = draggerForButton;
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

        if (draggerForButton.getComponent() != null) {
            ((JComponent) draggerForButton.getComponent()).setBorder(BorderFactory.createLineBorder(Color.black));
            draggerForButton.setComponent(null);
        }

        component = e.getComponent();
        ((JComponent) component).setBorder(BorderFactory.createLineBorder(Color.red));
        System.out.println(component);

    }

    public void setDragger(Dragger dragger) {
        this.dragger = dragger;
    }

    public void setDraggerForButton(DraggerForButton draggerForButton) {
        this.draggerForButton = draggerForButton;
    }

    public Component getComponent() {
        return component;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getComponent() == tf) {
            JTextField field = new JTextField();
            field.setBounds(e.getComponent().getBounds());
            field.setFont(font);
            //DraggerForTextField draggerForTextField = new DraggerForTextField(null);
            field.addMouseListener(this);
            field.addMouseMotionListener(this);
            e.getComponent().getParent().add(field);
            component = field;
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
        if (component != null) {
            component.setBounds(e.getXOnScreen() - origX + origXComponent, e.getYOnScreen() - origY + origYComponent, component.getWidth(), component.getHeight());
            e.getComponent().repaint();
        }

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
