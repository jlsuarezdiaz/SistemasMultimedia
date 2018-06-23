
package sm.jlsd.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Class ModularTranslateOp.
 * A BufferedImageOp that transforms each component of an image by adding it a quantity modulo 255.
 * @author jlsuarezdiaz
 */
public class ModularTranslateOp extends BufferedImageOpAdapter{
    private int translation;
    
    /**
     * Constructor
     * @param translation Translation quantity.
     */
    public ModularTranslateOp(int translation){
        this.translation = translation;
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
        
        WritableRaster srcRaster = src.getRaster();
        WritableRaster destRaster = dest.getRaster();
        
        if(srcRaster.getNumBands() != destRaster.getNumBands()){
            throw new IllegalArgumentException("src and dest don't have de same number of bands.");
        }
        
        for(int x = 0; x < srcRaster.getWidth(); x++){
            for(int y = 0; y < srcRaster.getHeight(); y++){
                for(int i = 0; i < srcRaster.getNumBands(); i++){
                    destRaster.setSample(x, y, i, (srcRaster.getSample(x, y, i)+translation)%256);
                }
            }
        }
        return dest;
    }
}
