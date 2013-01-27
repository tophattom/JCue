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
    
    public static StartMode fromString(String string) {
        if (string.equals("Manual")) {
            return StartMode.MANUAL;
        } else if (string.equals("After start of cue")) {
            return StartMode.AFTER_START;
        } else if (string.equals("After end of cue")) {
            return StartMode.AFTER_END;
        } else if (string.equals("Hotkey")) {
            return StartMode.HOTKEY;
        }
        
        return null;
    }
}
