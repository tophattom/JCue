package jcue.domain;

import java.util.LinkedHashSet;
import jcue.ui.AbstractCueUI;

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

    public LinkedHashSet<AbstractCue> getChildCues() {
        return childCues;
    }

    public AbstractCue getParentCue() {
        return parentCue;
    }

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

    public void stop() {
        for (AbstractCue child : this.childCues) {
            if (child.getStartMode() == StartMode.AFTER_END) {
                child.start(true);
            }
        }
        
        this.state = CueState.STOPPED;
    }
}
