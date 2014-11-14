package com.hawkprime.syndicate.dao;

import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.Post;
import com.hawkprime.syndicate.model.State;
import com.hawkprime.syndicate.model.builder.PostBuilder;
import com.hawkprime.syndicate.model.builder.StateBuilder;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Post DAO Tests.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
public class PostDaoTest extends AbstractDaoTest {
	private static final String FEED_1_GUID = "2048a082fd924a742f9d92b83c24092af8309a72";

	@Autowired
	private PostDao postDao;

	@Autowired
	private FeedDao feedDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private StateDao stateDao;

	/**
	 * Find all entities in table.
	 */
	@Test
	public void findAllTest() {
		List<Post> allPosts = postDao.findAll();
		assertThat(allPosts.size(), is(1));
	}

	/**
	 * Read entities.
	 */
	@Test
	public void readPostTest() {
		Post post = postDao.findById(1L);
		assertThat(post.getTitle(), is("My First Post"));
		assertThat(post.getLink(), is("http://myfeed.com/my_first_post"));
		assertThat(post.getText(), is("This is my first post!"));
		assertThat(post.getGuid(), is(FEED_1_GUID));
		assertThat(post.getPublished(), is(LocalDateTime.parse("2014-10-17T14:39:00")));
		assertThat(post.getFetched(), is(LocalDateTime.parse("2014-10-29T14:08:00")));
		assertThat(post.getFeed().getId(), is(1L));
	}

	/**
	 * Create entities.
	 */
	@Test
	@Transactional
	public void createPostTest() {
		final String title = "Another Post";
		final String link = "http://myfeed.com/another_post";
		final String text = "My next Post";

		Post post = new PostBuilder()
				.withTitle(title)
				.withLink(link)
				.withText(text)
				.withFeed(feedDao.findById(1L))
				.build();

		final String guid = post.getGuid();
		final LocalDateTime published = post.getPublished();
		final LocalDateTime fetched = post.getFetched();

		postDao.create(post);

		final Long id = post.getId();
		post = null;

		post = postDao.findById(id);

		assertThat(post.getTitle(), is(title));
		assertThat(post.getLink(), is(link));
		assertThat(post.getGuid(), is(guid));
		assertThat(post.getText(), is(text));
		assertThat(post.getPublished(), is(published));
		assertThat(post.getFetched(), is(fetched));
	}

	/**
	 * Update entities.
	 */
	@Test
	@Transactional
	public void updatePostTest() {
		Post post = new PostBuilder()
				.withFeed(feedDao.findById(1L))
				.build();

		// persist
		postDao.create(post);

		// clear out
		final Long id = post.getId();
		post = null;

		// fetch back
		post = postDao.findById(id);

		// change values
		final String title = "Awsome Post";
		final String link = "http://myfeed.com/awsome_post";
		final String guid = "1a0fa0829d924a741f9d92383c34032af8303a72";
		final String text = "Everything is awsome!";
		final LocalDateTime published = LocalDateTime.now();
		final LocalDateTime fetched = LocalDateTime.now();

		// update post
		post.setTitle(title);
		post.setLink(link);
		post.setGuid(guid);
		post.setText(text);
		post.setPublished(published);
		post.setFetched(fetched);

		// persist
		postDao.update(post);

		// clear out
		post = null;

		// fetch back
		post = postDao.findById(id);

		// test
		assertThat(post.getTitle(), is(title));
		assertThat(post.getLink(), is(link));
		assertThat(post.getGuid(), is(guid));
		assertThat(post.getText(), is(text));
		assertThat(post.getPublished(), is(published));
		assertThat(post.getFetched(), is(fetched));
	}

	/**
	 * Delete entities.
	 */
	@Test
	@Transactional
	public void deletePostTest() {
		Post post = new PostBuilder()
			.withFeed(feedDao.findById(1L))
			.build();

		postDao.create(post);

		final Long id = post.getId();
		post = null;

		postDao.deleteById(id);

		post = postDao.findById(id);
		assertThat(post, is(nullValue()));
	}

	/**
	 * Create a post.
	 * @param feed Feed
	 * @param fetched fetched date
	 * @param published published date
	 * @param withState true to create state object
	 * @param read true to mark read
	 * @param stared true to mark starred
	 * @return the post
	 */
	private Post createPost(final Feed feed, final int fetched, final int published,
			final boolean withState, final Boolean read, final Boolean stared) {
		Post post = new PostBuilder()
			.withFeed(feed)
			.withFetched(LocalDateTime.now().minusDays(fetched))
			.withPublished(LocalDateTime.now().minusDays(published))
			.build();

		postDao.create(post);
		assertThat(post.getId(), is(not(nullValue())));

		if (withState) {
			State state = new StateBuilder()
					.withPost(post)
					.withRead(read)
					.withStared(stared)
					.withUser(userDao.findById(1L))
					.build();
			stateDao.create(state);
			assertThat(state.getId(), is(not(nullValue())));
		}
		return post;
	}

