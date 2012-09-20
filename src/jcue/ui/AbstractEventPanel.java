package jcue.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import jcue.domain.eventcue.AbstractEvent;
import jcue.domain.eventcue.EventCue;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jaakko
 */
public class AbstractEventPanel extends JPanel implements ActionListener {
    
    private EventCue cue;
    
    private JLabel targetCueLabel;
    private JComboBox targetCueSelect;
    
    
    private static final ImageIcon removeIcon = new ImageIcon("images/remove_small.png");

    public AbstractEventPanel(EventCue cue) {
        super(new MigLayout("fillx, insets panel"));
        
        this.cue = cue;
        
        this.targetCueLabel = new JLabel("Target cue:");
        this.targetCueSelect = new JComboBox();
        
        addComponents();
    }
    
    private void addComponents() {
        this.add(this.targetCueLabel, "split 2");
        this.add(this.targetCueSelect, "wmin 200, span, wrap");
    }

    public void setCue(EventCue cue) {
        this.cue = cue;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        
        
    }
    
    
}
