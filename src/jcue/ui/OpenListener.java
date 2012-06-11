/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcue.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import jouvieje.bass.Bass;
import jouvieje.bass.structures.HSTREAM;

/**
 *
 * @author Jaakko
 */
public class OpenListener implements ActionListener {
    
    JFrame frame;

    public OpenListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JFileChooser chooser = new JFileChooser();

        int result = chooser.showOpenDialog(this.frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();

            HSTREAM stream = Bass.BASS_StreamCreateFile(false, path, 0, 0, 0);
            if (stream != null) {
                Bass.BASS_ChannelPlay(stream.asInt(), true);
            } else {
                System.out.println(Bass.BASS_ErrorGetCode());
            }
        }
    }
    
    
}
