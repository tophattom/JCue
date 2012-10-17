package jcue.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import javax.swing.JPanel;
import jcue.domain.fadecue.ParameterEnvelope;

/**
 *
 * @author Jaakko
 */
public class CurvePanel extends JPanel implements MouseListener, MouseMotionListener {
    
    private static final Color backgroundColor = new Color(64, 64, 64);
    private static final Color curveColor = Color.YELLOW;
    private static final Color controlColor = new Color(200, 200, 250);
    
    private static final int pointRadius = 6;
    private static final int ctrlRadius = 6;
    
    private static final int DRAG_P1 = 1;
    private static final int DRAG_P2 = 2;
    private static final int DRAG_CTRL = 3;
    
    private ParameterEnvelope envelope;
    
    private boolean dragging;
    private QuadCurve2D draggedCurve;
    private int draggedPoint;

    public CurvePanel() {
        super();
        super.setPreferredSize(new Dimension(500, 200));
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void setEnvelope(ParameterEnvelope envelope) {
        this.envelope = envelope;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        
        int width = this.getWidth();
        int height = this.getHeight();
        
        //Enable anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        //Clear background
        g.setColor(backgroundColor);
        g.fillRect(0, 0, width, height);

        if (this.envelope != null) {
            //Draw curves
            for (QuadCurve2D c : this.envelope.getCurves()) {
                QuadCurve2D tmpCurve = getRealCurve(c);
                
                double x1 = tmpCurve.getX1();
                double y1 = tmpCurve.getY1();
                
                double x2 = tmpCurve.getX2();
                double y2 = tmpCurve.getY2();
                
                double cX = tmpCurve.getCtrlX();
                double cY = tmpCurve.getCtrlY();

                //Curve
                g2d.setColor(curveColor);
                g2d.setStroke(new BasicStroke(3));
                g2d.draw(tmpCurve);

                //Control point
                g2d.setColor(controlColor);
                g2d.setStroke(new BasicStroke(2));
                
                g2d.draw(new Ellipse2D.Double(cX - ctrlRadius, cY - ctrlRadius, ctrlRadius * 2, ctrlRadius * 2));

                //Lines between points
                g2d.draw(new Line2D.Double(x1, y1, cX, cY));
                g2d.draw(new Line2D.Double(cX, cY, x2, y2));
                
                //Endpoints
                g2d.setColor(curveColor);
                g2d.fillOval((int) x1 - pointRadius, (int) y1 - pointRadius, pointRadius * 2, pointRadius * 2);
                g2d.fillOval((int) x2 - pointRadius, (int) y2 - pointRadius, pointRadius * 2, pointRadius * 2);
            }
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent me) {
        int mX = me.getX();
        int mY = me.getY();
        
        if (this.dragging) {
            double x1 = this.draggedCurve.getX1();
            double y1 = this.draggedCurve.getY1();
            double x2 = this.draggedCurve.getX2();
            double y2 = this.draggedCurve.getY2();
            double cX = this.draggedCurve.getCtrlX();
            double cY = this.draggedCurve.getCtrlY();
            
            double newX = (double) mX / this.getWidth();
            double newY = (double) mY / this.getHeight();
            
            if (this.draggedPoint == DRAG_P1) {
                this.envelope.setCurve(draggedCurve, newX, newY, cX, cY, x2, y2);
            } else if (this.draggedPoint == DRAG_P2) {
                this.envelope.setCurve(draggedCurve, x1, y1, cX, cY, newX, newY);
            } else if (this.draggedPoint == DRAG_CTRL) {
                this.envelope.setCurve(draggedCurve, x1, y1, newX, newY, x2, y2);
            }
            
            this.repaint();
        }
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
        int mY = me.getY();
        
        for (QuadCurve2D c : this.envelope.getCurves()) {
            QuadCurve2D tmpCurve = getRealCurve(c);
            
            boolean drag1 = (Point.distance(mX, mY, tmpCurve.getX1(), tmpCurve.getY1()) <= pointRadius);
            boolean drag2 = (Point.distance(mX, mY, tmpCurve.getX2(), tmpCurve.getY2()) <= pointRadius);
            boolean drag3 = (Point.distance(mX, mY, tmpCurve.getCtrlX(), tmpCurve.getCtrlY()) <= ctrlRadius);
            
            if (drag1) {
                this.dragging = true;
                this.draggedCurve = c;
                this.draggedPoint = DRAG_P1;
                
                break;
            } else if (drag2) {
                this.dragging = true;
                this.draggedCurve = c;
                this.draggedPoint = DRAG_P2;
                
                break;
            } else if (drag3) {
                this.dragging = true;
                this.draggedCurve = c;
                this.draggedPoint = DRAG_CTRL;
                
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        this.dragging = false;
        this.draggedCurve = null;
        this.draggedPoint = 0;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
    
    
    private QuadCurve2D getRealCurve(QuadCurve2D c) {
        int width = this.getWidth();
        int height = this.getHeight();
        
        QuadCurve2D tmpCurve = new QuadCurve2D.Double();
        tmpCurve.setCurve(c.getX1() * width, c.getY1() * height, c.getCtrlX() * width, c.getCtrlY() * height, c.getX2() * width, c.getY2() * height);
        
        return tmpCurve;
    }
}
