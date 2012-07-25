package jcue.ui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import jcue.domain.AudioCue;

/**
 *
 * @author Jaakko
 */
public class AudioCueUIListener implements ActionListener, DocumentListener {

    private JPanel panel;
    private AudioCue cue;

    public AudioCueUIListener() {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        
        if (command.equals("loadAudio")) {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(null);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                this.cue.loadAudio(file.getAbsolutePath());
                this.cue.updateUI();
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCue(AudioCue cue) {
        this.cue = cue;
    }
}
