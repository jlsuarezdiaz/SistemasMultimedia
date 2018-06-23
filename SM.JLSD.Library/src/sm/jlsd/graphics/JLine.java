
package sm.jlsd.graphics;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Class JEllipse.
 * A Jshape extension for the {@link java.awt.geom.Line2D} class.
 * Allows dynamic construction of Line2D in one construction step.
 * @author jlsuarezdiaz
 */
public class JLine extends Line2D.Double implements JShape{
    /**
     * Painter.
     */
    private BasicShapePainter painter = new BasicShapePainter(this);

    /**
     * Empty constructor. Constructs and initializes a line with coordinates from (0,0) to (0,0).
     */
    public JLine() {
        super();
    }

    /**
     * Constructs and initializes a JLine with the specified coordinates.
     * @param x1 the X coordinate of the start point.
     * @param y1 the Y coordinate of the start point.
     * @param x2 the X coordinate of the end point.
     * @param y2 the Y coordinate of the end point.
     */
    public JLine(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
    }

    /**
     * Constructs and initializes a line from the specified Point2D objects.
     * @param p1 the start Point2D of this line segment.
     * @param p2 the end Point2D of this line segment.
     */
    public JLine(Point2D p1, Point2D p2) {
        super(p1, p2);
    }
    
    /**
     * Tests if the given point is near to the line segment.
     * @param p Point2D to test.
     * @return true, if and only if the point is near the line segment.
     */
    public boolean isNear(Point2D p){
        return this.ptLineDist(p)<=2.0;
    }
    
    /**
     * Tests if the given point is near to the line segment.
     * @param p Point2D to test.
     * @return true, if and only if the point is near the line segment.
     */
    @Override
    public boolean contains(Point2D p){
        return isNear(p);
    }
    
    /**
     * Tests if the given point is near to the line segment.
     * @param x the X coordinate of the point to test.
     * @param y the Y coordinate of the point to test.
     * @return true, if and only if the point is near the line segment.
     */
    @Override
    public boolean contains(double x, double y){
        return contains(new Point2D.Double(x,y));
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void updateShape(Point2D p0, Point2D p1){
        this.setLine(p0, p1);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void move(Point2D vtrans) {
        vtrans = JShape.scaleRotate(vtrans, painter.getRotation(), painter.getScale());
        Point2D p1new = new Point2D.Double(this.getX1()+vtrans.getX(),this.getY1()+vtrans.getY());
        Point2D p2new = new Point2D.Double(this.getX2()+vtrans.getX(),this.getY2()+vtrans.getY());
        this.setLine(p1new, p2new);
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
