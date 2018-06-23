
package sm.jlsd.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Class JEllipse.
 * An JShape extendion for the {@link java.awt.geom.Ellipse2D } class.
 * Adds to the Ellipse2D class the functionality to construct a Ellipse dinamially in a single step.
 * @author jlsuarezdiaz
 */
public class JEllipse extends Ellipse2D.Double implements JShape{

    private BasicShapePainter painter = new BasicShapePainter(this);
    
    /**
     * Empty constructor. Constructs a new JEllipse initialized to location (0,0) and size (0,0).
     */
    public JEllipse() {
        super();
    }

    /**
     * Constructs and initializes the Ellipse with the specified coordinates.
     * @param x the X coordinate of the upper-left corner of the framing rectangle.
     * @param y the Y coordinate of the upper-left corner of the framing rectangle.
     * @param w the width of the framing rectangle.
     * @param h the height of the framing rectangle.
     */
    public JEllipse(double x, double y, double w, double h) {
        super(x, y, w, h);
    }
    
    /**
     * Constructs and initializes the Ellipse with the specified coordinates.
     * @param p0 the coordinates of the upper-left corner of the framing rectangle.
     * @param p1 the coordinates of the lower-right corner of the framing rectangle.
     */
    public JEllipse(Point2D p0, Point2D p1){
        super(Math.min(p0.getX(), p1.getX()), Math.min(p0.getY(), p1.getY()), 
              Math.abs(p0.getX()-p1.getX()), Math.abs(p0.getY()-p1.getY()));        
    }
    

    /**
     * {@inheritDoc } 
     * 
     * The function will be constructed in a single step.
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
