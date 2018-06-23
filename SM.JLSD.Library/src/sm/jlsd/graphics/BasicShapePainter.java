/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jlsd.graphics;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Class BasicModifierPainter.
 * A class with a default implementation for painting modifiers. 
 * This class accepts a JShape and provides a list of attributes and functions to paint the shape with the setted attributes.
 * @author jlsuarezdiaz
 */
public class BasicShapePainter extends ShapePainter implements BasicAttributePainter{
    
    // OPTIONS
    
    // -- Filling -- //
    
    /**
     * The 'no filling' mode.
     */
    public static final int FILL_EMPTY = 0;
    
    /**
     * The filling mode with a single solid color.
     */
    public static final int FILL_SINGLE_COLOR = 1;
    
    /**
     * The filling mode with a vertical gradient.
     */
    public static final int FILL_VERTICAL_GRADIENT = 2;
    
    /**
     * The filling mode with an horizontal gradient.
     */
    public static final int FILL_HORIZONTAL_GRADIENT = 3;
    
    /**
     * The filling mode with a diagonal gradient on the direction of the main diagonal (\).
     */
    public static final int FILL_MAIN_DIAGONAL_GRADIENT = 4;
    
    /**
     * The filling mode with a diagonal gradient on the direction of the secondary diagonal (/).
     */
    public static final int FILL_SECOND_DIAGONAL_GRADIENT = 5;
    
    /**
     * The filling mode with a radial gradient that is repeating periodically.
     */
    public static final int FILL_EQUIRADIAL_GRADIENT = 6;
    
    /**
     * The filling mode with a basic texture image.
     */
    public static final int FILL_TEXTURE_BASIC = 7;
    
    // -- Stroke -- //
    @Deprecated
    public static final int NO_STROKE = 0;
    
    @Deprecated
    public static final int STROKE_CONTINUOUS = 1;
    
    @Deprecated
    public static final int STROKE_DISCONTINUOUS = 2;
    
