package jcue.domain.eventcue;

import java.util.ArrayList;
import jcue.domain.AbstractCue;
import jcue.domain.CueType;

/**
 * Cue for launching events on other cues.
 * 
 * @author Jaakko
 */
public class EventCue extends AbstractCue {
    
    private ArrayList<Event> events;

    public EventCue(String name, String description) {
        super(name, description, CueType.EVENT);
        
        this.events = new ArrayList<Event>();
    }
    
    @Override
    public void start(boolean delay) {
        super.start(delay);
    }

    @Override
    public void pause() {
    }
}
