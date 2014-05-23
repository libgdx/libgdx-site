function getLatestVersions(document) {
    $.get("https://api.github.com/repos/libgdx/libgdx/tags", function (data) {
        this.latest = data[0].name;
        $('#releaseVersion').text(this.latest);
        var splits = data[0].name.split(".");
        var snapshot = splits[0] + "." + splits[1] + "." + increment(splits[2]) + "-SNAPSHOT";
        $('#snapshotVersion').text(snapshot);
    });
    updateVersions(true);
    updateVersions(false);
}

function increment(inString) {
    var intVer = parseInt(inString);
    return intVer + 1;
}

function updateVersions(isRelease) {
    var modifier = "";
    if (isRelease) {
        modifier = "Release";
    } else {
        modifier = "Snapshot";
    }

    var url = "http://libgdx.badlogicgames.com/nightlies/dist/versions.txt";
    $.get(url, function (data) {
        var root = data.split("\n")[isRelease ? 0 : 1];
        var start = root.indexOf("[") + 1;
        var end = root.indexOf("]", start);
        var versionString = root.substring(start, end);
        var split = versionString.split(":");
        $('#robovm' + modifier).text(split[0]);
        $('#robovmGradle' + modifier).text(split[1]);
        $('#androidTools' + modifier).text(split[2]);
        $('#androidSDK' + modifier).text(split[3]);
        $('#androidGradleTools' + modifier).text(split[4]);
        $('#gwt' + modifier).text(split[5]);
        $('#gwtGradle' + modifier).text(split[6]);
    });
}