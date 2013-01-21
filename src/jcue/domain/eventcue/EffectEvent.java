package jcue.domain.eventcue;

import jcue.domain.audiocue.effect.AbstractEffect;
import jcue.domain.audiocue.effect.EffectRack;

/**
 *
 * @author Jaakko
 */
public class EffectEvent extends AbstractEvent {

    public static final int EFFECT_ON = 1;
    public static final int EFFECT_OFF = 2;
    public static final int TOGGLE_EFFECT = 3;
    
    public static final int MODE_COUNT = 3;
    
    private int mode;
    private AbstractEffect targetEffect;
    private EffectRack rack;
    
    public EffectEvent(int mode) {
        super(AbstractEvent.TYPE_EFFECT);
        
        this.mode = mode;
    }

    public EffectEvent() {
        this(EFFECT_OFF);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public AbstractEffect getTargetEffect() {
        return targetEffect;
    }

    public void setTargetEffect(AbstractEffect targetEffect) {
        this.targetEffect = targetEffect;
    }

    public void setRack(EffectRack rack) {
        this.rack = rack;
    }
    
    
    
    
    
    @Override
    public void perform() {
        if (this.mode == EFFECT_ON) {
            this.targetEffect.setActive(true);
        } else if (this.mode == EFFECT_OFF) {
            this.targetEffect.setActive(false);
        } else {
            this.targetEffect.setActive(!targetEffect.isActive());
        }
        
        rack.update();
    }

    @Override
    public String toString() {
        return "Effect event";
    }
    
    public static String getModeString(int mode) {
        if (mode == 1) {
            return "Effect on";
        } else if (mode == 2) {
            return "Effect off";
        } else {
            return "Toggle effect";
        }
    }
}
