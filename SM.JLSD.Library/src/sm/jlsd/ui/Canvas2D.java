
package sm.jlsd.ui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import sm.jlsd.graphics.PaintTool;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import sm.jlsd.graphics.BasicAttributePainter;
import sm.jlsd.graphics.BasicShapePainter;
import sm.jlsd.graphics.BasicStrokeType;
import sm.jlsd.graphics.JArc;
import sm.jlsd.graphics.JCubicCurve;
import sm.jlsd.graphics.JEllipse;
import sm.jlsd.graphics.JFreePath;
import sm.jlsd.graphics.JLine;
import sm.jlsd.graphics.JPoint;
import sm.jlsd.graphics.JPolygon;
import sm.jlsd.graphics.JQuadCurve;
import sm.jlsd.graphics.JRectangle;
import sm.jlsd.graphics.JRoundRectangle;
import sm.jlsd.graphics.JShape;
import sm.jlsd.graphics.JSun;

/**
 * Class Canvas2D.
 * A class to configure and display JShapes.
 * @author jlsuarezdiaz
 */
public class Canvas2D extends javax.swing.JPanel implements BasicAttributePainter{
    
    /**
     * The shapes vector.
     */
    private ArrayList<JShape> shapes = new ArrayList<JShape>();
    
    /**
     * Primary color.
     */
    private Color primaryColor = Color.BLACK;
    
    /**
     * Secondary color.
     */
    private Color secondaryColor = Color.WHITE;
    
    /**
     * Thickness.
     */
    private float thickness = 1.0f;
    
    /**
     * Stroke type.
     */
    private BasicStrokeType stroke_type = BasicStrokeType.STROKE_CONTINUOUS;
    
    /**
     * Fill mode.
     */
    private int fill_mode = BasicShapePainter.FILL_EMPTY;
    
    /**
     * Transparency.
     */
    private float transparency = 1.0f;
    
    /**
     * Flatten mode activation.
     */
    private boolean flatten = false;
    
    /**
     * Rotation degree.
     */
    private double rotation = 0.0;
    
    /**
     * Scale degree.
     */
    private double scale = 1.0;
    
    /**
     * Edit mode activation.
     */
    private boolean editMode = false;
    
    /**
     * Current tool.
     */
    private PaintTool currentTool = PaintTool.POINT;
    
    // Mouse events management
    /**
     * Start position point.
     */
    private Point p0 = null;
    
    /**
     * End position point.
     */
    private Point p1 = null;
    
    /**
     * End position point from last mouse event.
     */
    private Point pOld = null;
    
    /**
     * Selected shape for edition.
     */
    private ArrayList<JShape> selectedShapes = new ArrayList<JShape>();
    
    /**
     * Current shape for creation.
     */
    private JShape currentShape = null;
    
    /**
     * Clip area.
     */
    protected JShape clipArea = null;
    
