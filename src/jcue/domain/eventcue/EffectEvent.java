package jcue.domain.eventcue;

/**
 *
 * @author Jaakko
 */
public class EffectEvent extends AbstractEvent {

    public static final int EFFECT_ON = 1;
    public static final int EFFECT_OFF = 2;
    
    private int type;
    //private SoundEffect targetEffect;

    public EffectEvent(int type) {
        this.type = type;
    }

    public EffectEvent() {
        this(EFFECT_OFF);
    }

    @Override
    public void perform() {
        if (this.type == EFFECT_ON) {
            //this.targetEffect.setActive(true);
        } else {
            //this.targetEffect.setActive(false);
        }
    }
}
