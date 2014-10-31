package com.hawkprime.syndicate.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.hawkprime.syndicate.dao.PostDao;
import com.hawkprime.syndicate.model.Post;
import com.hawkprime.syndicate.model.builder.PostBuilder;

/**
 * Post Service Tests.
 */
public class PostServiceTest {

	private final PostService postService = new PostService();

	/**
	 * Save test.
	 */
	@Test
	public void saveTest() {
		final Post post = new PostBuilder().build();
		final PostDao postDao = mock(PostDao.class);
		postService.setPostDao(postDao);

		when(postDao.doesPosExistsWithGuid(post.getGuid()))
				.thenReturn(false);

		when(postDao.create(post))
				.thenReturn(post);

		assertThat(postService.save(post), is(1));

		verify(postDao).create(post);
	}

	/**
	 * Save existing test.
	 */
	@Test
	public void saveExistingTest() {
		final Post post = new PostBuilder().build();
		final PostDao postDao = mock(PostDao.class);
		postService.setPostDao(postDao);

		when(postDao.doesPosExistsWithGuid(post.getGuid()))
				.thenReturn(true);

		assertThat(postService.save(post), is(0));
	}

	/**
	 * Delete published not stared by feed id older than test.
	 */
	@Test
	public void deletePublishedNotStaredByFeedIdOlderThanTest() {
		final PostDao postDao = mock(PostDao.class);
		postService.setPostDao(postDao);

		final long feedId = 1L;
		final int days = 30;
		postService.deletePublishedNotStaredByFeedIdOlderThan(feedId, days);

		verify(postDao).deletePublishedNotStaredByFeedIdOlderThan(feedId, days);
	}

	/**
	 * Delete read not stared by feed id older than test.
	 */
	@Test
	public void deleteReadNotStaredByFeedIdOlderThanTest() {
		final PostDao postDao = mock(PostDao.class);
		postService.setPostDao(postDao);

		final long feedId = 1L;
		final int days = 30;
		postService.deleteReadNotStaredByFeedIdOlderThan(feedId, days);

		verify(postDao).deleteReadNotStaredByFeedIdOlderThan(feedId, days);
	}

	/**
	 * Delete unread not stared by feed id older than test.
	 */
	@Test
	public void deleteUnreadNotStaredByFeedIdOlderThanTest() {
		final PostDao postDao = mock(PostDao.class);
		postService.setPostDao(postDao);

		final long feedId = 1L;
		final int days = 30;
		postService.deleteUnreadNotStaredByFeedIdOlderThan(feedId, days);

		verify(postDao).deleteUnreadNotStaredByFeedIdOlderThan(feedId, days);
	}
}
