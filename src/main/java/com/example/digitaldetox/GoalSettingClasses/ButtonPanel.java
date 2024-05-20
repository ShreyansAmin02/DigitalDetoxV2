package com.example.digitaldetox.GoalSettingClasses;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ButtonPanel extends JPanel{
    private JButton addTask;
    private JButton clear;

    Border emptyBorder = BorderFactory.createEmptyBorder();

    // Constructor
    ButtonPanel(){
        this.setPreferredSize(new Dimension(400,60));

        addTask = new JButton("Add Goals");
        addTask.setBorder(emptyBorder);
        addTask.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        //addTask.setVerticalAlignment();
        this.add(addTask);

        this.add(Box.createHorizontalStrut(15));
        clear = new JButton("Clear Completed Goals");
        clear.setBorder(emptyBorder);
        clear.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        this.add(clear);
    }

    public JButton getAddTask(){
        return addTask;
    }
    public JButton getClear(){
        return clear;
    }
}

