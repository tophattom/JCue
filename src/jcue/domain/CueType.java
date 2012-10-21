package jcue.domain;

/**
 * Represents a type of cue.
 * 
 * @author Jaakko
 */
public enum CueType {

    AUDIO("Audio"),
    EVENT("Event"),
    FADE("Fade"),
    NOTE("Note");
    
    private String displayName;

    private CueType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
