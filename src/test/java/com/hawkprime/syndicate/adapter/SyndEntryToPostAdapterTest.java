package com.hawkprime.syndicate.adapter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.Post;
import com.hawkprime.syndicate.model.builder.FeedBuilder;
import com.hawkprime.syndicate.model.builder.SyndEntryBuilder;
import com.hawkprime.syndicate.util.Sha1;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

/**
 * Syndicate Entry to Post Adapter Tests.
 */
public class SyndEntryToPostAdapterTest {

	@Test
	public void emptyTitleTest() {
		final Feed feed = new FeedBuilder().build();
		final SyndEntry entry = new SyndEntryImpl();
		Post post = null;
		try {
			post = SyndEntryToPostAdapter.convert(entry, feed);
			fail("SyndEntryToPostAdapterException expected");
		} catch (SyndEntryToPostAdapterException ex) {
			assertThat(post, is(nullValue()));
		}

	}

	@Test
	public void convertTest() throws SyndEntryToPostAdapterException {
		final Feed feed = new FeedBuilder().build();
		final SyndEntry entry = new SyndEntryBuilder().build();

		final Post post = SyndEntryToPostAdapter.convert(entry, feed);
		assertThat(post.getTitle(), is(entry.getTitle()));
		assertThat(post.getFeed(), is(feed));
		assertThat(post.getGuid(), is(Sha1.digest(entry.getUri())));
		assertThat(post.getLink(), is(entry.getLink()));
		assertThat(post.getPublished(), is(new LocalDateTime(entry.getPublishedDate())));
		assertThat(post.getText(), is(entry.getDescription().getValue()));
	}

	@Test
	public void emptyGuid() throws SyndEntryToPostAdapterException {
		final Feed feed = new FeedBuilder().build();
		final SyndEntry entry = new SyndEntryBuilder()
				.withUri("     ")
				.withPublished(null)
				.build();

		final Post post = SyndEntryToPostAdapter.convert(entry, feed);
		assertThat(post.getGuid(), is(Sha1.digest(entry.getTitle())));
	}

	@Test
	public void titleGuid() throws SyndEntryToPostAdapterException {
		final Feed feed = new FeedBuilder().build();
		final SyndEntry entry = new SyndEntryBuilder()
				.withUri(null)
				.withPublished(null)
				.build();

		final Post post = SyndEntryToPostAdapter.convert(entry, feed);
		assertThat(post.getGuid(), is(Sha1.digest(entry.getTitle())));
	}

	@Test
	public void titleAndPublishedGuid() throws SyndEntryToPostAdapterException {
		final Feed feed = new FeedBuilder().build();
		final SyndEntry entry = new SyndEntryBuilder()
				.withUri(null)
				.build();

		final Post post = SyndEntryToPostAdapter.convert(entry, feed);
		assertThat(post.getGuid(), is(Sha1.digest(entry.getTitle() + entry.getPublishedDate().getTime())));
	}
}
