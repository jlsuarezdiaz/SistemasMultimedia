
package sm.jlsd.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import sm.jlsd.graphics.BasicStrokeType;

/**
 * Class LineListCellRenderer.
 * A ListCellRenderer that allows to show in each cell any stroke line defined in {@link sm.jlsd.graphics.BasicStrokeType}.
 * @author jlsuarezdiaz 
*/
public class LineListCellRenderer extends javax.swing.JPanel implements ListCellRenderer{
    private BasicStrokeType type;

    /**
     * Creates new form LineListCellRenderer
     */
    public LineListCellRenderer() {
        initComponents();
    }
    
    /**
     * Return a component that has been configured to display the specified value. That component's paint method is then called to "render" the cell. 
     * If it is necessary to compute the dimensions of a list because the list cells do not have a fixed size, this method is called to generate a 
     * component on which getPreferredSize can be invoked.
     * @param list The JList we're painting.
     * @param value The value returned by list.getModel().getElementAt(index).
     * @param index The cells index.
     * @param isSelected True if the specified cell was selected.
     * @param cellHasFocus  True if the specified cell has the focus.
     */
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        JList.DropLocation dropLocation = list.getDropLocation();
        if(dropLocation != null && !dropLocation.isInsert() && dropLocation.getIndex() == index){
            setBackground(Color.red);
        }
        //else
        if(isSelected){
            setBackground(list.getSelectionBackground());
        }
        else{
            setBackground(list.getBackground());
        }
        this.type =  (BasicStrokeType) value;
        return this;
    }

    /**
     * Paints the panel according to the selected stroke.
     * @param g Graphics object to paint.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        if(this.type != null){
            Stroke stroke = type.getStroke(2.0f);
            if(stroke != null){
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setStroke(stroke);
                g2d.drawLine(getWidth()/10, getHeight()/2, 9*getWidth()/10, getHeight()/2);
            }
        }
        g2d.dispose();
    }

    /**
     * Preferred cell size.
     * @return Dimension with preferred size.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(75,20);
    }
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
