package com.badlogicgames.libgdx.site;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Scoring {
	public static void main(String[] argv) throws JsonParseException, JsonMappingException, UnsupportedEncodingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String postsJson = HttpUtils.getHttp("https://libgdx.badlogicgames.com/libgdx-site/service/jam/posts");
		DevLogPost[] posts = mapper.readValue(postsJson.getBytes("UTF-8"), DevLogPost[].class);
		
		Map<String, List<DevLogPost>> postsByAuthor = new HashMap<String, List<DevLogPost>>();
		for(DevLogPost post: posts) {
			List<DevLogPost> authorPosts = postsByAuthor.get(post.author);
			if(authorPosts == null) {
				authorPosts = new ArrayList<DevLogPost>();
				postsByAuthor.put(post.author, authorPosts);
			}
			authorPosts.add(post);
		}
		
		List<Entry<String, List<DevLogPost>>> sortedAuthors = new ArrayList<Entry<String, List<DevLogPost>>>(postsByAuthor.entrySet());
		Collections.sort(sortedAuthors, new Comparator<Entry<String, List<DevLogPost>>>() {
			public int compare(Entry<String, List<DevLogPost>> o1, Entry<String, List<DevLogPost>> o2) {
				return o2.getValue().size() - o1.getValue().size();
			}
		});
		
		// print second and third place winners
		System.out.println("First place: Lixus, Project Magellan");
		System.out.println("Second place: " + sortedAuthors.get(0).getKey() + ", " + sortedAuthors.get(0).getValue().get(0).url);
		System.out.println("Third place: " + sortedAuthors.get(1).getKey() + ", " + sortedAuthors.get(1).getValue().get(1).url);
		
		// remove winner by overall category, and the two 
		// winners with the most dev logs
		Iterator<Entry<String, List<DevLogPost>>> iter = sortedAuthors.iterator();
		int i = 0;
		while(iter.hasNext()) {
			Entry<String, List<DevLogPost>> author = iter.next();
			if(i < 2) {
				iter.remove();
			}
			if(author.getValue().equals("Lixus")) {
				iter.remove();
			}
			i++;
		}
		
		// shuffle, then remove 20 steam key winners
		for(i = 0; i < 10; i++) {
			Collections.shuffle(sortedAuthors);
		}
		iter = sortedAuthors.iterator();
		i = 0;
		while(iter.hasNext()) {
			Entry<String, List<DevLogPost>> author = iter.next();
			if(i < 20) {
				iter.remove();
			} else {
				break;
			}
			System.out.println("Steam key winner: " + author.getKey() + ", " + author.getValue().get(0).url);
			i++;
		}
		
		// shuffle once again, remove 5 t-shirt winners
		for(i = 0; i < 10; i++) {
			Collections.shuffle(sortedAuthors);
		}
		iter = sortedAuthors.iterator();
		i = 0;
		while(iter.hasNext()) {
			Entry<String, List<DevLogPost>> author = iter.next();
			if(i < 5) {
				iter.remove();
			} else {
				break;
			}
			System.out.println("T-shirt winner: " + author.getKey() + ", " + author.getValue().get(0).url);
			i++;
		}
	}
}
