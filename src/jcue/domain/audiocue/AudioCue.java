package jcue.domain.audiocue;

import java.awt.geom.QuadCurve2D;
import java.io.File;
import java.util.ArrayList;
import jcue.domain.*;
import jcue.domain.audiocue.effect.EffectRack;
import jcue.domain.fadecue.ParameterEnvelope;

/**
 * 
 *
 * @author Jaakko
 */
public class AudioCue extends AbstractCue {

    private AudioStream audio;
    private double inPos, outPos;
    
    private double fadeIn, fadeOut;
    private ParameterEnvelope fadeInCurve, fadeOutCurve;
    
    private double volume;
    
    private ArrayList<SoundDevice> outputs;
    
    private EffectRack effectRack;

    public AudioCue(String name, String description, ArrayList<SoundDevice> outputs) {
        super(name, description, CueType.AUDIO);
        
        this.outputs = outputs;

        this.audio = new AudioStream(this.outputs);
        
        this.volume = 1.0;
        
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
        
        effectRack.resetToDefaults();
        
        if (!delay || super.getStartDelay() == 0) {
            if (this.audio != null) {
                //Set audio position to start if the cue is not paused
                if (this.state == CueState.STOPPED) {
                    this.audio.setPosition(this.inPos);
                    this.audio.setMasterVolume(this.volume);
                    
                    //If fade in time has been set, start the fade
                    if (this.fadeInCurve != null) {
                        this.audio.setMasterVolumeDirect(0);
                        fadeInCurve.start();
                    }
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
        
        if (this.fadeInCurve != null) {
            fadeInCurve.stop();
        }

        if (this.fadeOutCurve != null) {
            fadeOutCurve.stop();
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

    public double getVolume() {
        return volume;
    }
    
    public double getDeviceVolume(SoundDevice sd) {
        return this.audio.getDeviceVolume(sd);
    }
    
    public double getRemainingTime() {
        return this.outPos - this.audio.getPosition();
    }

    public AudioStream getAudio() {
        return audio;
    }
    
    public void setFadeIn(double fadeIn) {
        this.fadeIn = fadeIn;
        
        if (fadeIn > 0) {
            if (this.fadeInCurve == null) {
                fadeInCurve = new ParameterEnvelope();
                
                fadeInCurve.setTargetCue(this);
            }
            
            this.updateFadeCurves();
        } else {
            fadeInCurve = null;
        }
    }

    public void setFadeOut(double fadeOut) {
        this.fadeOut = fadeOut;
        
        if (fadeOut > 0) {
            if (this.fadeOutCurve == null) {
                fadeOutCurve = new ParameterEnvelope();
                
                fadeOutCurve.setTargetCue(this);
            }
            
            this.updateFadeCurves();
            
        } else {
            fadeOutCurve = null;
        }
    }

    public void setInPos(double inPos) {
        this.inPos = inPos;
    }

    public void setOutPos(double outPos) {
        this.outPos = outPos;
        this.audio.setOutPosition(outPos, this);
        
        this.updateFadeCurves();
    }

    public void setVolume(double volume) {
        this.volume = volume;
        this.audio.setMasterVolume(volume);
        
        this.updateFadeCurves();
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
    
    private void updateFadeCurves() {
        double midY = this.volume / 2.0;
        
        if (this.fadeInCurve != null) {
            QuadCurve2D inCurve = fadeInCurve.getCurves().get(0);
            fadeInCurve.setCurve(inCurve, 0, 1, 0.5, midY, 1, 1.0 - this.volume, false);
            fadeInCurve.setDuration(this.fadeIn);
        }
        
        if (this.fadeOutCurve != null) {
            QuadCurve2D outCurve = fadeOutCurve.getCurves().get(0);
            fadeOutCurve.setCurve(outCurve, 0, 1.0 - this.volume, 0.5, midY, 1, 1, false);
            fadeOutCurve.setDuration(this.fadeOut);
            
            this.audio.setFadeOut(this.outPos - this.fadeOut, fadeOutCurve);
        }
    }
}
