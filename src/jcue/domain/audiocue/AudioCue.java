package jcue.domain.audiocue;

import java.io.File;
import java.util.ArrayList;
import jcue.domain.AbstractCue;
import jcue.domain.CueList;
import jcue.domain.CueState;
import jcue.domain.CueType;
import jcue.domain.SoundDevice;
import jcue.domain.audiocue.effect.EffectRack;
import jcue.domain.audiocue.effect.ReverbEffect;

/**
 * 
 *
 * @author Jaakko
 */
public class AudioCue extends AbstractCue {

    private AudioStream audio;
    private double inPos, outPos;
    private double fadeIn, fadeOut;
    
    private double volume, pan;
    
    private ArrayList<SoundDevice> outputs;
    
    private EffectRack effectRack;

    public AudioCue(String name, String description, ArrayList<SoundDevice> outputs) {
        super(name, description, CueType.AUDIO);
        
        this.outputs = outputs;

        this.audio = new AudioStream(this.outputs);
        
        this.volume = 1.0;
        this.pan = 0.0;
        
        this.fadeIn = 0.0;
        this.fadeOut = 0.0;
        
        this.effectRack = new EffectRack(this);
    }

    public void loadAudio(String filePath) {
        if (filePath == null) {
            return;
        }
        
        try {
            this.audio.loadFile(filePath);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        this.inPos = 0;
        this.outPos = this.audio.getLength();
        
        if (this.getDescription().isEmpty()) {
            File audioFile = new File(filePath);
            String desc = audioFile.getName();
            
            this.setDescription(desc);
        }
        
        CueList cl = CueList.getInstance();
        cl.fireContentsChanged(this, cl.getCueIndex(this) - 1, cl.getCueIndex(this));
    }

    @Override
    public void start(boolean delay) {
        super.start(delay);
        
        if (!delay || super.getStartDelay() == 0) {
            if (this.audio != null) {
                //Set audio position to start if the cue is not paused
                if (this.state == CueState.STOPPED) {
                    this.audio.setPosition(this.inPos);
                    this.audio.setMasterVolume(this.volume);
                }
                
                this.audio.play();              //Start playing the audio
            }
        }
    }

    @Override
    public void pause() {
        if (this.audio != null) {
            //TODO: pause fades when pausing audio
            this.audio.pause();
            super.state = CueState.PAUSED;
        }
    }

    @Override
    public void stop() {
        super.stop();
        
        if (this.audio != null) {
            this.audio.stop();
            this.audio.setPosition(this.inPos);
        }
    }

    //Getters and setters
    public double getFadeIn() {
        return fadeIn;
    }

    public double getFadeOut() {
        return fadeOut;
    }

    public double getInPos() {
        return inPos;
    }

    public double getOutPos() {
        return outPos;
    }

    public double getPan() {
        return pan;
    }

    public double getVolume() {
        return volume;
    }
    
    public double getDeviceVolume(SoundDevice sd) {
        return this.audio.getDeviceVolume(sd);
    }

    public AudioStream getAudio() {
        return audio;
    }
    
    public void setFadeIn(double fadeIn) {
        this.fadeIn = fadeIn;
    }

    public void setFadeOut(double fadeOut) {
        this.fadeOut = fadeOut;
    }

    public void setInPos(double inPos) {
        this.inPos = inPos;
    }

    public void setOutPos(double outPos) {
        this.outPos = outPos;
        this.audio.setOutPosition(outPos, this);
    }

    public void setPan(double pan) {
        this.pan = pan;
    }

    public void setVolume(double volume) {
        this.volume = volume;
        this.audio.setMasterVolume(volume);
    }
    
    public void setDeviceVolume(SoundDevice sd, double volume) {
        this.audio.setDeviceVolume(volume, sd);
    }

    public ArrayList<SoundDevice> getOutputs() {
        return outputs;
    }
    
    public void addOutput(SoundDevice sd) {
        if (!this.outputs.contains(sd)) {
            this.outputs.add(sd);
            this.audio.addOutput(sd);
        }
    }
    
    public boolean removeOutput(SoundDevice sd) {
        if (!this.outputs.contains(sd)) {
            return true;
        }
        
        if (this.outputs.size() == 1) {
            return false;
        }
        
        this.audio.removeOutput(sd);
        this.outputs.remove(sd);
        
        CueList cl = CueList.getInstance();
        cl.fireContentsChanged(this, cl.getCueIndex(this) - 1, cl.getCueIndex(this));
        
        return true;
    }

    public EffectRack getEffectRack() {
        return effectRack;
    }
}
