package jcue.ui;

import jcue.domain.AbstractCue;
import jcue.domain.fadecue.FadeCue;

/**
 *
 * @author Jaakko
 */
public class FadeCueUI extends AbstractCueUI {
    
    private CurvePanel curvePanel;
    
    private FadeCue cue;

    public FadeCueUI() {
        super();
        
        this.curvePanel = new CurvePanel();
        
        addComponents();
    }
    
    private void addComponents() {
        this.add(this.curvePanel, "grow, span, wrap");
    }

    @Override
    protected void setCurrentCue(AbstractCue cue) {
        super.setCurrentCue(cue);
        
        this.cue = (FadeCue) cue;
        this.update();
    }

    @Override
    protected void update() {
        super.update();
        
        if (this.cue != null) {
            this.curvePanel.setEnvelope(this.cue.getEnvelope());
        }
    }
    
    
    
    
}
