# Yassssb (Yet Another Super Simple Static Site Builder)

Yassssb is a very simple tool that generate a static HTML site from templates and data files.

- Data are stored as [YAML](http://www.yaml.org/spec/1.2/spec.html) files
- Templates are [Freemaker](https://freemarker.apache.org/) files
- Assets are optimized (CSS and JS are minified and merged)


## Site building structure 

Here is the directory tree required :

```
- + - src - + - assets (all assets of the site : css, js, images, ...)
  |         + - data (all yaml files)
  |         + - pages  (contains all HTML templates)
  |         + - site.yml (the site description in YAML format)
  |    
  + - dist (the directory where the site will be generated)
```


## Sample of a site.yml file

``` yaml
## site.yml sample
---
pages:
    - page: index.html               # The output page that will be generated into /dist directory 
      template: index.ftl            # The page template located into /src/pages/
      data: [  ]                     # Data files located into /src/data/
    
    - page: page1.html
      template: page1.ftl
      data: [ "commons.yml" , "data1.yml" ]
```

## Using data in templates

Here is YAML data sample file 

``` yaml
## data1.yml sample
---
links:
    - url: http://google.fr
      title: Google
   
    - url: http://github.com
      title: GitHub
```

Here is the template using the data
``` html

<#list data1.links as link>
    <a href="${link.url}">${link.title}</a>
</#list>
```
 




      

