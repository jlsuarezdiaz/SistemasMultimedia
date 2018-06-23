
package sm.jlsd.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;
import sm.image.ImageTools;
import sm.image.KernelProducer;

/**
 * Class SobelOp.
 * Gradient filter for border detections.
 * @author jlsuarezdiaz
 */
public class SobelOp extends BufferedImageOpAdapter{
    
    /**
     * Constructor.
     */
    public SobelOp(){}
    
    /**
     * Applies the filter to the given image.
     * @param src Image to filter.
     * @param dest Image where the filter will be applied. If null, a new BufferedImage will be created.
     * @return Filtered image.
     * 
     */
    public BufferedImage filter(BufferedImage src, BufferedImage dest){
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
        
        Kernel kgrad_x = KernelProducer.createKernel(KernelProducer.TYPE_SOBELX_3x3);
        Kernel kgrad_y = KernelProducer.createKernel(KernelProducer.TYPE_SOBELY_3x3);
        
        ConvolveOp cop_x = new ConvolveOp(kgrad_x);
        ConvolveOp cop_y = new ConvolveOp(kgrad_y);
        
        BufferedImage convX = cop_x.filter(src, null);
        BufferedImage convY = cop_y.filter(src, null);
        
        WritableRaster srcRaster_x = convX.getRaster();
        WritableRaster srcRaster_y = convY.getRaster();
        
        //WritableRaster destRaster = dest.getRaster();
        
        for(int x = 0; x < src.getWidth(); x++){
            for(int y = 0; y < src.getHeight(); y++){
                int [] pixelComp_x = null;
                int [] pixelComp_y = null;
                
                pixelComp_x = srcRaster_x.getPixel(x, y, pixelComp_x);
                pixelComp_y = srcRaster_y.getPixel(x, y, pixelComp_y);
                
                int grad_x, grad_y;
                grad_x = grad_y = 0;
                
                for(int i = 0; i < pixelComp_x.length; i++){
                    grad_x += pixelComp_x[i];
                    grad_y += pixelComp_y[i];
                }
                
                int module = (int)Math.hypot(grad_x, grad_y);
                module = ImageTools.clampRange(module, 0, 255);
                
                dest.setRGB(x, y, new Color(module, module, module).getRGB());
            }
        }
        return dest;
        
    }
}
