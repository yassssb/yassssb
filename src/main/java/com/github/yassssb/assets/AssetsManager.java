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

import com.github.yassssb.assets.images.ImagesOptimizer;
import com.github.yassssb.util.Utils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 * AssetsManager 
 */
public class AssetsManager 
{
    private static final String ASSETS_PATH = "/src/assets/";
    private static final String OUTPUT_PATH = "/dist/";
    private static final String IMAGES_DIR = "images";
    private static final String FONTS_DIR = "fonts";
    private static final String CSS_DIR = "css";
    private static final String JS_DIR = "js";

    private static final String[] SPECIAL_ASSETS_DIRECTORIES = { "css" , "js" , "images" }; 
    
    /**
     * Deploy assets into dist directory
     * @param strRootPath The source root path
     * @throws IOException if an error occurs
     */
    public static void deploy( String strRootPath ) throws IOException
    {
        System.out.println( "\n\n ############# DEPLOYING ASSETS ##############\n" );

        // Optimize and copy images
        System.out.println( "\n\n ############# Optimizing PNG and JPEG images ##############\n" );
        File dirImages = new File( strRootPath + ASSETS_PATH + IMAGES_DIR );
        ImagesOptimizer.processDirectory( dirImages , "/" , strRootPath + OUTPUT_PATH + IMAGES_DIR );


        // Merge and minify CSS
        System.out.println( "\n\n ############# Minification of CSS files into one global file ##############\n" );
        CompressorConfig configCSS = new CompressorConfig();
        configCSS.setExtension( ".css" );
        configCSS.setCompressor( CompressorService.CSS );
        configCSS.setGlobalFileName( "global.min.css" );
        configCSS.setCompressedExtension( ".min.css" );
        configCSS.setInputDir( ASSETS_PATH + CSS_DIR );
        configCSS.setOutputDir( OUTPUT_PATH + CSS_DIR );

        CompressorService.compress( strRootPath, configCSS, true );
        
        // Merge and minify JS
        System.out.println( "\n\n ############# Minification of JS files into one global file ##############\n" );
        CompressorConfig configJS = new CompressorConfig();
        configJS.setExtension( ".js" );
        configJS.setCompressor( CompressorService.JS );
        configJS.setGlobalFileName( "global.min.js" );
        configJS.setCompressedExtension( ".min.js" );
        configJS.setInputDir( ASSETS_PATH + JS_DIR );
        configJS.setOutputDir( OUTPUT_PATH + JS_DIR );

        CompressorService.compress( strRootPath, configJS, true );
        
        // Copy other assets
        System.out.println( "\n\n ############# Copy other assets files ##############\n" );
        File dirAssets = new File( strRootPath + ASSETS_PATH );
        copyDirectoryFiles( dirAssets , "" , strRootPath + OUTPUT_PATH );
        
        
    }
    
    /**
     * Copy all assets files recursively 
     * @param directory The current directory
     * @param strRelativePath The relative path from the input root 
     * @param strOutputPath The output root path
     */
    private static void copyDirectoryFiles( File directory , String strRelativePath , String strOutputPath ) 
    {
        File[] files = directory.listFiles();
        List<String> listNoFollowDirectories = Arrays.asList( SPECIAL_ASSETS_DIRECTORIES );

        for( File file : files )
        {
            try
            {
                if( file.isDirectory() )
                {
                    if( ! listNoFollowDirectories.contains(file.getName()) )
                    {
                        copyDirectoryFiles( file , strRelativePath +  file.getName() + File.separator , strOutputPath );
                    }
                }
                else
                {
                    Utils.copyFile( file , strOutputPath + strRelativePath );
                    System.out.println( "Copying "+ file.getAbsolutePath() + " to " + strOutputPath + strRelativePath );
                }
            }
            catch( IOException ex )
            {
                ex.printStackTrace();
            }
        }
    }

}
