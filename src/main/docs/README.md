## LibGDX document generation tool


Tool to generate official documentation for LibGDX. 

Documentation is generated from markdown files with frontmatter headers for search indexing. Markdown headers are also parsed for search indexes which is
generated during the serve process.  Any changes to the css/img/js/template files will resync the browser thanks to browsersync.

#### Build instructions

```bash
npm install

gulp serve
```

#### Deploy instructions

```bash
gulp deploy
```