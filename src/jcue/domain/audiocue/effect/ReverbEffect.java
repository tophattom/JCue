package jcue.domain.audiocue.effect;

import jouvieje.bass.structures.BASS_BFX_REVERB;

/**
 *
 * @author Jaakko
 */
public class ReverbEffect extends AbstractEffect {

    private BASS_BFX_REVERB struct;
    
    public ReverbEffect() {
        this.struct = BASS_BFX_REVERB.allocate();
        super.setEffectStruct(struct);
    }
    
    @Override
    public String[] getParameters() {
        String[] result = {"Level", "Delay"};
        return result;
    }
    
    @Override
    public double getParameter(String param) {
        if (param.toLowerCase().equals("level")) {
            return this.struct.getLevel();
        } else if (param.toLowerCase().equals("delay")) {
            return this.struct.getDelay();
        }
        
        return 0;
    }

    @Override
    public void setParameter(String param, double value) {
        if (param.toLowerCase().equals("level")) {
            this.struct.setLevel((float) Math.max(0, value));
        } else if (param.toLowerCase().equals("delay")) {
            this.struct.setDelay((int) Math.max(1200, Math.min(10000, value)));
        }
        
        this.updateEffect();
    }
    
}
