package jcue.domain;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jaakko
 */
public class CueDelayHandler implements Runnable {
    
    private long startTime;
    private long delay;
    private AbstractCue cue;
    
    private Thread waiter;
    private boolean running;
    
    public CueDelayHandler(long startTime, double delay, AbstractCue cue) {
        this.startTime = startTime;
        this.delay = (long) (delay * 1000000000L);
        
        this.cue = cue;
        
        this.waiter = new Thread(this, "DelayHandler for " + cue.getName());
        this.running = true;
        this.waiter.start();
    }

    @Override
    public void run() {
        while (running) {
            if (System.nanoTime() >= (this.startTime + this.delay)) {
                this.cue.start(false);
                this.running = false;
            }
            
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
