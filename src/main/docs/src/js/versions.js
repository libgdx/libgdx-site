function getLatestVersions(document) {

	$.ajax({
		url: "libgdx-site/service/getVersions?release=true",
		success: processVersions,
		dataType: "json",
		error: function() {
			//Error
		}
	});
    
    $.ajax({
		url: "libgdx-site/service/getVersions?release=false",
		success: processVersions,
		dataType: "json",
		error: function() {
			//Error
		}
	});

}

function processVersions(data) {
        var modifier = data.release ? "Release" : "Snapshot";
        $('#libgdx' + modifier).text(data.release ? data.libgdxRelease : data.libgdxSnapshot);
        $('#robovm' + modifier).text(data.robovmVersion);
        $('#robovmGradle' + modifier).text(data.robovmPluginVersion);
        $('#androidTools' + modifier).text(data.androidBuildtoolsVersion);
        $('#androidSDK' + modifier).text(data.androidSDKVersion);
        $('#androidGradleTools' + modifier).text(data.androidGradleToolVersion);
        $('#gwt' + modifier).text(data.gwtVersion);
        $('#gwtGradle' + modifier).text(data.gwtPluginVersion);
} 
