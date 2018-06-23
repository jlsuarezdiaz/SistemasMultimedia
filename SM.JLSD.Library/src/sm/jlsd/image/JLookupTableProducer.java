
package sm.jlsd.image;

import java.awt.image.ByteLookupTable;
import java.awt.image.LookupTable;

/**
 * Class JKernelProducer.
 * A library with functions to create different LookupTables for Lookup filter.
 * @author jlsuarezdiaz
 */
public class JLookupTableProducer {
    
    /**
     * Creates a lookup table for the sine function.
     * @param w wave frequency.
     * @return Lookup table for the sine function.
     */
    public static LookupTable sine(double w){
        double K = 255.0;
        byte lt[] = new byte[256];
        for(int i = 0; i < 256; i++){
            lt[i] = (byte)(K*Math.sin(Math.toRadians(w*i/255.0)));
        }
        ByteLookupTable slt = new ByteLookupTable(0, lt);
        return slt;
    }
    
    /**
     * Creates a lookup table for the arc tangent function.
     * The arc tangent graphic is translated to be (anti-)symmetric in the interval [0,255].
     * @return Lookup table for the arc tangent function.
     */
    public static LookupTable atan(){
        double min = Math.atan(-128.0);
        double max = Math.atan(127.0);
        double K = 255.0/(max - min);
        
        byte lt[] = new byte[256];
        for(int i = 0; i < 256; i++){
            lt[i] = (byte)(K*(Math.atan(i - 128.0) - min));
        }
        ByteLookupTable slt = new ByteLookupTable(0, lt);
        return slt;
    }
    
    /**
     * Creates a lookup table for the gaussian function, G(x) = exp(-alpha*x^2)
     * @param alpha An exponent factor. Must be positive.
     * @return Lookup table for the gaussian function.
     */
    public static LookupTable gaussian(double alpha){
        if(alpha <= 0){
            throw new IllegalArgumentException("alpha must be positive.");
        }
        alpha = alpha/(128.0*128.0); //Factor de corrección. Traslada la gráfica de la campana sobre el [-1,1] a una gráfica sobre el [-128,128]
        double min = Math.exp(-alpha*128.0*128.0);
        double max = 1.0;
        double K = 255.0/(max - min);
        
        byte lt[] = new byte[256];
        for(int i = 0; i < 256; i++){
            double x = i - 128.0;
            lt[i] = (byte)(K*(Math.exp(-alpha*x*x) - min));
        }
        ByteLookupTable slt = new ByteLookupTable(0,lt);
        return slt;
    }
}
