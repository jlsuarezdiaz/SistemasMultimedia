
package sm.jlsd.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import sm.image.BufferedImageOpAdapter;

/**
 * Class UmbralizacionOp.
 * A BufferedImageOp for threshold operation based on color.
 * @author jlsuarezdiaz
 */
public class UmbralizacionOp extends BufferedImageOpAdapter{
    /**
     * Threshold.
     */
    private int threshold;
    
    /**
     * Color.
     */
    private Color color;
    
    /**
     * Constructor. Sets default color to black.
     * @param threshold Threshold.
     */
    public UmbralizacionOp(int threshold){
        this.threshold = threshold;
        this.color = Color.BLACK;
    }
    
    /**
     * Constructor.
     * @param threshold Threshold.
     * @param c Color.
     */
    public UmbralizacionOp(int threshold, Color c){
        this.threshold = threshold;
        this.color = c;
    }

    /**
     * Applies the filter to the given image.
     * @param src Image to filter.
     * @param dest Image where the filter will be applied. If null, a new BufferedImage will be created.
     * @return Filtered image.
     * 
     */
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if(src == null){
            throw new NullPointerException("src image is null.");
        }
        if(dest == null){
            dest = createCompatibleDestImage(src, null);
        }
        else{
            if(src.getWidth() != dest.getWidth() || src.getHeight() != dest.getHeight()){
                throw new IllegalArgumentException("src and dest don't have the same size.");
            }
        }
        
        
        
        for(int x = 0; x < src.getWidth(); x++){
            for(int y = 0; y < src.getHeight(); y++){
                Color pix = new Color(src.getRGB(x, y));
                
                int avg_intensity = (pix.getRed()+pix.getGreen()+pix.getBlue())/3;
                
                if(avg_intensity >= threshold){
                    dest.setRGB(x, y, Color.WHITE.getRGB());
                }
                else{
                    dest.setRGB(x, y, color.getRGB());
                }
                
                
            }
        }
        return dest;
    }
    
}
