/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcue.domain;

/**
 *
 * @author Jaakko
 */
public class AudioCue extends Cue {
    
    private AudioStream audio;
    
    private double inPos, outPos;
    private double fadeIn, fadeOut;
    
    private CueState state;
    
    private double volume, pan;

    public AudioCue(String name, String description) {
        super(name, description, CueType.AUDIO);
        
        this.state = CueState.STOPPED;
    }
    
    public void loadAudio(String filePath) {
        this.audio.loadFile(filePath);
        this.inPos = 0;
        this.outPos = this.audio.getLength();
    }

    @Override
    public void start() {
        if (this.audio != null) {
            //TODO: fade in
            this.audio.play();
            this.state = CueState.PLAYING;
        }
    }

    @Override
    public void pause() {
       if (this.audio != null) {
           //TODO: pause fades when pausing audio
           this.audio.pause();
       }
    }

    @Override
    public void stop() {
        if (this.audio != null) {
            this.audio.stop();
            this.state = CueState.STOPPED;
        }
    }
}
