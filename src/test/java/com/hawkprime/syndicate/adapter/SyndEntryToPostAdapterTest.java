package com.hawkprime.syndicate.adapter;

import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.Post;
import com.hawkprime.syndicate.model.builder.FeedBuilder;
import com.hawkprime.syndicate.model.builder.SyndEntryBuilder;
import com.hawkprime.syndicate.util.Sha1;

import com.sun.syndication.feed.synd.SyndEntry;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Syndicate Entry to Post Adapter Tests.
 */
public class SyndEntryToPostAdapterTest {

	/**
	 * Empty title test.
	 *
	 * @throws SyndEntryToPostAdapterException the synd entry to post adapter exception
	 */
	@Test(expected=SyndEntryToPostAdapterException.class)
	public void emptyTitleTest() throws SyndEntryToPostAdapterException {
		Feed feed = new FeedBuilder().build();

		SyndEntry entry = new SyndEntryBuilder()
				.withTitle(null)
				.build();

		SyndEntryToPostAdapter.convert(entry, feed);
	}

	/**
	 * Blank title test.
	 *
	 * @throws SyndEntryToPostAdapterException the synd entry to post adapter exception
	 */
	@Test(expected=SyndEntryToPostAdapterException.class)
	public void blankTitleTest() throws SyndEntryToPostAdapterException {
		Feed feed = new FeedBuilder().build();

		SyndEntry entry = new SyndEntryBuilder()
				.withTitle("      ")
				.build();

		SyndEntryToPostAdapter.convert(entry, feed);
	}

	/**
	 * Convert test.
	 *
	 * @throws SyndEntryToPostAdapterException the synd entry to post adapter exception
	 */
	@Test
	public void convertTest() throws SyndEntryToPostAdapterException {
		Feed feed = new FeedBuilder().build();
		SyndEntry entry = new SyndEntryBuilder().build();

		Post post = SyndEntryToPostAdapter.convert(entry, feed);
		assertThat(post.getTitle(), is(entry.getTitle()));
		assertThat(post.getFeed(), is(feed));
		assertThat(post.getGuid(), is(Sha1.digest(entry.getUri())));
		assertThat(post.getLink(), is(entry.getLink()));
		assertThat(post.getPublished(), is(new LocalDateTime(entry.getPublishedDate())));
		assertThat(post.getText(), is(entry.getDescription().getValue()));
		assertThat(post.getFetched(), is(not(nullValue())));
	}

	/**
	 * Empty content test.
	 *
	 * @throws SyndEntryToPostAdapterException the synd entry to post adapter exception
	 */
	@Test
	public void emptyContentTest() throws SyndEntryToPostAdapterException {
		Feed feed = new FeedBuilder().build();
		SyndEntry entry = new SyndEntryBuilder()
				.withText(null)
				.build();

		Post post = SyndEntryToPostAdapter.convert(entry, feed);
		assertThat(post.getText(), is(nullValue()));
	}

	/**
	 * Empty GUID test.
	 *
	 * @throws SyndEntryToPostAdapterException the synd entry to post adapter exception
	 */
	@Test
	public void emptyGuidTest() throws SyndEntryToPostAdapterException {
		Feed feed = new FeedBuilder().build();
		SyndEntry entry = new SyndEntryBuilder()
				.withUri("     ")
				.withPublished(null)
				.build();

		Post post = SyndEntryToPostAdapter.convert(entry, feed);
		assertThat(post.getGuid(), is(Sha1.digest(entry.getTitle())));
	}

	/**
	 * Title GUID test.
	 *
	 * @throws SyndEntryToPostAdapterException the synd entry to post adapter exception
	 */
	@Test
	public void titleGuidTest() throws SyndEntryToPostAdapterException {
		Feed feed = new FeedBuilder().build();
		SyndEntry entry = new SyndEntryBuilder()
				.withUri(null)
				.withPublished(null)
				.build();

		Post post = SyndEntryToPostAdapter.convert(entry, feed);
		assertThat(post.getGuid(), is(Sha1.digest(entry.getTitle())));
	}

	/**
	 * Title and published GUID test.
	 *
	 * @throws SyndEntryToPostAdapterException the synd entry to post adapter exception
	 */
	@Test
	public void titleAndPublishedGuidTest() throws SyndEntryToPostAdapterException {
		Feed feed = new FeedBuilder().build();
		SyndEntry entry = new SyndEntryBuilder()
				.withUri(null)
				.build();

		Post post = SyndEntryToPostAdapter.convert(entry, feed);
		assertThat(post.getGuid(), is(Sha1.digest(entry.getTitle() + entry.getPublishedDate().getTime())));
	}
}
