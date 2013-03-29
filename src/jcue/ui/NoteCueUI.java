package jcue.ui;

import jcue.domain.AbstractCue;

/**
 * @author Jaakko
 */
public class NoteCueUI extends AbstractCueUI {

    public NoteCueUI() {
        removeComponents();
    }

    private void removeComponents() {
        super.remove(super.cueLabel);
        super.remove(super.cueSelect);
        super.remove(super.delayField);
        super.remove(super.delayLabel);
        super.remove(super.startModeLabel);
        super.remove(super.startModeSelect);
    }
}
