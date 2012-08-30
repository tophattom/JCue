package jcue.domain;

import java.awt.geom.QuadCurve2D;

/**
 *
 * @author Jaakko
 */
public class ParameterEnvelope extends QuadCurve2D.Double implements Runnable {
    
    private AudioCue targetCue;
    
    private double startPos, endPos;
    
    private Thread updater;
    private boolean running = false;

    public ParameterEnvelope(AudioCue targetCue) {
        super();
        
        this.targetCue = targetCue;
        
        this.startPos = 0.0;
        this.endPos = 0.0;
    }
    
    public void start() {
        if (this.updater == null) {
            this.updater = new Thread(this, "Envelope " + this.targetCue.getName());
        }
        
        this.updater.start();
        this.running = true;
    }
    
    public void stop() {
        this.running = false;
    }
    

    @Override
    public void run() {
        while (this.running) {
            
            try {
                Thread.sleep(1);
            }  catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
}
