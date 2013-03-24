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
    
    private AbstractCue currentCue;
    
    private boolean running;
    private Thread updater;

    public CuePlayer(CueList cues) {
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

        if (currentCue == null && cues.size() > 0) {
            this.currentCue = this.cues.getCue(0);
        }
    }

    public void stop() {
        running = false;
    }

    /**
     * Starts next cue
     */
    public void startNext() {
        this.activeCues.put(currentCue, currentCue.getState());
        this.currentCue.start(true);

        this.currentCue = getNextManualCue();
    }

    /**
     * Stops all cues
     */
    public void stopAll() {
        for (AbstractCue ac : this.cues.getCues()) {
            ac.stop();
        }

        if (cues.size() > 0) {
            this.currentCue = this.cues.getCue(0);
        }

        cues.fireTableDataChanged();
    }
    
    @Override
    public void run() {
        int size = cues.size();

        while (running) {
            Iterator<Entry<AbstractCue, CueState>> iterator = this.activeCues.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<AbstractCue, CueState> entry = iterator.next();
                AbstractCue ac = entry.getKey();
                CueState oldState = entry.getValue();
                
                CueState newState = ac.getState();
                if (newState != oldState) {
                    this.cues.fireTableCellUpdated(this.cues.getCueIndex(ac), 2);
                    
                    if (newState == CueState.STOPPED || newState == CueState.DONE) {
                        iterator.remove();
                    }
                }
            }

            for (int i = 0; i < size; i++) {
                cues.fireTableCellUpdated(i, 4);
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
