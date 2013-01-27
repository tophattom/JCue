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
    
    public static CueType fromString(String string) {
        if (string.equals("Audio")) {
            return CueType.AUDIO;
        } else if (string.equals("Event")) {
            return CueType.EVENT;
        } else if (string.equals("Fade")) {
            return CueType.FADE;
        } else if (string.equals("Note")) {
            return CueType.NOTE;
        }
        
        return null;
    }
}
