package com.example.digitaldetox.GoalSettingClasses;

import javax.swing.*;
import java.awt.*;

public class List extends JPanel{
    List(){
        GridLayout layout = new GridLayout(10,1);
        layout.setVgap(5);

        this.setLayout(layout);
    }

    public void updateNumbers(){
        Component[] listItems = this.getComponents();

        for(int i = 0; i < listItems.length; i++){
            if(listItems[i] instanceof Task){
                ((Task)listItems[i]).changeIndex(i+1);
            }
        }
    }

    public void removeCompletedTasks() {
        Component[] listItems = this.getComponents();

        for (int i = listItems.length -1; i>= 0; i--){
            if (listItems[i] instanceof Task) {
                Task task = (Task) listItems[i];
                if (task.isChecked()){
                    this.remove(i);
                }
            }
        }
        updateNumbers();

        this.revalidate();
        this.repaint();
    }
}
