/* Copyright (c) 2018-2021 Pierre LEVY
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.yassssb.assets.images;

import com.github.yassssb.util.Utils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.*;
import org.apache.commons.io.FileUtils;


/**
 * JpegOptimizerService
 */
public class JpegOptimizerService 
{
    private static final float COMPRESSION_LEVEL = 0.5f;

    /**
     * 
     * @param strFileName The file name
     * @param strInputDirectory The input directory
     * @param strOutputDirectory The output directory
     * @throws FileNotFoundException If the file is not found
     * @throws IOException if an other error occurs
     */
    static void optimize( String strFileName, String strInputDirectory, String strOutputDirectory ) throws FileNotFoundException, IOException
    {

        Iterator iter = ImageIO.getImageWritersByFormatName("jpeg");

        ImageWriter writer = (ImageWriter)iter.next();
        // instantiate an ImageWriteParam object with default compression options
        ImageWriteParam iwp = writer.getDefaultWriteParam();

        // Set the compression quality

        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        iwp.setCompressionQuality( COMPRESSION_LEVEL );   // an integer between 0 and 1
        // 1 specifies minimum compression and maximum quality

        // Read the input image
        File input = new File( strInputDirectory + File.separator + strFileName );
        long lInputSize = input.length();
        ImageInputStream iis = ImageIO.createImageInputStream(input);
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        ImageReader reader = (ImageReader) readers.next();
        reader.setInput(iis, false);
        BufferedImage bi = reader.read(0);
        IIOImage image = new IIOImage( bi, null, null);
        
        Utils.makeDir( strOutputDirectory );
        File file = new File( strOutputDirectory + File.separator + strFileName );
        if( file.exists() )
        {
            FileUtils.forceDelete( file );
        }
        FileImageOutputStream output = new FileImageOutputStream(file);
        writer.setOutput(output);
        writer.write(null, image, iwp);
        writer.dispose();
        long lOutputSize = file.length();
        
        long lRatio = ( 100 * (lInputSize - lOutputSize)) / lInputSize;
        String strLog = String.format("Optimizing %s from %s and copy to %s \n  %d%% : %dB -> %dB ", strFileName, strInputDirectory, strOutputDirectory, lRatio, lInputSize, lOutputSize );
        System.out.println( strLog );
    }
}
