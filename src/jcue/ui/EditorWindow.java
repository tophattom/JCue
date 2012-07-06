/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcue.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;

/**
 *
 * @author oppilas
 */
public class EditorWindow extends JFrame {
    
    private JButton audioButton, eventButton, changeButton;
    
    private JList cueList;
    private JButton upButton, downButton, deleteButton;
    
    private JTabbedPane editorTabs;
    
    public EditorWindow() {
        super("Cue - Editor");
        this.setPreferredSize(new Dimension(960, 720));
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        
        createComponents(this.getContentPane());
        
        this.pack();
    }

    private void createComponents(Container container) {
        this.audioButton = new JButton("Audio cue");
        this.eventButton = new JButton("Event cue");
        this.changeButton = new JButton("Level change cue");
        
        this.cueList = new JList();
        
        this.upButton = new JButton("^");
        this.downButton = new JButton("V");
        this.deleteButton = new JButton("X");
        
        this.editorTabs = new JTabbedPane();
        this.editorTabs.addTab("Basic", null);
        this.editorTabs.addTab("Effects", null);
        
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        top.add(this.audioButton);
        top.add(this.eventButton);
        top.add(this.changeButton);
        
        container.add(top, BorderLayout.NORTH);
        container.add(this.cueList, BorderLayout.WEST);
        container.add(this.editorTabs, BorderLayout.CENTER);
    }
}
