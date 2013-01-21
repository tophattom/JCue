package jcue.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import jcue.domain.audiocue.effect.AbstractEffect;
import jcue.domain.audiocue.effect.EffectParameter;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jaakko
 */
public class EffectPanel extends JPanel implements PropertyChangeListener, ActionListener {
    
    private AbstractEffect effect;
    
    private JCheckBox active;
    private ArrayList<ParameterKnob> knobs;
    
    private JButton removeButton;
    
    private static final ImageIcon removeIcon = new ImageIcon("images/remove_small.png");

    public EffectPanel(AbstractEffect effect) {
        super(new MigLayout("fillx"));
        this.setBorder(BorderFactory.createTitledBorder(effect.getName()));
        
        this.effect = effect;
        
        this.active = new JCheckBox("Active");
        this.active.setSelected(effect.isActive());
        this.active.addActionListener(this);
        
        this.knobs = new ArrayList<ParameterKnob>();
        
        this.removeButton = new JButton(removeIcon);
        this.removeButton.addActionListener(this);
        
        addComponents();
    }
    
    private void addComponents() {
        for (EffectParameter ep : this.effect.getParameters()) {
            ParameterKnob pk = new ParameterKnob(ep);
            pk.addPropertyChangeListener(this);
            this.add(pk);
            
            this.knobs.add(pk);
        }
        
        this.add(this.active, "newline, split 2, span");
        this.add(this.removeButton);
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

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        
        if (source == this.removeButton) {
            this.firePropertyChange("removeEffect", null, this.effect);
        } else if (source == this.active) {
            this.effect.setActive(this.active.isSelected());
            this.firePropertyChange("activeEffect", null, this.effect);
        }
    }
}
