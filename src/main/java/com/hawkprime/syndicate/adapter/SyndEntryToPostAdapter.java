package com.hawkprime.syndicate.adapter;

import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.Post;
import com.hawkprime.syndicate.util.Sha1;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;

import org.joda.time.LocalDateTime;

/**
 * Convert Syndicate Entry to Post.
 */
public final class SyndEntryToPostAdapter {

	/**
	 * No instances.
	 */
	private SyndEntryToPostAdapter() { }

	/**
	 * Convert Syndicate Entry to Post.
	 * @param entry Syndicate entry.
	 * @param feed Parent feed.
	 * @return The post object.
	 * @throws SyndEntryToPostAdapterException conversion errors
	 */
	public static Post convert(final SyndEntry entry, final Feed feed) throws SyndEntryToPostAdapterException {
		if (entry.getTitle() == null || entry.getTitle().trim().isEmpty()) {
			throw new SyndEntryToPostAdapterException("Title is required");
		}
		final SyndContent content = entry.getDescription();
		final Post post = new Post();
		post.setFeed(feed);
		post.setTitle(entry.getTitle());
		post.setGuid(generateGuid(entry));
		post.setLink(entry.getLink());
		post.setPublished(new LocalDateTime(entry.getPublishedDate()));
		post.setFetched(LocalDateTime.now());
		if (content != null) {
			post.setText(content.getValue());
		}
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
			final StringBuilder sb = new StringBuilder(entry.getTitle());
			if (entry.getPublishedDate() != null) {
				sb.append(entry.getPublishedDate().getTime());
			}
			guid = sb.toString();
		}
		return Sha1.digest(guid);
	}

}
