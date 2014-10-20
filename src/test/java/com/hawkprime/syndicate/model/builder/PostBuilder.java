package com.hawkprime.syndicate.model.builder;

import org.joda.time.LocalDateTime;

import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.Post;
import com.hawkprime.syndicate.util.Sha1;

public class PostBuilder {
	private String title = "Post";
	private String link = "http://myfeed.com/post";
	private String text = "My Post";
	private LocalDateTime published = new LocalDateTime();
	private Feed feed = null;
	private String guid = published.toString();

	public Post build() {
		Post post = new Post();
		post.setTitle(title);
		post.setLink(link);
		post.setGuid(Sha1.digest(guid));
		post.setText(text);
		post.setPublished(published);
		post.setFeed(feed);
		return post;
	}
	
	public PostBuilder withTitle(String title) {
		this.title = title;
		return this;
	}
	
	public PostBuilder withLink(String link) {
		this.link = link;
		return this;
	}
	
	public PostBuilder withGuid(String guid) {
		this.guid = guid;
		return this;
	}
	
	public PostBuilder withText(String text) {
		this.text = text;
		return this;
	}
	
	public PostBuilder withPublished(LocalDateTime published) {
		this.published = published;
		return this;
	}
	
	public PostBuilder withFeed(Feed feed) {
		this.feed = feed;
		return this;
	}
}
