package com.badlogicgames.libgdx.site.db;

public class DevLog {
	String author;
	String authorUrl;
	String authorAvatarUrl;
	String url;
	String title;

	public DevLog(String author, String authorUrl, String authorAvatarUrl, String url, String title) {
		super();
		this.author = author;
		this.authorUrl = authorUrl;
		this.authorAvatarUrl = authorAvatarUrl;
		this.url = url;
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorUrl() {
		return authorUrl;
	}

	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}

	public String getAuthorAvatarUrl() {
		return authorAvatarUrl;
	}

	public void setAuthorAvatarUrl(String authorAvatarUrl) {
		this.authorAvatarUrl = authorAvatarUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
