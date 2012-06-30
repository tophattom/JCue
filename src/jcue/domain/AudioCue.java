package jcue.domain;

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

    public AudioCue(String name, String description) {
        super(name, description, CueType.AUDIO);

        this.state = CueState.STOPPED;
        this.audio = new AudioStream();
        
        this.volume = 1.0;
        this.pan = 0.0;
        
        this.fadeIn = 0.0;
        this.fadeOut = 0.0;
    }

    public void loadAudio(String filePath) {
        if (filePath == null) {
            return;
        }

        this.audio.loadFile(filePath);
        this.inPos = 0;
        this.outPos = this.audio.getLength();
    }

    @Override
    public void start() {
        if (this.audio != null) {
            //Set audio position to start if the cue is not paused
            if (this.state == CueState.STOPPED) {
                this.audio.setPosition(this.inPos);
                this.audio.setVolume(this.volume);
            }
            
            //Fade in, if there is fade in time specified
            if (this.fadeIn != 0) {
                this.audio.setVolume(0);    //Start fading from silent
                this.audio.startVolumeChange(this.volume, this.fadeIn);
            }
            
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
}
