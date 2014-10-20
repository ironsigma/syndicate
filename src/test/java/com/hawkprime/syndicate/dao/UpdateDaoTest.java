package com.hawkprime.syndicate.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hawkprime.syndicate.model.Update;
import com.hawkprime.syndicate.model.builder.UpdateBuilder;

public class UpdateDaoTest extends BaseDaoTest {
	@Autowired
	private FeedDao feedDao;

	@Autowired
	private UpdateDao updateDao;

	@Test
	public void readUpdate() {
		Update update = updateDao.findById(1L);
		assertEquals(new Long(120), update.getTotalCount());
		assertEquals(new Long(13), update.getNewCount());
	}

	@Test
	@Transactional
	public void createUpdate() {
		Long totalCount = 530L;
		Long newCount = 330L;
		
		Update update = new UpdateBuilder()
				.withTotalCount(totalCount)
				.withNewCount(newCount)
				.withFeed(feedDao.findById(1L))
				.build();

		updateDao.create(update);

		Long id = update.getId();
		update = null;
		
		update = updateDao.findById(id);

		assertEquals(totalCount, update.getTotalCount());
		assertEquals(newCount, update.getNewCount());
	}
	
	@Test
	@Transactional
	public void updateUpdate() {
		Update update = new UpdateBuilder()
				.withFeed(feedDao.findById(1L))
				.build();

		// persist
		updateDao.create(update);

		// clear out
		Long id = update.getId();
		update = null;

		// fetch back
		update = updateDao.findById(id);

		// change values
		Long totalCount = 600L;
		Long newCount = 250L;

		// update Update
		update.setTotalCount(totalCount);
		update.setNewCount(newCount);

		// persist
		updateDao.update(update);

		// clear out
		update = null;

		// fetch back
		update = updateDao.findById(id);
		
		// test
		assertEquals(totalCount, update.getTotalCount());
		assertEquals(newCount, update.getNewCount());
	}

	@Test
	@Transactional
	public void deleteUpdate() {
		Update update = new UpdateBuilder()
				.withFeed(feedDao.findById(1L))
				.build();

		updateDao.create(update);

		Long id = update.getId();
		update = null;
		
		updateDao.delete(id);

		update = updateDao.findById(id);
		assertNull(update);
	}

	@Test
	@Transactional
	public void findLatest() {
		Update nonExistentUpdate = updateDao.findLatestUpdateByFeedId(0L);
		assertNull(nonExistentUpdate);

		Update update = updateDao.findLatestUpdateByFeedId(1L);
		assertEquals(new Long(120), update.getTotalCount());
		assertEquals(new Long(5), update.getNewCount());
		assertEquals(new LocalDateTime(2014, 10, 17, 14, 38, 0), update.getUpdated());

		update = updateDao.findLatestUpdateByFeedId(2L);
		assertEquals(new Long(980), update.getTotalCount());
		assertEquals(new Long(63), update.getNewCount());
		assertEquals(new LocalDateTime(2014, 10, 17, 18, 00, 1), update.getUpdated());
	}
}
