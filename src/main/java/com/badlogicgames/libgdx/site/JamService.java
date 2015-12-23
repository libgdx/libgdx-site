package com.badlogicgames.libgdx.site;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.badlogicgames.libgdx.site.db.DevLog;
import com.sun.jersey.spi.resource.Singleton;

/**
 * Crawls a itch.io community site and scans for new dev log
 * entries.
 * @author badlogic
 *
 */
@Path("/jam")
@Singleton
public class JamService {
	private List<DevLogPost> posts = new ArrayList<DevLogPost>();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public JamService() {
		Thread t = new Thread(new Runnable() {

			public void run() {
				while(true) {
					try {
						updateDevLogPosts();
						Thread.sleep(1000 * 60 * 5);
					} catch (Exception e) {					
						e.printStackTrace();
					}
				}
			}
			
		});
		t.setDaemon(true);
		t.start();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("posts")
	public List<DevLogPost> getDevLogPosts() {
		return posts;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updatePosts")
	public List<DevLogPost> updateDevLogPosts() throws IOException {
		List<DevLog> logs = collectLogs();
		List<DevLogPost> logPosts = new ArrayList<DevLogPost>();
		for(DevLog log: logs) {
			logPosts.addAll(processLogPosts(log));
		}		
		Collections.sort(logPosts, new Comparator<DevLogPost>() {
			public int compare(DevLogPost o1, DevLogPost o2) {
				return (int)o2.time - (int)o1.time;
			}
		});
		posts = logPosts;
		return posts;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("collectLogs")
	public List<DevLog> collectLogs() throws IOException {
		List<DevLog> logs = new ArrayList<DevLog>();
		Document page = Jsoup.connect("http://itch.io/jam/libgdxjam/community").get();
		while(page != null) {
			logs.addAll(processLogPage(page));			
			Elements pageLinks = page.getElementsByClass("page_link");
			page = null;
			if(pageLinks.size() != 0) {
				for(Element pageLink: pageLinks) {
					if("Next page".equals(pageLink.text())) {
						page = Jsoup.connect("https://itch.io" + pageLink.attr("href")).get();						
						break;
					}
				}
			}
		}
		return logs;
	}		
	
	private List<DevLog> processLogPage(Document page) throws IOException {
		List<DevLog> logs = new ArrayList<DevLog>();
		Elements topics = page.select(".topic_row");
		for(Element topic: topics) {
			Element title = topic.getElementsByClass("topic_title").first().getElementsByTag("a").first();			
			String logTitle = title.text();
			String logURL = "https://itch.io" + title.attr("href");
			Element author = topic.getElementsByClass("topic_poster").first().getElementsByClass("topic_author").first();
			String authorName = author.text();
			String authorURL = "https://itch.io" + author.attr("href");
			if(!logTitle.toLowerCase().contains("introduce yourself!")) {
				logs.add(new DevLog(authorName, authorURL, null, logURL, logTitle));
			}
			
		}
		return logs;
	}
	
	private List<DevLogPost> processLogPosts(DevLog log) throws IOException {
		List<DevLogPost> posts = new ArrayList<DevLogPost>();
		try {
			Document page = Jsoup.connect(log.getUrl()).get();
			while(page != null) {				
				for(Element entry: page.getElementsByClass("community_post")) {
					try {
						if(entry.hasClass("deleted")) {
							continue;
						}
						String avatarUrl = entry.getElementsByClass("post_avatar").first().attr("style");
						avatarUrl = avatarUrl.split("url")[1].replace(")", "").replace("(", "");
						if(!avatarUrl.startsWith("http")) {
							avatarUrl = "https://itch.io" + avatarUrl;
						}
						Element row = entry.getElementsByClass("post_content").first();
						String author = row.getElementsByClass("post_author").first().getElementsByTag("a").first().text();
						if(!log.getAuthor().equals(author)) {
							continue;
						}					
						Element postDate = row.getElementsByClass("post_date").first();
						String url = "https://itch.io" + postDate.getElementsByTag("a").attr("href");
						String date = postDate.attr("title");
						long dateUTC = dateFormat.parse(date).getTime();
						String content = row.getElementsByClass("post_body").first().toString();
						posts.add(new DevLogPost(author, avatarUrl, url, content, dateUTC, date));					
					} catch(Throwable e) {
						e.printStackTrace();
						System.out.println("Couldn't parse post " + log.getUrl());
					}
				}
				
				Elements pageLinks = page.getElementsByClass("page_link");
				page = null;
				if(pageLinks.size() != 0) {
					for(Element pageLink: pageLinks) {
						if("Next page".equals(pageLink.text())) {
							page = Jsoup.connect("https://itch.io" + pageLink.attr("href")).get();						
							break;
						}
					}
				}
			}			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return posts;
	}
}
