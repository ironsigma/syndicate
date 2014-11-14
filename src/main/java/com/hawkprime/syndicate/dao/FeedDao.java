package com.hawkprime.syndicate.dao;

import com.hawkprime.syndicate.model.Feed;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * Feed DAO.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
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
