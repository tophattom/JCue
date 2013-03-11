package jcue.domain.eventcue;

import jcue.domain.SoundDevice;
import jcue.domain.audiocue.AudioStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
    
    private SoundDevice targetOutput;
    private int mode;

    public MuteEvent(int mode) {
        super(AbstractEvent.TYPE_MUTE);
        
        this.mode = mode;
    }

    public MuteEvent() {
        this(MUTE);
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public void setTargetOutput(SoundDevice targetOutput) {
        this.targetOutput = targetOutput;
    }

    public SoundDevice getTargetOutput() {
        return targetOutput;
    }

    @Override
    public void perform() {
        if (this.mode == MUTE) {
            super.targetCue.getAudio().muteOutput(this.targetOutput);
        } else if (this.mode == UNMUTE) {
            super.targetCue.getAudio().unmuteOutput(this.targetOutput);
        } else if (this.mode == TOGGLE_MUTE) {
            AudioStream audio = super.targetCue.getAudio();

            if (audio.isMuted(this.targetOutput)) {
                audio.unmuteOutput(this.targetOutput);
            } else {
                audio.muteOutput(this.targetOutput);
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

    @Override
    public Element toElement(Document doc) {
        Element result = super.toElement(doc);

        //Mode
        Element modeElem = doc.createElement("mode");
        modeElem.appendChild(doc.createTextNode(Integer.toString(mode)));
        result.appendChild(modeElem);

        //Target output
        Element targetElem = doc.createElement("target");
        targetElem.appendChild(doc.createTextNode(Integer.toString(targetOutput.getId())));
        result.appendChild(targetElem);

        return result;
    }
}
