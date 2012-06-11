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
        if (initBass()) {
            SwingUtilities.invokeLater(new MainWindow());
        }
    }
    
    private static boolean initBass() {
        BassInit.loadLibraries();
        boolean BASS_Init = Bass.BASS_Init(-1, 44100, 0, null, null);
        //TODO: initialize all devices?
        
        if (!BASS_Init) {
            System.out.println("Error initializing BASS: " + Bass.BASS_ErrorGetCode());
            return false;
        }
        
        System.out.println("BASS initialized using default output device!");
        
        return true;
    }
}
