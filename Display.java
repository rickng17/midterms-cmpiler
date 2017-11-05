import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;


public class Display extends JFrame {

    private JPanel panel;
    private JPanel changePanel;
    private JLabel changeWidthLabel;
    private JTextField tfwidth;
    private JLabel changeHeightLabel;
    private JTextField tfheight;
    private JButton changeButton;
    private JLabel label;
    private JTextField tf;
    private JButton button;
    private JButton compile;
    private ArrayList<Object> objects;

    public Display() {

        DraggerForTextField draggerForTextField = null;
        DraggerForButton draggerForButton = null;
        Dragger dragger = null;
        objects = new ArrayList<>();

        Font font = new Font(null, 0, 20);

        setTitle("Midterms CMPILER");
        panel = new JPanel();
        panel.setBounds(0, 0, 960, 540);

        label = new JLabel("Drag this to create a label");
        label.setBounds(10, 10, 300, 30);
        label.setFont(font);

        tf = new JTextField("Drag this to create a text field");
        tf.setBounds(10, 50, 300, 30);
        tf.setFont(font);

        draggerForTextField = new DraggerForTextField(tf, dragger, draggerForButton, objects);
        tf.addMouseListener(draggerForTextField);
        tf.addMouseMotionListener(draggerForTextField);

        button = new JButton("Drag this to create a button");
        button.setBounds(10, 90, 300, 30);
        button.setFont(font);

        draggerForButton = new DraggerForButton(button, dragger, draggerForTextField, objects);

        button.addMouseListener(draggerForButton);
        button.addMouseMotionListener(draggerForButton);

        compile = new JButton("Compile");
        compile.setBounds(0, 541,943, 30);
        compile.addActionListener(e -> {
            export();
        });

        panel.add(label);
        panel.add(tf);
        panel.add(button);
        panel.add(compile);

        dragger = new Dragger(label, draggerForTextField, draggerForButton, objects);

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
        changePanel.setBounds(800, 0, 200, 400);
        changePanel.add(changeWidthLabel);
        changePanel.add(tfwidth);
        changePanel.add(changeHeightLabel);
        changePanel.add(tfheight);
        changePanel.add(changeButton);

        changePanel.setLayout(null);

        add(changePanel);
        add(panel);
        add(compile);
        setSize(960, 612);

        panel.setLayout(null);
        //pack();
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        dragger.setDraggerForButton(draggerForButton);
        dragger.setDraggerForTextField(draggerForTextField);

        draggerForTextField.setDragger(dragger);
        draggerForTextField.setDraggerForButton(draggerForButton);

        draggerForButton.setDragger(dragger);
        draggerForButton.setDraggerForTextField(draggerForTextField);
    }

    public void export() {
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<String> buttons = new ArrayList<>();
        ArrayList<String> textFields = new ArrayList<>();
        labels.add("{");
        labels.add("\t\"webpage\": {");
        labels.add("\t\t\"labels\": [");
        buttons.add("\t\t\"buttons\": [");
        textFields.add("\t\t\"textfields\": [");
        for (Object o :
                objects) {
            if (o instanceof JLabel) {
                JLabel jl = (JLabel) o;
                labels.add("\t\t\t{\"text\": \"" + jl.getText() + "\", " +
                        "\"font\": \"" + jl.getFont().getFamily() + "\", " +
                        "\"xposition\": \"" + jl.getX() + "\", " +
                        "\"yposition\": \"" + jl.getY() + "\"" +
                        "\"width\": \"" + jl.getWidth() +
                        "\"height\": \"" + jl.getHeight() + "\"},");
            } else if (o instanceof JButton) {
                JButton jb = (JButton) o;
                buttons.add("\t\t\t{\"text\": \"" + jb.getText() + "\", " +
                        "\"font\": \"" + jb.getFont().getFamily() + "\", " +
                        "\"xposition\": \"" + jb.getX() + "\", " +
                        "\"yposition\": \"" + jb.getY() +
                        "\"width\": \"" + jb.getWidth() +
                        "\"height\": \"" + jb.getHeight() + "\"},");
            } else if (o instanceof JTextField) {
                JTextField jt = (JTextField) o;
                textFields.add("\t\t\t{\"text\": \"" + jt.getText() + "\", " +
                        "\"font\": \"" + jt.getFont().getFamily() + "\", " +
                        "\"xposition\": \"" + jt.getX() + "\", " +
                        "\"yposition\": \"" + jt.getY() +
                        "\"width\": \"" + jt.getWidth() +
                        "\"height\": \"" + jt.getHeight() + "\"},");
            }
        }
        String s = labels.get(labels.size() - 1);
        labels.remove(s);
        labels.add(s.substring(0, s.length() - 1));
        s = buttons.get(buttons.size() - 1);
        buttons.remove(s);
        buttons.add(s.substring(0, s.length() - 1));
        s = textFields.get(textFields.size() - 1);
        textFields.remove(s);
        textFields.add(s.substring(0, s.length() - 1));
        labels.add("\t\t],");
        buttons.add("\t\t],");
        textFields.add("\t\t]");
        textFields.add("\t}");
        textFields.add("}");

        Path p = Paths.get("JSON_Objects.json");
        try {
            Files.write(p, labels, Charset.forName("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(p, buttons, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
            Files.write(p, textFields, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

