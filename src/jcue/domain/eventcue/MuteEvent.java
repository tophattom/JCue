package jcue.domain.eventcue;

import jcue.domain.audiocue.AudioStream;

/**
 * Mutes or unmutes an audio cue's output
 *
 * @author Jaakko
 */
public class MuteEvent extends AbstractEvent {

    public static final int MUTE = 1;
    public static final int UNMUTE = 2;
    public static final int TOGGLE_MUTE = 3;
    
    public static final int MODE_COUNT = 3;
    
    private int mode;

    public MuteEvent(int mode) {
        super(AbstractEvent.TYPE_MUTE);
        
        this.mode = mode;
    }

    public MuteEvent() {
        this(MUTE);
    }

    public void setType(int type) {
        this.mode = type;
    }

    @Override
    public void perform() {
        if (this.mode == MUTE) {
            super.targetCue.getAudio().muteOutput(super.targetOutput);
        } else if (this.mode == UNMUTE) {
            super.targetCue.getAudio().unmuteOutput(super.targetOutput);
        } else if (this.mode == TOGGLE_MUTE) {
            AudioStream audio = super.targetCue.getAudio();

            if (audio.isMuted(super.targetOutput)) {
                audio.unmuteOutput(super.targetOutput);
            } else {
                audio.muteOutput(super.targetOutput);
            }
        }
    }

    @Override
    public String toString() {
        return "Mute event";
    }
    
    public static String getModeString(int mode) {
        if (mode == MUTE) {
            return "Mute";
        } else if (mode == UNMUTE) {
            return "Unmute";
        } else if (mode == TOGGLE_MUTE) {
            return "Toggle state";
        } else {
            return "Unknown mode";
        }
    }
}
