package jcue.domain.eventcue;

import jcue.domain.SoundDevice;
import jcue.domain.audiocue.AudioCue;

/**
 * Represents an event that can be applied to a cue.
 * 
 * @author Jaakko
 */
public abstract class AbstractEvent {
    
    protected AudioCue targetCue;
    protected SoundDevice targetOutput;

    public void setTargetCue(AudioCue targetCue) {
        this.targetCue = targetCue;
    }

    public void setTargetOutput(SoundDevice targetOutput) {
        this.targetOutput = targetOutput;
    }

    public AudioCue getTargetCue() {
        return targetCue;
    }

    public SoundDevice getTargetOutput() {
        return targetOutput;
    }

    
    public abstract void perform();
}
