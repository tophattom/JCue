/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcue.ui;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author Jaakko
 */
public class MainWindow implements Runnable {

    JFrame frame;
    
    @Override
    public void run() {
        frame = new JFrame("Cue");
        frame.setPreferredSize(new Dimension(1024, 768));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        frame.pack();
        frame.setVisible(true);
    }
}
