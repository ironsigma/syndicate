package com.hawkprime.syndicate.dao;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.hawkprime.syndicate.model.Update;

/**
 * Update DAO.
 */
@Repository
public class UpdateDao extends AbstractDao<Update> {
	public int percentNewByFeedId(final Long id) {
		final Object[] counts = (Object[]) getEntityManager()
					.createQuery("SELECT sum(totalCount), sum(newCount) FROM Update WHERE feed.id=:id")
					.setParameter("id", id)
					.getSingleResult();

		if (counts[0] == null) {
			return 0;
		}

		final long totalCount = Long.valueOf(counts[0].toString());
		if (totalCount == 0) {
			return 0;
		}

		final long newCount = Long.valueOf(counts[1].toString());
		final double percent = 100.00;
		return (int) (Math.round(newCount * percent / totalCount));
	}

	public long countNewPostsByFeedId(final Long id) {
		return ((Number) getEntityManager()
					.createQuery("SELECT sum(newCount) FROM Update WHERE feed.id=:id")
					.setParameter("id", id)
					.getSingleResult())
					.longValue();
	}

	public Update findOldestUpdateByFeedId(final Long id) {
		try {
			return (Update) getEntityManager()
					.createQuery("SELECT u FROM Update u WHERE feed.id=:id ORDER BY updated ASC")
					.setParameter("id", id)
					.setMaxResults(1)
					.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	public Update findLatestUpdateByFeedId(final Long id) {
		try {
			return (Update) getEntityManager()
					.createQuery("SELECT u FROM Update u WHERE feed.id=:id ORDER BY updated DESC")
					.setParameter("id", id)
					.setMaxResults(1)
					.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}
}
