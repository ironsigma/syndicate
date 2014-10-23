package com.hawkprime.syndicate.adapter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.Post;
import com.hawkprime.syndicate.model.builder.FeedBuilder;
import com.hawkprime.syndicate.model.builder.SyndEntryBuilder;
import com.hawkprime.syndicate.util.Sha1;
import com.sun.syndication.feed.synd.SyndEntry;

/**
 * Syndicate Entry to Post Adapter Tests.
 */
public class SyndEntryToPostAdapterTest {

	@Test(expected=SyndEntryToPostAdapterException.class)
	public void emptyTitleTest() throws SyndEntryToPostAdapterException {
		final Feed feed = new FeedBuilder().build();

		final SyndEntry entry = new SyndEntryBuilder()
				.withTitle(null)
				.build();

		SyndEntryToPostAdapter.convert(entry, feed);
	}

	@Test(expected=SyndEntryToPostAdapterException.class)
	public void blankTitleTest() throws SyndEntryToPostAdapterException {
		final Feed feed = new FeedBuilder().build();

		final SyndEntry entry = new SyndEntryBuilder()
				.withTitle("      ")
				.build();

		SyndEntryToPostAdapter.convert(entry, feed);
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
	public void emptyContent() throws SyndEntryToPostAdapterException {
		final Feed feed = new FeedBuilder().build();
		final SyndEntry entry = new SyndEntryBuilder()
				.withText(null)
				.build();

		final Post post = SyndEntryToPostAdapter.convert(entry, feed);
		assertThat(post.getText(), is(nullValue()));
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
