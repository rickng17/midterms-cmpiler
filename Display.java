import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;


public class Display extends JFrame {

    JPanel panel;
    JPanel changePanel;
    JLabel changeWidthLabel;
    JTextField tfwidth;
    JLabel changeHeightLabel;
    JTextField tfheight;
    JButton changeButton;
    JLabel label;
    JTextField tf;
    JButton button;
    JButton compile;

    public Display() {
    	
    	DraggerForTextField draggerForTextField = null;
    	DraggerForButton draggerForButton = null;
    	Dragger dragger = null;

    	Font font = new Font(null, 0, 20);
    	
        setTitle("Midterms CMPILER");
        panel  = new JPanel();
        panel.setBounds(0, 0, 1600, 900);
        
        label = new JLabel("Drag this to create a label");
        label.setBounds(10, 10, 300, 30);
        label.setFont(font);
        
        tf = new JTextField("Drag this to create a text field");
        tf.setBounds(10, 50, 300, 30);
        tf.setFont(font);
        
        draggerForTextField = new DraggerForTextField(tf, dragger, draggerForButton);
        tf.addMouseListener(draggerForTextField);
        tf.addMouseMotionListener(draggerForTextField);
        
        button = new JButton("Drag this to create a button");
        button.setBounds(10, 90, 300, 30);
        button.setFont(font);
        
        draggerForButton = new DraggerForButton(button, dragger, draggerForTextField);
        
        button.addMouseListener(draggerForButton);
        button.addMouseMotionListener(draggerForButton);
        
        compile = new JButton("Compile");
        compile.setBounds(700, 750, 200, 30);
        
        panel.add(label);
        panel.add(tf);
        panel.add(button);
        panel.add(compile);

        dragger = new Dragger(label, draggerForTextField, draggerForButton);
        
        panel.addMouseListener(dragger);
        panel.addMouseMotionListener(dragger);
        panel.setLayout(null);
        
        changeWidthLabel = new JLabel("Change width:");
        changeWidthLabel.setBounds(0, 20, 200, 30);
        changeWidthLabel.setFont(font);
        
        changeHeightLabel = new JLabel("Change Height:");
        changeHeightLabel.setBounds(0, 100, 200, 30);
        changeHeightLabel.setFont(font);
        
        tfwidth = new JTextField();
        tfwidth.setBounds(0, 50, 100, 30);
        tfwidth.setFont(font);
        
        tfheight = new JTextField();
        tfheight.setBounds(0, 130, 100, 30);
        tfheight.setFont(font);
        
        changeButton = new JButton("Change Size");
        changeButton.setBounds(0, 200, 150, 30);
        changeButton.setFont(font);
        
        MyActionListener listener = new MyActionListener(dragger, draggerForTextField, draggerForButton, tfwidth, tfheight);
        
        changeButton.addActionListener(listener);
        
        changePanel = new JPanel();
        changePanel.setBounds(1600, 0, 200, 900);
        changePanel.add(changeWidthLabel);
        changePanel.add(tfwidth);
        changePanel.add(changeHeightLabel);
        changePanel.add(tfheight);
        changePanel.add(changeButton);
        
        changePanel.setLayout(null);
        
        add(changePanel);
        add(panel);
        setSize(1800, 900);
        
        panel.setLayout(null);
        //pack();
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        dragger.setDraggerForButton(draggerForButton);
        dragger.setDraggerForTextField(draggerForTextField);
        
        draggerForTextField.setDragger(dragger);
        draggerForTextField.setDraggerForButton(draggerForButton);
        
        draggerForButton.setDragger(dragger);
        draggerForButton.setDraggerForTextField(draggerForTextField);
    }
}

 