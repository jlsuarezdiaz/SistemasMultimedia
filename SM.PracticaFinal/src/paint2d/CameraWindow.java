
package paint2d;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.image.BufferedImage;

/**
 * Class CameraWindow.
 * Singleton class to manage the window that holds the webcam.
 * @author jlsuarezdiaz
 */
public class CameraWindow extends MediaWindowAdapter {
    /**
     * Single instance.
     */
    private static CameraWindow instance = null;
    
    /**
     * Camera.
     */
    private Webcam camera = null;
    
    /**
     * Creates new form CameraWindow
     */
    private CameraWindow() {
        initComponents();
        camera = Webcam.getDefault();
        if(camera != null){
            Dimension resolutions[] = camera.getViewSizes();
            Dimension maxRes = resolutions[resolutions.length-1];
            camera.setViewSize(maxRes);
            WebcamPanel viewArea = new WebcamPanel(camera);
            if(viewArea != null){
                getContentPane().add(viewArea,BorderLayout.CENTER);
                pack();
            }
            
        }
    }
    
    
    /**
     * @return A new instance of a camera window, or null, if no cameras are available.
     */
    public static CameraWindow getInstance(){
        if(instance == null){
            instance = new CameraWindow();
            instance =  (instance.camera != null?instance:null);
        }
        return instance;
    }
    
    /**
     * Closes the camera associated to the window.
     */
    public void close(){
        if(camera != null){
            camera.close();
        }
        camera = null;
        instance = null;
    }
    
    /**
     * Obtains the camera.
     */
    @Override
    public Webcam getCamera(){
        return this.camera;
    }

    /**
     * Makes a snapshot.
     * @return BufferedImage with a snapshot, or null if it couldn't be taken.
     */
    @Override
    public BufferedImage getSnapShot() {
        if(camera != null){
            return camera.getImage();
        }
        else return null;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("WebCam");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Window closing event handler.
     * @param evt InternalFrameEvent.
     */
    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        close();
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}