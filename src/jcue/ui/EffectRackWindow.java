package jcue.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import jcue.domain.audiocue.AudioCue;
import jcue.domain.audiocue.effect.AbstractEffect;
import jcue.domain.audiocue.effect.EchoEffect;
import jcue.domain.audiocue.effect.EffectRack;
import jcue.domain.audiocue.effect.HighPassFilter;
import jcue.domain.audiocue.effect.LowPassFilter;
import jcue.domain.audiocue.effect.ReverbEffect;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jaakko
 */
public class EffectRackWindow extends JFrame implements ActionListener, PropertyChangeListener {

    private AudioCue cue;
    
    private JComboBox selectEffect;
    private JButton addButton;
    
    
    private static final ImageIcon addIcon = new ImageIcon("images/add_small.png");

    public EffectRackWindow() throws HeadlessException {
        super("Effect rack");
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 500));
        
        String[] effects = {"Echo", "High pass filter", "Low pass filter", "Reverb"};
        this.selectEffect = new JComboBox(effects);
        
        this.addButton = new JButton(addIcon);
        this.addButton.addActionListener(this);
        
        update();
        
        this.pack();
        this.setVisible(false);
    }
    
    private void update() {
        Container c = this.getContentPane();
        
        c.removeAll();
        c.setLayout(new MigLayout("fillx"));
        
        c.add(this.selectEffect, "split 2");
        c.add(this.addButton, "wrap");
        
        if (this.cue != null) {
            EffectRack effectRack = this.cue.getEffectRack();
            for (AbstractEffect ae : effectRack.getEffects()) {
                EffectPanel ep = new EffectPanel(ae);
                ep.addPropertyChangeListener(this);
                c.add(ep, "growx, span, wrap");
            }
        }
        
        c.validate();
        c.repaint();
    }
    
    public void setCue(AudioCue cue) {
        this.cue = cue;
        
        if (cue != null) {
            this.setTitle("Effect rack for " + this.cue.getName());
            
            update();
        }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        
        if (source == this.addButton && cue.getAudio() != null) {
            String effectName = (String) this.selectEffect.getSelectedItem();
            AbstractEffect effect = null;
            
            if (effectName.equals("Echo")) {
                effect = new EchoEffect();
            } else if (effectName.equals("High pass filter")) {
                effect = new HighPassFilter(cue.getAudio());
            } else if (effectName.equals("Low pass filter")) {
                effect = new LowPassFilter(cue.getAudio());
            } else if (effectName.equals("Reverb")) {
                effect = new ReverbEffect();
            }
            
            this.cue.getEffectRack().addEffect(effect);
            
            this.update();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        Object source = pce.getSource();
        
        if (pce.getPropertyName().equals("removeEffect")) {
            AbstractEffect ae = (AbstractEffect) pce.getNewValue();
            this.cue.getEffectRack().removeEffect(ae);
            
            this.update();
        } else if (pce.getPropertyName().equals("activeEffect")) {
            AbstractEffect ae = (AbstractEffect) pce.getNewValue();
            this.cue.getEffectRack().update();
            
            this.update();
        }
    }
}
