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

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author pierre
 */
public class AssetsManager 
{
    private static final String ASSETS_PATH = "/src/assets/";
    private static final String OUTPUT_PATH = "/dist/";
    private static final String IMAGES_DIR = "images";
    private static final String FONTS_DIR = "fonts";
    private static final String CSS_DIR = "css";
    private static final String JS_DIR = "js";

    public static void deploy( String strRootPath ) throws IOException
    {
        // Copy images
        File srcDir = new File( strRootPath + ASSETS_PATH + IMAGES_DIR);
        File destDir = new File( strRootPath + OUTPUT_PATH + IMAGES_DIR);
        FileUtils.copyDirectory(srcDir, destDir);


        // Copy images
        srcDir = new File( strRootPath + ASSETS_PATH + FONTS_DIR);
        destDir = new File( strRootPath + OUTPUT_PATH + FONTS_DIR);
        FileUtils.copyDirectory(srcDir, destDir);

        // Merge and minify CSS
        CompressorConfig configCSS = new CompressorConfig();
        configCSS.setExtension( ".css" );
        configCSS.setCompressor( CompressorService.CSS );
        configCSS.setGlobalFileName( "global.min.css" );
        configCSS.setCompressedExtension( ".min.css" );
        configCSS.setInputDir( ASSETS_PATH + CSS_DIR );
        configCSS.setOutputDir( OUTPUT_PATH + CSS_DIR );

        System.out.println( "### Minification of CSS files into one global file" );
        CompressorService.compress( strRootPath, configCSS, true );
        
        // Merge and minify JS
        CompressorConfig configJS = new CompressorConfig();
        configJS.setExtension( ".js" );
        configJS.setCompressor( CompressorService.JS );
        configJS.setGlobalFileName( "global.min.js" );
        configJS.setCompressedExtension( ".min.js" );
        configJS.setInputDir( ASSETS_PATH + JS_DIR );
        configJS.setOutputDir( OUTPUT_PATH + JS_DIR );

        System.out.println( "### Minification of JS files into one global file" );
        CompressorService.compress( strRootPath, configJS, true );
        
    }
}
