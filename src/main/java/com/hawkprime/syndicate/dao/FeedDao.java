package com.hawkprime.syndicate.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hawkprime.syndicate.model.Feed;

/**
 * Feed DAO.
 */
@Repository
public class FeedDao extends AbstractDao<Feed> {
	@SuppressWarnings("unchecked")
	public List<Feed> findActive() {
		return getEntityManager()
				.createQuery("SELECT f FROM Feed f WHERE active=true ORDER BY name")
				.getResultList();
	}
}
