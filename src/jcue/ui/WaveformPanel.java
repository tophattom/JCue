package jcue.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import javax.swing.JPanel;
import jcue.domain.AudioCue;
import jcue.domain.AudioStream;

/**
 *
 * @author oppilas
 */
public class WaveformPanel extends JPanel {

    private BufferedImage cacheImg;
    private AudioCue cue;
    private FloatBuffer streamData;
    
    private final Color backgroundColor = new Color(64, 64, 64);
    private final Color waveformColor = new Color(200, 200, 250);
    private final Color inOutColor = Color.YELLOW;
    private final Color positionColor = Color.RED;
    
    private static final int DIR_RIGHT = 1;
    private static final int DIR_LEFT = -1;

    public WaveformPanel() {
        super();
    }

    public void setAudioStream(AudioStream as) {
        this.streamData = as.getStreamData();
    }

    public void setCue(AudioCue cue) {
        this.cue = cue;
        this.streamData = this.cue.getAudio().getStreamData();

        this.cacheImg = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = this.getWidth();
        int height = this.getHeight();

        //Waveform drawing
        this.createCacheImage();

        g.drawImage(this.cacheImg, 0, 0, null);
        
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

    private void createCacheImage() {
        int width = this.getWidth();
        int height = this.getHeight();

        if (this.cacheImg == null || this.cacheImg.getWidth() != width
                || this.cacheImg.getHeight() != height) {

            this.cacheImg = new BufferedImage(width, height, ColorSpace.TYPE_RGB);

            Graphics cg = this.cacheImg.getGraphics();

            cg.setColor(this.backgroundColor);
            cg.fillRect(0, 0, width, height);

            if (this.streamData != null) {
                int step = (int) Math.ceil(this.streamData.capacity() / width);

                cg.setColor(this.waveformColor);
                for (int i = 0; i < width; i++) {
                    float maxValue = 0.0f;

                    for (int k = (i * step); k < (i * step + step); k++) {
                        if (this.streamData.get(k) > maxValue) {
                            maxValue = this.streamData.get(k);
                        }
                    }

                    cg.drawLine(i, height / 2, i, (int) ((height / 2) + (height / 2) * maxValue));
                    cg.drawLine(i, height / 2, i, (int) ((height / 2) - (height / 2) * maxValue));
                }
            }
        }
    }
    
    private Polygon getMarkerTriangle(int x, int dir) {
        Polygon result = new Polygon();
        result.addPoint(x, 0);
        result.addPoint(x + (10 * dir), 7);
        result.addPoint(x, 14);
        
        return result;
    }
}
