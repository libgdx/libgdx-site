package com.badlogicgames.libgdx.site;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Versions {

	public boolean success;
	public String message;

	public boolean release;

	public String libgdxRelease;
	public String libgdxSnapshot;

	public String robovmVersion;
	public String robovmPluginVersion;

	public String androidBuildtoolsVersion;
	public String androidSDKVersion;
	public String androidGradleToolVersion;

	public String gwtVersion;
	public String gwtPluginVersion;

	public Versions() {

	}

	public Versions(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public Versions(boolean success, String message,
					boolean release,
					String libgdxRelease, String libgdxSnapshot,
					String robovmVersion, String robovmPluginVersion,
					String androidBuildtoolsVersion, String androidSDKVersion, String androidGradleToolVersion,
					String gwtVersion, String gwtPluginVersion) {
		this.success = success;
		this.message = message;
		this.release = release;
		this.libgdxRelease = libgdxRelease;
		this.libgdxSnapshot = libgdxSnapshot;
		this.robovmVersion = robovmVersion;
		this.robovmPluginVersion = robovmPluginVersion;
		this.androidBuildtoolsVersion = androidBuildtoolsVersion;
		this.androidSDKVersion = androidSDKVersion;
		this.androidGradleToolVersion = androidGradleToolVersion;
		this.gwtVersion = gwtVersion;
		this.gwtPluginVersion = gwtPluginVersion;
	}


	public Versions (boolean success, boolean release, File cachedFile) {
		this.success = success;
		this.release = release;
		this.message = "Completed";

		String bigString = null;

		try {
			byte[] byteArray = Files.readAllBytes(Paths.get(cachedFile.getPath()));
			bigString = new String(byteArray, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (bigString == null) {
			this.success = false;
			this.message = "IOException";
			return;
		}

		libgdxRelease = get("libgdxVersion", bigString);
		libgdxSnapshot = get("libgdxNightlyVersion", bigString);

		robovmVersion = get("roboVMVersion", bigString);
		robovmPluginVersion = getPlugin("roboVMPluginImport", bigString);

		androidBuildtoolsVersion = get("buildToolsVersion", bigString);
		androidSDKVersion = get("androidAPILevel", bigString);
		androidGradleToolVersion = getPlugin("androidPluginImport", bigString);

		gwtVersion = get("gwtVersion", bigString);
		gwtPluginVersion = getPlugin("gwtPluginImport", bigString);
	}

	private String get(String token, String content) {
		Pattern versionPattern = Pattern.compile(token + "\\s=\\s\"(.*?)\"");
		Matcher matcher = versionPattern.matcher(content);

		String versionString = null;

		while (matcher.find()) {
			versionString = matcher.group(1);
		}
		return versionString != null ? versionString : "Unknown";
	}

	private String getPlugin(String token, String content) {
		Pattern versionPattern = Pattern.compile(token + "\\s=\\s\"(.*?)\"");
		Matcher matcher = versionPattern.matcher(content);

		String versionString = null;

		while (matcher.find()) {
			versionString = matcher.group(1);
		}
		return versionString != null ? versionString.substring(versionString.lastIndexOf(":") + 1) : "Unknown";
	}

}
