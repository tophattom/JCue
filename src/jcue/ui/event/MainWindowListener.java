package jcue.ui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jcue.domain.CuePlayer;
import jcue.ui.MainWindow;

/**
 *
 * @author oppilas
 */
public class MainWindowListener implements ActionListener {
    
    private MainWindow mainWindow;
    
    public MainWindowListener(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        
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
    }
    
}
