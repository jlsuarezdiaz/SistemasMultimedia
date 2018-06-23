/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jlsd.graphics;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;


/**
 * Class JRoundRectangle.
 * An JShape extendion for the {@link java.awt.geom.RoundRectangle2D} class.
 * Adds to the RoundRectangle class the functionality to construct a round rectangle dinamially in a single step. 
 * The arc lengths of the corners will be proportional to the sides length during the construction.
 * @author jlsuarezdiaz
 */
public class JRoundRectangle extends RoundRectangle2D.Double implements JShape {
    
    private BasicShapePainter painter = new BasicShapePainter(this);

    /**
     * Constructs a new JRoundRectangle initialized to location (0.0, 0.0), size (0.0, 0.0), and corner arcs of radius 0.0.
     */
    public JRoundRectangle() {
        super();
        
    }
    
    /**
     * Constructor.
     * @param x The X coordinate of the up left corner.
     * @param y The Y coordinate of the up left corner.
     * @param w The width of the rectangle.
     * @param h The height of the rectangle.
     * @param arcw The width of the corners rounding arc.
     * @param arch The height of the corners rounding arc.
     */
    public JRoundRectangle(double x, double y, double w, double h, double arcw, double arch){
        super(x, y, w, h, arcw, arch);
    }
    
    /**
     * Constructor.
     * @param p Upper left corner.
     * @param dim Rectangle's dimension.
     * @param arc Rounding arcs' dimension.
     */
    public JRoundRectangle(Point2D p, Dimension dim, Dimension arc){
        super(p.getX(),p.getY(),dim.width,dim.height,arc.width,arc.height);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void updateShape(Point2D p0, Point2D p1) {
        this.setFrameFromDiagonal(p0, p1);
        this.setRoundRect(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.getWidth()/4, this.getHeight()/4);
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
