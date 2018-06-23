/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jlsd.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;

/**
 * Class JSun.
 * A JArea that makes a sun figure. It performs a single step construction.
 * @author jlsuarezdiaz
 */
public class JSun extends JArea{
    /**
     * Sun center.
     */
    private Ellipse2D.Double sunCenter;
    
    /**
     * Sun rays.
     */
    private QuadCurve2D.Double rays[];
    
    /**
     * Constructor.
     * @param p0 Initial point.
     */
    public JSun(Point2D p0) {
        super();
        sunCenter = new JEllipse();
        rays = new QuadCurve2D.Double[8];
        this.add(new Area(sunCenter));
        for(int i = 0; i < 8; i++){
            rays[i] = new QuadCurve2D.Double();
            this.add(new Area(rays[i]));
        }
    
    }
    
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void updateShape(Point2D p0, Point2D p1) {
        JRectangle r = new JRectangle();
        r.setFrameFromDiagonal(p0, p1);
        double cx = r.getCenterX(), cy = r.getCenterY();
        Point2D center = new Point2D.Double(cx, cy);
        Point2D sunCorner = new Point2D.Double(cx+(r.getX()-cx)/3,cy+(r.getY()-cy)/3);
        sunCenter.setFrameFromCenter(center, sunCorner);
        
        double rayStartx = sunCorner.getX() - cx;
        double rayStarty = sunCorner.getY() - cy;
        double rayEnd = r.getY() - cy;
        
        this.reset();
        this.add(new Area(sunCenter));
        
        for(int i = 0; i < 8; i++){
            double rx = cx + rayStartx*Math.cos(2*Math.PI*(2*i-1)/16.0f);
            double ry = cy + rayStarty*Math.sin(2*Math.PI*(2*i-1)/16.0f);
            double sx = cx + rayStartx*Math.cos(2*Math.PI*(2*i+1)/16.0f);
            double sy = cy + rayStarty*Math.sin(2*Math.PI*(2*i+1)/16.0f);
            double ex = cx + rayEnd*Math.cos(2*Math.PI*i/8.0f);
            double ey = cy + rayEnd*Math.sin(2*Math.PI*i/8.0f);
            rays[i].setCurve(rx, ry, ex, ey, sx, sy);
            this.add(new Area(rays[i]));
        }      
        
    }
    
}
