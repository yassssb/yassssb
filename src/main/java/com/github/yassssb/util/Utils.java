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

package com.github.yassssb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 * Utils
 */
public class Utils 
{
    /**
     * Create a directory if not exists
     * @param strPath The directory path
     * @throws IOException if an error occurs
     */
    public static void makeDir( String strPath ) throws IOException
    {
        File out = new File( strPath );
        if( !out.exists() )
        {
            if( !out.mkdirs() )
            {
                throw new IOException( "Couldn't create path: " + strPath );
            }
        }
    }

    /**
     * Copy a file to a destination directory
     * @param fileInput The file
     * @param strPath The destination path
     * @throws IOException if an error occurs
     */
    public static void copyFile(File fileInput , String strPath ) throws IOException
    {
        File fileOutput = new File( strPath + fileInput.getName() );
        FileUtils.copyFile( fileInput, fileOutput );
    }

}
