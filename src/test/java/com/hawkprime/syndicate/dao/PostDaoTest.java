package com.hawkprime.syndicate.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hawkprime.syndicate.model.Feed;
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
	@Transactional
	public void postExists() {
		assertFalse(postDao.isPosExistsByGuid("abc123"));

		Feed f = new Feed();
		f.setName("MyFeed");
		f.setUrl("http://myfeed.com/rss");
		feedDao.create(f);

		Post p = new Post();
		p.setPublished(new Date());
		p.setLink("http://myfeed.com/my_post");
		p.setTitle("My Post");
		p.setText("Cool Post");
		p.setGuid("abc123");
		p.setFeed(f);
		postDao.create(p);

		assertTrue(postDao.isPosExistsByGuid("abc123"));
	}
}
