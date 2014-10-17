package com.hawkprime.syndicate.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hawkprime.syndicate.model.Post;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/testContext.xml")
public class PostDaoTest {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PostDao postDao;

	@Autowired
	private FeedDao feedDao;

	@Test
	public void shouldHaveAnEntityManager() {
		assertNotNull(entityManager);
	}
	
	@Test
	public void readPost() {
		Post post = postDao.findById(1L);
		assertEquals("My First Post", post.getTitle());
		assertEquals("http://myfeed.com/my_first_post", post.getLink());
		assertEquals("This is my first post!", post.getText());
		assertEquals("2048a082fd924a742f9d92b83c24092af8309a72", post.getGuid());
		assertEquals(new LocalDateTime(2014, 10, 17, 14, 39, 0), post.getPublished());
	}

	@Test
	@Transactional
	public void createPost() {
		String title = "Another Post";
		String link = "http://myfeed.com/another_post";
		String guid = "4ac8a0829d924a748f9d92b83c34032af8309a72";
		String text = "My next Post";
		LocalDateTime published = new LocalDateTime();
		
		Post post = new Post();
		post.setTitle(title);
		post.setLink(link);
		post.setGuid(guid);
		post.setText(text);
		post.setPublished(published);
		
		postDao.create(post);

		Long id = post.getId();
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
	public void postExists() {
		assertFalse(postDao.isPosExistsByGuid("abc123"));

		Post p = new Post();
		p.setPublished(new LocalDateTime());
		p.setLink("http://myfeed.com/my_post");
		p.setTitle("My Post");
		p.setText("Cool Post");
		p.setGuid("abc123");
		p.setFeed(feedDao.findById(1L));
		postDao.create(p);

		assertTrue(postDao.isPosExistsByGuid("abc123"));
	}
}
