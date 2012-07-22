package jcue.ui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import jcue.domain.CueList;
import jcue.domain.CueType;

/**
 *
 * @author Jaakko
 */
public class EditorButtonListener implements ActionListener {

    private CueList cueList;
    private DefaultListModel lm;

    public EditorButtonListener(CueList cueList, DefaultListModel lm) {
        this.cueList = cueList;
        this.lm = lm;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        
        System.out.println(command);
        if (command.equals("audio")) {
            this.cueList.addCue(CueType.AUDIO);
            this.lm.addElement(this.cueList.getCue(this.cueList.size() - 1));
        } else if (command.equals("event")) {
            this.cueList.addCue(CueType.EVENT);
        } else if (command.equals("change")) {
            this.cueList.addCue(CueType.CHANGE);
        } else if (command.equals("note")) {
            this.cueList.addCue(CueType.NOTE);
        }
    }
}
