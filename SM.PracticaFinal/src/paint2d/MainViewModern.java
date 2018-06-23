
package paint2d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.RescaleOp;
import java.io.File;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import sm.image.BlendOp;
import sm.image.EqualizationOp;
import sm.image.Histogram;
import sm.image.KernelProducer;
import sm.image.LookupTableProducer;
import sm.image.SubtractionOp;
import sm.image.TintOp;
import sm.image.color.GreyColorSpace;
import sm.jlsd.graphics.BasicAttributePainter;
import sm.jlsd.graphics.BasicStrokeType;
import sm.jlsd.graphics.JRectangle;
import sm.jlsd.graphics.PaintTool;
import sm.jlsd.image.BackgroundRemoveOp;
import sm.jlsd.image.ColdEffectOp;
import sm.jlsd.image.FireEffectOp;
import sm.jlsd.image.JKernelProducer;
import sm.jlsd.image.JLookupTableProducer;
import sm.jlsd.image.ModularTranslateOp;
import sm.jlsd.image.ProductOp;
import sm.jlsd.image.SepiaOp;
import sm.jlsd.image.SobelOp;
import sm.jlsd.image.UmbralizacionOp;
import sm.jlsd.ui.Canvas2D;
import sm.jlsd.ui.ColorListCellRenderer;
import sm.jlsd.ui.FloatingWindow;
import sm.jlsd.ui.HistogramPanel;
import sm.jlsd.ui.LineListCellRenderer;
import sm.jlsd.utils.ImageUtils;
import sm.jlsd.utils.MediaFileFilter;
import sm.sound.SMSoundPlayer;
import sm.sound.SMSoundRecorder;
import uk.co.caprica.vlcj.filter.AudioFileFilter;
import uk.co.caprica.vlcj.filter.VideoFileFilter;
import uk.co.caprica.vlcj.filter.swing.SwingFileFilter;

/**
 * Class MainViewModern.
 * A modern view for the program.
 * @author jlsuarezdiaz
 */
public class MainViewModern extends MainView {
    /**
     * Array to group file tools.
     */
    private ArrayList<Component> filesToolGroup;
    
    /**
     * Array to group shape tools.
     */
    private ArrayList<Component> shapesToolGroup;
    
    /**
     * Array to group color tools.
     */
    private ArrayList<Component> colorsToolGroup;
    
    /**
     * Array to group attribute tools.
     */
    private ArrayList<Component> attributesToolGroup;
    
    /**
     * Array to group bright tools.
     */
    private ArrayList<Component> brightToolGroup;
    
    /**
     * Array to group convolution tools.
     */
    private ArrayList<Component> convolveToolGroup;
    
    /**
     * Array to group effect tools.
     */
    private ArrayList<Component> effectsToolGroup;
    
    /**
     * Array to group color space tools.
     */
    private ArrayList<Component> colorSpacesToolGroup;
    
    /**
     * Array to group transformation tools.
     */
    private ArrayList<Component> transformsToolGroup;
    
    /**
     * Array to group binary tools.
     */
    private ArrayList<Component> binaryToolGroup;
    
    /**
     * Array to group control tools.
     */
    private ArrayList<Component> controlToolGroup;
    
    /**
     * Array to group play tools.
     */
    private ArrayList<Component> playToolGroup;
    
    /**
     * Array to group record tools.
     */
    private ArrayList<Component> recordToolGroup;
    
    /**
     * Array to group webcam tools.
     */
    private ArrayList<Component> webcamToolGroup;
    
    /**
     * Array to group graphics tools.
     */
    private ArrayList<ArrayList<Component>> graphicsToolGroup;
    
    /**
     * Array to group images tools.
     */
    private ArrayList<ArrayList<Component>> imagesToolGroup;
    
    /**
     * Array to group audio and video tools.
     */
    private ArrayList<ArrayList<Component>> avToolGroup;
    
    /**
     * Default size for new images.
     */
    private Dimension defaultNewImgSize = new Dimension(500,500);
    
    /**
     * Default image for modifications.
     */
    private BufferedImage imgSource = null;
    private BufferedImage binarySrc1 = null;
    private BufferedImage binarySrc2 = null;
    private BufferedImage binaryDest = null;
    
    
    //FLOATING COMPONENTS
    private FloatingWindow brightnessWindow;
    private JSlider brightnessSlider;
    
    private FloatingWindow rotationWindow;
    private JSlider rotationSlider;
    private FloatingWindow exactRotationWindow;
    private JButton rotate90Bt;
    private JButton rotate180Bt;
    private JButton rotate270Bt;
    
    private FloatingWindow tintWindow;
    private JSlider tintSlider;
    
    private FloatingWindow thresholdWindow;
    private JSlider thresholdSlider;
    
    private FloatingWindow sumWindow;
    private JSlider sumSlider;
    
    private FloatingWindow backgroundRemoveWindow;
    private JSlider backgroundRemoveSlider;
    
    private FloatingWindow transparencyWindow;
    private JSlider transparencySlider;
    
    private FloatingWindow modularTranslationWindow;
    private JSlider modularTranslationSlider;
    
    private FloatingWindow transformWindow;
    private JSlider shapeRotationSlider;
    private JSlider shapeScaleSlider;
    
    private FloatingWindow shapesWindow;
    
    private FloatingWindow strokeWindow;
    
    private FloatingWindow moreEffectsWindow;
    
    private FloatingWindow moreOperationsWindow;
   
    //Other component data
    private Icon contrast_images[] = {
        new javax.swing.ImageIcon(getClass().getResource("/media/contraste.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/iluminar.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/oscurecer.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/negativo.png"))
    };
    
    private Icon fill_images[] = {
        new javax.swing.ImageIcon(getClass().getResource("/media/no_fill.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/single_color.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/vertical_gradient.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/horizontal_gradient.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/diagonal_main_gradient.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/diagonal_second_gradient.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/equiradial_gradient.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/basic_texture.png"))
    };
    
    private Icon shapes_images[] = {
        new javax.swing.ImageIcon(getClass().getResource("/media/punto.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/linea.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/rectangulo.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/elipse.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/round_rectangle.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/quad_curve.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/cubic_curve.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/polygon.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/arc.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/sun.png")),
        new javax.swing.ImageIcon(getClass().getResource("/media/seleccion.png"))
    };
    
    /**
     * Music player.
     */
    private SMSoundPlayer player = null;
    

    /**
     * Music recorder.
     */
    private SMSoundRecorder recorder = null;
    
    /**
     * Recorded audio.
     */
    private File audioTmpFile = null;
    