	/**
	 * Delete old unread posts.
	 */
	@Test
	@Transactional
	public void deleteOldUnreadPostsTest() {
		final int recent = 29;
		final int published = 90;
		final int fetched = 30;

		final Feed feed = feedDao.findById(1L);
		final Feed otherFeed = feedDao.findById(2L);

		final long postNoStateId = createPost(feed, fetched, published, false, null, null).getId();
		final long postWithStateUnreadId = createPost(feed, fetched, published, true, false, false).getId();
		final long postWithStateUnreadStaredId = createPost(feed, fetched, published, true, false, true).getId();
		final long postWithStateReadStaredId = createPost(feed, fetched, published, true, true, true).getId();
		final long postWithStateReadId = createPost(feed, fetched, published, true, true, false).getId();
		final long postRecentNoStateId = createPost(feed, recent, published, false, null, null).getId();
		final long postRecentWithStateUnreadId = createPost(feed, recent, published, true, false, false).getId();
		final long postRecentWithStateUnreadStaredId = createPost(feed, recent, published, true, false, true).getId();
		final long postRecentWithStateReadStaredId = createPost(feed, recent, published, true, true, true).getId();
		final long postRecentWithStateReadId = createPost(feed, recent, published, true, true, false).getId();
		final long postOtherNoStateId = createPost(otherFeed, fetched, published, false, null, null).getId();
		final long postOtherWithStateUnreadId = createPost(otherFeed, fetched, published, true, false, false).getId();

		postDao.deleteUnreadNotStaredByFeedIdOlderThan(feed.getId(), fetched);
		postDao.getEntityManager().clear();
		postDao.getEntityManager().flush();

		assertThat(postDao.findById(postNoStateId), is(nullValue()));
		assertThat(postDao.findById(postWithStateUnreadId), is(nullValue()));
		assertThat(postDao.findById(postOtherNoStateId), is(not(nullValue())));
		assertThat(postDao.findById(postOtherWithStateUnreadId), is(not(nullValue())));
		assertThat(postDao.findById(postWithStateUnreadStaredId), is(not(nullValue())));
		assertThat(postDao.findById(postWithStateReadStaredId), is(not(nullValue())));
		assertThat(postDao.findById(postWithStateReadId), is(not(nullValue())));
		assertThat(postDao.findById(postRecentNoStateId), is(not(nullValue())));
		assertThat(postDao.findById(postRecentWithStateUnreadId), is(not(nullValue())));
		assertThat(postDao.findById(postRecentWithStateUnreadStaredId), is(not(nullValue())));
		assertThat(postDao.findById(postRecentWithStateReadStaredId), is(not(nullValue())));
		assertThat(postDao.findById(postRecentWithStateReadId), is(not(nullValue())));
	}

	/**
	 * Delete old read posts.
	 */
	@Test
	@Transactional
	public void deleteOldReadPostsTest() {
		final int recent = 44;
		final int published = 90;
		final int fetched = 45;

		final Feed feed = feedDao.findById(1L);
		final Feed otherFeed = feedDao.findById(2L);

		final long postNoStateId = createPost(feed, fetched, published, false, null, null).getId();
		final long postWithStateUnreadId = createPost(feed, fetched, published, true, false, false).getId();
		final long postWithStateUnreadStaredId = createPost(feed, fetched, published, true, false, true).getId();
		final long postWithStateReadStaredId = createPost(feed, fetched, published, true, true, true).getId();
		final long postWithStateReadId = createPost(feed, fetched, published, true, true, false).getId();
		final long postRecentNoStateId = createPost(feed, recent, published, false, null, null).getId();
		final long postRecentWithStateUnreadId = createPost(feed, recent, published, true, false, false).getId();
		final long postRecentWithStateUnreadStaredId = createPost(feed, recent, published, true, false, true).getId();
		final long postRecentWithStateReadStaredId = createPost(feed, recent, published, true, true, true).getId();
		final long postRecentWithStateReadId = createPost(feed, recent, published, true, true, false).getId();
		final long postOtherWithStateReadId = createPost(otherFeed, fetched, published, true, true, false).getId();

		postDao.deleteReadNotStaredByFeedIdOlderThan(feed.getId(), fetched);
		postDao.getEntityManager().clear();
		postDao.getEntityManager().flush();

		assertThat(postDao.findById(postWithStateReadId), is(nullValue()));
		assertThat(postDao.findById(postOtherWithStateReadId), is(not(nullValue())));
		assertThat(postDao.findById(postNoStateId), is(not(nullValue())));
		assertThat(postDao.findById(postWithStateUnreadId), is(not(nullValue())));
		assertThat(postDao.findById(postWithStateUnreadStaredId), is(not(nullValue())));
		assertThat(postDao.findById(postWithStateReadStaredId), is(not(nullValue())));
		assertThat(postDao.findById(postRecentNoStateId), is(not(nullValue())));
		assertThat(postDao.findById(postRecentWithStateUnreadId), is(not(nullValue())));
		assertThat(postDao.findById(postRecentWithStateUnreadStaredId), is(not(nullValue())));
		assertThat(postDao.findById(postRecentWithStateReadStaredId), is(not(nullValue())));
		assertThat(postDao.findById(postRecentWithStateReadId), is(not(nullValue())));
	}

