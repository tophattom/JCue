package jcue.ui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
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
            
            rb.setActionCommand(Integer.toString(i));
            rb.addActionListener(this);
            
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
        super.setEvent(event);
        
        this.event = event;
        
        update();
    }

    @Override
    protected void update() {
        super.update();
        
        if (this.event != null) {
            int mode = this.event.getMode();
            
            for (int i = 1; i <= TransportEvent.MODE_COUNT; i++) {
                if (mode == i) {
                    this.modeSelect.get(i - 1).setSelected(true);
                    break;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        super.actionPerformed(ae);
        
        Object source = ae.getSource();
        
        for (int i = 0; i < TransportEvent.MODE_COUNT; i++) {
            if (source == this.modeSelect.get(i)) {
                this.event.setMode(i + 1);
                break;
            }
        }
    }
    
    
}
