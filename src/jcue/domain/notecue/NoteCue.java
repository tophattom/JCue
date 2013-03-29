package jcue.domain.notecue;

import jcue.domain.AbstractCue;
import jcue.domain.CueType;

/**
 * @author Jaakko
 */
public class NoteCue extends AbstractCue {


    public NoteCue(String name, String description) {
        super(name, description, CueType.NOTE);
    }

    @Override
    public void pause() {

    }
}
