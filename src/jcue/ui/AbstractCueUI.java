package jcue.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import jcue.domain.AbstractCue;
import jcue.domain.CueList;
import jcue.domain.StartMode;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jaakko
 */
public class AbstractCueUI implements PropertyChangeListener, ActionListener  {

    private JLabel nameLabel, descLabel;
    private JTextField nameField, descField;
    
    private JLabel startModeLabel, delayLabel, cueLabel;
    private JComboBox startModeSelect, cueSelect;
    private JFormattedTextField delayField;
    
    private AbstractCue cue;
    
    public static JPanel lastPanel = null;

    public AbstractCueUI() {
        this.nameLabel = new JLabel("Name:");
        this.nameField = new JTextField();
        this.nameField.addActionListener(this);

        this.descLabel = new JLabel("Description:");
        this.descField = new JTextField();
        this.descField.addActionListener(this);

        this.startModeLabel = new JLabel("Start mode:");
        StartMode[] modes = {
            StartMode.MANUAL,
            StartMode.AFTER_START,
            StartMode.AFTER_END,
            StartMode.HOTKEY
        };
        this.startModeSelect = new JComboBox(modes);
        this.startModeSelect.addActionListener(this);

        this.cueLabel = new JLabel("Cue:");
        this.cueSelect = new JComboBox();
        this.cueSelect.addActionListener(this);

        this.delayLabel = new JLabel("Delay:");
        
        NumberFormat delayFormat = NumberFormat.getNumberInstance();
        delayFormat.setMinimumFractionDigits(2);
        delayFormat.setMaximumFractionDigits(2);
        
        this.delayField = new JFormattedTextField(delayFormat);
        this.delayField.setColumns(5);
        this.delayField.addPropertyChangeListener(this);
    }

    public void showUI(JPanel panel) {
        panel.removeAll();
        
        panel.setLayout(new MigLayout("fillx"));
        
        panel.add(this.nameLabel);
        panel.add(this.nameField, "span 4, growx, wrap");
        
        panel.add(this.descLabel);
        panel.add(this.descField, "span 4, growx, wrap");
        
        panel.add(this.startModeLabel);
        panel.add(this.startModeSelect);
        
        panel.add(this.cueLabel);
        
        ArrayList<AbstractCue> cues = CueList.getInstance().getCues();
        AbstractCue[] tmpArray = new AbstractCue[cues.size() + 1];
        AbstractCue[] cueArray = cues.toArray(tmpArray);
        ComboBoxModel cbm = new DefaultComboBoxModel(cueArray);
        
        this.cueSelect.setModel(cbm);
        this.cueSelect.setSelectedItem(null);
        panel.add(this.cueSelect, "span 2, growx, wmin 200");
        
        panel.add(this.delayLabel);
        panel.add(this.delayField, "wrap");
        
        panel.revalidate();
    }
    
    public void showUI2(JPanel container) {
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
        this.delayField.setValue(value);
    }
    
    public void setStartModeSelectValue(StartMode value) {
        this.startModeSelect.setSelectedItem(value);
    }
    
    public void setCueSelectValue(AbstractCue ac) {
        this.cueSelect.setSelectedItem(ac);
    }

    public void setCurrentCue(AbstractCue cue) {
        this.cue = cue;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        Object source = pce.getSource();
        
        if (source == this.delayField) {
            Number value = (Number) this.delayField.getValue();
            
            if (this.cue != null) {
                this.cue.setStartDelay(value.doubleValue());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        
        if (source == this.startModeSelect) {
            JComboBox cb = (JComboBox) source;
            StartMode mode = (StartMode) cb.getSelectedItem();
            
            this.cue.setStartMode(mode);
        } else if (source == this.cueSelect) {
            JComboBox cb = (JComboBox) source;
            AbstractCue ac = (AbstractCue) cb.getSelectedItem();
            
            if (ac != null) {
                StartMode sm = this.cue.getStartMode();
                
                if (sm == StartMode.AFTER_START || sm == StartMode.AFTER_END) {
                    this.cue.setParentCue(ac);
                } else {
                    this.cueSelect.setSelectedItem(null);
                }
            }
        } else if (source == this.nameField) {
            this.cue.setName(this.nameField.getText());
        } else if (source == this.descField) {
            this.cue.setDescription(this.descField.getText());
        }
    }
}
