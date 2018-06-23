
package sm.jlsd.graphics;

import java.awt.Color;

/**
 * Interface BasicAttributePainter.
 * An interface that defines a list of getter and setter methods for a basic painter class.
 * @author jlsuarezdiaz
 */
public interface BasicAttributePainter {
    /**
     * Sets the primary color of the painter.
     * @param c Color to set.
     */
    public void setPrimaryColor(Color c);
    
    /**
     * Obtains the primary color of the painter.
     * @return Painter's primary color.
     */
    public Color getPrimaryColor();
    
    /**
     * Sets the secondary color of the painter.
     * @param c Color to set.
     */
    public void setSecondaryColor(Color c);
    
    /**
     * Obtains the secondary color of the painter.
     * @return Painter's secondary color.
     */
    public Color getSecondaryColor();
    
    /**
     * Sets the stroke thickness of the painter.
     * @param val Thickness to set.
     */
    public void setThickness(float val);
    
    /**
     * Obtains the stroke thickness.
     * @return Stroke thickness.
     */
    public float getThickness();
    
    /**
     * Sets the stroke type of the painter.
     * @param s A BasicStrokeType element indicating stroke type.
     */
    public void setStrokeType(BasicStrokeType s);
    
    /**
     * Obtains the stroke type of the painter.
     * @return A BasicStrokeType object.
     */
    public BasicStrokeType getStrokeType();
    
    /**
     * Sets the fill mode of the painter. Fill modes are defined in the classes that implement BasicAttributePainter.
     * @param f An integer representing the fill mode.
     */
    public void setFillMode(int f);
    
    /**
     * Obtains the painter's fill mode.
     * @return An integer representing the fill mode.
     */
    public int getFillMode();
    
    /**
     * Sets the transparency of the painter.
     * @param f Transparency level, a float from 0.0 to 1.0.
     */
    public void setTransparency(float f);
    
    /**
     * Obtains the transparency of the painter.
     * @return The transparency level, from 0.0 to 1.0.
     */
    public float getTransparency();
    
    /**
     * Sets the flatten mode of the painter.
     * @param b Boolean. If true, shapes will be flattened during painting.
     */
    public void setFlatten(boolean b);
    
    /**
     * Obtains the flatten mode of the painter.
     * @return True, if flatten mode is active, false otherwise.
     */
    public boolean getFlatten();
    
    /**
     * Sets the rotation transform of the painter.
     * @param f Rotation degree, in radians.
     */
    public void setRotation(double f);
    
    /**
     * Obtains the rotation transform of the painter.
     * @return Rotation degree, in radians.
     */
    public double getRotation();
    
    /**
     * Sets the scaling of the painter.
     * @param f Scale.
     */
    public void setScale(double f);
    
    /**
     * Obtains the scaling of the painter.
     * @return Scale.
     */
    public double getScale();
    
    /**
     * Checks if the painter is in edition mode.
     * @return True if painter is in edition mode, false otherwise.
     */
    public boolean isEditing();
    
    /**
     * Sets the edition mode.
     * @param b Boolean indicating whether activating edition mode.
     */
    public void setEditing(boolean b);
}
