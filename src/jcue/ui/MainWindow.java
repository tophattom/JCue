package jcue.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
import jcue.domain.CueList;
import jcue.domain.CuePlayer;
import jcue.domain.ProjectFile;
import jcue.ui.event.MainWindowListener;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jaakko
 */
public class MainWindow implements Runnable {

    private JFrame frame;
    
    private JMenuBar menuBar;
    private JMenu fileMenu, helpMenu;

    private JMenuItem fileNew, fileOpen, fileSave, fileSaveAs, fileExit;
    private JMenuItem helpAbout;
    
    private JPanel topPanel;
    private JTable mainCueList;
    
    private JButton playButton, pauseButton, stopButton;
    private JButton editorButton;
    
    private EditorWindow editor;
    
    private MainWindowListener eventListener;
    
    private CueList cues;
    private CuePlayer player;
    
    public MainWindow(CueList cues) {
        this.cues = cues;
        this.player = new CuePlayer(cues);
    }
    
    @Override
    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { 
        }
        
        frame = new JFrame("Cue");
        frame.setPreferredSize(new Dimension(1024, 768));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        this.editor = new EditorWindow(this.cues);
        
        this.eventListener = new MainWindowListener(this, player);
        
        createMenus();
        createComponents(frame.getContentPane());
        
        frame.pack();
        frame.setVisible(true);
    }
    
    private void createMenus() {
        menuBar = new JMenuBar();
        
        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");

        //Create file menu items
        fileNew = new JMenuItem("New...");
        fileNew.setActionCommand("fileNew");
        fileNew.addActionListener(eventListener);

        fileOpen = new JMenuItem("Open...");
        fileOpen.setActionCommand("fileOpen");
        fileOpen.addActionListener(eventListener);

        fileSave = new JMenuItem("Save");
        fileSave.setActionCommand("fileSave");
        fileSave.addActionListener(eventListener);

        fileSaveAs = new JMenuItem("Save as...");
        fileSaveAs.setActionCommand("fileSaveAs");
        fileSaveAs.addActionListener(eventListener);

        fileExit = new JMenuItem("Exit");
        fileExit.setActionCommand("fileExit");
        fileExit.addActionListener(eventListener);

        //Add options to file menu
        fileMenu.add(fileNew);
        fileMenu.add(fileOpen);
        fileMenu.add(fileSave);
        fileMenu.add(fileSaveAs);
        fileMenu.add(fileExit);

        //About menu
        helpAbout = new JMenuItem("About");

        helpMenu.add(helpAbout);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        
        frame.setJMenuBar(menuBar);
    }
    
    private void createComponents(Container container) {
        topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        mainCueList = new JTable(this.cues);
        mainCueList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        mainCueList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainCueList.setColumnSelectionAllowed(false);
        mainCueList.getSelectionModel().addListSelectionListener(eventListener);
        
        //Buttons for playback
        playButton = new JButton("Play next");
        playButton.setActionCommand("play");
        playButton.addActionListener(this.eventListener);
        playButton.setMargin(new Insets(10, 10, 10, 10));
        
        pauseButton = new JButton("Pause all");
        pauseButton.setMargin(new Insets(10, 10, 10, 10));
        
        stopButton = new JButton("Stop all");
        stopButton.setActionCommand("stop");
        stopButton.addActionListener(this.eventListener);
        stopButton.setMargin(new Insets(10, 10, 10, 10));
        
        //Opens the editor
        editorButton = new JButton("Editor");
        editorButton.setMargin(new Insets(10, 10, 10, 10));
        editorButton.setActionCommand("editor");
        editorButton.addActionListener(this.eventListener);
        
        //Add to panel
        topPanel.add(playButton);
        topPanel.add(pauseButton);
        topPanel.add(stopButton);
        topPanel.add(editorButton);
        
        //Add panel to top and list to center
        container.setLayout(new MigLayout("fill, insets panel"));
        container.add(topPanel, "dock north");
        container.add(mainCueList, "grow");
    }
    
    public void showEditorWindow() {
        this.editor.setVisible(true);

        if (cues.size() == 0) {
            editor.setCurrentCue(null);
            editor.setSelectedIndex(-1);
        }
    }

    public CuePlayer getPlayer() {
        return player;
    }
    
    public void setSelectedRow(int row) {
        this.mainCueList.setRowSelectionInterval(row, row);
    }

    public int getSelectedRow() {
        return mainCueList.getSelectedRow();
    }

    public void updateTitleBar() {
        frame.setTitle("Cue - " + ProjectFile.currentPath);
    }
}
