package jcue.domain.audiocue.effect;

import jouvieje.bass.enumerations.BASS_FX_BFX;
import jouvieje.bass.structures.BASS_BFX_ECHO2;

/**
 *
 * @author Jaakko
 */
public class EchoEffect extends AbstractEffect {

    private BASS_BFX_ECHO2 struct;

    public EchoEffect() {
        super(BASS_FX_BFX.BASS_FX_BFX_ECHO2.asInt());

        this.struct = BASS_BFX_ECHO2.allocate();
        super.setEffectStruct(struct);
        
        this.params.put("dry mix", new EffectParameter("Dry mix", "", -2, 2, "double"));
        this.params.put("wet mix", new EffectParameter("Wet mix", "", -2, 2, "double"));
        this.params.put(("feedback"), new EffectParameter("Feedback", "", -1, 1, "double"));
        this.params.put("delay", new EffectParameter("Delay", "s", 0, 6, "double"));
    }
    
    @Override
    public void setParameter(String param, double value) {
        String lowParam = param.toLowerCase();
        
        if (this.params.containsKey(lowParam)) {
            EffectParameter paramObject = this.params.get(lowParam);
            
            paramObject.setValue(value);
            
            if (lowParam.equals("dry mix")) {
                this.struct.setDryMix((float) paramObject.getValue());
            } else if (lowParam.equals("wet mix")) {
                this.struct.setWetMix((float) paramObject.getValue());
            } else if (lowParam.equals("feedback")) {
                this.struct.setFeedback((float) paramObject.getValue());
            } else if (lowParam.equals("delay")) {
                this.struct.setDelay((float) paramObject.getValue());
            }
        }
        
        this.updateEffect();
    }

    @Override
    public void updateParameters() {
        this.params.get("dry mix").setValue(this.struct.getDryMix());
        this.params.get("wet mix").setValue(this.struct.getWetMix());
        this.params.get("feedback").setValue(this.struct.getFeedback());
        this.params.get("delay").setValue(this.struct.getDelay());
    }
}
