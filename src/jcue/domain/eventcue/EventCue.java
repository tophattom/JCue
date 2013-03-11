package jcue.domain.eventcue;

import java.util.ArrayList;
import jcue.domain.AbstractCue;
import jcue.domain.CueState;
import jcue.domain.CueType;
import jcue.ui.EventCueUI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
        
        if (!delay || super.getStartDelay() == 0) {
            for (AbstractEvent ae : this.events) {
                ae.perform();
            }
            
            super.stop();
        }
        
        super.state = CueState.DONE;
    }

    @Override
    public void pause() {
    }

    @Override
    public Element toElement(Document doc) {
        Element result = super.toElement(doc);

        //Events
        Element eventsElem = doc.createElement("events");
        for (AbstractEvent ae : events) {
            eventsElem.appendChild(ae.toElement(doc));
        }
        result.appendChild(eventsElem);

        return result;
    }
}
