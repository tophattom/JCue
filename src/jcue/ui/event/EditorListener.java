package jcue.ui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import jcue.domain.AbstractCue;
import jcue.domain.CueList;
import jcue.domain.CueType;

/**
 *
 * @author Jaakko
 */
public class EditorListener implements ActionListener, ListSelectionListener {

    private CueList cueList;
    
    private JList list;
    private JPanel panel;

    public EditorListener(CueList cueList, JPanel panel, JList list) {
        this.cueList = cueList;
        this.panel = panel;
        this.list = list;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        
        System.out.println(command);
        if (command.equals("audio")) {
            this.cueList.addCue(CueType.AUDIO);
        } else if (command.equals("event")) {
            this.cueList.addCue(CueType.EVENT);
        } else if (command.equals("change")) {
            this.cueList.addCue(CueType.CHANGE);
        } else if (command.equals("note")) {
            this.cueList.addCue(CueType.NOTE);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (!lse.getValueIsAdjusting()) {
            Object selection = list.getSelectedValue();

            if (selection instanceof AbstractCue) {
                AbstractCue cue = (AbstractCue) list.getSelectedValue();
                cue.updateUI(panel);
            }
        }
    }
}
