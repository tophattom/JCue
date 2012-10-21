package jcue.domain;

/**
 * Represents cue's start mode.
 * 
 * @author Jaakko
 */
public enum StartMode {
    MANUAL("Manual"),
    AFTER_START("After start of cue"),
    AFTER_END("After end of cue"),
    HOTKEY("Hotkey");
    
    String displayName;

    private StartMode(String displayName) {
        this.displayName = displayName;
    }
    
    @Override
    public String toString() {
        return this.displayName;
    }
}
