/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jlsd.graphics;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * Class JPolygon.
 * A JGeneralPath that allows the dynamically creation of polygons, in an undefined number of steps.
 * @author jlsuarezdiaz
 */
public class JPolygon extends JGeneralPath implements JShape {
    
    /**
     * Auxiliar path (Path2D does not allow access to the path segments).
     */
    private JGeneralPath auxPath = null;
    
    /**
     * Constructor.
     * @param p0 Initial vertex.
     */
    public JPolygon(Point p0){
        super(new JPoint(p0));
        
    }
    
    /**
     * Updates the auxiliar path during construction.
     * @param p Update point.
     */
    private void updateAuxPath(Point2D p){
        auxPath = new JGeneralPath(this) {
            @Override
            public void updateShape(Point2D p0, Point2D p1) {}
        };
        auxPath.lineTo(p.getX(),p.getY());     
    }


    /**
     * {@inheritDoc }
     * 
     * The number of construction steps is not defined. Each different step will construct a new polygon edge.
     */
    @Override
    public void updateShape(Point2D p0, Point2D p1) {
        updateAuxPath(p1);
    }


    
    /**
     * {@inheritDoc }
     * 
     * A commit will confirm a polygon edge and updateShape will start adding a new one.
     */
    @Override
    public boolean commitShape(){
        if(auxPath != null){
            this.lineTo(auxPath.getCurrentPoint().getX(),auxPath.getCurrentPoint().getY());
        }
        this.auxPath = null;
        return false;
    }

    /**
     * {@inheritDoc }
     * 
     * Confirming thr JPolygon will close it, adding a line between the last and the first vertex.
     */
    @Override
    public void confirmShape() {
        this.closePath();
        this.auxPath = null;
    }
    
    /**
     * {@inheritDoc } 
     */
    public void paint(Graphics2D g){
        if(auxPath != null){
            auxPath.paint(g);
        }
        super.paint(g);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public JRectangle getEnclosingRectangle() {
        return new JRectangle(this.getBounds());
    }

    
}