    /**
     * Record timer.
     */
    private static final DateFormat df = new SimpleDateFormat("mm:ss");
    static {
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
    private ActionListener recordTimeAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                if(recorder != null && recorder.line != null) recordTimeLabel.setText(df.format(new Date(recorder.line.getMicrosecondPosition()/1000)));
            }
            catch(Exception ex){}
        }
    };
    private Timer recordTimer = new Timer(100,recordTimeAction);
    
    
    /**
     * Video management windows.
     */
    private Map<File,VLCWindow> videoWindows = new HashMap<File,VLCWindow>();
    private VLCWindow currentPlayerWindow = null;
    
    // FILE FILTERS
    private static final FileFilter imageFilter = new FileNameExtensionFilter("Archivos de imagen." /*"["+String.join(",", ImageIO.getReaderFileSuffixes())+"]"*/, ImageIO.getReaderFormatNames());
    private static final FileFilter audioFilter = new SwingFileFilter("Archivos de audio." /*"["+String.join(",", AudioFileFilter.INSTANCE.getExtensions())+"]"*/, AudioFileFilter.INSTANCE);
    private static final FileFilter videoFilter = new SwingFileFilter("Archivos de video." /*"["+String.join(",", VideoFileFilter.INSTANCE.getExtensions())+"]"*/, VideoFileFilter.INSTANCE);
    private static final FileFilter mediaFilter = new SwingFileFilter("Todos los archivos multimedia.", MediaFileFilter.INSTANCE);

    /**
     * A preventer flag for some combo box action events.
     */
    private boolean preventer = false;
    
    /**
     * Creates new form PaintViewModern
     */
    public MainViewModern() {
        initComponents();
        initToolGroups();
        initFloatingComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
    }
    
    /**
     * Initialization for tool groups.
     */
    private void initToolGroups(){
        filesToolGroup = new ArrayList<Component>();
        shapesToolGroup = new ArrayList<Component>();
        colorsToolGroup = new ArrayList<Component>();
        attributesToolGroup = new ArrayList<Component>();
        
        brightToolGroup = new ArrayList<Component>();
        convolveToolGroup = new ArrayList<Component>();
        effectsToolGroup = new ArrayList<Component>();
        colorSpacesToolGroup = new ArrayList<Component>();
        transformsToolGroup = new ArrayList<Component>();
        binaryToolGroup = new ArrayList<Component>();
        
        controlToolGroup = new ArrayList<Component>();
        playToolGroup = new ArrayList<Component>();
        recordToolGroup = new ArrayList<Component>();
        webcamToolGroup = new ArrayList<Component>();
        
        graphicsToolGroup = new ArrayList<ArrayList<Component>>();
        imagesToolGroup = new ArrayList<ArrayList<Component>>();
        avToolGroup = new ArrayList<ArrayList<Component>>();
        
        filesToolGroup.add(newBt);
        filesToolGroup.add(openBt);
        filesToolGroup.add(saveBt);
        
        shapesToolGroup.add(shapeSeparator);
        shapesToolGroup.add(shapesComboBox);
        shapesToolGroup.add(shapesBt);
        
        colorsToolGroup.add(colorSeparator);
        colorsToolGroup.add(primaryColorList);
        colorsToolGroup.add(secondaryColorList);
        colorsToolGroup.add(addColorBt);
        
        attributesToolGroup.add(attributesSeparator);
        attributesToolGroup.add(strokeBt);
        attributesToolGroup.add(fillComboBox);
        attributesToolGroup.add(transparencyBt);
        attributesToolGroup.add(transformBt);
        attributesToolGroup.add(moreOperationsBt);
        
        graphicsToolGroup.add(shapesToolGroup);
        graphicsToolGroup.add(colorsToolGroup);
        graphicsToolGroup.add(attributesToolGroup);
        
        brightToolGroup.add(brightnessSeparator);
        brightToolGroup.add(brightnessBt);
        brightToolGroup.add(contrastComboBox);
        
        convolveToolGroup.add(filterSeparator);
        convolveToolGroup.add(filterBox);
        
        effectsToolGroup.add(effectsSeparator);
        effectsToolGroup.add(tintBt);
        effectsToolGroup.add(umbralBt);
        effectsToolGroup.add(removeBackgroundBt);
        effectsToolGroup.add(moreEffectsBt);
        
        colorSpacesToolGroup.add(colorSpaceSeparator);
        colorSpacesToolGroup.add(bandsBt);
        colorSpacesToolGroup.add(colorSpaceBox);
        
        transformsToolGroup.add(transformSeparator);
        transformsToolGroup.add(rotateBt);
        transformsToolGroup.add(rotateExactBt);
        transformsToolGroup.add(scalePlusBt);
        transformsToolGroup.add(scaleMinusBt);
        
        binaryToolGroup.add(binarySeparator);
        binaryToolGroup.add(sumBt);
        binaryToolGroup.add(diffBt);
        binaryToolGroup.add(productBt);
        
        imagesToolGroup.add(brightToolGroup);
        imagesToolGroup.add(convolveToolGroup);
        imagesToolGroup.add(effectsToolGroup);
        imagesToolGroup.add(colorSpacesToolGroup);
        imagesToolGroup.add(transformsToolGroup);
        imagesToolGroup.add(binaryToolGroup);
        
        controlToolGroup.add(playerSeparator);
        controlToolGroup.add(playPauseBt);
        controlToolGroup.add(stopBt);
        controlToolGroup.add(audioBox);
        
        playToolGroup.add(playingSeparator);
        playToolGroup.add(clipSlider);
        
        recordToolGroup.add(recordSeparator);
        recordToolGroup.add(recordBt);
        recordToolGroup.add(recordTimeLabel);
        
        webcamToolGroup.add(webcamSeparator);
        webcamToolGroup.add(webcamBt);
        webcamToolGroup.add(snapshotBt);
        
        avToolGroup.add(controlToolGroup);
        avToolGroup.add(playToolGroup);
        avToolGroup.add(recordToolGroup);
        avToolGroup.add(webcamToolGroup);
        
        
    }
    
    /**
     * Initializes floating components.
     */
    private void initFloatingComponents(){
        //BRIGHTNESS
        brightnessWindow = new FloatingWindow(brightnessBt);
        brightnessSlider = new JSlider(-255, 255, 0);
        brightnessWindow.getInternalPanel().add(brightnessSlider,BorderLayout.CENTER);
        brightnessWindow.getInternalPanel().add(new JLabel(new ImageIcon(getClass().getResource("/media/brightness_min.png"))),BorderLayout.WEST);
        brightnessWindow.getInternalPanel().add(new JLabel(new ImageIcon(getClass().getResource("/media/brightness_max.png"))),BorderLayout.EAST);     
        brightnessSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                brightnessSliderStateChanged(e);
            }
        });
        
        //ROTATIONS
        rotationWindow = new FloatingWindow(rotateBt);
        rotationSlider = new JSlider(0, 360, 0);
        rotationSlider.setMinorTickSpacing(90);
        rotationSlider.setPaintTicks(true);
        rotationSlider.setPreferredSize(new java.awt.Dimension(100, 36));
        rotationSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rotationSliderStateChanged(evt);
            }
        });
        rotationWindow.getInternalPanel().add(rotationSlider);
        
        exactRotationWindow = new FloatingWindow(rotateExactBt);
        exactRotationWindow.getInternalPanel().setLayout(new FlowLayout(FlowLayout.CENTER));
        
        rotate90Bt = new JButton();
        rotate90Bt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/rotacion90.png"))); // NOI18N
        rotate90Bt.setToolTipText("90 º (sentido horario)");
        rotate90Bt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotation90BtActionPerformed(evt);
            }
        });
        exactRotationWindow.getInternalPanel().add(rotate90Bt);
        
        rotate180Bt = new JButton();
        rotate180Bt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/rotacion180.png"))); // NOI18N
        rotate180Bt.setToolTipText("180 º");
        rotate180Bt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotation180BtActionPerformed(evt);
            }
        });
        exactRotationWindow.getInternalPanel().add(rotate180Bt);

        rotate270Bt = new JButton();
        rotate270Bt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/rotacion270.png"))); // NOI18N
        rotate270Bt.setToolTipText("90 º (sentido antihorario)");
        rotate270Bt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotation270BtActionPerformed(evt);
            }
        });
        exactRotationWindow.getInternalPanel().add(rotate270Bt);

       
        //TINT
        tintWindow = new FloatingWindow(tintBt);
        tintSlider = new JSlider(0, 100, 0);
        tintWindow.getInternalPanel().add(tintSlider,BorderLayout.CENTER);
        tintWindow.getInternalPanel().add(new JLabel(new ImageIcon(getClass().getResource("/media/tint_empty.png"))),BorderLayout.WEST);
        tintWindow.getInternalPanel().add(new JLabel(new ImageIcon(getClass().getResource("/media/tint_full.png"))),BorderLayout.EAST);     
        tintSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                tintSliderStateChanged(e);
            }

        });
        
        //THRESHOLD
        thresholdWindow = new FloatingWindow(umbralBt);
        thresholdSlider = new JSlider(0,255,128);
        thresholdWindow.getInternalPanel().add(thresholdSlider,BorderLayout.CENTER);
        thresholdWindow.getInternalPanel().add(new JLabel(new ImageIcon(getClass().getResource("/media/brightness_max.png"))),BorderLayout.WEST);
        thresholdWindow.getInternalPanel().add(new JLabel(new ImageIcon(getClass().getResource("/media/brightness_min.png"))),BorderLayout.EAST);     
        thresholdSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                thresholdSliderStateChanged(e);
            }

        });
        
        //SUM
        sumWindow = new FloatingWindow(sumBt);
        sumSlider = new JSlider(0,100,50);
        sumWindow.getInternalPanel().add(sumSlider,BorderLayout.CENTER);
        sumWindow.getInternalPanel().add(new JLabel(new ImageIcon(getClass().getResource("/media/one.png"))),BorderLayout.WEST);
        sumWindow.getInternalPanel().add(new JLabel(new ImageIcon(getClass().getResource("/media/two.png"))),BorderLayout.EAST);
        sumSlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                sumSliderStateChanged(e);
            }
        });
        
        //BACKGROUND REMOVE
        backgroundRemoveWindow = new FloatingWindow(removeBackgroundBt);
        backgroundRemoveSlider = new JSlider(0,255,0);
        backgroundRemoveWindow.getInternalPanel().add(backgroundRemoveSlider,BorderLayout.CENTER);
        backgroundRemoveWindow.getInternalPanel().add(new JLabel(new ImageIcon(getClass().getResource("/media/opaque.png"))),BorderLayout.WEST);
        backgroundRemoveWindow.getInternalPanel().add(new JLabel(new ImageIcon(getClass().getResource("/media/transparent.png"))),BorderLayout.EAST);
        backgroundRemoveSlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                backgroundRemoveSliderStateChanged(e);
            }
        });
        
        transparencyWindow = new FloatingWindow(transparencyBt);
        transparencySlider = new JSlider(0,100,100);
        transparencyWindow.getInternalPanel().add(transparencySlider,BorderLayout.CENTER);
        transparencyWindow.getInternalPanel().add(new JLabel(new ImageIcon(getClass().getResource("/media/opaque.png"))),BorderLayout.EAST);
        transparencyWindow.getInternalPanel().add(new JLabel(new ImageIcon(getClass().getResource("/media/transparent.png"))),BorderLayout.WEST);
        transparencySlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                transparencySliderStateChanged(e);
            }
        });
        
        transformWindow = new FloatingWindow(transformBt);
        shapeRotationSlider = new JSlider(0,360,0);
        shapeScaleSlider = new JSlider(-500,500,0);
        JPanel upper = new JPanel(), lower = new JPanel();
        upper.setLayout(new BorderLayout());
        lower.setLayout(new BorderLayout());
        transformWindow.getInternalPanel().add(upper,BorderLayout.NORTH);
        transformWindow.getInternalPanel().add(lower,BorderLayout.SOUTH);
        upper.add(new JLabel(new ImageIcon(getClass().getResource("/media/shape_rotate.png"))),BorderLayout.WEST);
        lower.add(new JLabel(new ImageIcon(getClass().getResource("/media/shape_scale.png"))),BorderLayout.WEST);
        upper.add(shapeRotationSlider,BorderLayout.CENTER);
        lower.add(shapeScaleSlider,BorderLayout.CENTER);
        shapeRotationSlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                shapeRotationSliderStateChanged(e);
            }
        });
        shapeScaleSlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                shapeScaleSliderStateChanged(e);
            }
        });
        
        strokeWindow = new FloatingWindow(strokeBt);
        strokeWindow.getInternalPanel().add(strokeToolBar, BorderLayout.CENTER);
        
        
        
        //MORE EFFECTS
        moreEffectsWindow = new FloatingWindow(moreEffectsBt);
        JToolBar effectsToolBar = new JToolBar();
        effectsToolBar.add(sineBt);
        effectsToolBar.add(sepiaBt);
        effectsToolBar.add(equalizationBt);
        effectsToolBar.add(histogramBt);
        effectsToolBar.add(sobelBt);
        effectsToolBar.add(atanBt);
        effectsToolBar.add(gaussBt);
        effectsToolBar.add(modularTranslationBt);
        effectsToolBar.add(fireEffectBt);
        effectsToolBar.add(coldEffectBt);
        moreEffectsWindow.getInternalPanel().add(effectsToolBar,BorderLayout.CENTER);
        
        moreOperationsWindow = new FloatingWindow(moreOperationsBt);
        moreOperationsWindow.getInternalPanel().add(shapeOperationsToolBar);
        
        modularTranslationWindow = new FloatingWindow(modularTranslationBt);
        modularTranslationSlider = new JSlider(0,255,0);
        modularTranslationWindow.getInternalPanel().add(modularTranslationSlider);
        
        modularTranslationSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                modularTranslationSliderStateChanged(e);
            }
        });
        
        shapesWindow = new FloatingWindow(shapesBt);
        JToolBar shapesToolbar = new JToolBar();
        shapesWindow.getInternalPanel().add(shapesToolbar);
        shapesToolbar.add(pointBt);
        shapesToolbar.add(lineBt);
        shapesToolbar.add(rectangleBt);
        shapesToolbar.add(ovalBt);
        shapesToolbar.add(roundRectangleBt);
        shapesToolbar.add(quadCurveBt);
        shapesToolbar.add(cubicCurveBt);
        shapesToolbar.add(polygonBt);
        shapesToolbar.add(arcBt);
        shapesToolbar.add(sunBt);
        shapesToolbar.add(editBt);
    }
    

    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setImageView(Canvas2D canvas) {
        preventer = true;
        BasicAttributePainter painter;
        if(canvas.isEditing() && !canvas.getSelection().isEmpty()){ // Set the view with the last shape values.
            painter = canvas.getSelection().get(canvas.getSelection().size()-1).getPainter();   
        }
        else{ // Set the view with the current canvas values.
            painter = canvas;
        }
        
        this.primaryColorList.setSelectedItem(painter.getPrimaryColor());
        this.secondaryColorList.setSelectedItem(painter.getSecondaryColor());

        this.thicknessSpin.setValue(painter.getThickness());
        this.fillComboBox.setSelectedIndex(painter.getFillMode());
        this.transparencySlider.setValue((int)(painter.getTransparency()*100));
        this.flattenBt.setSelected(painter.getFlatten());
        this.strokeTypeList.setSelectedItem(painter.getStrokeType());
        this.shapeRotationSlider.setValue((int)Math.toDegrees(painter.getRotation()));
        this.shapeScaleSlider.setValue((int)(100*Math.log(painter.getScale())/Math.log(2)));
        
        switch(canvas.getCurrentTool()){
            case POINT:
                pointBt.setSelected(true);
                shapesComboBox.setSelectedIndex(0);
                shapesBt.setIcon(pointBt.getIcon());
                break;
            case LINE:
                lineBt.setSelected(true);
                shapesComboBox.setSelectedIndex(1);
                shapesBt.setIcon(lineBt.getIcon());
                break;
            case RECTANGLE:
                rectangleBt.setSelected(true);
                shapesComboBox.setSelectedIndex(2);
                shapesBt.setIcon(rectangleBt.getIcon());
                break;
            case OVAL:
                ovalBt.setSelected(true);
                shapesComboBox.setSelectedIndex(3);
                shapesBt.setIcon(ovalBt.getIcon());
                break;
            case ROUND_RECTANGLE:
                roundRectangleBt.setSelected(true);
                shapesComboBox.setSelectedIndex(4);
                shapesBt.setIcon(roundRectangleBt.getIcon());
                break;
            case QUAD_CURVE:
                quadCurveBt.setSelected(true);
                shapesComboBox.setSelectedIndex(5);
                shapesBt.setIcon(quadCurveBt.getIcon());
                break;
            case CUBIC_CURVE:
                cubicCurveBt.setSelected(true);
                shapesComboBox.setSelectedIndex(6);
                shapesBt.setIcon(cubicCurveBt.getIcon());
                break;
            case POLYGON:
                polygonBt.setSelected(true);
                shapesComboBox.setSelectedIndex(7);
                shapesBt.setIcon(polygonBt.getIcon());
                break;
            case ARC:
                arcBt.setSelected(true);
                shapesComboBox.setSelectedIndex(8);
                shapesBt.setIcon(arcBt.getIcon());
                break;
            case AREA_SUN:
                sunBt.setSelected(true);
                shapesComboBox.setSelectedIndex(9);
                shapesBt.setIcon(sunBt.getIcon());
                break;
        }
        this.editBt.setSelected(canvas.isEditing());
        if(canvas.isEditing()){
            shapesComboBox.setSelectedIndex(10);
            shapesBt.setIcon(editBt.getIcon());
        }
        preventer=false;
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public void setAudioVideoView(boolean playing){
        if(playing){
            playPauseBt.setIcon(new ImageIcon(getClass().getResource("/media/pausa.png")));
            playPauseBt.setToolTipText("Pausa");
            audioBox.setEnabled(false);
            audioBox.setToolTipText("Reproducción en curso");
        }
        else{
            playPauseBt.setIcon(new ImageIcon(getClass().getResource("/media/play.png")));
            playPauseBt.setToolTipText("Reproducir");
            audioBox.setEnabled(true);
            audioBox.setToolTipText("Lista de reproducción");
        }
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public void setAudioVideoView(boolean playing, long current, long duration){
        setAudioVideoView(playing);
        this.clipSlider.setCurrentTime(current);
        this.clipSlider.setDurationTime(duration);
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public void setAudioVideoView(boolean playing, float position){
        setAudioVideoView(playing);
        this.clipSlider.setRelativeTime(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPaintToolStateBar(PaintTool t) {
        this.stateBar.setTool(t); 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPointsStateBar(Point p0, Point p1) {
        this.stateBar.setPoints(p0, p1);
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void setColorStateBar(Integer rgb){
        this.stateBar.setColor(rgb);
    }
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        shapesBtGroup = new javax.swing.ButtonGroup();
        sineBt = new javax.swing.JButton();
        sepiaBt = new javax.swing.JButton();
        equalizationBt = new javax.swing.JButton();
        histogramBt = new javax.swing.JButton();
        sobelBt = new javax.swing.JButton();
        atanBt = new javax.swing.JButton();
        gaussBt = new javax.swing.JButton();
        pointBt = new javax.swing.JToggleButton();
        lineBt = new javax.swing.JToggleButton();
        rectangleBt = new javax.swing.JToggleButton();
        ovalBt = new javax.swing.JToggleButton();
        roundRectangleBt = new javax.swing.JToggleButton();
        quadCurveBt = new javax.swing.JToggleButton();
        cubicCurveBt = new javax.swing.JToggleButton();
        polygonBt = new javax.swing.JToggleButton();
        arcBt = new javax.swing.JToggleButton();
        editBt = new javax.swing.JToggleButton();
        strokeToolBar = new javax.swing.JToolBar();
        thicknessSpin = new javax.swing.JSpinner();
        strokeTypeList = new javax.swing.JComboBox(BasicStrokeType.values());
        shapeOperationsToolBar = new javax.swing.JToolBar();
        flattenBt = new javax.swing.JToggleButton();
        toBottomBt = new javax.swing.JButton();
        toTopBt = new javax.swing.JButton();
        backBt = new javax.swing.JButton();
        forwardBt = new javax.swing.JButton();
        modularTranslationBt = new javax.swing.JToggleButton();
        fireEffectBt = new javax.swing.JButton();
        coldEffectBt = new javax.swing.JButton();
        sunBt = new javax.swing.JToggleButton();
        shapesComboBox = new javax.swing.JComboBox(shapes_images);
        toolBar = new javax.swing.JToolBar();
        newBt = new javax.swing.JButton();
        openBt = new javax.swing.JButton();
        saveBt = new javax.swing.JButton();
        shapeSeparator = new javax.swing.JToolBar.Separator();
        shapesBt = new javax.swing.JToggleButton();
        colorSeparator = new javax.swing.JToolBar.Separator();
        Color colors[] = {Color.BLACK, Color.WHITE, Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN};
        primaryColorList = new javax.swing.JComboBox<Color>(colors);
        secondaryColorList = new javax.swing.JComboBox<Color>(colors);
        addColorBt = new javax.swing.JButton();
        attributesSeparator = new javax.swing.JToolBar.Separator();
        strokeBt = new javax.swing.JToggleButton();
        fillComboBox = new javax.swing.JComboBox(fill_images);
        transparencyBt = new javax.swing.JToggleButton();
        transformBt = new javax.swing.JToggleButton();
        moreOperationsBt = new javax.swing.JToggleButton();
        brightnessSeparator = new javax.swing.JToolBar.Separator();
        brightnessBt = new javax.swing.JToggleButton();
        contrastComboBox = new javax.swing.JComboBox(contrast_images);
        filterSeparator = new javax.swing.JToolBar.Separator();
        filterBox = new javax.swing.JComboBox<>();
        effectsSeparator = new javax.swing.JToolBar.Separator();
        tintBt = new javax.swing.JToggleButton();
        umbralBt = new javax.swing.JToggleButton();
        removeBackgroundBt = new javax.swing.JToggleButton();
        moreEffectsBt = new javax.swing.JToggleButton();
        colorSpaceSeparator = new javax.swing.JToolBar.Separator();
        bandsBt = new javax.swing.JButton();
        colorSpaceBox = new javax.swing.JComboBox<>();
        transformSeparator = new javax.swing.JToolBar.Separator();
        rotateBt = new javax.swing.JToggleButton();
        rotateExactBt = new javax.swing.JToggleButton();
        scalePlusBt = new javax.swing.JButton();
        scaleMinusBt = new javax.swing.JButton();
        binarySeparator = new javax.swing.JToolBar.Separator();
        sumBt = new javax.swing.JToggleButton();
        diffBt = new javax.swing.JButton();
        productBt = new javax.swing.JButton();
        playerSeparator = new javax.swing.JToolBar.Separator();
        playPauseBt = new javax.swing.JButton();
        stopBt = new javax.swing.JButton();
        audioBox = new javax.swing.JComboBox<File>();
        playingSeparator = new javax.swing.JToolBar.Separator();
        clipSlider = new sm.jlsd.ui.ClipSlider();
        recordSeparator = new javax.swing.JToolBar.Separator();
        recordBt = new javax.swing.JButton();
        recordTimeLabel = new javax.swing.JLabel();
        webcamSeparator = new javax.swing.JToolBar.Separator();
        webcamBt = new javax.swing.JButton();
        snapshotBt = new javax.swing.JButton();
        lowerPanel = new javax.swing.JPanel();
        stateBar = new paint2d.StateBar();
        desktop = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newMenuItem = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        duplicateMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        stateBarMenuItem = new javax.swing.JCheckBoxMenuItem();
        filesBarMenuItem = new javax.swing.JCheckBoxMenuItem();
        graphicsBarMenu = new javax.swing.JMenu();
        shapeBarMenuItem = new javax.swing.JCheckBoxMenuItem();
        colorBarMenuItem = new javax.swing.JCheckBoxMenuItem();
        attrBarMenuItem = new javax.swing.JCheckBoxMenuItem();
        barMenuSeparator = new javax.swing.JPopupMenu.Separator();
        showAllGraphicsBar = new javax.swing.JMenuItem();
        hideAllGraphicsBar = new javax.swing.JMenuItem();
        imageBarMenu = new javax.swing.JMenu();
        brightBarMenuItem = new javax.swing.JCheckBoxMenuItem();
        convolveBarMenuItem = new javax.swing.JCheckBoxMenuItem();
        effectsBarMenuItem = new javax.swing.JCheckBoxMenuItem();
        colorSpaceBarMenuItem = new javax.swing.JCheckBoxMenuItem();
        transformBarMenuItem = new javax.swing.JCheckBoxMenuItem();
        binaryBarMenuItem = new javax.swing.JCheckBoxMenuItem();
        barMenuSeparator1 = new javax.swing.JPopupMenu.Separator();
        showAllImageBar = new javax.swing.JMenuItem();
        hideAllImageBar = new javax.swing.JMenuItem();
        avBarMenu = new javax.swing.JMenu();
        controlBarMenuItem = new javax.swing.JCheckBoxMenuItem();
        playBarMenuItem = new javax.swing.JCheckBoxMenuItem();
        recordBarMenuItem = new javax.swing.JCheckBoxMenuItem();
        webcamBarMenuItem = new javax.swing.JCheckBoxMenuItem();
        barMenuSeparator2 = new javax.swing.JPopupMenu.Separator();
        showAllAvBar = new javax.swing.JMenuItem();
        hideAllAvBar = new javax.swing.JMenuItem();
        imageMenu = new javax.swing.JMenu();
        changeImgSizeMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        sineBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/sinusoidal.png"))); // NOI18N
        sineBt.setToolTipText("Efecto seno");
        sineBt.setPreferredSize(new java.awt.Dimension(40, 30));
        sineBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sineBtActionPerformed(evt);
            }
        });

        sepiaBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/sepia.png"))); // NOI18N
        sepiaBt.setToolTipText("Sepia");
        sepiaBt.setPreferredSize(new java.awt.Dimension(40, 30));
        sepiaBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sepiaBtActionPerformed(evt);
            }
        });

        equalizationBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/ecualizar.png"))); // NOI18N
        equalizationBt.setToolTipText("Ecualización");
        equalizationBt.setFocusable(false);
        equalizationBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        equalizationBt.setPreferredSize(new java.awt.Dimension(40, 30));
        equalizationBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        equalizationBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equalizationBtActionPerformed(evt);
            }
        });

        histogramBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/histogram.png"))); // NOI18N
        histogramBt.setToolTipText("Histograma");
        histogramBt.setFocusable(false);
        histogramBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        histogramBt.setPreferredSize(new java.awt.Dimension(40, 30));
        histogramBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        histogramBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                histogramBtActionPerformed(evt);
            }
        });

        sobelBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/gradient.png"))); // NOI18N
        sobelBt.setToolTipText("Sobel");
        sobelBt.setFocusable(false);
        sobelBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sobelBt.setPreferredSize(new java.awt.Dimension(40, 30));
        sobelBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sobelBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sobelBtActionPerformed(evt);
            }
        });

        atanBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/atan.png"))); // NOI18N
        atanBt.setToolTipText("Efecto arcotangente");
        atanBt.setFocusable(false);
        atanBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        atanBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        atanBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atanBtActionPerformed(evt);
            }
        });

        gaussBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/gauss.png"))); // NOI18N
        gaussBt.setToolTipText("Efecto campana de Gauss");
        gaussBt.setFocusable(false);
        gaussBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gaussBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gaussBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gaussBtActionPerformed(evt);
            }
        });

        shapesBtGroup.add(pointBt);
        pointBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/punto.png"))); // NOI18N
        pointBt.setSelected(true);
        pointBt.setToolTipText("Lápiz");
        pointBt.setFocusable(false);
        pointBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pointBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pointBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pointBtActionPerformed(evt);
            }
        });

        shapesBtGroup.add(lineBt);
        lineBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/linea.png"))); // NOI18N
        lineBt.setToolTipText("Línea");
        lineBt.setFocusable(false);
        lineBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lineBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lineBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineBtActionPerformed(evt);
            }
        });

        shapesBtGroup.add(rectangleBt);
        rectangleBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/rectangulo.png"))); // NOI18N
        rectangleBt.setToolTipText("Rectángulo");
        rectangleBt.setFocusable(false);
        rectangleBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rectangleBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rectangleBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rectangleBtActionPerformed(evt);
            }
        });

        shapesBtGroup.add(ovalBt);
        ovalBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/elipse.png"))); // NOI18N
        ovalBt.setToolTipText("Elipse");
        ovalBt.setFocusable(false);
        ovalBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ovalBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ovalBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ovalBtActionPerformed(evt);
            }
        });

        shapesBtGroup.add(roundRectangleBt);
        roundRectangleBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/round_rectangle.png"))); // NOI18N
        roundRectangleBt.setToolTipText("Rectángulo redondeado");
        roundRectangleBt.setFocusable(false);
        roundRectangleBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        roundRectangleBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        roundRectangleBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundRectangleBtActionPerformed(evt);
            }
        });

        shapesBtGroup.add(quadCurveBt);
        quadCurveBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/quad_curve.png"))); // NOI18N
        quadCurveBt.setToolTipText("Curva cuadrática");
        quadCurveBt.setFocusable(false);
        quadCurveBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        quadCurveBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        quadCurveBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quadCurveBtActionPerformed(evt);
            }
        });

        shapesBtGroup.add(cubicCurveBt);
        cubicCurveBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/cubic_curve.png"))); // NOI18N
        cubicCurveBt.setToolTipText("Curva cúbica");
        cubicCurveBt.setFocusable(false);
        cubicCurveBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cubicCurveBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cubicCurveBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cubicCurveBtActionPerformed(evt);
            }
        });

        shapesBtGroup.add(polygonBt);
        polygonBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/polygon.png"))); // NOI18N
        polygonBt.setToolTipText("Polígono");
        polygonBt.setFocusable(false);
        polygonBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        polygonBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        polygonBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                polygonBtActionPerformed(evt);
            }
        });

        shapesBtGroup.add(arcBt);
        arcBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/arc.png"))); // NOI18N
        arcBt.setToolTipText("Arco");
        arcBt.setFocusable(false);
        arcBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        arcBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        arcBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arcBtActionPerformed(evt);
            }
        });

        shapesBtGroup.add(editBt);
        editBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/seleccion.png"))); // NOI18N
        editBt.setToolTipText("Selección");
        editBt.setFocusable(false);
        editBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtActionPerformed(evt);
            }
        });

        strokeToolBar.setRollover(true);

        thicknessSpin.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(50.0f), Float.valueOf(1.0f)));
        thicknessSpin.setToolTipText("Grosor del trazo");
        thicknessSpin.setPreferredSize(new java.awt.Dimension(48, 32));
        thicknessSpin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                thicknessSpinStateChanged(evt);
            }
        });
        strokeToolBar.add(thicknessSpin);

        strokeTypeList.setSelectedItem(BasicStrokeType.STROKE_CONTINUOUS);
        strokeTypeList.setToolTipText("Estilo del trazo");
        strokeTypeList.setRenderer(new LineListCellRenderer()
        );
        strokeTypeList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                strokeTypeListActionPerformed(evt);
            }
        });
        strokeToolBar.add(strokeTypeList);

        shapeOperationsToolBar.setRollover(true);

        flattenBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/alisar.png"))); // NOI18N
        flattenBt.setToolTipText("Alisar");
        flattenBt.setFocusable(false);
        flattenBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        flattenBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        flattenBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flattenBtActionPerformed(evt);
            }
        });
        shapeOperationsToolBar.add(flattenBt);

        toBottomBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/to_bottom.png"))); // NOI18N
        toBottomBt.setToolTipText("Enviar al fondo");
        toBottomBt.setFocusable(false);
        toBottomBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toBottomBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toBottomBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toBottomBtActionPerformed(evt);
            }
        });
        shapeOperationsToolBar.add(toBottomBt);

        toTopBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/to_top.png"))); // NOI18N
        toTopBt.setToolTipText("Traer al frente");
        toTopBt.setFocusable(false);
        toTopBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toTopBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toTopBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toTopBtActionPerformed(evt);
            }
        });
        shapeOperationsToolBar.add(toTopBt);

        backBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/send_back.png"))); // NOI18N
        backBt.setToolTipText("Enviar atrás");
        backBt.setFocusable(false);
        backBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        backBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        backBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtActionPerformed(evt);
            }
        });
        shapeOperationsToolBar.add(backBt);

        forwardBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/bring_forward.png"))); // NOI18N
        forwardBt.setToolTipText("Traer adelante");
        forwardBt.setFocusable(false);
        forwardBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        forwardBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        forwardBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardBtActionPerformed(evt);
            }
        });
        shapeOperationsToolBar.add(forwardBt);

        modularTranslationBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/modular_translation.png"))); // NOI18N
        modularTranslationBt.setToolTipText("Efecto traslación modular");
        modularTranslationBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modularTranslationBtActionPerformed(evt);
            }
        });

        fireEffectBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/fire.png"))); // NOI18N
        fireEffectBt.setToolTipText("Efecto fuego");
        fireEffectBt.setFocusable(false);
        fireEffectBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        fireEffectBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        fireEffectBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fireEffectBtActionPerformed(evt);
            }
        });

        coldEffectBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/cold.png"))); // NOI18N
        coldEffectBt.setToolTipText("Efecto frío");
        coldEffectBt.setFocusable(false);
        coldEffectBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        coldEffectBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        coldEffectBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coldEffectBtActionPerformed(evt);
            }
        });

        shapesBtGroup.add(sunBt);
        sunBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/sun.png"))); // NOI18N
        sunBt.setToolTipText("Área: sol");
        sunBt.setFocusable(false);
        sunBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sunBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sunBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sunBtActionPerformed(evt);
            }
        });

        shapesComboBox.setToolTipText("Herramienta");
        shapesComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shapesComboBoxActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(Config.PROGRAM_NAME);
        setPreferredSize(new java.awt.Dimension(1400, 800));

        toolBar.setRollover(true);

        newBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/nuevo.png"))); // NOI18N
        newBt.setToolTipText("Nuevo");
        newBt.setFocusable(false);
        newBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        newBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtActionPerformed(evt);
            }
        });
        toolBar.add(newBt);

        openBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/abrir.png"))); // NOI18N
        openBt.setToolTipText("Abrir");
        openBt.setFocusable(false);
        openBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        openBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openBtActionPerformed(evt);
            }
        });
        toolBar.add(openBt);

        saveBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/guardar.png"))); // NOI18N
        saveBt.setToolTipText("Guardar");
        saveBt.setFocusable(false);
        saveBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtActionPerformed(evt);
            }
        });
        toolBar.add(saveBt);
        toolBar.add(shapeSeparator);

        shapesBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/punto.png"))); // NOI18N
        shapesBt.setToolTipText("Seleccionar herramientas");
        shapesBt.setFocusable(false);
        shapesBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        shapesBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        shapesBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shapesBtActionPerformed(evt);
            }
        });
        toolBar.add(shapesBt);
        toolBar.add(colorSeparator);

        primaryColorList.setSelectedItem(Color.BLACK);
        primaryColorList.setToolTipText("Color primario");
        primaryColorList.setRenderer(new ColorListCellRenderer());
        primaryColorList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                primaryColorListActionPerformed(evt);
            }
        });
        toolBar.add(primaryColorList);

        secondaryColorList.setSelectedItem(Color.WHITE);
        secondaryColorList.setToolTipText("Color secundario");
        secondaryColorList.setRenderer(new ColorListCellRenderer());
        secondaryColorList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secondaryColorListActionPerformed(evt);
            }
        });
        toolBar.add(secondaryColorList);

        addColorBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/palette.png"))); // NOI18N
        addColorBt.setToolTipText("Añadir color");
        addColorBt.setFocusable(false);
        addColorBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addColorBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addColorBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addColorBtActionPerformed(evt);
            }
        });
        toolBar.add(addColorBt);
        toolBar.add(attributesSeparator);

        strokeBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/stroke.png"))); // NOI18N
        strokeBt.setToolTipText("Trazo");
        strokeBt.setFocusable(false);
        strokeBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        strokeBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        strokeBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                strokeBtActionPerformed(evt);
            }
        });
        toolBar.add(strokeBt);

        fillComboBox.setToolTipText("Tipo de relleno");
        fillComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fillComboBoxActionPerformed(evt);
            }
        });
        toolBar.add(fillComboBox);

        transparencyBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/transparencia.png"))); // NOI18N
        transparencyBt.setToolTipText("Transparencia");
        transparencyBt.setFocusable(false);
        transparencyBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        transparencyBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        transparencyBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transparencyBtActionPerformed(evt);
            }
        });
        toolBar.add(transparencyBt);

        transformBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/transform.png"))); // NOI18N
        transformBt.setToolTipText("Transformaciones");
        transformBt.setFocusable(false);
        transformBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        transformBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        transformBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transformBtActionPerformed(evt);
            }
        });
        toolBar.add(transformBt);

        moreOperationsBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/menu.png"))); // NOI18N
        moreOperationsBt.setToolTipText("Más operaciones");
        moreOperationsBt.setFocusable(false);
        moreOperationsBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        moreOperationsBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        moreOperationsBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moreOperationsBtActionPerformed(evt);
            }
        });
        toolBar.add(moreOperationsBt);
        toolBar.add(brightnessSeparator);

        brightnessBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/brightness.png"))); // NOI18N
        brightnessBt.setToolTipText("Brillo");
        brightnessBt.setFocusable(false);
        brightnessBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        brightnessBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        brightnessBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brightnessBtActionPerformed(evt);
            }
        });
        toolBar.add(brightnessBt);

        contrastComboBox.setToolTipText("Contraste");
        contrastComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contrastComboBoxActionPerformed(evt);
            }
        });
        toolBar.add(contrastComboBox);
        toolBar.add(filterSeparator);

        filterBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Media 3x3", "Binomial", "Enfoque", "Relieve", "Laplaciano", "Media 5x5", "Media 7x7" }));
        filterBox.setToolTipText("Filtro");
        filterBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterBoxActionPerformed(evt);
            }
        });
        toolBar.add(filterBox);
        toolBar.add(effectsSeparator);

        tintBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/tintar.png"))); // NOI18N
        tintBt.setToolTipText("Tintado");
        tintBt.setFocusable(false);
        tintBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tintBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tintBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tintBtActionPerformed(evt);
            }
        });
        toolBar.add(tintBt);

        umbralBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/threshold.png"))); // NOI18N
        umbralBt.setToolTipText("Umbralización");
        umbralBt.setFocusable(false);
        umbralBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        umbralBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        umbralBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                umbralBtActionPerformed(evt);
            }
        });
        toolBar.add(umbralBt);

        removeBackgroundBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/background_remove.png"))); // NOI18N
        removeBackgroundBt.setToolTipText("Eliminar fondo");
        removeBackgroundBt.setFocusable(false);
        removeBackgroundBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeBackgroundBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeBackgroundBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBackgroundBtActionPerformed(evt);
            }
        });
        toolBar.add(removeBackgroundBt);

        moreEffectsBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/menu.png"))); // NOI18N
        moreEffectsBt.setToolTipText("Más efectos");
        moreEffectsBt.setFocusable(false);
        moreEffectsBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        moreEffectsBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        moreEffectsBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moreEffectsBtActionPerformed(evt);
            }
        });
        toolBar.add(moreEffectsBt);
        toolBar.add(colorSpaceSeparator);

        bandsBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/bandas.png"))); // NOI18N
        bandsBt.setToolTipText("Ventanas de bandas");
        bandsBt.setPreferredSize(new java.awt.Dimension(40, 30));
        bandsBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bandsBtActionPerformed(evt);
            }
        });
        toolBar.add(bandsBt);

        colorSpaceBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "sRGB", "YCC", "GRAY" }));
        colorSpaceBox.setToolTipText("Espacio de color");
        colorSpaceBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorSpaceBoxActionPerformed(evt);
            }
        });
        toolBar.add(colorSpaceBox);
        toolBar.add(transformSeparator);

        rotateBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/rotate.png"))); // NOI18N
        rotateBt.setToolTipText("Rotación");
        rotateBt.setFocusable(false);
        rotateBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotateBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rotateBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateBtActionPerformed(evt);
            }
        });
        toolBar.add(rotateBt);

        rotateExactBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/rotate_exact.png"))); // NOI18N
        rotateExactBt.setToolTipText("Rotación exacta");
        rotateExactBt.setFocusable(false);
        rotateExactBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotateExactBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rotateExactBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateExactBtActionPerformed(evt);
            }
        });
        toolBar.add(rotateExactBt);

        scalePlusBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/aumentar.png"))); // NOI18N
        scalePlusBt.setToolTipText("Aumentar imagen");
        scalePlusBt.setPreferredSize(new java.awt.Dimension(36, 36));
        scalePlusBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scalePlusBtActionPerformed(evt);
            }
        });
        toolBar.add(scalePlusBt);

        scaleMinusBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/disminuir.png"))); // NOI18N
        scaleMinusBt.setToolTipText("Reducir imagen");
        scaleMinusBt.setPreferredSize(new java.awt.Dimension(36, 36));
        scaleMinusBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleMinusBtActionPerformed(evt);
            }
        });
        toolBar.add(scaleMinusBt);
        toolBar.add(binarySeparator);

        sumBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/plus.png"))); // NOI18N
        sumBt.setToolTipText("Mezcla de imágenes");
        sumBt.setFocusable(false);
        sumBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sumBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sumBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumBtActionPerformed(evt);
            }
        });
        toolBar.add(sumBt);

        diffBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/minus.png"))); // NOI18N
        diffBt.setToolTipText("Diferencia de imágenes");
        diffBt.setFocusable(false);
        diffBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        diffBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        diffBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diffBtActionPerformed(evt);
            }
        });
        toolBar.add(diffBt);

        productBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/product.png"))); // NOI18N
        productBt.setToolTipText("Producto de imágenes");
        productBt.setFocusable(false);
        productBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        productBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        productBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productBtActionPerformed(evt);
            }
        });
        toolBar.add(productBt);
        toolBar.add(playerSeparator);

        playPauseBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/play.png"))); // NOI18N
        playPauseBt.setToolTipText("Reproducir");
        playPauseBt.setFocusable(false);
        playPauseBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playPauseBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        playPauseBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playPauseBtActionPerformed(evt);
            }
        });
        toolBar.add(playPauseBt);

        stopBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/stop.png"))); // NOI18N
        stopBt.setToolTipText("Parar");
        stopBt.setFocusable(false);
        stopBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        stopBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        stopBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopBtActionPerformed(evt);
            }
        });
        toolBar.add(stopBt);

        audioBox.setToolTipText("Lista de reproducción");
        audioBox.setPreferredSize(new java.awt.Dimension(200, 24));
        toolBar.add(audioBox);
        toolBar.add(playingSeparator);

        clipSlider.addSlideMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                clipSliderSlided(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                clipSliderSlided(evt);
            }
        });
        toolBar.add(clipSlider);
        toolBar.add(recordSeparator);

        recordBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/record.png"))); // NOI18N
        recordBt.setToolTipText("Grabar");
        recordBt.setFocusable(false);
        recordBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        recordBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        recordBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordBtActionPerformed(evt);
            }
        });
        toolBar.add(recordBt);

        recordTimeLabel.setText("00:00");
        recordTimeLabel.setToolTipText("Tiempo de grabación");
        toolBar.add(recordTimeLabel);
        toolBar.add(webcamSeparator);

        webcamBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Camara.png"))); // NOI18N
        webcamBt.setToolTipText("Abrir webcam");
        webcamBt.setFocusable(false);
        webcamBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        webcamBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        webcamBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webcamBtActionPerformed(evt);
            }
        });
        toolBar.add(webcamBt);

        snapshotBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Capturar.png"))); // NOI18N
        snapshotBt.setToolTipText("Capturar instantánea");
        snapshotBt.setFocusable(false);
        snapshotBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        snapshotBt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        snapshotBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                snapshotBtActionPerformed(evt);
            }
        });
        toolBar.add(snapshotBt);

        getContentPane().add(toolBar, java.awt.BorderLayout.PAGE_START);

        lowerPanel.setLayout(new java.awt.BorderLayout());

        stateBar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        lowerPanel.add(stateBar, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(lowerPanel, java.awt.BorderLayout.PAGE_END);
        getContentPane().add(desktop, java.awt.BorderLayout.CENTER);

        fileMenu.setText("Archivo");

        newMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/nuevo.png"))); // NOI18N
        newMenuItem.setText("Nuevo");
        newMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(newMenuItem);

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/abrir.png"))); // NOI18N
        openMenuItem.setText("Abrir");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/guardar.png"))); // NOI18N
        saveMenuItem.setText("Guardar");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        duplicateMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        duplicateMenuItem.setText("Duplicar");
        duplicateMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                duplicateMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(duplicateMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText("Edición");

        stateBarMenuItem.setSelected(true);
        stateBarMenuItem.setText("Barra de estado");
        stateBarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateBarMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(stateBarMenuItem);

        filesBarMenuItem.setSelected(true);
        filesBarMenuItem.setText("Barra de archivos");
        filesBarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filesBarMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(filesBarMenuItem);

        graphicsBarMenu.setText("Barras de gráficos");

        shapeBarMenuItem.setSelected(true);
        shapeBarMenuItem.setText("Barra de formas");
        shapeBarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shapeBarMenuItemActionPerformed(evt);
            }
        });
        graphicsBarMenu.add(shapeBarMenuItem);

        colorBarMenuItem.setSelected(true);
        colorBarMenuItem.setText("Barra de color");
        colorBarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorBarMenuItemActionPerformed(evt);
            }
        });
        graphicsBarMenu.add(colorBarMenuItem);

        attrBarMenuItem.setSelected(true);
        attrBarMenuItem.setText("Barra de atributos");
        attrBarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attrBarMenuItemActionPerformed(evt);
            }
        });
        graphicsBarMenu.add(attrBarMenuItem);
        graphicsBarMenu.add(barMenuSeparator);

        showAllGraphicsBar.setText("Mostrar todas");
        showAllGraphicsBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAllGraphicsBarActionPerformed(evt);
            }
        });
        graphicsBarMenu.add(showAllGraphicsBar);

        hideAllGraphicsBar.setText("Ocultar todas");
        hideAllGraphicsBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hideAllGraphicsBarActionPerformed(evt);
            }
        });
        graphicsBarMenu.add(hideAllGraphicsBar);

        editMenu.add(graphicsBarMenu);

        imageBarMenu.setText("Barras de imagen");

        brightBarMenuItem.setSelected(true);
        brightBarMenuItem.setText("Barra de iluminación");
        brightBarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brightBarMenuItemActionPerformed(evt);
            }
        });
        imageBarMenu.add(brightBarMenuItem);

        convolveBarMenuItem.setSelected(true);
        convolveBarMenuItem.setText("Barra de convolución");
        convolveBarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                convolveBarMenuItemActionPerformed(evt);
            }
        });
        imageBarMenu.add(convolveBarMenuItem);

        effectsBarMenuItem.setSelected(true);
        effectsBarMenuItem.setText("Barra de efectos");
        effectsBarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                effectsBarMenuItemActionPerformed(evt);
            }
        });
        imageBarMenu.add(effectsBarMenuItem);

        colorSpaceBarMenuItem.setSelected(true);
        colorSpaceBarMenuItem.setText("Barra de espacios de color");
        colorSpaceBarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorSpaceBarMenuItemActionPerformed(evt);
            }
        });
        imageBarMenu.add(colorSpaceBarMenuItem);

        transformBarMenuItem.setSelected(true);
        transformBarMenuItem.setText("Barra de afinidades");
        transformBarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transformBarMenuItemActionPerformed(evt);
            }
        });
        imageBarMenu.add(transformBarMenuItem);

        binaryBarMenuItem.setSelected(true);
        binaryBarMenuItem.setText("Barra de operaciones binarias");
        binaryBarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                binaryBarMenuItemActionPerformed(evt);
            }
        });
        imageBarMenu.add(binaryBarMenuItem);
        imageBarMenu.add(barMenuSeparator1);

        showAllImageBar.setText("Mostrar todas");
        showAllImageBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAllImageBarActionPerformed(evt);
            }
        });
        imageBarMenu.add(showAllImageBar);

        hideAllImageBar.setText("Ocultar todas");
        hideAllImageBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hideAllImageBarActionPerformed(evt);
            }
        });
        imageBarMenu.add(hideAllImageBar);

        editMenu.add(imageBarMenu);

        avBarMenu.setText("Barras de audio y vídeo");

        controlBarMenuItem.setSelected(true);
        controlBarMenuItem.setText("Barra de control");
        controlBarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                controlBarMenuItemActionPerformed(evt);
            }
        });
        avBarMenu.add(controlBarMenuItem);

        playBarMenuItem.setSelected(true);
        playBarMenuItem.setText("Barra de reproducción");
        playBarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playBarMenuItemActionPerformed(evt);
            }
        });
        avBarMenu.add(playBarMenuItem);

        recordBarMenuItem.setSelected(true);
        recordBarMenuItem.setText("Barra de grabación");
        recordBarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordBarMenuItemActionPerformed(evt);
            }
        });
        avBarMenu.add(recordBarMenuItem);

        webcamBarMenuItem.setSelected(true);
        webcamBarMenuItem.setText("Barra de webcam");
        webcamBarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webcamBarMenuItemActionPerformed(evt);
            }
        });
        avBarMenu.add(webcamBarMenuItem);
        avBarMenu.add(barMenuSeparator2);

        showAllAvBar.setText("Mostrar todas");
        showAllAvBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAllAvBarActionPerformed(evt);
            }
        });
        avBarMenu.add(showAllAvBar);

        hideAllAvBar.setText("Ocultar todas");
        hideAllAvBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hideAllAvBarActionPerformed(evt);
            }
        });
        avBarMenu.add(hideAllAvBar);

        editMenu.add(avBarMenu);

        menuBar.add(editMenu);

        imageMenu.setText("Imagen");

        changeImgSizeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.ALT_MASK));
        changeImgSizeMenuItem.setText("Cambiar tamaño de imagen");
        changeImgSizeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeImgSizeMenuItemActionPerformed(evt);
            }
        });
        imageMenu.add(changeImgSizeMenuItem);

        menuBar.add(imageMenu);

        helpMenu.setText("Ayuda");

        aboutMenuItem.setText("Acerca de");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Action performed event handler for "new" menu option.
     * @param evt ActionEvent
     */
    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        ImageWindow iw = new ImageWindow(this);
        desktop.add(iw);
        iw.setVisible(true);
        
        BufferedImage img;
        img = new BufferedImage(defaultNewImgSize.width,defaultNewImgSize.height,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        JRectangle r = new JRectangle(img.getWidth(),img.getHeight());
        g2d.setPaint(Color.WHITE);
        g2d.fill(r);
        iw.getCanvas().setImage(img);
    }//GEN-LAST:event_newMenuItemActionPerformed

    /**
     * Obtains a string indicating the color space of a BufferedImage.
     * @param img BufferedImage.
     * @return String associated to the color space.
     */
    private String getColorSpaceString(BufferedImage img){
        int cs = img.getColorModel().getColorSpace().getType();
        switch(cs){
            case ColorSpace.CS_sRGB:
            case ColorSpace.CS_LINEAR_RGB:
            case ColorSpace.TYPE_RGB:
                return "RGB";
            case ColorSpace.CS_PYCC:
            case ColorSpace.TYPE_YCbCr:
                return "YCC";
            case ColorSpace.CS_CIEXYZ:
            case ColorSpace.TYPE_XYZ:
                return "XYZ";
            case ColorSpace.CS_GRAY:
            case ColorSpace.TYPE_GRAY:
                return "GRAY";
            case ColorSpace.TYPE_HSV:
                return "HSV";
            default:
                return "CS "+Integer.toString(cs);
        }
    }
    
    /**
     * Action performed event handler for "open" menu option.
     * @param evt ActionEvent
     */
    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        JFileChooser dlg = new JFileChooser();
        
        dlg.setFileFilter(imageFilter);
        dlg.setFileFilter(audioFilter);
        dlg.setFileFilter(videoFilter);
        dlg.setFileFilter(mediaFilter);
        
        dlg.setMultiSelectionEnabled(true);
        int resp = dlg.showOpenDialog(this);
        if( resp == JFileChooser.APPROVE_OPTION) {
            File [] files = dlg.getSelectedFiles();
            MediaWindowAdapter previous = null;
            for(File f: files){
                try{
                    // Open image.
                    if(imageFilter.accept(f)){
                        BufferedImage img = ImageIO.read(f);
                        
                        ImageWindow iw = new ImageWindow(this);
                        if(previous != null) translateNewWindow(previous, iw);
                        iw.getCanvas().setImage(img);
                        this.desktop.add(iw);
                        iw.setImageTitle(f.getName());
                        iw.setImageInfo("["+getColorSpaceString(img)+"]");
                        iw.setVisible(true);  
                        previous = iw;
                    }
                    // Open video and audio.
                    else if(videoFilter.accept(f) || audioFilter.accept(f)){
                        f = new File(f.getAbsolutePath()){
                            @Override
                            public String toString() {
                                return this.getName();
                            }       
                        };  
                        audioBox.addItem(f);
                        
                    }
                    else{
                        throw new UnsupportedOperationException("El formato del archivo no es reconocido como multimedia.");
                    }
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al abrir el archivo "+f.getPath()+": "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_openMenuItemActionPerformed

    /**
     * Action performed event handler for "save" menu option.
     * @param evt ActionEvent
     */
    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        MediaWindowAdapter iw = (MediaWindowAdapter) desktop.getSelectedFrame();
        if(iw != null){
            try{
                BufferedImage img = iw.getImage(true);
                if(img != null){
                    JFileChooser dlg = new JFileChooser();
                    FileFilter filter = new FileNameExtensionFilter("Archivos de imagen ["+String.join(",", ImageIO.getWriterFileSuffixes())+"]", ImageIO.getWriterFormatNames());
                    dlg.setFileFilter(filter);
                    dlg.setSelectedFile(new File(iw.getImageTitle()));

                    int resp = dlg.showSaveDialog(this);
                    if( resp == JFileChooser.APPROVE_OPTION) {

                        File f = dlg.getSelectedFile();
                        //Obtain extension
                        String ext = "";
                        int i_ext = f.getName().lastIndexOf('.');
                        if( i_ext > 0){
                            ext = f.getName().substring(i_ext+1);
                        }
                        //if(ext.equals("")){
                        //    ext = "jpg"; //Extensión por defecto
                        //    f.renameTo(new File(f.getName()+"."+ext);
                        //}
                        //
                        if(ImageIO.write(img, ext, f)){
                            iw.setTitle(f.getName());
                        }
                        else{
                            throw new  Exception("No se pudo escribir la imagen en el formato seleccionado.");
                        }
                    }
                }
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Error al guardar la imagen: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_saveMenuItemActionPerformed

    /**
     * Action performed event handler for "state bar" menu option.
     * @param evt ActionEvent
     */
    private void stateBarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateBarMenuItemActionPerformed
        this.stateBar.setVisible(this.stateBarMenuItem.isSelected());
    }//GEN-LAST:event_stateBarMenuItemActionPerformed

    /**
     * Action performed event handler for color selection.
     * @param evt ActionEvent
     */
    private void primaryColorListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_primaryColorListActionPerformed
        if(!preventer){
            MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
            if(currentWindow != null && currentWindow.getCanvas() != null) currentWindow.getCanvas().updatePrimaryColor((Color)primaryColorList.getSelectedItem());
        }
    }//GEN-LAST:event_primaryColorListActionPerformed

    /**
     * Action performed event handler for "new" menu option.
     * @param evt ActionEvent
     */
    private void newBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtActionPerformed
        this.newMenuItemActionPerformed(evt);
    }//GEN-LAST:event_newBtActionPerformed

    /**
     * Action performed event handler for "open" menu option.
     * @param evt ActionEvent
     */
    private void openBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openBtActionPerformed
        this.openMenuItemActionPerformed(evt);
    }//GEN-LAST:event_openBtActionPerformed

    /**
     * Action performed event handler for "save" menu option.
     * @param evt ActionEvent
     */
    private void saveBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtActionPerformed
        this.saveMenuItemActionPerformed(evt);
    }//GEN-LAST:event_saveBtActionPerformed

     /**
     * Action performed event handler for point button.
     * @param evt ActionEvent
     */
    private void pointBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pointBtActionPerformed
        MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
        if(currentWindow != null && currentWindow.getCanvas() != null){
            currentWindow.getCanvas().setCurrentTool(PaintTool.POINT);
            currentWindow.getCanvas().setEditing(false);
            setImageView(currentWindow.getCanvas());
        }
        stateBar.setTool(PaintTool.POINT);
        shapesBt.setIcon(pointBt.getIcon());
    }//GEN-LAST:event_pointBtActionPerformed

    /**
     * Action performed event handler for line button.
     * @param evt ActionEvent
     */
    private void lineBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineBtActionPerformed
        MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
         if(currentWindow != null && currentWindow.getCanvas() != null){
            currentWindow.getCanvas().setCurrentTool(PaintTool.LINE);
            currentWindow.getCanvas().setEditing(false);
            setImageView(currentWindow.getCanvas());
        }
        stateBar.setTool(PaintTool.LINE);
        shapesBt.setIcon(lineBt.getIcon());
    }//GEN-LAST:event_lineBtActionPerformed

    /**
     * Action performed event handler for rectangle button.
     * @param evt ActionEvent
     */
    private void rectangleBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rectangleBtActionPerformed
        MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
         if(currentWindow != null && currentWindow.getCanvas() != null){
            currentWindow.getCanvas().setCurrentTool(PaintTool.RECTANGLE);
            currentWindow.getCanvas().setEditing(false);
            setImageView(currentWindow.getCanvas());
        }
        stateBar.setTool(PaintTool.RECTANGLE);
        shapesBt.setIcon(rectangleBt.getIcon());
    }//GEN-LAST:event_rectangleBtActionPerformed

    /**
     * Action performed event handler for oval button.
     * @param evt ActionEvent
     */
    private void ovalBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ovalBtActionPerformed
        MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
         if(currentWindow != null && currentWindow.getCanvas() != null){
            currentWindow.getCanvas().setCurrentTool(PaintTool.OVAL);
            currentWindow.getCanvas().setEditing(false);
            setImageView(currentWindow.getCanvas());
        }
        stateBar.setTool(PaintTool.OVAL);
        shapesBt.setIcon(ovalBt.getIcon());
    }//GEN-LAST:event_ovalBtActionPerformed

    /**
     * Action performed event handler for edit button.
     * @param evt ActionEvent
     */
    private void editBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtActionPerformed
        MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
        if(currentWindow != null && currentWindow.getCanvas() != null){
            currentWindow.getCanvas().setEditing(true);
            setImageView(currentWindow.getCanvas());
        }
        stateBar.setTool(null);
        shapesBt.setIcon(editBt.getIcon());
    }//GEN-LAST:event_editBtActionPerformed

    /**
     * State changed event handler for the thickness spinner.
     * @param evt ChangeEvent.
     */
    private void thicknessSpinStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_thicknessSpinStateChanged
        if(!preventer){
            MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
            if(currentWindow != null && currentWindow.getCanvas() != null) currentWindow.getCanvas().updateThickness((float) thicknessSpin.getValue());
        }
    }//GEN-LAST:event_thicknessSpinStateChanged

    /**
     * Action performed event handler for transparency button.
     * @param evt ActionEvent
     */
    private void transparencyBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transparencyBtActionPerformed
        transparencyWindow.setVisible(transparencyBt.isSelected());
        //if(currentWindow != null && currentWindow.getCanvas() != null) currentWindow.getCanvas().setTransparency(transparencyBt.isSelected());
    }//GEN-LAST:event_transparencyBtActionPerformed

    /**
     * Action performed event handler for flatten button.
     * @param evt ActionEvent
     */
    private void flattenBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flattenBtActionPerformed
        MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
        if(currentWindow != null && currentWindow.getCanvas() != null) currentWindow.getCanvas().updateFlatten(flattenBt.isSelected());
    }//GEN-LAST:event_flattenBtActionPerformed

    
    /**
     * State changed event handler for brightness slider.
     * @param evt ChangeEvent.
     */
    private void brightnessSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                               
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        int value = brightnessSlider.getValue();
         if(iw != null){
            if(imgSource != null && iw.getImage() != null){
                try{
                    RescaleOp rop = null;
                    if(imgSource.getColorModel().hasAlpha()){
                        rop = new RescaleOp(new float[]{1.0F,1.0F,1.0F,1.0F}, new float[]{value,value,value,0.0F},null); 
                    }
                    else{
                        rop = new RescaleOp(1.0F, value,null); 
                    }
                    rop.filter(imgSource, iw.getImage());
                    desktop.repaint();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al aplicar el filtro: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
            
        }
    } 
    
    /**
     * Action performed event handler for filter combobox.
     * @param evt ActionEvent
     */
    private void filterBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterBoxActionPerformed
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        
        if(iw != null && iw.getCanvas() != null){
            Kernel k = null;
            switch((String)filterBox.getSelectedItem()){
                case "Media":
                case "Media 3x3":
                    k = KernelProducer.createKernel(KernelProducer.TYPE_MEDIA_3x3);
                    break;
                case "Binomial":
                    k = KernelProducer.createKernel(KernelProducer.TYPE_BINOMIAL_3x3);
                    break;
                case "Enfoque":
                    k = KernelProducer.createKernel(KernelProducer.TYPE_ENFOQUE_3x3);
                    break;
                case "Relieve":
                    k = KernelProducer.createKernel(KernelProducer.TYPE_RELIEVE_3x3);
                    break;
                case "Laplaciano":
                    k = KernelProducer.createKernel(KernelProducer.TYPE_LAPLACIANA_3x3);
                    break;
                case "Media 5x5":
                    k = JKernelProducer.createAverageKernel(5,5);
                    break;
                case "Media 7x7":
                    k = JKernelProducer.createAverageKernel(7,7);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "No se ha especificado un filtro válido.","Filtro inválido",JOptionPane.ERROR_MESSAGE);
                    return;        
            }

            if(iw.getImage() != null){
                imgSource = ImageUtils.copyImage(iw.getImage());
                try{
                    ConvolveOp cop = new ConvolveOp(k,ConvolveOp.EDGE_NO_OP,null);
                    BufferedImage imgdest = cop.filter(imgSource, iw.getImage());
                    iw.getCanvas().setImage(imgdest);
                    desktop.repaint();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al aplicar el filtro: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
        }
    }//GEN-LAST:event_filterBoxActionPerformed

    /**
     * Rotates the image in the selected window.
     * @param degree Rotation degree (sexagesimal).
     */
    private void rotationAction(int degree){
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        
        if(iw != null && iw.getCanvas() != null){
            if(imgSource == null && iw.getImage() != null) imgSource = iw.getImage();
            if(imgSource != null){                
                try{
                    AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(degree),imgSource.getWidth()/2, imgSource.getHeight()/2);
                    AffineTransformOp aop = new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = aop.filter(imgSource, null);
                    iw.getCanvas().setImage(imgdest);
                    desktop.repaint();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al rotar: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
        }
    }
    
    /**
     * State changed event handler for rotation slider.
     * @param evt ChangeEvent.
     */
    private void rotationSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                             
        rotationAction(rotationSlider.getValue());
    }  
    
    /**
     * Action performed event handler for roation button.
     * @param evt Action event.
     */
    private void rotation90BtActionPerformed(java.awt.event.ActionEvent evt) {                                              
        rotationAction(90);
        imgSource = null;
    }     
    
    /**
     * Action performed event handler for roation button.
     * @param evt Action event.
     */
    private void rotation180BtActionPerformed(java.awt.event.ActionEvent evt) {                                               
        rotationAction(180);
        imgSource = null;
    }  
    
    /**
     * Action performed event handler for roation button.
     * @param evt Action event.
     */
    private void rotation270BtActionPerformed(java.awt.event.ActionEvent evt) {                                               
        rotationAction(270);
        imgSource = null;
    }  
    
    /**
     * Scales the image in the selected window.
     * @param factor_w width scaling factor.
     * @param factor_h height scaling factor.
     */
    private void scaleAction(float factor_w, float factor_h){
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        
        if(iw != null && iw.getCanvas() != null){
            BufferedImage imgSource = iw.getImage();
            if(imgSource != null){
                
                try{
                    AffineTransform at = AffineTransform.getScaleInstance(factor_w, factor_h);
                    AffineTransformOp aop = new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = aop.filter(imgSource, null);
                    iw.getCanvas().setImage(imgdest);
                    iw.repaint();
                    desktop.repaint();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al rotar: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
        }
    }
    
    /**
     * Action performed event handler for scale button.
     * @param evt Action event.
     */
    private void scalePlusBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scalePlusBtActionPerformed
        scaleAction(1.25f,1.25f);
    }//GEN-LAST:event_scalePlusBtActionPerformed

    /**
     * Action performed event handler for scale button.
     * @param evt Action event.
     */
    private void scaleMinusBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scaleMinusBtActionPerformed
        scaleAction(0.75f,0.75f);
    }//GEN-LAST:event_scaleMinusBtActionPerformed

    /**
     * Action performed event handler for sine button.
     * @param evt Action event.
     */
    private void sineBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sineBtActionPerformed
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        
        if(iw != null){
            BufferedImage imgSource = iw.getImage();
            if(imgSource != null){
                try{
                    LookupTable lt = JLookupTableProducer.sine(180.0);
                    LookupOp lop = new LookupOp(lt, null) ;
                    lop.filter(imgSource, imgSource);
                    desktop.repaint();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al aplicar el efecto: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
        }
    }//GEN-LAST:event_sineBtActionPerformed

    /**
     * Action performed event handler for image size menu item.
     * @param evt Action event.
     */
    private void changeImgSizeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeImgSizeMenuItemActionPerformed
        ImageSizeDialog isd = new ImageSizeDialog(this, true);
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        Dimension currentImgSize = null;
        if(iw != null && iw.getImage() != null){
            currentImgSize = new Dimension(iw.getImage().getWidth(), iw.getImage().getHeight());
        }
     
        isd.setCurrentValues(defaultNewImgSize, currentImgSize);
        int option = isd.showDialog();
        
        switch(option){
            case ImageSizeDialog.CHANGE_DEFAULT:
                this.defaultNewImgSize = isd.getDefaultSize();
                break;
            case ImageSizeDialog.RESIZE:
            case ImageSizeDialog.SCALE:
                Dimension newSize = isd.getCurrentSize();
                if(iw != null && iw.getCanvas() != null){
                    BufferedImage imgSource = iw.getImage();
                    if(imgSource != null){
                        BufferedImage imagedest = new BufferedImage(newSize.width, newSize.height, imgSource.getType());
                        Graphics2D g2d = imagedest.createGraphics();
                        JRectangle r = new JRectangle(newSize.width,newSize.height);
                        g2d.setPaint(Color.WHITE);
                        g2d.fill(r);
                        
                        if(option == ImageSizeDialog.RESIZE){
                            g2d.drawImage(imgSource,0,0,null);
                        }
                        else if(option == ImageSizeDialog.SCALE){
                            g2d.drawImage(imgSource,0,0,newSize.width,newSize.height,null);
                        }
                        iw.getCanvas().setImage(imagedest);
                        iw.getCanvas().repaint();
                    }
                }
                break;
        }
    }//GEN-LAST:event_changeImgSizeMenuItemActionPerformed

    /**
     * Changes the location of a window created from another one.
     * @param curr Current window.
     * @param neww New window.
     */
    private static void translateNewWindow(JInternalFrame curr, JInternalFrame neww){
        neww.setLocation(curr.getLocation().x+10, curr.getLocation().y+20);
    }
    
    /**
     * Action performed event handler for duplicate menu item.
     * @param evt Action event.
     */
    private void duplicateMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_duplicateMenuItemActionPerformed
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        if(iw != null){
            BufferedImage imgSource = iw.getImage();
            if(imgSource != null){
                BufferedImage imgdest = ImageUtils.copyImage(imgSource);
                ImageWindow dupl = new ImageWindow(this);
                translateNewWindow(iw,dupl);
                dupl.getCanvas().setImage(imgdest);
                dupl.setImageTitle(iw.getImageTitle()+" - copia");
                desktop.add(dupl);
                dupl.setVisible(true);
            }
        }
    }//GEN-LAST:event_duplicateMenuItemActionPerformed

    /**
     * Action performed event handler for sepia buton.
     * @param evt ActionEvent.
     */
    private void sepiaBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sepiaBtActionPerformed
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        
        if(iw != null){
            BufferedImage imgSource = iw.getImage();
            if(imgSource != null){
                try{
                    SepiaOp sop = new SepiaOp();
                    sop.filter(imgSource, imgSource);
                    desktop.repaint();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al aplicar el filtro: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
        }
    }//GEN-LAST:event_sepiaBtActionPerformed

    /**
     * Action performed event handler for bands button.
     * @param evt ActionEvent.
     */
    private void bandsBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bandsBtActionPerformed
        MediaWindowAdapter iw = (MediaWindowAdapter) desktop.getSelectedFrame();
        if(iw != null){
            BufferedImage imgSource = iw.getImage();
            if(imgSource != null){
                try{
                    MediaWindowAdapter previous = iw;
                    for(int i = 0; i < imgSource.getRaster().getNumBands(); i++){
                        BufferedImage imgDest = ImageUtils.getBand(imgSource, i);
                        ImageWindow iwband = new ImageWindow(this);
                        translateNewWindow(previous,iwband);
                        iwband.getCanvas().setImage(imgDest);
                        desktop.add(iwband);
                        iwband.setImageTitle(iw.getImageTitle())  ;
                        iwband.setImageInfo((iw.getImageInfo()==null)?"["+i+"]":iw.getImageInfo()+" ["+i+"]");
                        iwband.setVisible(true);
                        previous=iwband;
                    }
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "No se pudieron obtener las bandas de la imagen seleccionada.", "Operación no admitida.", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_bandsBtActionPerformed

    /**
     * Action performed event handler for color space combo box.
     * @param evt ActionEvent
     */
    private void colorSpaceBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorSpaceBoxActionPerformed
        MediaWindowAdapter iw = (MediaWindowAdapter) desktop.getSelectedFrame();
        if(iw != null){
            BufferedImage imgSource = iw.getImage();
            if(imgSource != null){
                BufferedImage imgDest = null;
                String cstext = (String)colorSpaceBox.getSelectedItem();
                switch(cstext){
                    case "sRGB":
                        if(!imgSource.getColorModel().getColorSpace().isCS_sRGB())
                        {    
                            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
                            ColorConvertOp cop = new ColorConvertOp(cs,null);
                            imgDest = cop.filter(imgSource, null);
                        }
                        break;
                    case "YCC":
                        if(imgSource.getColorModel().getColorSpace().isCS_sRGB())
                        {
                            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_PYCC);
                            ColorConvertOp cop = new ColorConvertOp(cs,null);
                            imgDest = cop.filter(imgSource, null);
                        }
                        break;
                    case "GRAY":{
                        ColorSpace cs = new GreyColorSpace();
                        ColorConvertOp cop = new ColorConvertOp(cs,null);
                        imgDest = cop.filter(imgSource, null);
                    }
                        break;
                }
                if(imgDest != null){
                    ImageWindow iwcs = new ImageWindow(this);
                    translateNewWindow(iw, iwcs);
                    iwcs.getCanvas().setImage(imgDest);
                    desktop.add(iwcs);
                    iwcs.setImageTitle(iw.getImageTitle());
                    iwcs.setImageInfo((iw.getImageInfo()==null)?"["+cstext+"]":iw.getImageInfo()+" ["+cstext+"]");
                    iwcs.setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog(this, "La conversión no es válida o el espacio de colores no ha cambiado.", "Conversión inválida", JOptionPane.WARNING_MESSAGE);
                }
                
            }
        }
    }//GEN-LAST:event_colorSpaceBoxActionPerformed

    /**
     * Action performed event handler for brightness button
     * @param evt ActionEvent
     */
    private void brightnessBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brightnessBtActionPerformed
        if(brightnessBt.isSelected()){
            MediaWindowAdapter iw = (MediaWindowAdapter) desktop.getSelectedFrame();
            if(iw != null && iw.getImage() != null){
                imgSource = ImageUtils.copyImage(iw.getImage());
            }
            brightnessWindow.setVisible(true);
        }
        else{
            imgSource = null;
            brightnessSlider.setValue(0);
            brightnessWindow.setVisible(false);
        }
    }//GEN-LAST:event_brightnessBtActionPerformed

    /**
     * Action performed event handler for contrast combobox.
     * @param evt ActionEvent
     */
    private void contrastComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contrastComboBoxActionPerformed
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        
        if(iw != null){
            BufferedImage imgSource = iw.getImage();
            if(imgSource != null){
                try{
                    LookupTable lt = null;
                    
                
                    switch(contrastComboBox.getSelectedIndex()){
                        case 0:
                            lt = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_SFUNCION);
                            break;
                        case 1:
                            lt = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_LOGARITHM);
                            break;
                        case 2:
                            lt = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_POWER);
                            break;
                        case 3:
                            lt = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_NEGATIVE);
                            break;

                    }
                    LookupOp lop = new LookupOp(lt, null) ;
                    lop.filter(imgSource, imgSource);
                    desktop.repaint();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al aplicar el filtro: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
        }
        
    }//GEN-LAST:event_contrastComboBoxActionPerformed

    /**
     * Action performed event handler for rotate button.
     * @param evt ActionEvent
     */
    private void rotateBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateBtActionPerformed
        if(rotateBt.isSelected()){
            MediaWindowAdapter iw = (MediaWindowAdapter) desktop.getSelectedFrame();
            if(iw != null && iw.getImage() != null){
                imgSource = ImageUtils.copyImage(iw.getImage());
            }
            rotationWindow.setVisible(true);
        }
        else{
            imgSource = null;
            rotationSlider.setValue(0);
            rotationWindow.setVisible(false);
        }
    }//GEN-LAST:event_rotateBtActionPerformed

    /**
     * Action performed event handler for rotate exact button.
     * @param evt ActionEvent
     */
    private void rotateExactBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateExactBtActionPerformed
        exactRotationWindow.setVisible(rotateExactBt.isSelected());
    }//GEN-LAST:event_rotateExactBtActionPerformed

    /**
     * Action performed event handler for tint button
     * @param evt ActionEvent
     */
    private void tintBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tintBtActionPerformed
        if(tintBt.isSelected()){
            MediaWindowAdapter iw = (MediaWindowAdapter) desktop.getSelectedFrame();
            if(iw != null && iw.getImage() != null){
                imgSource = ImageUtils.copyImage(iw.getImage());
            }
            tintWindow.setVisible(true);
        }
        else{
            imgSource = null;
            tintSlider.setValue(0);
            tintWindow.setVisible(false);
        }
    }//GEN-LAST:event_tintBtActionPerformed

    /**
     * Action performed event handler for equalization button
     * @param evt ActionEvent
     */
    private void equalizationBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equalizationBtActionPerformed
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        
        if(iw != null){
            BufferedImage imgSource = iw.getImage();
            if(imgSource != null){
                try{
                    EqualizationOp eop = new EqualizationOp();
                    eop.filter(imgSource, imgSource);
                    desktop.repaint();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al aplicar el filtro: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
        }
    }//GEN-LAST:event_equalizationBtActionPerformed

    /**
     * Action performed event handler for umbralization button
     * @param evt ActionEvent
     */
    private void umbralBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_umbralBtActionPerformed
        if(umbralBt.isSelected()){
            MediaWindowAdapter iw = (MediaWindowAdapter) desktop.getSelectedFrame();
            if(iw != null && iw.getImage() != null){
                imgSource = ImageUtils.copyImage(iw.getImage());
            }
            thresholdWindow.setVisible(true);
        }
        else{
            imgSource = null;
            thresholdSlider.setValue(128);
            thresholdWindow.setVisible(false);
        }
    }//GEN-LAST:event_umbralBtActionPerformed

    /**
     * Action performed event handler for histogram button
     * @param evt ActionEvent
     */
    private void histogramBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_histogramBtActionPerformed
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        
        if(iw != null){
            BufferedImage imgSource = iw.getImage();
            if(imgSource != null){
                try{
                    Histogram hist = new Histogram(imgSource);
                    HistogramPanel hp = new HistogramPanel(hist);
                    JDialog dialog = new JDialog(this, "Histograma", false);
                    dialog.setLocationRelativeTo(null);
                    dialog.getContentPane().add(hp);
                    dialog.pack();
                    dialog.setVisible(true);
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al crear el histograma: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
        }
    }//GEN-LAST:event_histogramBtActionPerformed

    /**
     * Action performed event handler for sobel button
     * @param evt ActionEvent
     */
    private void sobelBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sobelBtActionPerformed
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        
        if(iw != null){
            BufferedImage imgSource = iw.getImage();
            if(imgSource != null){
                try{
                    SobelOp sop = new SobelOp();
                    sop.filter(imgSource, imgSource);
                    desktop.repaint();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al aplicar el filtro: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
        }
    }//GEN-LAST:event_sobelBtActionPerformed

    /**
     * Action performed event handler for sum button
     * @param evt ActionEvent
     */
    private void sumBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sumBtActionPerformed
        if(sumBt.isSelected()){
            MediaWindowAdapter iw = (MediaWindowAdapter) desktop.getSelectedFrame();
            if(iw != null){
                MediaWindowAdapter iwNext = (MediaWindowAdapter) desktop.selectFrame(true);
                if(iwNext != null){
                    binarySrc1 = iwNext.getImage();
                    binarySrc2 = iw.getImage();
                    
                    if(binarySrc1 != null && binarySrc2 != null){
                        try{
                            BlendOp bop = new BlendOp(binarySrc1);
                            binaryDest = bop.filter(binarySrc2, null);
                            ImageWindow iwsum = new ImageWindow(this);
                            iwsum.getCanvas().setImage(binaryDest);
                            this.desktop.add(iwsum);
                            iwsum.setImageTitle(iw.getImageTitle()+"+"+iwNext.getImageTitle());
                            iwsum.setVisible(true);
                        }
                        catch(Exception ex){
                            JOptionPane.showMessageDialog(this, "Error al sumar: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);   
                        }
                    }
                }
            }
            sumWindow.setVisible(true);
        }
        else{
            sumWindow.setVisible(false);
            sumSlider.setValue(50);
            binarySrc1 = null;
            binarySrc2 = null;
            binaryDest = null;
        }
    }//GEN-LAST:event_sumBtActionPerformed

    /**
     * Action performed event handler for diff button
     * @param evt ActionEvent
     */
    private void diffBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diffBtActionPerformed
        MediaWindowAdapter iw = (MediaWindowAdapter) desktop.getSelectedFrame();
        if(iw != null){
            MediaWindowAdapter iwNext = (MediaWindowAdapter) desktop.selectFrame(true);
            if(iwNext != null){
                BufferedImage imgRight = iwNext.getImage();
                BufferedImage imgLeft = iw.getImage();

                if(imgRight != null && imgLeft != null){
                    try{
                        SubtractionOp sop = new SubtractionOp(imgLeft);
                        BufferedImage imageDest = sop.filter(imgRight, null);
                        ImageWindow iwdiff = new ImageWindow(this);
                        iwdiff.getCanvas().setImage(imageDest);
                        this.desktop.add(iwdiff);
                        iwdiff.setImageTitle(iw.getImageTitle()+"-"+iwNext.getImageTitle());
                        iwdiff.setVisible(true);
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error al restar: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);   
                    }
                }
            }
        }
    }//GEN-LAST:event_diffBtActionPerformed

    /**
     * Action performed event handler for product button
     * @param evt ActionEvent
     */
    private void productBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productBtActionPerformed
        MediaWindowAdapter iw = (MediaWindowAdapter) desktop.getSelectedFrame();
        if(iw != null){
            MediaWindowAdapter iwNext = (MediaWindowAdapter) desktop.selectFrame(true);
            if(iwNext != null){
                BufferedImage imgRight = iwNext.getImage();
                BufferedImage imgLeft = iw.getImage();

                if(imgRight != null && imgLeft != null){
                    try{
                        ProductOp pop = new ProductOp(imgLeft);
                        BufferedImage imageDest = pop.filter(imgRight, null);
                        ImageWindow iwprod = new ImageWindow(this);
                        iwprod.getCanvas().setImage(imageDest);
                        this.desktop.add(iwprod);
                        iwprod.setImageTitle(iw.getImageTitle()+"x"+iwNext.getImageTitle());
                        iwprod.setVisible(true);
                    }
                    catch(Exception ex){
                        JOptionPane.showMessageDialog(this, "Error al multiplicar: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);   
                    }
                }
            }
        }
    }//GEN-LAST:event_productBtActionPerformed

    /**
     * Action performed event handler for more effects button
     * @param evt ActionEvent
     */
    private void moreEffectsBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moreEffectsBtActionPerformed
        moreEffectsWindow.setVisible(moreEffectsBt.isSelected());
    }//GEN-LAST:event_moreEffectsBtActionPerformed

    /**
     * Action performed event handler for play-pause  button
     * @param evt ActionEvent
     */
    private void playPauseBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playPauseBtActionPerformed
        if(currentPlayerWindow != null && currentPlayerWindow.isPlaying()){
            currentPlayerWindow.pause();
        }
        else{
            File f  = (File) audioBox.getSelectedItem();
            if(f != null){
                if(videoWindows.containsKey(f)){
                    currentPlayerWindow = videoWindows.get(f);
                    currentPlayerWindow.moveToFront();
                    currentPlayerWindow.play();
                }
                else{
                    currentPlayerWindow = VLCWindow.getInstance(f, this);
                    if(currentPlayerWindow != null){
                        currentPlayerWindow.setTitle(f.getName());
                        currentPlayerWindow.addInternalFrameListener(new InternalFrameAdapter(){
                            @Override
                            public void internalFrameClosing(InternalFrameEvent e) {
                                videoWindows.remove(currentPlayerWindow.getFile());
                            }
                        });
                        desktop.add(currentPlayerWindow);
                        if(videoFilter.accept(f)){
                            
                            currentPlayerWindow.setVisible(true);
                        }
                        videoWindows.put(f, currentPlayerWindow);
                        currentPlayerWindow.play();
                    }
                }
            }
            
            /*
            else{
                if(player == null){
                    if(f != null){
                        player = new SMSoundPlayer(f);
                        player.addLineListener(new PlayerManager());
                        if(player != null){
                            player.play();
                        }
                    }
                }
                else{
                    if(playPauseBt.getToolTipText().equals("Pausa")){
                        player.stop();
                    }
                    else{
                        player.play();
                    }
                }
            }*/
        }
    }//GEN-LAST:event_playPauseBtActionPerformed

    /**
     * Action performed event handler for stop button
     * @param evt ActionEvent
     */
    private void stopBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopBtActionPerformed
        if(currentPlayerWindow != null){
            currentPlayerWindow.stop();
        }
        /*
        else{
            if(player != null){
                player.stop();
                player = null;
            }
        }*/
    }//GEN-LAST:event_stopBtActionPerformed

    /**
     * Action performed event handler for record button
     * @param evt ActionEvent
     */
    private void recordBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordBtActionPerformed
        if(recorder == null && audioTmpFile == null){
            try{
                audioTmpFile = File.createTempFile("audio", ".wav");
                recorder = new SMSoundRecorder(audioTmpFile);
                recorder.addLineListener(new RecordManager());
                recorder.record();
                recordTimer.start();

            }
            catch(Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al grabar: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);    
            }
        }
        else{
            try{
                recorder.stop();
                recordTimer.stop();
                JFileChooser dlg = new JFileChooser();                    
                int resp = dlg.showSaveDialog(this);
                if( resp == JFileChooser.APPROVE_OPTION) {
                    File f = dlg.getSelectedFile();
                    f = new File(f.getAbsolutePath()){
                        @Override
                        public String toString() {
                            return this.getName();
                        }  
                    };
                    Files.copy(audioTmpFile.toPath(), f.toPath(), REPLACE_EXISTING);
                    audioBox.addItem(f);   
                }
                audioTmpFile = null;
            }
            catch(Exception ex){
                audioTmpFile = null;
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al grabar: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
            recordTimeLabel.setText("00:00");
        }
    }//GEN-LAST:event_recordBtActionPerformed

    /**
     * Action performed event handler for webcam button
     * @param evt ActionEvent
     */
    private void webcamBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webcamBtActionPerformed
        CameraWindow cw = CameraWindow.getInstance();
        if(cw != null){
            if(cw.getParent() == null){
                desktop.add(cw);
                cw.setVisible(true);
            }
            else{
                cw.moveToFront();
                try{
                    cw.setSelected(true);
                }catch(Exception ex){}
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "No se pudo abrir la WebCam. Asegúrese de que el dispositivo dispone de una WebCam.", "Error al abrir la cámara", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_webcamBtActionPerformed

    /**
     * Action performed event handler for snapshot button
     * @param evt ActionEvent
     */
    private void snapshotBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_snapshotBtActionPerformed
        MediaWindowAdapter cw = (MediaWindowAdapter) desktop.getSelectedFrame();
        if(cw != null){
            try{
                BufferedImage snapshot = cw.getSnapShot();
                if(snapshot != null){
                    ImageWindow iw = new ImageWindow(this);
                    translateNewWindow(cw, iw);
                    iw.getCanvas().setImage(snapshot);
                    if(cw.getCamera() != null) iw.setTitle("Captura de webcam");
                    else iw.setTitle("Captura de vídeo.");
                    desktop.add(iw);
                    iw.setVisible(true);
                }
            }catch(Exception ex){}
        }
    }//GEN-LAST:event_snapshotBtActionPerformed

    /**
     * Action performed event handler for atan button
     * @param evt ActionEvent
     */
    private void atanBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atanBtActionPerformed
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        
        if(iw != null){
            BufferedImage imgSource = iw.getImage();
            if(imgSource != null){
                try{
                    LookupTable lt = JLookupTableProducer.atan();
                    LookupOp lop = new LookupOp(lt, null) ;
                    lop.filter(imgSource, imgSource);
                    desktop.repaint();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al aplicar el efecto: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
        }
    }//GEN-LAST:event_atanBtActionPerformed

    /**
     * Action performed event handler for gauss button
     * @param evt ActionEvent
     */
    private void gaussBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gaussBtActionPerformed
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        
        if(iw != null){
            BufferedImage imgSource = iw.getImage();
            if(imgSource != null){
                try{
                    LookupTable lt = JLookupTableProducer.gaussian(10);
                    LookupOp lop = new LookupOp(lt, null) ;
                    lop.filter(imgSource, imgSource);
                    desktop.repaint();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al aplicar el efecto: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
        }
    }//GEN-LAST:event_gaussBtActionPerformed

    /**
     * Action performed event handler for remove background button
     * @param evt ActionEvent
     */
    private void removeBackgroundBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBackgroundBtActionPerformed
        if(removeBackgroundBt.isSelected()){
            MediaWindowAdapter iw = (MediaWindowAdapter) desktop.getSelectedFrame();
            if(iw != null && iw.getImage() != null){
                imgSource = ImageUtils.copyImage(iw.getImage());
            }
            backgroundRemoveWindow.setVisible(true);
        }
        else{
            imgSource = null;
            backgroundRemoveSlider.setValue(0);
            backgroundRemoveWindow.setVisible(false);
        }
    }//GEN-LAST:event_removeBackgroundBtActionPerformed

    /**
     * Action performed event handler for secondary color button
     * @param evt ActionEvent
     */
    private void secondaryColorListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secondaryColorListActionPerformed
        if(!preventer){
            MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
            if(currentWindow != null && currentWindow.getCanvas() != null) currentWindow.getCanvas().updateSecondaryColor((Color)secondaryColorList.getSelectedItem());
        }
    }//GEN-LAST:event_secondaryColorListActionPerformed

    /**
     * Action performed event handler for add color button
     * @param evt ActionEvent
     */
    private void addColorBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addColorBtActionPerformed
        Color c = JColorChooser.showDialog(this, "Añadir colores", (Color)primaryColorList.getSelectedItem());
        if(c != null){
            primaryColorList.addItem(c);
            secondaryColorList.addItem(c);
        }
        
    }//GEN-LAST:event_addColorBtActionPerformed

    /**
     * Action performed event handler for shapes combo box
     * @param evt ActionEvent
     */
    private void shapesComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shapesComboBoxActionPerformed
        if(!preventer){
            switch(shapesComboBox.getSelectedIndex()){
                case 0:
                    pointBtActionPerformed(evt);
                break;
                
                case 1:
                    lineBtActionPerformed(evt);
                break;
                
                case 2:
                    rectangleBtActionPerformed(evt);
                break;
                
                case 3:
                    ovalBtActionPerformed(evt);
                break;
                
                case 4:
                    roundRectangleBtActionPerformed(evt);
                break;
                
                case 5:
                    quadCurveBtActionPerformed(evt);
                break;
                
                case 6:
                    cubicCurveBtActionPerformed(evt);
                break;
                
                case 7:
                    polygonBtActionPerformed(evt);
                break;
                
                case 8:
                    arcBtActionPerformed(evt);
                break;
                case 9:
                    sunBtActionPerformed(evt);
                    break;
                case 10:
                    editBtActionPerformed(evt);
                    break;
            }
        }
    }//GEN-LAST:event_shapesComboBoxActionPerformed

    /**
     * Action performed event handler for stroke type list.
     * @param evt ActionEvent
     */
    private void strokeTypeListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_strokeTypeListActionPerformed
        if(!preventer){
            MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
            if(iw != null && iw.getCanvas() != null){              
                iw.getCanvas().updateStrokeType((BasicStrokeType) strokeTypeList.getSelectedItem());
            }
        }
    }//GEN-LAST:event_strokeTypeListActionPerformed

    /**
     * Action performed event handler for round rectangle button.
     * @param evt ActionEvent
     */
    private void roundRectangleBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundRectangleBtActionPerformed
        MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
        if(currentWindow != null && currentWindow.getCanvas() != null){
            currentWindow.getCanvas().setCurrentTool(PaintTool.ROUND_RECTANGLE);
            currentWindow.getCanvas().setEditing(false);
            setImageView(currentWindow.getCanvas());
        }
        stateBar.setTool(PaintTool.ROUND_RECTANGLE);
        shapesBt.setIcon(roundRectangleBt.getIcon());
    }//GEN-LAST:event_roundRectangleBtActionPerformed

    /**
     * Action performed event handler for quadratic curve button.
     * @param evt ActionEvent
     */
    private void quadCurveBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quadCurveBtActionPerformed
        MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
        if(currentWindow != null && currentWindow.getCanvas() != null){
            currentWindow.getCanvas().setCurrentTool(PaintTool.QUAD_CURVE);
            currentWindow.getCanvas().setEditing(false);
            setImageView(currentWindow.getCanvas());
        }
        stateBar.setTool(PaintTool.QUAD_CURVE);
        shapesBt.setIcon(quadCurveBt.getIcon());
    }//GEN-LAST:event_quadCurveBtActionPerformed

    /**
     * Action performed event handler for cubic curve button.
     * @param evt ActionEvent
     */
    private void cubicCurveBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cubicCurveBtActionPerformed
        MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
        if(currentWindow != null && currentWindow.getCanvas() != null){
            currentWindow.getCanvas().setCurrentTool(PaintTool.CUBIC_CURVE);
            currentWindow.getCanvas().setEditing(false);
            setImageView(currentWindow.getCanvas());
        }
        stateBar.setTool(PaintTool.CUBIC_CURVE);
        shapesBt.setIcon(cubicCurveBt.getIcon());
    }//GEN-LAST:event_cubicCurveBtActionPerformed

    /**
     * Action performed event handler for polygon button
     * @param evt ActionEvent
     */
    private void polygonBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_polygonBtActionPerformed
        MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
        if(currentWindow != null && currentWindow.getCanvas() != null){
            currentWindow.getCanvas().setCurrentTool(PaintTool.POLYGON);
            currentWindow.getCanvas().setEditing(false);
            setImageView(currentWindow.getCanvas());
        }
        stateBar.setTool(PaintTool.POLYGON);
        shapesBt.setIcon(polygonBt.getIcon());
    }//GEN-LAST:event_polygonBtActionPerformed

    /**
     * Action performed event handler for arc button.
     * @param evt ActionEvent
     */
    private void arcBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arcBtActionPerformed
        MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
        if(currentWindow != null && currentWindow.getCanvas() != null){
            currentWindow.getCanvas().setCurrentTool(PaintTool.ARC);
            currentWindow.getCanvas().setEditing(false);
            setImageView(currentWindow.getCanvas());
        }
        stateBar.setTool(PaintTool.ARC);
        shapesBt.setIcon(arcBt.getIcon());
    }//GEN-LAST:event_arcBtActionPerformed

    /**
     * Action performed event handler for stroke button
     * @param evt ActionEvent
     */
    private void strokeBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_strokeBtActionPerformed
        strokeWindow.setVisible(strokeBt.isSelected());
    }//GEN-LAST:event_strokeBtActionPerformed

    /**
     * Action performed event handler for more operations button.
     * @param evt ActionEvent
     */
    private void moreOperationsBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moreOperationsBtActionPerformed
        moreOperationsWindow.setVisible(moreOperationsBt.isSelected());
    }//GEN-LAST:event_moreOperationsBtActionPerformed

    /**
     * Action performed event handler to bottom button
     * @param evt ActionEvent
     */
    private void toBottomBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toBottomBtActionPerformed
        MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
        if(currentWindow != null && currentWindow.getCanvas() != null){
            currentWindow.getCanvas().sendToBottomSelected();
        }
    }//GEN-LAST:event_toBottomBtActionPerformed

    /**
     * Action performed event handler for to top button
     * @param evt ActionEvent
     */
    private void toTopBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toTopBtActionPerformed
        MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
        if(currentWindow != null && currentWindow.getCanvas() != null){
            currentWindow.getCanvas().bringToTopSelected();
        }
    }//GEN-LAST:event_toTopBtActionPerformed

    /**
     * Action performed event handler for back button
     * @param evt ActionEvent
     */
    private void backBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtActionPerformed
        MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
        if(currentWindow != null && currentWindow.getCanvas() != null){
            currentWindow.getCanvas().sendBackSelected();
        }
    }//GEN-LAST:event_backBtActionPerformed

    /**
     * Action performed event handler for forward button
     * @param evt ActionEvent
     */
    private void forwardBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardBtActionPerformed
        MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
        if(currentWindow != null && currentWindow.getCanvas() != null){
            currentWindow.getCanvas().bringForwardSelected();
        }
    }//GEN-LAST:event_forwardBtActionPerformed

    /**
     * Action performed event handler for fill combo box.
     * @param evt ActionEvent
     */
    private void fillComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fillComboBoxActionPerformed
        if(!preventer){
            MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
            if(currentWindow != null && currentWindow.getCanvas() != null){
                currentWindow.getCanvas().updateFillMode(fillComboBox.getSelectedIndex());
            }
        }
    }//GEN-LAST:event_fillComboBoxActionPerformed

    /**
     * Action performed event handler for modular translation button
     * @param evt ActionEvent
     */
    private void modularTranslationBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modularTranslationBtActionPerformed
        if(modularTranslationBt.isSelected()){
            MediaWindowAdapter iw = (MediaWindowAdapter) desktop.getSelectedFrame();
            if(iw != null && iw.getImage() != null){
                imgSource = ImageUtils.copyImage(iw.getImage());
            }
            modularTranslationWindow.setVisible(true);
        }
        else{
            imgSource = null;
            modularTranslationSlider.setValue(0);
            modularTranslationWindow.setVisible(false);
        }
    }//GEN-LAST:event_modularTranslationBtActionPerformed

    /**
     * Action performed event handler for fire button
     * @param evt ActionEvent
     */
    private void fireEffectBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fireEffectBtActionPerformed
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        
        if(iw != null){
            BufferedImage imgSource = iw.getImage();
            if(imgSource != null){
                try{
                    FireEffectOp fop = new FireEffectOp();
                    fop.filter(imgSource, imgSource);
                    desktop.repaint();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al aplicar el filtro: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
        }
    }//GEN-LAST:event_fireEffectBtActionPerformed

    /**
     * Action performed event handler for cold button
     * @param evt ActionEvent
     */
    private void coldEffectBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coldEffectBtActionPerformed
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        
        if(iw != null){
            BufferedImage imgSource = iw.getImage();
            if(imgSource != null){
                try{
                    ColdEffectOp cop = new ColdEffectOp();
                    cop.filter(imgSource, imgSource);
                    desktop.repaint();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al aplicar el filtro: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
        }
    }//GEN-LAST:event_coldEffectBtActionPerformed

    /**
     * Action performed event handler for sun button
     * @param evt ActionEvent
     */
    private void sunBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sunBtActionPerformed
        MediaWindowAdapter currentWindow = (MediaWindowAdapter)desktop.getSelectedFrame();
        if(currentWindow != null && currentWindow.getCanvas() != null){
            currentWindow.getCanvas().setCurrentTool(PaintTool.AREA_SUN);
            currentWindow.getCanvas().setEditing(false);
            setImageView(currentWindow.getCanvas());
        }
        stateBar.setTool(PaintTool.AREA_SUN);
        shapesBt.setIcon(sunBt.getIcon());
    }//GEN-LAST:event_sunBtActionPerformed

    /**
     * Action performed event handler for transform button
     * @param evt ActionEvent
     */
    private void transformBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transformBtActionPerformed
        transformWindow.setVisible(transformBt.isSelected());
    }//GEN-LAST:event_transformBtActionPerformed

    /**
     * Sets the visibility of a components tool group.
     * @param components Components tool group.
     * @param b True to make the components visible, false otherwise.
     */
    private void setAllVisible(ArrayList<Component> components, boolean b){
        for(Component c: components){
            c.setVisible(b);
        }
    }
    
    /**
     * Sets the visibility of a group of tool groups.
     * @param components Group.
     * @param b True to make the components visible, false otherwise.
     */
    private void setAllGroupVisible(ArrayList<ArrayList<Component>> components, boolean b){
        for(ArrayList<Component> cs: components){
            for(Component c: cs){
                c.setVisible(b);
            }
        }
    }
    
    /**
     * Action performed event handler for shape bar menu item.
     * @param evt ActionEvent.
     */
    private void shapeBarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shapeBarMenuItemActionPerformed
        setAllVisible(shapesToolGroup, shapeBarMenuItem.isSelected());
    }//GEN-LAST:event_shapeBarMenuItemActionPerformed

    /**
     * Action performed event handler for color bar menu item.
     * @param evt ActionEvent.
     */
    private void colorBarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorBarMenuItemActionPerformed
        setAllVisible(colorsToolGroup, colorBarMenuItem.isSelected());
    }//GEN-LAST:event_colorBarMenuItemActionPerformed

    /**
     * Action performed event handler for attributes bar menu item.
     * @param evt ActionEvent.
     */
    private void attrBarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attrBarMenuItemActionPerformed
        setAllVisible(attributesToolGroup, attrBarMenuItem.isSelected());
    }//GEN-LAST:event_attrBarMenuItemActionPerformed

    /**
     * Action performed event handler for show all graphics menu item.
     * @param evt ActionEvent.
     */
    private void showAllGraphicsBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAllGraphicsBarActionPerformed
        setAllGroupVisible(graphicsToolGroup, true);
        shapeBarMenuItem.setSelected(true);
        colorBarMenuItem.setSelected(true);
        attrBarMenuItem.setSelected(true);
    }//GEN-LAST:event_showAllGraphicsBarActionPerformed

    /**
     * Action performed event handler for hide all graphics menu item.
     * @param evt ActionEvent.
     */
    private void hideAllGraphicsBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hideAllGraphicsBarActionPerformed
        setAllGroupVisible(graphicsToolGroup, false);
        shapeBarMenuItem.setSelected(false);
        colorBarMenuItem.setSelected(false);
        attrBarMenuItem.setSelected(false);
    }//GEN-LAST:event_hideAllGraphicsBarActionPerformed

    /**
     * Action performed event handler for bright bar menu item.
     * @param evt ActionEvent.
     */
    private void brightBarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brightBarMenuItemActionPerformed
        setAllVisible(brightToolGroup, brightBarMenuItem.isSelected());
    }//GEN-LAST:event_brightBarMenuItemActionPerformed

    /**
     * Action performed event handler for convolve bar menu item.
     * @param evt ActionEvent.
     */
    private void convolveBarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_convolveBarMenuItemActionPerformed
        setAllVisible(convolveToolGroup, convolveBarMenuItem.isSelected());
    }//GEN-LAST:event_convolveBarMenuItemActionPerformed

    /**
     * Action performed event handler for effects bar menu item.
     * @param evt ActionEvent.
     */
    private void effectsBarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_effectsBarMenuItemActionPerformed
         setAllVisible(effectsToolGroup, effectsBarMenuItem.isSelected());
    }//GEN-LAST:event_effectsBarMenuItemActionPerformed

    /**
     * Action performed event handler for color space bar menu item.
     * @param evt ActionEvent.
     */
    private void colorSpaceBarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorSpaceBarMenuItemActionPerformed
        setAllVisible(colorSpacesToolGroup, colorSpaceBarMenuItem.isSelected());
    }//GEN-LAST:event_colorSpaceBarMenuItemActionPerformed

    /**
     * Action performed event handler for transform bar menu item.
     * @param evt ActionEvent.
     */
    private void transformBarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transformBarMenuItemActionPerformed
        setAllVisible(transformsToolGroup, transformBarMenuItem.isSelected());
    }//GEN-LAST:event_transformBarMenuItemActionPerformed

    /**
     * Action performed event handler for binary bar menu item.
     * @param evt ActionEvent.
     */
    private void binaryBarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_binaryBarMenuItemActionPerformed
        setAllVisible(binaryToolGroup, binaryBarMenuItem.isSelected());
    }//GEN-LAST:event_binaryBarMenuItemActionPerformed

    /**
     * Action performed event handler for control bar menu item.
     * @param evt ActionEvent.
     */
    private void controlBarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_controlBarMenuItemActionPerformed
        setAllVisible(controlToolGroup, controlBarMenuItem.isSelected());
    }//GEN-LAST:event_controlBarMenuItemActionPerformed

    /**
     * Action performed event handler for play bar menu item.
     * @param evt ActionEvent.
     */
    private void playBarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playBarMenuItemActionPerformed
        setAllVisible(playToolGroup, playBarMenuItem.isSelected());
    }//GEN-LAST:event_playBarMenuItemActionPerformed

    /**
     * Action performed event handler for record bar menu item.
     * @param evt ActionEvent.
     */
    private void recordBarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordBarMenuItemActionPerformed
        setAllVisible(recordToolGroup, recordBarMenuItem.isSelected());
    }//GEN-LAST:event_recordBarMenuItemActionPerformed

    /**
     * Action performed event handler for webcam bar menu item.
     * @param evt ActionEvent.
     */
    private void webcamBarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webcamBarMenuItemActionPerformed
        setAllVisible(webcamToolGroup, webcamBarMenuItem.isSelected());
    }//GEN-LAST:event_webcamBarMenuItemActionPerformed

    /**
     * Action performed event handler for show all image bar menu item.
     * @param evt ActionEvent.
     */
    private void showAllImageBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAllImageBarActionPerformed
        setAllGroupVisible(imagesToolGroup, true);
        brightBarMenuItem.setSelected(true);
        convolveBarMenuItem.setSelected(true);
        effectsBarMenuItem.setSelected(true);
        colorSpaceBarMenuItem.setSelected(true);
        transformBarMenuItem.setSelected(true);
        binaryBarMenuItem.setSelected(true);
    }//GEN-LAST:event_showAllImageBarActionPerformed

    /**
     * Action performed event handler for hide all image bar menu item.
     * @param evt ActionEvent.
     */
    private void hideAllImageBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hideAllImageBarActionPerformed
        setAllGroupVisible(imagesToolGroup, false);
        brightBarMenuItem.setSelected(false);
        convolveBarMenuItem.setSelected(false);
        effectsBarMenuItem.setSelected(false);
        colorSpaceBarMenuItem.setSelected(false);
        transformBarMenuItem.setSelected(false);
        binaryBarMenuItem.setSelected(false);
    }//GEN-LAST:event_hideAllImageBarActionPerformed

    /**
     * Action performed event handler for show all av bar menu item.
     * @param evt ActionEvent.
     */
    private void showAllAvBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAllAvBarActionPerformed
        setAllGroupVisible(avToolGroup, true);
        controlBarMenuItem.setSelected(true);
        playBarMenuItem.setSelected(true);
        recordBarMenuItem.setSelected(true);
        webcamBarMenuItem.setSelected(true);
    }//GEN-LAST:event_showAllAvBarActionPerformed

    /**
     * Action performed event handler for show all av bar menu item.
     * @param evt ActionEvent.
     */
    private void hideAllAvBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hideAllAvBarActionPerformed
        setAllGroupVisible(avToolGroup, false);
        controlBarMenuItem.setSelected(false);
        playBarMenuItem.setSelected(false);
        recordBarMenuItem.setSelected(false);
        webcamBarMenuItem.setSelected(false);
    }//GEN-LAST:event_hideAllAvBarActionPerformed

    /**
     * Action performed event handler for files bar menu item.
     * @param evt ActionEvent.
     */
    private void filesBarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filesBarMenuItemActionPerformed
        setAllVisible(filesToolGroup, filesBarMenuItem.isSelected());
    }//GEN-LAST:event_filesBarMenuItemActionPerformed

    /**
     * Shows about dialog.
     * @param evt ActionEvent.
     */
    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        new ConfigDialog(this, true).setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    /**
     * Shows the shapes button list.
     * @param evt ActionEvent.
     */
    private void shapesBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shapesBtActionPerformed
        shapesWindow.setVisible(shapesBt.isSelected());
    }//GEN-LAST:event_shapesBtActionPerformed

    /**
     * State chenge event handler for tint slider.
     * @param e ChangeEvent.
     */
    private void tintSliderStateChanged(ChangeEvent e) {
        Color c = (Color) primaryColorList.getSelectedItem();
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        float value = tintSlider.getValue()/100.0f;
         if(iw != null && iw.getImage() != null){
            if(imgSource != null){
                try{
                    TintOp top = new TintOp(c,value);
                    top.filter(imgSource, iw.getImage());
                    desktop.repaint();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al aplicar el filtro: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
            
        }
    }
    
    /**
     * State chenge event handler for threshold slider.
     * @param e ChangeEvent.
     */
    private void thresholdSliderStateChanged(ChangeEvent e) {
        Color c = (Color) primaryColorList.getSelectedItem();
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        int value = thresholdSlider.getValue();
         if(iw != null){
            if(imgSource != null && iw.getImage() != null){
                try{
                    UmbralizacionOp uop = new UmbralizacionOp(value,c);
                    uop.filter(imgSource, iw.getImage());
                    iw.getCanvas().repaint();
                    desktop.repaint();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al aplicar el filtro: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
            
        }
    }
    
    /**
     * State chenge event handler for sum slider.
     * @param e ChangeEvent.
     */
    private void sumSliderStateChanged(ChangeEvent e) {
        float alpha = sumSlider.getValue()/100.0f;
        if(binarySrc1 != null && binarySrc2 != null){
            try{
                BlendOp bop = new BlendOp(binarySrc1,alpha);
                bop.filter(binarySrc2, binaryDest);
                desktop.repaint();
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Error al sumar: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);   
            }
        }
    }
    
    /**
     * State change event handler for background remove slider.
     * @param e ChangeEvent.
     */
    private void backgroundRemoveSliderStateChanged(ChangeEvent e){
        Color c = (Color) primaryColorList.getSelectedItem();
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        int value = backgroundRemoveSlider.getValue();
         if(iw != null && iw.getCanvas() != null){
            if(imgSource != null){
                try{
                    BackgroundRemoveOp bgop = new BackgroundRemoveOp(value,c);
                    BufferedImage dest = bgop.filter(imgSource, null);
                    iw.getCanvas().setImage(dest);
                    desktop.repaint();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al aplicar el filtro: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
            
        }
    }
    
    /**
     * State change event handler for transparency slider.
     * @param e ChangeEvent.
     */
    private void transparencySliderStateChanged(ChangeEvent e) {
        if(!preventer){
            MediaWindowAdapter iw = (MediaWindowAdapter) desktop.getSelectedFrame();
            if(iw != null && iw.getCanvas() != null){
                iw.getCanvas().updateTransparency(transparencySlider.getValue()/100.0f);
            }
        }
    }
    
    /**
     * State change event handler for modular translation slider.
     * @param e ChangeEvent.
     */
    private void modularTranslationSliderStateChanged(ChangeEvent e) {
        MediaWindowAdapter iw = (MediaWindowAdapter)desktop.getSelectedFrame();
        int value = modularTranslationSlider.getValue();
         if(iw != null && iw.getImage() != null){
            if(imgSource != null){
                try{
                    ModularTranslateOp mtop = new ModularTranslateOp(value);
                    mtop.filter(imgSource, iw.getImage());
                    desktop.repaint();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Error al aplicar el filtro: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);     
                }
            }
            
        }
    }
    
    /**
     * State change event handler for śhape rotation slider.
     * @param e ChangeEvent.
     */
    public void shapeRotationSliderStateChanged(ChangeEvent e){
        if(!preventer){
            MediaWindowAdapter iw = (MediaWindowAdapter) desktop.getSelectedFrame();
            if(iw != null && iw.getCanvas() != null){
                iw.getCanvas().updateRotation(Math.toRadians(shapeRotationSlider.getValue()));
            }
        }
    }
    
    /**
     * State change event handler for shape scale slider.
     * @param e ChangeEvent.
     */
    public void shapeScaleSliderStateChanged(ChangeEvent e){
        if(!preventer){
            MediaWindowAdapter iw = (MediaWindowAdapter) desktop.getSelectedFrame();
            if(iw != null && iw.getCanvas() != null){
                float val = shapeScaleSlider.getValue();
                double scale = Math.pow(2,val/100.0f);
                iw.getCanvas().updateScale(scale);
            }
        }
    }
    
   
    //Audio managers
    class PlayerManager implements LineListener {
        @Override
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.START) {
                playPauseBt.setIcon(new ImageIcon(getClass().getResource("/media/pausa.png")));
                playPauseBt.setToolTipText("Pausa");
            }
            if (event.getType() == LineEvent.Type.STOP) {
                playPauseBt.setIcon(new ImageIcon(getClass().getResource("/media/play.png")));
                playPauseBt.setToolTipText("Reproducir");
                if(event.getFramePosition() == 0){
                    System.out.println(event.getFramePosition());
                    player = null;
                }
            }
            if (event.getType() == LineEvent.Type.CLOSE) {
                
            }
        }
    }
    
    class RecordManager implements LineListener {

        @Override
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.START) {
                recordBt.setIcon(new ImageIcon(getClass().getResource("/media/stopRecord.png")));
                recordBt.setToolTipText("Terminar grabación");
            }
            if (event.getType() == LineEvent.Type.STOP) {
                recordBt.setIcon(new ImageIcon(getClass().getResource("/media/record.png")));
                recordBt.setToolTipText("Grabar");
                recorder = null;
            }
            if (event.getType() == LineEvent.Type.CLOSE) {

            }
        }
    }
    
    private void clipSliderSlided(java.awt.event.MouseEvent evt){
        if(currentPlayerWindow != null){
            currentPlayerWindow.seek(this.clipSlider.getRelativeChange());
        }
    }

    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JButton addColorBt;
    private javax.swing.JToggleButton arcBt;
    private javax.swing.JButton atanBt;
    private javax.swing.JCheckBoxMenuItem attrBarMenuItem;
    private javax.swing.JToolBar.Separator attributesSeparator;
    private javax.swing.JComboBox<File> audioBox;
    private javax.swing.JMenu avBarMenu;
    private javax.swing.JButton backBt;
    private javax.swing.JButton bandsBt;
    private javax.swing.JPopupMenu.Separator barMenuSeparator;
    private javax.swing.JPopupMenu.Separator barMenuSeparator1;
    private javax.swing.JPopupMenu.Separator barMenuSeparator2;
    private javax.swing.JCheckBoxMenuItem binaryBarMenuItem;
    private javax.swing.JToolBar.Separator binarySeparator;
    private javax.swing.JCheckBoxMenuItem brightBarMenuItem;
    private javax.swing.JToggleButton brightnessBt;
    private javax.swing.JToolBar.Separator brightnessSeparator;
    private javax.swing.JMenuItem changeImgSizeMenuItem;
    private sm.jlsd.ui.ClipSlider clipSlider;
    private javax.swing.JButton coldEffectBt;
    private javax.swing.JCheckBoxMenuItem colorBarMenuItem;
    private javax.swing.JToolBar.Separator colorSeparator;
    private javax.swing.JCheckBoxMenuItem colorSpaceBarMenuItem;
    private javax.swing.JComboBox<String> colorSpaceBox;
    private javax.swing.JToolBar.Separator colorSpaceSeparator;
    private javax.swing.JComboBox<String> contrastComboBox;
    private javax.swing.JCheckBoxMenuItem controlBarMenuItem;
    private javax.swing.JCheckBoxMenuItem convolveBarMenuItem;
    private javax.swing.JToggleButton cubicCurveBt;
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JButton diffBt;
    private javax.swing.JMenuItem duplicateMenuItem;
    private javax.swing.JToggleButton editBt;
    private javax.swing.JMenu editMenu;
    private javax.swing.JCheckBoxMenuItem effectsBarMenuItem;
    private javax.swing.JToolBar.Separator effectsSeparator;
    private javax.swing.JButton equalizationBt;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JCheckBoxMenuItem filesBarMenuItem;
    private javax.swing.JComboBox<String> fillComboBox;
    private javax.swing.JComboBox<String> filterBox;
    private javax.swing.JToolBar.Separator filterSeparator;
    private javax.swing.JButton fireEffectBt;
    private javax.swing.JToggleButton flattenBt;
    private javax.swing.JButton forwardBt;
    private javax.swing.JButton gaussBt;
    private javax.swing.JMenu graphicsBarMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem hideAllAvBar;
    private javax.swing.JMenuItem hideAllGraphicsBar;
    private javax.swing.JMenuItem hideAllImageBar;
    private javax.swing.JButton histogramBt;
    private javax.swing.JMenu imageBarMenu;
    private javax.swing.JMenu imageMenu;
    private javax.swing.JToggleButton lineBt;
    private javax.swing.JPanel lowerPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JToggleButton modularTranslationBt;
    private javax.swing.JToggleButton moreEffectsBt;
    private javax.swing.JToggleButton moreOperationsBt;
    private javax.swing.JButton newBt;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JButton openBt;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JToggleButton ovalBt;
    private javax.swing.JCheckBoxMenuItem playBarMenuItem;
    private javax.swing.JButton playPauseBt;
    private javax.swing.JToolBar.Separator playerSeparator;
    private javax.swing.JToolBar.Separator playingSeparator;
    private javax.swing.JToggleButton pointBt;
    private javax.swing.JToggleButton polygonBt;
    private javax.swing.JComboBox<Color> primaryColorList;
    private javax.swing.JButton productBt;
    private javax.swing.JToggleButton quadCurveBt;
    private javax.swing.JCheckBoxMenuItem recordBarMenuItem;
    private javax.swing.JButton recordBt;
    private javax.swing.JToolBar.Separator recordSeparator;
    private javax.swing.JLabel recordTimeLabel;
    private javax.swing.JToggleButton rectangleBt;
    private javax.swing.JToggleButton removeBackgroundBt;
    private javax.swing.JToggleButton rotateBt;
    private javax.swing.JToggleButton rotateExactBt;
    private javax.swing.JToggleButton roundRectangleBt;
    private javax.swing.JButton saveBt;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JButton scaleMinusBt;
    private javax.swing.JButton scalePlusBt;
    private javax.swing.JComboBox<Color> secondaryColorList;
    private javax.swing.JButton sepiaBt;
    private javax.swing.JCheckBoxMenuItem shapeBarMenuItem;
    private javax.swing.JToolBar shapeOperationsToolBar;
    private javax.swing.JToolBar.Separator shapeSeparator;
    private javax.swing.JToggleButton shapesBt;
    private javax.swing.ButtonGroup shapesBtGroup;
    private javax.swing.JComboBox<String> shapesComboBox;
    private javax.swing.JMenuItem showAllAvBar;
    private javax.swing.JMenuItem showAllGraphicsBar;
    private javax.swing.JMenuItem showAllImageBar;
    private javax.swing.JButton sineBt;
    private javax.swing.JButton snapshotBt;
    private javax.swing.JButton sobelBt;
    private paint2d.StateBar stateBar;
    private javax.swing.JCheckBoxMenuItem stateBarMenuItem;
    private javax.swing.JButton stopBt;
    private javax.swing.JToggleButton strokeBt;
    private javax.swing.JToolBar strokeToolBar;
    private javax.swing.JComboBox strokeTypeList;
    private javax.swing.JToggleButton sumBt;
    private javax.swing.JToggleButton sunBt;
    private javax.swing.JSpinner thicknessSpin;
    private javax.swing.JToggleButton tintBt;
    private javax.swing.JButton toBottomBt;
    private javax.swing.JButton toTopBt;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JCheckBoxMenuItem transformBarMenuItem;
    private javax.swing.JToggleButton transformBt;
    private javax.swing.JToolBar.Separator transformSeparator;
    private javax.swing.JToggleButton transparencyBt;
    private javax.swing.JToggleButton umbralBt;
    private javax.swing.JCheckBoxMenuItem webcamBarMenuItem;
    private javax.swing.JButton webcamBt;
    private javax.swing.JToolBar.Separator webcamSeparator;
    // End of variables declaration//GEN-END:variables

    
}
