package jcue.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import jcue.domain.audiocue.AudioCue;

/**
 *
 * @author oppilas
 */
public class WaveformPanel extends JPanel implements MouseMotionListener, MouseListener {
    
    public static final int PREF_HEIGHT = 170;
    
    public static final Color backgroundColor = new Color(64, 64, 64);
    public static final Color waveformColor = new Color(200, 200, 250);
    private final Color inOutColor = Color.YELLOW;
    private final Color positionColor = Color.RED;
    
    private static final int DIR_RIGHT = 1;
    private static final int DIR_LEFT = -1;
    
    private static final int DRAG_IN = 1;
    private static final int DRAG_OUT = 2;
    
    private AudioCue cue;
    
    private int inX, outX;
    private int dragging;

    public WaveformPanel() {
        super();
        super.setPreferredSize(new Dimension(0, PREF_HEIGHT));
        
        this.dragging = 0;
        
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }

    public void setCue(AudioCue cue) {
        this.cue = cue;
        
        this.inX = (int) (this.getWidth() * (this.cue.getInPos() / this.cue.getAudio().getLength()));
        this.outX = (int) (this.getWidth() * (this.cue.getOutPos() / this.cue.getAudio().getLength()));
        
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        //if (this.cue != null) {
        int width = this.getWidth();
        int height = this.getHeight();

        //Waveform drawing
        BufferedImage waveformImg = this.cue.getAudio().getWaveformImg();
        
        if (waveformImg != null) {
            g2d.drawImage(this.cue.getAudio().getWaveformImg(), 0, 0, width, height, null);
        } else {
            g2d.setColor(backgroundColor);
            g2d.fillRect(0, 0, width, height);
        }
        
        //In and out marker drawing
        if (this.cue != null && this.cue.getAudio() != null) {
            this.inX = (int) ((double) this.getWidth() * (this.cue.getInPos() / this.cue.getAudio().getLength()));
            this.outX = (int) ((double) this.getWidth() * (this.cue.getOutPos() / this.cue.getAudio().getLength()));
        }
        
        g2d.setColor(this.inOutColor);
        
        //In marker
        g2d.drawLine(inX, 0, inX, height);
        g2d.fillPolygon(this.getMarkerTriangle(inX, DIR_RIGHT));
        
        g2d.drawLine(outX, 0, outX, height);
        g2d.fillPolygon(this.getMarkerTriangle(outX, DIR_LEFT));
        //}
    }

    private Polygon getMarkerTriangle(int x, int dir) {
        Polygon result = new Polygon();
        result.addPoint(x, 0);
        result.addPoint(x + (10 * dir), 7);
        result.addPoint(x, 14);
        
        return result;
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if (this.dragging == DRAG_IN) {
            this.inX = Math.max(0, Math.min(this.getWidth(), me.getX()));
            
            double oldIn = this.cue.getInPos();
            double inPos = this.cue.getAudio().getLength() * ((double) this.inX / this.getWidth());
            this.cue.setInPos(inPos);
            
            this.firePropertyChange("inPos", oldIn, inPos);
        } else if (this.dragging == DRAG_OUT) {
            this.outX = Math.max(0, Math.min(this.getWidth(), me.getX()));
            
            double oldOut = this.cue.getOutPos();
            double outPos = this.cue.getAudio().getLength() * ((double) this.outX / this.getWidth());
            this.cue.setOutPos(outPos);
            
            this.firePropertyChange("outPos", oldOut, outPos);
        }
        
        this.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        int mX = me.getX();
        
        if (Math.abs(mX - this.inX) < 10) {
            this.dragging = DRAG_IN;
        } else if (Math.abs(mX - this.outX) < 10) {
            this.dragging = DRAG_OUT;
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        this.dragging = 0;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
