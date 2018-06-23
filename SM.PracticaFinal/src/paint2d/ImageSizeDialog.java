/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint2d;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JOptionPane;

/**
 * Class ImageSizeDialog. A dialog to set default and current image sizes.
 * @author jlsuarezdiaz
 */
public class ImageSizeDialog extends javax.swing.JDialog {
    /**
     * Cancel option.
     */
    public static final int CANCEL = 0;
    
    /**
     * Change default size option.
     */
    public static final int CHANGE_DEFAULT = 1;
    
    /**
     * Resize option.
     */
    public static final int RESIZE = 2;
    
    /**
     * Scale option.
     */
    public static final int SCALE = 3;
    
    /**
     * Selected option.
     */
    private int selected_option = CANCEL;

    /**
     * Creates new form ImageSizeDialog
     */
    public ImageSizeDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        topPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        defaultHeightSpin = new javax.swing.JSpinner();
        defaultWidthSpin = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btChangeDefault = new javax.swing.JButton();
        lowPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        currentHeightSpin = new javax.swing.JSpinner();
        currentWidthSpin = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btResize = new javax.swing.JButton();
        btScale = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cambiar tamaño de imagen");

        topPanel.setPreferredSize(new java.awt.Dimension(320, 160));
        topPanel.setLayout(null);

        jLabel1.setText("Tamaño por defecto:");
        topPanel.add(jLabel1);
        jLabel1.setBounds(20, 10, 220, 15);

        defaultHeightSpin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        topPanel.add(defaultHeightSpin);
        defaultHeightSpin.setBounds(200, 60, 90, 20);

        defaultWidthSpin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        topPanel.add(defaultWidthSpin);
        defaultWidthSpin.setBounds(30, 60, 90, 20);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel2.setText("Alto (px)  ");
        topPanel.add(jLabel2);
        jLabel2.setBounds(200, 40, 80, 15);

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel3.setText("Ancho (px)");
        topPanel.add(jLabel3);
        jLabel3.setBounds(30, 40, 90, 15);

        btChangeDefault.setText("Cambiar");
        btChangeDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btChangeDefaultActionPerformed(evt);
            }
        });
        topPanel.add(btChangeDefault);
        btChangeDefault.setBounds(110, 90, 92, 25);

        getContentPane().add(topPanel, java.awt.BorderLayout.PAGE_START);

        lowPanel.setPreferredSize(new java.awt.Dimension(320, 160));
        lowPanel.setLayout(null);

        jLabel4.setText("Tamaño de imagen actual");
        lowPanel.add(jLabel4);
        jLabel4.setBounds(20, 10, 260, 15);

        currentHeightSpin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        lowPanel.add(currentHeightSpin);
        currentHeightSpin.setBounds(200, 60, 90, 20);

        currentWidthSpin.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        lowPanel.add(currentWidthSpin);
        currentWidthSpin.setBounds(30, 60, 90, 20);

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel5.setText("Alto (px)  ");
        lowPanel.add(jLabel5);
        jLabel5.setBounds(200, 40, 80, 15);

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel6.setText("Ancho (px)");
        lowPanel.add(jLabel6);
        jLabel6.setBounds(30, 40, 90, 15);

        btResize.setText("Redimensionar");
        btResize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btResizeActionPerformed(evt);
            }
        });
        lowPanel.add(btResize);
        btResize.setBounds(160, 90, 140, 25);

        btScale.setText("Escalar");
        btScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btScaleActionPerformed(evt);
            }
        });
        lowPanel.add(btScale);
        btScale.setBounds(20, 90, 140, 25);

        getContentPane().add(lowPanel, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Selects the option to change default size.
     * @param evt ActionEvent.
     */
    private void btChangeDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btChangeDefaultActionPerformed
        this.selected_option = CHANGE_DEFAULT;
        dispose();
    }//GEN-LAST:event_btChangeDefaultActionPerformed

    /**
     * Selects the option to scale image.
     * @param evt ActionEvent.
     */
    private void btScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btScaleActionPerformed
        this.selected_option = SCALE;
        dispose();
    }//GEN-LAST:event_btScaleActionPerformed

    /**
     * Selects the option to resize image.
     * @param evt ActionEvent.
     */
    private void btResizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btResizeActionPerformed
        this.selected_option = RESIZE;
        dispose();
    }//GEN-LAST:event_btResizeActionPerformed


    /**
     * Set current image valies.
     * @param defaultSize current default size.
     * @param currentSize current image size.
     */
    public void setCurrentValues(Dimension defaultSize, Dimension currentSize){
        if(defaultSize != null){
            topPanel.setEnabled(true);
            for(Component c: topPanel.getComponents()){
                c.setEnabled(true);
            }
            this.defaultWidthSpin.setValue(defaultSize.width);
            this.defaultHeightSpin.setValue(defaultSize.height);
        }
        else{
            topPanel.setEnabled(false);
            for(Component c: topPanel.getComponents()){
                c.setEnabled(false);
            }
        }
        if(currentSize != null){
            lowPanel.setEnabled(true);
            for(Component c: lowPanel.getComponents()){
                c.setEnabled(true);
            }
            this.currentWidthSpin.setValue(currentSize.width);
            this.currentHeightSpin.setValue(currentSize.height);
        }
        else{
            lowPanel.setEnabled(false);
            for(Component c: lowPanel.getComponents()){
                c.setEnabled(false);
            }
        }
    }
    
    /**
     * Sets the visibility of the options for current image sizes.
     * @param b Boolean indicating visibility.
     */
    private void setCurrentVisible(boolean b){
        this.lowPanel.setVisible(b);
    }
    
    /**
     * Show the dialog.
     * @return The selected option.
     */
    public int showDialog(){
        this.setVisible(true);
        return selected_option;
    }
    
    /**
     * 
     * @return Default size set in the dialog.
     */
    public Dimension getDefaultSize(){
        return new Dimension((int)defaultWidthSpin.getValue(),(int)defaultHeightSpin.getValue());
    }
    
    /**
     * 
     * @return Current size set in the dialog.
     */
    public Dimension getCurrentSize(){
        return new Dimension((int)currentWidthSpin.getValue(),(int)currentHeightSpin.getValue());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btChangeDefault;
    private javax.swing.JButton btResize;
    private javax.swing.JButton btScale;
    private javax.swing.JSpinner currentHeightSpin;
    private javax.swing.JSpinner currentWidthSpin;
    private javax.swing.JSpinner defaultHeightSpin;
    private javax.swing.JSpinner defaultWidthSpin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel lowPanel;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}