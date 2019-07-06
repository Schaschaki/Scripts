package Enchanter.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static Enchanter.Properties.ItemType;
import static Enchanter.Properties.ItemType.*;

import Enchanter.Properties;
import Superior.Utilities;

public class Interface extends JFrame {

    static int level = -1;
    static String itemType;

    public Interface() {
        super("Enchanter");
        Properties.setWindow(true);
        setLayout(new BorderLayout());
        setSize(450,250);
        setResizable(true);
        spawnLevelButtons();
        spawnItemButtons();
        spawnSaveButton();

        setVisible(true);
    }

    ArrayList<SelectionButton> spawnItemButtons() {
        JPanel west = new JPanel(new GridLayout(4, 1));
        add(west, BorderLayout.WEST);
        ArrayList<SelectionButton> itemButtons = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            SelectionButton item = new SelectionButton(Properties.NAMES[i]);
            west.add(item);
            itemButtons.add(item);

        }
        return itemButtons;
    }

    ArrayList<SelectionButton> spawnLevelButtons() {
        JPanel east = new JPanel(new GridLayout(7, 1));
        add(east, BorderLayout.EAST);
        ArrayList<SelectionButton> levelButtons = new ArrayList<>();
        for(int i = 1; i <= 7; i++) {
            SelectionButton level = new SelectionButton("" + i);
            east.add(level);
            levelButtons.add(level);
        }
        return levelButtons;
    }

    void spawnSaveButton() {
        JPanel center = new JPanel(new GridLayout(1, 1));
        add(center, BorderLayout.CENTER);
        JButton saveButton = new JButton(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("I saved item: " + itemType + " and level: " + level);
                Properties.SelectedItem.SELECTED_ITEM.setSelectedItem(itemType, level);
                Properties.setRunning(true);
                Properties.setWindow(false);
                setVisible(false);
                dispose();
                System.out.println(Properties.SelectedItem.SELECTED_ITEM.id());
            }
        });
        center.add(saveButton);
    }

    class SelectionButton extends JButton implements ActionListener {
        boolean checked = false;
        String item = "";

        SelectionButton(String name) {
            super(name);
            setBackground(Color.white);
            addActionListener(this);
            this.item = name;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!Utilities.isInteger(this.getText())) {
                for(int i = 0; i < 4; i++) {
                    if(Properties.NAMES[i].equals(this.getText())) {
                        itemType = Properties.NAMES[i];
                    }
                }
            } else {
               level = Integer.parseInt(this.getText());
            }
        }
    }
}
