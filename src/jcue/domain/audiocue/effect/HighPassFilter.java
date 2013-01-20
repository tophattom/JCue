package jcue.domain.audiocue.effect;

import jcue.domain.audiocue.AudioStream;
import jouvieje.bass.enumerations.BASS_FX_BFX;
import jouvieje.bass.structures.BASS_BFX_BQF;

/**
 *
 * @author Jaakko
 */
public class HighPassFilter extends AbstractEffect {
    
    private BASS_BFX_BQF struct;
    
    public HighPassFilter(AudioStream stream) {
        super(BASS_FX_BFX.BASS_FX_BFX_BQF.asInt(), "High pass filter");
        
        this.struct = BASS_BFX_BQF.allocate();
        this.struct.setFilter(1);
        super.setEffectStruct(struct);
        
        this.params.put("cutoff freq", new EffectParameter("Cutoff freq", "Hz", 1, stream.getFreq() / 2, "double"));
    }

    @Override
    public void setParameter(String param, double value) {
        String lowParam = param.toLowerCase();
        
        if (params.containsKey(lowParam)) {
            EffectParameter paramObject = params.get(lowParam);
            
            paramObject.setValue(value);
            
            if (lowParam.equals("cutoff freq")) {
                this.struct.setCenter((float) paramObject.getValue());
            }
        }
        
        this.updateEffect();
    }

    @Override
    public void updateParameters() {
        this.struct.setFilter(1);
        this.params.get("cutoff freq").setValue(struct.getCenter());
    }
}
