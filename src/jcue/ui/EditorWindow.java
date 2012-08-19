package jcue.ui;

import java.awt.*;
import javax.swing.*;
import jcue.domain.CueList;
import jcue.ui.event.EditorListener;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jaakko
 */
public class EditorWindow extends JFrame {
    
    private JButton audioButton, eventButton, changeButton;
    
    private JList cueList;
    private JButton upButton, downButton, deleteButton;
    
    private JTabbedPane editorTabs;
    private JPanel basicPanel, effectPanel;

    private CueList cues;
    
    public EditorWindow(CueList cues) {
        super("Cue - Editor");
        this.setPreferredSize(new Dimension(960, 720));
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        
        this.cues = cues;
        
        createComponents(this.getContentPane());
        createEventListeners();
        
        this.pack();
    }

    private void createComponents(Container container) {
        //Panels for cue controls
        this.basicPanel = new JPanel();
        JScrollPane basicScroll = new JScrollPane(this.basicPanel);
        basicScroll.setBorder(null);
        
        this.effectPanel = new JPanel();
        //**********
        
        //Cue list
        this.cueList = new JList(this.cues);
        this.cueList.setPreferredSize(new Dimension(200, 100));
        this.cueList.setCellRenderer(new EditorListRenderer());
        this.cueList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        //**********
        
        //Buttons for adding new cues
        this.audioButton = new JButton("Audio cue");
        this.audioButton.setMargin(new Insets(10, 10, 10, 10));  
        this.audioButton.setActionCommand("audio");
        
        this.eventButton = new JButton("Event cue");
        this.eventButton.setMargin(new Insets(10, 10, 10, 10));
        this.eventButton.setActionCommand("event");
        
        this.changeButton = new JButton("Level change cue");
        this.changeButton.setMargin(new Insets(10, 10, 10, 10));
        this.changeButton.setActionCommand("change");
        //*************

        //Buttons for managing cue list
        this.upButton = new JButton("^");
        this.downButton = new JButton("V");
        this.deleteButton = new JButton("X");
        //**********
        
        //Tabs for cue controls
        this.editorTabs = new JTabbedPane();
        
        this.editorTabs.addTab("Basic", basicScroll);
        this.editorTabs.addTab("Effects", this.effectPanel);
        //**********
        
        //Add buttons to a new panel for layout
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        top.add(this.audioButton);
        top.add(this.eventButton);
        top.add(this.changeButton);
        
        //Add everything to window
        container.setLayout(new MigLayout("fill, insets panel"));
        container.add(top, "dock north");
        container.add(this.cueList, "dock west, gap 6px 0 0 6px");
        container.add(this.editorTabs, "grow");
    }

    private void createEventListeners() {
        //Event listener for editor
        EditorListener editorListener = new EditorListener(this.cues, this.basicPanel, this.cueList);
        
        //Buttons for adding cues
        this.audioButton.addActionListener(editorListener);
        this.eventButton.addActionListener(editorListener);
        this.changeButton.addActionListener(editorListener);
        
        //Editor list
        this.cueList.addListSelectionListener(editorListener);
    }
}
