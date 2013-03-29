package jcue.ui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import jcue.domain.audiocue.AudioCue;
import jcue.domain.eventcue.EventCue;
import jcue.domain.fadecue.FadeCue;
import jcue.domain.notecue.NoteCue;

/**
 *
 * @author Jaakko
 */
public class EditorListRenderer extends JLabel implements ListCellRenderer {

    public EditorListRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList jlist, Object o, int i, boolean bln, boolean bln1) {
        if (bln) {
            setBackground(jlist.getSelectionBackground());
            setForeground(jlist.getSelectionForeground());
        } else {
            if (o instanceof AudioCue) {
                setBackground(new Color(100, 200, 200));
            } else if (o instanceof EventCue) {
                setBackground(new Color(100, 200, 100));
            } else if (o instanceof FadeCue) {
                setBackground(new Color(200, 100, 200));
            } else if (o instanceof NoteCue) {
                setBackground(new Color(240, 190, 0));
            }

            setForeground(jlist.getForeground());
        }

        setText(o.toString());

        return this;
    }
}
