
package sm.jlsd.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Class JPoint.
 * A class to represent graphically points.
 * @author jlsuarezdiaz
 */
public class JPoint implements JShape{
    /**
     * Painter.
     */
    private BasicShapePainter painter = new BasicShapePainter(this);
    
    /**
     * Point position.
     */
    private Point2D point;
    
    /**
     * Point design.
     */
    private JShape design;
    
    /**
     * Constructor.
     * @param p the point position.
     */
    public JPoint(Point2D p){
        this.point = p;
        this.design = new JLine(p, p);
    }


    /**
     * Does nothing.
     */
    @Override
    public void updateShape(Point2D p0, Point2D p1) {
        
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void move(Point2D vtrans) {
        this.design.move(vtrans);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Rectangle getBounds() {
        return design.getBounds();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Rectangle2D getBounds2D() {
        return design.getBounds2D();
    }
    
    /**
     * Checks if another point is near from this one.
     * @param p point to check
     * @return true, when both points are near according JPoint threshold.
     */
    public boolean isNear(Point2D p){
        return Math.abs(this.point.getX() - p.getX()) < 5.0 &&
               Math.abs(this.point.getY() - p.getY()) < 5.0;
    }

    /**
     * Checks if another point is near from this one.
     * @param x the X coordinate of the point to check.
     * @param y the Y coordinate of the point to check.
     * @return true, when both points are near according JPoint threshold.
     */
    @Override
    public boolean contains(double x, double y) {
        return this.contains(new Point2D.Double(x,y));
    }

    /**
     * Checks if another point is near from this one.
     * @param p point to check.
     * @return true, when both points are near according JPoint threshold.
     */
    @Override
    public boolean contains(Point2D p) {
        return isNear(p);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return design.intersects(x, y, w, h);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean intersects(Rectangle2D r) {
        return design.intersects(r);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean contains(double x, double y, double w, double h) {
        return false;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean contains(Rectangle2D r) {
        return false;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return design.getPathIterator(at);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return design.getPathIterator(at, flatness);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public JRectangle getEnclosingRectangle() {
        return new JRectangle((int)this.point.getX()-5, (int)this.point.getY()-5, 10, 10);
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
    
    /**
     * Optains the position point of the shape.
     * @return A Point2D object with the shape position.
     */
    public Point2D getPoint(){
        return this.point;
    }
    
          
}
