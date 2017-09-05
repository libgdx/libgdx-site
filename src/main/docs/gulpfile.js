var path = require("path");
var gulp = require("gulp");
var browsersync = require("browser-sync").create();
var data = require("gulp-data");
var swig = require("gulp-swig");
var del = require("del");
var marked = require("marked");
var frontmatter = require("front-matter");
var fs = require ("fs-extra");

var globalTokens = [];

function TokenMap (document, fullpath) {

    this.tokens = [];
    this.document = document.replace("index", "Introduction");

    this.path = fullpath;
    this.path = this.path.replace(".md", ".html");

    this.addToken = function addToken (text) {

        text = text.replace(/\<().*?\>/g, "");

        var split = text.split(" ");

        for (var i = 0; i < split.length; i++) {
            if (split[i] == "br") continue;
            if (split[i] == "") continue;
            this.tokens.push(split[i]);
        }
        
    }
}


gulp.task("clean", function() {
    return del.sync(['dist/index.html']);
});

gulp.task("templates", ['clean'], function () {
    return gulp.src(['src/docs/**/*.md'])
    .pipe(data(function(file) {
        var template = new Buffer(fs.readFileSync("templates/default"));

        var content = frontmatter(String(file.contents));

        var name = path.basename(file.path, ".md");

        var relative = path.relative(__dirname + "/src/docs/", file.path);
        

        var tokenPath = new TokenMap(name, relative);
        globalTokens.push(tokenPath);
        
        var title = content.attributes.title;
        var additionalTags = content.attributes.tags;
        var body = content.body;

        if (additionalTags != null) {
            for (var i = 0; i < additionalTags.length; i++) {
                tokenPath.addToken(additionalTags[i]);
            }
        }

        var headerRenderer = new marked.Renderer();
        headerRenderer.heading = function (text, level) {
        var escapedText = text.toLowerCase().replace(/[^\w]+/g, '-');

        tokenPath.addToken(text);
        return '<h' + level + '><a name="' +
                        escapedText +
                        '" class="anchor" href="#' +
                        escapedText +
                        '">' + text + '</a></h' + level + '>';
        }

        try {
            var markedBody = marked(body, { renderer: headerRenderer });
            var tokens = marked.lexer(body);
            var output = marked.parser(tokens);

            file.contents = template;

            var data = {
                content: "content",
                body: markedBody,
                title: title
            }
        } catch (e) {
            console.error(e);
        }
        return data;
    }))
    .pipe(swig({ defaults: {autoescape: false, cache: false} }))
    .pipe(gulp.dest('dist/documentation'));
});

gulp.task('css', function() {
    return gulp.src(['src/css/**.css'])
    .pipe(gulp.dest('dist/css/'));
});


gulp.task('js', function() {
    return gulp.src(['src/js/**.js'])
    .pipe(gulp.dest('dist/js/'));
});

gulp.task('img', function() {
    return gulp.src(['src/img/**'])
    .pipe(gulp.dest('dist/img/'));
});

gulp.task("index", ['templates'], function () {
    fs.writeFile("dist/documentation/documentation_index", JSON.stringify(globalTokens), "UTF-8");
})

gulp.task('build', ['templates', 'img', 'css', 'js', 'index'])

gulp.task('deploy', ['build'], function () {
    del.sync(['deploy/']);
    gulp.src(['dist/documentation/**/*']).pipe(gulp.dest('deploy/documentation'));
})

gulp.task('serve', ['build'], function() {

    browsersync.init({
        server: "./dist",
        startPath: "documentation",
        scrollRestoreTechnique: 'cookie'
    });

    gulp.watch(['src/**', 'templates/**'], ['build']);
    gulp.watch("dist/**").on('change', browsersync.reload);
});