package jcue.domain.audiocue.effect;

import jcue.domain.audiocue.AudioStream;
import jouvieje.bass.enumerations.BASS_FX_BFX;
import jouvieje.bass.structures.BASS_BFX_LPF;

/**
 *
 * @author oppilas
 */
public class LowPassFilter extends AbstractEffect {
    
    private BASS_BFX_LPF struct;
    
    public LowPassFilter(AudioStream stream) {
        super(BASS_FX_BFX.BASS_FX_BFX_LPF.asInt(), "Low pass filter");
        
        this.struct = BASS_BFX_LPF.allocate();
        super.setEffectStruct(struct);
        
        this.params.put("resonance", new EffectParameter("Resonance", "", 0.01, 10, "double"));
        this.params.put("cutoff freq", new EffectParameter("Cutoff freq", "Hz", 1, stream.getFreq() / 2, "double"));
    }

    @Override
    public void setParameter(String param, double value) {
        String lowParam = param.toLowerCase();
        
        if (this.params.containsKey(lowParam)) {
            EffectParameter paramObject = this.params.get(lowParam);
            
            paramObject.setValue(value);
            
            if (lowParam.equals("resonance")) {
                this.struct.setResonance((float) paramObject.getValue());
            } else if (lowParam.equals("cutoff freq")) {
                this.struct.setCutOffFreq((float) paramObject.getValue());
            }
        }
        
        this.updateEffect();
    }

    @Override
    public void updateParameters() {
        this.params.get("resonance").setValue(this.struct.getResonance());
        this.params.get("cutoff freq").setValue(this.struct.getCutOffFreq());
    }
    
}
