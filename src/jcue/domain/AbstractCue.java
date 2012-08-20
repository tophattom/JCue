package jcue.domain;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import javax.swing.JPanel;
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
    

    @Override
    public String toString() {
        return this.name + " " + this.description + " (" + this.type + ")";
    }
    
    
    
    public void updateUI(JPanel panel) {
        ui.showUI(panel);
        ui.setCurrentCue(this);
        
        ui.setNameFieldText(this.name);
        ui.setDescFieldText(this.description);
        ui.setDelayFieldValue(this.startDelay);
        ui.setStartModeSelectValue(this.startMode);
        ui.setCueSelectValue(this.parentCue);
        
        AbstractCueUI.lastPanel = panel;
    }
    
    public void updateUI() {
        this.updateUI(AbstractCueUI.lastPanel);
    }

    public abstract void start();

    public abstract void pause();

    public abstract void stop();
}
