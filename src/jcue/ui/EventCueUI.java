package jcue.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;
import jcue.domain.AbstractCue;
import jcue.domain.eventcue.AbstractEvent;
import jcue.domain.eventcue.EffectEvent;
import jcue.domain.eventcue.EventCue;
import jcue.domain.eventcue.LoopEvent;
import jcue.domain.eventcue.MuteEvent;
import jcue.domain.eventcue.TransportEvent;

/**
 *
 * @author Jaakko
 */
public class EventCueUI extends AbstractCueUI {
    
    private JComboBox eventSelect;
    
    private JButton addButton;
    private JPopupMenu addMenu;
    private JMenuItem addTransport, addLoop, addMute, addEffect;
    
    private JLabel targetCueLabel;
    private JComboBox selectTargetCue;
    
    private JLabel targetOutLabel;
    private JComboBox selectTargetOut;
    
    private JLabel targetEffectLabel;
    private JComboBox selectTargetEffect;
    
    private EventCue cue;

    public EventCueUI() {
        String[] types = {"Transport event", "Mute event", "Loop event", "Effect event"};
        ComboBoxModel cbm = new DefaultComboBoxModel(types);
        this.eventSelect = new JComboBox(cbm);
        
        //Event adding stuff
        this.addButton = new JButton(new ImageIcon("images/add_small.png"));
        this.addButton.addActionListener(this);
        
        this.addMenu = new JPopupMenu();
        
        this.addTransport = new JMenuItem("Transport event");
        this.addTransport.addActionListener(this);
        this.addMenu.add(addTransport);
        
        this.addMute = new JMenuItem("Mute event");
        this.addMute.addActionListener(this);
        this.addMenu.add(addMute);
        
        this.addLoop = new JMenuItem("Loop event");
        this.addLoop.addActionListener(this);
        this.addMenu.add(addLoop);
        
        this.addEffect = new JMenuItem("Effect event");
        this.addEffect.addActionListener(this);
        this.addMenu.add(addEffect);
        //*******
        
        this.targetCueLabel = new JLabel("Target cue:");
        this.selectTargetCue = new JComboBox();
        
        this.targetOutLabel = new JLabel("Target output:");
        this.selectTargetOut = new JComboBox();
        
        this.targetEffectLabel = new JLabel("Target effect:");
        this.selectTargetEffect = new JComboBox();
        
        addComponents();
    }
    
    private void addComponents() {
        this.add(this.eventSelect, "span 2, split 2, wmin 150");
        
        this.add(this.addButton, "wrap");
    }

    @Override
    protected void update() {
        super.update();
        
        if (this.cue != null) {
        }
    }

    @Override
    protected void setCurrentCue(AbstractCue cue) {
        super.setCurrentCue(cue);
        
        this.cue = (EventCue) cue;
        
        this.update();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        super.actionPerformed(ae);
        
        Object source = ae.getSource();
        
        if (source == this.addButton) {
            String selection = this.eventSelect.getSelectedItem().toString();
            AbstractEvent newEvent = null;
            
            if (selection.equals("Transport event")) {
                newEvent = new TransportEvent();
            } else if (selection.equals("Mute event")) {
                newEvent = new MuteEvent();
            } else if (selection.equals("Loop event")) {
                newEvent = new LoopEvent();
            } else if (selection.equals("Effect event")) {
                newEvent = new EffectEvent();
            }
            
            if (newEvent != null) {
                this.cue.addEvent(newEvent);
                this.add(new EventControlPanel(this.cue, newEvent), "span, growx, wrap");
            }
        }
        
        this.update();
    }
    
    
}
