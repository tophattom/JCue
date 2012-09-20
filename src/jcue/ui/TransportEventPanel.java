package jcue.ui;

import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import jcue.domain.eventcue.AbstractEvent;
import jcue.domain.eventcue.EventCue;
import jcue.domain.eventcue.TransportEvent;

/**
 *
 * @author Jaakko
 */
public class TransportEventPanel extends AbstractEventPanel {
    
    private TransportEvent event;
    
    private ButtonGroup modeGroup;
    private ArrayList<JRadioButton> modeSelect;
    
    public TransportEventPanel(EventCue cue) {
        super(cue);
        
        this.modeGroup = new ButtonGroup();
        
        this.modeSelect = new ArrayList<JRadioButton>();
        for (int i = 1; i <= TransportEvent.MODE_COUNT; i++) {
            JRadioButton rb = new JRadioButton(TransportEvent.getModeString(i));
            this.modeSelect.add(rb);
            this.modeGroup.add(rb);
        }
        
        addComponents();
    }
    
    private void addComponents() {
        for (JRadioButton rb : this.modeSelect) {
            this.add(rb, "wrap");
        }
    }

    public void setEvent(TransportEvent event) {
        this.event = event;
    }
    
    
}
