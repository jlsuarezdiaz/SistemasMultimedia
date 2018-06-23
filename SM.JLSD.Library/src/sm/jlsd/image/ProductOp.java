/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jlsd.image;

import java.awt.image.BufferedImage;
import sm.image.BinaryOp;

/**
 * Class ProductOp.
 * A BinaryOp that multiplies two images.
 * @author jlsuarezdiaz
 */
public class ProductOp extends BinaryOp{
    /**
     * Constructor.
     * @param img One of the factors used in the BinaryOp.
     */
    public ProductOp(BufferedImage img){
        super(img);
    }
    
    /**
     * BinaryOp component function.
     * @param v1 First factor.
     * @param v2 Second factor.
     * @return Product of the factors, truncated to [0,255].
     */
    public int binaryOp(int v1, int v2){
        int rdo = v1*v2;
        if(rdo < 0) rdo = 0;
        else if (rdo > 255) rdo = 255;
        return rdo;
    }
}
