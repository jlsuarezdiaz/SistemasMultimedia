
package paint2d;

import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JFrame;
import sm.jlsd.graphics.PaintTool;
import sm.jlsd.ui.Canvas2D;

/**
 * Class MainView.
 * An abstract class that represents any view for the media model.
 * @author jlsuarezdiaz
 */
public abstract class MainView extends JFrame{
    
    /**
     * Sets the view tools according to the canvas values.
     * @param canvas Canvas.
     */
    public abstract void setImageView(Canvas2D canvas);
    
    /**
     * Sets the audio and video tools according to the player values.
     * @param playing True if player is playing, false otherwise.
     */
    public abstract void setAudioVideoView(boolean playing);
    
    /**
     * Sets the audio and video tools according to the player values.
     * @param playing True if player is playing, false otherwise.
     * @param current The clip current position, in miliseconds.
     * @param duration The clip duration, in miliseconds.
     */
    public abstract void setAudioVideoView(boolean playing, long current, long duration);
    
    /**
     * Sets the audio and video tools according to the player values.
     * @param playing True if player is playing, false otherwise.
     * @param position Relative position of the clip, from 0 to 1.
     */
    public abstract void setAudioVideoView(boolean playing, float position);
    
    /**
     * Sets the paint tool in the state bar of the view.
     * @param t PaintTool
     */
    public abstract void setPaintToolStateBar(PaintTool t);
    
    /**
     * Sets the mouse points in the state bar.
     * @param p0 Current mouse position, or mouse pressed position if mouse is pressed, or null.
     * @param p1 Current mouse position if the mouse is pressed, or null.
     */
    public abstract void setPointsStateBar(Point p0, Point p1);
    
    /**
     * Sets the mouse pointer pixel color in the state bar.
     * @param rgb Integer representing the rgb color.
     */
    public abstract void setColorStateBar(Integer rgb);
    
    
}
