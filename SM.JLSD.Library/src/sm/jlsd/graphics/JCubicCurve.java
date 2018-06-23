/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jlsd.graphics;

import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;

/**
 * Class JCubicCurve.
 * A JShape extension of the {@link java.awt.geom.CubicCurve2D} class.
 * JCubicCurves add the possibility of 3 steps construction, defining first the associated line and then each control point.
 * @author jlsuarezdiaz
 */
public class JCubicCurve extends CubicCurve2D.Double implements JShape{
    /**
     * Construction steps.
     */
    private static final int STEPS = 3;
    
    /**
     * Current step.
     */
    private int currentStep = 0;
    
    /**
     * Painter.
     */
    private BasicShapePainter painter = new BasicShapePainter(this);

    /**
     * Constructor. Creates an empty curve.
     */
    public JCubicCurve() {
        super();
    }

    /**
     * Constructor.
     * @param x1 X coordinate of the curve start.
     * @param y1 Y coordinate of the curve start.
     * @param ctrlx1 X coordinate of the first control point.
     * @param ctrly1 Y coordinate of the first control point.
     * @param ctrlx2 X coordinate of the second control point.
     * @param ctrly2 Y coordinate of the second control point.
     * @param x2 X coordinate of the curve end.
     * @param y2 Y coordinate of the curve end.
     */
    public JCubicCurve(double x1, double y1, double ctrlx1, double ctrly1, double ctrlx2, double ctrly2, double x2, double y2) {
        super(x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2);
    }
    
    /**
     * Constructor.
     * @param p1 Curve start.
     * @param ctrl1 First control point.
     * @param ctrl2 Second control point.
     * @param p2 Curve end.
     */
    public JCubicCurve(Point2D p1, Point2D ctrl1, Point2D ctrl2, Point2D p2){
        super(p1.getX(), p1.getY(), ctrl1.getX(), ctrl1.getY(),ctrl2.getX(),ctrl2.getY(), p2.getX(), p2.getY());
    }


    /**
     * {@inheritDoc }
     * 
     * The updating will depend on the step:
     * - First step: sets the line.
     * - Second step: sets the first control point.
     * - Third step: sets the second control point.
     */
    @Override
    public void updateShape(Point2D p0, Point2D p1) {
        switch(currentStep){
            case 0:
                Point2D halfPoint = new Point2D.Double(0.5*(p0.getX()+p1.getX()),0.5*(p0.getY()+p1.getY()));
                this.setCurve(p0, halfPoint, halfPoint, p1);
                break;
            case 1:
                this.setCurve(this.getP1(), p1,p1, this.getP2());
                break;
            case 2:
                this.setCurve(this.getP1(), this.getCtrlP1(), p1, this.getP2());
                break;
        }
    }

    /**
     * {@inheritDoc }
     * 
     * For this JShape, the currentStep will be increased.
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
        Point2D ctrl1new = new Point2D.Double(this.getCtrlX1()+vtrans.getX(),this.getCtrlY1()+vtrans.getY());
        Point2D ctrl2new = new Point2D.Double(this.getCtrlX2()+vtrans.getX(),this.getCtrlY2()+vtrans.getY());
        this.setCurve(p1new, ctrl1new,ctrl2new, p2new);
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
