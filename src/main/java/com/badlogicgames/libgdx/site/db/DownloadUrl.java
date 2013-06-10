package com.badlogicgames.libgdx.site.db;

public class DownloadUrl {
	public static enum DownloadUrlType {
		GooglePlay,
		iOSAppStore,
		Steam,
		Source,
		Other
	}
	public String url;
	public DownloadUrlType type;
}
