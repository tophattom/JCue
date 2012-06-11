/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcue;

import javax.swing.SwingUtilities;
import jcue.ui.MainWindow;
import jouvieje.bass.Bass;
import jouvieje.bass.BassInit;

/**
 *
 * @author Jaakko
 */
public class JCue {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BassInit.loadLibraries();
        boolean BASS_Init = Bass.BASS_Init(-1, 44100, 0, null, null);
        
        if (BASS_Init) {
            SwingUtilities.invokeLater(new MainWindow());
        } else {
            System.out.println("BASS error: " + Bass.BASS_ErrorGetCode());
        }
    }
}
