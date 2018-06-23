
package sm.jlsd.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import uk.co.caprica.vlcj.filter.AudioFileFilter;
import uk.co.caprica.vlcj.filter.ExtensionFileFilter;
import uk.co.caprica.vlcj.filter.VideoFileFilter;

/**
 * Class MediaFileFilter.
 * A FileFilter for every multimedia file supported by this library.
 * Based on VLCj extension filters.
 * @author jlsuarezdiaz
 */
public class MediaFileFilter extends ExtensionFileFilter{
    /**
     * Extensions list.
     */
    private static final String[] EXTENSIONS_MEDIA = Stream.concat(Stream.concat(Arrays.stream(ImageIO.getReaderFormatNames()),
                                                                    Arrays.stream(AudioFileFilter.INSTANCE.getExtensions())),
                                                                    Arrays.stream(VideoFileFilter.INSTANCE.getExtensions())).
                                                                toArray(String[]::new) ;
    
    /**
     * MediaFileFilter instance.
     */
    public static final MediaFileFilter INSTANCE = new MediaFileFilter();
    
    /**
     * Constructor.
     */
    public MediaFileFilter(){
        super(EXTENSIONS_MEDIA);
    }
    
}
