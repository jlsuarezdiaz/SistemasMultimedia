/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jlsd.graphics;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

/**
 * Class JQuadCurve.
 * A JShape extension of the {@link java.awt.geom.QuadCurve2D} class.
 * JQuadCurves add the possibility of 2 steps construction, defining first the associated line and then the control point.
 * @author jlsuarezdiaz
 */
public class JQuadCurve extends QuadCurve2D.Double implements JShape{
    /**
     * Construction steps.
     */
    private static final int STEPS = 2;
    
    /**
     * Current construction step.
     */
    private int currentStep = 0;
    
    /**
     * Painter.
     */
    private BasicShapePainter painter = new BasicShapePainter(this);

    /**
     * Constructor. Creates an empty curve.
     */
    public JQuadCurve() {
        super();
    }

    /**
     * Constructor.
     * @param x1 X coordinate of start point.
     * @param y1 Y coordinate of start point.
     * @param ctrlx X coordinate of control point.
     * @param ctrly Y coordinate of control point.
     * @param x2 X coordinate of end point.
     * @param y2 Y coordinate of end point.
     */
    public JQuadCurve(double x1, double y1, double ctrlx, double ctrly, double x2, double y2) {
        super(x1, y1, ctrlx, ctrly, x2, y2);
    }
    
    /**
     * Constructor.
     * @param p1 Start point.
     * @param ctrl Control point.
     * @param p2 End point.
     */
    public JQuadCurve(Point2D p1, Point2D ctrl, Point2D p2){
        super(p1.getX(), p1.getY(), ctrl.getX(), ctrl.getY(), p2.getX(), p2.getY());
    }

    /**
     * {@inheritDoc }
     * 
     * The updating will depend on the step:
     * - First step: sets the line.
     * - Second step: sets the control point.
     */
    @Override
    public void updateShape(Point2D p0, Point2D p1) {
        switch(currentStep){
            case 0:
                Point2D halfPoint = new Point2D.Double(0.5*(p0.getX()+p1.getX()),0.5*(p0.getY()+p1.getY()));
                this.setCurve(p0, halfPoint, p1);
                break;
            case 1:
                this.setCurve(this.getP1(), p1, this.getP2());
                break;
        }
    }

    /**
     * {@inheritDoc }
     *
     * For this JShape, the current step will be increased.
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
        Point2D p1new = new Point2D.Double(this.getX1()+vtrans.getX(),this.getY1()+vtrans.getY());
        Point2D p2new = new Point2D.Double(this.getX2()+vtrans.getX(),this.getY2()+vtrans.getY());
        Point2D ctrlnew = new Point2D.Double(this.getCtrlX()+vtrans.getX(),this.getCtrlY()+vtrans.getY());
        this.setCurve(p1new, ctrlnew, p2new);
    }

    /**
     * {@inheritDoc } 
     */
    @Override
    public JRectangle getEnclosingRectangle() {
        return new JRectangle(this.getBounds());
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
