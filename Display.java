import javax.swing.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.awt.*;
import java.io.File;
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
    private JLabel changeTextLabel;
    private JTextField tfText;
    private JButton changeButton;
    private JLabel label;
    private JTextField tf;
    private JButton button;
    private JButton compile;
    private ArrayList<Object> objects;
    private JButton load;
    private Font font;

    public Display() {

        DraggerForTextField draggerForTextField = null;
        DraggerForButton draggerForButton = null;
        Dragger dragger = null;
        objects = new ArrayList<>();

        font = new Font(null, 0, 20);

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
        compile.setBounds(0, 540, 1160, 30);
        compile.setFont(font);
        compile.addActionListener(e -> {
            export();
            JOptionPane.showMessageDialog(new JFrame(), "Compile Finished");
        });

        panel.add(label);
        panel.add(tf);
        panel.add(button);
       

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
        
        changeTextLabel = new JLabel("Change Text:");
        changeTextLabel.setBounds(0, 180, 200, 30);
        changeTextLabel.setFont(font);

        tfwidth = new JTextField();
        tfwidth.setBounds(0, 50, 100, 30);
        tfwidth.setFont(font);

        tfheight = new JTextField();
        tfheight.setBounds(0, 130, 100, 30);
        tfheight.setFont(font);
        
        tfText = new JTextField();
        tfText.setBounds(0, 210, 100, 30);
        tfText.setFont(font);

        changeButton = new JButton("Change");
        changeButton.setBounds(0, 300, 150, 30);
        changeButton.setFont(font);
        
        load = new JButton("load Json file");
        load.setBounds(0, 400, 150, 30);
        load.setFont(font);
        load.addActionListener(e -> {
        	loadJson();
        });
        

        MyActionListener listener = new MyActionListener(dragger, draggerForTextField, draggerForButton, tfwidth, tfheight, tfText);

        changeButton.addActionListener(listener);

        changePanel = new JPanel();
        changePanel.setBounds(960, 0, 200, 540);
        changePanel.add(changeWidthLabel);
        changePanel.add(tfwidth);
        changePanel.add(changeHeightLabel);
        changePanel.add(tfheight);
        changePanel.add(changeTextLabel);
        changePanel.add(tfText);
        changePanel.add(changeButton);
        changePanel.add(load);

        changePanel.setLayout(null);

        add(changePanel);
        add(panel);
        add(compile);
        setSize(1160, 610);

        panel.setLayout(null);
        //pack();
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

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
                        "\"font\": " + jl.getFont().getFamily() + ", " +
                        "\"xposition\": " + jl.getX() + ", " +
                        "\"yposition\": " + jl.getY() + ", " +
                        "\"width\": " + jl.getWidth() + ", " +
                        "\"height\": " + jl.getHeight() + "},");
            } else if (o instanceof JButton) {
                JButton jb = (JButton) o;
                buttons.add("\t\t\t{\"text\": \"" + jb.getText() + "\", " +
                        "\"font\": " + jb.getFont().getFamily() + ", " +
                        "\"xposition\": " + jb.getX() + ", " +
                        "\"yposition\": " + jb.getY() + ", " +
                        "\"width\": " + jb.getWidth() + ", " +
                        "\"height\": " + jb.getHeight() + "},");
            } else if (o instanceof JTextField) {
                JTextField jt = (JTextField) o;
                textFields.add("\t\t\t{\"text\": \"" + jt.getText() + "\", " +
                        "\"font\": " + jt.getFont().getFamily() + ", " +
                        "\"xposition\": " + jt.getX() + ", " +
                        "\"yposition\": " + jt.getY() + ", " +
                        "\"width\": " + jt.getWidth() + ", " +
                        "\"height\": " + jt.getHeight() + "},");
            }
        }
        String s;
        if (labels.size() > 3) {
            s = labels.get(labels.size() - 1);
            labels.remove(s);
            labels.add(s.substring(0, s.length() - 1));
        }
        if (buttons.size() > 1) {
            s = buttons.get(buttons.size() - 1);
            buttons.remove(s);
            buttons.add(s.substring(0, s.length() - 1));
        }
        if (textFields.size() > 1) {
            s = textFields.get(textFields.size() - 1);
            textFields.remove(s);
            textFields.add(s.substring(0, s.length() - 1));
        }
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
    
    public void loadJson(){
    	JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setDialogTitle("Load Json file");
    	File workingDirectory = new File(System.getProperty("user.dir"));
    	fileChooser.setCurrentDirectory(workingDirectory);
    	
    	 int returnValue = fileChooser.showOpenDialog(null);
         if (returnValue == JFileChooser.APPROVE_OPTION) {
           File selectedFile = fileChooser.getSelectedFile();
           String jsonString = readAllBytes(selectedFile.getPath());
           
           JsonToUI(jsonString);
         }
    }
    
    private void JsonToUI(String jsonString){
    	JsonElement jelement = new JsonParser().parse(jsonString);
        JsonObject  jobject = jelement.getAsJsonObject();
        jobject = jobject.getAsJsonObject("webpage");
        
        JsonArray jarray = jobject.getAsJsonArray("labels");
        if(jarray != null){
	        for(int i = 0; i < jarray.size(); i++){
	        	jobject = jarray.get(i).getAsJsonObject();
	            String text = jobject.get("text").toString();
	            text = text.substring(1,text.length()-1);
	            String x = jobject.get("xposition").toString();
	            String y = jobject.get("yposition").toString();
	            String width = jobject.get("width").toString();
	            String height = jobject.get("height").toString();
	            
	            JLabel label = new JLabel(text); 
	            label.setBounds(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(width), Integer.parseInt(height));
	            panel.add(label);
	            label.setFont(font);
	            objects.add(label);
	        }
        }
        
        jobject = jelement.getAsJsonObject();
        jobject = jobject.getAsJsonObject("webpage");
        
        jarray = jobject.getAsJsonArray("buttons");
        
        if(jarray != null){
	        for(int i = 0; i < jarray.size(); i++){
	        	jobject = jarray.get(i).getAsJsonObject();
	            String text = jobject.get("text").toString();
	            text = text.substring(1,text.length()-1);
	            String x = jobject.get("xposition").toString();
	            String y = jobject.get("yposition").toString();
	            String width = jobject.get("width").toString();
	            String height = jobject.get("height").toString();
	            
	            JButton button = new JButton(text); 
	            button.setBounds(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(width), Integer.parseInt(height));
	            panel.add(button);
	            button.setFont(font);
	            objects.add(button);
	        }
        }
        
        jobject = jelement.getAsJsonObject();
        jobject = jobject.getAsJsonObject("webpage");
        
        jarray = jobject.getAsJsonArray("textfields");
        
        if(jarray != null){
	        for(int i = 0; i < jarray.size(); i++){
	        	jobject = jarray.get(i).getAsJsonObject();
	            String text = jobject.get("text").toString();
	            text = text.substring(1,text.length()-1);
	            String x = jobject.get("xposition").toString();
	            String y = jobject.get("yposition").toString();
	            String width = jobject.get("width").toString();
	            String height = jobject.get("height").toString();
	            
	            JTextField tf = new JTextField(text); 
	            tf.setBounds(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(width), Integer.parseInt(height));
	            panel.add(tf);
	            tf.setFont(font);
	            objects.add(tf);
	        }
        }
    }
    
    private static String readAllBytes(String filePath)
    {
        String content = "";
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return content;
    }
}

