package editor;

import javax.swing.*;
import java.awt.*;

public class Display {
    private JFrame frame;
    private TextArea textArea;
    private int width, height;
    private MenuBar menu = new MenuBar(this,"Menu", 100, 30);
    private String title;

    public Display(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;

        createDisplay();
        frame.setJMenuBar(menu.getMenuBar());
    }

    private void createDisplay(){
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width,height);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());

        textArea = new TextArea();
        textArea.setFocusable(true);
        textArea.setPreferredSize(new Dimension(width, height));
        textArea.setEditable(true);

        frame.add(menu.getMenuBar());
        frame.add(textArea);
        frame.pack();

    }

    public TextArea getTextArea(){
        return this.textArea;
    }
}
