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
    
    /**
     * Initializes BASS and loads necessary plugins.
     * 
     * @return Returns true if everything went smoothly
     */
    private static boolean initBass() {
        setLibraryPath();
        
        //Load the libraries
        BassInit.loadLibraries();
        
        //Initialize default sound device with 44100 sample rate
        boolean BASS_Init = Bass.BASS_Init(-1, 44100, 0, null, null);
        //TODO: initialize all devices?
        
        if (!BASS_Init) {
            System.out.println("Error initializing BASS: " + Bass.BASS_ErrorGetCode());
            return false;
        }
        
        System.out.println("BASS initialized using default output device!");
        
        return true;
    }
    
    /**
     * Sets the library path based on the operating system.
     * 
     * @return Returns true if path succesfully set
     */
    private static boolean setLibraryPath() {
        //Get OS name and architecture
        String os = System.getProperty("os.name").toLowerCase();
        String arch = System.getProperty("os.arch");
        String libPath = null;
        
        if (os.contains("windows")) {       //Is Windows
            if (arch.equals("x86")) {
                libPath = "lib/win32";
            } else if (arch.equals("x64")) {
                libPath = "lib/win64";
            }
        } else if (os.contains("linux")) {  //Is Linux
            if (arch.equals("x86")) {
                libPath = "lib/linux32";
            } else if (arch.equals("x64")) {
                libPath = "lib/linux64";
            }
        } else if (os.contains("mac")) {    //Is Mac
            libPath = "lib/mac";
        }
        
        //Set the BASS library location according to the os type
        if (libPath != null) {
            System.setProperty("java.library.path", libPath);
            return true;
        }
        
        return false;
    }
}
