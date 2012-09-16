package jcue.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import jcue.domain.eventcue.AbstractEvent;
import jcue.domain.eventcue.EventCue;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jaakko
 */
public class EventControlPanel extends JPanel implements ActionListener {
    
    private EventCue cue;
    private AbstractEvent event;
    
    private JComboBox targetCueSelect;
    
    private JButton removeButton;
    
    private static final ImageIcon removeIcon = new ImageIcon("images/remove_small.png");

    public EventControlPanel(EventCue cue, AbstractEvent event) {
        super(new MigLayout("fillx"));
        super.setBorder(BorderFactory.createTitledBorder(event.toString()));
        
        this.cue = cue;
        this.event = event;
        
        this.removeButton = new JButton(removeIcon);
        this.removeButton.addActionListener(this);
        
        addComponents();
    }
    
    private void addComponents() {
        this.add(this.removeButton);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        
        if (source == this.removeButton) {
            this.cue.removeEvent(this.event);
        }
    }
    
    
}
