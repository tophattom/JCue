package jcue.ui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jcue.domain.CueList;
import jcue.domain.CuePlayer;
import jcue.domain.ProjectFile;
import jcue.ui.MainWindow;

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
            mainWindow.updateTitleBar();
        } else if (command.equals("fileSave")) {
            mainWindow.updateTitleBar();
        } else if (command.equals("fileSaveAs")) {
            mainWindow.updateTitleBar();
        } else if (command.equals("fileExit")) {
            player.stop();
            System.exit(0);
        }
    }
    
}
