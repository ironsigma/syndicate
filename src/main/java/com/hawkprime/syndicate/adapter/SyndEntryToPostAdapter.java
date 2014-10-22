package com.hawkprime.syndicate.adapter;

import org.joda.time.LocalDateTime;

import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.Post;
import com.hawkprime.syndicate.util.Sha1;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;

/**
 * Convert Syndicate Entry to Post.
 */
public final class SyndEntryToPostAdapter {

	private SyndEntryToPostAdapter() { }

	/**
	 * Convert Syndicate Entry to Post.
	 * @param entry Syndicate entry.
	 * @param feed Parent feed.
	 * @return The post object.
	 */
	public static Post convert(final SyndEntry entry, final Feed feed) {
		final SyndContent content = entry.getDescription();
		final Post post = new Post();
		post.setId(null);
		post.setFeed(feed);
		post.setTitle(entry.getTitle());
		post.setGuid(generateGuid(entry));
		post.setLink(entry.getLink());
		post.setPublished(new LocalDateTime(entry.getPublishedDate()));
		post.setText(content.getValue());
		return post;
	}

	/**
	 * Generate entry GUID.
	 * @param entry Entry
	 * @return 40 Character SHA1 digest GUID
	 */
	private static String generateGuid(final SyndEntry entry) {
		String guid = entry.getUri();
		if (guid == null || guid.trim().isEmpty()) {
			guid = entry.getTitle() + entry.getPublishedDate().getTime();
		}
		return Sha1.digest(guid);
	}

}
