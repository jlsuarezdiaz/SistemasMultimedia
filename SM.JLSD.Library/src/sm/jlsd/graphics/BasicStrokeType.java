
package sm.jlsd.graphics;

import java.awt.BasicStroke;
import java.awt.Stroke;

/**
 * Basic Stroke type.
 * An enum that contains some predefined basic strokes and provides functions to automatically generate them, for a given thickness.
 * @author jlsuarezdiaz
 */
public enum BasicStrokeType {
    /**
     * No stroke.
     */
    NO_STROKE{
        @Override
        public Stroke getStroke(float thickness) {
            return null;
        }
        
    },
    
    /**
     * Continuous stroke.
     */
    STROKE_CONTINUOUS{
        @Override
        public Stroke getStroke(float thickness) {
            return new BasicStroke(thickness);
        }
        
    },
    
    /**
     * Discontinuous stroke with long segments and rounded decoration at borders.
     */
    STROKE_DISCONTINUOUS_LARGE_ROUND{
        @Override
        public Stroke getStroke(float thickness) {
            return new BasicStroke(thickness,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER,1.0f,new float[]{15.0f,15.0f},0.0f);
        }
        
    },
    
    /**
     * Discontinuous stroke with long segments and squared decoration at borders.
     */
    STROKE_DISCONTINUOUS_LARGE_SQUARE{
        @Override
        public Stroke getStroke(float thickness) {
            return new BasicStroke(thickness,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_MITER,1.0f,new float[]{15.0f,15.0f},0.0f);
        }
        
    },
    
    /**
     * Discontinuous stroke with long segments and no decoration at borders.
     */
    STROKE_DISCONTINUOUS_LARGE_BUTT{
        @Override
        public Stroke getStroke(float thickness) {
            return new BasicStroke(thickness,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,1.0f,new float[]{15.0f,15.0f},0.0f);
        }
        
    },
    
    /**
     * Discontinuous stroke with short segments and rounded decoration at borders.
     */
    STROKE_DISCONTINUOUS_SMALL_ROUND{
        @Override
        public Stroke getStroke(float thickness) {
            return new BasicStroke(thickness,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER,1.0f,new float[]{5.0f,5.0f},0.0f);
        }
        
    },
    
    /**
     * Discontinuous stroke with short segments and squared decoration at borders.
     */
    STROKE_DISCONTINUOUS_SMALL_SQUARE{
        @Override
        public Stroke getStroke(float thickness) {
            return new BasicStroke(thickness,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_MITER,1.0f,new float[]{5.0f,5.0f},0.0f);
        }
        
    },
    
    /**
     * Discontinuous stroke with short segments and no decoration at borders.
     */
    STROKE_DISCONTINUOUS_SMALL_BUTT{
        @Override
        public Stroke getStroke(float thickness) {
            return new BasicStroke(thickness,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,1.0f,new float[]{5.0f,5.0f},0.0f);
        }
        
    },
     
    /**
     * Discontinuous stroke that combines long and short borders. Usually used to remark symmetry axis.
     */
    STROKE_DISCONTINUOUS_AXIS{
        @Override
        public Stroke getStroke(float thickness) {
            return new BasicStroke(thickness,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER,1.0f,new float[]{15.0f,9.0f,3.0f,9.0f},0.0f);
        }
        
    },
    ;
    
    /**
     * Obtains the stroke associated to the instance.
     * @param thickness Stroke thickness.
     * @return The stroke associated to this enum instance.
     */
    public abstract Stroke getStroke(float thickness);
}
