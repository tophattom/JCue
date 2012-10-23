package jcue.domain.audiocue.effect;

import java.util.ArrayList;
import jcue.domain.audiocue.AudioCue;
import jouvieje.bass.Bass;
import jouvieje.bass.structures.HFX;

/**
 *
 * @author Jaakko
 */
public class EffectRack {
    
    private AudioCue cue;
    
    private ArrayList<AbstractEffect> effects;

    public EffectRack(AudioCue cue) {
        this.cue = cue;
    }

    public void addEffect(AbstractEffect effect) {
        if (this.cue != null) {
            ArrayList<HFX> handles = this.cue.getAudio().addEffect(effect.getType(), this.effects.size());
            effect.setHandles(handles);
            this.effects.add(effect);
        }
    }
}