    /**
     * Flattening renderization.
     */
    private static final RenderingHints flattenRender = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
    /**
     * Stroke for selected items.
     */
    private static final Stroke selectionStroke = new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 1.0f,new float[]{5.0f, 5.0f},0.0f);
    
    /**
     * Basic texture image.
     */
    private static BufferedImage basic_texture;
    static{
        try{
            basic_texture = ImageIO.read(BasicAttributePainter.class.getResource("/sm/jlsd/resources/basic_texture.jpg"));
        }
        catch(Exception ex){
            System.err.println("WARNING: Basic texture could not be loaded.");
            basic_texture = null;
        }
    }
    
    // ATTRIBUTES
    
    /**
     * Primary color for the painter.
     */
    protected Color primaryColor = Color.BLACK;    //Dependencies: fill
    
    /**
     * Secondary color for the painter.
     */
    protected Color secondaryColor = Color.WHITE;  //Dependencies: fill
    
    /**
     * Stroke thickness.
     */
    protected float thickness = 1.0f;              //Dependencies: stroke
    
    /**
     * Stroke type.
     */
    protected BasicStrokeType stroke_type = BasicStrokeType.STROKE_CONTINUOUS; //Dependencies: stroke
    
    /**
     * Stroke for the painter.
     */
    protected Stroke stroke = new BasicStroke(thickness);
    
    /**
     * Filling mode of the painter. Allowed values are defined in BasicShapePainter:
     * FILL_EMPTY, FILL_SINGLE_COLOR, FILL_HORIZONTAL_GRADIENT, FILL_VERTICAL_GRADIENT,
     * FILL_MAIN_DIAGONAL_GRADIENT, FILL_SECOND_DIAGONAL_GRADIENT, FILL_EQUIRADIAL_GRADIENT.
     */
    protected int fillMode = FILL_EMPTY;          //Dependencies: fill
    
    /**
     * Filling mode for the painter.
     */
    protected Paint fill = null;
    
    /**
     * Transparency level for the painter.
     */
    protected float transparency = 1.0f;    //Dependencies: composition
    
    /**
     * Painter's composition rules.
     */
    protected Composite composition = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency);
    
    /**
     * Rotation degree (in radians) for the painter.
     */
    protected double rotation = 0.0f; //Dependencies: transform.
    
    /**
     * Scale level for the painter.
     */
    protected double scale = 1.0f; //Dependencies: scale.
    
    /**
     * Painter's affine transform.
     */
    protected AffineTransform transform = new AffineTransform();
    
    /**
     * Painter's font.
     */
    protected Font font;
    
    /**
     * Flattening activation for the painter.
     */
    protected boolean flatten = false;        //Dependencies: render
    //protected RenderingHints render;
    
    /**
     * Edit mode setting for the painter.
     */
    protected boolean editing = false;
    
    /**
     * Auxiliar variable to keep the graphics saved transform.
     */
    protected AffineTransform savedTransform = null;
    
    /**
     * Constructor.
     * @param s JShape associated to the painter. 
     */
    public BasicShapePainter(JShape s){
        super(s);
    }

    /**
     * {@inheritDoc }
     */
    public void paintForCommon(Graphics2D g2d){
        savedTransform = g2d.getTransform();
        g2d.transform(transform);
        if(editing){
            JRectangle border = shape.getEnclosingRectangle();
            g2d.setPaint(primaryColor); //Para que la selección sea del mismo color que el borde.
            g2d.setStroke(selectionStroke);
            g2d.draw(border);
        }
        if(stroke_type != BasicStrokeType.NO_STROKE){
            g2d.setStroke(stroke);
        }
        g2d.setComposite(composition);
        
        if(flatten){
            g2d.setRenderingHints(flattenRender);
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void paintForFill(Graphics2D g2d) {
        if(fillMode != FILL_EMPTY){
            if(fillMode != FILL_SINGLE_COLOR) updateFill(); //Necesario, pues el gradiente depende de la forma de la figura en cada instante.
            g2d.setPaint(fill);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void paintForBorder(Graphics2D g2d) {
        g2d.setPaint(primaryColor);    
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void paintShape(Graphics2D g2d) {
        if(stroke_type != BasicStrokeType.NO_STROKE){
            g2d.draw(this.shape);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void unset(Graphics2D g2d) {
        g2d.setTransform(savedTransform);
    }
    
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void paintFill(Graphics2D g2d) {
        if(fillMode != FILL_EMPTY)
            g2d.fill(this.shape);
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public void setPrimaryColor(Color c){
        this.primaryColor = c;
        updateFill();
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public Color getPrimaryColor(){
        return this.primaryColor;
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public void setSecondaryColor(Color c){
        this.secondaryColor = c;
        updateFill();
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public Color getSecondaryColor(){
        return this.secondaryColor;
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public void setThickness(float val){
        this.thickness = val;
        updateStroke();
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public float getThickness(){
        return this.thickness;
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public void setStrokeType(BasicStrokeType s){
        this.stroke_type = s;
        updateStroke();
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public BasicStrokeType getStrokeType(){
        return this.stroke_type;
    }
    
    /**
     * {@inheritDoc } 
     * 
     * Allowed values are defined in BasicShapePainter:
     * FILL_EMPTY, FILL_SINGLE_COLOR, FILL_HORIZONTAL_GRADIENT, FILL_VERTICAL_GRADIENT,
     * FILL_MAIN_DIAGONAL_GRADIENT, FILL_SECOND_DIAGONAL_GRADIENT, FILL_EQUIRADIAL_GRADIENT.
     */
    @Override
    public void setFillMode(int f){
        this.fillMode = f;
        updateFill();
    }
    
    /**
     * {@inheritDoc } 
     * 
     * Allowed values are defined in BasicShapePainter:
     * FILL_EMPTY, FILL_SINGLE_COLOR, FILL_HORIZONTAL_GRADIENT, FILL_VERTICAL_GRADIENT,
     * FILL_MAIN_DIAGONAL_GRADIENT, FILL_SECOND_DIAGONAL_GRADIENT, FILL_EQUIRADIAL_GRADIENT.
     */
    @Override
    public int getFillMode(){
        return this.fillMode;
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public void setTransparency(float f){
        this.transparency = f;
        updateComposition();
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public float getTransparency(){
        return this.transparency;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void setFlatten(boolean b){
        this.flatten = b;
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public boolean getFlatten(){
        return this.flatten;
    }
    
    /**
     * {@inheritDoc } 
     * 
     * The scaling takes as center the center of the shape's enclosing rectangle.
     */
    @Override
    public void setRotation(double f){
        this.rotation = f;
        updateTransform();
    }
    
    /**
     * {@inheritDoc } 
     * 
     * The rotation takes as center the center of the shape's enclosing rectangle.
     */
    @Override
    public double getRotation(){
        return this.rotation;
    }
    
    /**
     * {@inheritDoc }
     * 
     * The scaling takes as center the center of the shape's enclosing rectangle.
     */
    @Override
    public void setScale(double f){
        this.scale = f;
        updateTransform();
    }
    
    /**
     * {@inheritDoc } 
     * 
     * The scaling takes as center the center of the shape's enclosing rectangle.
     */
    @Override
    public double getScale(){
        return this.scale;
    }
    
    /**
     * {@inheritDoc } 
     */
    public boolean isEditing(){
        return this.editing;
    }
    
    /**
     * {@inheritDoc } 
     */
    public void setEditing(boolean b){
        this.editing = b;
    }
    
    /**
     * Updates the fill attribute according to the current fill mode and colors.
     */
    private void updateFill(){
        JRectangle rect = shape.getEnclosingRectangle();
        switch(this.fillMode){
            case FILL_EMPTY:
                this.fill = null;
                break;
            case FILL_SINGLE_COLOR:
                this.fill = secondaryColor;
                break;
            case FILL_HORIZONTAL_GRADIENT:
                this.fill = new GradientPaint(rect.upLeftCorner(), primaryColor, rect.upRightCorner(), secondaryColor);
                break;
            case FILL_VERTICAL_GRADIENT:
                this.fill = new GradientPaint(rect.upLeftCorner(), primaryColor, rect.downLeftCorner(), secondaryColor);
                break;
            case FILL_MAIN_DIAGONAL_GRADIENT:
                this.fill = new GradientPaint(rect.upLeftCorner(), primaryColor, rect.downRightCorner(), secondaryColor);
                break;
            case FILL_SECOND_DIAGONAL_GRADIENT:
                this.fill = new GradientPaint(rect.downLeftCorner(), primaryColor, rect.upRightCorner(), secondaryColor);
                break;
            case FILL_EQUIRADIAL_GRADIENT:
                this.fill = new RadialGradientPaint(new Point2D.Double(rect.getCenterX(),rect.getCenterY()),20.0f,new float[]{0.2f,1.0f},new Color[]{primaryColor,secondaryColor},MultipleGradientPaint.CycleMethod.REPEAT);
                break;
            case FILL_TEXTURE_BASIC:
                try{
                    if(basic_texture != null) this.fill = new TexturePaint(basic_texture, rect);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                    this.fill = null;
                }
                break;
        }
    }
    
    /**
     * Updates the stroke according to the current stroke type and thickness.
     */
    private void updateStroke(){
        this.stroke = this.stroke_type.getStroke(this.thickness);
    }
    
    /**
     * Updates the composition according to the current transparency.
     */
    private void updateComposition(){
        this.composition = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,this.transparency);
    }

    /**
     * Updates the transform according to the current rotation ans scale values.
     */
    private void updateTransform(){
        JRectangle rect = shape.getEnclosingRectangle();
        double cx = rect.getCenterX(), cy = rect.getCenterY();
        transform.setToIdentity();
        
        transform.translate(cx, cy);
        //Rotation
        transform.rotate(rotation);
        //Scaling
        transform.scale(scale, scale);
        transform.translate(-cx, -cy);
        
        //Como la rotación y el escalado son sobre el mismo centro, conmutan.
    }
    
    
    
}
