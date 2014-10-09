package com.hawkprime.syndicate.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.service.FeedService;

/**
 * RSS Parse Task.
 */
public class RssParserTask {
	private static final int DELAY_30_SECONDS = 30000;
	private static final int EVERY_60_SECONDS = 60000;
	private static final Logger LOG  = LoggerFactory.getLogger(RssParserTask.class);

	@Autowired
	private FeedService feedService; 
	
	/**
	 * Parse RSS feeds.
	 */
	@Scheduled(initialDelay=DELAY_30_SECONDS, fixedDelay=EVERY_60_SECONDS)
	public void contextDestroyed() {
		LOG.info("--- Starting RSS Parse");
		List<Feed> feedList = feedService.list();
		
		for (Feed feed : feedList) {
			LOG.debug(String.format("Checking feed \"%s\" (%s) ...", feed.getName(), feed.getUrl()));
		}
		LOG.info("--- Done");
	}
}