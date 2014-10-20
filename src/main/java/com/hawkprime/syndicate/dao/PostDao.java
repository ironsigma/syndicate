package com.hawkprime.syndicate.dao;

import org.springframework.stereotype.Repository;

import com.hawkprime.syndicate.model.Post;

/**
 * Post DAO.
 */
@Repository
public class PostDao extends AbstractDao<Post> {
	public boolean doesPosExistsWithGuid(final String guid) {
		return 0 != (Long) getEntityManager()
				.createQuery("SELECT COUNT(guid) FROM Post WHERE guid=:guid")
				.setParameter("guid", guid)
				.getSingleResult();
	}
}
