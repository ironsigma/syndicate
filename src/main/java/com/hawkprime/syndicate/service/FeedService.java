package com.hawkprime.syndicate.service;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hawkprime.syndicate.dao.FeedDao;
import com.hawkprime.syndicate.dao.UpdateDao;
import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.Update;
import com.hawkprime.syndicate.util.FrequencyCalculator;

/**
 * Feed Service.
 */
@Service
public class FeedService {
	private static final Logger LOG = LoggerFactory.getLogger(FeedService.class);
	@Autowired
	private FeedDao feedDao;

	@Autowired
	private UpdateDao updateDao;

	@Autowired
	private FrequencyCalculator frequencyCalculator;

	/**
	 * Find all feeds.
	 * @return results.
	 */
	@Transactional(readOnly=true)
	public List<Feed> findAll() {
		return feedDao.findAll();
	}

	/**
	 * Find all active feeds.
	 * @return results.
	 */
	@Transactional(readOnly=true)
	public List<Feed> findActiveFeeds() {
		return feedDao.findActive();
	}

	@Transactional
	public void updateFeedFrequency(final Feed feed) {
		final int percentNewPosts = updateDao.percentNewByFeedId(feed.getId());
		final int newUpdateFrequency = frequencyCalculator.calculateNewFrequency(
				feed.getUpdateFrequency(), percentNewPosts);

		if (newUpdateFrequency != feed.getUpdateFrequency()) {
			feed.setUpdateFrequency(newUpdateFrequency);
			feedDao.update(feed);
		}
	}

	@Transactional
	public void saveTotals(final Feed feed, final long totalCount, final long newCount) {
		final Update update = new Update();
		update.setFeed(feed);
		update.setTotalCount(totalCount);
		update.setNewCount(newCount);
		update.setUpdated(LocalDateTime.now());
		updateDao.create(update);
	}

	@Transactional(readOnly=true)
	public long postPerMinute(final Feed feed) {
		final Update oldestUpdate = updateDao.findOldestUpdateByFeedId(feed.getId());
		if (oldestUpdate == null) {
			return 0;
		}
		final LocalDateTime now = LocalDateTime.now();
		final long minutesSinceOldestUpdate = Minutes.minutesBetween(oldestUpdate.getUpdated(), now).getMinutes();
		return updateDao.countNewPostsByFeedId(feed.getId()) / minutesSinceOldestUpdate;
	}

	@Transactional(readOnly=true)
	public boolean needsUpdate(final Feed feed) {
		if (feed.getUpdateFrequency() <= 0) {
			LOG.warn("Feed \"{}\" update frequency is {}, not updating",
					feed.getName(), feed.getUpdateFrequency());
			return false;
		}
		final Update feedUpdate = updateDao.findLatestUpdateByFeedId(feed.getId());
		if (feedUpdate == null) {
			LOG.debug("Feed \"{}\" has no previous updates", feed.getName());
			return true;
		}
		final LocalDateTime now = LocalDateTime.now();
		final int minutesSinceUpdate = Minutes.minutesBetween(feedUpdate.getUpdated(), now).getMinutes();
		if (feed.getUpdateFrequency() <= minutesSinceUpdate) {
			LOG.debug("Feed \"{}\" updates every {} minutes, "
					+ "it's been {} minutes since lasts update, update required",
					feed.getName(), feed.getUpdateFrequency(), minutesSinceUpdate);
			return true;
		}
		LOG.debug("Feed \"{}\" updates every {} minutes, "
				+ "it's been {} minutes since lasts update, no update required",
				feed.getName(), feed.getUpdateFrequency(), minutesSinceUpdate);
		return false;
	}

	public void setUpdateDao(final UpdateDao updateDao) {
		this.updateDao = updateDao;
	}

	public void setFeedDao(final FeedDao feedDao) {
		this.feedDao = feedDao;
	}

	public void setFrequencyCalculator(final FrequencyCalculator frequencyCalculator) {
		this.frequencyCalculator = frequencyCalculator;
	}
}
