package it.unibo.mvc;

import javax.swing.*;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.plaf.basic.BasicArrowButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUI {

    private static int PROPORTIONS = 2;
    private final JFrame frame = new JFrame();
    private final SimpleController controller;

    public SimpleGUI(SimpleController controller) {
        this.controller = controller;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        mainPanel.add(northPanel, BorderLayout.NORTH);

        JTextField upperText = new JTextField("Write here to print!");
        northPanel.add(upperText, BorderLayout.CENTER);

        JButton bPrint = new JButton("Print");
        bPrint.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.print(upperText.getText());
            }
            
        });
        northPanel.add(bPrint, BorderLayout.EAST);

        JTextArea textArea = new JTextArea("\n\nThis text area in not editable.\n" +
                "This is to show the print history only.");
        textArea.setEditable(false);
        textArea.setBackground(Color.black);
        textArea.setForeground(Color.white);
        mainPanel.add(textArea, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout());
        JButton bHistory = new JButton("Show History");
        bHistory.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                for (String str : controller.getAllPrintedStrings()) {
                    textArea.append(str + "\n");
                }
            }
            
        });
        southPanel.add(bHistory);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel);
    }

    private void display() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        screen.width = (int) screen.width / PROPORTIONS;
        screen.height = (int) screen.height / PROPORTIONS;
        frame.setSize(screen);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String...args){
        new SimpleGUI(new SimpleController()).display();
    }
}
