package jcue.ui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Jaakko
 */
public class AudioFileFilter extends FileFilter {
    
    private static final String[] supportedTypes = {
        "mp3", "mp2", "mp1", "ogg", "wav", "aiff", "aif", "aifc", "flac"
    };

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        
        String extension = getExtension(file);
        
        if (extension != null) {
            for (int i = 0; i < supportedTypes.length; i++) {
                if (extension.equals(supportedTypes[i])) {
                    return true;
                }
            }
        }
        
        return false;
    }

    @Override
    public String getDescription() {
        return "Audio files";
    }
    
    private String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf(".");
        
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }

        return ext;
    }
}
