package jcue.domain;

/**
 * Represents a state of cue.
 * 
 * @author Jaakko
 */
public enum CueState {
    PLAYING("Playing"),
    STOPPED("Stopped"),
    PAUSED("Paused"),
    FADING("Fading"),
    WAITING("Waiting"),
    DONE("Done");
    
    String displayName;

    private CueState(String displayName) {
        this.displayName = displayName;
    }
    
    @Override
    public String toString() {
        return this.displayName;
    }
}
