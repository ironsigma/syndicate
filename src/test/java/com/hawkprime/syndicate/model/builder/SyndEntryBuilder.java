package com.hawkprime.syndicate.model.builder;

import java.util.Date;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

/**
 * Post Builder.
 */
public class SyndEntryBuilder {
	private String title = "Entry";
	private String link = "http://myfeed.com/post";
	private String text = "My entry";
	private Date published = new Date();
	private String uri = "http://myfeed.com/permlink/1023843";

	/**
	 * Builds the Syndicate Entry.
	 *
	 * @return the synd entry
	 */
	public SyndEntry build() {
		final SyndEntry entry = new SyndEntryImpl();
		entry.setTitle(title);
		entry.setUri(uri);
		entry.setLink(link);
		entry.setPublishedDate(published);

		if (text != null) {
			final SyndContent content = new SyndContentImpl();
			content.setType("text");
			content.setValue(text);
			entry.setDescription(content);
		}
		return entry;
	}

	/**
	 * With title.
	 *
	 * @param title the title
	 * @return the synd entry builder
	 */
	public SyndEntryBuilder withTitle(final String title) {
		this.title = title;
		return this;
	}

	/**
	 * With link.
	 *
	 * @param link the link
	 * @return the synd entry builder
	 */
	public SyndEntryBuilder withLink(final String link) {
		this.link = link;
		return this;
	}

	/**
	 * With uri.
	 *
	 * @param uri the uri
	 * @return the synd entry builder
	 */
	public SyndEntryBuilder withUri(final String uri) {
		this.uri = uri;
		return this;
	}

	/**
	 * With text.
	 *
	 * @param text the text
	 * @return the synd entry builder
	 */
	public SyndEntryBuilder withText(final String text) {
		this.text = text;
		return this;
	}

	/**
	 * With published.
	 *
	 * @param published the published
	 * @return the synd entry builder
	 */
	public SyndEntryBuilder withPublished(final Date published) {
		this.published = published;
		return this;
	}
}
