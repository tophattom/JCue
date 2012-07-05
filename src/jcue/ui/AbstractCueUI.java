/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcue.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Jaakko
 */
public class AbstractCueUI {
    
    private JLabel nameLabel, descLabel;
    private JTextField nameField, descField;
    
    private JLabel startModeLabel, delayLabel, cueLabel;
    private JComboBox startModeSelect, cueSelect;
    private JTextField delayField;

    public AbstractCueUI() {
        this.nameLabel = new JLabel("Name:");
        this.nameField = new JTextField();
        
        this.descLabel = new JLabel("Description:");
        this.descField = new JTextField();
        
        this.startModeLabel = new JLabel("Start mode:");
        String[] modes = {"Manual", "After start of cue", "After end of cue", "Hotkey"};
        this.startModeSelect = new JComboBox(modes);
        
        this.cueLabel = new JLabel("Cue:");
        this.cueSelect = new JComboBox();
        
        this.delayLabel = new JLabel("Delay:");
        this.delayField = new JTextField();
    }
    
    public void showUI(JPanel container) {
        container.removeAll();
        
        container.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.insets = new Insets(5, 3, 5, 3);
        
        //Name field
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        container.add(this.nameLabel, c);
        
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        c.weightx = 0.5;
        container.add(this.nameField, c);
        
        //Description field
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0;
        container.add(this.descLabel, c);
        
        c.gridx = 1;
        c.weightx = 0.5;
        c.gridwidth = 3;
        container.add(this.descField, c);
        
        //Start mode select line
        //Start mode
        c.insets = new Insets(5, 3, 20, 3);
        
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0;
        container.add(this.startModeLabel, c);
        
        c.gridx = 1;
        c.weightx = 0;
        container.add(this.startModeSelect, c);
        
        //Cue
        c.gridx = 2;
        c.weightx = 0;
        container.add(this.cueLabel, c);
        
        c.gridx = 3;
        c.weightx = 0.5;
        container.add(this.cueSelect, c);
        
        //Delay
        c.gridx = 4;
        c.weightx = 0;
        container.add(this.delayLabel, c);
        
        c.gridx = 5;
        c.weightx = 0.5;
        container.add(this.delayField, c);
        
        container.revalidate();
    }
}
