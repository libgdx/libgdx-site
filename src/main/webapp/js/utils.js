function testGame() {
	return {
			"id": "apparatus",
			"title": "Apparatus",
			"creator":  "Bithack",
			"description": "Build a complex machine to perform a simple task. Apparatus is a game about setting up simple mechanical structures to move one or several marbles to the goal.",
			"videoUrl": "",
			"imageUrls": [
				"http://libgdx.googlecode.com/svn/wiki/devguide/apparatus.jpg"
			],
			"downloadUrls": [
				{ "name": "Play Store", "url": "https://play.google.com/store/apps/details?id=com.bithack.apparatus" }
			]
		};
} 

function testGames(num) {
	var games = new Array();
	for(var i = 0; i < num; i++) {
		games.push(testGame());
	}
	return games;
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}