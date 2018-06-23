
package sm.jlsd.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;

/**
 * Class FloatingWindow.
 * A window that can be set to appear at any position on the screen.
 * It also allows to be associated to a component, and in this case it will be moved if the component is moved, and also will be hidden if the component is hidden.
 * @author jlsuarezdiaz
 */
public class FloatingWindow extends javax.swing.JWindow {

    /**
     * Place the window at the same position of the component.
     */
    public static final int SAME_POSITION = -1;
    
    /**
     * Place the window below the component.
     */
    public static final int BELOW = 0;
    
    /**
     * Place the window above the component.
     */
    public static final int ABOVE = 1;
    
    /**
     * A controller variable for visibility.
     */
    private boolean wasVisible = false;
    
    /**
     * Constructor. Creates a new window on the top left corner of the screen.
     */
    public FloatingWindow(){
        setLocation(0,0);
        setPreferredSize(new Dimension(100,32));
        initComponents();
    }
    
    /**
     * Constructor. Creates a new window on the given position.
     * @param position Window position.
     */
    public FloatingWindow(Point position){
        setLocation(position);
        initComponents();
    }
    
    /**
     * Constructor. Creates a new window on the given position with the given dimension.
     * @param position Window position.
     * @param dim Window dimension.
     */
    public FloatingWindow(Point position, Dimension dim){
        setLocation(position);
        setPreferredSize(dim);
        initComponents();
    }
    
    /**
     * Constructor. Creates a window associated to a component. Window will appear below the component.
     * @param c Associated component.
     */
    public FloatingWindow(JComponent c){
        associateToComponent(c);
        initComponents();
    }
    
    /**
     * Constructor. Creates a window associated to a component.
     * @param c Associated component.
     * @param rel_pos Relative position respect to the component to place the window: FloatingWindow.SAME_POSITION, FloatingWindow.BELOW, FloatingWindow.ABOVE.
     */
    public FloatingWindow(JComponent c, int rel_pos){
        associateToComponent(c,rel_pos);
        initComponents();
    }
    
    /**
     * Constructor. Creates a window associated to a component.
     * @param c Associated component.
     * @param rel_pos Relative position respect to the component to place the window: FloatingWindow.SAME_POSITION, FloatingWindow.BELOW, FloatingWindow.ABOVE.
     * @param dim  Window dimension.
     */
    public FloatingWindow(JComponent c, int rel_pos, Dimension dim){
        associateToComponent(c, rel_pos);
        setPreferredSize(dim);
        initComponents();
    }
    
    /**
     * Constructor. Creates a window associated to a component. Window will be placed below the component.
     * @param c Associated component.
     * @param dim  Window dimension.
     */
    public FloatingWindow(JComponent c, Dimension dim){
        associateToComponent(c);
        setPreferredSize(dim);
        initComponents();
    }
    
    /**
     * Associates the window to a component. Window will be placed below the component.
     * @param c Associated component.
     */
    public void associateToComponent(JComponent c){
        associateToComponent(c,BELOW);
    }
    
    /**
     * Associates the window to a component.
     * @param c Associated component.
     * @param rel_pos Relative position respect to the component to place the window: FloatingWindow.SAME_POSITION, FloatingWindow.BELOW, FloatingWindow.ABOVE.
     */
    public void associateToComponent(JComponent c,int rel_pos){
        SwingUtilities.getWindowAncestor(c).addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                super.componentMoved(e);
                if(c.isVisible()){
                    setLocationFromComponent(c,rel_pos);
                    pack();
                }
            }
            
            

            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e); 
                if(c.isVisible()){
                    setLocationFromComponent(c,rel_pos);
                    pack();
                }
            }
            
        });
        
        WindowAdapter wa = new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                super.windowActivated(e); 
                setVisible(wasVisible);
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                super.windowDeactivated(e); 
                wasVisible = isVisible();
                setVisible(false);
            }
        };
        
        WindowAdapter wathis = new WindowAdapter(){
            @Override
            public void windowActivated(WindowEvent e) {
                System.out.println("EYYY");
                setLocationFromComponent(c,rel_pos);
                pack();
            }
            
        };
        
        SwingUtilities.getWindowAncestor(c).addWindowStateListener(wa);
        SwingUtilities.getWindowAncestor(c).addWindowFocusListener(wa);
        SwingUtilities.getWindowAncestor(c).addWindowListener(wa);
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                setLocationFromComponent(c,rel_pos);
                pack();
            }
            
        });
        

    }
    
    /**
     * Sets the window location according to the location of the given component. The window will be placed below the component.
     * @param c Component whose location is taken.
     */
    public void setLocationFromComponent(JComponent c){
        setLocationFromComponent(c,BELOW);
    }
    
    /**
     * Sets the window location according to the location of the given component. 
     * @param c Component whose location is taken.
     * @param rel_pos Relative position respect to the component to place the window: FloatingWindow.SAME_POSITION, FloatingWindow.BELOW, FloatingWindow.ABOVE.
     */
    public void setLocationFromComponent(JComponent c, int rel_pos){
        switch(rel_pos){
            case BELOW:
            {
                if(c.isShowing()){
                    Point comp_loc = c.getLocationOnScreen();
                    Dimension dim = c.getSize();
                    setLocation(comp_loc.x, comp_loc.y + dim.height);
                }
                
                
                break;
            }
            case ABOVE:
            {
                Point comp_loc = c.getLocationOnScreen();
                Dimension dim = this.getSize();
                setLocation(comp_loc.x, comp_loc.y - dim.height);
            }
                break;
            case SAME_POSITION:
            default:
                setLocation(c.getLocation());
                break;
        }
    }
    
    /**
     * Obtains the internal panel of the window. Components must be added on this panel.
     * @return Internal window panel.
     */
    public JPanel getInternalPanel(){
        return borderPanel;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        borderPanel = new javax.swing.JPanel();

        setAlwaysOnTop(true);

        borderPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        borderPanel.setLayout(new java.awt.BorderLayout());
        getContentPane().add(borderPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel borderPanel;
    // End of variables declaration//GEN-END:variables
}
