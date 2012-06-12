package jcue.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.*;

/**
 *
 * @author Jaakko
 */
public class MainWindow implements Runnable {

    JFrame frame;
    
    JPanel topPanel;
    JList mainCueList;
    
    JButton playButton, pauseButton, stopButton;
    JButton editorButton;
    
    @Override
    public void run() {
        frame = new JFrame("Cue");
        frame.setPreferredSize(new Dimension(1024, 768));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        createComponents(frame.getContentPane());
        
        frame.pack();
        frame.setVisible(true);
        
        //TODO: whole ui
    }
    
    private void createComponents(Container container) {
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        
        mainCueList = new JList();
        
        playButton = new JButton("Play next");
        pauseButton = new JButton("Pause all");
        stopButton = new JButton("Stop all");
        
        editorButton = new JButton("Editor");
        
        topPanel.add(playButton);
        topPanel.add(pauseButton);
        topPanel.add(stopButton);
        topPanel.add(editorButton);
        
        container.add(topPanel, BorderLayout.NORTH);
        container.add(mainCueList, BorderLayout.CENTER);
    }
}
