package jcue.domain;

import java.util.Iterator;
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
        double delay = this.currentCue.getStartDelay();
        
        if (delay > 0) {
            this.currentCue.setStartTime(System.nanoTime());
            this.waitList.add(currentCue);
        } else {
            this.currentCue.start();
            this.playingList.add(currentCue);
        }
    }

    @Override
    public void run() {
        while (running) {
            //Check delayed cues
            Iterator<AbstractCue> it = this.waitList.iterator();
            while (it.hasNext()) {
                AbstractCue ac = it.next();
                
                double delay = ac.getStartDelay();
                long lDelay = (long) (delay * 1000000000);
                long startTime = ac.getStartTime();
                
                //Wait time over, start cue
                if (System.nanoTime() > (startTime + lDelay)) {
                    ac.start();
                    this.playingList.add(ac);   //Add to playing list
                    
                    it.remove();    //Remove from wait list
                }
            }
            
            //Sleep a bit
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
