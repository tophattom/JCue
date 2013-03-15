package jcue.domain.eventcue;

import java.util.ArrayList;

import jcue.domain.*;
import jcue.ui.EventCueUI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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

    public static EventCue fromElement(Element elem) {
        //General cue stuff
        String name = ProjectFile.getTagValue("name", elem);
        String description = ProjectFile.getTagValue("description", elem);
        StartMode startMode = StartMode.fromString(ProjectFile.getTagValue("startmode", elem));
        double delay = Double.parseDouble(ProjectFile.getTagValue("delay", elem));

        String parentName = ProjectFile.getTagValue("parentcue", elem);
        AbstractCue parentCue = CueList.getInstance().getCue(parentName);

        //Create cue
        EventCue result = new EventCue(name, description);

        //Set cue parameters
        result.setStartMode(startMode);
        result.setStartDelay(delay);
        if (parentCue != null) {
            result.setParentCue(parentCue);
        } else {
            ProjectFile.addToParentQueue(result, parentName);
        }

        //Parse events
        NodeList eventNodes = elem.getElementsByTagName("event");
        for (int i = 0; i < eventNodes.getLength(); i++) {
            Element eventElem = (Element) eventNodes.item(i);

            if (eventElem.getParentNode().getParentNode() == elem) {
                result.addEvent(AbstractEvent.fromElement(eventElem));
            }
        }

        return result;
    }
}
