/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcue.ui;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JButton;
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
        
        createComponents(frame.getContentPane());
        
        frame.pack();
        frame.setVisible(true);
    }
    
    private void createComponents(Container container) {
        JButton button = new JButton("Open file...");
        
        button.addActionListener(new OpenListener(this.frame));
        
        container.add(button);
    }
}
