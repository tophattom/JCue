package jcue.domain;

import java.util.LinkedHashSet;
import jcue.ui.AbstractCueUI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Base class for all different types of cues. Defines properties common to all
 * types such as name, description and start info. Also defines three abstract
 * methods for starting, stopping and pausing cues.
 *
 * @author Jaakko
 */
public abstract class AbstractCue {

    
    private String name, description;
    private StartMode startMode;
    
    protected CueState state;
    
    private double startDelay;
    private long startTime;
    
    private CueType type;
    
    private AbstractCue parentCue;
    private LinkedHashSet<AbstractCue> childCues;
    
    private static AbstractCueUI ui = new AbstractCueUI();

    public AbstractCue(String name, String description, CueType type) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.startMode = StartMode.MANUAL;
        
        this.state = CueState.STOPPED;
        
        this.parentCue = null;
        this.childCues = new LinkedHashSet<AbstractCue>();
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public StartMode getStartMode() {
        return startMode;
    }

    public double getStartDelay() {
        return startDelay;
    }

    public CueType getType() {
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDelay(double startDelay) {
        this.startDelay = startDelay;
    }

    public void setStartMode(StartMode startMode) {
        this.startMode = startMode;
        
        if (startMode == StartMode.MANUAL && this.parentCue != null) {
            this.setParentCue(null);
        }
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns cues that start after this cue.
     * 
     * @return child cues
     */
    public LinkedHashSet<AbstractCue> getChildCues() {
        return childCues;
    }

    /**
     * Returns cue after which this cue starts
     * 
     * @return parent cue
     */
    public AbstractCue getParentCue() {
        return parentCue;
    }

    /**
     * Sets the parent cue. Also adds this cue
     * to parent cue's child cue list. Null parameter removes
     * this cue from parent cue's children.
     * 
     * @param parentCue parent cue
     */
    public void setParentCue(AbstractCue parentCue) {
        if (this.parentCue != null) {
            this.parentCue.removeChildCue(this);
        }
        
        this.parentCue = parentCue;
        
        if (this.parentCue != null) {
            this.parentCue.addChildCue(this);
        }
    }
    
    public void addChildCue(AbstractCue cue) {
        this.childCues.add(cue);
    }
    
    public void removeChildCue(AbstractCue cue) {
        if (this.childCues.contains(cue)) {
            this.childCues.remove(cue);
        }
    }

    public CueState getState() {
        return state;
    }

    public void setState(CueState state) {
        this.state = state;
    }
    
    @Override
    public String toString() {
        return this.name + " " + this.description + " (" + this.type + ")";
    }
    
    public Element toElement(Document doc) {
        Element result = doc.createElement("cue");
        
        //Type
        Element typeElem = doc.createElement("type");
        typeElem.appendChild(doc.createTextNode(type.toString()));
        result.appendChild(typeElem);
        
        //Name and description
        Element nameElem = doc.createElement("name");
        nameElem.appendChild(doc.createTextNode(name));
        result.appendChild(nameElem);
        
        Element descElem = doc.createElement("description");
        descElem.appendChild(doc.createTextNode(description));
        result.appendChild(descElem);
        
        //Start mode
        Element modeElem = doc.createElement("startmode");
        modeElem.appendChild(doc.createTextNode(startMode.toString()));
        result.appendChild(modeElem);
        
        //Start delay
        Element delayElem = doc.createElement("delay");
        delayElem.appendChild(doc.createTextNode(Double.toString(startDelay)));
        result.appendChild(delayElem);
        
        //Parent elem
        Element parentElem = doc.createElement("parentcue");
        if (parentCue != null) {
            parentElem.appendChild(doc.createTextNode(parentCue.getName()));
        }
        result.appendChild(parentElem);
        
        //Children
        Element childrenElem = doc.createElement("children");
        for (AbstractCue ac : childCues) {
            Element childElem = doc.createElement("child");
            childElem.appendChild(doc.createTextNode(ac.getName()));
            childrenElem.appendChild(childElem);
        }
        result.appendChild(childrenElem);
        
        return result;
    }

    /**
     * Starts the cue. Delay parameter specifies wether to
     * take start delay into account or not. Starts child cues
     * that are supposed to start after start of this cue.
     * 
     * @param delay is delay noted
     */
    public void start(boolean delay) {
        if (delay && this.startDelay > 0) {
            new CueDelayHandler(System.nanoTime(), startDelay, this);
            this.state = CueState.WAITING;
        } else {
            for (AbstractCue child : this.childCues) {
                if (child.getStartMode() == StartMode.AFTER_START) {
                    child.start(true);
                }
            }
        }
    }

    public abstract void pause();

    /**
     * Stops the cue. Starts child cues that are supposed
     * to start after end of this cue.
     */
    public void stop() {
        for (AbstractCue child : this.childCues) {
            if (child.getStartMode() == StartMode.AFTER_END) {
                child.start(true);
            }
        }
        
        this.state = CueState.STOPPED;
    }
}
