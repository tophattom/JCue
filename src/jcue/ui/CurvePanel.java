package jcue.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
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
    private static final Color textColor = Color.LIGHT_GRAY;
    private static final Color cursorColor = new Color(90, 90, 90);
    
    private static final Stroke curveStroke = new BasicStroke(3);
    private static final Stroke ctrlPointStroke = new BasicStroke(2);
    private static final float[] dash = {7.0f, 10.0f};
    private static final Stroke ctrlLineStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
    
    private static final int pointDrawRadius = 6;
    private static final int ctrlDrawRadius = 6;
    
    private static final int grabRadius = 10;
    
    public static final int POINT_P1 = 1;
    public static final int POINT_P2 = 2;
    public static final int POINT_CTRL = 3;
    
    private ParameterEnvelope envelope;
    
    private boolean dragging;
    private QuadCurve2D selectedCurve;
    private int selectedPoint;
    
    private int cursorX;

    public CurvePanel() {
        super();
        super.setMinimumSize(new Dimension(500, 200));
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void setEnvelope(ParameterEnvelope envelope) {
        this.envelope = envelope;
        this.repaint();
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
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, width, height);
        
        //Draw guidelines
        g2d.setColor(cursorColor);
        
        double step = height / 8;
        for (int i = 0; i < 8; i++) {
            g2d.drawLine(0, (int) (i * step), width, (int) (i * step));
        }
        
        if (this.envelope != null) {
            //Draw start and end times
            FontMetrics fontMetrics = g2d.getFontMetrics();
            g2d.setColor(textColor);
            g2d.drawString("0,00", 2, height - fontMetrics.getDescent());

            String duration = String.format("%.2f", this.envelope.getDuration());
            g2d.drawString(duration, width - fontMetrics.stringWidth(duration), height - fontMetrics.getDescent());

            //Draw cursor
            g2d.setColor(cursorColor);
            g2d.drawLine(this.cursorX, 0, this.cursorX, height);

            //Draw cursor time
            double cursorTime = (this.envelope.getDuration() * this.cursorX) / width;
            String cursorString = String.format("%.2f", cursorTime);

            g2d.setColor(textColor);
            g2d.drawString(cursorString, this.cursorX - fontMetrics.stringWidth(cursorString) / 2, 10);


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
                g2d.setStroke(curveStroke);
                g2d.draw(tmpCurve);

                //Control point
                g2d.setColor(controlColor);
                g2d.setStroke(ctrlPointStroke);

                g2d.draw(new Ellipse2D.Double(cX - ctrlDrawRadius, cY - ctrlDrawRadius, ctrlDrawRadius * 2, ctrlDrawRadius * 2));

                //Lines between points
                g2d.setStroke(ctrlLineStroke);

                g2d.draw(new Line2D.Double(x1, y1, cX, cY));
                g2d.draw(new Line2D.Double(x2, y2, cX, cY));

                //Endpoints
                g2d.setColor(curveColor);
                g2d.fillOval((int) x1 - pointDrawRadius, (int) y1 - pointDrawRadius, pointDrawRadius * 2, pointDrawRadius * 2);
                g2d.fillOval((int) x2 - pointDrawRadius, (int) y2 - pointDrawRadius, pointDrawRadius * 2, pointDrawRadius * 2);
            }
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent me) {
        int mX = me.getX();
        int mY = me.getY();
        
        this.cursorX =  me.getX();
        
        if (this.dragging) {
            double x1 = this.selectedCurve.getX1();
            double y1 = this.selectedCurve.getY1();
            double x2 = this.selectedCurve.getX2();
            double y2 = this.selectedCurve.getY2();
            double cX = this.selectedCurve.getCtrlX();
            double cY = this.selectedCurve.getCtrlY();
            
            double newX = (double) mX / this.getWidth();
            double newY = (double) mY / this.getHeight();
            
            boolean alt = me.isAltDown();
            
            if (this.selectedPoint == POINT_P1) {
                if (me.isShiftDown()) {
                    newX = x1;
                } else if (me.isControlDown()) {
                    newY = y1;
                }
                
                this.envelope.setCurve(selectedCurve, newX, newY, cX, cY, x2, y2, alt);
            } else if (this.selectedPoint == POINT_P2) {
                if (me.isShiftDown()) {
                    newX = x2;
                } else if (me.isControlDown()) {
                    newY = y2;
                }
                
                this.envelope.setCurve(selectedCurve, x1, y1, cX, cY, newX, newY, alt);
            } else if (this.selectedPoint == POINT_CTRL) {
                if (me.isShiftDown()) {
                    newX = cX;
                } else if (me.isControlDown()) {
                    newY = cY;
                }
                
                this.envelope.setCurve(selectedCurve, x1, y1, newX, newY, x2, y2, false);
            }
            
            this.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        this.cursorX =  me.getX();
        
        this.repaint();
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
            
            boolean hit1 = (Point.distance(mX, mY, tmpCurve.getX1(), tmpCurve.getY1()) <= grabRadius);
            boolean hit2 = (Point.distance(mX, mY, tmpCurve.getX2(), tmpCurve.getY2()) <= grabRadius);
            boolean hit3 = (Point.distance(mX, mY, tmpCurve.getCtrlX(), tmpCurve.getCtrlY()) <= grabRadius);
            
            if (hit1) {
                if (me.getButton() == MouseEvent.BUTTON1) {
                    this.dragging = true;
                    this.selectedCurve = c;
                    this.selectedPoint = POINT_P1;
                } else if (me.getButton() == MouseEvent.BUTTON3) {
                    this.envelope.deletePoint(c, POINT_P1);
                    this.repaint();
                }
                
                break;
            } else if (hit2) {
                if (me.getButton() == MouseEvent.BUTTON1) {
                    this.dragging = true;
                    this.selectedCurve = c;
                    this.selectedPoint = POINT_P2;
                } else if (me.getButton() == MouseEvent.BUTTON3) {
                    this.envelope.deletePoint(c, POINT_P2);
                    this.repaint();
                }
                
                break;
            } else if (hit3) {
                if (me.getButton() == MouseEvent.BUTTON1) {
                    this.dragging = true;
                    this.selectedCurve = c;
                    this.selectedPoint = POINT_CTRL;
                } else if (me.getButton() == MouseEvent.BUTTON3) {
                    this.envelope.deletePoint(c, POINT_CTRL);
                    this.repaint();
                }
                
                break;
            }
        }
        
        if (!this.dragging && (me.getButton() == MouseEvent.BUTTON1)) {
            this.envelope.addPoint((double) mX / this.getWidth(), (double) mY / this.getHeight());
            
            this.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        this.dragging = false;
        this.selectedCurve = null;
        this.selectedPoint = 0;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
        this.cursorX = -100;
        this.repaint();
    }
    
    
    private QuadCurve2D getRealCurve(QuadCurve2D c) {
        int width = this.getWidth();
        int height = this.getHeight();
        
        QuadCurve2D tmpCurve = new QuadCurve2D.Double();
        tmpCurve.setCurve(c.getX1() * width, c.getY1() * height, c.getCtrlX() * width, c.getCtrlY() * height, c.getX2() * width, c.getY2() * height);
        
        return tmpCurve;
    }
}
