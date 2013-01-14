package jcue.domain.audiocue;

import jcue.domain.fadecue.ParameterEnvelope;
import jouvieje.bass.callbacks.SYNCPROC;
import jouvieje.bass.structures.HSYNC;
import jouvieje.bass.utils.Pointer;

/**
 *
 * @author Jaakko
 */
public class FadeOutCallback implements SYNCPROC {
    
    private ParameterEnvelope fadeCurve;

    public FadeOutCallback(ParameterEnvelope fadeCurve) {
        this.fadeCurve = fadeCurve;
    }
    
    @Override
    public void SYNCPROC(HSYNC arg0, int arg1, int arg2, Pointer arg3) {
        fadeCurve.start();
    }
}
