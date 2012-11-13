package jcue.ui;

import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import jcue.domain.audiocue.AudioCue;
import jcue.domain.audiocue.effect.AbstractEffect;
import jcue.domain.audiocue.effect.EffectRack;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jaakko
 */
public class EffectRackWindow extends JFrame implements ActionListener {

    private AudioCue cue;

    public EffectRackWindow() throws HeadlessException {
        super("Effect rack");
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        
        this.pack();
        this.setVisible(false);
    }
    
    public void setCue(AudioCue cue) {
        this.cue = cue;
        
        if (cue != null) {
            this.setTitle("Effect rack for " + this.cue.getName());
            
            Container container = this.getContentPane();
            container.removeAll();
            container.setLayout(new MigLayout());
            
            EffectRack effectRack = cue.getEffectRack();
            for (AbstractEffect ae : effectRack.getEffects()) {
                EffectPanel ep = new EffectPanel(ae);
                container.add(ep, "wrap");
            }
        }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
