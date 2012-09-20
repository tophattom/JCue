package jcue.domain.eventcue;

/**
 * Stops, pauses or starts an audio cue
 *
 * @author Jaakko
 */
public class TransportEvent extends AbstractEvent {

    public static final int STOP = 1;
    public static final int PAUSE = 2;
    public static final int START = 3;
    
    public static final int MODE_COUNT = 3;
    
    private int mode;

    public TransportEvent(int mode) {
        super(AbstractEvent.TYPE_TRANSPORT);
        
        this.mode = mode;
    }

    public TransportEvent() {
        this(STOP);
    }

    public void setType(int type) {
        this.mode = type;
    }

    @Override
    public void perform() {
        if (this.mode == STOP) {
            super.targetCue.stop();
        } else if (this.mode == PAUSE) {
            super.targetCue.pause();
        } else if (this.mode == START) {
            super.targetCue.start(true);
        }
    }

    @Override
    public String toString() {
        return "Transport event";
    }
    
    public static String getModeString(int mode) {
        if (mode == STOP) {
            return "Stop";
        } else if (mode == PAUSE) {
            return "Pause";
        } else if (mode == START) {
            return "Start";
        } else {
            return "Unknown mode";
        }
    }
}
