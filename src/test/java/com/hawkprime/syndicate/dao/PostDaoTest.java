package com.hawkprime.syndicate.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hawkprime.syndicate.model.Post;
import com.hawkprime.syndicate.model.builder.PostBuilder;

/**
 * Post DAO Tests.
 */
public class PostDaoTest extends AbstractDaoTest {
	@Autowired
	private PostDao postDao;

	@Autowired
	private FeedDao feedDao;

	@Test
	public void readPost() {
		final Post post = postDao.findById(1L);
		assertEquals("My First Post", post.getTitle());
		assertEquals("http://myfeed.com/my_first_post", post.getLink());
		assertEquals("This is my first post!", post.getText());
		assertEquals("2048a082fd924a742f9d92b83c24092af8309a72", post.getGuid());
		assertEquals(LocalDateTime.parse("2014-10-17T14:39:00"), post.getPublished());
	}

	@Test
	@Transactional
	public void createPost() {
		final String title = "Another Post";
		final String link = "http://myfeed.com/another_post";
		final String text = "My next Post";

		Post post = new PostBuilder()
				.withTitle("Another Post")
				.withLink(link)
				.withText(text)
				.withFeed(feedDao.findById(1L))
				.build();

		final String guid = post.getGuid();
		final LocalDateTime published = post.getPublished();

		postDao.create(post);

		final Long id = post.getId();
		post = null;

		post = postDao.findById(id);

		assertEquals(title, post.getTitle());
		assertEquals(link, post.getLink());
		assertEquals(guid, post.getGuid());
		assertEquals(text, post.getText());
		assertEquals(published, post.getPublished());
	}

	@Test
	@Transactional
	public void updatePost() {
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
		final LocalDateTime published = new LocalDateTime();

		// update post
		post.setTitle(title);
		post.setLink(link);
		post.setGuid(guid);
		post.setText(text);
		post.setPublished(published);

		// persist
		postDao.update(post);

		// clear out
		post = null;

		// fetch back
		post = postDao.findById(id);

		// test
		assertEquals(title, post.getTitle());
		assertEquals(link, post.getLink());
		assertEquals(guid, post.getGuid());
		assertEquals(text, post.getText());
		assertEquals(published, post.getPublished());
	}

	@Test
	@Transactional
	public void deletePost() {
		Post post = new PostBuilder()
			.withFeed(feedDao.findById(1L))
			.build();

		postDao.create(post);

		final Long id = post.getId();
		post = null;

		postDao.delete(id);

		post = postDao.findById(id);
		assertNull(post);
	}

	@Test
	@Transactional
	public void postExists() {
		assertTrue(postDao.doesPosExistsWithGuid("2048a082fd924a742f9d92b83c24092af8309a72"));
		assertFalse(postDao.doesPosExistsWithGuid("abc123"));
	}
}
