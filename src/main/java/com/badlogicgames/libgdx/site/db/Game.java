package com.badlogicgames.libgdx.site.db;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Game {
	public String id;
	public Date created;
	public String title;
	public String creator;
	public String description;
	public String videoUrl;
	public List<String> imageUrls;
	public List<DownloadUrl> downloadUrls;
}
