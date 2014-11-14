package com.hawkprime.syndicate.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hawkprime.syndicate.model.Feed;

/**
 * Feed DAO.
 */
@Repository
public class FeedDao extends AbstractDao<Feed> {
	/**
	 * Find active feeds.
	 * @return List of active feeds, empty list if no results
	 */
	@SuppressWarnings("unchecked")
	public List<Feed> findActive() {
		return getEntityManager()
				.createNamedQuery("Feed.findActiveFeeds")
				.getResultList();
	}
}
