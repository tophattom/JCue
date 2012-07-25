package jcue.domain;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author Jaakko
 */
public class CueList extends AbstractListModel {
    
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
        
        //Notify JList about new element
        super.fireIntervalAdded(cue, getSize() - 1,  getSize());
        
        //Increment the "id" counter
        this.counter++;
    }
    
    public void addCue(AbstractCue cue) {
        this.cues.add(cue);
        
        //Notify JList about new element
        super.fireIntervalAdded(cue, getSize() - 1,  getSize());
        
        //Increment the "id" counter
        this.counter++;
    }
    
    public AbstractCue getCue(int pos) {
        return this.cues.get(pos);
    }
    
    public int size() {
        return this.cues.size();
    }

    @Override
    public int getSize() {
        return this.cues.size();
    }

    @Override
    public Object getElementAt(int i) {
        return this.cues.get(i);
    }
}
