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
package com.github.yassssb;

import com.github.yassssb.assets.AssetsManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SiteBuilder
 */
public class SiteBuilder
{

    private static final String SITE_FILE = "/src/site.yml";
    private static final String TEMPLATE_PATH = "/src/pages/";
    private static final String DATA_PATH = "/src/data/";
    private static final String ASSETS_PATH = "/src/assets/";
    private static final String OUTPUT_PATH = "/dist/";

    /**
     * The main 
     * @param args command line arguments 
     */
    public static void main(String[] args)
    {
        try
        {

            if (args.length == 0)
            {
                System.out.println("The site path should be given at first argument.");
                return;
            }
            String strSitePath = args[0];
            String strSiteFile = strSitePath + SITE_FILE;

            // Read YAML site file
            Site site = readYamlSiteFile(strSiteFile);

            // Copy assets
            AssetsManager.deploy(strSitePath);

            // Generate pages
            generatePages(strSitePath, site.getPages());

        }
        catch (IOException | TemplateException ex)
        {
            ex.printStackTrace();
        }

    }

    /**
     * Initialize the Freemarker template engine
     * @param strTemplatePath The template path
     * @return The FM configuration
     * @throws IOException if an error occurs
     */
    private static Configuration getFreemarkerConfig(String strTemplatePath) throws IOException
    {
        // Create your Configuration instance, and specify if up to what FreeMarker
        // version (here 2.3.27) do you want to apply the fixes that are not 100%
        // backward-compatible. See the Configuration JavaDoc for details.
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);

        // Specify the source where the template files come from. Here I set a
        // plain directory for it, but non-file-system sources are possible too:
        cfg.setDirectoryForTemplateLoading(new File(strTemplatePath));

        // Set the preferred charset template files are stored in. UTF-8 is
        // a good choice in most applications:
        cfg.setDefaultEncoding("UTF-8");

        // Sets how errors will appear.
        // During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // Don't log exceptions inside FreeMarker that it will thrown at you anyway:
        cfg.setLogTemplateExceptions(false);

        // Wrap unchecked exceptions thrown during template processing into TemplateException-s.
        cfg.setWrapUncheckedExceptions(true);

        return cfg;
    }

    /**
     * Extrat the data name from the YAML filename (removing the extension)
     * @param strDataFile The YAML filename
     * @return The data name
     */
    private static Object getDataName(String strDataFile)
    {
        // Remove the file extension
        int nPos = strDataFile.indexOf('.');
        if (nPos > 0)
        {
            return strDataFile.substring(0, nPos);
        }
        return strDataFile;
    }

    
    /**
     * Generate site pages
     * @param strSitePath The site path
     * @param pages The list of pages to generate
     * @throws MalformedTemplateNameException if an error occurs
     * @throws IOException if an error occurs
     * @throws TemplateException  if an error occurs
     */
    private static void generatePages(String strSitePath, List<Page> pages) throws MalformedTemplateNameException, IOException, TemplateException
    {
        // Initialize Freemarker
        String strTemplatePath = strSitePath + TEMPLATE_PATH;
        Configuration cfg = getFreemarkerConfig(strTemplatePath);

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        System.out.println( "\n\n ############# Generating pages ##############\n" );
        for (Page page : pages)
        {
            Template template = cfg.getTemplate(page.getTemplate());
            Map model = new HashMap();

            for (String filename : page.getData())
            {
                String strDataFile = strSitePath + DATA_PATH + filename;
                Object object = mapper.readValue(new File(strDataFile), Object.class);
                model.put(getDataName(filename), object);
            }

            String strOutputPath = strSitePath + OUTPUT_PATH;
            FileWriter out = new FileWriter(strOutputPath + page.getPage());
            template.process(model, out);
            System.out.println( "Generating page " + page.getPage());
        }
    }

    /**
     * Read the SiteFile in YAML format (site.yml)
     * @param strSiteFile The site file path
     * @return a Site object
     * @throws IOException if an error occurs 
     */
    private static Site readYamlSiteFile(String strSiteFile) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(new File(strSiteFile), Site.class);

    }
}
