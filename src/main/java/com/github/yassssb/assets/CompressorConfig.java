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


/**
 * CompressorConfig
 */
public class CompressorConfig 
{

    private String extension;
    private int compressor;
    private String globalFileName;
    private String compressedExtension;
    private String inputDir;
    private String outputDir;
            

    /**
     * @return the extension
     */
    public String getExtension()
    {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension( String extension )
    {
        this.extension = extension;
    }

    /**
     * @return the compressor
     */
    public int getCompressor()
    {
        return compressor;
    }

    /**
     * @param compressor the compressor to set
     */
    public void setCompressor( int compressor )
    {
        this.compressor = compressor;
    }

    /**
     * @return the globalFileName
     */
    public String getGlobalFileName()
    {
        return globalFileName;
    }

    /**
     * @param globalFileName the globalFileName to set
     */
    public void setGlobalFileName( String globalFileName )
    {
        this.globalFileName = globalFileName;
    }

    /**
     * @return the compressedExtension
     */
    public String getCompressedExtension()
    {
        return compressedExtension;
    }

    /**
     * @param compressedExtension the compressedExtension to set
     */
    public void setCompressedExtension( String compressedExtension )
    {
        this.compressedExtension = compressedExtension;
    }
    
    /**
     * @return the inputDir
     */
    public String getInputDir()
    {
        return inputDir;
    }

    /**
     * @param inputDir the inputDir to set
     */
    public void setInputDir( String inputDir )
    {
        this.inputDir = inputDir;
    }

    /**
     * @return the outputDir
     */
    public String getOutputDir()
    {
        return outputDir;
    }

    /**
     * @param outputDir the outputDir to set
     */
    public void setOutputDir( String outputDir )
    {
        this.outputDir = outputDir;
    }
    
}
