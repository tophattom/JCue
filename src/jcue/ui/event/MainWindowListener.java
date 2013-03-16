package jcue.ui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import jcue.domain.CueList;
import jcue.domain.CuePlayer;
import jcue.domain.ProjectFile;
import jcue.ui.MainWindow;

import javax.swing.*;

/**
 *
 * @author oppilas
 */
public class MainWindowListener implements ActionListener {
    
    private MainWindow mainWindow;
    private CuePlayer player;

    public MainWindowListener(MainWindow mainWindow, CuePlayer player) {
        this.mainWindow = mainWindow;
        this.player = player;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();

        //Buttons
        if (command.equals("editor")) {
            player.stopAll();
            player.stop();

            this.mainWindow.showEditorWindow();
        } else if (command.equals("play")) {
            CuePlayer player = this.mainWindow.getPlayer();
            
            if (!player.isRunning()) {
                player.start();
            }
            
            player.startNext();
            
            this.mainWindow.setSelectedRow(player.getCurrentIndex());
        } else if (command.equals("stop")) {
            this.mainWindow.getPlayer().stopAll();
        }

        //Menus
        if (command.equals("fileNew")) {
            player.stop();

            CueList cueList = CueList.getInstance();
            cueList.clear();

            ProjectFile.currentPath = "";
            mainWindow.updateTitleBar();
        } else if (command.equals("fileOpen")) {
            player.stop();

            File file = chooseFile();

            if (file != null) {
                CueList.getInstance().clear();
                ProjectFile.openProject(file);
            }

            mainWindow.updateTitleBar();
        } else if (command.equals("fileSave")) {
            if (ProjectFile.currentPath.isEmpty()) {
                File saveLocation = chooseSaveLocation();

                if (saveLocation != null) {
                    ProjectFile.currentPath = saveLocation.getAbsolutePath();
                }
            }

            ProjectFile.saveProject();

            mainWindow.updateTitleBar();
        } else if (command.equals("fileSaveAs")) {
            File saveLocation = chooseSaveLocation();
            if (saveLocation != null) {
                ProjectFile.currentPath = saveLocation.getAbsolutePath();

                ProjectFile.saveProject();
            }

            mainWindow.updateTitleBar();
        } else if (command.equals("fileExit")) {
            player.stop();
            System.exit(0);
        }
    }


    private static File chooseSaveLocation() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(null);

        File file = null;

        if (result == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }

        return file;
    }

    private static File chooseFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(null);

        File file = null;

        if (result == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }

        return file;
    }
}
