package jcue.domain;

import jouvieje.bass.Bass;
import jouvieje.bass.callbacks.SYNCPROC;
import jouvieje.bass.structures.HSYNC;
import jouvieje.bass.utils.Pointer;

/**
 *
 * @author Jaakko
 */
public class StopCallback implements SYNCPROC {

    @Override
    public void SYNCPROC(HSYNC arg0, int arg1, int arg2, Pointer arg3) {
        Bass.BASS_ChannelStop(arg1);
    }
}
