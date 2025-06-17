package com.farid;

import org.fusesource.jansi.Ansi;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Objects;

public class Frame extends JFrame implements ActionListener, ItemListener {

    private ImageListener listener;
    private String absolute_path = null;
    private String brightness_mapping = "average";
    private Boolean invert_dec = false;
    private Ansi.Color color = Ansi.Color.WHITE;
    JButton button;
    JRadioButton original;
    JRadioButton invert;
    JRadioButton average;
    JRadioButton min_max;
    JRadioButton luminosity;
    JRadioButton White;
    JRadioButton Red;
    JRadioButton Green;
    JRadioButton Yellow;
    JRadioButton Blue;
    JRadioButton Magenta;
    JButton submit;

    Frame(ImageListener listener){
        this.listener = listener;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        original = new JRadioButton("Original Color");
        invert = new JRadioButton("Invert Color");

        ButtonGroup color = new ButtonGroup();
        color.add(original);
        color.add(invert);
        original.setSelected(true);

        average = new JRadioButton("Average Mapping");
        min_max = new JRadioButton("Min Max Mapping");
        luminosity = new JRadioButton("Luminosity Mapping");

        ButtonGroup brightness_mapping = new ButtonGroup();
        brightness_mapping.add(average);
        brightness_mapping.add(min_max);
        brightness_mapping.add(luminosity);
        average.setSelected(true);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.GRAY);

        JPanel panel2 = new JPanel();
        panel2.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel2.setBackground(Color.GRAY);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(2, 3)); // 2 rows, 3 column
        panel3.setBackground(Color.GRAY);


        ButtonGroup colors = new ButtonGroup();
        White = new JRadioButton("WHITE");
        Red = new JRadioButton("RED");
        Green = new JRadioButton("GREEN");
        Yellow = new JRadioButton("YELLOW");
        Blue = new JRadioButton("BLUE");
        Magenta = new JRadioButton("MAGENTA");

        colors.add(White);
        colors.add(Red);
        colors.add(Green);
        colors.add(Yellow);
        colors.add(Blue);
        colors.add(Magenta);
        White.setSelected(true);


        button = new JButton("Select an image");
        submit = new JButton("Submit");

        button.addActionListener(this);
        submit.addActionListener(this);

        original.addItemListener(this);
        invert.addItemListener(this);
        original.setBackground(null);
        invert.setBackground(null);
        original.setActionCommand("group1");
        invert.setActionCommand("group1");

        average.addItemListener(this);
        min_max.addItemListener(this);
        luminosity.addItemListener(this);
        average.setBackground(null);
        min_max.setBackground(null);
        luminosity.setBackground(null);
        average.setActionCommand("group2");
        min_max.setActionCommand("group2");
        luminosity.setActionCommand("group2");

        White.addItemListener(this);
        Red.addItemListener(this);
        Green.addItemListener(this);
        Yellow.addItemListener(this);
        Blue.addItemListener(this);
        Magenta.addItemListener(this);
        White.setBackground(null);
        Red.setBackground(null);
        Green.setBackground(null);
        Yellow.setBackground(null);
        Blue.setBackground(null);
        Magenta.setBackground(null);
        White.setActionCommand("group3");
        Red.setActionCommand("group3");
        Green.setActionCommand("group3");
        Yellow.setActionCommand("group3");
        Blue.setActionCommand("group3");
        Magenta.setActionCommand("group3");

        this.add(button);
        panel.add(original);
        panel.add(invert);
        this.add(panel);
        panel2.add(average);
        panel2.add(min_max);
        panel2.add(luminosity);
        this.add(panel2);
        panel3.add(White);
        panel3.add(Red);
        panel3.add(Green);
        panel3.add(Yellow);
        panel3.add(Blue);
        panel3.add(Magenta);
        this.add(panel3);
        this.add(submit);
        this.pack();
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            JFileChooser chooser = new JFileChooser();

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                absolute_path = chooser.getSelectedFile().getAbsolutePath();
            }
        }
        if(e.getSource() == submit){
            listener.onImageSelected(absolute_path,brightness_mapping,invert_dec,color);

        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED){
            JRadioButton source = ((JRadioButton) e.getSource());
            String groupId = source.getActionCommand();

            if ("group2".equals(groupId)) {
                brightness_mapping = source.getText();
            } else if ("group1".equals(groupId)) {
                invert_dec = Objects.equals(source.getText(), "Invert Color");
            } else if ("group3".equals(groupId)) {
                switch (source.getText()) {
                    case "WHITE":
                        color = Ansi.Color.WHITE;
                        break;
                    case "RED":
                        color = Ansi.Color.RED;
                        break;
                    case "GREEN":
                        color = Ansi.Color.GREEN;
                        break;
                    case "YELLOW":
                        color = Ansi.Color.YELLOW;
                        break;
                    case "BLUE":
                        color = Ansi.Color.BLUE;
                        break;
                    case "MAGENTA":
                        color = Ansi.Color.MAGENTA;
                        break;
                }
            }
        }
    }
}
