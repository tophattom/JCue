package jcue.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
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
    
    private JLabel listLabel;
    private JList eventsList;
    
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
    private AbstractEvent event;

    public EventCueUI() {
        this.listLabel = new JLabel("Events:");
        this.eventsList = new JList();
        this.eventsList.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
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
        this.add(this.listLabel, "wrap");
        this.add(this.eventsList, "hmin 200, wmin 100, wrap");
        
        this.add(this.addButton);
    }

    @Override
    protected void update() {
        super.update();
        
        if (this.cue != null) {
            ArrayList<AbstractEvent> events = this.cue.getEvents();
            AbstractEvent[] tmpArray = new AbstractEvent[events.size()];
            AbstractEvent[] eventsArray = events.toArray(tmpArray);
            ListModel lm = new DefaultComboBoxModel(eventsArray);

            this.eventsList.setModel(lm);
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
            this.addMenu.show(this, this.addButton.getX(), this.addButton.getY() + this.addButton.getHeight());
        } else if (source == this.addTransport) {
            this.cue.addEvent(new TransportEvent());
        } else if (source == this.addMute) {
            this.cue.addEvent(new MuteEvent());
        } else if (source == this.addLoop) {
            this.cue.addEvent(new LoopEvent());
        } else if (source == this.addEffect) {
            this.cue.addEvent(new EffectEvent());
        }
        
        this.update();
    }
    
    
}
