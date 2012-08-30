package jcue.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import jcue.domain.AudioCue;

/**
 *
 * @author oppilas
 */
public class WaveformPanel extends JPanel {
    
    public static final int PREF_HEIGHT = 170;

    private AudioCue cue;
    
    public static final Color backgroundColor = new Color(64, 64, 64);
    public static final Color waveformColor = new Color(200, 200, 250);
    private final Color inOutColor = Color.YELLOW;
    private final Color positionColor = Color.RED;
    
    private static final int DIR_RIGHT = 1;
    private static final int DIR_LEFT = -1;

    public WaveformPanel() {
        super();
        super.setPreferredSize(new Dimension(0, PREF_HEIGHT));
    }

    public void setCue(AudioCue cue) {
        this.cue = cue;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = this.getWidth();
        int height = this.getHeight();

        //Waveform drawing
        BufferedImage waveformImg = this.cue.getAudio().getWaveformImg();
        
        if (waveformImg != null) {
            g.drawImage(this.cue.getAudio().getWaveformImg(), 0, 0, width, height, null);
        } else {
            g.setColor(backgroundColor);
            g.fillRect(0, 0, width, height);
        }
        
        //In and out marker drawing
        int inX = (int) (width * (this.cue.getInPos() / this.cue.getAudio().getLength()));
        int outX = (int) (width * (this.cue.getOutPos() / this.cue.getAudio().getLength()));
        
        g.setColor(this.inOutColor);
        
        //In marker
        g.drawLine(inX, 0, inX, height);
        g.fillPolygon(this.getMarkerTriangle(inX, DIR_RIGHT));
        
        g.drawLine(outX, 0, outX, height);
        g.fillPolygon(this.getMarkerTriangle(outX, DIR_LEFT));
    }

    private Polygon getMarkerTriangle(int x, int dir) {
        Polygon result = new Polygon();
        result.addPoint(x, 0);
        result.addPoint(x + (10 * dir), 7);
        result.addPoint(x, 14);
        
        return result;
    }
}
