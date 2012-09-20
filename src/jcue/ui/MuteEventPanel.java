package jcue.ui;

import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import jcue.domain.eventcue.AbstractEvent;
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
        
        this.modeGroup = new ButtonGroup();
        this.modeSelect = new ArrayList<JRadioButton>();
        
        for (int i = 1; i <= MuteEvent.MODE_COUNT; i++) {
            JRadioButton rb = new JRadioButton(MuteEvent.getModeString(i));
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
        this.event = event;
    }
    
    
}
