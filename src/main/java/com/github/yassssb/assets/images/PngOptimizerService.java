/* Copyright (c) 2018 Pierre LEVY
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
import com.googlecode.pngtastic.core.PngImage;
import com.googlecode.pngtastic.core.PngOptimizer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * PngOptimizerService
 */
public class PngOptimizerService
{

//    private static final String COMPRESSOR_ZOPFLI = "zopfli";
        private static final String COMPRESSOR_ZOPFLI = "";
    private static final int COMPRESSOR_ITERATIONS = 1;
    private static final String LOGLEVEL = "info";
    private static final boolean REMOVE_GAMME = true;
    private static final int COMPRESSION_LEVEL = 9;

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
        System.out.println( "Optimizing " + strFileName + " from " + strInputDirectory + " and copy to " + strOutputDirectory );
        PngOptimizer optimizer;
        optimizer = new PngOptimizer( LOGLEVEL );
        optimizer.setCompressor( COMPRESSOR_ZOPFLI, COMPRESSOR_ITERATIONS );
        PngImage image = new PngImage( strInputDirectory + File.separator + strFileName, LOGLEVEL );
        Utils.makeDir( strOutputDirectory );
        optimizer.optimize( image, strOutputDirectory + File.separator + strFileName, REMOVE_GAMME, COMPRESSION_LEVEL );

    }

    
}
