
package sm.jlsd.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Abstract class ShapePainter.
 * An abstract class that defines functions to draw a JShape object.
 * @author jlsuarezdiaz
 */
public abstract class ShapePainter {
    protected JShape shape;
    
    /**
     * Constructor.
     * @param s JShape to be painted.
     */
    public ShapePainter(JShape s){
        this.shape = s;
    }
    
    /**
     * Sets the common painting attributes (border and filling) to paint the JShape.
     * @param g2d Graphics2D object to set.
     */
    public abstract void paintForCommon(Graphics2D g2d);
    
    /**
     * Sets the filling specific attributes to paint the JShape.
     * @param g2d Graohics2D object to set.
     */
    public abstract void paintForFill(Graphics2D g2d);
    
    /**
     * Sets the border specific attributes to paint the JShape. 
     * @param g2d Graphics2D object to set.
     */
    public abstract void paintForBorder(Graphics2D g2d);
    
    /**
     * Paints the border of the JShape
     * @param g2d Graphics2D object to be painted.
     */
    public abstract void paintShape(Graphics2D g2d);
    
    /**
     * Paints the filling of the JShape.
     * @param g2d Graphics2D object to be painted.
     */
    public abstract void paintFill(Graphics2D g2d);
    
    /**
     * Restores the before-painting configuration of the Graphics2D object.
     * This method must be called after calling the paintForCommon function.
     * @param g2d Graohics2D object to restore.
     */
    public abstract void unset(Graphics2D g2d);
    
    /**
     * Paints the complete JShape (attributes, shape and filling) on the given Graphics2D object.
     * @param g Graphics2D object to be painted.
     */
    public void paint(Graphics2D g){
        Graphics2D g2d = (Graphics2D) g.create();
        paintForCommon(g2d);
        paintForFill(g2d);
        paintFill(g2d);
        paintForBorder(g2d);
        paintShape(g2d);
        unset(g2d);
        g2d.dispose();
    }
    
    
}
