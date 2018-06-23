
package sm.jlsd.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import sm.image.BufferedImageOpAdapter;

/**
 * Class FireEffectOp.
 * A BufferedImageOp that transforms the image using only hot color combinations.
 * @author jlsuarezdiaz
 */
public class FireEffectOp extends BufferedImageOpAdapter{
    /**
     * Constructor.
     */
    public FireEffectOp(){}

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
                
                int sred= Math.min(255, (int) (1.0*pix.getRed() + 0.666*pix.getGreen() + 0.666*pix.getBlue()));
                int sgreen = Math.min(255, (int) (0.537*pix.getGreen() + 0.461*pix.getBlue()));
                int sblue = 0;
                           
                dest.setRGB(x, y, new Color(sred, sgreen, sblue).getRGB());
            }
        }
        return dest;
    }
}
