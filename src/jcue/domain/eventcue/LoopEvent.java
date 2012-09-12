package jcue.domain.eventcue;

/**
 *
 * @author Jaakko
 */
public class LoopEvent extends AbstractEvent {

    @Override
    public void perform() {
        throw new UnsupportedOperationException("Looping not implemented");
    }

    @Override
    public String toString() {
        return "Release loop";
    }
    
    
}
