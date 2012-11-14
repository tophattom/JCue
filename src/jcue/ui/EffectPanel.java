package jcue.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import jcue.domain.audiocue.effect.AbstractEffect;
import jcue.domain.audiocue.effect.EffectParameter;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jaakko
 */
public class EffectPanel extends JPanel implements PropertyChangeListener {
    
    private AbstractEffect effect;
    
    private JCheckBox active;
    private ArrayList<ParameterKnob> knobs;

    public EffectPanel(AbstractEffect effect) {
        super(new MigLayout("fillx"));
        this.setBorder(BorderFactory.createTitledBorder("Effect"));
        
        this.effect = effect;
        
        this.active = new JCheckBox("Active");
        this.knobs = new ArrayList<ParameterKnob>();
        
        addComponents();
    }
    
    private void addComponents() {
        this.add(this.active);
        
        for (EffectParameter ep : this.effect.getParameters()) {
            ParameterKnob pk = new ParameterKnob(ep);
            pk.addPropertyChangeListener(this);
            this.add(pk);
            
            this.knobs.add(pk);
        }
    }

    public void setEffect(AbstractEffect effect) {
        this.effect = effect;
        
        this.removeAll();
        this.knobs.clear();
        
        addComponents();
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
