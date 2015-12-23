package com.badlogicgames.libgdx.site;

class DevLogPost {
	String author;
	String authorAvatarUrl;
	String url;
	String content;
	long time;
	String timeText;

	public DevLogPost() {
	}

	public DevLogPost(String author, String authorAvatarUrl, String url, String content, long time, String timeText) {
		super();
		this.author = author;
		this.authorAvatarUrl = authorAvatarUrl;
		this.url = url;
		this.content = content;
		this.time = time;
		this.timeText = timeText;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getTimeText() {
		return timeText;
	}

	public void setTimeText(String timeText) {
		this.timeText = timeText;
	}
}