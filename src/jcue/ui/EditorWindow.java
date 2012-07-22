/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcue.ui;

import java.awt.*;
import javax.swing.*;
import jcue.domain.CueList;
import jcue.ui.event.EditorButtonListener;
import jcue.ui.event.EditorListListener;

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
        
        this.pack();
    }

    private void createComponents(Container container) {
        //Panels for cue controls
        this.basicPanel = new JPanel();
        
        this.effectPanel = new JPanel();
        //**********
        
        //Cue list
        DefaultListModel lm = new DefaultListModel();
        
        this.cueList = new JList(lm);
        this.cueList.setPreferredSize(new Dimension(200, 100));
        this.cueList.addListSelectionListener(new EditorListListener(this.cueList, this.basicPanel));
        //**********
        
        //Buttons for adding new cues
        EditorButtonListener buttonListener = new EditorButtonListener(this.cues, lm);
        
        this.audioButton = new JButton("Audio cue");
        this.audioButton.setMargin(new Insets(10, 10, 10, 10));  
        this.audioButton.setActionCommand("audio");
        this.audioButton.addActionListener(buttonListener);
        
        this.eventButton = new JButton("Event cue");
        this.eventButton.setMargin(new Insets(10, 10, 10, 10));
        this.eventButton.setActionCommand("event");
        this.eventButton.addActionListener(buttonListener);
        
        this.changeButton = new JButton("Level change cue");
        this.changeButton.setMargin(new Insets(10, 10, 10, 10));
        this.changeButton.setActionCommand("change");
        this.changeButton.addActionListener(buttonListener);
        //*************

        //Buttons for managing cue list
        this.upButton = new JButton("^");
        this.downButton = new JButton("V");
        this.deleteButton = new JButton("X");
        //**********
        
        
        
//        //UI testing shit: REMOVE
//        AbstractCueUI ui = new AbstractCueUI();
//        ui.showUI(basicPanel);
//        AudioCueUI aui = new AudioCueUI();
//        aui.showUI(basicPanel);
//        //**********
        
        //Tabs for cue controls
        this.editorTabs = new JTabbedPane();
        
        this.editorTabs.addTab("Basic", this.basicPanel);
        this.editorTabs.addTab("Effects", this.effectPanel);
        //**********
        
        //Add buttons to a new panel for layout
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        top.add(this.audioButton);
        top.add(this.eventButton);
        top.add(this.changeButton);
        
        //Add everything to window
        container.add(top, BorderLayout.NORTH);
        container.add(this.cueList, BorderLayout.WEST);
        container.add(this.editorTabs, BorderLayout.CENTER);
    }
}
