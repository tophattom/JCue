package jcue.domain;

/**
 * Handles delaying of cues. If cue has delay specified,
 * a new instance of this class is created. When delay time
 * has passed CueDelayHandler starts the cue.
 * 
 * @author Jaakko
 */
public class CueDelayHandler implements Runnable {
    
    private long startTime;
    private long delay;
    private AbstractCue cue;
    
    private Thread waiter;
    private boolean running;
    
    /**
     * 
     * @param startTime time of starting cue in nanoseconds
     * @param delay time to wait in seconds
     * @param cue cue to start
     */
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
