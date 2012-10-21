package jcue.ui;

import java.awt.*;
import javax.swing.*;
import jcue.domain.AbstractCue;
import jcue.domain.CueList;
import jcue.domain.CueType;
import jcue.domain.audiocue.AudioCue;
import jcue.domain.eventcue.EventCue;
import jcue.domain.fadecue.FadeCue;
import jcue.ui.event.EditorListener;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jaakko
 */
public class EditorWindow extends JFrame {
    
    private JButton audioButton, eventButton, fadeButton;
    
    private JList cueList;
    private JButton upButton, downButton, deleteButton;
    
    private JTabbedPane editorTabs;
    private JPanel effectPanel;
    
    private AudioCueUI audioPanel;
    private EventCueUI eventPanel;
    private FadeCueUI fadePanel;
    
    private JPanel uiArea;
    
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
        this.uiArea = new JPanel(new CardLayout());
        JScrollPane scrollPane = new JScrollPane(this.uiArea);
        scrollPane.setBorder(null);
        //**********
        
        this.audioPanel = new AudioCueUI();
        this.eventPanel = new EventCueUI();
        this.fadePanel = new FadeCueUI();
        
        this.uiArea.add(new JPanel(), "empty");
        this.uiArea.add(audioPanel, "audio");
        this.uiArea.add(eventPanel, "event");
        this.uiArea.add(fadePanel, "fade");
        
        
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
        
        this.fadeButton = new JButton("Fade cue");
        this.fadeButton.setMargin(new Insets(10, 10, 10, 10));
        this.fadeButton.setActionCommand("change");
        //*************

        //Buttons for managing cue list
        this.upButton = new JButton("^");
        this.downButton = new JButton("V");
        this.deleteButton = new JButton("X");
        //**********
        
        //Add buttons to a new panel for layout
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        top.add(this.audioButton);
        top.add(this.eventButton);
        top.add(this.fadeButton);
        
        //Add everything to window
        container.setLayout(new MigLayout("fill, insets panel"));
        container.add(top, "dock north");
        container.add(this.cueList, "dock west, gap 6px 0 0 6px");
        container.add(scrollPane, "grow");
    }

    private void createEventListeners() {
        //Event listener for editor
        EditorListener editorListener = new EditorListener(this.cues, this, this.cueList);
        
        //Buttons for adding cues
        this.audioButton.addActionListener(editorListener);
        this.eventButton.addActionListener(editorListener);
        this.fadeButton.addActionListener(editorListener);
        
        //Editor list
        this.cueList.addListSelectionListener(editorListener);
        
        //CueList data listener
        this.cues.addListDataListener(this.audioPanel);
        this.cues.addListDataListener(this.eventPanel);
    }
    
    public void setUI(AbstractCue cue) {
        CueType type = cue.getType();
        CardLayout cl = (CardLayout) this.uiArea.getLayout();
        
        if (type == CueType.AUDIO) {
            cl.show(this.uiArea, "audio");
            
            AudioCue ac = (AudioCue) cue;
            this.audioPanel.setCurrentCue(ac);
        } else if (type == CueType.EVENT) {
            cl.show(this.uiArea, "event");
            
            EventCue ec = (EventCue) cue;
            this.eventPanel.setCurrentCue(ec);
        } else if (type == CueType.FADE) {
            cl.show(this.uiArea, "fade");
            
            FadeCue fc = (FadeCue) cue;
            this.fadePanel.setCurrentCue(fc);
        }
    }
}
