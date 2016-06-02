function fetchSkins() {

	$.ajax({
		url: "libgdx-site/service/getSkins",
		success: processSkins,
		dataType: "json",
		error: function() {
			//Error
		}
	});

}

function processSkins (data) {
    for (var i = 0; i < data.length; i++) {

        var title = "<h2>" + data[i].title + "</h2>";
        var author = "<h3>by " + data[i].author + "</h3>";
        var link = "<a class='btn skin-download' href='libgdx-skins-dist/" + data[i].foldername + ".zip')><img src='img/save.png' height='28px'/>Download</a>";
        var preview = "<a style='margin-right: 4px' class='btn skin-download' href='demos/libgdx-skins?skin=" + data[i].foldername + "')><img src='img/preview.png' style='margin-right:2px;' height='28px'/>Preview</a>";

        var divString = "<div class='one-of-three' id=''>" + title + author + "<div class='skin-element' id='" + data[i].foldername + "-preview'></div>" + preview + link + "</div>"

        $('#skins').append(divString);
        $('#' + data[i].foldername + "-preview").css('background-image', 'url(libgdx-skins-dist/skin-previews/' + data[i].foldername + '-preview.png)');
    }
}
