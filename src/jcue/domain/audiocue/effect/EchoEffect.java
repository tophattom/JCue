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
    }

    @Override
    public String[] getParameters() {
        String[] result = {"Dry mix", "Wet mix", "Feedback", "Delay"};
        return result;
    }

    @Override
    public double getParameter(String param) {
        String lowParam = param.toLowerCase();

        if (lowParam.equals("dry mix")) {
            return this.struct.getDryMix();
        } else if (lowParam.equals("wet mix")) {
            return this.struct.getWetMix();
        } else if (lowParam.equals("feedback")) {
            return this.struct.getFeedback();
        } else if (lowParam.contains("delay")) {
            return this.struct.getDelay();
        }

        return 0;
    }

    @Override
    public void setParameter(String param, double value) {
        String lowParam = param.toLowerCase();

        if (lowParam.equals("dry mix")) {
            this.struct.setDryMix((float) Math.max(-2, Math.min(2, value)));
        } else if (lowParam.equals("wet mix")) {
            this.struct.setWetMix((float) Math.max(-2, Math.min(2, value)));
        } else if (lowParam.equals("feedback")) {
            this.struct.setFeedback((float) Math.max(-1, Math.min(1, value)));
        } else if (lowParam.contains("delay")) {
            this.struct.setDelay((float) Math.max(0, Math.min(6, value)));
        }
    }
}
