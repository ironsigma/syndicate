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
	private LocalDateTime published = LocalDateTime.now();
	private LocalDateTime fetched = LocalDateTime.now();
	private Feed feed;
	private String guid = published.toString();

	/**
	 * Builds the post.
	 *
	 * @return the post
	 */
	public Post build() {
		final Post post = new Post();
		post.setId(id);
		post.setTitle(title);
		post.setLink(link);
		post.setGuid(Sha1.digest(guid));
		post.setText(text);
		post.setPublished(published);
		post.setFetched(fetched);
		post.setFeed(feed);
		return post;
	}

	/**
	 * With id.
	 *
	 * @param id the id
	 * @return the post builder
	 */
	public PostBuilder withId(final Long id) {
		this.id = id;
		return this;
	}

	/**
	 * With title.
	 *
	 * @param title the title
	 * @return the post builder
	 */
	public PostBuilder withTitle(final String title) {
		this.title = title;
		return this;
	}

	/**
	 * With link.
	 *
	 * @param link the link
	 * @return the post builder
	 */
	public PostBuilder withLink(final String link) {
		this.link = link;
		return this;
	}

	/**
	 * With guid.
	 *
	 * @param guid the guid
	 * @return the post builder
	 */
	public PostBuilder withGuid(final String guid) {
		this.guid = guid;
		return this;
	}

	/**
	 * With text.
	 *
	 * @param text the text
	 * @return the post builder
	 */
	public PostBuilder withText(final String text) {
		this.text = text;
		return this;
	}

	/**
	 * With published.
	 *
	 * @param published the published
	 * @return the post builder
	 */
	public PostBuilder withPublished(final LocalDateTime published) {
		this.published = published;
		return this;
	}

	/**
	 * With fetched.
	 *
	 * @param fetched the fetched
	 * @return the post builder
	 */
	public PostBuilder withFetched(final LocalDateTime fetched) {
		this.fetched = fetched;
		return this;
	}

	/**
	 * With feed.
	 *
	 * @param feed the feed
	 * @return the post builder
	 */
	public PostBuilder withFeed(final Feed feed) {
		this.feed = feed;
		return this;
	}
}
