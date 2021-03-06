package com.hawkprime.syndicate.dao;

import com.hawkprime.syndicate.model.Update;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

/**
 * Update DAO.
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
@Repository
public class UpdateDao extends AbstractDao<Update> {
	private static final String ID = "id";

	/**
	 * Return the percent new posts.
	 * @param id Feed id
	 * @return percentage as an integer from 0 to 100
	 */
	public int percentNewByFeedId(final Long id) {
		final Object[] counts = (Object[]) getEntityManager()
					.createNamedQuery("Update.totalAndNewCountByFeedId")
					.setParameter(ID, id)
					.getSingleResult();

		if (counts[0] == null) {
			return 0;
		}

		final long totalCount = Long.valueOf(counts[0].toString());
		int percentNew = 0;
		if (totalCount != 0) {
			final long newCount = Long.valueOf(counts[1].toString());
			final double percent = 100.00;
			percentNew = (int) (Math.round(newCount * percent / totalCount));
		}
		return percentNew;
	}

	/**
	 * Count new posts by feed id.
	 *
	 * @param id the id
	 * @return the long
	 */
	public long countNewPostsByFeedId(final Long id) {
		return ((Number) getEntityManager()
					.createNamedQuery("Update.newCountByFeedId")
					.setParameter(ID, id)
					.getSingleResult())
					.longValue();
	}

	/**
	 * Find oldest update by feed id.
	 *
	 * @param id the id
	 * @return the update
	 */
	public Update findOldestUpdateByFeedId(final Long id) {
		try {
			return (Update) getEntityManager()
					.createNamedQuery("Update.updatesByFeedIdSortedByUpdatedAsc")
					.setParameter(ID, id)
					.setMaxResults(1)
					.getSingleResult();
		} catch (final NoResultException ex) {
			return null;
		}
	}

	/**
	 * Find latest update by feed id.
	 *
	 * @param id the id
	 * @return the update
	 */
	public Update findLatestUpdateByFeedId(final Long id) {
		try {
			return (Update) getEntityManager()
					.createNamedQuery("Update.updatesByFeedIdSortedByUpdatedDesc")
					.setParameter(ID, id)
					.setMaxResults(1)
					.getSingleResult();
		} catch (final NoResultException ex) {
			return null;
		}
	}
}
