package it.unibo.mvc;

import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUI {

    private final Controller myController = new Controller();
    private final static int PROPORTION = 2;
    private final JFrame frame = new JFrame();

    private void setView() {

        frame.setTitle("My first Java Graphical Interface!");
        final Dimension display = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) display.getWidth() / PROPORTION,
                (int) display.getHeight() / PROPORTION);

        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public SimpleGUI() {
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

        setView();
    }

    public static void main(String...args){
        SimpleGUI myGui = new SimpleGUI();
    }
}
