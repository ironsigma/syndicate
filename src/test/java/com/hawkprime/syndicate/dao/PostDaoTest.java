package com.hawkprime.syndicate.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

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
	public void findAllTest() {
		final List<Post> allPosts = postDao.findAll();
		assertThat(allPosts.size(), is(1));
	}

	@Test
	public void readPostTest() {
		final Post post = postDao.findById(1L);
		assertThat(post.getTitle(), is("My First Post"));
		assertThat(post.getLink(), is("http://myfeed.com/my_first_post"));
		assertThat(post.getText(), is("This is my first post!"));
		assertThat(post.getGuid(), is("2048a082fd924a742f9d92b83c24092af8309a72"));
		assertThat(post.getPublished(), is(LocalDateTime.parse("2014-10-17T14:39:00")));
	}

	@Test
	@Transactional
	public void createPostTest() {
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

		assertThat(post.getTitle(), is(title));
		assertThat(post.getLink(), is(link));
		assertThat(post.getGuid(), is(guid));
		assertThat(post.getText(), is(text));
		assertThat(post.getPublished(), is(published));
	}

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
		assertThat(post.getTitle(), is(title));
		assertThat(post.getLink(), is(link));
		assertThat(post.getGuid(), is(guid));
		assertThat(post.getText(), is(text));
		assertThat(post.getPublished(), is(published));
	}

	@Test
	@Transactional
	public void deletePostTest() {
		Post post = new PostBuilder()
			.withFeed(feedDao.findById(1L))
			.build();

		postDao.create(post);

		final Long id = post.getId();
		post = null;

		postDao.delete(id);

		post = postDao.findById(id);
		assertThat(post, is(nullValue()));
	}

	@Test
	@Transactional
	public void postExistsTest() {
		assertThat(postDao.doesPosExistsWithGuid("2048a082fd924a742f9d92b83c24092af8309a72"), is(true));
		assertThat(postDao.doesPosExistsWithGuid("abc123"), is(false));
	}
}
