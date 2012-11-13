package jcue.ui;

import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import jcue.domain.audiocue.effect.AbstractEffect;
import jcue.domain.audiocue.effect.EffectParameter;

/**
 *
 * @author Jaakko
 */
public class EffectPanel extends JPanel implements PropertyChangeListener {
    
    private AbstractEffect effect;
    
    private ArrayList<ParameterKnob> knobs;

    public EffectPanel(AbstractEffect effect) {
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.effect = effect;
        
        this.knobs = new ArrayList<ParameterKnob>();
        
        for (EffectParameter ep : effect.getParameters()) {
            ParameterKnob pk = new ParameterKnob(ep);
            pk.addPropertyChangeListener(this);
            this.add(pk);
            
            this.knobs.add(pk);
        }
    }

    public void setEffect(AbstractEffect effect) {
        this.effect = effect;
        
        this.removeAll();
        
        for (EffectParameter ep : this.effect.getParameters()) {
            ParameterKnob pk = new ParameterKnob(ep);
            this.add(pk);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        Object source = pce.getSource();
        
        if (source instanceof ParameterKnob) {
            ParameterKnob knob = (ParameterKnob) source;
            if (this.knobs.contains(knob)) {
                EffectParameter ep = knob.getParam();
                this.effect.setParameter(ep.getName().toLowerCase(), ep.getValue());
            }
        }
    }
}
