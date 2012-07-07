/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcue.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author oppilas
 */
public class WaveformPanel extends JPanel {

    private int maxWidth, maxHeight;
    private BufferedImage waveImg;

    public WaveformPanel(int maxWidth, int maxHeight) {
        super();

        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;

        waveImg = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_RGB);
    }

    public void setWaveImg(BufferedImage waveImg) {
        this.waveImg = waveImg;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
    }
    
    
}
