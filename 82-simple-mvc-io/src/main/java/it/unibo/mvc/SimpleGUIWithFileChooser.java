package it.unibo.mvc;

import java.io.IOException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {
    
    private Controller myController = new Controller();
    private final static int PROPORTION = 2;
    private final JFrame frame = new JFrame();

    private void setView() {
        
        frame.setTitle("My second Java Graphical Interface!");
        final Dimension display = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) display.getWidth() / PROPORTION,
                (int) display.getHeight() / PROPORTION);

        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public SimpleGUIWithFileChooser() {
      
        JPanel canvas = new JPanel();
        frame.setContentPane(canvas);
        canvas.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        canvas.add(textArea, BorderLayout.CENTER);

        JButton bSave = new JButton("Save");
        bSave.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    myController.writeStringOnFile(textArea.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            
        });
        canvas.add(bSave, BorderLayout.SOUTH);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());

        JTextField northTextField = new JTextField(myController.getPath());
        northTextField.setEditable(false);
        northPanel.add(northTextField, BorderLayout.CENTER);

        JButton browse = new JButton("Browse...");
        browse.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser browse = new JFileChooser();
                try{
                    switch(browse.showSaveDialog(null)){
                        case JFileChooser.APPROVE_OPTION:
                            myController.setCurrentFile(browse.getSelectedFile());
                            northTextField.setText(myController.getPath());
                            break;
                        case JFileChooser.CANCEL_OPTION:
                            break;
                        default:
                            JOptionPane.showMessageDialog(null,
                                    "An error has occured... :( \nMaybe you have to simply try again :)", "ERROR!",
                                    JOptionPane.ERROR_MESSAGE);
                            break;
                    };
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null,
                                    "An error has occured... :( \nMaybe you have to simply try again :)", "ERROR!",
                                    JOptionPane.ERROR_MESSAGE);
                }             
            }
            
        });
        northPanel.add(browse, BorderLayout.EAST);

        canvas.add(northPanel, BorderLayout.NORTH);
        

        this.setView();
    }

    public static void main(String...args){
        SimpleGUIWithFileChooser myGui = new SimpleGUIWithFileChooser();
    }
}
