package jcue.ui;

import java.awt.*;
import javax.swing.*;
import jcue.ui.event.MainButtonListener;

/**
 *
 * @author Jaakko
 */
public class MainWindow implements Runnable {

    private JFrame frame;
    
    private JMenuBar menuBar;
    private JMenu fileMenu, aboutMenu;
    
    private JPanel topPanel;
    private JList mainCueList;
    
    private JButton playButton, pauseButton, stopButton;
    private JButton editorButton;
    
    private EditorWindow editor;
    
    private MainButtonListener buttonListener;
    
    @Override
    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { 
        }
        
        frame = new JFrame("Cue");
        frame.setPreferredSize(new Dimension(1024, 768));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        this.editor = new EditorWindow();
        
        this.buttonListener = new MainButtonListener(this);
        
        createMenus();
        createComponents(frame.getContentPane());
        
        frame.pack();
        frame.setVisible(true);
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
        editorButton.setActionCommand("editor");
        editorButton.addActionListener(this.buttonListener);
        
        //Add to panel
        topPanel.add(playButton);
        topPanel.add(pauseButton);
        topPanel.add(stopButton);
        topPanel.add(editorButton);
        
        //Add panel to top and list to center
        container.add(topPanel, BorderLayout.NORTH);
    }
    
    public void showEditorWindow() {
        this.editor.setVisible(true);
    }
}
