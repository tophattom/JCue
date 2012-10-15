package jcue.domain.audiocue;

import jcue.domain.AbstractCue;
import jouvieje.bass.callbacks.SYNCPROC;
import jouvieje.bass.structures.HSYNC;
import jouvieje.bass.utils.Pointer;

/**
 *
 * @author Jaakko
 */
public class StopCallback implements SYNCPROC {
    
    private AbstractCue target;

    public StopCallback(AbstractCue target) {
        this.target = target;
    }

    @Override
    public void SYNCPROC(HSYNC arg0, int arg1, int arg2, Pointer arg3) {
        this.target.stop();
    }
}
