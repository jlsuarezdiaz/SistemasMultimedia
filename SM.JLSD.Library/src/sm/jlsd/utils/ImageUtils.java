/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jlsd.utils;

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import sm.image.color.GreyColorSpace;

/**
 * Class ImageUtils.
 * A generic class with some image utilities.
 * @author jlsuarezdiaz
 */
public class ImageUtils {
    /**
     * Creates a copy (clone) of an image.
     * @param img Image to clone.
     * @return Copied image.
     */
    public static BufferedImage copyImage(BufferedImage img){
        ColorModel cm = img.getColorModel();
        WritableRaster raster = img.copyData(null);
        boolean alfaPre = img.isAlphaPremultiplied();
        return new BufferedImage(cm,raster,alfaPre,null);
    }
    
    /**
     * Obtains an image associated to one of the bands of another image. Changes in one of the images will be shared by the other one.
     * @param img BufferedImage.
     * @param iBand Integer with the band to obtain.
     * @return Image with the specified band.
     */
    public static BufferedImage getBand(BufferedImage img, int iBand){
        ColorSpace cs = new GreyColorSpace();
        ComponentColorModel cm = new ComponentColorModel(cs, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        
        int bandList[] = {iBand};
        WritableRaster bandRaster = (WritableRaster)img.getRaster().
                createWritableChild(0, 0, img.getWidth(), img.getHeight(),0, 0, bandList);
   
        return new BufferedImage(cm,bandRaster, false, null);
    }
}