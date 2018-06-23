/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jlsd.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

/**
 * Class JArea.
 * An abstract class that defines JShapes that extend the {@link java.awt.geom.Area} class.
 * Construction methods for update and (eventually) commit and confirm shape will be implemented in subclases.
 * @author jlsuarezdiaz
 */
public abstract class JArea extends Area implements JShape{
    
    private BasicShapePainter painter = new BasicShapePainter(this);

    /**
     * Default constructor that creates an empty area.
     */
    public JArea() {
        super();
    }
    
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
