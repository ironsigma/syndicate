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

	@Test
	public void testSave() {
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

	@Test
	public void testSaveExisting() {
		final Post post = new PostBuilder().build();
		final PostDao postDao = mock(PostDao.class);
		postService.setPostDao(postDao);

		when(postDao.doesPosExistsWithGuid(post.getGuid()))
				.thenReturn(true);

		assertThat(postService.save(post), is(0));
	}
}
