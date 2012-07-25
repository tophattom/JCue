/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcue.ui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import jcue.domain.AudioCue;

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
            }
            //TODO: render other cue types

            setForeground(jlist.getForeground());
        }

        setText(o.toString());

        return this;
    }
}
