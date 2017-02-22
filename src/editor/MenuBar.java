package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class MenuBar {

    private String title;
    private int width, height;
    private JMenuBar menuBar;
    private Display display;
    private JFileChooser fileChooser = new JFileChooser();
    private File selectedFile;
    private String filePath;

    public MenuBar(Display display, String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.display = display;

        createMenu();
    }

    private void createMenu() {
        menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(width, height));
        JMenu menu = new JMenu(this.title);


        //menu item to exit the editor
        JMenuItem itemExit = new JMenuItem("Exit");
        itemExit.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        //menu item to save changes to a file
        JMenuItem itemSave = new JMenuItem("Save Changes");
        itemSave.setEnabled(false);
        itemSave.addActionListener((ActionEvent event) -> {

            try(BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))){
                writer.write(display.getTextArea().getText());
                JOptionPane.showMessageDialog(null, "Saved Changes");
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


        //menu item to save new file
        JMenuItem itemSaveNew = new JMenuItem("Save New File");
        itemSaveNew.addActionListener((ActionEvent event) -> {

            String fileName = JOptionPane.showInputDialog("Name file");
            fileChooser.setSelectedFile(new File(fileName));
            int returnVal = fileChooser.showSaveDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()))) {
                    writer.write(display.getTextArea().getText());
                    JOptionPane.showMessageDialog(null, "File has been saved.");
                    itemSave.setEnabled(true);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (returnVal == JFileChooser.CANCEL_OPTION) {
                JOptionPane.showMessageDialog(null, "File has not been saved.");
            }
        });

        //menu item to open file in the editor
        JMenuItem itemOpen = new JMenuItem("Open");
        itemOpen.addActionListener((ActionEvent event) -> {
            int returnVal = fileChooser.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                filePath = selectedFile.getAbsolutePath();

                try (FileInputStream fis = new FileInputStream(filePath)) {

                    String textAreaData = display.getTextArea().getText().trim();
                    if (!textAreaData.equals("")) {
                        display.getTextArea().setText(null);
                    }

                    char current;
                    while (fis.available() > 0) {
                        current = (char) fis.read();
                        display.getTextArea().append(String.valueOf(current));
                    }

                    itemSave.setEnabled(true);

                } catch (FileNotFoundException e) {
                    System.out.println(e);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        menu.add(itemOpen);
        menu.add(itemSave);
        menu.add(itemSaveNew);
        menu.add(itemExit);
        menuBar.add(menu);

    }


    public JMenuBar getMenuBar() {
        return this.menuBar;
    }

}
