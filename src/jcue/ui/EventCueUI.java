package jcue.ui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import jcue.domain.eventcue.AbstractEvent;
import jcue.domain.eventcue.EventCue;

/**
 *
 * @author Jaakko
 */
public class EventCueUI extends AbstractCueUI {
    
    private JLabel listLabel;
    private JList eventsList;
    
    private JButton addTransport, addLoop, addMute, addEffect;
    
    private JLabel targetCueLabel;
    private JComboBox selectTargetCue;
    
    private JLabel targetOutLabel;
    private JComboBox selectTargetOut;
    
    private JLabel targetEffectLabel;
    private JComboBox selectTargetEffect;
    
    private EventCue cue;
    private AbstractEvent event;

    public EventCueUI() {
        this.listLabel = new JLabel("Events:");
        this.eventsList = new JList();
        
        this.addTransport = new JButton("Transport event");
        this.addMute = new JButton("Mute event");
        this.addLoop = new JButton("Loop event");
        this.addEffect = new JButton("Effect event");
        
        this.targetCueLabel = new JLabel("Target cue:");
        this.selectTargetCue = new JComboBox();
        
        this.targetOutLabel = new JLabel("Target output:");
        this.selectTargetOut = new JComboBox();
        
        this.targetEffectLabel = new JLabel("Target effect:");
        this.selectTargetEffect = new JComboBox();
    }
    
    public void showUI(JPanel panel) {
        panel.add(this.listLabel);
        
        panel.revalidate();
    }
}
