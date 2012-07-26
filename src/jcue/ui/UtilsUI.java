package jcue.ui;

import java.awt.GridBagConstraints;

/**
 *
 * @author Jaakko
 */
public class UtilsUI {
    
    public static void setGBC(GridBagConstraints c, int gridx, int gridy,
            double weightx, double weighty, int gridwidth, int gridheight, int fill) {
        c.gridx = gridx;
        c.gridy = gridy;
        
        c.weightx = weightx;
        c.weighty = weighty;
        
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        
        c.fill = fill;
    }
}
