package jcue.domain;

import java.util.ArrayList;
import javax.swing.JPanel;
import jcue.ui.AudioCueUI;

/**
 * 
 *
 * @author Jaakko
 */
public class AudioCue extends AbstractCue {

    private AudioStream audio;
    private double inPos, outPos;
    private double fadeIn, fadeOut;
    private CueState state;
    private double volume, pan;
    
    private ArrayList<SoundDevice> outputs;
    
    private static AudioCueUI ui = new AudioCueUI();

    public AudioCue(String name, String description, ArrayList<SoundDevice> outputs) {
        super(name, description, CueType.AUDIO);
        
        this.outputs = outputs;

        this.state = CueState.STOPPED;
        this.audio = new AudioStream(this.outputs);
        
        this.volume = 1.0;
        this.pan = 0.0;
        
        this.fadeIn = 0.0;
        this.fadeOut = 0.0;
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
    }

    @Override
    public void start() {
        if (this.audio != null) {
            //TODO: uncomment when multi-device shit works
//            //Set audio position to start if the cue is not paused
//            if (this.state == CueState.STOPPED) {
//                this.audio.setPosition(this.inPos);
//                this.audio.setVolume(this.volume);
//            }
//            
//            //Fade in, if there is fade in time specified
//            if (this.fadeIn != 0) {
//                this.audio.setVolume(0);    //Start fading from silent
//                this.audio.startVolumeChange(this.volume, this.fadeIn);
//            }
            
            this.audio.play();              //Start playing the audio
            this.state = CueState.PLAYING;  //Set state to playing
            
            //TODO: start automatic cues
        }
    }

    @Override
    public void pause() {
        if (this.audio != null) {
            //TODO: pause fades when pausing audio
            this.audio.pause();
            this.state = CueState.PAUSED;
        }
    }

    @Override
    public void stop() {
        if (this.audio != null) {
            this.audio.stop();
            this.audio.setPosition(this.inPos);
            this.state = CueState.STOPPED;
            
            //TODO: start automatic cues
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

    public CueState getState() {
        return state;
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
    }

    public void setPan(double pan) {
        this.pan = pan;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
    
    @Override
    public void updateUI(JPanel panel) {
        super.updateUI(panel);
        
        ui.showUI(panel);
        
        ui.setFileFieldText(this.audio.getFilePath());
        ui.setLengthFieldValue(this.audio.getLength());
        
        ui.setInFieldValue(this.inPos);
        ui.setOutFieldValue(this.outPos);
        
        ui.setFadeInFieldValue(this.fadeIn);
        ui.setFadeOutFieldValue(this.fadeOut);
        
        ui.setVolumeControlValue(this.volume);
        ui.setPanControlValue(this.pan);
        
        ui.setWaveformData(this.audio);
    }
}
