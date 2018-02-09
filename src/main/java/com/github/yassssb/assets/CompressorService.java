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

package com.github.yassssb.assets;


import com.github.yassssb.util.Utils;
import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import org.mozilla.javascript.ErrorReporter;

/**
 * CompressorService : minify and merge CSS and Javascripts
 */
public class CompressorService
{

    public static final int CSS = 1;
    public static final int JS = 2;

    /**
     * Compress files found in a directory 
     * @param strRootPath The directory
     * @param config The compression config
     * @param bMerged If files should be merged into a single file
     * @throws IOException If an error occurs
     */
    public static void compress( String strRootPath, CompressorConfig config, boolean bMerged ) throws IOException
    {
        String strExtension = config.getExtension();
        String strInputPath = strRootPath + config.getInputDir();
        String strOutputPath = strRootPath + config.getOutputDir();
        String[] files = getFiles( strInputPath, strExtension );
        FileWriter out = null;
        String strOutputFilePath = "";
        if( bMerged )
        {
            strOutputFilePath = strOutputPath + File.separator + config.getGlobalFileName();
            Utils.makeDir( strOutputPath );
            out = new FileWriter( strOutputFilePath );
        }
        for( String file : files )
        {
            String strInputFilePath = strInputPath + File.separator + file;
            boolean bCompressed = true;
            String strOutputFilename = file;
            if( ! file.endsWith( config.getCompressedExtension() ))
            {
                strOutputFilename = file.replaceAll( strExtension, config.getCompressedExtension() );
                bCompressed = false;
            }
            FileReader in = new FileReader( strInputFilePath );
            
            if( !bMerged )
            {
                strOutputFilePath = strOutputPath + File.separator + strOutputFilename;
                out = new FileWriter( strOutputFilePath );
            }
            if( ! bCompressed )
            {
                System.out.print( "Compression of '" + strInputFilePath );
                if( bMerged )
                {
                    System.out.println( "' and append content to file '" +  strOutputFilePath + "'" );
                }
                else
                {
                    System.out.println( "' and create file '" +  strOutputFilePath + "'" );
                }
                    
                switch( config.getCompressor() )
                {
                    case CSS:
                        CssCompressor compressorCSS = new CssCompressor( in );
                        compressorCSS.compress( out, 0 );
                        break;

                    case JS:
                        ErrorReporter reporter = new JsErrorReporter( strInputFilePath );
                        JavaScriptCompressor compressorJS = new JavaScriptCompressor(in, reporter );
                        compressorJS.compress( out, CSS, bMerged, bMerged, bMerged, bMerged );
                        break;
                }
            }
            else
            {
                if( bMerged )
                {
                    System.out.println( "Append content of file '" + strInputFilePath + "' into file '"  + strOutputFilePath + "'" );
                }
                else
                {
                    System.out.println( "Copy content of file '" + strInputFilePath + "' into file '"  + strOutputFilePath + "'" );
                }
                int c;
                while( ( c = in.read()) != -1 )
                {
                    if( out != null )
                    {
                        out.write( c );
                    }
                }
            }
            if( !bMerged )
            {
                if( out != null )
                {
                    out.close();
                }
            }
        }
        if( bMerged )
        {
            if( out != null )
            {
                out.close();
            }
        }

    }

    /**
     * Gets all files with a given extension from a given directory 
     * @param directory The directory
     * @param extension The file extension
     * @return The files list
     */
    private static String[] getFiles( String directory, String extension )
    {
        GenericExtFilter filter = new GenericExtFilter( extension );
        File dir = new File( directory );
        String[] files = dir.list( filter );
        Arrays.sort( files );
        return files;
    }

    /**
     * Filename filter by extension
     */
    public static class GenericExtFilter implements FilenameFilter
    {

        private String ext;

        /**
         * Constructor
         * @param ext The filename extension
         */
        public GenericExtFilter( String ext )
        {
            this.ext = ext;
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public boolean accept( File dir, String name )
        {
            return ( name.endsWith( ext ) );
        }
    }
}
