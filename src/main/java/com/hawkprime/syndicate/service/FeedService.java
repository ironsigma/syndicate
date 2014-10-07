package com.hawkprime.syndicate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hawkprime.syndicate.dao.FeedDao;
import com.hawkprime.syndicate.model.Feed;

/**
 * Feed Service.
 */
@Component
public class FeedService {
	@Autowired
	private FeedDao feedDao;

	public List<Feed> list() {
		return feedDao.list();
	}
}
