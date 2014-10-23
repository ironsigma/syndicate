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

	public SyndEntryBuilder withTitle(final String title) {
		this.title = title;
		return this;
	}

	public SyndEntryBuilder withLink(final String link) {
		this.link = link;
		return this;
	}

	public SyndEntryBuilder withUri(final String uri) {
		this.uri = uri;
		return this;
	}

	public SyndEntryBuilder withText(final String text) {
		this.text = text;
		return this;
	}

	public SyndEntryBuilder withPublished(final Date published) {
		this.published = published;
		return this;
	}
}
