/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jlsd.graphics;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Class JFreePath.
 * A JShape extending a {@link java.awt.geom.Path2D } allowing to create free designs.
 * This class adds functionality for a single step construction of free paths.
 * @author jlsuarezdiaz
 */
public class JFreePath extends JGeneralPath implements JShape{
    
    /**
     * Painter.
     */
    private BasicShapePainter painter = new BasicShapePainter(this);
    
    /**
     * Constructor.
     * @param p0 Initial point.
     */
    public JFreePath(Point p0){
        super(p0);
    }


    /**
     * {@inheritDoc }
     */
    @Override
    public void updateShape(Point2D p0, Point2D p1) {
        this.lineTo(p1.getX(), p1.getY());
    }

    

    
}
