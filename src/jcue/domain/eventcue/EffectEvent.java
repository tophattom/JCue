package jcue.domain.eventcue;

import jcue.domain.CueList;
import jcue.domain.ProjectFile;
import jcue.domain.audiocue.AudioCue;
import jcue.domain.audiocue.effect.AbstractEffect;
import jcue.domain.audiocue.effect.EffectRack;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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


    @Override
    public void setTargetCue(AudioCue targetCue) {
        super.setTargetCue(targetCue);
        this.rack = targetCue.getEffectRack();
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

    @Override
    public Element toElement(Document doc) {
        Element result = super.toElement(doc);

        //Mode
        Element modeElem = doc.createElement("mode");
        modeElem.appendChild(doc.createTextNode(Integer.toString(mode)));
        result.appendChild(modeElem);

        //Target effect
        Element targetElem = doc.createElement("targeteffect");
        targetElem.appendChild(doc.createTextNode(targetEffect.getName()));
        result.appendChild(targetElem);

        return result;
    }

    public static EffectEvent fromElement(Element elem) {
        int mode = Integer.parseInt(ProjectFile.getTagValue("mode", elem));
        String targetName = ProjectFile.getTagValue("target", elem);
        AudioCue targetCue = (AudioCue) CueList.getInstance().getCue(targetName);

        String effectName = ProjectFile.getTagValue("targeteffect", elem);

        EffectEvent result = new EffectEvent(mode);

        if (targetCue != null) {
            result.setTargetCue(targetCue);
        } else {
            ProjectFile.addToTargetQueue(result, targetName);
        }

        ProjectFile.addToTargetQueue(result, "effect:" + effectName);

        return result;
    }
}
