package jcue.ui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import jcue.domain.SoundDevice;
import jcue.domain.eventcue.EventCue;
import jcue.domain.eventcue.MuteEvent;

/**
 *
 * @author Jaakko
 */
public class MuteEventPanel extends AbstractEventPanel {

    private MuteEvent event;
    
    private JLabel targetOutLabel;
    private JComboBox targetOutSelect;
    
    private ButtonGroup modeGroup;
    private ArrayList<JRadioButton> modeSelect;
    
    public MuteEventPanel(EventCue cue) {
        super(cue);
        
        this.targetOutLabel = new JLabel("Target output:");
        this.targetOutSelect = new JComboBox();
        this.targetOutSelect.addActionListener(this);
        
        this.modeGroup = new ButtonGroup();
        this.modeSelect = new ArrayList<JRadioButton>();
        
        for (int i = 1; i <= MuteEvent.MODE_COUNT; i++) {
            JRadioButton rb = new JRadioButton(MuteEvent.getModeString(i));
            rb.addActionListener(this);
            
            this.modeSelect.add(rb);
            this.modeGroup.add(rb);
        }
        
        addComponents();
    }

    private void addComponents() {
        this.add(this.targetOutLabel, "split 2");
        this.add(this.targetOutSelect, "wmin 200, span, wrap");
        
        for (JRadioButton rb : this.modeSelect) {
            this.add(rb, "wrap");
        }
    }

    public void setEvent(MuteEvent event) {
        super.setEvent(event);
        
        this.event = event;
        
        this.update();
    }

    @Override
    protected void update() {
        super.update();
        
        if (this.event != null) {
            if (this.event.getTargetCue() != null) {
                //Update output select menu
                ArrayList<SoundDevice> outputs = this.event.getTargetCue().getOutputs();
                SoundDevice[] tmpArray = new SoundDevice[outputs.size()];
                SoundDevice[] deviceArray = outputs.toArray(tmpArray);

                ComboBoxModel cbm = new DefaultComboBoxModel(deviceArray);
                this.targetOutSelect.setModel(cbm);

                //Set currently selected output
                this.targetOutSelect.setSelectedItem(this.event.getTargetOutput());
            }
            
            //Set currently selected mode
            int mode = this.event.getMode();
            for (int i = 1; i <= MuteEvent.MODE_COUNT; i++) {
                if (mode == i) {
                    this.modeSelect.get(i - 1).setSelected(true);
                    break;
                }
            }
        } else {
            this.targetOutSelect.setSelectedItem(null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        super.actionPerformed(ae);
        
        Object source = ae.getSource();
        
        if (source == super.targetCueSelect) {
            this.update();
        } else if (source == this.targetOutSelect) {
            JComboBox cb = (JComboBox) source;
            
            if (this.event != null) {
                this.event.setTargetOutput((SoundDevice) cb.getSelectedItem());
            }
        }
        
        for (int i = 0; i < MuteEvent.MODE_COUNT; i++) {
            if (source == this.modeSelect.get(i)) {
                this.event.setMode(i + 1);
                break;
            }
        }
    }
    
    
}
