package jcue.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 *
 * @author Jaakko
 */
public class Knob extends JPanel implements MouseMotionListener, MouseListener {
    
    private static double minTheta = -Math.PI / 4;
    private static double maxTheta = 5 * Math.PI / 4;
    
    private static double spotRadius = 4;
    
    private double minValue, maxValue, value;
    
    private double theta;
    
    private int lastY;

    public Knob(double minValue, double maxValue, double initValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        
        setValue(initValue);
        
        super.setPreferredSize(new Dimension(40, 40));
        super.setOpaque(true);
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    
    public Knob(double minValue, double maxValue) {
        this(minValue, maxValue, minValue);
    }
    
    public void setValue(double value) {
        this.value = value;
        
        this.theta = (minTheta * this.value - minTheta * this.maxValue -
                maxTheta * this.value + maxTheta * this.minValue) / 
                (this.minValue - this.maxValue);
        
        this.repaint();
    }

    public double getValue() {
        return value;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int width = this.getWidth();
        int height = this.getHeight();
        
        double centerX = (double) width / 2;
        double centerY = (double) height / 2;
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        //Draw circle
        g2d.setColor(new Color(64, 64, 64));
        g2d.fillOval(0, 0, width, height);
        
        g2d.setColor(Color.yellow);
        
        //Draw spot
        double spotX = centerX + Math.cos(Math.PI - this.theta) * ((double) width / 2 - spotRadius - 2);
        double spotY = centerY - Math.sin(Math.PI - this.theta) * ((double) height / 2 - spotRadius - 2);
        
        g2d.fillOval((int) (spotX - spotRadius), (int) (spotY - spotRadius), (int) (spotRadius * 2), (int) (spotRadius * 2));
        
        g2d.dispose();
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        int mY = me.getY();
        double deltaY = (this.lastY - mY) / (double) this.getHeight();
        double oldValue = this.value;
        
        this.theta = Math.max(minTheta, Math.min(maxTheta, this.theta + deltaY));
        this.value = ((this.theta - minTheta) / (maxTheta - minTheta)) * (this.maxValue - this.minValue) + this.minValue;
        
        this.lastY = mY;
        
        this.repaint();
        
        this.firePropertyChange("value", oldValue, this.value);
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        this.lastY = me.getY();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
