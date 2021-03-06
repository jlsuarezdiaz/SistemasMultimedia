/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint2d;

import java.awt.Color;
import java.awt.Point;
import sm.jlsd.graphics.PaintTool;

/**
 * Class StateBar. The program state bar.
 * @author jlsuarezdiaz
 */
public class StateBar extends javax.swing.JPanel {

    /**
     * Creates new form StateBar
     */
    public StateBar() {
        initComponents();
    }

    /**
     * Set the paint tool in the state bar.
     * @param t Paint tool.
     */
    public void setTool(PaintTool t){
        if(t == null) toolLabel.setText(" Modo edición. ");
        else
            switch(t){
                case POINT:
                    toolLabel.setText(" Trazo libre. ");
                    break;
                case LINE:
                    toolLabel.setText(" Línea. ");
                    break;
                case RECTANGLE:
                    toolLabel.setText(" Rectángulo. ");
                    break;
                case OVAL:
                    toolLabel.setText(" Elipse. ");
                    break;
                case ROUND_RECTANGLE:
                    toolLabel.setText(" Rectángulo redondeado.");
                    break;
                case QUAD_CURVE:
                    toolLabel.setText(" Curva cuadrática. ");
                    break;
                case CUBIC_CURVE:
                    toolLabel.setText(" Curva cúbica. ");
                    break;
                case POLYGON:
                    toolLabel.setText(" Polígono. ");
                    break;
                case ARC:
                    toolLabel.setText(" Arco. ");
                    break;
                case AREA_SUN:
                    toolLabel.setText(" Área: sol. ");
                    break;
            }
    }
    
    /**
     * Set points location in the state bar. Indicates the mouse position in canvas, or the mouse initial
     * and final points when drawing a figure.
     * @param p1 null, or mouse position in canvas, or mouse pressed position (if p2 is specified).
     * @param p2 null, or mouse dragged position.
     */
    public void setPoints(Point p1, Point p2){
        if(p1 != null && p2 != null){
            pointsLabel.setText(" ["+Integer.toString(p1.x)+","+Integer.toString(p1.y)+"]-->["+
                                    Integer.toString(p2.x)+","+Integer.toString(p2.y)+"] ");
        }
        else if(p1 != null){
            pointsLabel.setText(" ["+Integer.toString(p1.x)+","+Integer.toString(p1.y)+"] ");
        }
        else{
            pointsLabel.setText("");
        }
    }
    
    /**
     * Set RGB color in the state bar.
     * @param rgb Integer representing an RGB color, or null.
     */
    public void setColor(Integer rgb){
        if(rgb != null){
            Color c = new Color(rgb);
            colorLabel.setText("R: "+c.getRed()+"; G: "+c.getGreen()+"; B: "+c.getBlue());
        }
        else{
            colorLabel.setText("");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        coordsPanel = new javax.swing.JPanel();
        colorLabel = new javax.swing.JLabel();
        pointsLabel = new javax.swing.JLabel();
        toolPanel = new javax.swing.JPanel();
        toolLabel = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        colorLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        colorLabel.setPreferredSize(new java.awt.Dimension(200, 24));
        coordsPanel.add(colorLabel);

        pointsLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pointsLabel.setPreferredSize(new java.awt.Dimension(200, 24));
        coordsPanel.add(pointsLabel);

        add(coordsPanel, java.awt.BorderLayout.LINE_END);

        toolLabel.setText(" Trazo libre. ");
        toolLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        toolLabel.setPreferredSize(new java.awt.Dimension(200, 24));
        toolPanel.add(toolLabel);

        add(toolPanel, java.awt.BorderLayout.LINE_START);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel colorLabel;
    private javax.swing.JPanel coordsPanel;
    private javax.swing.JLabel pointsLabel;
    private javax.swing.JLabel toolLabel;
    private javax.swing.JPanel toolPanel;
    // End of variables declaration//GEN-END:variables
}
