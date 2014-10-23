package com.hawkprime.syndicate.model.builder;

import org.joda.time.LocalDateTime;

import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.Post;
import com.hawkprime.syndicate.util.Sha1;

/**
 * Post Builder.
 */
public class PostBuilder {
	private Long id;
	private String title = "Post";
	private String link = "http://myfeed.com/post";
	private String text = "My Post";
	private LocalDateTime published = new LocalDateTime();
	private Feed feed;
	private String guid = published.toString();

	public Post build() {
		final Post post = new Post();
		post.setId(id);
		post.setTitle(title);
		post.setLink(link);
		post.setGuid(Sha1.digest(guid));
		post.setText(text);
		post.setPublished(published);
		post.setFeed(feed);
		return post;
	}

	public PostBuilder withId(final Long id) {
		this.id = id;
		return this;
	}

	public PostBuilder withTitle(final String title) {
		this.title = title;
		return this;
	}

	public PostBuilder withLink(final String link) {
		this.link = link;
		return this;
	}

	public PostBuilder withGuid(final String guid) {
		this.guid = guid;
		return this;
	}

	public PostBuilder withText(final String text) {
		this.text = text;
		return this;
	}

	public PostBuilder withPublished(final LocalDateTime published) {
		this.published = published;
		return this;
	}

	public PostBuilder withFeed(final Feed feed) {
		this.feed = feed;
		return this;
	}
}
