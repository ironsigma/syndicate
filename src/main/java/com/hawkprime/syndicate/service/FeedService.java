package com.hawkprime.syndicate.service;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hawkprime.syndicate.dao.FeedDao;
import com.hawkprime.syndicate.dao.UpdateDao;
import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.Update;

/**
 * Feed Service.
 */
@Component
public class FeedService {
	@Autowired
	private FeedDao feedDao;

	@Autowired
	private UpdateDao updateDao;

	@Transactional(readOnly=true)
	public List<Feed> list() {
		return feedDao.findAll();
	}

	@Transactional
	public void saveTotals(final Feed feed, final long totalCount, final long newCount) {
		final Update update = new Update();
		update.setFeed(feed);
		update.setTotalCount(totalCount);
		update.setNewCount(newCount);
		update.setUpdated(new LocalDateTime());
		updateDao.create(update);
	}
}
