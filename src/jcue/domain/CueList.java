package jcue.domain;

import java.util.ArrayList;

/**
 *
 * @author Jaakko
 */
public class CueList {
    
    private ArrayList<AbstractCue> cues;
    
    private AbstractCue currentCue;
    
    private int counter;
    
    private DeviceManager dm;

    public CueList(DeviceManager dm) {
        this.cues = new ArrayList<AbstractCue>();
        this.currentCue = null;
        
        this.counter = 1;
        
        this.dm = dm;
    }

    public void addCue(CueType cueType) {
        AbstractCue cue = null;
        
        if (cueType == CueType.AUDIO) {
            cue = new AudioCue("Q" + counter, "", this.dm.getAutoIncludeDevices());
        } else if (cueType == CueType.EVENT) {
        } else if (cueType == CueType.CHANGE) {
        } else if (cueType == CueType.NOTE) {
        }
        
        this.cues.add(cue);
    }
    
    public void addCue(AbstractCue cue) {
        this.cues.add(cue);
    }
    
    public AbstractCue getCue(int pos) {
        return this.cues.get(pos);
    }
    
    public int size() {
        return this.cues.size();
    }
}
