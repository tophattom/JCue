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
    
    private int type;

    public TransportEvent(int type) {
        this.type = type;
    }

    public TransportEvent() {
        this(STOP);
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void perform() {
        if (this.type == STOP) {
            super.targetCue.stop();
        } else if (this.type == PAUSE) {
            super.targetCue.pause();
        } else if (this.type == START) {
            super.targetCue.start(true);
        }
    }

    @Override
    public String toString() {
        return "Transport event";
    }
}
