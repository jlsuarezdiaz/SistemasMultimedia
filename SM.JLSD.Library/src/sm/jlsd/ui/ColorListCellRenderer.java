
package sm.jlsd.ui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Class ColorListCellRenderer.
 * An JPanel to be displayed in a list to show different colors.
 * @author jlsuarezdiaz
 */
public class ColorListCellRenderer extends javax.swing.JPanel implements ListCellRenderer{

    /**
     * Creates new form ColorCellListRenderer
     */
    public ColorListCellRenderer() {
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
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        
        Color background = (Color) value;
        
        JList.DropLocation dropLocation = list.getDropLocation();
        if(dropLocation != null && !dropLocation.isInsert() && dropLocation.getIndex() == index){
            //
        }
        //else
        if(isSelected){
            setBackground(list.getSelectionBackground());
        }
        else{
            setBackground(list.getBackground());
        }
        
        colorBt.setBackground(background);
        return this;
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        colorBt = new javax.swing.JToggleButton();

        setPreferredSize(new java.awt.Dimension(26, 26));
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 1, 1));

        colorBt.setBackground(new java.awt.Color(0, 0, 0));
        colorBt.setBorder(null);
        colorBt.setPreferredSize(new java.awt.Dimension(24, 24));
        add(colorBt);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton colorBt;
    // End of variables declaration//GEN-END:variables
}