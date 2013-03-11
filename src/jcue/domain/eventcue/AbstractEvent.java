package jcue.domain.eventcue;

import jcue.domain.audiocue.AudioCue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Represents an event that can be applied to a cue.
 * 
 * @author Jaakko
 */
public abstract class AbstractEvent {
    
    public static final int TYPE_TRANSPORT = 1;
    public static final int TYPE_MUTE = 2;
    public static final int TYPE_LOOP = 3;
    public static final int TYPE_EFFECT = 4;
    
    protected AudioCue targetCue;
    
    private int type;

    public AbstractEvent(int type) {
        this.type = type;
    }
    
    public void setTargetCue(AudioCue targetCue) {
        this.targetCue = targetCue;
    }

    public AudioCue getTargetCue() {
        return targetCue;
    }

    public Element toElement(Document doc) {
        Element result = doc.createElement("event");

        //Type
        Element typeElem = doc.createElement("type");
        typeElem.appendChild(doc.createTextNode(Integer.toString(type)));
        result.appendChild(typeElem);

        //Target
        Element targetElem = doc.createElement("target");
        targetElem.appendChild(doc.createTextNode(targetCue.getName()));
        result.appendChild(targetElem);

        return result;
    }

    public abstract void perform();
}
