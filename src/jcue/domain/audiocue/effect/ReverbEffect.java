package jcue.domain.audiocue.effect;

import jouvieje.bass.enumerations.BASS_FX_BFX;
import jouvieje.bass.structures.BASS_BFX_REVERB;

/**
 *
 * @author Jaakko
 */
public class ReverbEffect extends AbstractEffect {

    private BASS_BFX_REVERB struct;

    public ReverbEffect() {
        super(BASS_FX_BFX.BASS_FX_BFX_REVERB.asInt(), "Reverb");

        this.struct = BASS_BFX_REVERB.allocate();
        super.setEffectStruct(struct);
        
        this.params.put("level", new EffectParameter("Level", "", 0, 0.9, "double"));
        this.params.put("delay", new EffectParameter("Delay", "ms", 1200, 10000, "int"));
    }

    @Override
    public void setParameter(String param, double value) {
        String lowParam = param.toLowerCase();
        
        if (this.params.containsKey(lowParam)) {
            EffectParameter paramObject = this.params.get(lowParam);
            
            paramObject.setValue(value);
        
            if (param.toLowerCase().equals("level")) {
                this.struct.setLevel((float) paramObject.getValue());
            } else if (param.toLowerCase().equals("delay")) {
                this.struct.setDelay((int) paramObject.getValue());
            }
        }

        this.updateEffect();
    }

    @Override
    public void updateParameters() {
        this.params.get("level").setValue(this.struct.getLevel());
        this.params.get("delay").setValue(this.struct.getDelay());
    }
}
