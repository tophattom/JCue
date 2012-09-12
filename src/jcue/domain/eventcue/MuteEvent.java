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
    
    private int type;

    public MuteEvent(int type) {
        this.type = type;
    }

    public MuteEvent() {
        this(MUTE);
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void perform() {
        if (this.type == MUTE) {
            super.targetCue.getAudio().muteOutput(super.targetOutput);
        } else if (this.type == UNMUTE) {
            super.targetCue.getAudio().unmuteOutput(super.targetOutput);
        } else if (this.type == TOGGLE_MUTE) {
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
        if (this.type == MUTE) {
            return "Mute";
        } else if (this.type == UNMUTE) {
            return "Unmute";
        } else {
            return "Toggle muting";
        }
    }
}
