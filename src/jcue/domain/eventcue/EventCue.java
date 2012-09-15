package jcue.domain.eventcue;

import java.util.ArrayList;
import javax.swing.JPanel;
import jcue.domain.AbstractCue;
import jcue.domain.CueType;
import jcue.ui.EventCueUI;

/**
 * Cue for launching events on other cues.
 * 
 * @author Jaakko
 */
public class EventCue extends AbstractCue {
    
    private ArrayList<AbstractEvent> events;
    
    private static EventCueUI ui = new EventCueUI();

    public EventCue(String name, String description) {
        super(name, description, CueType.EVENT);
        
        this.events = new ArrayList<AbstractEvent>();
    }
    
    public void addEvent(AbstractEvent ae) {
        this.events.add(ae);
    }
    
    public void removeEvent(AbstractEvent ae) {
        if (this.events.contains(ae)) {
            this.events.remove(ae);
        }
    }

    public ArrayList<AbstractEvent> getEvents() {
        return events;
    }
    
    @Override
    public void start(boolean delay) {
        super.start(delay);
        
        if (!delay) {
            for (AbstractEvent ae : this.events) {
                ae.perform();
            }
            
            super.stop();
        }
    }

    @Override
    public void pause() {
    }
}
