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
            double weightx, double weighty, int gridwidth, int gridheight, int fill) {
        c.gridx = gridx;
        c.gridy = gridy;
        
        c.weightx = weightx;
        c.weighty = weighty;
        
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        
        c.fill = fill;
    }
    
    public static String secondsToString(double seconds) {
        int mins = (int) (seconds / 60);
        double tmp = (seconds / 60.0 - mins) * 60.0;
        String secs;
        
        if (tmp < 10) {
            secs = "0" + tmp;
        } else {
            secs = String.valueOf(tmp);
        }
        
        return "" + mins + ":" + secs;
    }
    
    public static double stringToSeconds(String text) {
        String[] parts = text.split(":");
        double mins = Double.parseDouble(parts[0]);
        double secs = Double.parseDouble(parts[1]);
        
        return mins * 60.0 + secs;
    }
}
