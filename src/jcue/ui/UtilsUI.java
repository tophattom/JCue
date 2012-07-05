/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcue.ui;

import java.awt.GridBagConstraints;

/**
 *
 * @author Jaakko
 */
public class UtilsUI {
    
    public static void setGBC(GridBagConstraints c, int gridx, int gridy,
            int weightx, int weighty, int gridwidth, int gridheight, int fill) {
        c.gridx = gridx;
        c.gridy = gridy;
        
        c.weightx = weightx;
        c.weighty = weighty;
        
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        
        c.fill = fill;
    }
}
