/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jlsd.graphics;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Class JArc.
 * A JShape extension for the {@link java.awt.geom.Arc2D} class.
 * JArcs add to the functionality of the mentioned Arc2D the possibility of constructing them dinamically.
 * This construction is made in 3 steps. The first step defines de bounding rectangle, the second step defines its position,
 * and the final step defines its length.
 * 
 * @author jlsuarezdiaz
 */
public class JArc extends Arc2D.Double implements JShape{
    /**
     * Painter.
     */
    private BasicShapePainter painter = new BasicShapePainter(this);
    
    /**
     * Construction steps.
     */
    private static final int STEPS = 3;
    
    /**
     * Current sterp.
     */
    private int currentStep = 0;
    
    /**
     * Constructor. Creates an empty arc.
     * @param p Initial point.
     */
    public JArc(Point2D p){
        super();
        setAngleStart(0.0);
        setAngleExtent(180.0);
    }

    /**
     * Constructor.
     * @param type The closure type for the arc: Arc2D.Double.OPEN, Arc2D.Double.CHORD, or Arc2D.Double.PIE.
     */
    public JArc(int type) {
        super(type);
    }

    /**
     * Constructor
     * @param ellipseBounds The framing rectangle that defines the outer boundary of the full ellipse of which this arc is a partial section.
     * @param start  The starting angle of the arc in degrees.
     * @param extent The angular extent of the arc in degrees.
     * @param type The closure type for the arc: Arc2D.Double.OPEN, Arc2D.Double.CHORD, or Arc2D.Double.PIE.
     */
    public JArc(Rectangle2D ellipseBounds, double  start, double extent, int type) {
        super(ellipseBounds, start, extent, type);
    }

    /**
     * Constructor.
     * @param x The X coordinate of the upper-left corner of the arc's framing rectangle.
     * @param y The Y coordinate of the upper-left corner of the arc's framing rectangle.
     * @param w The overall width of the full ellipse of which this arc is a partial section.
     * @param h The overall height of the full ellipse of which this arc is a partial section.
     * @param start  The starting angle of the arc in degrees.
     * @param extent The angular extent of the arc in degrees.
     * @param type The closure type for the arc: Arc2D.Double.OPEN, Arc2D.Double.CHORD, or Arc2D.Double.PIE.
     */
    public JArc(double x, double y, double w, double h, double start, double extent, int type) {
        super(x, y, w, h, start, extent, type);
    }

    /**
     * {@inheritDoc }
     * 
     * The behaviour will depend on the current construction step:
     * - First step: a semiellipse that defines de framing rectangle will be created.
     * - Second step: the arc will be rotated to the define the initial point.
     * - Third step: the arc length will be modified to define the arc extent.
     */
    @Override
    public void updateShape(Point2D p0, Point2D p1) {
        switch(currentStep){
            case 0:
                this.setFrameFromDiagonal(p0, p1);              
                break;
            case 1:
            {
                double cx = this.getFrame().getCenterX();
                double cy = this.getFrame().getCenterY();
                double vx = p1.getX() - cx;
                double vy = p1.getY() - cy;
                double angleStart = -Math.toDegrees(Math.atan2(vy, vx));//(360.0 - Math.toDegrees(Math.atan2(vy, vx)))%360.0;
                this.setAngleStart(angleStart);
                break;
            }
            case 2:
            {
                double cx = this.getFrame().getCenterX();
                double cy = this.getFrame().getCenterY();
                double vx = p1.getX() - cx;
                double vy = p1.getY() - cy;
                double angleExtent = -Math.toDegrees(Math.atan2(vy, vx));//(360.0 - Math.toDegrees(Math.atan2(vy, vx)))%360.0;
                if(angleExtent < 0.0) angleExtent = 360 + angleExtent;
                this.setAngleExtent(angleExtent);
                break;
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean commitShape() {
        this.currentStep+=1;
        return currentStep >= STEPS;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void move(Point2D vtrans) {
        vtrans = JShape.scaleRotate(vtrans, painter.getRotation(), painter.getScale());
        Point2D pnew = new Point2D.Double(this.getX()+vtrans.getX(),this.getY()+vtrans.getY());
        this.setFrame(pnew.getX(),pnew.getY(),this.getWidth(),this.getHeight());
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public JRectangle getEnclosingRectangle() {
        return new JRectangle(super.getBounds());
    }

    /**
     * {@inheritDoc } 
     */
    @Override
    public void paint(Graphics2D g2d) {
        painter.paint(g2d);
    }

    /**
     * {@inheritDoc } 
     */
    @Override
    public BasicShapePainter getPainter() {
        return this.painter;
    }
    
}
