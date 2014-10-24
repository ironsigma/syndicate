package com.hawkprime.syndicate.task;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.hawkprime.syndicate.adapter.SyndEntryToPostAdapter;
import com.hawkprime.syndicate.adapter.SyndEntryToPostAdapterException;
import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.service.FeedService;
import com.hawkprime.syndicate.service.PostService;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.fetcher.impl.FeedFetcherCache;
import com.sun.syndication.fetcher.impl.HashMapFeedInfoCache;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.sun.syndication.io.FeedException;

/**
 * RSS Parse Task.
 */
public class RssParserTask {
	private static final int DELAY_30_SECONDS = 30000;
	private static final int EVERY_60_SECONDS = 60000;
	private static final Logger LOG  = LoggerFactory.getLogger(RssParserTask.class);

	@Autowired
	private FeedService feedService;

	@Autowired
	private PostService postService;

	private FeedFetcherCache feedInfoCache;
	private FeedFetcher feedFetcher;

	@PostConstruct
	public void init() {
		feedInfoCache = HashMapFeedInfoCache.getInstance();
		feedFetcher = new HttpURLFeedFetcher(feedInfoCache);
	}

	/**
	 * Parse RSS feeds.
	 */
	@Scheduled(initialDelay=DELAY_30_SECONDS, fixedDelay=EVERY_60_SECONDS)
	public void parseFeeds() {
		LOG.info("--- Starting RSS Parse");
		final List<Feed> feedList = feedService.findActiveFeeds();

		for (Feed feed: feedList) {
			try {
				LOG.debug("Checking feed \"{}\" ({}) ...", feed.getName(), feed.getUrl());
				if (!feedService.needsUpdate(feed)) {
					continue;
				}

				long totalCount = 0;
				long newCount = 0;

				final SyndFeed syndFeed = feedFetcher.retrieveFeed(new URL(feed.getUrl()));

				@SuppressWarnings("unchecked")
				final List<SyndEntry> entries = syndFeed.getEntries();

				for (SyndEntry entry : entries) {
					totalCount++;
					try {
						newCount += postService.save(SyndEntryToPostAdapter.convert(entry, feed));
					} catch (SyndEntryToPostAdapterException ex) {
						LOG.error("Unable to convert Syndicate Entry to Post");
						LOG.debug(ExceptionUtils.getStackTrace(ex));
					}
				}

				feedService.saveTotals(feed, totalCount, newCount);
				LOG.debug("Total entries {}, new entries {}", totalCount, newCount);

				feedService.updateFeedFrequency(feed);

			} catch (IOException ex) {
				LOG.error("IO Error reading feed");
				LOG.debug(ExceptionUtils.getStackTrace(ex));

			} catch (FeedException ex) {
				LOG.error("Error reading feed");
				LOG.debug(ExceptionUtils.getStackTrace(ex));

			} catch (FetcherException ex) {
				LOG.error("Error fetching feed");
				LOG.debug(ExceptionUtils.getStackTrace(ex));
			}
		}

		LOG.info("--- Done");
	}
}
