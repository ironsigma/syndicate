package com.hawkprime.syndicate.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * RSS Parse Task.
 */
public class RssParserTask {
	private static final int DELAY_30_SECONDS = 30000;
	private static final int EVERY_60_SECONDS = 60000;

	private static final Logger LOG  = LoggerFactory.getLogger(RssParserTask.class);

	/**
	 * Parse RSS feeds.
	 */
	@Scheduled(initialDelay=DELAY_30_SECONDS, fixedDelay=EVERY_60_SECONDS)
	public void contextDestroyed() {
		LOG.info("--- Starting RSS Parse");
		try {
			Thread.sleep(120000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOG.info("--- Done");
	}
}