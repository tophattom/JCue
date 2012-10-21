package jcue.domain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

/**
 * Handles playback of the CueList.
 * 
 * @author Jaakko
 */
public class CuePlayer implements Runnable {

    private CueList cues;
    
    private HashMap<AbstractCue, CueState> activeCues;
    private LinkedList<AbstractCue> activeList;
    
    private AbstractCue currentCue;
    
    private boolean running;
    private Thread updater;

    public CuePlayer(CueList cues) {
        this.activeList = new LinkedList<AbstractCue>();
        this.activeCues = new HashMap<AbstractCue, CueState>();

        this.running = false;
        this.cues = cues;
    }

    /**
     * Starts the updater loop.
     */
    public void start() {
        if (this.updater == null || !this.running) {
            this.updater = new Thread(this, "CuePlayer");
        }

        this.updater.start();
        running = true;

        this.currentCue = this.cues.getCue(0);
    }

    public void stop() {
        running = false;
    }

    /**
     * Starts next cue
     */
    public void startNext() {
        this.currentCue.start(true);
        this.activeList.add(currentCue);
        this.activeCues.put(currentCue, currentCue.getState());

        this.currentCue = getNextManualCue();
    }

    /**
     * Stops all cues
     */
    public void stopAll() {
        for (AbstractCue ac : this.cues.getCues()) {
            ac.stop();
        }
        
        this.currentCue = this.cues.getCue(0);
    }
    
    @Override
    public void run() {
        while (running) {
            Iterator<Entry<AbstractCue, CueState>> iterator = this.activeCues.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<AbstractCue, CueState> entry = iterator.next();
                AbstractCue ac = entry.getKey();
                CueState oldState = entry.getValue();
                
                CueState newState = ac.getState();
                if (newState != oldState) {
                    this.cues.fireTableCellUpdated(this.cues.getCueIndex(ac), 3);
                    
                    if (newState == CueState.STOPPED || newState == CueState.DONE) {
                        iterator.remove();
                    }
                }
            }

            //Sleep a bit
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setCurrentCue(AbstractCue currentCue) {
        this.currentCue = currentCue;
    }

    /**
     * Returns next cue that has "Manual" as start mode.
     * 
     * @return next manual cue
     */
    private AbstractCue getNextManualCue() {
        int index = this.cues.getCueIndex(this.currentCue);
        int size = this.cues.size();
        AbstractCue result = null;

        for (index += 1; index < size; index++) {
            
                result = this.cues.getCue(index);
                if (result.getStartMode() == StartMode.MANUAL) {
                    break;
                } else {
                    result = null;
                }
            
        }

        return result;
    }
    
    public int getCurrentIndex() {
        if (this.currentCue == null) {
            return 0;
        }
        return this.cues.getCueIndex(this.currentCue);
    }

    public boolean isRunning() {
        return running;
    }
}
