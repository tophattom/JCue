package jcue.domain;

import java.util.LinkedList;

/**
 *
 * @author Jaakko
 */
public class CuePlayer implements Runnable {
    
    private CueList cues;
    
    private LinkedList<AbstractCue> waitList;
    private LinkedList<AbstractCue> playingList;
    
    private AbstractCue currentCue;
    
    private boolean running;
    private Thread updater;

    public CuePlayer(CueList cues) {
        this.waitList = new LinkedList<AbstractCue>();
        
        this.running = false;
        this.cues = cues;
    }
    
    public void start() {
        if (this.updater == null || !this.running) {
            this.updater = new Thread(this);
        }
        
        this.updater.start();
        running = true;
    }
    
    public void stop() {
        running = false;
    }
    
    public void startNext() {
        this.currentCue.start();
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
    
}
