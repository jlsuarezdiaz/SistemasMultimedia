 
package sm.jlsd.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Class SepiaOp.
 * A buffered image operation that makes a sepia transformation to the image colors.
 * @author jlsuarezdiaz
 */
public class SepiaOp extends BufferedImageOpAdapter{
    
    /**
     * Constructor.
     */
    public SepiaOp(){}

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
                
                int sred= Math.min(255, (int) (0.393*pix.getRed() + 0.769*pix.getGreen() + 0.189*pix.getBlue()));
                int sgreen = Math.min(255, (int) (0.349*pix.getRed() + 0.686*pix.getGreen() + 0.168*pix.getBlue()));
                int sblue = Math.min(255, (int) (0.272*pix.getRed() + 0.534*pix.getGreen() + 0.131*pix.getBlue()));
                
                dest.setRGB(x, y, new Color(sred, sgreen, sblue).getRGB());
            }
        }
        return dest;
    }
    
}
