package jcue.ui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import jcue.domain.AbstractCue;
import jcue.domain.CueList;
import jcue.domain.CueType;
import jcue.domain.audiocue.AudioCue;
import jcue.domain.fadecue.FadeCue;

/**
 *
 * @author Jaakko
 */
public class FadeCueUI extends AbstractCueUI  {
    
    private JLabel targetCueLabel;
    private JComboBox targetCueSelect;
    
    private JLabel durationLabel;
    private JFormattedTextField durationField;
    
    private CurvePanel curvePanel;
    
    private FadeCue cue;

    public FadeCueUI() {
        super();
        
        this.targetCueLabel = new JLabel("Target cue:");
        this.targetCueSelect = new JComboBox();
        this.targetCueSelect.addActionListener(this);
        
        this.durationLabel = new JLabel("Duration:");
        
        NumberFormat durationFormat = NumberFormat.getNumberInstance();
        durationFormat.setMaximumFractionDigits(2);
        durationFormat.setMinimumFractionDigits(2);
        this.durationField = new JFormattedTextField(durationFormat);
        this.durationField.setColumns(5);
        this.durationField.addPropertyChangeListener(this);
        
        this.curvePanel = new CurvePanel();
        
        addComponents();
    }
    
    private void addComponents() {
        this.add(this.targetCueLabel);
        this.add(this.targetCueSelect, "span, wrap");
        
        this.add(this.durationLabel);
        this.add(this.durationField, "wrap");
        
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
            ArrayList<AbstractCue> cues = CueList.getInstance().getCues(CueType.AUDIO);
            AbstractCue[] tmpArray = new AbstractCue[cues.size()];
            AbstractCue[] cueArray = cues.toArray(tmpArray);
            
            ComboBoxModel cbm = new DefaultComboBoxModel(cueArray);
            this.targetCueSelect.setModel(cbm);
            this.targetCueSelect.setSelectedItem(this.cue.getEnvelope().getTargetCue());
            
            this.durationField.setValue(this.cue.getDuration());
            
            this.curvePanel.setEnvelope(this.cue.getEnvelope());
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        super.actionPerformed(ae);
        
        Object source = ae.getSource();
        if (source == this.targetCueSelect) {
            JComboBox cb = (JComboBox) source;
            AudioCue cue = (AudioCue) cb.getSelectedItem();
            
            this.cue.getEnvelope().setTargetCue(cue);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        super.propertyChange(pce);
        
        Object source = pce.getSource();
        if (source == this.durationField) {
            Number value = (Number) this.durationField.getValue();
            
            if (this.cue != null) {
                this.cue.setDuration(value.doubleValue());
                this.curvePanel.repaint();
            }
        }
    }
}
