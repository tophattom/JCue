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
import jcue.domain.StartMode;

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
        StartMode[] modes = {
            StartMode.MANUAL,
            StartMode.AFTER_START,
            StartMode.AFTER_END,
            StartMode.HOTKEY
        };
        this.startModeSelect = new JComboBox(modes);

        this.cueLabel = new JLabel("Cue:");
        this.cueSelect = new JComboBox();

        this.delayLabel = new JLabel("Delay:");
        this.delayField = new JTextField(6);
    }

    public void showUI(JPanel container) {
        container.removeAll();

        container.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(5, 3, 5, 3);

        //Name field
        UtilsUI.setGBC(c, 0, 0, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.nameLabel, c);

        UtilsUI.setGBC(c, 1, 0, 0.5, 0, 3, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.nameField, c);

        //Description field
        UtilsUI.setGBC(c, 0, 1, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.descLabel, c);

        UtilsUI.setGBC(c, 1, 1, 0.5, 0, 3, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.descField, c);

        //Start mode select line
        //Start mode
        c.insets = new Insets(5, 3, 20, 3);

        UtilsUI.setGBC(c, 0, 3, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.startModeLabel, c);

        UtilsUI.setGBC(c, 1, 3, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.startModeSelect, c);

        //Cue
        UtilsUI.setGBC(c, 2, 3, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.cueLabel, c);

        UtilsUI.setGBC(c, 3, 3, 0.5, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.cueSelect, c);

        //Delay
        UtilsUI.setGBC(c, 4, 3, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.delayLabel, c);

        UtilsUI.setGBC(c, 5, 3, 0.5, 0, 1, 1, GridBagConstraints.NONE);
        c.anchor = GridBagConstraints.WEST;
        container.add(this.delayField, c);

        container.revalidate();
    }

    public void setNameFieldText(String text) {
        this.nameField.setText(text);
    }

    public void setDescFieldText(String text) {
        this.descField.setText(text);
    }

    public void setDelayFieldValue(double value) {
        this.delayField.setText(String.format("%.2f", value));
    }
    
    public void setStartModeSelectValue(StartMode value) {
        this.startModeSelect.setSelectedItem(value);
    }
}
