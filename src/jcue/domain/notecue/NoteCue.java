package jcue.domain.notecue;

import jcue.domain.AbstractCue;
import jcue.domain.CueType;
import jcue.domain.ProjectFile;
import jcue.domain.StartMode;
import org.w3c.dom.Element;

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

    public static NoteCue fromElement(Element elem) {
        //General cue stuff
        String name = ProjectFile.getTagValue("name", elem);
        String description = ProjectFile.getTagValue("description", elem);

        return new NoteCue(name, description);
    }
}
