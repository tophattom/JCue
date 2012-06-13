package jcue.ui;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Jaakko
 */
public class MainWindow implements Runnable {

    JFrame frame;
    
    JMenuBar menuBar;
    JMenu fileMenu, aboutMenu;
    
    JPanel topPanel;
    JList mainCueList;
    
    JButton playButton, pauseButton, stopButton;
    JButton editorButton;
    
    @Override
    public void run() {
        frame = new JFrame("Cue");
        frame.setPreferredSize(new Dimension(1024, 768));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        createMenus();
        createComponents(frame.getContentPane());
        
        frame.pack();
        frame.setVisible(true);
        
        //TODO: whole ui
    }
    
    private void createMenus() {
        menuBar = new JMenuBar();
        
        fileMenu = new JMenu("File");
        aboutMenu = new JMenu("About");
        
        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);
        
        frame.setJMenuBar(menuBar);
    }
    
    private void createComponents(Container container) {
        topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        mainCueList = new JList();
        
        //Buttons for playback
        playButton = new JButton("Play next");
        playButton.setMargin(new Insets(10, 10, 10, 10));
        
        pauseButton = new JButton("Pause all");
        pauseButton.setMargin(new Insets(10, 10, 10, 10));
        
        stopButton = new JButton("Stop all");
        stopButton.setMargin(new Insets(10, 10, 10, 10));
        
        //Opens the editor
        editorButton = new JButton("Editor");
        editorButton.setMargin(new Insets(10, 10, 10, 10));
        
        //Add to panel
        topPanel.add(playButton);
        topPanel.add(pauseButton);
        topPanel.add(stopButton);
        topPanel.add(editorButton);
        
        //Add panel to top and list to center
        container.add(topPanel, BorderLayout.NORTH);
        container.add(mainCueList, BorderLayout.CENTER);
    }
}
