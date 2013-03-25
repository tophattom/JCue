package jcue.domain;

import javax.swing.*;
import java.io.File;

/**
 * @author Jaakko
 */
public class FileUtils {

    private static String previousFolder = "";

    public static File chooseSaveLocation() {
        JFileChooser chooser = new JFileChooser(previousFolder);
        int result = chooser.showSaveDialog(null);

        File file = null;

        if (result == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }

        updatePreviousFolder(file);

        return file;
    }

    public static File chooseFile() {
        JFileChooser chooser = new JFileChooser(previousFolder);
        int result = chooser.showOpenDialog(null);

        File file = null;

        if (result == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }

        updatePreviousFolder(file);

        return file;
    }

    private static void updatePreviousFolder(File file) {
        if (file != null) {
            String parent = file.getParent();

            if (parent != null) {
                previousFolder = parent;
            }
        }
    }
}
