package Enchanter.UI;

import Enchanter.Properties;
import Superior.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



class SelectionButton extends JButton implements ActionListener {
    boolean checked = false;

    SelectionButton(String name) {
        super(name);
        setBackground(Color.white);
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!Utilities.isInteger(this.getText())) {
            for(int i = 0; i < 4; i++) {
                if(Properties.NAMES[i].equals(this.getText())) {
                    Interface.itemType = Properties.NAMES[i];
                }
            }
        } else {
            Interface.level = Integer.parseInt(this.getText());
        }
    }
}