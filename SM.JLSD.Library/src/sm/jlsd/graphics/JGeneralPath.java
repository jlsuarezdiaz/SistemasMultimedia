/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jlsd.graphics;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * Class JGeneralPath.
 * An abstract JShape class that extends the {@link java.awt.geom.Path2D} functionality.
 * @author jlsuarezdiaz
 */
public abstract class JGeneralPath extends Path2D.Double implements JShape {
    
    /**
     * Painter.
     */
    private BasicShapePainter painter = new BasicShapePainter(this);

    /**
     * Constructor.
     * @param p0 Initial point.
     */
    public JGeneralPath(Point2D p0){
        super(new JPoint(p0));
    }
    
    /**
     * Construcor.
     * @param s Initial shape.
     */
    public JGeneralPath(Shape s){
        super(s);
    }
    
    /**
     * Constructor.
     * @param rule The winding rule.
     */
    public JGeneralPath(int rule){
        super(rule);
    }
    
    /**
     * Constructor.
     * @param s Initial shape.
     * @param at Initial transform.
     */
    public JGeneralPath(Shape s, AffineTransform at){
        super(s, at);
    }
    
    /**
     * Constructor.
     * @param rule The winding rule.
     * @param initialCapacity The estimate for the number of path segments. 
     */
    public JGeneralPath(int rule, int initialCapacity){
        super(rule, initialCapacity);
    }

    /**
     * {@inheritDoc }
     * Implemented in sub classes.
     */
    @Override
    public abstract void updateShape(Point2D p0, Point2D p1);

    /**
     * {@inheritDoc }
     */
    @Override
    public void move(Point2D vtrans) {
        vtrans = JShape.scaleRotate(vtrans, painter.getRotation(), painter.getScale());
        AffineTransform at = AffineTransform.getTranslateInstance(vtrans.getX(), vtrans.getY());
        this.transform(at);
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
