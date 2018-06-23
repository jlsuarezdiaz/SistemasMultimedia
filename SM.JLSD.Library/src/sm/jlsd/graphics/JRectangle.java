
package sm.jlsd.graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

/**
 * Class JRectangle.
 * An JShape extendion for the {@link java.awt.Rectangle } class.
 * Adds to the Rectangle class the functionality to construct a rectangle dinamially in a single step.
 * @author jlsuarezdiaz
 */
public class JRectangle extends Rectangle implements JShape{
    
    /**
     * Painter.
     */
    private BasicShapePainter painter = new BasicShapePainter(this);

    /**
     * Empty constructor. Constructs a new JRectangle initialized to location (0,0) and size (0,0).
     */
    public JRectangle() {
        super();
    }

    /**
     * Constructs a new JRectangle, initialized to match the values of the specified Rectangle.
     * @param r the Rectangle from which to copy initial values to a newly constructed Rectangle
     */
    public JRectangle(Rectangle r) {
        super(r);
    }

    /**
     * Constructs a new JRectangle whose upper-left corner is specified as (x,y) and whose width and height are specified by the arguments of the same name.
     * @param x the specified X coordinate.
     * @param y the specified Y coordinate.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     */
    public JRectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * Constructs a new JRectangle whose upper-left corner is at (0, 0) in the coordinate space, and whose width and height are specified by the arguments of the same name.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     */
    public JRectangle(int width, int height) {
        super(width, height);
    }

    /**
     * Constructs a new Rectangle whose upper-left corner is specified by the Point argument, and whose width and height are specified by the Dimension argument.
     * @param p a Point that is the upper-left corner of the Rectangle.
     * @param d a Dimension, representing the width and height of the Rectangle.
     */
    public JRectangle(Point p, Dimension d) {
        super(p, d);
    }

    /**
     * Constructs a new Rectangle whose upper-left corner is the specified Point, and whose width and height are both zero.
     * @param p a Point that is the upper-left corner of the Rectangle.
     */
    public JRectangle(Point p) {
        super(p);
    }

    /**
     * Constructs a new Rectangle whose top left corner is (0, 0) and whose width and height are specified by the Dimension argument.
     * @param d a Dimension, representing the width and height of the Rectangle.
     */
    public JRectangle(Dimension d) {
        super(d);
    }


    /**
     * {@inheritDoc }
     */
    @Override
    public void updateShape(Point2D p0, Point2D p1) {
        this.setFrameFromDiagonal(p0, p1);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void move(Point2D vtrans) {
        vtrans = JShape.scaleRotate(vtrans, painter.getRotation(), painter.getScale());
        super.setLocation((int)this.getX() + (int)vtrans.getX(), (int)this.getY() + (int)vtrans.getY());
    }
    
    /**
     * @return Up left corner of the rectangle.
     */
    public Point upLeftCorner(){
        return new Point(x,y);
    }
    
    /**
     * @return Up right corner of the rectangle.
     */
    public Point upRightCorner(){
        return new Point(x+width,y);
    }
    
    /**
     * @return Down left corner of the rectangle.
     */
    public Point downLeftCorner(){
        return new Point(x,y+height);
    }
    
    /**
     * @return Down right corner of the rectangle.
     */
    public Point downRightCorner(){
        return new Point(x+width,y+height);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public JRectangle getEnclosingRectangle() {
        return new JRectangle(x-1, y-1, width+2, height+2);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void paint(Graphics2D g) {
        painter.paint(g);
    }

    /**
     * {@inheritDoc } 
     */
    @Override
    public BasicShapePainter getPainter() {
        return this.painter;
    }
    
    
    
}
