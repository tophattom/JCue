package jcue.ui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import jcue.domain.AbstractCue;
import jcue.domain.CueList;
import jcue.domain.CuePlayer;
import jcue.domain.CueType;
import jcue.ui.EditorWindow;

/**
 *
 * @author Jaakko
 */
public class EditorListener implements ActionListener, ListSelectionListener, WindowListener {

    private CueList cueList;
    
    private JList list;
    private JPanel panel;
    
    private EditorWindow window;

    public EditorListener(CueList cueList, EditorWindow window, JList list) {
        this.cueList = cueList;
        this.window = window;
        this.list = list;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        
        System.out.println(command);
        if (command.equals("audio")) {
            this.cueList.addCue(CueType.AUDIO);
            
            this.list.setSelectedIndex(this.cueList.getSize() - 1);
        } else if (command.equals("event")) {
            this.cueList.addCue(CueType.EVENT);
            
            this.list.setSelectedIndex(this.cueList.getSize() - 1);
        } else if (command.equals("change")) {
            this.cueList.addCue(CueType.FADE);
            
            this.list.setSelectedIndex(this.cueList.getSize() - 1);
        } else if (command.equals("note")) {
            this.cueList.addCue(CueType.NOTE);
            
            this.list.setSelectedIndex(this.cueList.getSize() - 1);
        } else if (command.equals("deleteCue")) {
            this.cueList.deleteCue(window.getCurrentCue());
            
            window.setCurrentCue(null);
            window.setUI(null);
            window.setSelectedIndex(-1);
        } else if (command.equals("moveUp")) {
            AbstractCue cue = window.getCurrentCue();
            int index = cueList.getCueIndex(cue);
            
            boolean moveCue = this.cueList.moveCue(cue, index - 1);
            
            if (moveCue) {
                window.setSelectedIndex(index - 1);
            }
        } else if (command.equals("moveDown")) {
            AbstractCue cue = window.getCurrentCue();
            int index = cueList.getCueIndex(cue);
            
            boolean moveCue = this.cueList.moveCue(cue, index + 1);
            
            if (moveCue) {
                window.setSelectedIndex(index + 1);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (!lse.getValueIsAdjusting()) {
            Object selection = list.getSelectedValue();

            if (selection instanceof AbstractCue) {
                AbstractCue cue = (AbstractCue) list.getSelectedValue();
                this.window.setUI(cue);
                this.window.setCurrentCue(cue);
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        for (AbstractCue ac : cueList.getCues()) {
            ac.stop();
        }
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
