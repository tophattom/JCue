package jcue.ui.event;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import jcue.domain.AbstractCue;

/**
 *
 * @author Jaakko
 */
public class EditorListListener implements ListSelectionListener {

    private JList list;
    private JPanel panel;

    public EditorListListener(JList list, JPanel panel) {
        this.list = list;
        this.panel = panel;
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
