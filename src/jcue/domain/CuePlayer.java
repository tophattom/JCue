package jcue.domain;

import java.util.LinkedList;

/**
 *
 * @author Jaakko
 */
public class CuePlayer implements Runnable {

    private CueList cues;
    
    private LinkedList<AbstractCue> playingList;
    
    private AbstractCue currentCue;
    
    private boolean running;
    private Thread updater;

    public CuePlayer(CueList cues) {
        this.playingList = new LinkedList<AbstractCue>();

        this.running = false;
        this.cues = cues;
    }

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

    public void startNext() {
        this.currentCue.start(true);
        this.playingList.add(currentCue);

        this.currentCue = getNextManualCue();
    }

    public void stopAll() {
        for (AbstractCue ac : this.playingList) {
            ac.stop();
        }
        
        this.currentCue = this.cues.getCue(0);
    }
    
    @Override
    public void run() {
        while (running) {

            //Sleep a bit
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setCurrentCue(AbstractCue currentCue) {
        this.currentCue = currentCue;
    }

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
        return this.cues.getCueIndex(this.currentCue);
    }

    public boolean isRunning() {
        return running;
    }
}
