package jcue.domain.fadecue;

import jcue.domain.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jaakko
 */
public class FadeCue extends AbstractCue {
    
    private ParameterEnvelope envelope;

    public FadeCue(String name, String description) {
        super(name, description, CueType.FADE);
        
        this.envelope = new ParameterEnvelope();
    }

    
    @Override
    public void start(boolean delay) {
        super.start(delay);
        
        if (!delay ||super.getStartDelay() == 0) {
            this.envelope.start();
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void stop() {
        super.stop();
        
        this.envelope.stop();
    }

    public ParameterEnvelope getEnvelope() {
        return envelope;
    }
    
    public double getDuration() {
        return this.envelope.getDuration();
    }
    
    public void setDuration(double duration) {
        this.envelope.setDuration(duration);
    }

    @Override
    public Element toElement(Document doc) {
        Element result = super.toElement(doc);

        //Envelope
        result.appendChild(envelope.toElement(doc));

        return result;
    }

    public static FadeCue fromElement(Element elem) {
        //General cue stuff
        String name = ProjectFile.getTagValue("name", elem);
        String description = ProjectFile.getTagValue("description", elem);
        StartMode startMode = StartMode.fromString(ProjectFile.getTagValue("startmode", elem));
        double delay = Double.parseDouble(ProjectFile.getTagValue("delay", elem));

        String parentName = ProjectFile.getTagValue("parentcue", elem);
        AbstractCue parentCue = CueList.getInstance().getCue(parentName);

        FadeCue result = new FadeCue(name, description);
        result.setStartMode(startMode);
        result.setStartDelay(delay);

        if (parentCue != null) {
            result.setParentCue(parentCue);
        } else {
            ProjectFile.addToParentQueue(result, parentName);
        }

        //Envelope
        Element envElem = (Element) elem.getElementsByTagName("envelope").item(0);
        result.envelope.fromElement(envElem);


        return result;
    }
}
