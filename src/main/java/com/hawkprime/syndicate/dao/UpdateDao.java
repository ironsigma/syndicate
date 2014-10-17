package com.hawkprime.syndicate.dao;

import org.springframework.stereotype.Repository;

import com.hawkprime.syndicate.model.Update;

/**
 * Update DAO.
 */
@Repository
public class UpdateDao extends GenericDao<Update> {
	public Update getLatestUpdateFeedById(Long id) {
		return (Update) entityManager
				.createQuery("SELECT u FROM Update u WHERE feed.id=:id ORDER BY updated DESC")
				.setParameter("id", id)
				.setMaxResults(1)
				.getSingleResult();
	}
}
