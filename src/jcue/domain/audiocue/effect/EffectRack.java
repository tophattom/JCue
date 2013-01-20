package jcue.domain.audiocue.effect;

import java.util.ArrayList;
import java.util.Iterator;
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
        
        this.effects = new ArrayList<AbstractEffect>();
    }

    public void addEffect(AbstractEffect effect) {
        if (this.cue != null && this.cue.getAudio() != null) {
            ArrayList<HFX> handles = null;
            handles = this.cue.getAudio().addEffect(effect.getType(), 0);
            
            if (handles != null && !handles.isEmpty()) {
                effect.setHandles(handles);
                
                Bass.BASS_FXGetParameters(handles.get(0), effect.getEffectStruct());
                effect.updateParameters();
                
                effect.updateEffect();
            }
            
            this.effects.add(effect);
            
            this.update();
        }
    }
    
    private void addEffect(AbstractEffect effect, int priority) {
        if (this.cue != null) {
            ArrayList<HFX> handles = null;
            
            if (effect.isActive()) {
                handles = this.cue.getAudio().addEffect(effect.getType(), priority);
            }
            
            if (handles != null && !handles.isEmpty()) {
                effect.setHandles(handles);
                
                effect.updateEffect();
            }
            
            this.effects.add(effect);
        }
    }
    
    public void removeEffect(AbstractEffect effect) {
        if (this.effects.contains(effect)) {
            this.cue.getAudio().removeEffect(effect);
            
            this.effects.remove(effect);
        }
    }

    public ArrayList<AbstractEffect> getEffects() {
        return effects;
    }
    
    public void update() {
        ArrayList<AbstractEffect> tmpEffects = new ArrayList<AbstractEffect>(this.effects);
        
        for (AbstractEffect ae : tmpEffects) {
            removeEffect(ae);
        }
        
        int size = tmpEffects.size();
        int i = 0;
        
        for (AbstractEffect ae : tmpEffects) {
            this.addEffect(ae, size - i);
            
            i++;
        }
    }
    
    public void clear() {
        Iterator<AbstractEffect> it = this.effects.iterator();
        while (it.hasNext()) {
            AbstractEffect ae = it.next();
            
            this.cue.getAudio().removeEffect(ae);
        }
        
        this.effects.clear();
    }
    
    public int size() {
        return this.effects.size();
    }
}
