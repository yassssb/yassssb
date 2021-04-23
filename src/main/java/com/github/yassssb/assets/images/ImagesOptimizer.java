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


import java.io.File;
import java.io.IOException;

/**
 * ImagesOptimizer
 */
public class ImagesOptimizer 
{
    private static final String PNG_EXT = ".png";
    private static final String JPG_EXT = ".jpg";
    private static final String JPEG_EXT = ".jpeg";
    
    /**
     * Process recusively an directory tree that contains PNG or JPEG images.
     * It optimize images and copy them into an output tree
     * @param directory The current directory
     * @param strRelativePath The relative path from the root
     * @param strOutputPath the output root path
     */
    public static void processDirectory( File directory , String strRelativePath , String strOutputPath ) 
    {
        File[] files = directory.listFiles();
        for( File file : files )
        {
            try 
            {
                
            
            if( file.isDirectory() )
            {
                processDirectory( file , strRelativePath + file.getName() , strOutputPath );
            }
            if( file.getName().endsWith( PNG_EXT ))
            {
                PngOptimizerService.optimize( file.getName(), directory.getAbsolutePath() , strOutputPath + strRelativePath );
            }
            else if( file.getName().endsWith( JPG_EXT ) || file.getName().endsWith( JPEG_EXT ) )
            {
                JpegOptimizerService.optimize( file.getName(), directory.getAbsolutePath() , strOutputPath + strRelativePath );
            }
            }
            catch( IOException ex )
            {
                System.out.println( "***** Error optimizing file : " + file.getAbsolutePath()+ " Message : " + ex.getMessage() );
            }
        }
    }
}
