package jcue.domain.eventcue;

/**
 *
 * @author Jaakko
 */
public class LoopEvent extends AbstractEvent {

    public LoopEvent() {
        super(AbstractEvent.TYPE_LOOP);
    }
    
    @Override
    public void perform() {
        throw new UnsupportedOperationException("Looping not implemented");
    }

    @Override
    public String toString() {
        return "Release loop";
    }
}
