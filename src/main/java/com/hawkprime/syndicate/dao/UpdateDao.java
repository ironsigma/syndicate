package com.hawkprime.syndicate.dao;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.hawkprime.syndicate.model.Update;

/**
 * Update DAO.
 */
@Repository
public class UpdateDao extends AbstractDao<Update> {
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
