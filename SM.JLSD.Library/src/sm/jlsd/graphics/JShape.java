
package sm.jlsd.graphics;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;

/**
 * Interface JShape.
 * An extended interface for the Java AWT Shape.
 * 
 * This interface adds functions to the Shape class that allow the shapes to be created and displayed dinamically,
 * by fixing and initial point and moving a second point around the plane, thanks to the {@link  #updateShape(java.awt.geom.Point2D, java.awt.geom.Point2D)} method.
 * 
 * Also, the interface adds functionality for the shapes that need to be created dinamically in several steps. After updating the shape
 * in some step, the method {@link #commitShape() } will activate the next construction step. If the shape has an undefined number of
 * steps, the method {@link  #confirmShape() } will finish its creation.
 * 
 * JShapes also can be translated easily by a translation vector thanks to the {@link #move(java.awt.geom.Point2D) } function.
 * 
 * Finally, JShapes can also be painted each one with its own attributes. Every JShape contains a BasicShapePainter, whose attributes 
 * can be accessed by the {@link #getPainter() } method, and they can be painted individually, using the {@link #paint(java.awt.Graphics2D) }
 * method defined in this interface.
 * 
 * @author jlsuarezdiaz
 */
public interface JShape extends Shape{
    
    
    /**
     * Updates the shape positions during construction.
     * @param p0 The starting (fixed) point of the shape construction.
     * @param p1 The final point of the shape construction.
     * This function may change its behaviour according to the calling to the commitShape function.
     */
    public void updateShape(Point2D p0, Point2D p1);
    
    /**
     * Commits a shape construction step.
     * A construction step corresponds to a sequence of callings to updateShape moving the points around the plane.
     * Making a commit may change the behaviour of the updateShape function.
     * The default interface implementation assumes that the shape is constructed in a single step.
     * @return True, if the shape is completed. False, otherwise.
     */
    default boolean commitShape(){
        return true;
    }
    
    /**
     * Confirms the creation of a shape.
     * This method only must have an effect in the shapes that doesn't have a fix number of construction steps
     * (that is, the commit method will always return false), so this method will complete the figure.
     * The default interface implementation assumes that the shape is constructed in a fixed number of steps,
     * so the method has no effect.
     */
    default void confirmShape(){}
    
    /**
     * Updates the shape translating it by the specified vector.
     * @param vtrans the coordinates of the translation vector.
     */
    public void move(Point2D vtrans);
    
    /**
     * An utility function to calculate rotations and scalings of vectors.
     * Useful for sinchronization in the move method.
     * @param p A Point2D representing a vector.
     * @param rot Rotation degree (in radians).
     * @param esc Scaling degree.
     * @return The rotated and scaled vector.
     */
    static Point2D scaleRotate(Point2D p, double rot, double esc){
        double cos = Math.cos(rot);
        double sin = Math.sin(rot);
        return new Point2D.Double((p.getX()*cos+p.getY()*sin),(-p.getX()*sin+p.getY()*cos));
    }
    
    /**
     * Obtains a JRectangle that encloses the figure.
     * @return the enclosing JRectangle.
     */
    public JRectangle getEnclosingRectangle();
    
    /**
     * Paints the shape.
     * @param g2d Graphics2D object where the shape will be painted.
     */
    public void paint(Graphics2D g2d);
    
    /**
     * Obtains the painter associated to the shape.
     * @return The shape BasicShapePainter.
     */
    public BasicShapePainter getPainter();
    
    
}
