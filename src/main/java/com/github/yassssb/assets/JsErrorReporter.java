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


import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

/**
 * JsErrorReporter
 */
public class JsErrorReporter implements ErrorReporter
{

    private String localFilename;

    public JsErrorReporter( String localFilename )
    {
        this.localFilename = localFilename;
    }

    @Override
    public void warning( String message, String sourceName,
            int line, String lineSource, int lineOffset )
    {
        System.err.println( "\n[WARNING] in " + localFilename );
        if( line < 0 )
        {
            System.err.println( "  " + message );
        }
        else
        {
            System.err.println( "  " + line + ':' + lineOffset + ':' + message );
        }
    }

    @Override
    public void error( String message, String sourceName,
            int line, String lineSource, int lineOffset )
    {
        System.err.println( "[ERROR] in " + localFilename );
        if( line < 0 )
        {
            System.err.println( "  " + message );
        }
        else
        {
            System.err.println( "  " + line + ':' + lineOffset + ':' + message );
        }
    }

    @Override
    public EvaluatorException runtimeError( String message, String sourceName,
            int line, String lineSource, int lineOffset )
    {
        error( message, sourceName, line, lineSource, lineOffset );
        return new EvaluatorException( message );
    }
}
