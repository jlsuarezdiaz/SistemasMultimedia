
package sm.jlsd.image;

import java.awt.image.Kernel;
import sm.image.KernelProducer;

/**
 * Class JKernelProducer.
 * A library with functions to create different kernels for convolution.
 * @author jlsuarezdiaz
 */
public class JKernelProducer{
    
    /**
     * Creates an average kernel.
     * @param width kernel matrix width.
     * @param height kernel matrix height.
     * @return An (width)x(height) average kernel
     */
    public static Kernel createAverageKernel(int width, int height){
        int size = width*height;
        float cell_value = 1.0f/size;
        float matrix[] = new float[size];
        for(int i = 0; i < size; i++){
            matrix[i] = cell_value;
        }
        return new Kernel(width, height, matrix);
    }
}