    /**
     * Clip stroke.
     */
    private static final Stroke clipStroke = new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 1.0f,new float[]{5.0f, 5.0f},0.0f);

    /**
     * Boolean indicating if drawing clip border (to not show it in saved images).
     */
    private boolean drawFrontier = true;
    
    /**
     * Flag for regular drawing mode. Allows drawing squares, circles and vertical, horizontal and 45º lines.
     */
    private boolean regular_flag = false;
    

    /**
     * Creates new form Canvas2D
     */
    public Canvas2D() {
        initComponents();
    }
    
    /**
     * Paints this component and all the JShapes defined on it.s
     * @param g Graphics objecto to paint.
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        Graphics2D g2d = (Graphics2D) g;
        
        if(clipArea != null){
            g2d.setStroke(clipStroke);
            if(drawFrontier) g2d.draw(clipArea);
            g2d.clip(clipArea); 
        }
        
        for(JShape s: shapes){
            s.paint(g2d);
        }
    }
    
    /**
     * Creates a new shape with the current shape set parameters.
     */
    private void createShape(){
        //Shape creation.
        switch(currentTool){
            case POINT:
                //currentShape = new JPoint(p0);
                currentShape = new JFreePath(p0);
                break;
            case LINE:
                currentShape = new JLine(p0, p0);
                break;
            case RECTANGLE:
                currentShape = new JRectangle((Point)p0);
                break;
            case OVAL:
                currentShape = new JEllipse(p0,p0);
                break;
            case ROUND_RECTANGLE:
                currentShape = new JRoundRectangle(p0,new Dimension(0,0),new Dimension(0,0));
                break;
            case QUAD_CURVE:
                currentShape = new JQuadCurve(p0,p0,p0);
                break;
            case CUBIC_CURVE:
                currentShape = new JCubicCurve(p0,p0,p0,p0);
                break;
            case POLYGON:
                currentShape = new JPolygon(p0);
                break;
            case ARC:
                currentShape = new JArc(p0);
                break;
            case AREA_SUN:
                currentShape = new JSun(p0);
                break;
                
        }
        shapes.add(currentShape);
        
        //Shape initial attributes.
        currentShape.getPainter().setPrimaryColor(this.getPrimaryColor());
        currentShape.getPainter().setSecondaryColor(this.getSecondaryColor());
        currentShape.getPainter().setFillMode(this.getFillMode());
        currentShape.getPainter().setThickness(this.getThickness());
        currentShape.getPainter().setStrokeType(this.getStrokeType());
        currentShape.getPainter().setFlatten(this.getFlatten());
        currentShape.getPainter().setTransparency(this.getTransparency());
        //Las transformaciones no son útiles durante la creación, solo para edición.
        //currentShape.getPainter().setRotation(this.getRotation());
        //currentShape.getPainter().setScale(this.getScale());
        
        this.repaint();
    }
    
    /**
     * Updates current shape.
     */
    private void updateShape(){
        if(currentShape != null)
            currentShape.updateShape(p0,p1);
        this.repaint();
    }
    
    /**
     * Obtains the selected shape in a given position.
     * @param p Position to obtain the selected shape.
     * @return Selected shape, or null, if no shape is selected.
     */
    private JShape getSelectedShape(Point2D p){
        for(int i = shapes.size()-1; i >= 0; i--){ //En orden inverso, para que la primera seleccionada sea la última en pintarse
            JShape s = shapes.get(i);
            if(s.contains(p)){
                return s;
            }
        }
        return null;
    }
    
    /**
     * Moves the selected shape according to the translation between consecutive mouse events.
     */
    private void editShape(){
        for(JShape s: selectedShapes){
            if(s != null){
                Point vtrans = new Point(p1.x-pOld.x, p1.y-pOld.y);
                s.move(vtrans);
            }
        }
    }
    
    /**
     * Moves the selected JShapes to the bottom of the canvas.
     */
    public void sendToBottomSelected(){
        for(JShape s: selectedShapes){
            shapes.remove(s);
            shapes.add(0, s);
        }
        this.repaint();
    }
    
    /**
     * Brings the selected JShapes to the top of the canvas.
     */
    public void bringToTopSelected(){
        for(JShape s: selectedShapes){
            shapes.remove(s);
            shapes.add(s);
        }
        this.repaint();
    }
    
    /**
     * Sends the selected JShapes one position back in the canvas.
     */
    public void sendBackSelected(){
        /*for(JShape s: selectedShapes){
            int index = shapes.indexOf(s);
            if(index > 0) Collections.swap(shapes, index, index-1);
        }*/
        for(int i = 1; i < shapes.size(); i++){
            JShape s = shapes.get(i);
            if(s.getPainter().isEditing()){
                Collections.swap(shapes, i, i-1);
            }
        }
        this.repaint();
    }
    
    /**
     * Brings the selected JShapes one position forward in the canvas.
     */
    public void bringForwardSelected(){
        /*
        for(JShape s: selectedShapes){
            int index = shapes.indexOf(s);
            if(index < shapes.size()-1) Collections.swap(shapes,index,index+1);
        }
        */
        for(int i = shapes.size()-2; i >=0; i--){
            JShape s = shapes.get(i);
            if(s.getPainter().isEditing()){
                Collections.swap(shapes, i, i+1);
            }
        }
        this.repaint();
    }
    
    /**
     * Sets the current paint tool
     * @param t PaintTool to set.
     */
    public void setCurrentTool(PaintTool t){
        this.currentTool = t;
    }
    
    /**
     * Obtains the current paint tool.
     * @return Current paint tool. 
     */
    public PaintTool getCurrentTool(){
        return this.currentTool;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void setPrimaryColor(Color c){
        this.primaryColor = c;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public Color getPrimaryColor(){
        return this.primaryColor;
    }
    
    /**
     * Updates the current color of the canvas, or of the selected shapes if editing is activated.
     * @param c Color to set.
     */
    public void updatePrimaryColor(Color c){
        if(!this.editMode){
            setPrimaryColor(c);
        }
        else{
            for(JShape s: selectedShapes){
                if(s != null) s.getPainter().setPrimaryColor(c);
            }
            this.repaint();
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void setSecondaryColor(Color c){
        this.secondaryColor = c;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public Color getSecondaryColor(){
        return this.secondaryColor;
    }
    
    /**
     * Updates the secondary color of the canvas, or of the selected shapes if editing is activated.
     * @param c Color to set.
     */
    public void updateSecondaryColor(Color c){
        if(!this.editMode){
            setSecondaryColor(c);
        }
        else{
            for(JShape s: selectedShapes){
                if(s != null) s.getPainter().setSecondaryColor(c);
            }
            this.repaint();
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void setThickness(float t){
        this.thickness= t;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public float getThickness(){
        return this.thickness;
    }
    
    /**
     * Updates the thickness of the canvas, or of the selected shapes if editing is activated.
     * @param t thickness.
     */
    public void updateThickness(float t){
        if(!this.editMode){
            setThickness(t);
        }
        else{
            for(JShape s: selectedShapes){
                if(s != null) s.getPainter().setThickness(t);
            }
            this.repaint();
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void setStrokeType(BasicStrokeType type){
        this.stroke_type = type;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public BasicStrokeType getStrokeType(){
        return this.stroke_type;
    }
    
    /**
     * Updates the stroke type of the canvas, or of the selected shapes if editing is activated.
     * @param type Stroke type.
     */
    public void updateStrokeType(BasicStrokeType type){
        if(!this.editMode){
            setStrokeType(type);
        }
        else{
            for(JShape s: selectedShapes){
                if(s != null) s.getPainter().setStrokeType(type);
            }
            this.repaint();
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void setTransparency(float f){
        this.transparency = f;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public float getTransparency(){
        return this.transparency;
    }
    
    /**
     * Updates the transparency of the canvas, or of the selected shapes if editing is activated.
     * @param f transparency, from 0 to 1.
     */
    public void updateTransparency(float f){
        if(!this.editMode){
            setTransparency(f);
        }
        else{
            for(JShape s: selectedShapes){
                if(s != null) s.getPainter().setTransparency(f);
            }
            this.repaint();
        }
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
     * Updates the flatten of the canvas, or of the selected shapes if editing is activated.
     * @param b boolean indicating flatten.
     */
    public void updateFlatten(boolean b){
        if(!this.editMode){
            setFlatten(b);
        }
        else{
            for(JShape s: selectedShapes){
                if(s != null) s.getPainter().setFlatten(b);
            }
            this.repaint();
        }
    }
    
    /**
     * {@inheritDoc }
     */
    public void setFillMode(int mode){
        this.fill_mode = mode;
    }
    
    /**
     * {@inheritDoc }
     */
    public int getFillMode(){
        return this.fill_mode;
    }
    
    /**
     * Updates the flatten of the canvas, or of the selected shapes if editing is activated.
     * @param mode fill mode.
     */
    public void updateFillMode(int mode){
        if(!this.editMode){
            setFillMode(mode);
        }
        else{
            for(JShape s: selectedShapes){
                if(s != null) s.getPainter().setFillMode(mode);
            }
            this.repaint();
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setRotation(double f) {
        this.rotation = f;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public double getRotation() {
        return this.rotation;
    }
    
    /**
     * Updates the rotation value of the canvas, or of the selected shapes if editing is activated.
     * @param rot Rotation degree, in radians.
     */
    public void updateRotation(double rot){
        if(!this.editMode){
            setRotation(rot);
        }
        else{
            for(JShape s: selectedShapes){
                if(s != null) s.getPainter().setRotation(rot);
            }
            this.repaint();
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void setScale(double f) {
        this.scale = f;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public double getScale() {
        return this.scale;
    }
    
    /**
     * Updates the scale value of the canvas, or of the selected shapes if editing is activated.
     * @param sc Scale value.
     */
    public void updateScale(double sc){
        if(!this.editMode){
            setScale(sc);
        }
        else{
            for(JShape s: selectedShapes){
                if(s != null) s.getPainter().setScale(sc);
            }
            this.repaint();
        }
    }
    
    
    
    
    
    /**
     * Enables or disables edition mode.
     * @param b Boolean indicating if enabling or disabling.
     */
    public void setEditing(boolean b){
        this.editMode = b;
        if(editMode){
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }
        else{
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
            for(JShape s: selectedShapes){
                s.getPainter().setEditing(false);
            }
            selectedShapes.clear();
            this.repaint();
        }
    }
    
    /**
     * @return Edition enabling.
     */
    public boolean isEditing(){
        return this.editMode;
    }
    
    /**
     * Obtains the initial point from canvas mouse events.
     * @return Initial point from canvas mouse events.
     */
    public Point getP0(){
        return this.p0;
    }
    
    /**
     * Obtains the final point from canvas mouse events.
     * @return Final point from canvas mouse events.
     */
    public Point getP1(){
        return this.p1;
    }
    
    /**
     * Sets the frontier draw of the canvas.
     * @param b Boolean indicating whether drawing frontier.
     */
    public void setFrontierDraw(boolean b){
        this.drawFrontier = b;
    }
    
    /**
     * Checks if frontier is drawing.
     * @return True, if frontier is drawing, false otherwise.
     */
    public boolean isFrontierDrawing(){
        return drawFrontier;
    }
    
    /**
     * Obtains selected shapes.
     * @return ArrayList with the selected shapes.
     */
    public ArrayList<JShape> getSelection(){
        return this.selectedShapes;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Canvas mouse pressed event handler.
     * Creates a new shape if there are not current shapes being created at the moment and edition mode is disabled.
     * @param evt MouseEvent.
     */
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        p0 = p1 = pOld = evt.getPoint();
        if(!editMode)
            if(this.currentShape == null)
                createShape();
        else{
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        }
        
        this.repaint();
    }//GEN-LAST:event_formMousePressed

    /**
     * Obtains a regular point from the given points.
     * A regular point for points p0 and p1 will be a point in a horizontal, vertical or diagonal 45º ray with origin p0
     * that intersects a vertical or horizontal ray from p1.
     * @param p0 Initial point.
     * @param p1 End point.
     * @return The regular point for p0 and p1.
     */
    private Point getRegularPointFrom(Point p0,Point p1){
        Point diff = new Point(p1.x-p0.x,p1.y-p0.y);
        if(Math.abs(diff.x) < 5) return new Point(p0.x,p1.y);
        if(Math.abs(diff.y) < 5) return new Point(p1.x,p0.y);
        Point reg;
            if(Math.abs(diff.x) > Math.abs(diff.y)){
                reg = new Point(p0.x+Integer.signum(diff.x)*Math.abs(diff.y),p0.y+diff.y);
            }
            else{
                reg = new Point(p0.x+diff.x,p0.y+Integer.signum(diff.y)*Math.abs(diff.x));
            }
        
        return reg;
    }
    
    /**
     * Canvas mouse dragged event handler.
     * Updates the figure in current creation, or moves selected figures if edition mode is activated.
     * @param evt MouseEvent.
     */
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        pOld = p1;
        p1 = evt.getPoint();
        if(!editMode){
            if(regular_flag){
                p1 = getRegularPointFrom(p0,p1);
            }
            updateShape();
        }
        else
            editShape();
        this.repaint();
    }//GEN-LAST:event_formMouseDragged

    /**
     * Canvas mouse released event handler.
     * Commits the current figure if a figure is being created.
     * @param evt MouseEvent.
     */
    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        //this.formMouseDragged(evt);
        //this.selectedShape = null;
        if(editMode){
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }
        else{
            if(currentShape != null && this.currentShape.commitShape())
                this.currentShape = null;
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
            
        }
        
        this.repaint();
    }//GEN-LAST:event_formMouseReleased

    /**
     * If edition mode is activated, selects or unselects the figure that reaches the mouse position, if any.
     * Else, confirms a shape if a double click is performed.
     * @param evt MouseEvent.
     */
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        if(editMode){
            JShape selectedShape = getSelectedShape(p0);
            if(selectedShape != null){
                if(selectedShape.getPainter().isEditing()){
                    selectedShape.getPainter().setEditing(false);
                    selectedShapes.remove(selectedShape);
                }
                else{
                    selectedShape.getPainter().setEditing(true);
                    selectedShapes.add(selectedShape);
                }
            }
            this.repaint();
        }
        else{
            if(evt.getClickCount() == 2){ //Confirm shape.
                if(currentShape != null){
                    this.currentShape.confirmShape();
                    this.currentShape = null;
                }
                this.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
            }
            
        }
    }//GEN-LAST:event_formMouseClicked

    /**
     * Detects if SHIFT key is pressed, to activate regular mode.
     * Regular mode allows to construct squares when using rectangles and circles when using ellipses. Also, it will allow to construct
     * horizontal, vertical and 45º diagonal lines.
     * @param evt KeyEvent.
     */
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_SHIFT){
            regular_flag = true;
            
        }
    }//GEN-LAST:event_formKeyPressed

    /**
     * Detects if SHIFT key is released, to deactivate regular mode.
     * Regular mode allows to construct squares when using rectangles and circles when using ellipses. Also, it will allow to construct
     * horizontal, vertical and 45º diagonal lines.
     * @param evt KeyEvent.
     */
    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_SHIFT){
            regular_flag = false;
        }
    }//GEN-LAST:event_formKeyReleased



    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
