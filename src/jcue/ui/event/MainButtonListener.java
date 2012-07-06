/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcue.ui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jcue.ui.MainWindow;

/**
 *
 * @author oppilas
 */
public class MainButtonListener implements ActionListener {
    
    MainWindow mainWindow;
    
    public MainButtonListener(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("editor")) {
            this.mainWindow.showEditorWindow();
        }
    }
    
}