	/**
	 * Delete old published posts.
	 */
	@Test
	@Transactional
	public void deleteOldPublishedPostsTest() {
		final int recent = 89;
		final int published = 90;
		final int fetched = 60;

		final Feed otherFeed = feedDao.findById(2L);
		final Feed feed = feedDao.findById(1L);

		final long postNoStateId = createPost(feed, fetched, published, false, null, null).getId();
		final long postWithStateUnreadId = createPost(feed, fetched, published, true, false, false).getId();
		final long postWithStateUnreadStaredId = createPost(feed, fetched, published, true, false, true).getId();
		final long postWithStateReadStaredId = createPost(feed, fetched, published, true, true, true).getId();
		final long postWithStateReadId = createPost(feed, fetched, published, true, true, false).getId();
		final long postRecentNoStateId = createPost(feed, fetched, recent, false, null, null).getId();
		final long postRecentWithStateUnreadId = createPost(feed, fetched, recent, true, false, false).getId();
		final long postRecentWithStateUnreadStaredId = createPost(feed, fetched, recent, true, false, true).getId();
		final long postRecentWithStateReadStaredId = createPost(feed, fetched, recent, true, true, true).getId();
		final long postRecentWithStateReadId = createPost(feed, fetched, recent, true, true, false).getId();
		final long postOtherNoStateId = createPost(otherFeed, fetched, published, false, null, null).getId();
		final long postOtherWithStateUnreadId = createPost(otherFeed, fetched, published, true, false, false).getId();
		final long postOtherWithStateReadId = createPost(otherFeed, fetched, published, true, true, false).getId();

		postDao.deletePublishedNotStaredByFeedIdOlderThan(feed.getId(), published);
		postDao.getEntityManager().clear();
		postDao.getEntityManager().flush();

		assertThat(postDao.findById(postNoStateId), is(nullValue()));
		assertThat(postDao.findById(postWithStateUnreadId), is(nullValue()));
		assertThat(postDao.findById(postWithStateReadId), is(nullValue()));
		assertThat(postDao.findById(postOtherNoStateId), is(not(nullValue())));
		assertThat(postDao.findById(postOtherWithStateUnreadId), is(not(nullValue())));
		assertThat(postDao.findById(postOtherWithStateReadId), is(not(nullValue())));
		assertThat(postDao.findById(postWithStateUnreadStaredId), is(not(nullValue())));
		assertThat(postDao.findById(postWithStateReadStaredId), is(not(nullValue())));
		assertThat(postDao.findById(postRecentNoStateId), is(not(nullValue())));
		assertThat(postDao.findById(postRecentWithStateUnreadId), is(not(nullValue())));
		assertThat(postDao.findById(postRecentWithStateUnreadStaredId), is(not(nullValue())));
		assertThat(postDao.findById(postRecentWithStateReadStaredId), is(not(nullValue())));
		assertThat(postDao.findById(postRecentWithStateReadId), is(not(nullValue())));
	}

	/**
	 * Test if a post exists.
	 */
	@Test
	@Transactional
	public void postExistsTest() {
		assertThat(postDao.doesPosExistsWithGuid(FEED_1_GUID), is(true));
		assertThat(postDao.doesPosExistsWithGuid("abc123"), is(false));
	}
}
