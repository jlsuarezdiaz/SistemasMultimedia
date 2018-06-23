
package paint2d;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * Class VLCWindow. A class to manage windows associated to videos using VLC.
 * @author jlsuarezdiaz
 */
public class VLCWindow extends MediaWindowAdapter {
    /**
     * Player.
     */
    private EmbeddedMediaPlayer vlcplayer = null;
    
    /**
     * Media file.
     */
    private File fMedia;
    
    
    /**
     * A controller thread to update audio and video values in the swing view.
     */
    Timer controller = null;

    /**
     * Creates new form VLCWindow
     */
    private VLCWindow(File f) {
        initComponents();
        fMedia = f;
        
        EmbeddedMediaPlayerComponent aVisual = new EmbeddedMediaPlayerComponent();
        aVisual.setPreferredSize(new Dimension(600,600));
        getContentPane().add(aVisual,BorderLayout.CENTER);
        pack();
        vlcplayer = aVisual.getMediaPlayer();
        if(vlcplayer != null){
            vlcplayer.addMediaPlayerEventListener(new VideoListener()); //Player event listener.
            controller = new Timer(10, new Updater(vlcplayer));
        } 
    }
    

    /**
     * Obtains a new VLCWindow instance.
     * @param f Media file.
     * @return new VLCWindow instance, or null if construction failed.
     */
    public static VLCWindow getInstance(File f){
        VLCWindow vlcw = new VLCWindow(f);
        return (vlcw.vlcplayer != null?vlcw:null);
    }
    
    /**
     * Obtains a new VLCWindow instance.
     * @param f Media file.
     * @param parent Parent MainView.
     * @return new VLCWindow instance, or null if construction failed.
     */
    public static VLCWindow getInstance(File f,MainView parent){
        VLCWindow vlcw = new VLCWindow(f);
        vlcw.setParent(parent);
        return (vlcw.vlcplayer != null?vlcw:null);
    }
    
    /**
     * Plays the media.
     */
    @Override
    public void play(){
        if(vlcplayer != null){
            if(vlcplayer.isPlayable()){
                vlcplayer.play();
            }
            else{
                vlcplayer.playMedia(fMedia.getAbsolutePath());
            }
            controller.start();// GUI controller.
        }
    }
    
    /**
     * Pauses the media.
     */
    @Override
    public void pause(){
        if(vlcplayer != null){
            vlcplayer.pause();
        }
    }
    
    /**
     * Switches between play and pause.
     */
    @Override
    public void playPause(){
        if(vlcplayer != null){
            if(vlcplayer.isPlayable()){
                if(vlcplayer.isPlaying()){
                    vlcplayer.pause();
                }
                else{
                    vlcplayer.play();
                    controller.start();
                }
            }
            else{
                vlcplayer.playMedia(fMedia.getAbsolutePath());
                controller.start();
            }
        }
    }
    
    /**
     * Stops the media.
     */
    @Override
    public void stop(){
        if(vlcplayer != null){
            vlcplayer.stop();
        }
    }
    
    /**
     * Seeks the media.
     * @param position Relative position.
     */
    @Override
    public void seek(float position){
        if(vlcplayer != null && vlcplayer.isSeekable()){
            vlcplayer.setPosition(position);
        }
    }
    
    /**
     * Checks if player is playing.
     * @return True if player is playing, false otherwise.
     */
    @Override
    public boolean isPlaying(){
        if(vlcplayer != null){
            return vlcplayer.isPlaying();
        }
        else return false;
    }
    
    /**
     * Obtains the media file.
     * @return Current media file.
     */
    public File getFile(){
        return fMedia;
    }
    
    /**
     * Makes a snapshot.
     * @return BufferedImage with a snapshot, or null, if it failed.
     */
    public BufferedImage getSnapShot(){
        if(vlcplayer != null){
            return vlcplayer.getSnapshot();
        }
        else return null;
    }
    
    /**
     * Class VideoListener.
     * An EventHandler for the media player.
     */
    private class VideoListener extends MediaPlayerEventAdapter{
        /**
         * {@inheritDoc }
         *
         */
        @Override
        public void finished(MediaPlayer mediaPlayer) {
            super.finished(mediaPlayer);
            parent.setAudioVideoView(false, 0,0);
            controller.stop();
        }

       
        /**
         * {@inheritDoc } 
         */
        @Override
        public void paused(MediaPlayer mediaPlayer) {
            super.paused(mediaPlayer);
            parent.setAudioVideoView(false);
            controller.stop();
            System.out.println("PAUSED");
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public void stopped(MediaPlayer mediaPlayer) {
            super.stopped(mediaPlayer);
            parent.setAudioVideoView(false,0,0);
            controller.stop();
            System.out.println("STOPPED");
        }

        /**
         * {@inheritDoc } 
         */
        @Override
        public void newMedia(MediaPlayer mediaPlayer) {
            super.newMedia(mediaPlayer);
        }
        
        /**
         * {@inheritDoc } 
         */
        @Override
        public void playing(MediaPlayer mediaPlayer) {
            super.playing(mediaPlayer);
            parent.setAudioVideoView(false, 0, mediaPlayer.getLength());
            System.out.println("PLAYING");
        }
        
    }
    
    /**
     * Class updater.
     * Contains the action for updating the GUI when playing media.
     */
    private final class Updater implements ActionListener{
        /**
         * Player.
         */
        private MediaPlayer mediaPlayer;
        
        /**
         * Constructor.
         * @param mediaPlayer Media player. 
         */
        private Updater(MediaPlayer mediaPlayer){
            this.mediaPlayer = mediaPlayer;
        }
        
        /**
         * ActionPerformed event handler.
         * @param e ActionEvent.
         */
        @Override
        public void actionPerformed(ActionEvent e){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if(parent != null) parent.setAudioVideoView(mediaPlayer.isPlaying(), mediaPlayer.getPosition());
                }
            });
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

        jPanel1 = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
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
     * Internal frame closing event handler.
     * @param evt InternalFrameEvent.
     */
    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        stop();
        vlcplayer = null;
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
