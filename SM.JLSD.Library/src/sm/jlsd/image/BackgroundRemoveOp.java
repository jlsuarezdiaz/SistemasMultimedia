

package sm.jlsd.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Class BackgroundRemoveOp.
 * A BufferedImageOp that takes a color and a tolerance level, and creates a new RGBA image
 * where the pixels near to the given color for the tolerance level given are made transparent.
 * The tolerance level measures a maximum distance (max of the absolute difference of the components) 
 * in the pixel vector.
 * 
 * @author jlsuarezdiaz
 */
public class BackgroundRemoveOp extends BufferedImageOpAdapter{
    /**
     * Tolerance level.
     */
    private int threshold;
    
    /**
     * Removing color.
     */
    private Color color;
    
    /**
     * Constructor. Initializes the removing color to white.
     * @param threshold Tolerance level.
     */
    public BackgroundRemoveOp(int threshold){
        this.threshold = threshold;
        this.color = Color.WHITE;
    }
    
    /**
     * Constructor.
     * @param threshold Tolerance level.
     * @param c Removing color.
     */
    public BackgroundRemoveOp(int threshold, Color c){
        this.threshold = threshold;
        this.color = c;
    }

    /**
     * Applies the filter to the given image.
     * @param src Image to filter.
     * @param dest Image where the filter will be applied. If null, a new BufferedImage will be created.
     * @return Filtered image.
     * 
     * WARNING: dest image, if provided, must be of the same size as src, and be of the tipe TYPE_INT_ARGB.
     */
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if(src == null){
            throw new NullPointerException("src image is null.");
        }
        if(dest == null){
            dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        }
        else{
            if(src.getWidth() != dest.getWidth() || src.getHeight() != dest.getHeight()){
                throw new IllegalArgumentException("src and dest don't have the same size.");
            }
            if(dest.getType() != BufferedImage.TYPE_INT_ARGB){
                throw new IllegalArgumentException("Dest image must be ARGB.");
            }
        }
        
        WritableRaster raster = dest.getRaster();
        
        for(int x = 0; x < src.getWidth(); x++){
            for(int y = 0; y < src.getHeight(); y++){
                int rgb = src.getRGB(x, y);
                Color pix = new Color(rgb);
                int red = pix.getRed();
                int green = pix.getGreen();
                int blue = pix.getBlue();
                int alpha = 255;
                
                if(Math.abs(red-color.getRed()) <= threshold &&
                   Math.abs(green-color.getGreen()) <= threshold &&
                   Math.abs(blue-color.getBlue()) <= threshold){
                    alpha = 0;
                }
                
                raster.setPixel(x, y, new int[]{red,green,blue,alpha});
                
                
                
                
                
            }
        }
        return dest;
    }
    
}
