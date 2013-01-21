package jcue.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import jcue.domain.audiocue.effect.EffectParameter;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jaakko
 */
public class ParameterKnob extends JPanel implements PropertyChangeListener {

    private Knob knob;
    
    private JFormattedTextField valueField;
    private JLabel nameLabel, unitLabel;
    
    private EffectParameter param;

    public ParameterKnob(EffectParameter param) {
        super(new MigLayout());
        
        this.param = param;
        
        this.knob = new Knob(param.getMinValue(), param.getMaxValue(), param.getValue());
        this.knob.addPropertyChangeListener(this);
        
        NumberFormat valueFormat = NumberFormat.getInstance();
        valueFormat.setMinimumFractionDigits(2);
        valueFormat.setMaximumFractionDigits(2);
        
        this.valueField = new JFormattedTextField(valueFormat);
        this.valueField.setColumns(6);
        this.valueField.setValue(param.getValue());
        this.valueField.addPropertyChangeListener(this);
        
        this.unitLabel = new JLabel(param.getUnit());
        this.nameLabel = new JLabel(param.getName());
        
        addComponents();
    }
    
    private void addComponents() {
        this.add(this.nameLabel, "align center, wrap");
        this.add(this.knob, "align center, wrap");
        this.add(this.valueField, "split 2, align center");
        this.add(this.unitLabel);
    }
    
    public void update() {
        this.knob.setValue(this.param.getValue());
        this.valueField.setValue(this.param.getValue());
    }

    public EffectParameter getParam() {
        return param;
    }
   
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        Object source = pce.getSource();
        
        if (source == this.knob) {
            if (pce.getPropertyName().equals(("value"))) {
                this.param.setValue((Double) this.knob.getValue());
                this.valueField.setValue(this.knob.getValue());
            }
        } else if (source == this.valueField) {
            Number value = (Number) this.valueField.getValue();
            
            this.param.setValue(value.doubleValue());
            this.knob.setValue(value.doubleValue());
        }
        
        this.firePropertyChange("effectProperty", null, null);
    }
    
}
